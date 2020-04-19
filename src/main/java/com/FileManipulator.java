package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.testng.log4testng.Logger;
import org.w3c.dom.Document;

public class FileManipulator{

	File file;
	FileInputStream fileInputStream;
	FileWriter fileWriter;
	BufferedReader bufferedReader;
	BufferedWriter writer;
	String temp;
	String textFromFile;
	String fileNameWithPath = "C:\\Users\\nivins1\\OneDrive - Verifone\\Desktop\\TestSuite.xml";
	Pattern pattern;
	Matcher matcher;
	boolean valid;
	Logger logger = Logger.getLogger(FileManipulator.class);
	
	/*
	 * This method is used to create a folder in the specified path It takes two
	 * arguments as input. 1. Name in which the folder should be created. 2. Path in
	 * which folder should be created.
	 */
	public String createFolder(String folderName, String path) {
		file = new File(path + folderName);
		if (file.exists()) {
			logger.warn(folderName + " already exists");
			return "\n"+folderName + " already exists";
		} else {
			if (file.mkdir()) {
				logger.info("Folder " + folderName + " was created successfully");
				return "\nFolder " + folderName + " was created successfully";
			} else {
				logger.info("Unable to create folder " + folderName);
				return "\nUnable to create folder " + folderName;
			}
		}

	}

	/*
	 * This method is used to create a text file in the specified path with the
	 * given content. This takes two arguments as input. 1. Content in the String
	 * format i.e., content with which the text file should be created. 2. Path in
	 * which the text file should be created.
	 */
	public String createTextFile(String content, String path) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(content);
			return "\nText file was created successfully in"+path;
		} catch (IOException e) {
			e.printStackTrace();
			logger.fatal("Unable to create the text file");
			return "\nUnable to create the text file";
		} finally {
			try {
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/*
	 * This method is used to create xml in the specified path. Please note, this
	 * only creates an empty xml file and will not contain any nodes.
	 */
	public String createXMLFile(String fileNameWithPath) {
		file = new File(fileNameWithPath);
		if (file.exists()) {
			logger.info("File already exists");
			file.delete();
			createXMLFile(fileNameWithPath);
			return "\nTest suite xml file already exists.\nExisting test suite file was deleted and new file was created.";
		} else {
			try {
				DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
				Document document = documentBuilder.newDocument();
				// To create xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource domSource = new DOMSource(document);
				File file = new File(fileNameWithPath);
				StreamResult streamResult = new StreamResult(file);
				transformer.transform(domSource, streamResult);
				logger.info("XML file creation completed");
				return "\nXML file create successfully";
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				logger.info("Error occured while trying to create xml file");
				return "\nError occured while trying to create xml file";
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
				logger.info("Error occured while trying to create xml file");
				return "\nError occured while trying to create xml file";
			} catch (TransformerException e) {
				e.printStackTrace();
				logger.info("Error occured while trying to create xml file");
				return "\nError occured while trying to create xml file";
			}
		}
	}

	/*
	 * This method is to check whether an xml is empty i.e., if it contains any
	 * nodes. This will be used to check if the <testcases> tag should be added
	 * before adding test cases.
	 */
	public boolean isXMLEmpty(String fileNameWithPath) throws IOException {
		file = new File(fileNameWithPath);
		temp = "";
		if (file.exists()) {
			fileInputStream = new FileInputStream(file);
			bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			while ((temp = bufferedReader.readLine()) != null) {
				if (temp.contains("<testcases>")) {
					return false;
				}
			}
			return true;
		} else {
			logger.fatal("Unable to check if xml is empty, since the file doesnt exist");
			return (Boolean) null;
		}
	}

	public String addTestCasesTag(String fileNameWithPath) throws IOException {
		file = new File(fileNameWithPath);
		temp = "";
		textFromFile = "";
		valid = true;
		pattern = Pattern.compile("<\\?xml.+\\?>");

		if (isXMLEmpty(fileNameWithPath) == true) {
			fileInputStream = new FileInputStream(file);
			bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			while ((temp = bufferedReader.readLine()) != null) {
				textFromFile = textFromFile + temp;
				matcher = pattern.matcher(temp);
				if (matcher.find()) {
					textFromFile = textFromFile + "\n<testcases>\n</testcases>\n";
				}
			}
			fileWriter = new FileWriter(fileNameWithPath);
			writer = new BufferedWriter(fileWriter);
			writer.write(textFromFile);
			bufferedReader.close();
			writer.close();
			fileInputStream.close();
			fileWriter.close();
			logger.info("Test case tag was added");
			return "\nTest case tag was added to the test suite xml file";
		} else {
			logger.info("Test case tag could not be added");
			return "\nTest case tag could not be added";
		}
	}

	public String addTestCasesToTestSuite(String testCaseDetails, String fileNameWithPath) throws IOException {
		file = new File(fileNameWithPath);
		temp = "";
		textFromFile = "";
		if (file.exists()) {
			fileInputStream = new FileInputStream(file);
			bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			while ((temp = bufferedReader.readLine()) != null) {
				textFromFile = textFromFile + temp;
				if (temp.contains("<testcases>")) {
					textFromFile = textFromFile + testCaseDetails;
				}
			}
			fileWriter = new FileWriter(fileNameWithPath);
			writer = new BufferedWriter(fileWriter);
			writer.write(textFromFile);
			bufferedReader.close();
			writer.close();
			fileInputStream.close();
			fileWriter.close();
			logger.info("Test case(s) updated to the TestSuite");
			return "\nTest case(s) updated to the TestSuite";
			

		} else {
			logger.info("File does not exist");
			return "\nFile does not exist";

		}
	}
}
