package com;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.text.BadLocationException;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JProgressBar;

public class WindowGUI {

	public JFrame frmAttDriver;
	public JTextArea txtAreaLogs;
	public JFormattedTextField txtTestPlanID;
	public JLabel lblDownloadedTcValue;
	public JLabel lblAvailableTcValue;
	String status;
	public JTextField txtReceiptRules;
	public JTextField txtTLOG;
	public JTextField txtTLOGRules;
	public JTextField txtReceipts;
	public JTextField txtTestSuite;
	public JTextField txtPATSinit;
	public JTextField txtResultReport;
	private JCheckBox chckbxPATSinit;
	private JCheckBox chckbxPATSfileDefaultName;
	private JCheckBox chckbxTestSuiteDefaultPath;
	private JCheckBox chckbxReceiptsDefaultPath;
	private JCheckBox chckbxReceiptRulesDefaultPath;
	private JCheckBox chckbxTLOGDefaultPath;
	private JCheckBox chckbxTLOGRulesDefaultPath;
	private JCheckBox chckbxPATSInitDefaultPath;
	private JTextField txtReportCheckDuration;
	private JCheckBox chckbxResultReport;
	private JButton btnInitiatePatsExecution;
	public JTextField txtPATSfileName;

	/**
	 * Create the application.
	 * 
	 */
	public WindowGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAttDriver = new JFrame();
		frmAttDriver.setTitle("ATT Driver");
		frmAttDriver.setBounds(100, 100, 570, 751);
		frmAttDriver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		GroupLayout groupLayout = new GroupLayout(frmAttDriver.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE).addGap(5)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE).addContainerGap()));

		JPanel panelMain = new JPanel();
		tabbedPane.addTab("Main", null, panelMain, null);
		panelMain.setLayout(null);

		JLabel lblTestPlanId = new JLabel("Test Plan ID");
		lblTestPlanId.setBounds(12, 62, 77, 16);
		panelMain.add(lblTestPlanId);
		lblTestPlanId.setFont(new Font("Tahoma", Font.BOLD, 13));

		// NumberFormat format = NumberFormat.getInstance();
		// NumberFormatter formatter = new NumberFormatter(format);
		// formatter.setValueClass(Integer.class);
		// formatter.setMinimum(0);
		// formatter.setMaximum(Integer.MAX_VALUE);
		// formatter.setAllowsInvalid(false);
		// // If you want the value to be committed on each keystroke instead of focus
		// lost
		// formatter.setCommitsOnValidEdit(true);
		txtTestPlanID = new JFormattedTextField();
		txtTestPlanID.setBounds(119, 59, 201, 22);
		panelMain.add(txtTestPlanID);

		JButton btnOk = new JButton("Download Attachments");
		btnOk.setBounds(332, 58, 186, 25);
		panelMain.add(btnOk);

		JLabel lblAvailableTcField = new JLabel("Available Test Cases:");
		lblAvailableTcField.setBounds(12, 110, 123, 16);
		panelMain.add(lblAvailableTcField);

		JLabel lblDownloadedTcField = new JLabel("Downloaded Testcases:");
		lblDownloadedTcField.setBounds(12, 162, 136, 16);
		panelMain.add(lblDownloadedTcField);

		lblDownloadedTcValue = new JLabel("");
		lblDownloadedTcValue.setBounds(160, 162, 28, 16);
		panelMain.add(lblDownloadedTcValue);

		lblAvailableTcValue = new JLabel("");
		lblAvailableTcValue.setBounds(147, 110, 28, 16);
		panelMain.add(lblAvailableTcValue);

		JProgressBar progressBarTestCaseDownload = new JProgressBar();
		progressBarTestCaseDownload.setBounds(12, 221, 506, 16);
		panelMain.add(progressBarTestCaseDownload);

		btnInitiatePatsExecution = new JButton("Initiate PATS Execution");
		btnInitiatePatsExecution.setEnabled(false);
		btnInitiatePatsExecution.setBounds(175, 306, 175, 25);
		panelMain.add(btnInitiatePatsExecution);

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblAvailableTcValue.setText("");
				lblDownloadedTcValue.setText("");
				printLog();
			}
		});

		JPanel paneConfiguration = new JPanel();
		tabbedPane.addTab("Configuration", null, paneConfiguration, null);
		paneConfiguration.setLayout(null);

		JLabel lblTestsuite = new JLabel("TestSuite");
		lblTestsuite.setBounds(13, 154, 77, 16);
		paneConfiguration.add(lblTestsuite);
		lblTestsuite.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtTestSuite = new JTextField();
		txtTestSuite.setText(Constants.defaultTestsuiteFilePath);
		txtTestSuite.setEnabled(false);
		txtTestSuite.setBounds(120, 151, 361, 22);
		paneConfiguration.add(txtTestSuite);
		txtTestSuite.setColumns(10);

		JLabel lblReceipts = new JLabel("Receipts");
		lblReceipts.setBounds(13, 225, 56, 16);
		paneConfiguration.add(lblReceipts);
		lblReceipts.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtReceipts = new JTextField();
		txtReceipts.setText(Constants.defaultReceiptFilesPath);
		txtReceipts.setEnabled(false);
		txtReceipts.setBounds(120, 222, 361, 22);
		paneConfiguration.add(txtReceipts);
		txtReceipts.setColumns(10);

		chckbxReceiptsDefaultPath = new JCheckBox("Set Default Path");

		chckbxReceiptsDefaultPath.setSelected(true);
		chckbxReceiptsDefaultPath.setBounds(120, 253, 121, 25);
		paneConfiguration.add(chckbxReceiptsDefaultPath);
		chckbxReceiptsDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel lblReceiptRules = new JLabel("Receipt Rules");
		lblReceiptRules.setBounds(13, 297, 95, 16);
		paneConfiguration.add(lblReceiptRules);
		lblReceiptRules.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtReceiptRules = new JTextField();
		txtReceiptRules.setText(Constants.defaultReceiptRulesFilesPath);
		txtReceiptRules.setEnabled(false);
		txtReceiptRules.setBounds(120, 291, 361, 22);
		paneConfiguration.add(txtReceiptRules);
		txtReceiptRules.setColumns(10);

		chckbxReceiptRulesDefaultPath = new JCheckBox("Set Default Path");
		chckbxReceiptRulesDefaultPath.setSelected(true);
		chckbxReceiptRulesDefaultPath.setBounds(120, 322, 121, 25);
		paneConfiguration.add(chckbxReceiptRulesDefaultPath);
		chckbxReceiptRulesDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel lblTlog = new JLabel("TLOG");
		lblTlog.setBounds(13, 361, 56, 16);
		paneConfiguration.add(lblTlog);
		lblTlog.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtTLOG = new JTextField();
		txtTLOG.setText(Constants.defaultTLogFilesPath);
		txtTLOG.setEnabled(false);
		txtTLOG.setBounds(120, 358, 361, 22);
		paneConfiguration.add(txtTLOG);
		txtTLOG.setColumns(10);

		chckbxTLOGDefaultPath = new JCheckBox("Set Default Path");
		chckbxTLOGDefaultPath.setSelected(true);
		chckbxTLOGDefaultPath.setBounds(120, 389, 121, 25);
		paneConfiguration.add(chckbxTLOGDefaultPath);
		chckbxTLOGDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel lblTlogRules = new JLabel("TLOG Rules");
		lblTlogRules.setBounds(12, 421, 77, 16);
		paneConfiguration.add(lblTlogRules);
		lblTlogRules.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtTLOGRules = new JTextField();
		txtTLOGRules.setText(Constants.defaultTLogRuleFilesPath);
		txtTLOGRules.setEnabled(false);
		txtTLOGRules.setBounds(119, 418, 362, 22);
		paneConfiguration.add(txtTLOGRules);
		txtTLOGRules.setColumns(10);

		chckbxTLOGRulesDefaultPath = new JCheckBox("Set Default Path");
		chckbxTLOGRulesDefaultPath.setSelected(true);
		chckbxTLOGRulesDefaultPath.setBounds(120, 449, 121, 25);
		paneConfiguration.add(chckbxTLOGRulesDefaultPath);
		chckbxTLOGRulesDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));

		chckbxTestSuiteDefaultPath = new JCheckBox("Set Default Path");
		chckbxTestSuiteDefaultPath.setSelected(true);
		chckbxTestSuiteDefaultPath.setBounds(120, 182, 121, 25);
		paneConfiguration.add(chckbxTestSuiteDefaultPath);
		chckbxTestSuiteDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel lblNewLabel = new JLabel("PATS init file");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(13, 13, 80, 16);
		paneConfiguration.add(lblNewLabel);

		txtPATSinit = new JTextField();
		txtPATSinit.setText(Constants.defaultPATSinitFilePath);
		txtPATSinit.setEnabled(false);
		txtPATSinit.setBounds(120, 81, 361, 22);
		paneConfiguration.add(txtPATSinit);
		txtPATSinit.setColumns(10);

		chckbxPATSInitDefaultPath = new JCheckBox("Set Default Path");
		chckbxPATSInitDefaultPath.setSelected(true);
		chckbxPATSInitDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxPATSInitDefaultPath.setBounds(120, 112, 121, 25);
		paneConfiguration.add(chckbxPATSInitDefaultPath);

		chckbxPATSinit = new JCheckBox("Initiate PATS automaticatically");
		chckbxPATSinit.setSelected(true);
		chckbxPATSinit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxPATSinit.setBounds(287, 112, 194, 25);
		paneConfiguration.add(chckbxPATSinit);

		JButton btnValidatePaths = new JButton("Validate path accessibility");
		btnValidatePaths.setBounds(12, 552, 468, 25);
		paneConfiguration.add(btnValidatePaths);

		JLabel lblResultReportCheck = new JLabel("Result Report Check:");
		lblResultReportCheck.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblResultReportCheck.setBounds(12, 590, 148, 16);
		paneConfiguration.add(lblResultReportCheck);

		JLabel lblEvery = new JLabel("Every");
		lblEvery.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEvery.setBounds(172, 590, 39, 16);
		paneConfiguration.add(lblEvery);

		txtReportCheckDuration = new JTextField();
		txtReportCheckDuration.setText("30");
		txtReportCheckDuration.setBounds(212, 587, 56, 22);
		paneConfiguration.add(txtReportCheckDuration);
		txtReportCheckDuration.setColumns(10);

		JLabel lblMinutes = new JLabel("minutes");
		lblMinutes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMinutes.setBounds(280, 590, 45, 16);
		paneConfiguration.add(lblMinutes);

		JLabel lblNewLabel_1 = new JLabel("Result Report");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(13, 488, 95, 16);
		paneConfiguration.add(lblNewLabel_1);

		txtResultReport = new JTextField();
		txtResultReport.setText(Constants.defaultResultReportPath);
		txtResultReport.setEnabled(false);
		txtResultReport.setBounds(120, 483, 361, 22);
		paneConfiguration.add(txtResultReport);
		txtResultReport.setColumns(10);

		chckbxResultReport = new JCheckBox("Set Default Path");
		chckbxResultReport.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxResultReport.setSelected(true);
		chckbxResultReport.setBounds(120, 514, 121, 25);
		paneConfiguration.add(chckbxResultReport);
		
		txtPATSfileName = new JTextField();
		txtPATSfileName.setEnabled(false);
		txtPATSfileName.setText(Constants.defaultPATSinitFileName);
		txtPATSfileName.setBounds(120, 46, 234, 22);
		paneConfiguration.add(txtPATSfileName);
		txtPATSfileName.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblName.setBounds(52, 49, 38, 16);
		paneConfiguration.add(lblName);
		
		JLabel lblPath = new JLabel("Path");
		lblPath.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPath.setBounds(55, 84, 35, 16);
		paneConfiguration.add(lblPath);
		
		chckbxPATSfileDefaultName = new JCheckBox("Set Default Name");
		chckbxPATSfileDefaultName.setSelected(true);
		chckbxPATSfileDefaultName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxPATSfileDefaultName.setBounds(356, 45, 125, 25);
		paneConfiguration.add(chckbxPATSfileDefaultName);
		
		chckbxPATSfileDefaultName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxPATSfileDefaultName.isSelected()) {
					txtPATSfileName.setEnabled(false);
					txtPATSfileName.setText(Constants.defaultPATSinitFileName);
				}else {
					txtPATSfileName.setEnabled(true);
				}
			}
		});

		chckbxTestSuiteDefaultPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxTestSuiteDefaultPath.isSelected()) {
					txtTestSuite.setEnabled(false);
					txtTestSuite.setText(Constants.defaultTestsuiteFilePath);
				} else {
					txtTestSuite.setEnabled(true);
				}
			}
		});

		chckbxReceiptsDefaultPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxReceiptsDefaultPath.isSelected()) {
					txtReceipts.setEnabled(false);
					txtReceipts.setText(Constants.defaultReceiptFilesPath);
				} else {
					txtReceipts.setEnabled(true);
				}
			}
		});

		chckbxReceiptRulesDefaultPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxReceiptRulesDefaultPath.isSelected()) {
					txtReceiptRules.setEnabled(false);
					txtReceiptRules.setText(Constants.defaultReceiptRulesFilesPath);
				} else {
					txtReceiptRules.setEnabled(true);
				}
			}
		});

		chckbxTLOGDefaultPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxTLOGDefaultPath.isSelected()) {
					txtTLOG.setEnabled(false);
					txtTLOG.setText(Constants.defaultTLogFilesPath);
				} else {
					txtTLOG.setEnabled(true);
				}
			}
		});

		chckbxTLOGRulesDefaultPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxTLOGRulesDefaultPath.isSelected()) {
					txtTLOGRules.setEnabled(false);
					txtTLOGRules.setText(Constants.defaultTLogRuleFilesPath);
				} else {
					txtTLOGRules.setEnabled(true);
				}
			}
		});

		chckbxPATSInitDefaultPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxPATSInitDefaultPath.isSelected()) {
					txtPATSinit.setEnabled(false);
					txtPATSinit.setText(Constants.defaultPATSinitFilePath);
				} else {
					txtPATSinit.setEnabled(true);
				}
			}
		});

		chckbxResultReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxResultReport.isSelected()) {
					txtResultReport.setEnabled(false);
					txtResultReport.setText(Constants.defaultResultReportPath);
				} else {
					txtResultReport.setEnabled(true);
				}
			}
		});
		
		chckbxPATSinit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxPATSinit.isSelected()) {
					btnInitiatePatsExecution.setEnabled(false);
				}else {
					btnInitiatePatsExecution.setEnabled(true);
				}
			}
		});
		
		btnInitiatePatsExecution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Initializer.pi.initiatePATS();
			}
		});
		
		btnValidatePaths.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Initializer.cv.validatePaths()) {
					System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
							+ "Configured paths are valid");
					JOptionPane.showMessageDialog(txtAreaLogs, "Configured paths are valid");
				}
			}
		});

		JPanel panelLogs = new JPanel();
		tabbedPane.addTab("Logs", null, panelLogs, null);
		panelLogs.setLayout(null);
		JScrollPane spLogs = new JScrollPane();
		spLogs.setBounds(12, 38, 506, 535);
		panelLogs.add(spLogs);

		txtAreaLogs = new JTextArea();
		spLogs.setViewportView(txtAreaLogs);
		txtAreaLogs.setEditable(false);
		txtAreaLogs.setLineWrap(true);
		PrintStream printStream = new PrintStream(new CustomOutputStream(txtAreaLogs));

		JLabel lblRuntimeLogs = new JLabel("Runtime logs:");
		lblRuntimeLogs.setBounds(12, 13, 79, 16);
		panelLogs.add(lblRuntimeLogs);

		JButton btnClearLogs = new JButton("Clear Logs");
		btnClearLogs.setBounds(413, 9, 105, 25);
		panelLogs.add(btnClearLogs);

		btnClearLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtAreaLogs.getDocument().remove(0, txtAreaLogs.getDocument().getLength());
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				;
			}
		});
		// PrintStream standardout = System.out;
		System.setOut(printStream);
		System.setErr(printStream);

		frmAttDriver.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		frmAttDriver.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmSaveLogs = new JMenuItem("Save Logs");
		mnFile.add(mntmSaveLogs);

		JMenuItem mntmExit = new JMenuItem("Exit");

		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);

		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
	}

	/**
	 * Prints log statements in the text area
	 */
	private void printLog() {
		Thread thread = new Thread(new Runnable() {
			// @Override
			public void run() {
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
								+ Constants.startProcessText);
				if (txtTestPlanID.getText().equals("")) {
					System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
							+ Constants.testPlanIDMandate);
					JOptionPane.showMessageDialog(txtTestPlanID, Constants.testPlanIDMandate);
				} else {
					if(Initializer.cv.validatePaths()) {
						Initializer.AD.createFilesForAttachmentsofTestCases(txtTestPlanID.getText());
					}					
				}
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
								+  Constants.endOfProcessText);
			}
		});
		thread.start();
	}
}
