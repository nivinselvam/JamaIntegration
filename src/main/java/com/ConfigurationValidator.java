package com;

import java.io.File;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class ConfigurationValidator {
	String filePath, invalidPaths = "";
	File file;
	boolean valid;
	Pattern pattern;
	Matcher matcher;

	public boolean validatePaths() {
		valid = true;
		filePath = Initializer.GUI.txtPATSinit.getText();
		file = new File(filePath);
		if (!file.exists()) {
			invalidPaths = invalidPaths + "PATS initialization file\n";
			valid = false;
		}
		filePath = Initializer.GUI.txtTestSuite.getText();
		file = new File(filePath);
		if (!file.exists()) {
			invalidPaths = invalidPaths + "Test suite file\n";
			valid = false;
		}
		filePath = Initializer.GUI.txtReceipts.getText();
		file = new File(filePath);
		if (!file.exists()) {
			invalidPaths = invalidPaths + "Receipt files\n";
			valid = false;
		}
		filePath = Initializer.GUI.txtReceiptRules.getText();
		file = new File(filePath);
		if (!file.exists()) {
			invalidPaths = invalidPaths + "Receipt Rule files\n";
			valid = false;
		}
		filePath = Initializer.GUI.txtTLOG.getText();
		file = new File(filePath);
		if (!file.exists()) {
			invalidPaths = invalidPaths + "TLOG files\n";
			valid = false;
		}
		filePath = Initializer.GUI.txtTLOGRules.getText();
		file = new File(filePath);
		if (!file.exists()) {
			invalidPaths = invalidPaths + "TLOG rule files\n";
			valid = false;
		}
		filePath = Initializer.GUI.txtResultReport.getText();
		file = new File(filePath);
		if (!file.exists()) {
			invalidPaths = invalidPaths + "Results report file\n";
			valid = false;
		}

		displayInvalidPaths();
		if (valid == true && Initializer.GUI.chckbxPATSinit.isSelected()) {
			valid = validatePATSfile();
		}

		if (valid == true && Initializer.GUI.chckbxAutoTriggerBuild.isSelected()) {
			valid = validateBuildMachinePath();
		}

		return valid;
	}

	public void displayInvalidPaths() {
		if (valid == false) {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ "Paths configured for saving below files are invalid\n");
			System.out.println(invalidPaths);
			JOptionPane.showMessageDialog(Initializer.GUI.txtAreaLogs,
					"Paths configured for saving below files are invalid\n\n" + invalidPaths
							+ "\nPlease correct the path!");
			invalidPaths = "";
		}
	}

	public boolean validatePATSfile() {
		valid = true;
		file = new File(Initializer.GUI.txtPATSinit.getText() + "\\" + getPATSinitFileName());
		if (!file.exists()) {
			System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
					+ "PATS init file configured is not accessible or invalid.\n");
			JOptionPane.showMessageDialog(Initializer.GUI.txtAreaLogs,
					"PATS init file configured is not accessible or invalid\n");
			valid = false;
		}
		return valid;
	}

	public String getPATSinitFileName() {
		String name = Initializer.GUI.txtPATSfileName.getText();
		pattern = Pattern.compile(".*\\.bat", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(name);
		if (matcher.find()) {
			return name;
		} else {
			return name + ".bat";
		}
	}

	public boolean validateBuildMachinePath() {
		valid = true;
		if(Initializer.GUI.cbxBuildMachine.getSelectedItem().toString().equals("Blr2petroauto01")||Initializer.GUI.cbxBuildMachine.getSelectedItem().toString().equals("BLR2-NIVINS1")) {
			file = new File("\\\\" + Initializer.GUI.cbxBuildMachine.getSelectedItem().toString() + "\\cicd\\BuildSubmit");
		}else {
			file = new File("\\\\" + Initializer.GUI.cbxBuildMachine.getSelectedItem().toString() + "\\queue");
		}
		
		if (!file.exists()) {
			valid = false;
			JOptionPane.showMessageDialog(Initializer.GUI.cbxBuildMachine, "Selected build machine is not accessible");
		}
		return valid;
	}

}
