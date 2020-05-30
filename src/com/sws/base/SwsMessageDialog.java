package com.sws.base;

import javax.swing.JOptionPane;

public class SwsMessageDialog {

	private static final String MESSAGE_WARNING = " ¾¯¸æ";
	private static final String MESSAGE_INFORMATION = " ÏûÏ¢";
	private static final String MESSAGE_ERROR = " ´íÎó";
	public static final int OK_OPTION = 0;
	public final static int CANCEL_OPTION = 2;
	
	public static void warningDialog(String message)
	{
		String msg = "<html><font color=\"black\" style=\"font-weight:bold;\">"+message+"</font></html>";
		JOptionPane.showMessageDialog(null, msg, MESSAGE_WARNING, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void errorDialog(String message)
	{
		String msg = "<html><font color=\"red\" style=\"font-weight:bold\">"+message+"</font></html>";
		JOptionPane.showMessageDialog(null, msg, MESSAGE_ERROR, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void infoDialog(String message)
	{
		String msg = "<html><font color=\"green\" style=\"font-weight:bold\">"+message+"</font></html>";
		JOptionPane.showMessageDialog(null, msg, MESSAGE_INFORMATION, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static int confirmDialog(String message, String title)
	{
		String msg = "<html><font color=\"red\" style=\"font-weight:bold\">"+message+"</font></html>";
		
		return JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.OK_CANCEL_OPTION);
	}
}
