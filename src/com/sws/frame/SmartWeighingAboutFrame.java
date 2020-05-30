package com.sws.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 关于界面
 * @author Ben
 *
 */
public class SmartWeighingAboutFrame {

	public JPanel showFrame()
	{
		JPanel panel = new JPanel();
		Font font = new Font("宋体", Font.BOLD, 20);
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300,500,200};
		gridBagLayout.rowHeights = new int[]{100, 100,100,100,100,100,300};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0,1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0,1.0, 1.0,1.0, 1.0,1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 1,gridY = 1;
		
		JLabel systemNameLabel = new JLabel("系统名称：智能称重数据采集终端系统");
		systemNameLabel.setFont(font);
		GridBagConstraints gbc_systemNameLabel = new GridBagConstraints();
		gbc_systemNameLabel.fill = GridBagConstraints.BOTH;
		gbc_systemNameLabel.insets = new Insets(0, 0, 0, 0);
		gbc_systemNameLabel.gridx = gridX;
		gbc_systemNameLabel.gridy = gridY;
		panel.add(systemNameLabel,gbc_systemNameLabel);
		
		gridY++;
		
		JLabel versionLabel = new JLabel("版本号：V1.0");
		versionLabel.setFont(font);
		GridBagConstraints gbc_versionLabel = new GridBagConstraints();
		gbc_versionLabel.fill = GridBagConstraints.BOTH;
		gbc_versionLabel.insets = new Insets(0, 0, 0, 0);
		gbc_versionLabel.gridx = gridX;
		gbc_versionLabel.gridy = gridY;
		panel.add(versionLabel,gbc_versionLabel);
		
		gridY++;
		
		JLabel copyrightLabel = new JLabel("版权：版权所有");
		copyrightLabel.setFont(font);
		GridBagConstraints gbc_copyrightLabel = new GridBagConstraints();
		gbc_copyrightLabel.fill = GridBagConstraints.BOTH;
		gbc_copyrightLabel.insets = new Insets(0, 0, 0, 0);
		gbc_copyrightLabel.gridx = gridX;
		gbc_copyrightLabel.gridy = gridY;
		panel.add(copyrightLabel,gbc_copyrightLabel);
		
		gridY++;
		
		JLabel warningLabel = new JLabel("警告：本软件仅供使用，未经许可，任何人不能任意");
		warningLabel.setFont(font);
		warningLabel.setForeground(Color.RED);
		GridBagConstraints gbc_warningLabel = new GridBagConstraints();
		gbc_warningLabel.fill = GridBagConstraints.BOTH;
		gbc_warningLabel.insets = new Insets(0, 0, 0, 0);
		gbc_warningLabel.gridx = gridX;
		gbc_warningLabel.gridy = gridY;
		panel.add(warningLabel,gbc_warningLabel);
		
		gridY++;
		
		JLabel warningLabel2 = new JLabel("复制散发，否则将追究其法律责任！");
		warningLabel2.setFont(font);
		warningLabel2.setForeground(Color.RED);
		GridBagConstraints gbc_warningLabel2 = new GridBagConstraints();
		gbc_warningLabel2.fill = GridBagConstraints.BOTH;
		gbc_warningLabel2.insets = new Insets(0, 0, 0, 0);
		gbc_warningLabel2.gridx = gridX;
		gbc_warningLabel2.gridy = gridY;
		panel.add(warningLabel2,gbc_warningLabel2);
		
		return panel;
	}
}
