package com.sws.frame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.sws.base.SwsParameter;
import com.sws.components.JDateChooserTextField;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;

/**
 * 统计报表界面
 * @author Ben
 *
 */
public class SmartWeighingStatisticalReportFrame3 implements ActionListener{
	
	//public JPanel resultPanel = new JPanel();
	private JTextField produceDate;
	private JPanel leftPanel = new JPanel();
	private JCheckBox produceDateBox;
	private JCheckBox deviceNameBox;
	private JCheckBox classBox;
	private JCheckBox teamNameBox;
	private JCheckBox materialNameBox;
	private JCheckBox materialSpecificationBox;
	private JCheckBox operatorBox;
	private JCheckBox unitNameBox;//机组名称
	
	
	private JButton weightDetailExportBtn;
	private JButton weightSummaryExportBtn;
	private JButton inOutDetailExportBtn;
	private JButton inOutSummaryExportBtn;
	
	private Type queryType;
	
	public SmartWeighingStatisticalReportFrame3() 
	{
		EventListener eListener = new EventListener();
		SwsParameter.swsEventManager.addListener(eListener);
	}

	public JPanel showFrame()
	{
		JPanel panel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1000};
		gridBagLayout.rowHeights = new int[]{100, 600};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		JPanel buttonPanel = createButtonPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.insets = new Insets(0, 0, 0, 5);
		gbc_buttonPanel.gridx = gridX;
		gbc_buttonPanel.gridy = gridY;
		panel.add(buttonPanel,gbc_buttonPanel);
		
		gridY++;
		
		JPanel mainTainPanel = createMaintainPanel();
		GridBagConstraints gbc_mainTainPanel = new GridBagConstraints();
		gbc_mainTainPanel.fill = GridBagConstraints.BOTH;
		gbc_mainTainPanel.insets = new Insets(0, 0, 0, 0);
		gbc_mainTainPanel.gridx = gridX;
		gbc_mainTainPanel.gridy = gridY;
		panel.add(mainTainPanel,gbc_mainTainPanel);
		
		return panel;
	}
	
	private JPanel createButtonPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 100, 20, 100, 20, 100, 20, 100, 20};
		gridBagLayout.rowHeights = new int[]{100};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 1,gridY = 0;
		
		JButton weightDetailBtn = new JButton("称重数据明细表");
		weightDetailBtn.addActionListener(this);
		GridBagConstraints gbc_weightDetailBtn = new GridBagConstraints();
		gbc_weightDetailBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_weightDetailBtn.insets = new Insets(0, 0, 0, 0);
		gbc_weightDetailBtn.gridx = gridX;
		gbc_weightDetailBtn.gridy = gridY;
		panel.add(weightDetailBtn,gbc_weightDetailBtn);
		
		gridX+=2;
		
		JButton weightSummaryBtn = new JButton("称重数据汇总表");
		weightSummaryBtn.addActionListener(this);
		GridBagConstraints gbc_weightSummaryBtn = new GridBagConstraints();
		gbc_weightSummaryBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_weightSummaryBtn.insets = new Insets(0, 0, 0, 0);
		gbc_weightSummaryBtn.gridx = gridX;
		gbc_weightSummaryBtn.gridy = gridY;
		panel.add(weightSummaryBtn,gbc_weightSummaryBtn);
		
		gridX+=2;
		
		JButton inOutConsumeDetailBtn = new JButton("进出消耗数据明细表");
		inOutConsumeDetailBtn.addActionListener(this);
		GridBagConstraints gbc_inOutConsumeDetailBtn = new GridBagConstraints();
		gbc_inOutConsumeDetailBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_inOutConsumeDetailBtn.insets = new Insets(0, 0, 0, 0);
		gbc_inOutConsumeDetailBtn.gridx = gridX;
		gbc_inOutConsumeDetailBtn.gridy = gridY;
		panel.add(inOutConsumeDetailBtn,gbc_inOutConsumeDetailBtn);
		
		gridX+=2;
		
		JButton inOutConsumeSummaryBtn = new JButton("进出消耗数据汇总表");
		inOutConsumeSummaryBtn.addActionListener(this);
		GridBagConstraints gbc_inOutConsumeSummaryBtn = new GridBagConstraints();
		gbc_inOutConsumeSummaryBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_inOutConsumeSummaryBtn.insets = new Insets(0, 0, 0, 0);
		gbc_inOutConsumeSummaryBtn.gridx = gridX;
		gbc_inOutConsumeSummaryBtn.gridy = gridY;
		panel.add(inOutConsumeSummaryBtn,gbc_inOutConsumeSummaryBtn);
		
		return panel;
	}
	
	private JPanel createMaintainPanel()
	{
		JPanel panel = new JPanel();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300,700};
		gridBagLayout.rowHeights = new int[]{600};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		int gridX=0, gridY=0;
		
		//JPanel leftPanel = createLeftPanel();
		GridBagConstraints gbc_leftPanel = new GridBagConstraints();
		gbc_leftPanel.fill = GridBagConstraints.BOTH;
		gbc_leftPanel.insets = new Insets(0, 0, 0, 0);
		gbc_leftPanel.gridx = gridX;
		gbc_leftPanel.gridy = gridY;
		panel.add(leftPanel,gbc_leftPanel);
		
		gridX++;
		
		JPanel rightPanel = createRightPanel();
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.fill = GridBagConstraints.BOTH;
		gbc_rightPanel.insets = new Insets(0, 0, 0, 0);
		gbc_rightPanel.gridx = gridX;
		gbc_rightPanel.gridy = gridY;
		panel.add(rightPanel,gbc_rightPanel);
		
		return panel;
	}
	
	//称重数据明细表左边面板
	private JPanel createWeightDetailPanel()
	{
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 50, 200};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50, 50, 50, 50, 50,200};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		leftPanel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		produceDateBox = new JCheckBox();
		GridBagConstraints gbc_produceDateBox = new GridBagConstraints();
		gbc_produceDateBox.fill = GridBagConstraints.EAST;
		gbc_produceDateBox.insets = new Insets(0, 0, 0, 0);
		gbc_produceDateBox.gridx = gridX;
		gbc_produceDateBox.gridy = gridY;
		leftPanel.add(produceDateBox,gbc_produceDateBox);
		
		gridX++;
		
		JLabel produceDateLabel = new JLabel("生产日期：");
		GridBagConstraints gbc_produceDateLabel = new GridBagConstraints();
		gbc_produceDateLabel.fill = GridBagConstraints.BOTH;
		gbc_produceDateLabel.insets = new Insets(0, 0, 0, 0);
		gbc_produceDateLabel.gridx = gridX;
		gbc_produceDateLabel.gridy = gridY;
		leftPanel.add(produceDateLabel,gbc_produceDateLabel);
		
		gridX++;
		
		JTextField produceDateTextField = new JDateChooserTextField();
		GridBagConstraints gbc_produceDateTextField = new GridBagConstraints();
		gbc_produceDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_produceDateTextField.insets = new Insets(0, 0, 0, 5);
		gbc_produceDateTextField.gridx = gridX;
		gbc_produceDateTextField.gridy = gridY;
		leftPanel.add(produceDateTextField,gbc_produceDateTextField);
		
		gridX=0;
		gridY++;
		
		deviceNameBox = new JCheckBox();
		GridBagConstraints gbc_deviceNameBox = new GridBagConstraints();
		gbc_deviceNameBox.fill = GridBagConstraints.EAST;
		gbc_deviceNameBox.insets = new Insets(0, 0, 0, 0);
		gbc_deviceNameBox.gridx = gridX;
		gbc_deviceNameBox.gridy = gridY;
		leftPanel.add(deviceNameBox,gbc_deviceNameBox);
		
		gridX++;
		
		JLabel deviceNameLabel = new JLabel("设备名称：");
		GridBagConstraints gbc_deviceNameLabel = new GridBagConstraints();
		gbc_deviceNameLabel.fill = GridBagConstraints.BOTH;
		gbc_deviceNameLabel.insets = new Insets(0, 0, 0, 0);
		gbc_deviceNameLabel.gridx = gridX;
		gbc_deviceNameLabel.gridy = gridY;
		leftPanel.add(deviceNameLabel,gbc_deviceNameLabel);
		
		gridX++;
		
		JComboBox<String> deviceNameComboBox = new JComboBox<>();
		GridBagConstraints gbc_deviceNameComboBox = new GridBagConstraints();
		gbc_deviceNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_deviceNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_deviceNameComboBox.gridx = gridX;
		gbc_deviceNameComboBox.gridy = gridY;
		leftPanel.add(deviceNameComboBox,gbc_deviceNameComboBox);
		
		gridX=0;
		gridY++;
		
		classBox = new JCheckBox();
		GridBagConstraints gbc_classBox = new GridBagConstraints();
		gbc_classBox.fill = GridBagConstraints.EAST;
		gbc_classBox.insets = new Insets(0, 0, 0, 0);
		gbc_classBox.gridx = gridX;
		gbc_classBox.gridy = gridY;
		leftPanel.add(classBox,gbc_classBox);
		
		gridX++;
		
		JLabel classLabel = new JLabel("班别：");
		GridBagConstraints gbc_classLabel = new GridBagConstraints();
		gbc_classLabel.fill = GridBagConstraints.BOTH;
		gbc_classLabel.insets = new Insets(0, 0, 0, 0);
		gbc_classLabel.gridx = gridX;
		gbc_classLabel.gridy = gridY;
		leftPanel.add(classLabel,gbc_classLabel);
		
		gridX++;
		
		JComboBox<String> classComboBox = new JComboBox<>();
		GridBagConstraints gbc_classComboBox = new GridBagConstraints();
		gbc_classComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_classComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_classComboBox.gridx = gridX;
		gbc_classComboBox.gridy = gridY;
		leftPanel.add(classComboBox,gbc_classComboBox);
		
		gridX=0;
		gridY++;
		
		teamNameBox = new JCheckBox();
		GridBagConstraints gbc_teamNameBox = new GridBagConstraints();
		gbc_teamNameBox.fill = GridBagConstraints.EAST;
		gbc_teamNameBox.insets = new Insets(0, 0, 0, 0);
		gbc_teamNameBox.gridx = gridX;
		gbc_teamNameBox.gridy = gridY;
		leftPanel.add(teamNameBox,gbc_teamNameBox);
		
		gridX++;
		
		JLabel teamNameLabel = new JLabel("班组名称：");
		GridBagConstraints gbc_teamNameLabel = new GridBagConstraints();
		gbc_teamNameLabel.fill = GridBagConstraints.BOTH;
		gbc_teamNameLabel.insets = new Insets(0, 0, 0, 0);
		gbc_teamNameLabel.gridx = gridX;
		gbc_teamNameLabel.gridy = gridY;
		leftPanel.add(teamNameLabel,gbc_teamNameLabel);
		
		gridX++;
		
		JComboBox<String> teamNameComboBox = new JComboBox<>();
		GridBagConstraints gbc_teamNameComboBox = new GridBagConstraints();
		gbc_teamNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_teamNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_teamNameComboBox.gridx = gridX;
		gbc_teamNameComboBox.gridy = gridY;
		leftPanel.add(teamNameComboBox,gbc_teamNameComboBox);
		
		gridX=0;
		gridY++;
		
		materialNameBox = new JCheckBox();
		GridBagConstraints gbc_materialNameBox = new GridBagConstraints();
		gbc_materialNameBox.fill = GridBagConstraints.EAST;
		gbc_materialNameBox.insets = new Insets(0, 0, 0, 0);
		gbc_materialNameBox.gridx = gridX;
		gbc_materialNameBox.gridy = gridY;
		leftPanel.add(materialNameBox,gbc_materialNameBox);
		
		gridX++;
		
		JLabel materialNameLabel = new JLabel("物料名称：");
		GridBagConstraints gbc_materialNameLabel = new GridBagConstraints();
		gbc_materialNameLabel.fill = GridBagConstraints.BOTH;
		gbc_materialNameLabel.insets = new Insets(0, 0, 0, 0);
		gbc_materialNameLabel.gridx = gridX;
		gbc_materialNameLabel.gridy = gridY;
		leftPanel.add(materialNameLabel,gbc_materialNameLabel);
		
		gridX++;
		
		JComboBox<String> materialNameComboBox = new JComboBox<>();
		GridBagConstraints gbc_materialNameComboBox = new GridBagConstraints();
		gbc_materialNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_materialNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_materialNameComboBox.gridx = gridX;
		gbc_materialNameComboBox.gridy = gridY;
		leftPanel.add(materialNameComboBox,gbc_materialNameComboBox);
		
		gridX=0;
		gridY++;
		
		materialSpecificationBox = new JCheckBox();
		GridBagConstraints gbc_materialSpecificationBox = new GridBagConstraints();
		gbc_materialSpecificationBox.fill = GridBagConstraints.EAST;
		gbc_materialSpecificationBox.insets = new Insets(0, 0, 0, 0);
		gbc_materialSpecificationBox.gridx = gridX;
		gbc_materialSpecificationBox.gridy = gridY;
		leftPanel.add(materialSpecificationBox,gbc_materialSpecificationBox);
		
		gridX++;
		
		JLabel materialSpecificationLabel = new JLabel("物料规格：");
		GridBagConstraints gbc_materialSpecificationLabel = new GridBagConstraints();
		gbc_materialSpecificationLabel.fill = GridBagConstraints.BOTH;
		gbc_materialSpecificationLabel.insets = new Insets(0, 0, 0, 0);
		gbc_materialSpecificationLabel.gridx = gridX;
		gbc_materialSpecificationLabel.gridy = gridY;
		leftPanel.add(materialSpecificationLabel,gbc_materialSpecificationLabel);
		
		gridX++;
		
		JComboBox<String> materialSpecificationComboBox = new JComboBox<>();
		GridBagConstraints gbc_materialSpecificationComboBox = new GridBagConstraints();
		gbc_materialSpecificationComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_materialSpecificationComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_materialSpecificationComboBox.gridx = gridX;
		gbc_materialSpecificationComboBox.gridy = gridY;
		leftPanel.add(materialSpecificationComboBox,gbc_materialSpecificationComboBox);
		
		gridX=0;
		gridY++;
		
		operatorBox = new JCheckBox();
		GridBagConstraints gbc_operatorBox = new GridBagConstraints();
		gbc_operatorBox.fill = GridBagConstraints.EAST;
		gbc_operatorBox.insets = new Insets(0, 0, 0, 0);
		gbc_operatorBox.gridx = gridX;
		gbc_operatorBox.gridy = gridY;
		leftPanel.add(operatorBox,gbc_operatorBox);
		
		gridX++;
		
		JLabel operatorLabel = new JLabel("操作员：");
		GridBagConstraints gbc_operatorLabel = new GridBagConstraints();
		gbc_operatorLabel.fill = GridBagConstraints.BOTH;
		gbc_operatorLabel.insets = new Insets(0, 0, 0, 0);
		gbc_operatorLabel.gridx = gridX;
		gbc_operatorLabel.gridy = gridY;
		leftPanel.add(operatorLabel,gbc_operatorLabel);
		
		gridX++;
		
		JComboBox<String> operatorComboBox = new JComboBox<>();
		GridBagConstraints gbc_operatorComboBox = new GridBagConstraints();
		gbc_operatorComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_operatorComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_operatorComboBox.gridx = gridX;
		gbc_operatorComboBox.gridy = gridY;
		leftPanel.add(operatorComboBox,gbc_operatorComboBox);
		
		gridX=2;
		gridY++;
		
		weightDetailExportBtn = new JButton("生成报表并导出Excel");
		weightDetailExportBtn.addActionListener(this);
		GridBagConstraints gbc_weightDetailExportBtn = new GridBagConstraints();
		gbc_weightDetailExportBtn.fill = GridBagConstraints.HORIZONTAL;
		//gbc_exportExcelBtn.gridwidth = 2;
		gbc_weightDetailExportBtn.insets = new Insets(0, 0, 0, 5);
		gbc_weightDetailExportBtn.gridx = gridX;
		gbc_weightDetailExportBtn.gridy = gridY;
		leftPanel.add(weightDetailExportBtn,gbc_weightDetailExportBtn);
		
		return leftPanel;
	}
	
	//称重数据汇总表
	private JPanel createWeightSummaryPanel()
	{
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 50, 200};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50, 50, 350};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		leftPanel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		produceDateBox = new JCheckBox();
		GridBagConstraints gbc_produceDateBox = new GridBagConstraints();
		gbc_produceDateBox.fill = GridBagConstraints.EAST;
		gbc_produceDateBox.insets = new Insets(0, 0, 0, 0);
		gbc_produceDateBox.gridx = gridX;
		gbc_produceDateBox.gridy = gridY;
		leftPanel.add(produceDateBox,gbc_produceDateBox);
		
		gridX++;
		
		JLabel produceDateLabel = new JLabel("生产日期：");
		GridBagConstraints gbc_produceDateLabel = new GridBagConstraints();
		gbc_produceDateLabel.fill = GridBagConstraints.BOTH;
		gbc_produceDateLabel.insets = new Insets(0, 0, 0, 0);
		gbc_produceDateLabel.gridx = gridX;
		gbc_produceDateLabel.gridy = gridY;
		leftPanel.add(produceDateLabel,gbc_produceDateLabel);
		
		gridX++;
		
		JTextField produceDateTextField = new JDateChooserTextField();
		GridBagConstraints gbc_produceDateTextField = new GridBagConstraints();
		gbc_produceDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_produceDateTextField.insets = new Insets(0, 0, 0, 5);
		gbc_produceDateTextField.gridx = gridX;
		gbc_produceDateTextField.gridy = gridY;
		leftPanel.add(produceDateTextField,gbc_produceDateTextField);
		
		gridX=0;
		gridY++;
		
		unitNameBox = new JCheckBox();
		GridBagConstraints gbc_unitNameBox = new GridBagConstraints();
		gbc_unitNameBox.fill = GridBagConstraints.EAST;
		gbc_unitNameBox.insets = new Insets(0, 0, 0, 0);
		gbc_unitNameBox.gridx = gridX;
		gbc_unitNameBox.gridy = gridY;
		leftPanel.add(unitNameBox,gbc_unitNameBox);
		
		gridX++;
		
		JLabel unitNameLabel = new JLabel("机组名称：");
		GridBagConstraints gbc_unitNameLabel = new GridBagConstraints();
		gbc_unitNameLabel.fill = GridBagConstraints.BOTH;
		gbc_unitNameLabel.insets = new Insets(0, 0, 0, 0);
		gbc_unitNameLabel.gridx = gridX;
		gbc_unitNameLabel.gridy = gridY;
		leftPanel.add(unitNameLabel,gbc_unitNameLabel);
		
		gridX++;
		
		JComboBox<String> unitNameComboBox = new JComboBox<>();
		GridBagConstraints gbc_unitNameComboBox = new GridBagConstraints();
		gbc_unitNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_unitNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_unitNameComboBox.gridx = gridX;
		gbc_unitNameComboBox.gridy = gridY;
		leftPanel.add(unitNameComboBox,gbc_unitNameComboBox);
		
		gridX=0;
		gridY++;
		
		classBox = new JCheckBox();
		GridBagConstraints gbc_classBox = new GridBagConstraints();
		gbc_classBox.fill = GridBagConstraints.EAST;
		gbc_classBox.insets = new Insets(0, 0, 0, 0);
		gbc_classBox.gridx = gridX;
		gbc_classBox.gridy = gridY;
		leftPanel.add(classBox,gbc_classBox);
		
		gridX++;
		
		JLabel classLabel = new JLabel("班别：");
		GridBagConstraints gbc_classLabel = new GridBagConstraints();
		gbc_classLabel.fill = GridBagConstraints.BOTH;
		gbc_classLabel.insets = new Insets(0, 0, 0, 0);
		gbc_classLabel.gridx = gridX;
		gbc_classLabel.gridy = gridY;
		leftPanel.add(classLabel,gbc_classLabel);
		
		gridX++;
		
		JComboBox<String> classComboBox = new JComboBox<>();
		GridBagConstraints gbc_classComboBox = new GridBagConstraints();
		gbc_classComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_classComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_classComboBox.gridx = gridX;
		gbc_classComboBox.gridy = gridY;
		leftPanel.add(classComboBox,gbc_classComboBox);
		
		gridX=0;
		gridY++;
		
		teamNameBox = new JCheckBox();
		GridBagConstraints gbc_teamNameBox = new GridBagConstraints();
		gbc_teamNameBox.fill = GridBagConstraints.EAST;
		gbc_teamNameBox.insets = new Insets(0, 0, 0, 0);
		gbc_teamNameBox.gridx = gridX;
		gbc_teamNameBox.gridy = gridY;
		leftPanel.add(teamNameBox,gbc_teamNameBox);
		
		gridX++;
		
		JLabel teamNameLabel = new JLabel("班组名称：");
		GridBagConstraints gbc_teamNameLabel = new GridBagConstraints();
		gbc_teamNameLabel.fill = GridBagConstraints.BOTH;
		gbc_teamNameLabel.insets = new Insets(0, 0, 0, 0);
		gbc_teamNameLabel.gridx = gridX;
		gbc_teamNameLabel.gridy = gridY;
		leftPanel.add(teamNameLabel,gbc_teamNameLabel);
		
		gridX++;
		
		JComboBox<String> teamNameComboBox = new JComboBox<>();
		GridBagConstraints gbc_teamNameComboBox = new GridBagConstraints();
		gbc_teamNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_teamNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_teamNameComboBox.gridx = gridX;
		gbc_teamNameComboBox.gridy = gridY;
		leftPanel.add(teamNameComboBox,gbc_teamNameComboBox);
		
		gridX=2;
		gridY++;
		
		weightSummaryExportBtn = new JButton("生成报表并导出Excel");
		weightSummaryExportBtn.addActionListener(this);
		GridBagConstraints gbc_weightSummaryExportBtn = new GridBagConstraints();
		gbc_weightSummaryExportBtn.fill = GridBagConstraints.HORIZONTAL;
		//gbc_exportExcelBtn.gridwidth = 2;
		gbc_weightSummaryExportBtn.insets = new Insets(0, 0, 0, 5);
		gbc_weightSummaryExportBtn.gridx = gridX;
		gbc_weightSummaryExportBtn.gridy = gridY;
		leftPanel.add(weightSummaryExportBtn,gbc_weightSummaryExportBtn);
		
		return leftPanel;
	}
	
	//进出消耗数据明细表
	private JPanel createInOutDetailPanel()
	{
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 50, 200};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50, 50, 50, 50, 50,200};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		leftPanel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		produceDateBox = new JCheckBox();
		GridBagConstraints gbc_produceDateBox = new GridBagConstraints();
		gbc_produceDateBox.fill = GridBagConstraints.EAST;
		gbc_produceDateBox.insets = new Insets(0, 0, 0, 0);
		gbc_produceDateBox.gridx = gridX;
		gbc_produceDateBox.gridy = gridY;
		leftPanel.add(produceDateBox,gbc_produceDateBox);
		
		gridX++;
		
		JLabel produceDateLabel = new JLabel("生产日期：");
		GridBagConstraints gbc_produceDateLabel = new GridBagConstraints();
		gbc_produceDateLabel.fill = GridBagConstraints.BOTH;
		gbc_produceDateLabel.insets = new Insets(0, 0, 0, 0);
		gbc_produceDateLabel.gridx = gridX;
		gbc_produceDateLabel.gridy = gridY;
		leftPanel.add(produceDateLabel,gbc_produceDateLabel);
		
		gridX++;
		
		JTextField produceDateTextField = new JDateChooserTextField();
		GridBagConstraints gbc_produceDateTextField = new GridBagConstraints();
		gbc_produceDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_produceDateTextField.insets = new Insets(0, 0, 0, 5);
		gbc_produceDateTextField.gridx = gridX;
		gbc_produceDateTextField.gridy = gridY;
		leftPanel.add(produceDateTextField,gbc_produceDateTextField);
		
		gridX=0;
		gridY++;
		
		deviceNameBox = new JCheckBox();
		GridBagConstraints gbc_deviceNameBox = new GridBagConstraints();
		gbc_deviceNameBox.fill = GridBagConstraints.EAST;
		gbc_deviceNameBox.insets = new Insets(0, 0, 0, 0);
		gbc_deviceNameBox.gridx = gridX;
		gbc_deviceNameBox.gridy = gridY;
		leftPanel.add(deviceNameBox,gbc_deviceNameBox);
		
		gridX++;
		
		JLabel deviceNameLabel = new JLabel("设备名称：");
		GridBagConstraints gbc_deviceNameLabel = new GridBagConstraints();
		gbc_deviceNameLabel.fill = GridBagConstraints.BOTH;
		gbc_deviceNameLabel.insets = new Insets(0, 0, 0, 0);
		gbc_deviceNameLabel.gridx = gridX;
		gbc_deviceNameLabel.gridy = gridY;
		leftPanel.add(deviceNameLabel,gbc_deviceNameLabel);
		
		gridX++;
		
		JComboBox<String> deviceNameComboBox = new JComboBox<>();
		GridBagConstraints gbc_deviceNameComboBox = new GridBagConstraints();
		gbc_deviceNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_deviceNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_deviceNameComboBox.gridx = gridX;
		gbc_deviceNameComboBox.gridy = gridY;
		leftPanel.add(deviceNameComboBox,gbc_deviceNameComboBox);
		
		gridX=0;
		gridY++;
		
		classBox = new JCheckBox();
		GridBagConstraints gbc_classBox = new GridBagConstraints();
		gbc_classBox.fill = GridBagConstraints.EAST;
		gbc_classBox.insets = new Insets(0, 0, 0, 0);
		gbc_classBox.gridx = gridX;
		gbc_classBox.gridy = gridY;
		leftPanel.add(classBox,gbc_classBox);
		
		gridX++;
		
		JLabel classLabel = new JLabel("班别：");
		GridBagConstraints gbc_classLabel = new GridBagConstraints();
		gbc_classLabel.fill = GridBagConstraints.BOTH;
		gbc_classLabel.insets = new Insets(0, 0, 0, 0);
		gbc_classLabel.gridx = gridX;
		gbc_classLabel.gridy = gridY;
		leftPanel.add(classLabel,gbc_classLabel);
		
		gridX++;
		
		JComboBox<String> classComboBox = new JComboBox<>();
		GridBagConstraints gbc_classComboBox = new GridBagConstraints();
		gbc_classComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_classComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_classComboBox.gridx = gridX;
		gbc_classComboBox.gridy = gridY;
		leftPanel.add(classComboBox,gbc_classComboBox);
		
		gridX=0;
		gridY++;
		
		teamNameBox = new JCheckBox();
		GridBagConstraints gbc_teamNameBox = new GridBagConstraints();
		gbc_teamNameBox.fill = GridBagConstraints.EAST;
		gbc_teamNameBox.insets = new Insets(0, 0, 0, 0);
		gbc_teamNameBox.gridx = gridX;
		gbc_teamNameBox.gridy = gridY;
		leftPanel.add(teamNameBox,gbc_teamNameBox);
		
		gridX++;
		
		JLabel teamNameLabel = new JLabel("班组名称：");
		GridBagConstraints gbc_teamNameLabel = new GridBagConstraints();
		gbc_teamNameLabel.fill = GridBagConstraints.BOTH;
		gbc_teamNameLabel.insets = new Insets(0, 0, 0, 0);
		gbc_teamNameLabel.gridx = gridX;
		gbc_teamNameLabel.gridy = gridY;
		leftPanel.add(teamNameLabel,gbc_teamNameLabel);
		
		gridX++;
		
		JComboBox<String> teamNameComboBox = new JComboBox<>();
		GridBagConstraints gbc_teamNameComboBox = new GridBagConstraints();
		gbc_teamNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_teamNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_teamNameComboBox.gridx = gridX;
		gbc_teamNameComboBox.gridy = gridY;
		leftPanel.add(teamNameComboBox,gbc_teamNameComboBox);
		
		gridX=0;
		gridY++;
		
		materialNameBox = new JCheckBox();
		GridBagConstraints gbc_materialNameBox = new GridBagConstraints();
		gbc_materialNameBox.fill = GridBagConstraints.EAST;
		gbc_materialNameBox.insets = new Insets(0, 0, 0, 0);
		gbc_materialNameBox.gridx = gridX;
		gbc_materialNameBox.gridy = gridY;
		leftPanel.add(materialNameBox,gbc_materialNameBox);
		
		gridX++;
		
		JLabel materialNameLabel = new JLabel("物料名称：");
		GridBagConstraints gbc_materialNameLabel = new GridBagConstraints();
		gbc_materialNameLabel.fill = GridBagConstraints.BOTH;
		gbc_materialNameLabel.insets = new Insets(0, 0, 0, 0);
		gbc_materialNameLabel.gridx = gridX;
		gbc_materialNameLabel.gridy = gridY;
		leftPanel.add(materialNameLabel,gbc_materialNameLabel);
		
		gridX++;
		
		JComboBox<String> materialNameComboBox = new JComboBox<>();
		GridBagConstraints gbc_materialNameComboBox = new GridBagConstraints();
		gbc_materialNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_materialNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_materialNameComboBox.gridx = gridX;
		gbc_materialNameComboBox.gridy = gridY;
		leftPanel.add(materialNameComboBox,gbc_materialNameComboBox);
		
		gridX=0;
		gridY++;
		
		materialSpecificationBox = new JCheckBox();
		GridBagConstraints gbc_materialSpecificationBox = new GridBagConstraints();
		gbc_materialSpecificationBox.fill = GridBagConstraints.EAST;
		gbc_materialSpecificationBox.insets = new Insets(0, 0, 0, 0);
		gbc_materialSpecificationBox.gridx = gridX;
		gbc_materialSpecificationBox.gridy = gridY;
		leftPanel.add(materialSpecificationBox,gbc_materialSpecificationBox);
		
		gridX++;
		
		JLabel materialSpecificationLabel = new JLabel("物料规格：");
		GridBagConstraints gbc_materialSpecificationLabel = new GridBagConstraints();
		gbc_materialSpecificationLabel.fill = GridBagConstraints.BOTH;
		gbc_materialSpecificationLabel.insets = new Insets(0, 0, 0, 0);
		gbc_materialSpecificationLabel.gridx = gridX;
		gbc_materialSpecificationLabel.gridy = gridY;
		leftPanel.add(materialSpecificationLabel,gbc_materialSpecificationLabel);
		
		gridX++;
		
		JComboBox<String> materialSpecificationComboBox = new JComboBox<>();
		GridBagConstraints gbc_materialSpecificationComboBox = new GridBagConstraints();
		gbc_materialSpecificationComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_materialSpecificationComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_materialSpecificationComboBox.gridx = gridX;
		gbc_materialSpecificationComboBox.gridy = gridY;
		leftPanel.add(materialSpecificationComboBox,gbc_materialSpecificationComboBox);
		
		gridX=0;
		gridY++;
		
		operatorBox = new JCheckBox();
		GridBagConstraints gbc_operatorBox = new GridBagConstraints();
		gbc_operatorBox.fill = GridBagConstraints.EAST;
		gbc_operatorBox.insets = new Insets(0, 0, 0, 0);
		gbc_operatorBox.gridx = gridX;
		gbc_operatorBox.gridy = gridY;
		leftPanel.add(operatorBox,gbc_operatorBox);
		
		gridX++;
		
		JLabel operatorLabel = new JLabel("操作员：");
		GridBagConstraints gbc_operatorLabel = new GridBagConstraints();
		gbc_operatorLabel.fill = GridBagConstraints.BOTH;
		gbc_operatorLabel.insets = new Insets(0, 0, 0, 0);
		gbc_operatorLabel.gridx = gridX;
		gbc_operatorLabel.gridy = gridY;
		leftPanel.add(operatorLabel,gbc_operatorLabel);
		
		gridX++;
		
		JComboBox<String> operatorComboBox = new JComboBox<>();
		GridBagConstraints gbc_operatorComboBox = new GridBagConstraints();
		gbc_operatorComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_operatorComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_operatorComboBox.gridx = gridX;
		gbc_operatorComboBox.gridy = gridY;
		leftPanel.add(operatorComboBox,gbc_operatorComboBox);
		
		gridX=2;
		gridY++;
		
		weightDetailExportBtn = new JButton("生成报表并导出Excel");
		weightDetailExportBtn.addActionListener(this);
		GridBagConstraints gbc_weightDetailExportBtn = new GridBagConstraints();
		gbc_weightDetailExportBtn.fill = GridBagConstraints.HORIZONTAL;
		//gbc_exportExcelBtn.gridwidth = 2;
		gbc_weightDetailExportBtn.insets = new Insets(0, 0, 0, 5);
		gbc_weightDetailExportBtn.gridx = gridX;
		gbc_weightDetailExportBtn.gridy = gridY;
		leftPanel.add(weightDetailExportBtn,gbc_weightDetailExportBtn);
		
		return leftPanel;
	}
	
	//进出消耗数据汇总表
	private JPanel createInOutSummaryPanel()
	{
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 50, 200};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50, 50, 350};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		leftPanel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		produceDateBox = new JCheckBox();
		GridBagConstraints gbc_produceDateBox = new GridBagConstraints();
		gbc_produceDateBox.fill = GridBagConstraints.EAST;
		gbc_produceDateBox.insets = new Insets(0, 0, 0, 0);
		gbc_produceDateBox.gridx = gridX;
		gbc_produceDateBox.gridy = gridY;
		leftPanel.add(produceDateBox,gbc_produceDateBox);
		
		gridX++;
		
		JLabel produceDateLabel = new JLabel("生产日期：");
		GridBagConstraints gbc_produceDateLabel = new GridBagConstraints();
		gbc_produceDateLabel.fill = GridBagConstraints.BOTH;
		gbc_produceDateLabel.insets = new Insets(0, 0, 0, 0);
		gbc_produceDateLabel.gridx = gridX;
		gbc_produceDateLabel.gridy = gridY;
		leftPanel.add(produceDateLabel,gbc_produceDateLabel);
		
		gridX++;
		
		JTextField produceDateTextField = new JDateChooserTextField();
		GridBagConstraints gbc_produceDateTextField = new GridBagConstraints();
		gbc_produceDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_produceDateTextField.insets = new Insets(0, 0, 0, 5);
		gbc_produceDateTextField.gridx = gridX;
		gbc_produceDateTextField.gridy = gridY;
		leftPanel.add(produceDateTextField,gbc_produceDateTextField);
		
		gridX=0;
		gridY++;
		
		unitNameBox = new JCheckBox();
		GridBagConstraints gbc_unitNameBox = new GridBagConstraints();
		gbc_unitNameBox.fill = GridBagConstraints.EAST;
		gbc_unitNameBox.insets = new Insets(0, 0, 0, 0);
		gbc_unitNameBox.gridx = gridX;
		gbc_unitNameBox.gridy = gridY;
		leftPanel.add(unitNameBox,gbc_unitNameBox);
		
		gridX++;
		
		JLabel unitNameLabel = new JLabel("机组名称：");
		GridBagConstraints gbc_unitNameLabel = new GridBagConstraints();
		gbc_unitNameLabel.fill = GridBagConstraints.BOTH;
		gbc_unitNameLabel.insets = new Insets(0, 0, 0, 0);
		gbc_unitNameLabel.gridx = gridX;
		gbc_unitNameLabel.gridy = gridY;
		leftPanel.add(unitNameLabel,gbc_unitNameLabel);
		
		gridX++;
		
		JComboBox<String> unitNameComboBox = new JComboBox<>();
		GridBagConstraints gbc_unitNameComboBox = new GridBagConstraints();
		gbc_unitNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_unitNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_unitNameComboBox.gridx = gridX;
		gbc_unitNameComboBox.gridy = gridY;
		leftPanel.add(unitNameComboBox,gbc_unitNameComboBox);
		
		gridX=0;
		gridY++;
		
		classBox = new JCheckBox();
		GridBagConstraints gbc_classBox = new GridBagConstraints();
		gbc_classBox.fill = GridBagConstraints.EAST;
		gbc_classBox.insets = new Insets(0, 0, 0, 0);
		gbc_classBox.gridx = gridX;
		gbc_classBox.gridy = gridY;
		leftPanel.add(classBox,gbc_classBox);
		
		gridX++;
		
		JLabel classLabel = new JLabel("班别：");
		GridBagConstraints gbc_classLabel = new GridBagConstraints();
		gbc_classLabel.fill = GridBagConstraints.BOTH;
		gbc_classLabel.insets = new Insets(0, 0, 0, 0);
		gbc_classLabel.gridx = gridX;
		gbc_classLabel.gridy = gridY;
		leftPanel.add(classLabel,gbc_classLabel);
		
		gridX++;
		
		JComboBox<String> classComboBox = new JComboBox<>();
		GridBagConstraints gbc_classComboBox = new GridBagConstraints();
		gbc_classComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_classComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_classComboBox.gridx = gridX;
		gbc_classComboBox.gridy = gridY;
		leftPanel.add(classComboBox,gbc_classComboBox);
		
		gridX=0;
		gridY++;
		
		teamNameBox = new JCheckBox();
		GridBagConstraints gbc_teamNameBox = new GridBagConstraints();
		gbc_teamNameBox.fill = GridBagConstraints.EAST;
		gbc_teamNameBox.insets = new Insets(0, 0, 0, 0);
		gbc_teamNameBox.gridx = gridX;
		gbc_teamNameBox.gridy = gridY;
		leftPanel.add(teamNameBox,gbc_teamNameBox);
		
		gridX++;
		
		JLabel teamNameLabel = new JLabel("班组名称：");
		GridBagConstraints gbc_teamNameLabel = new GridBagConstraints();
		gbc_teamNameLabel.fill = GridBagConstraints.BOTH;
		gbc_teamNameLabel.insets = new Insets(0, 0, 0, 0);
		gbc_teamNameLabel.gridx = gridX;
		gbc_teamNameLabel.gridy = gridY;
		leftPanel.add(teamNameLabel,gbc_teamNameLabel);
		
		gridX++;
		
		JComboBox<String> teamNameComboBox = new JComboBox<>();
		GridBagConstraints gbc_teamNameComboBox = new GridBagConstraints();
		gbc_teamNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_teamNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_teamNameComboBox.gridx = gridX;
		gbc_teamNameComboBox.gridy = gridY;
		leftPanel.add(teamNameComboBox,gbc_teamNameComboBox);
		
		gridX=2;
		gridY++;
		
		weightSummaryExportBtn = new JButton("生成报表并导出Excel");
		weightSummaryExportBtn.addActionListener(this);
		GridBagConstraints gbc_weightSummaryExportBtn = new GridBagConstraints();
		gbc_weightSummaryExportBtn.fill = GridBagConstraints.HORIZONTAL;
		//gbc_exportExcelBtn.gridwidth = 2;
		gbc_weightSummaryExportBtn.insets = new Insets(0, 0, 0, 5);
		gbc_weightSummaryExportBtn.gridx = gridX;
		gbc_weightSummaryExportBtn.gridy = gridY;
		leftPanel.add(weightSummaryExportBtn,gbc_weightSummaryExportBtn);
		
		return leftPanel;
	}
	
	
	private JPanel createRightPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		return panel;
	}
	
	private class EventListener implements SWSEventListener
	{
		public void lmsTransferEvent(SWSEvent event)
		{
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();
			String nvramStr = null;
			
			int sensorID = 0;

			if (SwsParameter.SETTING_EVENT.equals(eventType))
			{
				nvramStr = (String) eventExtra.get(SwsParameter.EXTRA_SETTING_NVRAM);
				
				if(nvramStr == null)
					return;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("称重数据明细表"))
		{
			queryType = Type.WEIGHT_DETAIL;
			leftPanel.removeAll();
			leftPanel = createWeightDetailPanel();
			leftPanel.revalidate();
		}
		else if(e.getActionCommand().equals("称重数据汇总表"))
		{
			queryType = Type.WEIGHT_SUMMARY;
			leftPanel.removeAll();
			leftPanel = createWeightSummaryPanel();
			leftPanel.revalidate();
		}
		else if(e.getActionCommand().equals("进出消耗数据明细表"))
		{
			queryType = Type.INOUT_DETAIL;
			leftPanel.removeAll();
			leftPanel = createInOutDetailPanel();
			leftPanel.revalidate();
		}
		else if(e.getActionCommand().equals("进出消耗数据汇总表"))
		{
			queryType = Type.INOUT_SUMMARY;
			leftPanel.removeAll();
			leftPanel = createInOutSummaryPanel();
			leftPanel.revalidate();
		}
		
	}
	
	private enum Type{
		WEIGHT_DETAIL,
		WEIGHT_SUMMARY,
		INOUT_DETAIL,
		INOUT_SUMMARY,
	}
}
