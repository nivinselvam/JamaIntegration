package com;

public class Initilizer {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WindowGUI window = new WindowGUI();
			window.frmAttDriver.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
