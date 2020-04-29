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

import java.util.Calendar;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.BadLocationException;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class WindowGUI {

	public JFrame frmAttDriver;
	public JTextArea txtAreaLogs;
	public JFormattedTextField txtTestPlanID;
	public JLabel lblDownloadedTcValue;
	public JLabel lblAvailableTcValue;
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
		frmAttDriver.setBounds(100, 100, 511, 576);
		frmAttDriver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblTestPlanId = new JLabel("Test Plan ID");
		lblTestPlanId.setFont(new Font("Tahoma", Font.BOLD, 13));

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
		
		lblDownloadedTcValue = new JLabel("");
		
		JLabel lblDownloadedTcField = new JLabel("Downloaded Testcases:");
		
		lblAvailableTcValue = new JLabel("");
		
		JLabel lblAvailableTcField = new JLabel("Available Test Cases:");

		GroupLayout groupLayout = new GroupLayout(frmAttDriver.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(spLogs, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDownloadedTcField)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDownloadedTcValue))
						.addComponent(lblRuntimeLogs)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblTestPlanId)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtTestPlanID, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(130)
									.addComponent(lblAvailableTcValue))
								.addComponent(lblAvailableTcField))
							.addGap(18)
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnClearLogs, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTestPlanId)
						.addComponent(txtTestPlanID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOk)
						.addComponent(btnClearLogs, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAvailableTcValue)
						.addComponent(lblAvailableTcField))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDownloadedTcField)
						.addComponent(lblDownloadedTcValue))
					.addGap(18)
					.addComponent(lblRuntimeLogs)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spLogs, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
					.addContainerGap())
		);
		
				txtAreaLogs = new JTextArea();
				spLogs.setViewportView(txtAreaLogs);
				txtAreaLogs.setEditable(false);
				txtAreaLogs.setLineWrap(true);
				PrintStream printStream = new PrintStream(new CustomOutputStream(txtAreaLogs));
				//PrintStream standardout = System.out;
				System.setOut(printStream);
				System.setErr(printStream);

		frmAttDriver.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmAttDriver.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSaveLogs = new JMenuItem("Save Logs");
		mnFile.add(mntmSaveLogs);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		
		mnFile.add(mntmClose);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblAvailableTcValue.setText("");
				lblDownloadedTcValue.setText("");
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
		
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
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
							|| testCasesResult.equals(Constants.jamaConnectivityIssue)|| testCasesResult.equals(Constants.webservicesError)) {
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
