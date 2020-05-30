package com.sws.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SwsJRadioButton extends JRadioButton{

	public SwsJRadioButton(JPanel panel, int gridX, int gridY, String text, ActionListener actionListener)
	{
		this(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 1, 1, text, actionListener);
	}
	
	public SwsJRadioButton(JPanel panel, int gridX, int gridY, int gridWidth, String text, ActionListener actionListener)
	{
		this(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, gridWidth, 1, text, actionListener);
	}
	
	public SwsJRadioButton(JPanel panel,int fill, int gridX, int gridY, int gridWidth, int gridHeight, String text, ActionListener actionListener)
	{
		this(panel, fill, 5, 5, gridX, gridY, gridWidth, gridHeight, text, actionListener);
	}
	
	public SwsJRadioButton(JPanel panel,int fill, int insetLeft, int insetRight, int gridX, int gridY, int gridWidth, int gridHeight, String text, ActionListener actionListener)
	{
		this.setText(text);
		this.addActionListener(actionListener);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = fill;
		gbc.insets = new Insets(0, insetLeft, 0, insetRight);
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		gbc.gridwidth=gridWidth;
		gbc.gridheight=gridHeight;
		panel.add(this,gbc);
	}
}
