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

public class WindowGUI{

	public JFrame frmAttDriver;
	public JTextArea txtAreaLogs;
	public JFormattedTextField txtTestPlanID;
	public Logger logger = Logger.getLogger(WindowGUI.class);
	FileManipulator fm = new FileManipulator();
	WebServiceProcessor wsp = new WebServiceProcessor();
	String consolidatedTestCases, xmlFileCreationStatus;

	/**
	 * Create the application.
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
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(spLogs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtAreaLogs, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(lblTestPlanId)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtTestPlanID, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(lblRuntimeLogs))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(60)
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addGap(56)
							.addComponent(btnClearLogs, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTestPlanId)
						.addComponent(txtTestPlanID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(22)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOk)
						.addComponent(btnClearLogs, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblRuntimeLogs)
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(spLogs, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
						.addComponent(txtAreaLogs, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);

		frmAttDriver.getContentPane().setLayout(groupLayout);

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtTestPlanID.getText().equals("")) {
					txtAreaLogs.append("\nPlease enter a valid test plan id");
				} else {
					logger.info("Started processing");
					txtAreaLogs.append("\nStarted processing");
					
					try {
						InetAddress addr = InetAddress.getLocalHost();
						String filePath = "C:\\Users\\" + addr.getHostName().substring(5).toLowerCase()
								+ "\\OneDrive - Verifone\\Desktop\\TestSuite.xml";
						logger.info("xml file will be created in the path "+filePath);
						txtAreaLogs.append("\nxml file will be created in the path "+filePath);						
						xmlFileCreationStatus = fm.createXMLFile(filePath);
						txtAreaLogs.append(xmlFileCreationStatus);
						if(xmlFileCreationStatus.equals("\nTest suite xml file already exists.\nExisting test suite file was deleted and new file was created.")) {
							txtAreaLogs.append(fm.addTestCasesTag(filePath));
						}else {
							txtAreaLogs.append(xmlFileCreationStatus);
						}
						
						consolidatedTestCases = wsp.getTestcaseFromTestPlan(txtTestPlanID.getText());
						if(consolidatedTestCases.equals("Unable to get testcase from testplan")) {
							txtAreaLogs.append("\nEntered test plan ID is invalid or there is JAMA connectivity issue");
						}else {
							txtAreaLogs.append(fm.addTestCasesToTestSuite(consolidatedTestCases, filePath));
						}						

					} catch (IOException e1) {
						logger.fatal("Unable to create the xml file");
						txtAreaLogs.append("\nUnable to create the xml file\n");
					}
					
					logger.info("Finished processing");
					txtAreaLogs.append("\nFinished processing");
				}
			}
		});
		
		btnClearLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAreaLogs.setText("");
			}
		});
	}
}
