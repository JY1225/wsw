package com.sws.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwsJLabel extends JLabel{

	public SwsJLabel(JPanel panel, int gridX, int gridY, String labelText)
	{
		this(panel, GridBagConstraints.BOTH, gridX, gridY, 1, 1, labelText);
	}
	
	public SwsJLabel(JPanel panel, int gridX, int gridY, int gridWidth, String labelText)
	{
		this(panel, GridBagConstraints.BOTH, gridX, gridY, gridWidth, 1, labelText);
	}
	
	public SwsJLabel(JPanel panel,int fill, int gridX, int gridY, int gridWidth, int gridHeight, String labelText)
	{
		this(panel, fill, 5, 5, gridX, gridY, gridWidth, gridHeight, labelText);
	}
	
	public SwsJLabel(JPanel panel,int fill, int insetLeft, int insetRight, int gridX, int gridY, int gridWidth, int gridHeight, String labelText)
	{
		this.setText(labelText);
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
