package com.sws.components;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class AutoCloseDialog extends Thread{
	public JDialog dialog;
	public AutoCloseDialog( String title, String message)
	{
		JOptionPane op = new JOptionPane(message,JOptionPane.INFORMATION_MESSAGE);
	        
		dialog = op.createDialog(title);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setAlwaysOnTop(false);
		dialog.setModal(false);
		dialog.setVisible(true);
	}
}
