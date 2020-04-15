package com;

import java.awt.EventQueue;

public class Initilizer {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//System.out.println(Thread.currentThread().getName());
		//EventQueue.invokeLater(new Runnable() {			
			//public void run() {
				try {
					//Thread.currentThread().setName("GUI");
					WindowGUI window = new WindowGUI();
					System.out.println(Thread.currentThread().getName());
					window.frmAttDriver.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			//}
		//});
	}

}
