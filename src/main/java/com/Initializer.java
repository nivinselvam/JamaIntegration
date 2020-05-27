package com;

import java.util.TreeSet;

import javax.swing.SwingUtilities;

public class Initializer {
	static WindowGUI GUI;
	static FileManipulator fm = new FileManipulator();
	static WebServiceProcessor wsp = new WebServiceProcessor();
	static AttachmentDownloader AD = new AttachmentDownloader();
	static ConfigurationValidator cv = new ConfigurationValidator();
	static PATSInitializer pi = new PATSInitializer();
	static TreeSet<TestCaseDetails> testcaseDetails = new TreeSet<TestCaseDetails>();
	static DirectoryMonitor dm = new DirectoryMonitor();
	static HTMLProcessor hp = new HTMLProcessor();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI = new WindowGUI();
				GUI.frmAttDriver.setVisible(true);
			}
		});
	}

}
