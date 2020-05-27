package com;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.text.BadLocationException;

import org.springframework.beans.factory.config.CustomEditorConfigurer;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class WindowGUI {

	public JFrame frmAttDriver;
	public JTextArea txtAreaLogs;
	public JFormattedTextField txtTestPlanID;
	public JLabel lblDownloadedTcValue;
	public JLabel lblAvailableTcValue;
	public String status;
	public JTextField txtReceiptRules;
	public JTextField txtTLOG;
	public JTextField txtTLOGRules;
	public JTextField txtReceipts;
	public JTextField txtTestSuite;
	public JTextField txtPATSinit;
	public JTextField txtResultReport;
	public JComboBox cbxBuildMachine;
	public JTextField txtBaseVersion;
	public JCheckBox chckbxPATSinit;
	private JCheckBox chckbxPATSfileDefaultName;
	private JCheckBox chckbxTestSuiteDefaultPath;
	private JCheckBox chckbxReceiptsDefaultPath;
	private JCheckBox chckbxReceiptRulesDefaultPath;
	private JCheckBox chckbxTLOGDefaultPath;
	private JCheckBox chckbxTLOGRulesDefaultPath;
	private JCheckBox chckbxPATSInitDefaultPath;
	private JCheckBox chckbxResultReport;
	public JCheckBox chckbxAutoTriggerBuild;
	private JButton btnInitiatePatsExecution;
	private JButton btnTriggerBuild;
	private JRadioButton rdbtnBaseBuild;
	private JRadioButton rdbtnCrBuild;
	private JButton btnDownloadAttachments;
	private JComboBox cbxFEP;
	public JTextField txtPATSfileName;
	private JTextField txtCRnumber;
	private JComboBox cbxAutomationMachines;
	private InetAddress systemDetails;
	private CustomComboCheckBox cbxNotificationList;

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
		frmAttDriver.setBounds(100, 100, 566, 751);
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

		btnDownloadAttachments = new JButton("Download Attachments");
		btnDownloadAttachments.setBounds(332, 58, 186, 25);
		panelMain.add(btnDownloadAttachments);

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

		btnDownloadAttachments.addActionListener(new ActionListener() {
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
		lblTestsuite.setBounds(14, 175, 77, 16);
		paneConfiguration.add(lblTestsuite);
		lblTestsuite.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtTestSuite = new JTextField();
		txtTestSuite.setText(Constants.defaultTestsuiteFilePath);
		txtTestSuite.setEnabled(false);
		txtTestSuite.setBounds(121, 172, 361, 22);
		paneConfiguration.add(txtTestSuite);
		txtTestSuite.setColumns(10);

		JLabel lblReceipts = new JLabel("Receipts");
		lblReceipts.setBounds(14, 246, 56, 16);
		paneConfiguration.add(lblReceipts);
		lblReceipts.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtReceipts = new JTextField();
		txtReceipts.setText(Constants.defaultReceiptFilesPath);
		txtReceipts.setEnabled(false);
		txtReceipts.setBounds(121, 243, 361, 22);
		paneConfiguration.add(txtReceipts);
		txtReceipts.setColumns(10);

		chckbxReceiptsDefaultPath = new JCheckBox("Set Default Path");

		chckbxReceiptsDefaultPath.setSelected(true);
		chckbxReceiptsDefaultPath.setBounds(121, 274, 121, 25);
		paneConfiguration.add(chckbxReceiptsDefaultPath);
		chckbxReceiptsDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel lblReceiptRules = new JLabel("Receipt Rules");
		lblReceiptRules.setBounds(14, 318, 95, 16);
		paneConfiguration.add(lblReceiptRules);
		lblReceiptRules.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtReceiptRules = new JTextField();
		txtReceiptRules.setText(Constants.defaultReceiptRulesFilesPath);
		txtReceiptRules.setEnabled(false);
		txtReceiptRules.setBounds(121, 312, 361, 22);
		paneConfiguration.add(txtReceiptRules);
		txtReceiptRules.setColumns(10);

		chckbxReceiptRulesDefaultPath = new JCheckBox("Set Default Path");
		chckbxReceiptRulesDefaultPath.setSelected(true);
		chckbxReceiptRulesDefaultPath.setBounds(121, 343, 121, 25);
		paneConfiguration.add(chckbxReceiptRulesDefaultPath);
		chckbxReceiptRulesDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel lblTlog = new JLabel("TLOG");
		lblTlog.setBounds(14, 382, 56, 16);
		paneConfiguration.add(lblTlog);
		lblTlog.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtTLOG = new JTextField();
		txtTLOG.setText(Constants.defaultTLogFilesPath);
		txtTLOG.setEnabled(false);
		txtTLOG.setBounds(121, 379, 361, 22);
		paneConfiguration.add(txtTLOG);
		txtTLOG.setColumns(10);

		chckbxTLOGDefaultPath = new JCheckBox("Set Default Path");
		chckbxTLOGDefaultPath.setSelected(true);
		chckbxTLOGDefaultPath.setBounds(121, 410, 121, 25);
		paneConfiguration.add(chckbxTLOGDefaultPath);
		chckbxTLOGDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel lblTlogRules = new JLabel("TLOG Rules");
		lblTlogRules.setBounds(13, 442, 77, 16);
		paneConfiguration.add(lblTlogRules);
		lblTlogRules.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtTLOGRules = new JTextField();
		txtTLOGRules.setText(Constants.defaultTLogRuleFilesPath);
		txtTLOGRules.setEnabled(false);
		txtTLOGRules.setBounds(120, 439, 362, 22);
		paneConfiguration.add(txtTLOGRules);
		txtTLOGRules.setColumns(10);

		chckbxTLOGRulesDefaultPath = new JCheckBox("Set Default Path");
		chckbxTLOGRulesDefaultPath.setSelected(true);
		chckbxTLOGRulesDefaultPath.setBounds(121, 470, 121, 25);
		paneConfiguration.add(chckbxTLOGRulesDefaultPath);
		chckbxTLOGRulesDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));

		chckbxTestSuiteDefaultPath = new JCheckBox("Set Default Path");
		chckbxTestSuiteDefaultPath.setSelected(true);
		chckbxTestSuiteDefaultPath.setBounds(121, 203, 121, 25);
		paneConfiguration.add(chckbxTestSuiteDefaultPath);
		chckbxTestSuiteDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel lblNewLabel = new JLabel("PATS init file");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(14, 34, 80, 16);
		paneConfiguration.add(lblNewLabel);

		txtPATSinit = new JTextField();
		txtPATSinit.setText(Constants.defaultPATSinitFilePath);
		txtPATSinit.setEnabled(false);
		txtPATSinit.setBounds(121, 102, 361, 22);
		paneConfiguration.add(txtPATSinit);
		txtPATSinit.setColumns(10);

		chckbxPATSInitDefaultPath = new JCheckBox("Set Default Path");
		chckbxPATSInitDefaultPath.setSelected(true);
		chckbxPATSInitDefaultPath.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxPATSInitDefaultPath.setBounds(121, 133, 121, 25);
		paneConfiguration.add(chckbxPATSInitDefaultPath);

		chckbxPATSinit = new JCheckBox("Initiate PATS automaticatically");
		chckbxPATSinit.setSelected(true);
		chckbxPATSinit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxPATSinit.setBounds(288, 133, 194, 25);
		paneConfiguration.add(chckbxPATSinit);

		JButton btnValidatePaths = new JButton("Validate path accessibility");
		btnValidatePaths.setBounds(13, 573, 468, 25);
		paneConfiguration.add(btnValidatePaths);

		JLabel lblNewLabel_1 = new JLabel("Result Report");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(14, 509, 95, 16);
		paneConfiguration.add(lblNewLabel_1);

		txtResultReport = new JTextField();
		txtResultReport.setText(Constants.defaultResultReportPath);
		txtResultReport.setEnabled(false);
		txtResultReport.setBounds(121, 504, 361, 22);
		paneConfiguration.add(txtResultReport);
		txtResultReport.setColumns(10);

		chckbxResultReport = new JCheckBox("Set Default Path");
		chckbxResultReport.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxResultReport.setSelected(true);
		chckbxResultReport.setBounds(120, 535, 121, 25);
		paneConfiguration.add(chckbxResultReport);

		txtPATSfileName = new JTextField();
		txtPATSfileName.setEnabled(false);
		txtPATSfileName.setText(Constants.defaultPATSinitFileName);
		txtPATSfileName.setBounds(121, 67, 234, 22);
		paneConfiguration.add(txtPATSfileName);
		txtPATSfileName.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblName.setBounds(53, 70, 38, 16);
		paneConfiguration.add(lblName);

		JLabel lblPath = new JLabel("Path");
		lblPath.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPath.setBounds(56, 105, 35, 16);
		paneConfiguration.add(lblPath);

		chckbxPATSfileDefaultName = new JCheckBox("Set Default Name");
		chckbxPATSfileDefaultName.setSelected(true);
		chckbxPATSfileDefaultName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxPATSfileDefaultName.setBounds(357, 66, 125, 25);
		paneConfiguration.add(chckbxPATSfileDefaultName);

		chckbxPATSfileDefaultName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxPATSfileDefaultName.isSelected()) {
					txtPATSfileName.setEnabled(false);
					txtPATSfileName.setText(Constants.defaultPATSinitFileName);
				} else {
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
				if (chckbxPATSinit.isSelected()) {
					btnInitiatePatsExecution.setEnabled(false);
				} else {
					btnInitiatePatsExecution.setEnabled(true);
				}
			}
		});

		btnInitiatePatsExecution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Initializer.cv.validatePATSfile()) {
					Initializer.pi.initiatePATS();
				}
			}
		});

		btnValidatePaths.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Initializer.cv.validatePaths()) {
					System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
							+ "Configured paths are valid");
					JOptionPane.showMessageDialog(txtAreaLogs, "Configured paths are valid");
				}
			}
		});

		JPanel pnTriggerBuild = new JPanel();
		tabbedPane.addTab("Trigger build", null, pnTriggerBuild, null);

		JLabel lblBuildMachine = new JLabel("Build Machine");
		lblBuildMachine.setFont(new Font("Tahoma", Font.BOLD, 13));

		String localMachineName = "";
		try {
			systemDetails = InetAddress.getLocalHost();
			localMachineName = systemDetails.getHostName();
		} catch (UnknownHostException e2) {
			e2.printStackTrace();
		}

		String[] buildMachines = { "blr2wvcoverity1", "blr2wvcoverity2", "BLR2BUILDPC6", "BLR2BUILDPC7",
				localMachineName };
		cbxBuildMachine = new JComboBox(buildMachines);

		JLabel lblBuildMachineName = new JLabel("Name");
		lblBuildMachineName.setFont(new Font("Tahoma", Font.PLAIN, 13));

		JLabel lblBuildDetails = new JLabel("Build Details");
		lblBuildDetails.setFont(new Font("Tahoma", Font.BOLD, 13));

		JButton btnBuildsRunningInfo = new JButton("Builds running info");
		btnBuildsRunningInfo.setEnabled(false);

		JLabel lblFep = new JLabel("FEP");
		lblFep.setFont(new Font("Tahoma", Font.PLAIN, 13));

		String[] FEPs = { "base", "bp", "bp_buypass", "buypass", "chevron", "citgo", "cop", "exxon", "fcb",
				"generic_buypass", "generic_hps", "hps", "marathon", "nbs", "nts", "shell", "sinclair", "sunoco",
				"tesoro", "testFep", "valero", "verifone_eps", "worldpay" };
		cbxFEP = new JComboBox(FEPs);

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.BOLD, 13));

		rdbtnBaseBuild = new JRadioButton("Base build");

		rdbtnBaseBuild.setSelected(true);
		rdbtnCrBuild = new JRadioButton("CR build");
		rdbtnCrBuild.setEnabled(false);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnCrBuild);
		bg.add(rdbtnBaseBuild);

		JLabel lblVersion = new JLabel("Version");
		lblVersion.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtBaseVersion = new JTextField();
		txtBaseVersion.setColumns(10);

		JLabel lblCrNumber = new JLabel("CR Number");
		lblCrNumber.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtCRnumber = new JTextField();
		txtCRnumber.setEnabled(false);
		txtCRnumber.setColumns(10);

		btnTriggerBuild = new JButton("Trigger Build Manually");

		btnTriggerBuild.setEnabled(false);

		chckbxAutoTriggerBuild = new JCheckBox("Trigger Build Automatically as part of ATT Driver");

		chckbxAutoTriggerBuild.setSelected(true);

		JLabel lblAutomationMachine = new JLabel("Automation Machine");
		lblAutomationMachine.setFont(new Font("Tahoma", Font.BOLD, 13));

		JLabel lblName_1 = new JLabel("Name");
		lblName_1.setFont(new Font("Tahoma", Font.PLAIN, 13));

		String[] automationMacines = { "BLR2PETROAUTO01" };
		cbxAutomationMachines = new JComboBox(automationMacines);

		JLabel lblNotificationList = new JLabel("Notification List");
		lblNotificationList.setFont(new Font("Tahoma", Font.BOLD, 13));

		JLabel lblMailId = new JLabel("Mail id");
		lblMailId.setFont(new Font("Tahoma", Font.PLAIN, 13));

		Vector vector = new Vector();
		vector.add(new JCheckBox("nivins1", false));
		vector.add(new JCheckBox("rania1", false));
		vector.add(new JCheckBox("jenishp1", false));
		vector.add(new JCheckBox("anbarasang1", false));
		vector.add(new JCheckBox("petroauto", false));
		// JComboBox cbxNotificationList = new JComboBox();
		cbxNotificationList = new CustomComboCheckBox(vector);

		JLabel lblverifonecom = new JLabel("@verifone.com");
		GroupLayout gl_pnTriggerBuild = new GroupLayout(pnTriggerBuild);
		gl_pnTriggerBuild.setHorizontalGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnTriggerBuild.createSequentialGroup().addContainerGap().addGroup(gl_pnTriggerBuild
						.createParallelGroup(Alignment.LEADING).addComponent(lblBuildMachine)
						.addComponent(lblAutomationMachine).addComponent(lblBuildDetails)
						.addGroup(gl_pnTriggerBuild.createSequentialGroup().addComponent(lblFep).addGap(58)
								.addGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_pnTriggerBuild.createSequentialGroup().addComponent(rdbtnBaseBuild)
												.addGap(38).addComponent(rdbtnCrBuild))
										.addComponent(cbxFEP, GroupLayout.PREFERRED_SIZE, 423,
												GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblType)
						.addGroup(gl_pnTriggerBuild.createSequentialGroup().addComponent(lblVersion).addGap(30)
								.addComponent(txtBaseVersion, 423, 423, 423))
						.addGroup(gl_pnTriggerBuild.createSequentialGroup().addComponent(lblCrNumber).addGap(12)
								.addComponent(txtCRnumber, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
						.addGroup(gl_pnTriggerBuild.createSequentialGroup().addComponent(chckbxAutoTriggerBuild)
								.addPreferredGap(ComponentPlacement.RELATED, 36,
										Short.MAX_VALUE)
								.addComponent(btnTriggerBuild))
						.addComponent(lblNotificationList)
						.addGroup(gl_pnTriggerBuild.createSequentialGroup().addGroup(gl_pnTriggerBuild
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnTriggerBuild.createSequentialGroup().addComponent(lblMailId).addGap(42)
										.addComponent(cbxNotificationList, GroupLayout.PREFERRED_SIZE, 265,
												GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(gl_pnTriggerBuild.createSequentialGroup().addComponent(lblName_1).addGap(46)
										.addComponent(cbxAutomationMachines, GroupLayout.PREFERRED_SIZE, 266,
												GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(gl_pnTriggerBuild.createSequentialGroup().addComponent(lblBuildMachineName)
										.addGap(48).addComponent(cbxBuildMachine, GroupLayout.PREFERRED_SIZE, 264,
												GroupLayout.PREFERRED_SIZE)))
								.addGap(18)
								.addGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.LEADING)
										.addComponent(lblverifonecom).addComponent(btnBuildsRunningInfo))))
						.addContainerGap()));
		gl_pnTriggerBuild.setVerticalGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnTriggerBuild.createSequentialGroup().addContainerGap().addComponent(lblBuildMachine)
						.addGap(16)
						.addGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBuildMachineName)
								.addComponent(cbxBuildMachine, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnBuildsRunningInfo))
						.addGap(18).addComponent(lblAutomationMachine).addGap(18)
						.addGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.BASELINE).addComponent(lblName_1)
								.addComponent(cbxAutomationMachines, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18).addComponent(lblNotificationList).addGap(18)
						.addGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.BASELINE).addComponent(lblMailId)
								.addComponent(cbxNotificationList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblverifonecom))
						.addGap(23).addComponent(lblBuildDetails).addGap(18)
						.addGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.BASELINE).addComponent(lblFep)
								.addComponent(cbxFEP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.BASELINE).addComponent(lblType)
								.addComponent(rdbtnBaseBuild).addComponent(rdbtnCrBuild))
						.addGap(18)
						.addGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.BASELINE).addComponent(lblVersion)
								.addComponent(txtBaseVersion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.BASELINE).addComponent(lblCrNumber)
								.addComponent(txtCRnumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(27)
						.addGroup(gl_pnTriggerBuild.createParallelGroup(Alignment.BASELINE)
								.addComponent(chckbxAutoTriggerBuild).addComponent(btnTriggerBuild))
						.addContainerGap(150, Short.MAX_VALUE)));
		pnTriggerBuild.setLayout(gl_pnTriggerBuild);

		JPanel panelLogs = new JPanel();
		tabbedPane.addTab("Logs", null, panelLogs, null);
		JScrollPane spLogs = new JScrollPane();

		JLabel lblRuntimeLogs = new JLabel("Runtime logs:");

		JButton btnClearLogs = new JButton("Clear Logs");
		GroupLayout gl_panelLogs = new GroupLayout(panelLogs);
		gl_panelLogs.setHorizontalGroup(gl_panelLogs.createParallelGroup(Alignment.LEADING).addGroup(gl_panelLogs
				.createSequentialGroup().addGap(12)
				.addGroup(gl_panelLogs.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelLogs.createSequentialGroup().addComponent(lblRuntimeLogs)
								.addPreferredGap(ComponentPlacement.RELATED, 239, Short.MAX_VALUE).addComponent(
										btnClearLogs, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
						.addComponent(spLogs, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
				.addContainerGap()));
		gl_panelLogs.setVerticalGroup(gl_panelLogs.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLogs.createSequentialGroup().addGap(9)
						.addGroup(gl_panelLogs.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelLogs.createSequentialGroup().addGap(4).addComponent(lblRuntimeLogs))
								.addGroup(gl_panelLogs.createSequentialGroup().addGap(1).addComponent(btnClearLogs)))
						.addGap(9).addComponent(spLogs, GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
						.addContainerGap()));

		txtAreaLogs = new JTextArea();
		spLogs.setViewportView(txtAreaLogs);
		txtAreaLogs.setEditable(false);
		txtAreaLogs.setLineWrap(true);
		PrintStream printStream = new PrintStream(new CustomOutputStream(txtAreaLogs));
		panelLogs.setLayout(gl_panelLogs);

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

		chckbxAutoTriggerBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxAutoTriggerBuild.isSelected()) {
					btnTriggerBuild.setEnabled(false);
				} else {
					btnTriggerBuild.setEnabled(true);
				}
			}
		});

		rdbtnCrBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnCrBuild.isSelected()) {
					txtCRnumber.setEnabled(true);
				}
			}
		});

		rdbtnBaseBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnBaseBuild.isSelected()) {
					txtCRnumber.setEnabled(false);
				}
			}
		});

		btnTriggerBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Initializer.cv.validateBuildMachinePath()) {
					triggerBuild();
				}
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
				btnDownloadAttachments.setEnabled(false);
				System.out.println(
						Constants.logsDateFormat.format(Calendar.getInstance().getTime()) + Constants.startProcessText);
				if (txtTestPlanID.getText().equals("")) {
					System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
							+ Constants.testPlanIDMandate);
					JOptionPane.showMessageDialog(txtTestPlanID, Constants.testPlanIDMandate);
				} else {
					if (Initializer.cv.validatePaths()) {
						if (Initializer.AD.createFilesForAttachmentsofTestCases(txtTestPlanID.getText())) {
							Initializer.pi.initiatePATS();
							if (chckbxAutoTriggerBuild.isSelected()) {
								triggerBuild();
							}
						} else {
							System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
									+ Constants.unableToInitiatePATS);
						}
					}
				}
				Initializer.dm.monitorDirectory();
				for (TestCaseDetails tcd : Initializer.testcaseDetails) {
					System.out.println(tcd.getTestcaseID() + "-" + tcd.getTestcaseDocumentKey() + "-"
							+ tcd.getTestcaseStatus() + "" + tcd.getTestcaseJSON());
				}
				System.out.println(
						Constants.logsDateFormat.format(Calendar.getInstance().getTime()) + Constants.endOfProcessText);
				btnDownloadAttachments.setEnabled(true);
			}
		});
		thread.start();
	}

	//
	private void triggerBuild() {
		String buildPath, fepName, notificationList = "";
		if (Initializer.GUI.cbxFEP.getSelectedItem().toString().equals("base")) {
			fepName = "";
		} else {
			fepName = Initializer.GUI.cbxFEP.getSelectedItem().toString();
		}

		buildPath = "\\\\" + Initializer.GUI.cbxBuildMachine.getSelectedItem().toString() + "\\queue\\master"
				+ Initializer.GUI.txtBaseVersion.getText();

		for (String currentString : cbxNotificationList.getSelectedItems()) {
			notificationList = notificationList + " " + currentString;
		}

		String content = "#customer-list buypass\n#notify " + notificationList + "\n#component \\isdDist\\sc" + fepName
				+ "\n#destinations \\\\" + Initializer.GUI.cbxAutomationMachines.getSelectedItem().toString()
				+ "/CICD/Builds";
		System.out.println(buildPath);
		System.out.println(content);
		Initializer.fm.createTextFile(content, buildPath);
	}
}
