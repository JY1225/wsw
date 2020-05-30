package com.sws.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class SwsJCheckBox extends JCheckBox{

	public SwsJCheckBox(JPanel panel, int gridX, int gridY)
	{
		this(panel, GridBagConstraints.BOTH, gridX, gridY, 1, 1);
	}
	
	public SwsJCheckBox(JPanel panel, int gridX, int gridY, int gridWidth)
	{
		this(panel, GridBagConstraints.BOTH, gridX, gridY, gridWidth, 1);
	}
	
	public SwsJCheckBox(JPanel panel,int fill, int gridX, int gridY, int gridWidth, int gridHeight)
	{
		/*GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = fill;
		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		gbc.gridwidth=gridWidth;
		gbc.gridheight=gridHeight;
		panel.add(this,gbc);*/
		this(panel, fill, 5, 5, gridX, gridY, gridWidth, gridHeight);
	}
	
	public SwsJCheckBox(JPanel panel,int fill, int insetLeft, int insetRight, int gridX, int gridY, int gridWidth, int gridHeight)
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
