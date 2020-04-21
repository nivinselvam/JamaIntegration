package com;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.NumberFormatter;

import org.apache.log4j.Logger;

public class WindowGUI extends Thread {

	public JFrame frmAttDriver;
	public JTextArea txtAreaLogs;
	public JFormattedTextField txtTestPlanID;
	public Logger logger = Logger.getLogger(WindowGUI.class);
	FileManipulator fm = new FileManipulator();
	WebServiceProcessor wsp = new WebServiceProcessor();
	String status;

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public WindowGUI() {

	}

	@Override
	public void run() {
		initialize();
		frmAttDriver.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAttDriver = new JFrame();
		frmAttDriver.setTitle("ATT Driver");
		frmAttDriver.setBounds(100, 100, 406, 517);
		frmAttDriver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblTestPlanId = new JLabel("Please enter the test plan ID :");
		lblTestPlanId.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblRuntimeLogs = new JLabel("Runtime logs:");

		JButton btnOk = new JButton("Ok");
		JScrollPane spLogs = new JScrollPane();

//		NumberFormat format = NumberFormat.getInstance();
//		NumberFormatter formatter = new NumberFormatter(format);
//		formatter.setValueClass(Integer.class);
//		formatter.setMinimum(0);
//		formatter.setMaximum(Integer.MAX_VALUE);
//		formatter.setAllowsInvalid(false);
//		// If you want the value to be committed on each keystroke instead of focus lost
//		formatter.setCommitsOnValidEdit(true);
		txtTestPlanID = new JFormattedTextField();

		txtAreaLogs = new JTextArea();
		txtAreaLogs.setEditable(false);
		txtAreaLogs.setLineWrap(true);

		JButton btnClearLogs = new JButton("Clear Logs");

		GroupLayout groupLayout = new GroupLayout(frmAttDriver.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(14)
										.addComponent(spLogs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtAreaLogs, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup().addGap(12).addComponent(lblTestPlanId)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtTestPlanID, GroupLayout.PREFERRED_SIZE, 130,
												GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup().addGap(12).addComponent(lblRuntimeLogs))
								.addGroup(groupLayout.createSequentialGroup().addGap(60)
										.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 106,
												GroupLayout.PREFERRED_SIZE)
										.addGap(56).addComponent(btnClearLogs, GroupLayout.PREFERRED_SIZE, 105,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(40)
				.addGroup(groupLayout
						.createParallelGroup(Alignment.BASELINE).addComponent(lblTestPlanId).addComponent(txtTestPlanID,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(22)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnOk)
						.addComponent(btnClearLogs, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(lblRuntimeLogs).addGap(9)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(spLogs, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup().addGap(4).addComponent(txtAreaLogs,
								GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)))
				.addContainerGap()));

		frmAttDriver.getContentPane().setLayout(groupLayout);

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath = "", testCasesResult = "";
				txtAreaLogs.append(Constants.startProcessText+"\n");
				if (txtTestPlanID.getText().equals("")) {
					txtAreaLogs.append(Constants.testPlanIDMandate);
				} else {

					testCasesResult = wsp.getTestcaseFromTestPlan(txtTestPlanID.getText());
					if (testCasesResult.equals(Constants.invalidTestPlanID)
							|| testCasesResult.equals(Constants.jamaConnectivityIssue)) {
						txtAreaLogs.append(testCasesResult);
					} else {
						// Below try catch block will create test suite xml file
						try {
							InetAddress addr = InetAddress.getLocalHost();
							filePath = "C:\\Users\\" + addr.getHostName().substring(5).toLowerCase()
									+ "\\OneDrive - Verifone\\Desktop\\TestSuite.xml";
							// logger.info("System name is " +
							// addr.getHostName().substring(5).toLowerCase());
							// txtAreaLogs.append("\nSystem name is " +
							// addr.getHostName().substring(5).toLowerCase());
							status = fm.createXMLFile(filePath);
							if (status.equals(Constants.unableToDeleteTestSuite)) {
								txtAreaLogs.append(status);
							} else {
								txtAreaLogs.append(status);
								// This try catch block will add the test case tag to the test suite xml
								try {
									status = fm.addTestCasesTag(filePath);
									if (status.equals(Constants.testcaseTagAdded)) {
										txtAreaLogs.append(status);
										try {
											txtAreaLogs.append(fm.addTestCasesToTestSuite(testCasesResult, filePath));
										} catch (IOException e1) {
											txtAreaLogs.append(Constants.unableToAddTestcase);
										}

									} else {
										txtAreaLogs.append(Constants.testcaseTagNotAdded);
									}
								} catch (IOException e1) {
									txtAreaLogs.append(Constants.testcaseTagNotAdded);
								}
							}

						} catch (IOException e1) {
							txtAreaLogs.append(Constants.unableToCreateTestSuite);
						}
					}
				}
				txtAreaLogs.append("\n"+Constants.endOfProcessText);
			}
		});

		btnClearLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAreaLogs.setText("");
			}
		});
	}
}
