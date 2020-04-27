package com;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.BadLocationException;
import javax.swing.text.NumberFormatter;

import org.apache.log4j.Logger;

public class WindowGUI {

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
		frmAttDriver.setBounds(100, 100, 527, 517);
		frmAttDriver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblTestPlanId = new JLabel("Test Plan ID");
		lblTestPlanId.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblRuntimeLogs = new JLabel("Runtime logs:");

		JButton btnOk = new JButton("Ok");

//		NumberFormat format = NumberFormat.getInstance();
//		NumberFormatter formatter = new NumberFormatter(format);
//		formatter.setValueClass(Integer.class);
//		formatter.setMinimum(0);
//		formatter.setMaximum(Integer.MAX_VALUE);
//		formatter.setAllowsInvalid(false);
//		// If you want the value to be committed on each keystroke instead of focus lost
//		formatter.setCommitsOnValidEdit(true);
		txtTestPlanID = new JFormattedTextField();
		JScrollPane spLogs = new JScrollPane();		

		JButton btnClearLogs = new JButton("Clear Logs");

		GroupLayout groupLayout = new GroupLayout(frmAttDriver.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(lblTestPlanId)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtTestPlanID, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addGap(23)
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnClearLogs, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblRuntimeLogs)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(spLogs)
					.addGap(14))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTestPlanId)
						.addComponent(txtTestPlanID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClearLogs, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOk))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblRuntimeLogs)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spLogs, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
					.addContainerGap())
		);
		
				txtAreaLogs = new JTextArea();
				spLogs.setViewportView(txtAreaLogs);
				txtAreaLogs.setEditable(false);
				txtAreaLogs.setLineWrap(true);
				PrintStream printStream = new PrintStream(new CustomOutputStream(txtAreaLogs));
				PrintStream standardout = System.out;
				System.setOut(printStream);
				System.setErr(printStream);

		frmAttDriver.getContentPane().setLayout(groupLayout);

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printLog();
			}
		});

		btnClearLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtAreaLogs.getDocument().remove(0, txtAreaLogs.getDocument().getLength());
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				};
			}
		});
	}
	
	/**
     * Prints log statements in the text area
     */
    private void printLog() {
        Thread thread = new Thread(new Runnable() {
            //@Override
            public void run() {

				String filePath = "", testCasesResult = "";
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+Constants.startProcessText);
				if (txtTestPlanID.getText().equals("")) {
					System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+Constants.testPlanIDMandate);
				} else {

					testCasesResult = wsp.getTestcaseFromTestPlan(txtTestPlanID.getText());
					if (testCasesResult.equals(Constants.invalidTestPlanID)
							|| testCasesResult.equals(Constants.jamaConnectivityIssue)) {
						System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+testCasesResult);
					} else {
						// Below try catch block will create test suite xml file
						try {
							InetAddress addr = InetAddress.getLocalHost();
							filePath = "C:\\Users\\" + addr.getHostName().substring(5).toLowerCase()
									+ "\\OneDrive - Verifone\\Desktop\\TestSuite.xml";
							// logger.info("System name is " +
							// addr.getHostName().substring(5).toLowerCase());
							System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+"Trying to create test suite in the path: "+filePath);
							status = fm.createXMLFile(filePath);
							if (status.equals(Constants.unableToDeleteTestSuite)) {
								System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+status);
							} else {
								System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+status);
								// This try catch block will add the test case tag to the test suite xml
								try {
									status = fm.addTestCasesTag(filePath);
									if (status.equals(Constants.testcaseTagAdded)) {
										System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+status);
										try {
											System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+fm.addTestCasesToTestSuite(testCasesResult, filePath));
										} catch (IOException e1) {
											System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+Constants.unableToAddTestcase);
										}

									} else {
										System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+Constants.testcaseTagNotAdded);
									}
								} catch (IOException e1) {
									System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+Constants.testcaseTagNotAdded);
								}
							}

						} catch (IOException e1) {
							System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+Constants.unableToCreateTestSuite);
						}
					}
				}
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())+Constants.endOfProcessText);
			
            }
        });
        thread.start();
    }
}
