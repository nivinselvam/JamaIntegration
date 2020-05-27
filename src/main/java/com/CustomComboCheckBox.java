package com;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

public class CustomComboCheckBox extends JComboBox {

	private JCheckBox chkbx;
	private ArrayList<String> selectedItems;
	private ComboBoxModel model;

	public CustomComboCheckBox(Vector vector) {
		super(vector);
		// Set Renderer
		setRenderer(new ComboCheckBoxRenderer());
		// Set Listener
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkboxSelection();
			}
		});
	}

	private void checkboxSelection() {
		// Get selection
		Object selected = getSelectedItem();
		if (selected instanceof JCheckBox) {
			chkbx = (JCheckBox) selected;
			chkbx.setSelected(!chkbx.isSelected());
			repaint();
		}
	}

	public ArrayList<String> getSelectedItems() {
		int size;
		selectedItems = new ArrayList<String>();
		model = this.getModel();
		size = model.getSize();
		for(int i=0; i<size;i++) {
			Object element = model.getElementAt(i);
			JCheckBox chkbx = (JCheckBox) element;
			if(chkbx.isSelected()) {
				selectedItems.add(chkbx.getText());
			}
		}		
		return selectedItems;
	}
}

class ComboCheckBoxRenderer implements ListCellRenderer {

	private JLabel label;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		// TODO Auto-generated method stub

		// Assign current properties of JCombo to Checkbox e.g bg, fg colors
		if (value instanceof Component) {
			Component component = (Component) value;
			if (isSelected) {
				component.setBackground(list.getSelectionBackground());
				component.setForeground(list.getSelectionForeground());
			} else {
				component.setBackground(list.getBackground());
				component.setForeground(list.getForeground());
			}
			return component;
		} else {
			if (label == null) {
				label = new JLabel(value.toString());
			} else {
				label.setText(value.toString());
			}
			return label;
		}
	}

}