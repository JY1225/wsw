package com.sws.components;

import javax.swing.*;
import java.awt.*;

public class SwsJPasswordField extends JPasswordField{

	public SwsJPasswordField(JPanel panel, int gridX, int gridY)
	{
		this(panel, GridBagConstraints.BOTH, gridX, gridY, 1, 1);
	}

	public SwsJPasswordField(JPanel panel, int gridX, int gridY, int gridWidth)
	{
		this(panel, GridBagConstraints.BOTH, gridX, gridY, gridWidth, 1);
	}

	public SwsJPasswordField(JPanel panel, int fill, int gridX, int gridY, int gridWidth, int gridHeight)
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
