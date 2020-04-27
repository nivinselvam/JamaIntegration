package com;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.SwingUtilities;

public class Initilizer {
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WindowGUI GUI = new WindowGUI();
                GUI.frmAttDriver.setVisible(true);
            }
        });
    }

}
