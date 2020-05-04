package com;

import java.io.File;
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
			for (String currentTestCycle : Initializer.wsp.getTestCyclesFromTestPlan(testplan)) {
				status = Constants.webservicesError;
				for (String currentTestCase : Initializer.wsp.getTestCasesFromTestCycle(currentTestCycle)) {
					for (String currentAttachment : Initializer.wsp.getAttachmentsInTestCase(currentTestCase)) {
						attachmentName = Initializer.wsp.getAttachmentFileName(currentAttachment);
						attachmentContent = Initializer.wsp.getAttachmentContent(currentAttachment);
						if (attachmentName.contains(Constants.testsuiteFileNamePrefix)) {
							consolidatedTestCases = consolidatedTestCases + "\n\n" + attachmentContent;
						} else {
							saveFile(attachmentName, attachmentContent);
						}
					}
					downloadedTcCount = downloadedTcCount + 1;
					Initializer.GUI.lblDownloadedTcValue.setText(String.valueOf(downloadedTcCount));
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
				JOptionPane.showMessageDialog(Initializer.GUI.txtAreaLogs, Constants.jamaConnectivityIssue);
			} else {
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime()) + status);
				JOptionPane.showMessageDialog(Initializer.GUI.txtAreaLogs, status);
			}

		}

	}

	public void saveFile(String attachmentName, String attachmentContent) {
		fileName = "\\" + attachmentName + ".txt";
		pattern = Pattern.compile(Constants.receiptFileNamePrefix);
		matcher = pattern.matcher(attachmentName);
		if (matcher.find()) {
			Initializer.fm.createTextFile(attachmentContent, Initializer.GUI.txtReceipts.getText() + fileName);
			System.out.println("Attachment file " + attachmentName + ".txt was saved to the path: "
					+ Initializer.GUI.txtReceipts.getText());
		} else {
			pattern = Pattern.compile(Constants.receiptRulesFileNamePrefix);
			matcher = pattern.matcher(attachmentName);
			if (matcher.find()) {
				Initializer.fm.createTextFile(attachmentContent, Initializer.GUI.txtReceiptRules.getText() + fileName);
				System.out.println("Attachment file " + attachmentName + ".txt was saved to the path: "
						+ Initializer.GUI.txtReceipts.getText());
			} else {
				pattern = Pattern.compile(Constants.tLogFileNamePrefix);
				matcher = pattern.matcher(attachmentName);
				if (matcher.find()) {
					Initializer.fm.createTextFile(attachmentContent, Initializer.GUI.txtTLOG.getText() + fileName);
					System.out.println("Attachment file " + attachmentName + ".txt was saved to the path: "
							+ Initializer.GUI.txtTLOG.getText());
				} else {
					pattern = Pattern.compile(Constants.tLogRulesFileNamePrefix,Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(attachmentName);
					if (matcher.find()) {
						Initializer.fm.createTextFile(attachmentContent,
								Initializer.GUI.txtTLOGRules.getText() + fileName);
						System.out.println("Attachment file " + attachmentName + ".txt was saved to the path: "
								+ Initializer.GUI.txtTLOGRules.getText());
					}
				}
			}
		}
	}

	public void saveTestSuiteFile() {
		fileName = "\\TestSuite.xml";
		System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
				+ "Trying to create test suite in the path: " + Initializer.GUI.txtTestSuite.getText() + fileName);

		try {
			status = Initializer.fm.createXMLFile(Initializer.GUI.txtTestSuite.getText() + fileName);
			if (status.equals(Constants.unableToDeleteTestSuite)) {
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime()) + status);
				JOptionPane.showMessageDialog(Initializer.GUI.txtTestPlanID, status);
			} else {
				// This try catch block will add the test case tag to the test suite xml
				try {
					status = Initializer.fm.addTestCasesTag(Initializer.GUI.txtTestSuite.getText() + fileName);
					if (status.equals(Constants.testcaseTagAdded)) {
						System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime()) + status);
						try {
							System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
									+ Initializer.fm.addTestCasesToTestSuite(this.consolidatedTestCases,
											Initializer.GUI.txtTestSuite.getText() + fileName));
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(Initializer.GUI.txtAreaLogs, Constants.unableToAddTestcase);
							System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
									+ Constants.unableToAddTestcase);
						}

					} else {
						JOptionPane.showMessageDialog(Initializer.GUI.txtAreaLogs, Constants.testcaseTagNotAdded);
						System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
								+ Constants.testcaseTagNotAdded);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(Initializer.GUI.txtAreaLogs, Constants.testcaseTagNotAdded);
					System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
							+ Constants.testcaseTagNotAdded);
				}
			}

		} catch (IOException e1) {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ Constants.unableToCreateTestSuite);
			JOptionPane.showMessageDialog(Initializer.GUI.txtAreaLogs, Constants.unableToCreateTestSuite);
		}
		consolidatedTestCases = "";
	}

}
