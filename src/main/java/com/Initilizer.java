package com;

import javax.swing.SwingUtilities;

public class Initilizer {
	static WindowGUI GUI;
	static FileManipulator fm = new FileManipulator();
	static WebServiceProcessor wsp = new WebServiceProcessor();
	static AttachmentDownloader AD = new AttachmentDownloader();
	
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
