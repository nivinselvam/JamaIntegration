package com;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class WebServiceProcessor {

	static String baseURL = "https://" + Constants.username + ":" + Constants.password
			+ "@jama.verifone.com/contour/rest/latest/";
	String request = "/testruns/11183131";
	String responseString;
	ResponseBody body;
	Response response;
	String testplan;
	int testCaseCount = 0;
	
	

	public WebServiceProcessor() {

	}
	
	public int getTestcaseCount() {
		return testCaseCount;
	}

	public void putData() throws ParseException {
		RestAssured.baseURI = baseURL;
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(responseString);
		response = requestSpecification.put(request);
		System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+response.getStatusCode());
	}

	/*
	 * This method is used to retrieve the test cycles of a particular test plan
	 * This takes test plan id as input argument and returns the list of test cycles
	 * as String array
	 */
	public String[] getTestCyclesFromTestPlan(String testPlanID) {
		int positionOfResultCount, testCycleCount, i = 0;
		response = RestAssured.get(baseURL + "testplans/" + testPlanID + "/testcycles");
		body = response.getBody();
		positionOfResultCount = body.asString().indexOf("\"totalResults\":");
		testCycleCount = Integer
				.parseInt(body.asString().substring(positionOfResultCount + 15, positionOfResultCount + 16));
		String[] testCycleIDs = new String[testCycleCount];
		System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+"Test cycles in the test plan " + testPlanID + ": " + testCycleCount);
		Pattern pattern = Pattern.compile("\"id\":\\w*");
		Matcher match = pattern.matcher(body.asString());
		while (match.find()) {
			testCycleIDs[i] = match.group().substring(5);
			i++;
		}
		return testCycleIDs;
	}

	/*
	 * This method is used to retrieve the test cases of a particular test cycle
	 * This takes test cycle id as input argument and returns the list of test cases
	 * as String array
	 */
	public String[] getTestCasesFromTestCycle(String testCycleID) {
		int positionOfResultCount, i = 0;
		response = RestAssured.get(baseURL + "testcycles/" + testCycleID + "/testruns");
		body = response.getBody();
		positionOfResultCount = body.asString().indexOf("\"totalResults\":");
		testCaseCount = Integer
				.parseInt(body.asString().substring(positionOfResultCount + 15, positionOfResultCount + 16));
		System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+"Test cases assigned to test Cycle " + testCycleID + ": " + testCaseCount);
		String[] testCaseIDs = new String[testCaseCount];
		Pattern pattern = Pattern.compile("\"testCase\":\\w*");
		Matcher match = pattern.matcher(body.asString());
		while (match.find()) {
			testCaseIDs[i] = match.group().substring(11);
			i++;
		}
		return testCaseIDs;
	}

	/*
	 * This method is used to retrieve the attachment IDs associated with a
	 * particular test case This takes test case id as input argument and returns
	 * the list of attachment ids as String array Please note, this will only return
	 * the attachment ids and not the contents of the attachment
	 */
	public String[] getAttachmentsInTestCase(String testCaseID) {
		int positionOfResultCount, attachmentsCount, i = 0;
		response = RestAssured.get(baseURL + "items/" + testCaseID + "/attachments");
		body = response.getBody();
		positionOfResultCount = body.asString().indexOf("\"totalResults\":");
		attachmentsCount = Integer
				.parseInt(body.asString().substring(positionOfResultCount + 15, positionOfResultCount + 16));
		System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+"Count of attachments in test case " + testCaseID + ": " + attachmentsCount);
		String[] attachmentIDs = new String[attachmentsCount];
		Pattern pattern = Pattern.compile("\"id\":\\w*");
		Matcher match = pattern.matcher(body.asString());
		while (match.find()) {
			attachmentIDs[i] = match.group().substring(5);
			i++;
		}
		return attachmentIDs;
	}

	/*
	 * This method is used to retrieve the attachment contents This takes attachment
	 * id as input argument and returns content of the attachment in String format
	 * Please note, this method supports only text files since this is done for PATS
	 * tool. Support for other tools would be added as enhancement based on demand.
	 */
	public String getAttachmentContent(String attachmentID) {
		response = RestAssured.get(baseURL + "attachments/" + attachmentID + "/file");
		body = response.getBody();
		System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+"Content of attachment: " + attachmentID);
		System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+body.asString());
		return body.asString();
	}

	/*
	 * This method is used to retrieve name of a JAMA item. This takes the item ID
	 * as input argument and returns the name of the item in string format.
	 */
	public String getItemName(String itemID) {
		String name = "";
		response = RestAssured.get(baseURL + "abstractitems/" + itemID);
		body = response.getBody();
		Pattern pattern = Pattern.compile("\"name\":\"[\\w|\\s]*");
		Matcher match = pattern.matcher(body.asString());
		if (match.find()) {
			name = match.group().substring(8);
		} else {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+"This item does not contain name information");
		}
		return name;
	}

	public String getAttachmentFileName(String itemID) {
		String name = "";
		response = RestAssured.get(baseURL + "abstractitems/" + itemID);
		body = response.getBody();
		Pattern pattern = Pattern.compile("\"fileName\":\"[\\w|\\s]*");
		Matcher match = pattern.matcher(body.asString());
		if (match.find()) {
			name = match.group().substring(12);
		} else {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+"This item does not contain file information");
		}
		return name;
	}

	public String getTestcaseFromTestPlan(String testplan) {
		String testplanName, testCycleName, attachmentName, consolidatedTestCases = "";
		try {
			for (String currentTestCycle : getTestCyclesFromTestPlan(testplan)) {
				testplanName = getItemName(testplan);
				for (String currentTestCase : getTestCasesFromTestCycle(currentTestCycle)) {
					testCycleName = getItemName(currentTestCycle);
					for (String currentAttachment : getAttachmentsInTestCase(currentTestCase)) {
						attachmentName = getItemName(currentAttachment);
						consolidatedTestCases = consolidatedTestCases + "\n\n"
								+ getAttachmentContent(currentAttachment);
					}
					
				}
				
			}
			return consolidatedTestCases;
		} catch (Exception e) {
			if (e.toString().contains("java.net.UnknownHostException")) {
				return Constants.jamaConnectivityIssue;
			} else {
				return Constants.invalidTestPlanID;
			}

		}

	}
}