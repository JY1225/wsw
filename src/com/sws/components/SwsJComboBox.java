package com.sws.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SwsJComboBox extends JComboBox{

	public SwsJComboBox(JPanel panel, int gridX, int gridY)
	{
		this(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 1, 1);
	}
	
	public SwsJComboBox(JPanel panel, int gridX, int gridY, int gridWidth)
	{
		this(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, gridWidth, 1);
	}
	
	public SwsJComboBox(JPanel panel,int fill, int gridX, int gridY, int gridWidth, int gridHeight)
	{
		this(panel, fill, 5, 5, gridX, gridY, gridWidth, gridHeight);
	}
	
	public SwsJComboBox(JPanel panel,int fill, int insetLeft, int insetRight, int gridX, int gridY, int gridWidth, int gridHeight)
	{
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
