package com.sws.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SwsJPanel extends JPanel{

	public SwsJPanel(JFrame frame, JPanel panel, int gridX, int gridY)
	{
		this(frame, panel, GridBagConstraints.BOTH, gridX, gridY, 1, 1);
	}
	
	public SwsJPanel(JFrame frame, JPanel panel, int gridX, int gridY, int gridWidth)
	{
		this(frame, panel, GridBagConstraints.BOTH, gridX, gridY, gridWidth, 1);
	}
	
	public SwsJPanel(JFrame frame, JPanel panel,int fill, int gridX, int gridY, int gridWidth, int gridHeight)
	{
		this(frame, panel, fill, 0, 5, 0, 5, gridX, gridY, gridWidth, gridHeight);
	}
	
	public SwsJPanel(JFrame frame, JPanel panel,int fill,int insetTop, int insetLeft, int insetBottom, int insetRight, int gridX, int gridY, int gridWidth, int gridHeight)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = fill;
		gbc.insets = new Insets(insetTop, insetLeft, insetBottom, insetRight);
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		gbc.gridwidth=gridWidth;
		gbc.gridheight=gridHeight;
		frame.add(this,gbc);
	}
}
