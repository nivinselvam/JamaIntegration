package com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCaseDetails implements Comparable<TestCaseDetails> {
	private String testrunID, testcaseDocumentKey, testrunJSON, testrunStatus;

	private Pattern pattern;
	private Matcher match;
	private int greppedKey1, greppedKey2;

	public TestCaseDetails(String testrunID, String testcaseDocumentKey, String JSON, String testrunStatus) {
		this.testrunID = testrunID;
		this.testcaseDocumentKey = testcaseDocumentKey;
		this.testrunJSON = JSON;
		this.testrunStatus = testrunStatus;
	}

	public String getTestcaseID() {
		return testrunID;
	}

	public void setTestcaseID(String testcaseID) {
		this.testrunID = testcaseID;
	}

	public String getTestcaseDocumentKey() {
		return testcaseDocumentKey;
	}

	public void setTestcaseDocumentKey(String testcaseDocumentKey) {
		this.testcaseDocumentKey = testcaseDocumentKey;
	}
	
	public String getTestcaseJSON() {
		return testrunJSON;
	}
	
	public void setTestcaseJSON(String testcaseJSON) {
		this.testrunJSON = testcaseJSON;
	}

	public String getTestcaseStatus() {
		return testrunStatus;
	}

	public void setTestcaseStatus(String testcaseStatus) {
		this.testrunStatus = testcaseStatus;
	}

	public int compareTo(TestCaseDetails tc) {
		pattern = Pattern.compile("-TC-[\\d]+");
		match = pattern.matcher(testcaseDocumentKey);
		if (match.find()) {
			greppedKey1 = Integer.parseInt(match.group().toString().substring(4));
		}
		match = pattern.matcher(tc.testcaseDocumentKey);
		if (match.find()) {
			greppedKey2 = Integer.parseInt(match.group().toString().substring(4));
		}
		if(greppedKey1>greppedKey2) {
			return 1;
		}else if(greppedKey1<greppedKey2) {
			return -1;
		}else {
			return 0;
		}
	}

}
