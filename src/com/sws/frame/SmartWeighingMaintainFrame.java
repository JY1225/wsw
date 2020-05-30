package com.sws.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import com.sws.base.SwsMessageDialog;
import com.sws.base.SwsParameter;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJLabel;
import com.sws.components.SwsJTextField;
import com.sws.database.SwsDeleteDBUtils;
import com.sws.database.SwsQueryDBUtils;
import com.sws.database.SwsSaveDBUtils;
import com.sws.database.SwsUpdateDBUtils;
import com.sws.entity.Material;
import com.sws.entity.Team;
import com.sws.components.JDateChooserTextField;
import com.sws.components.JLabelComboBox;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.utils.CommonUtils;

/**
 * 数据维护界面
 * @author Ben
 *
 */
public class SmartWeighingMaintainFrame implements ActionListener{
	
	private JTextField produceDate;
	private JPanel leftPanel = new JPanel();
	private Type maintainType;
	private JButton materialMaintainBtn;
	private JButton teamMaintainBtn;
	private JButton customerMaintainBtn;
	private JButton dataDefaultBtn;
	private JButton exportMaterialBtn;
	private JButton exportTeamBtn;
	private JButton addBtn;
	private JButton updateBtn;
	private JButton deleteBtn;
	
	//物料信息
	private JTextField materialCodeTextField;
	private JTextField materialNameTextField;
	private JTextField materialAliasTextField;
	private JTextField materialSpecifitionTextField;
	private JTextField outerDiameterTextField;
	private JTextField wallThicknessTextField;
	private JTextField materialLengthTextField;
	private JTextField dxxsTextField;//镀锌系数
	private JTextField zzTextField;//支重
	private JTextField everyZsTextField;//每件支数
	private JTextField pieceWeightTextField;//件重
	private JTextField minimalWeightTextField;//最小重量
	private JTextField maximumWeightTextField;//最大重量
	private JLabelComboBox isMatchComboBox;
	
	private JTextField teamCodeTextField;
	private JTextField teamNameTextField;
	private JTextField teamAliasTextField;
	private JTextField teamClassTextField;
	private JTextField produceUnitTextField;
	
	private JPanel tablePanel;
	//private int id;
	
	private enum Type {
		MATERIAL_MAINTAIN,//物料维护
		TEAM_MAINTAIN, //班组维护
		CUSTOMER_MAINTAIN, //客户维护
	}
	
	public SmartWeighingMaintainFrame() 
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
		gridBagLayout.columnWidths = new int[]{100, 100, 100, 100, 100};
		gridBagLayout.rowHeights = new int[]{100};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "物料信息维护", "物料信息维护", false, false, SwsParameter.bNvramMaterialsMessageMaintain);
		materialMaintainBtn = new SwsJButton(panel, gridX, gridY, "物料信息维护", this);
		
		gridX++;
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "班组信息维护", "班组信息维护", false, false, SwsParameter.bNvramTeamMessageMaintain);
		teamMaintainBtn = new SwsJButton(panel, gridX, gridY, "班组信息维护", this);
		
		gridX++;
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "客户信息维护", "客户信息维护", false, false, SwsParameter.bNvramCustomerMessageMaintain);
		//customerMaintainBtn = new SwsJButton(panel, gridX, gridY, "客户信息维护", this);
		exportMaterialBtn = new SwsJButton(panel,gridX, gridY, "导入物料信息", this);
		
		gridX++;
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "客户信息维护", "客户信息维护", false, false, SwsParameter.bNvramCustomerMessageMaintain);
		//customerMaintainBtn = new SwsJButton(panel, gridX, gridY, "客户信息维护", this);
		exportTeamBtn = new SwsJButton(panel,gridX, gridY, "导入班组信息", this);
		
		gridX++;
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "数据项默认值", "数据项默认值", false, false, SwsParameter.bNvramDataitemDefaultValue);
		dataDefaultBtn = new SwsJButton(panel, gridX, gridY, "数据项默认值", this);
		
		//gridX++;
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "导入物料信息", "导入物料信息", false, false, SwsParameter.bnvramExportMaterialsMessage);
		
		
		return panel;
	}
	
	private JPanel createMaintainPanel()
	{
		JPanel panel = new JPanel();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300, 700};
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
		
		//JPanel rightPanel = createRightPanel();
		tablePanel = createTablePanel();
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.fill = GridBagConstraints.BOTH;
		gbc_rightPanel.insets = new Insets(0, 0, 0, 0);
		gbc_rightPanel.gridx = gridX;
		gbc_rightPanel.gridy = gridY;
		panel.add(tablePanel,gbc_rightPanel);
		
		return panel;
	}
	
	//物料信息左边面板
	private JPanel createMaterialsLeftPanel()
	{
		//JPanel panel = new JPanel();
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100};
		gridBagLayout.rowHeights = new int[]{35, 35, 35,35, 35, 35,35, 35, 35,35, 35, 35,35, 35, 35, 35};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0, Double.MIN_VALUE};
		leftPanel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "货物代码：", true, false, false, SwsParameter.iNvramCargoCode);
		new SwsJLabel(leftPanel, gridX, gridY, "货物代码：");
		
		gridX++;
		
		materialCodeTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "货物别名：", true, false, false, SwsParameter.sNvramCargoAlias);
		new SwsJLabel(leftPanel, gridX, gridY, "货物名称：");
		
		gridX++;
		
		materialNameTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "货物名称：", true, false, false, SwsParameter.sNvramCargoName);
		new SwsJLabel(leftPanel, gridX, gridY, "货物别名：");
		
		gridX++;
		
		materialAliasTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "货物规格：", true, false, false, SwsParameter.sNvramCargoSpecifications);
		new SwsJLabel(leftPanel, gridX, gridY, "货物规格：");
		
		gridX++;
		
		materialSpecifitionTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "外径：", true, false, false, SwsParameter.fNvramOuterDiameter);
		new SwsJLabel(leftPanel, gridX, gridY, "外径：");
		
		gridX++;
		
		outerDiameterTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "壁厚：", true, false, false, SwsParameter.fNvramWallThickness);
		new SwsJLabel(leftPanel, gridX, gridY, "壁厚：");
		
		gridX++;
		
		wallThicknessTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "长度：", true, false, false, SwsParameter.fNvramCargoLength);
		new SwsJLabel(leftPanel, gridX, gridY, "长度：");
		
		gridX++;
		
		materialLengthTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "镀锌系数：", true, false, false, SwsParameter.fNvramGalvanizationCoefficient);
		new SwsJLabel(leftPanel, gridX, gridY, "镀锌系数：");
		
		gridX++;
		
		dxxsTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "支重：", true, false, false, SwsParameter.fNvramCargoWeight);
		new SwsJLabel(leftPanel, gridX, gridY, "支重：");
		
		gridX++;
		
		zzTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "每件支数：", true, false, false, SwsParameter.iNvramEveryCount);
		new SwsJLabel(leftPanel, gridX, gridY, "每件支数：");
		
		gridX++;
		
		everyZsTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "件重：", true, false, false, SwsParameter.iNvramPieceWeight);
		new SwsJLabel(leftPanel, gridX, gridY, "件重：");
		
		gridX++;
		
		pieceWeightTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);

		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "最小重量：", true, false, false, SwsParameter.iNvramMinimalWeight);
		new SwsJLabel(leftPanel, gridX, gridY, "最小重量：");
		
		gridX++;
		
		minimalWeightTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "最大重量：", true, false, false, SwsParameter.iNvramMaximumWeight);
		new SwsJLabel(leftPanel, gridX, gridY, "最大重量：");
		
		gridX++;
		
		maximumWeightTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		JLabel produceDateLabel = new JLabel("生产日期：");
		GridBagConstraints gbc_produceDateLabel = new GridBagConstraints();
		gbc_produceDateLabel.fill = GridBagConstraints.BOTH;
		gbc_produceDateLabel.insets = new Insets(0, 5, 5, 5);
		gbc_produceDateLabel.gridx = gridX;
		gbc_produceDateLabel.gridy = gridY;
		leftPanel.add(produceDateLabel, gbc_produceDateLabel);
		
		gridX++;
		
		produceDate = new JDateChooserTextField();
		produceDate.setFont(new Font("宋体", Font.BOLD, 14));
		GridBagConstraints gbc_produceDate = new GridBagConstraints();
		gbc_produceDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_produceDate.insets = new Insets(0, 0, 5, 5);
		gbc_produceDate.gridwidth = 2;
		gbc_produceDate.gridx = gridX;
		gbc_produceDate.gridy = gridY;
		leftPanel.add(produceDate, gbc_produceDate);
		
		gridX=0;
		gridY++;
		
		isMatchComboBox = new JLabelComboBox(leftPanel, gridX, gridY, 1, 2, "区间匹配：", false, SwsParameter.eNvramMatchIntervalType, SwsParameter.enumMatchIntervalType);
		
		gridX=0;
		gridY++;
		
		addBtn = new SwsJButton(leftPanel, gridX, gridY, "添加", this);
		
		gridX++;
		
		updateBtn = new SwsJButton(leftPanel, gridX, gridY, "修改", this);
		
		gridX++;
		
		deleteBtn = new SwsJButton(leftPanel, gridX, gridY, "删除", this);
		
		return leftPanel;
	}
	
	//班组信息维护左边面板
	private JPanel createTeamLeftPanel()
	{
		//JPanel panel = new JPanel();
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100};
		gridBagLayout.rowHeights = new int[]{35, 35, 35,35, 35, 35, 35, 35,35, 35, 35, 35, 35,35, 35, 35};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0, Double.MIN_VALUE};
		leftPanel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "班组代号：", true, true, false, SwsParameter.iNvramTeamCode);
		new SwsJLabel(leftPanel, gridX, gridY, "班组代码：");
		
		gridX++;
		
		teamCodeTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(leftPanel, gridX, gridY, "班组名称：");
		
		gridX++;
		
		teamNameTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(leftPanel, gridX, gridY, "班组别名：");
		
		gridX++;
		
		teamAliasTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(leftPanel, gridX, gridY, "班      别：");
		
		gridX++;
		
		teamClassTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(leftPanel, gridX, gridY, "生产机组：");
		
		gridX++;
		
		produceUnitTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		addBtn = new SwsJButton(leftPanel, gridX, gridY, "添加", this);
		
		gridX++;
		
		updateBtn = new SwsJButton(leftPanel, gridX, gridY, "修改", this);
		
		gridX++;
		
		deleteBtn = new SwsJButton(leftPanel, gridX, gridY, "删除", this);
		
		return leftPanel;
	} 
	
	//客户信息维护左边面板
	private JPanel createCustomerLeftPanel()
	{
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100};
		gridBagLayout.rowHeights = new int[]{35, 35, 35,35, 35, 35, 35, 35,35, 35, 35, 35, 35,35, 35, 35};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0, Double.MIN_VALUE};
		leftPanel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		new SwsJLabel(leftPanel, gridX, gridY, 1, "客户名称");
		
		gridX++;
		
		new SwsJTextField(leftPanel, GridBagConstraints.HORIZONTAL, gridX, gridY, 2, 1);
		
		gridX=0;
		gridY++;
		
		new SwsJButton(leftPanel, gridX, gridY, "添加", this);
		
		gridX++;
		
		new SwsJButton(leftPanel, gridX, gridY, "修改", this);
		
		return leftPanel;
	}
	
	private JPanel createTablePanel()
	{
		tablePanel = new JPanel();
		tablePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{700};
		gridBagLayout.rowHeights = new int[]{600};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		tablePanel.setLayout(gridBagLayout);

		return tablePanel;
	}
	
	private void displayTable(String[] titles, Object[][] data, int[] columnWidth)
	{
		tablePanel.removeAll();
		tablePanel.revalidate();
		System.out.println("显示列表");
		JTable table = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel(data, titles)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		table.setModel(tableModel);
		
		for(int i=0; i<columnWidth.length; i++)
		{
			int width = columnWidth[i];
			//隐藏ID列
			TableColumnModel tcm = table.getColumnModel();  
			TableColumn tc = tcm.getColumn(i);
			tc.setMinWidth(width);
			tc.setMaxWidth(width);
			tc.setPreferredWidth(width);
		}
		
		//-------设置隔行设置背景颜色-----------------------------------------------------------------
		DefaultTableCellRenderer ter = new DefaultTableCellRenderer()
		{	
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
			{
				if (row % 2 == 0)
				{
					setBackground(Color.white);
				}
				else if (row % 2 == 1)
				{
					setBackground(new Color(206, 231, 255));
				}
				
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		
		//单元格内容居中
		ter.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, ter);
		
        //被选中行的背景颜色
		table.setSelectionBackground(Color.GRAY);
		//设置行高
		table.setRowHeight(30);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		tablePanel.add(scrollPane,gbc_scrollPane);
		tablePanel.revalidate();
		
		//注册双击某行的响应事件
		table.addMouseListener(new MouseAdapter()
		{ 
			public void mouseClicked(MouseEvent e) 
			{
                //实现双击 
				if(e.getClickCount() == 2)
				{ 
					int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 

					int id =(int) ((JTable)e.getSource()).getValueAt(row,0); //获得点击单元格数据 

					if(maintainType == Type.TEAM_MAINTAIN)
					{
						String teamCode = (String) ((JTable)e.getSource()).getValueAt(row,2); //获得点击单元格数据 
						//显示班组详细信息
						Team team = SwsQueryDBUtils.queryTeamByCode(teamCode);
						if(team != null)
						{
							teamCodeTextField.setText(team.getTeamCode());
							teamNameTextField.setText(team.getTeamName());
							teamAliasTextField.setText(team.getTeamAlias());
							teamClassTextField.setText(team.getTeamClass());
							produceUnitTextField.setText(team.getProduceUnit());
						}
					}
					else if(maintainType == Type.MATERIAL_MAINTAIN)
					{
						int materialCode = (int) ((JTable)e.getSource()).getValueAt(row,2); //获得点击单元格数据 
						//显示班组详细信息
						Material material = SwsQueryDBUtils.queryMaterialByCode(materialCode);
						if(material != null)
						{
							materialCodeTextField.setText(material.getMaterialCode()+"");
							materialNameTextField.setText(material.getMaterialName());
							materialAliasTextField.setText(material.getMaterialAlias());
							materialSpecifitionTextField.setText(material.getMaterialDimension());
							outerDiameterTextField.setText(material.getOuterDiameter()+"");
							wallThicknessTextField.setText(material.getWallThickness()+"");
							materialLengthTextField.setText(material.getLength()+"");
							dxxsTextField.setText(material.getDxxs()+"");
							zzTextField.setText(material.getZz()+"");
							everyZsTextField.setText(material.getZs()+"");
							pieceWeightTextField.setText(material.getPieceWeight()+"");
							minimalWeightTextField.setText(material.getMinWeight()+"");
							maximumWeightTextField.setText(material.getMaxWeight()+"");
							produceDate.setText(material.getProduceDate());
							String isMatch = material.getIsMatch();
							if("否".equals(isMatch))
							{
								isMatchComboBox.autoSetSelectedItem(SwsParameter.enumMatchIntervalType.MATCH_NO);
							}
							else
							{
								isMatchComboBox.autoSetSelectedItem(SwsParameter.enumMatchIntervalType.MATCH_YES);
							}
						}
					}
				} 
			}
		});
	}
	
	private class EventListener implements SWSEventListener
	{
		public void lmsTransferEvent(SWSEvent event)
		{
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();
			String nvramStr = null;
			
			int sensorID = 0;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(materialMaintainBtn))
		{
			//物料信息维护
			System.out.println("运行到这里，物料信息维护");
			maintainType = Type.MATERIAL_MAINTAIN;
			//物料信息维护
			leftPanel.removeAll();
			leftPanel = createMaterialsLeftPanel();
			leftPanel.revalidate();
			
			showTable();
		}
		else if(e.getSource().equals(teamMaintainBtn))
		{
			System.out.println("运行到这里，班组信息维护");
			maintainType = Type.TEAM_MAINTAIN;
			//班组信息维护
			leftPanel.removeAll();
			leftPanel = createTeamLeftPanel();
			leftPanel.revalidate();
			//显示右边列表
			showTable();
		}
		else if(e.getSource().equals(exportTeamBtn))
		{
			/*maintainType = Type.CUSTOMER_MAINTAIN;
			//客户信息维护
			leftPanel.removeAll();
			leftPanel = createCustomerLeftPanel();
			leftPanel.revalidate();*/
			System.out.println("导入班组信息");
			//导入班组信息
			SmartWeighingExportTeamFrame.showFrame();
			
			
		}
		else if(e.getSource().equals(dataDefaultBtn))
		{
			System.out.println("数据默认项");
		}
		else if(e.getSource().equals(exportMaterialBtn))
		{
			System.out.println("导入物料信息");
			//导入物料信息
			SmartWeighingExportMaterialFrame.showFrame();
		}
		else if(e.getSource().equals(addBtn))
		{
			if(maintainType == Type.MATERIAL_MAINTAIN)
			{
				System.out.println("添加物料信息");
				addMaterial();
				showTable();
				clear();
			}
			else if(maintainType == Type.TEAM_MAINTAIN)
			{
				System.out.println("添加班组信息");
				addTeam();
				showTable();
				clear();
			}
		}
		else if(e.getSource().equals(updateBtn))
		{
			if(maintainType == Type.MATERIAL_MAINTAIN)
			{
				System.out.println("修改物料信息");
				updateMaterial();
				showTable();
				clear();
			}
			else if(maintainType == Type.TEAM_MAINTAIN)
			{
				System.out.println("修改班组信息");
				updateTeam();
				showTable();
				clear();
			}
		}
		else if(e.getSource().equals(deleteBtn))
		{
			if(maintainType == Type.MATERIAL_MAINTAIN)
			{
				System.out.println("删除物料信息");
				deleteMaterial();
				showTable();
				clear();
			}
			else if(maintainType == Type.TEAM_MAINTAIN)
			{
				System.out.println("删除班组信息");
				deleteTeam();
				showTable();
				clear();
			}
		}
	}
	
	/**
	 * 清空
	 */
	private void clear()
	{
		if(maintainType == Type.MATERIAL_MAINTAIN)
		{
			materialCodeTextField.setText("");
			materialNameTextField.setText("");
			materialAliasTextField.setText("");
			materialSpecifitionTextField.setText("");
			outerDiameterTextField.setText("");
			wallThicknessTextField.setText("");
			materialLengthTextField.setText("");
			dxxsTextField.setText("");
			zzTextField.setText("");
			everyZsTextField.setText("");
			pieceWeightTextField.setText("");
			minimalWeightTextField.setText("");
			maximumWeightTextField.setText("");
		}
		else if(maintainType == Type.TEAM_MAINTAIN)
		{
			teamCodeTextField.setText("");
			teamNameTextField.setText("");
			teamAliasTextField.setText("");
			teamClassTextField.setText("");
			produceUnitTextField.setText("");
		}
	}
	
	private void showTable()
	{
		if(maintainType == Type.TEAM_MAINTAIN)
		{
			//班组信息
			List<Team> teamList = SwsQueryDBUtils.queryAllTeam();
			if(teamList.size() > 0)
			{
				//标题
				String[] title = new String[]{"ID", "序号", "班组代码", "班组名称", "班组别名", "班别", "生产机组", "修改时间"};
				//生成表格
				Object[][] data = new Object[teamList.size()][title.length];
				
				int[] columnWidth = new int[]{0, 100, 100, 100, 100, 100, 100, 200};
				
				for(int i=0; i<teamList.size(); i++)
				{
					Team team = teamList.get(i);
					data[i][0] = team.getTeamId();
					data[i][1] = (i+1);
					data[i][2] = team.getTeamCode();
					data[i][3] = team.getTeamName();
					data[i][4] = team.getTeamAlias();
					data[i][5] = team.getTeamClass();
					data[i][6] = team.getProduceUnit();
					data[i][7] = team.getModifyTime();
				}
				
				displayTable(title, data, columnWidth);
			}
		}
		else if(maintainType == Type.MATERIAL_MAINTAIN)//物料维护
		{
			//班组信息
			List<Material> materialList = SwsQueryDBUtils.queryAllMaterial();
			//标题
			String[] title = new String[]{"ID", "序号", "物料代码", "物料名称", "物料别名", "物料规格", "物料寸别", "外径", "壁厚", "长度", "镀锌系数", "支重", "每件支数", "件重", "最小重量", "最大重量", "是否匹配", "生产日期", "修改时间"};
			//生成表格
			Object[][] data = new Object[materialList.size()][title.length];
			
			int[] columnWidth = new int[]{0, 100, 100, 100, 150, 0, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 200, 200};
			
			if(materialList.size() > 0)
			{
				for(int i=0; i<materialList.size(); i++)
				{
					Material material = materialList.get(i);
					data[i][0] = material.getMaterialCode();
					data[i][1] = (i+1);
					data[i][2] = material.getMaterialCode();
					data[i][3] = material.getMaterialName();
					data[i][4] = material.getMaterialAlias();
					data[i][5] = material.getMaterialSpecifition();
					data[i][6] = material.getMaterialDimension();
					data[i][7] = material.getOuterDiameter();
					data[i][8] = material.getWallThickness();
					data[i][9] = material.getLength();
					data[i][10] = material.getDxxs();
					data[i][11] = material.getZz();
					data[i][12] = material.getZs();
					data[i][13] = material.getPieceWeight();
					data[i][14] = material.getMinWeight();
					data[i][15] = material.getMaxWeight();
					data[i][16] = material.getIsMatch();
					data[i][17] = material.getProduceDate();
					data[i][18] = material.getModifyTime();
				}
			}
			
			displayTable(title, data, columnWidth);
		}
	}
	
	//添加班组
	private void addMaterial()
	{
		if("".equals(materialCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("物料代码不能为空!");
			return;
		}
		if("".equals(materialNameTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("物料名称不能为空!");
			return;
		}
		if("".equals(materialAliasTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("物料别名别名不能为空!");
			return;
		}
		if("".equals(materialSpecifitionTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("物料规格不能为空！");
			return;
		}
		if("".equals(outerDiameterTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("外径不能为空！");
			return;
		}
		if("".equals(wallThicknessTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("壁厚不能为空！");
			return;
		}
		if("".equals(materialLengthTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("长度不能为空！");
			return;
		}
		if("".equals(dxxsTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("镀锌系数不能为空！");
			return;
		}
		if("".equals(zzTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("支重不能为空！");
			return;
		}
		if("".equals(everyZsTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("每件支数不能为空！");
			return;
		}
		if("".equals(pieceWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("件重不能为空！");
			return;
		}
		if("".equals(minimalWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("最小重量不能为空！");
			return;
		}
		if("".equals(maximumWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("最大重量不能为空！");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.materialExist(materialCodeTextField.getText().trim());
		if(bExist)
		{
			SwsMessageDialog.errorDialog("该物料代码已存在，请重新输入!");
		}
		else
		{
			Material material = new Material();
			material.setMaterialCode(Integer.parseInt(materialCodeTextField.getText().trim()));
			material.setMaterialName(materialNameTextField.getText().trim());
			material.setMaterialAlias(materialAliasTextField.getText().trim());
			//material.setMaterialSpecifition(materialSpecifitionTextField.getText().trim());
			material.setMaterialDimension(materialSpecifitionTextField.getText().trim());
			material.setOuterDiameter(Float.parseFloat(outerDiameterTextField.getText().trim()));
			material.setWallThickness(Float.parseFloat(wallThicknessTextField.getText().trim()));
			material.setLength(Float.parseFloat(materialLengthTextField.getText().trim()));
			material.setDxxs(Float.parseFloat(dxxsTextField.getText().trim()));
			material.setZz(Float.parseFloat(zzTextField.getText().trim()));
			material.setZs(Integer.parseInt(everyZsTextField.getText().trim()));
			material.setPieceWeight(Float.parseFloat(pieceWeightTextField.getText().trim()));
			material.setMinWeight(Float.parseFloat(minimalWeightTextField.getText().trim()));
			material.setMaxWeight(Float.parseFloat(maximumWeightTextField.getText().trim()));
			material.setProduceDate(produceDate.getText().trim());
			String isMatch = "MATCH_NO".equals(SwsParameter.enumMatchIntervalType.key)?"否":"是";
			material.setIsMatch(isMatch);
			material.setModifyTime(CommonUtils.getCurrentTime());
			
			boolean bAdd = SwsSaveDBUtils.addMaterial(material);
			if(bAdd)
			{
				SwsMessageDialog.infoDialog("添加物料信息成功!");
				SwsSaveDBUtils.saveLog("添加物料信息", "添加物料信息", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
			}
			else
			{
				SwsMessageDialog.errorDialog("添加物料信息失败!");
			}
		}
	}
	
	//添加班组
	private void addTeam()
	{
		if("".equals(teamCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("班组代码不能为空!");
			return;
		}
		if("".equals(teamNameTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("班组名称不能为空!");
			return;
		}
		if("".equals(teamAliasTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("班组别名不能为空!");
			return;
		}
		if("".equals(teamClassTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("班别不能为空！");
			return;
		}
		if("".equals(produceUnitTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("生产机组不能为空!");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.teamExist(teamCodeTextField.getText().trim());
		if(bExist)
		{
			SwsMessageDialog.errorDialog("该班组代码已存在，请重新输入!");
		}
		else
		{
			Team team = new Team();
			team.setTeamCode(teamCodeTextField.getText().trim());
			team.setTeamName(teamNameTextField.getText().trim());
			team.setTeamAlias(teamAliasTextField.getText().trim());
			team.setTeamClass(teamClassTextField.getText().trim());
			team.setProduceUnit(produceUnitTextField.getText().trim());
			team.setModifyTime(CommonUtils.getCurrentTime());
			boolean bAdd = SwsSaveDBUtils.addTeam(team);
			if(bAdd)
			{
				SwsMessageDialog.infoDialog("添加班组信息成功!");
				SwsSaveDBUtils.saveLog("添加班组信息", "添加班组信息", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
			}
			else
			{
				SwsMessageDialog.errorDialog("添加班组信息失败!");
			}
		}
	}
	
	//修改班组
	private void updateTeam()
	{
		if("".equals(teamCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("班组代码不能为空!");
			return;
		}
		if("".equals(teamNameTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("班组名称不能为空!");
			return;
		}
		if("".equals(teamAliasTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("班组别名不能为空!");
			return;
		}
		if("".equals(teamClassTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("班别不能为空！");
			return;
		}
		if("".equals(produceUnitTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("生产机组不能为空!");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.teamExist(teamCodeTextField.getText().trim());
		if(bExist)
		{
			int option = SwsMessageDialog.confirmDialog("确定修改"+teamNameTextField.getText().trim()+"的班组信息吗？", "修改班组信息提示");
			if(option == SwsMessageDialog.OK_OPTION)
			{
				Team team = new Team();
				team.setTeamCode(teamCodeTextField.getText().trim());
				team.setTeamName(teamNameTextField.getText().trim());
				team.setTeamAlias(teamAliasTextField.getText().trim());
				team.setTeamClass(teamClassTextField.getText().trim());
				team.setProduceUnit(produceUnitTextField.getText().trim());
				team.setModifyTime(CommonUtils.getCurrentTime());
				boolean bUpdate = SwsUpdateDBUtils.updateTeam(team);
				if(bUpdate)
				{
					SwsMessageDialog.infoDialog("修改班组信息成功!");
					SwsSaveDBUtils.saveLog("修改班组信息", "修改班组信息", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
				}
				else
				{
					SwsMessageDialog.errorDialog("修改班组信息失败!");
				}
			}
		}
		else
		{
			SwsMessageDialog.errorDialog("该班组代码不存在，请重新输入!");
		}
	}
	
	
	//修改物料
	private void updateMaterial()
	{
		if("".equals(materialCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("物料代码不能为空!");
			return;
		}
		if("".equals(materialNameTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("物料名称不能为空!");
			return;
		}
		if("".equals(materialAliasTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("物料别名别名不能为空!");
			return;
		}
		if("".equals(materialSpecifitionTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("物料规格不能为空！");
			return;
		}
		if("".endsWith(outerDiameterTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("外径不能为空！");
			return;
		}
		if("".endsWith(wallThicknessTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("壁厚不能为空！");
			return;
		}
		if("".endsWith(materialLengthTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("长度不能为空！");
			return;
		}
		if("".endsWith(dxxsTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("镀锌系数不能为空！");
			return;
		}
		if("".endsWith(zzTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("支重不能为空！");
			return;
		}
		if("".endsWith(everyZsTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("每件支数不能为空！");
			return;
		}
		if("".endsWith(pieceWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("件重不能为空！");
			return;
		}
		if("".endsWith(minimalWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("最小重量不能为空！");
			return;
		}
		if("".endsWith(maximumWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("最大重量不能为空！");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.materialExist(materialCodeTextField.getText().trim());
		if(bExist)
		{
			
			int option = SwsMessageDialog.confirmDialog("确定修改物料名称为"+materialNameTextField.getText().trim()+"的信息吗？", "修改物料信息提示");
			if(option == SwsMessageDialog.OK_OPTION)
			{
				Material material = new Material();
				material.setMaterialCode(Integer.parseInt(materialCodeTextField.getText().trim()));
				material.setMaterialName(materialNameTextField.getText().trim());
				material.setMaterialAlias(materialAliasTextField.getText().trim());
				material.setMaterialSpecifition(materialSpecifitionTextField.getText().trim());
				material.setMaterialDimension("6寸");
				material.setOuterDiameter(Float.parseFloat(outerDiameterTextField.getText().trim()));
				material.setWallThickness(Float.parseFloat(wallThicknessTextField.getText().trim()));
				material.setLength(Float.parseFloat(materialLengthTextField.getText().trim()));
				material.setDxxs(Float.parseFloat(dxxsTextField.getText().trim()));
				material.setZz(Float.parseFloat(zzTextField.getText().trim()));
				material.setZs(Integer.parseInt(everyZsTextField.getText().trim()));
				material.setPieceWeight(Float.parseFloat(pieceWeightTextField.getText().trim()));
				material.setMinWeight(Float.parseFloat(minimalWeightTextField.getText().trim()));
				material.setMaxWeight(Float.parseFloat(maximumWeightTextField.getText().trim()));
				material.setProduceDate(produceDate.getText().trim());
				String isMatch = "MATCH_NO".equals(SwsParameter.enumMatchIntervalType.key)?"否":"是";
				material.setIsMatch(isMatch);
				material.setModifyTime(CommonUtils.getCurrentTime());
				
				boolean bAdd = SwsUpdateDBUtils.updateMaterial(material);
				if(bAdd)
				{
					SwsMessageDialog.infoDialog("修改物料信息成功!");
					SwsSaveDBUtils.saveLog("修改物料信息", "修改物料信息", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
				}
				else
				{
					SwsMessageDialog.errorDialog("添加物料信息失败!");
				}
			}
		}
		else
		{
			SwsMessageDialog.errorDialog("该物料代码不存在，请重新输入!");
		}
	}
	
	//删除班组
	private void deleteTeam()
	{
		if("".equals(teamCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("班组代码不能为空!");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.teamExist(teamCodeTextField.getText().trim());
		
		if(bExist)
		{
			int option = SwsMessageDialog.confirmDialog("确定删除\" 班组代码为"+teamCodeTextField.getText().trim()+"\" 的班组信息吗？", "删除班组信息提示");
			if(option == SwsMessageDialog.OK_OPTION)
			{
				boolean bUpdate = SwsDeleteDBUtils.deleteTeam(teamCodeTextField.getText().trim());
				if(bUpdate)
				{
					SwsMessageDialog.infoDialog("删除班组信息成功!");
					SwsSaveDBUtils.saveLog("删除班组信息", "删除班组信息", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
				}
				else
				{
					SwsMessageDialog.errorDialog("删除班组信息失败!");
				}
			}
		}
		else
		{
			SwsMessageDialog.errorDialog("该班组代码不存在，请重新输入!");
		}
	}
	
	//删除班组
	private void deleteMaterial()
	{
		if("".equals(materialCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("物料代码不能为空!");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.materialExist(materialCodeTextField.getText().trim());
		
		if(bExist)
		{
			int option = SwsMessageDialog.confirmDialog("确定删除\" 物料代码为"+materialCodeTextField.getText().trim()+"\" 的物料信息吗？", "删除物料信息提示");
			if(option == SwsMessageDialog.OK_OPTION)
			{
				boolean bUpdate = SwsDeleteDBUtils.deleteMaterial(materialCodeTextField.getText().trim());
				if(bUpdate)
				{
					SwsMessageDialog.infoDialog("删除物料信息成功!");
					SwsSaveDBUtils.saveLog("删除物料信息", "删除物料信息", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
				}
				else
				{
					SwsMessageDialog.errorDialog("删除物料信息失败!");
				}
			}
		}
		else
		{
			SwsMessageDialog.errorDialog("该班组代码不存在，请重新输入!");
		}
	}
}
