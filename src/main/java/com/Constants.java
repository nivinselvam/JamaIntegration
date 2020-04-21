package com;

public final class Constants {
	
	public final static String username = "nivins1";
	public final static String password = "Mypassw0rdis1";
	
	
	/*
	 * Below constants are the texts to be used in the logging
	 * \n is added to all the texts to make sure the line is printed in new line
	 */
	public final static String startProcessText = "\n---------------------------------------------------------------------------------\nStarted processing. Please wait...";
	public final static String endOfProcessText = "\nFinished processing\n---------------------------------------------------------------------------------";
	public final static String testPlanIDMandate = "\nTest plan id field cannot be left blank";
	public final static String invalidTestPlanID = "\nEntered test plan ID is invalid, so test suite was not updated";
	public final static String jamaConnectivityIssue = "\nUnable to connect to JAMA. Please check connectivity";
	public final static String deletedExistingTestSuite = "\nTest suite xml file already existed.\nExisting file was deleted and new test suite created";
	public final static String unableToDeleteTestSuite = "\nUnable to delete the existing test suite xml file. Please delete the file manually and retry";
	public final static String testcaseTagAdded = "\nTest case tag was added to test suite xml file";
	public final static String testcaseTagNotAdded = "\nUnable to add test cases tag to the test suite xml file";
	public final static String unableToAddTestcase = "\nUnable to add test cases to the test suite.";
	public final static String unableToCreateTestSuite = "\nUnable to create the xml file";
	public final static String testSuiteCreated= "\nTest suite XML file created successfully";
	public final static String testcasesAdded = "\nTest case(s) updated to the TestSuite";
	public final static String fileDoesntExist = "\nFile does not exist";
}