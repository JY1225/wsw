package com.sws.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SwsJTextField extends JTextField{

	public SwsJTextField(JPanel panel, int gridX, int gridY)
	{
		this(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 1, 1);
	}
	
	public SwsJTextField(JPanel panel, int gridX, int gridY, int gridWidth)
	{
		this(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, gridWidth, 1);
	}
	
	public SwsJTextField(JPanel panel,int fill, int gridX, int gridY, int gridWidth, int gridHeight)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = fill;
		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		gbc.gridwidth=gridWidth;
		gbc.gridheight=gridHeight;
		panel.add(this,gbc);
	}
}
