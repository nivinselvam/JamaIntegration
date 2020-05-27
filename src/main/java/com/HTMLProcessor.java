package com;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HTMLProcessor {
	private TreeMap<String, String> testcaseResults;
	private String filePath;
	private String query;
	private Document document;
	private Pattern pattern;
	private Matcher match;
	private File file;

	private void getTestResultsFromHTML(String filePath) {
		testcaseResults = new TreeMap<String, String>(new TestResultComparator());
		query = "li[class^=node level-1 leaf]";
		pattern = Pattern.compile("[\\w]+-TC-[\\d]+");
		try {
			file = new File(filePath);
			document = Jsoup.parse(file, "UTF-8");
			for (Element element : document.select(query)) {
				match = pattern.matcher(element.text());
				if (match.find()) {
					testcaseResults.put(match.group().toString(), element.attr("status"));
				}
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	public void updateTestCaseResultsLocally(String filePath) {
		// filePath =
		// "C:\\automnTool-V4\\automn\\AutomationInput\\ReferenceFiles\\PATS-Execution-Summary-Report
		// (1).html";
		getTestResultsFromHTML(filePath);
		for (Map.Entry<String, String> currentEntry : testcaseResults.entrySet()) {
			for (TestCaseDetails currentTestcaseDetail : Initializer.testcaseDetails) {
				if (currentEntry.getKey().equals(currentTestcaseDetail.getTestcaseDocumentKey())) {
					currentTestcaseDetail.setTestcaseStatus(currentEntry.getValue());
				}
			}
		}
	}

}
