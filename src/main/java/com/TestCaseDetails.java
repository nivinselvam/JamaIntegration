package com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCaseDetails implements Comparable<TestCaseDetails> {
	private String testcaseID, testcaseDocumentKey, testcaseJSON, testcaseStatus;

	private Pattern pattern;
	private Matcher match;
	private int greppedKey1, greppedKey2;

	public TestCaseDetails(String testcaseID, String testcaseDocumentKey, String JSON, String testcaseStatus) {
		this.testcaseID = testcaseID;
		this.testcaseDocumentKey = testcaseDocumentKey;
		this.testcaseJSON = testcaseJSON;
		this.testcaseStatus = testcaseStatus;
	}

	public String getTestcaseID() {
		return testcaseID;
	}

	public void setTestcaseID(String testcaseID) {
		this.testcaseID = testcaseID;
	}

	public String getTestcaseDocumentKey() {
		return testcaseDocumentKey;
	}

	public void setTestcaseDocumentKey(String testcaseDocumentKey) {
		this.testcaseDocumentKey = testcaseDocumentKey;
	}
	
	public String getTestcaseJSON() {
		return testcaseJSON;
	}
	
	public void setTestcaseJSON(String testcaseJSON) {
		this.testcaseJSON = testcaseJSON;
	}

	public String getTestcaseStatus() {
		return testcaseStatus;
	}

	public void setTestcaseStatus(String testcaseStatus) {
		this.testcaseStatus = testcaseStatus;
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
