package com;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class Constants {
	
	public final static String username = "nivins1";
	public final static String password = "Mypassw0rdis1";
	
	public final static SimpleDateFormat logsDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss   ");
	public final static Date date = new Date();	
	
	/*
	 * Below constants are the texts to be used in the logging
	 * \n is added to all the texts to make sure the line is printed in new line
	 */
	public final static String startProcessText = "Started processing. Please wait...";
	public final static String endOfProcessText = "Finished processing\n----------------------------------------------------------------------------------------------";
	public final static String testPlanIDMandate = "Test plan id field should not be left blank";
	public final static String invalidTestPlanID = "Entered test plan ID is invalid";
	public final static String jamaConnectivityIssue = "Unable to connect to JAMA. Please check connectivity";
	public final static String deletedExistingTestSuite = "Test suite xml file already existed. Existing file was deleted and new test suite created";
	public final static String unableToDeleteTestSuite = "Unable to delete the existing test suite xml file. Please delete the file manually and retry";
	public final static String testcaseTagAdded = "Test case tag was added to test suite xml file";
	public final static String testcaseTagNotAdded = "Unable to add test cases tag to the test suite xml file";
	public final static String unableToAddTestcase = "Unable to add test cases to the test suite.";
	public final static String unableToCreateTestSuite = "Unable to create the xml file";
	public final static String testSuiteCreated= "Test suite XML file created successfully";
	public final static String testcasesAdded = "Test case(s) updated to the TestSuite";
	public final static String fileDoesntExist = "File does not exist";
	public final static String webservicesError = "Something went wrong while fetching the test cases from JAMA.";
}