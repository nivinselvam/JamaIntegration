package com;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

public class PATSInitializer {
	
	Process PATSinitProcess;
	private Date date;
	
	public Date getDate() {
		return date;
	}
	
	
	public boolean initiatePATS(){
		try {
			//PATSinitProcess = Runtime.getRuntime().exec(Initializer.GUI.txtPATSinit+"\\"+Initializer.cv.getPATSinitFileName());
			PATSinitProcess = Runtime.getRuntime().exec(
					"cmd /c "+Initializer.cv.getPATSinitFileName(), null, new File(Initializer.GUI.txtPATSinit.getText()));
			if(PATSinitProcess.isAlive()) {
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ "PATS initiated succesfully");
				date = Calendar.getInstance().getTime();
				return true;
			}else {
				System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
						+ "PATS initiation failed");
				JOptionPane.showMessageDialog(Initializer.GUI.txtAreaLogs, "PATS initiation failed. Please retry.");
				return false;
			}
		}catch(IOException e) {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ "PATS initiation failed");
			JOptionPane.showMessageDialog(Initializer.GUI.txtAreaLogs, "PATS initiation failed. Please retry.");
			return false;
		}
		
	}
	
}
