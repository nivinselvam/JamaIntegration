package com;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class AttachmentDownloader {
	String status, attachmentContent, attachmentName, consolidatedTestCases = "", fileName;
	Pattern pattern;
	Matcher matcher;

	public void createFilesForAttachmentsofTestCases(String testplan) {
		int downloadedTcCount = 0;
		status = Constants.invalidTestPlanID;
		try {
			for (String currentTestCycle : Initilizer.wsp.getTestCyclesFromTestPlan(testplan)) {
				status = Constants.webservicesError;
				for (String currentTestCase : Initilizer.wsp.getTestCasesFromTestCycle(currentTestCycle)) {
					for (String currentAttachment : Initilizer.wsp.getAttachmentsInTestCase(currentTestCase)) {
						attachmentName = Initilizer.wsp.getAttachmentFileName(currentAttachment);
						attachmentContent = Initilizer.wsp.getAttachmentContent(currentAttachment);
						if (attachmentName.contains(Constants.testsuiteFileNamePrefix)) {
							consolidatedTestCases = consolidatedTestCases + "\n\n" + attachmentContent;
						} else {
							saveFile(attachmentName, attachmentContent);
						}
					}
					downloadedTcCount = downloadedTcCount + 1;
					Initilizer.GUI.lblDownloadedTcValue.setText(String.valueOf(downloadedTcCount));
				}
			}
			if (consolidatedTestCases.equals("")) {
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
						+ Constants.noUpdateForTestSuite);
			} else {
				saveTestSuiteFile();
			}

		} catch (Exception e) {
			if (e.toString().contains("java.net.UnknownHostException")) {
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
						+ Constants.jamaConnectivityIssue);
				JOptionPane.showMessageDialog(Initilizer.GUI.txtAreaLogs, Constants.jamaConnectivityIssue);
			} else {
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime()) + status);
				JOptionPane.showMessageDialog(Initilizer.GUI.txtAreaLogs, status);
			}

		}

	}

	public void saveFile(String attachmentName, String attachmentContent) {
		fileName = "\\" + attachmentName + ".txt";
		pattern = Pattern.compile(Constants.receiptFileNamePrefix);
		matcher = pattern.matcher(attachmentName);
		if (matcher.find()) {
			Initilizer.fm.createTextFile(attachmentContent, Initilizer.GUI.txtReceipts.getText() + fileName);
			System.out.println("Attachment file " + attachmentName + ".txt was saved to the path: "
					+ Initilizer.GUI.txtReceipts.getText());
		} else {
			pattern = Pattern.compile(Constants.receiptRulesFileNamePrefix);
			matcher = pattern.matcher(attachmentName);
			if (matcher.find()) {
				Initilizer.fm.createTextFile(attachmentContent, Initilizer.GUI.txtReceiptRules.getText() + fileName);
				System.out.println("Attachment file " + attachmentName + ".txt was saved to the path: "
						+ Initilizer.GUI.txtReceipts.getText());
			} else {
				pattern = Pattern.compile(Constants.tLogFileNamePrefix);
				matcher = pattern.matcher(attachmentName);
				if (matcher.find()) {
					Initilizer.fm.createTextFile(attachmentContent, Initilizer.GUI.txtTLOG.getText() + fileName);
					System.out.println("Attachment file " + attachmentName + ".txt was saved to the path: "
							+ Initilizer.GUI.txtTLOG.getText());
				} else {
					pattern = Pattern.compile(Constants.tLogRulesFileNamePrefix,Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(attachmentName);
					if (matcher.find()) {
						Initilizer.fm.createTextFile(attachmentContent,
								Initilizer.GUI.txtTLOGRules.getText() + fileName);
						System.out.println("Attachment file " + attachmentName + ".txt was saved to the path: "
								+ Initilizer.GUI.txtTLOGRules.getText());
					}
				}
			}
		}
	}

	public void saveTestSuiteFile() {
		fileName = "\\TestSuite.xml";
		System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
				+ "Trying to create test suite in the path: " + Initilizer.GUI.txtTestSuite.getText() + fileName);

		try {
			status = Initilizer.fm.createXMLFile(Initilizer.GUI.txtTestSuite.getText() + fileName);
			if (status.equals(Constants.unableToDeleteTestSuite)) {
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime()) + status);
				JOptionPane.showMessageDialog(Initilizer.GUI.txtTestPlanID, status);
			} else {
				// This try catch block will add the test case tag to the test suite xml
				try {
					status = Initilizer.fm.addTestCasesTag(Initilizer.GUI.txtTestSuite.getText() + fileName);
					if (status.equals(Constants.testcaseTagAdded)) {
						System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime()) + status);
						try {
							System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
									+ Initilizer.fm.addTestCasesToTestSuite(this.consolidatedTestCases,
											Initilizer.GUI.txtTestSuite.getText() + fileName));
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(Initilizer.GUI.txtAreaLogs, Constants.unableToAddTestcase);
							System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
									+ Constants.unableToAddTestcase);
						}

					} else {
						JOptionPane.showMessageDialog(Initilizer.GUI.txtAreaLogs, Constants.testcaseTagNotAdded);
						System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
								+ Constants.testcaseTagNotAdded);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(Initilizer.GUI.txtAreaLogs, Constants.testcaseTagNotAdded);
					System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
							+ Constants.testcaseTagNotAdded);
				}
			}

		} catch (IOException e1) {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ Constants.unableToCreateTestSuite);
			JOptionPane.showMessageDialog(Initilizer.GUI.txtAreaLogs, Constants.unableToCreateTestSuite);
		}
		consolidatedTestCases = "";
	}

}
