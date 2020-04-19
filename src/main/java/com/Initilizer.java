package com;

public class Initilizer {
	public static WindowGUI window;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			window = new WindowGUI();		
			window.start();
			Thread.currentThread().setName("GUIThread");
			window.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
