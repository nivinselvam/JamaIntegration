package com;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
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
	String request;
	Map<String, String> testcaseAndTestrunMap;
	String responseString;
	ResponseBody body;
	Response response;
	String testplan, status, itemName, fileName, testplanName, testCycleName, attachmentName, attachmentContent,
			consolidatedTestCases;
	Pattern pattern;
	Matcher match;

	/*
	 * This method is used to retrieve the test cycles of a particular test plan
	 * This takes test plan id as input argument and returns the list of test cycles
	 * as String array
	 */
	public String[] getTestCyclesFromTestPlan(String testPlanID) {
		int testCycleCount = 0, i = 0;
		response = RestAssured.get(baseURL + "testplans/" + testPlanID + "/testcycles");
		body = response.getBody();
		pattern = Pattern.compile("\"totalResults\":\\d{1,}");
		match = pattern.matcher(body.asString());
		if (match.find()) {
			testCycleCount = Integer.parseInt(match.group().substring(15));
		}
		String[] testCycleIDs = new String[testCycleCount];
		pattern = Pattern.compile("\"id\":\\w*");
		match = pattern.matcher(body.asString());
		while (match.find()) {
			testCycleIDs[i] = match.group().substring(5);
			i++;
		}
		if (testCycleCount == 0) {
			return null;
		} else {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ "Test cycles in the test plan " + testPlanID + ": " + testCycleCount);
			return testCycleIDs;
		}

	}

	/*
	 * This method is used to retrieve the test cases of a particular test cycle
	 * This takes test cycle id as input argument and returns the list of test cases
	 * as String array
	 */
//	public String[] getTestCasesFromTestCycle(String testCycleID) {
//		int testCaseCount = 0, i = 0;
//		response = RestAssured.get(baseURL + "testcycles/" + testCycleID + "/testruns");
//		body = response.getBody();
//		pattern = Pattern.compile("\"totalResults\":\\d{1,}");
//		match = pattern.matcher(body.asString());
//		if (match.find()) {
//			testCaseCount = Integer.parseInt(match.group().substring(15));
//		}
//		Initializer.GUI.lblAvailableTcValue.setText(String.valueOf(testCaseCount));
//		String[] testCaseIDs = new String[testCaseCount];
//		pattern = Pattern.compile("\"testCase\":\\w*");
//		match = pattern.matcher(body.asString());
//		while (match.find()) {
//			testCaseIDs[i] = match.group().substring(11);
//			i++;
//		}
//		if (testCaseCount == 0) {
//			return null;
//		} else {
//			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
//					+ "Test cases assigned to test Cycle " + testCycleID + ": " + testCaseCount);
//			return testCaseIDs;
//		}
//
//	}

	public Map<String, String> getTestCasesFromTestCycle(String testCycleID) {
		// Test case id's will be the key and test run id's will be the value;
		testcaseAndTestrunMap = new HashMap<String, String>();
		int testCaseCount = 0, i = 0;
		response = RestAssured.get(baseURL + "testcycles/" + testCycleID + "/testruns");
		body = response.getBody();
		pattern = Pattern.compile("\"totalResults\":\\d{1,}");
		match = pattern.matcher(body.asString());
		if (match.find()) {
			testCaseCount = Integer.parseInt(match.group().substring(15));
		}
		Initializer.GUI.lblAvailableTcValue.setText(String.valueOf(testCaseCount));

		String[] testcaseIDs = new String[testCaseCount];
		String[] testcaseRunIDs = new String[testCaseCount];

		pattern = Pattern.compile("\"testCase\":\\w*");
		match = pattern.matcher(body.asString());
		while (match.find()) {
			testcaseIDs[i] = match.group().substring(11);
			i++;
		}
		i = 0;
		pattern = Pattern.compile("\"id\":[\\d]+");
		match = pattern.matcher(body.asString());
		while (match.find()) {
			testcaseRunIDs[i] = match.group().substring(5);
			i++;
		}
		for (int j = 0; j < testCaseCount; j++) {
			testcaseAndTestrunMap.put(testcaseIDs[j], testcaseRunIDs[j]);
		}
		if (testCaseCount == 0) {
			return null;
		} else {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ "Test cases assigned to test Cycle " + testCycleID + ": " + testCaseCount);
			return testcaseAndTestrunMap;
		}

	}

	/*
	 * This method is used to retrieve the attachment IDs associated with a
	 * particular test case This takes test case id as input argument and returns
	 * the list of attachment ids as String array Please note, this will only return
	 * the attachment ids and not the contents of the attachment
	 */
	public String[] getAttachmentsInTestCase(String testCaseID) {
		int attachmentsCount = 0, i = 0;
		response = RestAssured.get(baseURL + "items/" + testCaseID + "/attachments");
		body = response.getBody();
		pattern = Pattern.compile("\"totalResults\":\\d{1,}");
		match = pattern.matcher(body.asString());
		if (match.find()) {
			attachmentsCount = Integer.parseInt(match.group().substring(15));
		}
		String[] attachmentIDs = new String[attachmentsCount];
		pattern = Pattern.compile("\"id\":\\w*");
		match = pattern.matcher(body.asString());
		while (match.find()) {
			attachmentIDs[i] = match.group().substring(5);
			i++;
		}
		if (attachmentsCount == 0) {
			return null;
		} else {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ "Count of attachments in test case " + testCaseID + ": " + attachmentsCount);
			return attachmentIDs;
		}

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
		System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime()) + "Content of attachment: "
				+ attachmentID);
		System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime()) + body.asString());
		return body.asString();
	}

	/*
	 * This method is used to retrieve name of a JAMA item. This takes the item ID
	 * as input argument and returns the name of the item in string format.
	 */
	public String getItemName(String itemID) {
		itemName = "";
		response = RestAssured.get(baseURL + "abstractitems/" + itemID);
		body = response.getBody();
		pattern = Pattern.compile("\"name\":\"[\\w|\\s]*");
		match = pattern.matcher(body.asString());
		if (match.find()) {
			itemName = match.group().substring(8);
		} else {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ "This item does not contain name information");
		}
		return itemName;
	}

	public String getAttachmentFileName(String itemID) {
		fileName = "";
		response = RestAssured.get(baseURL + "abstractitems/" + itemID);
		body = response.getBody();
		pattern = Pattern.compile("\"fileName\":\"[\\w|\\s|-]*");
		match = pattern.matcher(body.asString());
		if (match.find()) {
			fileName = match.group().substring(12);
		} else {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ "This item does not contain file information");
		}
		return fileName;
	}

	public String getAttachmentFileNameWithoutWebService(String response) {
		fileName = "";
		pattern = Pattern.compile("\"fileName\":\"[\\w|\\s|-]*");
		match = pattern.matcher(body.asString());
		if (match.find()) {
			fileName = match.group().substring(12);
		} else {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ "This item does not contain file information");
		}
		return fileName;
	}

	/*
	 * This method is used to fetch the JSON details of the test run. This returns
	 * the JSON details without the meta data
	 */
	public String getTestrunJSON(String testrunID) {
		response = RestAssured.get(baseURL + "abstractitems/" + testrunID);
		body = response.getBody();
		return body.asString();
	}

	public String getTestcaseDetails(String testcaseID) {
		response = RestAssured.get(baseURL + "abstractitems/" + testcaseID);
		body = response.getBody();
		return body.asString();
	}

	/*
	 * This method is used to update back the results to the JAMA portal.
	 */
	public void updateTestResultsInJAMA(String testCaseJSON, String testCaseStatus) throws ParseException {
		String result = testCaseStatus, id = "";
		if (result.contains("ed")) {
			result = result.toUpperCase();
		} else {
			result = (result + "ed").toUpperCase();
		}
		// To grep the test case details without meta data
		pattern = Pattern.compile("\\{\"id\":.*}");
		match = pattern.matcher(testCaseJSON);
		if (match.find()) {
			responseString = match.group();
		}
		
		//To update the test step status as execution result in the JSON
		pattern = Pattern.compile("\"status\":\"[\\w]+\"");
		match = pattern.matcher(responseString);
		if (match.find()) {
			status = match.group();
			responseString = responseString.replaceAll(status, "\"status\":\"" + result + "\"");
			
		}

		//To fectch the id from the JSON string
		pattern = Pattern.compile("\"id\":[\\d]+");
		match = pattern.matcher(responseString);
		if (match.find()) {
			id = match.group().substring(5);			
		}
		
		RestAssured.baseURI = baseURL;
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(responseString);
		response = requestSpecification.put(baseURL+"testruns/"+id);
		System.out
				.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime()) +"Response received from the JAMA server - " +response.getStatusCode());
	}

}