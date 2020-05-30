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
 * ����ά������
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
	
	//������Ϣ
	private JTextField materialCodeTextField;
	private JTextField materialNameTextField;
	private JTextField materialAliasTextField;
	private JTextField materialSpecifitionTextField;
	private JTextField outerDiameterTextField;
	private JTextField wallThicknessTextField;
	private JTextField materialLengthTextField;
	private JTextField dxxsTextField;//��пϵ��
	private JTextField zzTextField;//֧��
	private JTextField everyZsTextField;//ÿ��֧��
	private JTextField pieceWeightTextField;//����
	private JTextField minimalWeightTextField;//��С����
	private JTextField maximumWeightTextField;//�������
	private JLabelComboBox isMatchComboBox;
	
	private JTextField teamCodeTextField;
	private JTextField teamNameTextField;
	private JTextField teamAliasTextField;
	private JTextField teamClassTextField;
	private JTextField produceUnitTextField;
	
	private JPanel tablePanel;
	//private int id;
	
	private enum Type {
		MATERIAL_MAINTAIN,//����ά��
		TEAM_MAINTAIN, //����ά��
		CUSTOMER_MAINTAIN, //�ͻ�ά��
	}
	
	public SmartWeighingMaintainFrame() 
	{
		EventListener eListener = new EventListener();
		SwsParameter.swsEventManager.addListener(eListener);
	}

	public JPanel showFrame()
	{
		JPanel panel = new JPanel();
		
		//���ò���
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
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "������Ϣά��", "������Ϣά��", false, false, SwsParameter.bNvramMaterialsMessageMaintain);
		materialMaintainBtn = new SwsJButton(panel, gridX, gridY, "������Ϣά��", this);
		
		gridX++;
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "������Ϣά��", "������Ϣά��", false, false, SwsParameter.bNvramTeamMessageMaintain);
		teamMaintainBtn = new SwsJButton(panel, gridX, gridY, "������Ϣά��", this);
		
		gridX++;
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "�ͻ���Ϣά��", "�ͻ���Ϣά��", false, false, SwsParameter.bNvramCustomerMessageMaintain);
		//customerMaintainBtn = new SwsJButton(panel, gridX, gridY, "�ͻ���Ϣά��", this);
		exportMaterialBtn = new SwsJButton(panel,gridX, gridY, "����������Ϣ", this);
		
		gridX++;
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "�ͻ���Ϣά��", "�ͻ���Ϣά��", false, false, SwsParameter.bNvramCustomerMessageMaintain);
		//customerMaintainBtn = new SwsJButton(panel, gridX, gridY, "�ͻ���Ϣά��", this);
		exportTeamBtn = new SwsJButton(panel,gridX, gridY, "���������Ϣ", this);
		
		gridX++;
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "������Ĭ��ֵ", "������Ĭ��ֵ", false, false, SwsParameter.bNvramDataitemDefaultValue);
		dataDefaultBtn = new SwsJButton(panel, gridX, gridY, "������Ĭ��ֵ", this);
		
		//gridX++;
		
		//new JButtonBoolean(panel, gridX, gridY, 1, "����������Ϣ", "����������Ϣ", false, false, SwsParameter.bnvramExportMaterialsMessage);
		
		
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
	
	//������Ϣ������
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
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "������룺", true, false, false, SwsParameter.iNvramCargoCode);
		new SwsJLabel(leftPanel, gridX, gridY, "������룺");
		
		gridX++;
		
		materialCodeTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "���������", true, false, false, SwsParameter.sNvramCargoAlias);
		new SwsJLabel(leftPanel, gridX, gridY, "�������ƣ�");
		
		gridX++;
		
		materialNameTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "�������ƣ�", true, false, false, SwsParameter.sNvramCargoName);
		new SwsJLabel(leftPanel, gridX, gridY, "���������");
		
		gridX++;
		
		materialAliasTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "������", true, false, false, SwsParameter.sNvramCargoSpecifications);
		new SwsJLabel(leftPanel, gridX, gridY, "������");
		
		gridX++;
		
		materialSpecifitionTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "�⾶��", true, false, false, SwsParameter.fNvramOuterDiameter);
		new SwsJLabel(leftPanel, gridX, gridY, "�⾶��");
		
		gridX++;
		
		outerDiameterTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "�ں�", true, false, false, SwsParameter.fNvramWallThickness);
		new SwsJLabel(leftPanel, gridX, gridY, "�ں�");
		
		gridX++;
		
		wallThicknessTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "���ȣ�", true, false, false, SwsParameter.fNvramCargoLength);
		new SwsJLabel(leftPanel, gridX, gridY, "���ȣ�");
		
		gridX++;
		
		materialLengthTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "��пϵ����", true, false, false, SwsParameter.fNvramGalvanizationCoefficient);
		new SwsJLabel(leftPanel, gridX, gridY, "��пϵ����");
		
		gridX++;
		
		dxxsTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "֧�أ�", true, false, false, SwsParameter.fNvramCargoWeight);
		new SwsJLabel(leftPanel, gridX, gridY, "֧�أ�");
		
		gridX++;
		
		zzTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "ÿ��֧����", true, false, false, SwsParameter.iNvramEveryCount);
		new SwsJLabel(leftPanel, gridX, gridY, "ÿ��֧����");
		
		gridX++;
		
		everyZsTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "���أ�", true, false, false, SwsParameter.iNvramPieceWeight);
		new SwsJLabel(leftPanel, gridX, gridY, "���أ�");
		
		gridX++;
		
		pieceWeightTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);

		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "��С������", true, false, false, SwsParameter.iNvramMinimalWeight);
		new SwsJLabel(leftPanel, gridX, gridY, "��С������");
		
		gridX++;
		
		minimalWeightTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "���������", true, false, false, SwsParameter.iNvramMaximumWeight);
		new SwsJLabel(leftPanel, gridX, gridY, "���������");
		
		gridX++;
		
		maximumWeightTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		JLabel produceDateLabel = new JLabel("�������ڣ�");
		GridBagConstraints gbc_produceDateLabel = new GridBagConstraints();
		gbc_produceDateLabel.fill = GridBagConstraints.BOTH;
		gbc_produceDateLabel.insets = new Insets(0, 5, 5, 5);
		gbc_produceDateLabel.gridx = gridX;
		gbc_produceDateLabel.gridy = gridY;
		leftPanel.add(produceDateLabel, gbc_produceDateLabel);
		
		gridX++;
		
		produceDate = new JDateChooserTextField();
		produceDate.setFont(new Font("����", Font.BOLD, 14));
		GridBagConstraints gbc_produceDate = new GridBagConstraints();
		gbc_produceDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_produceDate.insets = new Insets(0, 0, 5, 5);
		gbc_produceDate.gridwidth = 2;
		gbc_produceDate.gridx = gridX;
		gbc_produceDate.gridy = gridY;
		leftPanel.add(produceDate, gbc_produceDate);
		
		gridX=0;
		gridY++;
		
		isMatchComboBox = new JLabelComboBox(leftPanel, gridX, gridY, 1, 2, "����ƥ�䣺", false, SwsParameter.eNvramMatchIntervalType, SwsParameter.enumMatchIntervalType);
		
		gridX=0;
		gridY++;
		
		addBtn = new SwsJButton(leftPanel, gridX, gridY, "���", this);
		
		gridX++;
		
		updateBtn = new SwsJButton(leftPanel, gridX, gridY, "�޸�", this);
		
		gridX++;
		
		deleteBtn = new SwsJButton(leftPanel, gridX, gridY, "ɾ��", this);
		
		return leftPanel;
	}
	
	//������Ϣά��������
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
		
		//new JSettingLabelTextField(leftPanel, gridX, gridY, 1, 2, "������ţ�", true, true, false, SwsParameter.iNvramTeamCode);
		new SwsJLabel(leftPanel, gridX, gridY, "������룺");
		
		gridX++;
		
		teamCodeTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(leftPanel, gridX, gridY, "�������ƣ�");
		
		gridX++;
		
		teamNameTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(leftPanel, gridX, gridY, "���������");
		
		gridX++;
		
		teamAliasTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(leftPanel, gridX, gridY, "��      ��");
		
		gridX++;
		
		teamClassTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(leftPanel, gridX, gridY, "�������飺");
		
		gridX++;
		
		produceUnitTextField = new SwsJTextField(leftPanel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		addBtn = new SwsJButton(leftPanel, gridX, gridY, "���", this);
		
		gridX++;
		
		updateBtn = new SwsJButton(leftPanel, gridX, gridY, "�޸�", this);
		
		gridX++;
		
		deleteBtn = new SwsJButton(leftPanel, gridX, gridY, "ɾ��", this);
		
		return leftPanel;
	} 
	
	//�ͻ���Ϣά��������
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
		
		new SwsJLabel(leftPanel, gridX, gridY, 1, "�ͻ�����");
		
		gridX++;
		
		new SwsJTextField(leftPanel, GridBagConstraints.HORIZONTAL, gridX, gridY, 2, 1);
		
		gridX=0;
		gridY++;
		
		new SwsJButton(leftPanel, gridX, gridY, "���", this);
		
		gridX++;
		
		new SwsJButton(leftPanel, gridX, gridY, "�޸�", this);
		
		return leftPanel;
	}
	
	private JPanel createTablePanel()
	{
		tablePanel = new JPanel();
		tablePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

		//���ò���
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
		System.out.println("��ʾ�б�");
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
			//����ID��
			TableColumnModel tcm = table.getColumnModel();  
			TableColumn tc = tcm.getColumn(i);
			tc.setMinWidth(width);
			tc.setMaxWidth(width);
			tc.setPreferredWidth(width);
		}
		
		//-------���ø������ñ�����ɫ-----------------------------------------------------------------
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
		
		//��Ԫ�����ݾ���
		ter.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, ter);
		
        //��ѡ���еı�����ɫ
		table.setSelectionBackground(Color.GRAY);
		//�����и�
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
		
		//ע��˫��ĳ�е���Ӧ�¼�
		table.addMouseListener(new MouseAdapter()
		{ 
			public void mouseClicked(MouseEvent e) 
			{
                //ʵ��˫�� 
				if(e.getClickCount() == 2)
				{ 
					int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //�����λ�� 

					int id =(int) ((JTable)e.getSource()).getValueAt(row,0); //��õ����Ԫ������ 

					if(maintainType == Type.TEAM_MAINTAIN)
					{
						String teamCode = (String) ((JTable)e.getSource()).getValueAt(row,2); //��õ����Ԫ������ 
						//��ʾ������ϸ��Ϣ
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
						int materialCode = (int) ((JTable)e.getSource()).getValueAt(row,2); //��õ����Ԫ������ 
						//��ʾ������ϸ��Ϣ
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
							if("��".equals(isMatch))
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
			//������Ϣά��
			System.out.println("���е����������Ϣά��");
			maintainType = Type.MATERIAL_MAINTAIN;
			//������Ϣά��
			leftPanel.removeAll();
			leftPanel = createMaterialsLeftPanel();
			leftPanel.revalidate();
			
			showTable();
		}
		else if(e.getSource().equals(teamMaintainBtn))
		{
			System.out.println("���е����������Ϣά��");
			maintainType = Type.TEAM_MAINTAIN;
			//������Ϣά��
			leftPanel.removeAll();
			leftPanel = createTeamLeftPanel();
			leftPanel.revalidate();
			//��ʾ�ұ��б�
			showTable();
		}
		else if(e.getSource().equals(exportTeamBtn))
		{
			/*maintainType = Type.CUSTOMER_MAINTAIN;
			//�ͻ���Ϣά��
			leftPanel.removeAll();
			leftPanel = createCustomerLeftPanel();
			leftPanel.revalidate();*/
			System.out.println("���������Ϣ");
			//���������Ϣ
			SmartWeighingExportTeamFrame.showFrame();
			
			
		}
		else if(e.getSource().equals(dataDefaultBtn))
		{
			System.out.println("����Ĭ����");
		}
		else if(e.getSource().equals(exportMaterialBtn))
		{
			System.out.println("����������Ϣ");
			//����������Ϣ
			SmartWeighingExportMaterialFrame.showFrame();
		}
		else if(e.getSource().equals(addBtn))
		{
			if(maintainType == Type.MATERIAL_MAINTAIN)
			{
				System.out.println("���������Ϣ");
				addMaterial();
				showTable();
				clear();
			}
			else if(maintainType == Type.TEAM_MAINTAIN)
			{
				System.out.println("��Ӱ�����Ϣ");
				addTeam();
				showTable();
				clear();
			}
		}
		else if(e.getSource().equals(updateBtn))
		{
			if(maintainType == Type.MATERIAL_MAINTAIN)
			{
				System.out.println("�޸�������Ϣ");
				updateMaterial();
				showTable();
				clear();
			}
			else if(maintainType == Type.TEAM_MAINTAIN)
			{
				System.out.println("�޸İ�����Ϣ");
				updateTeam();
				showTable();
				clear();
			}
		}
		else if(e.getSource().equals(deleteBtn))
		{
			if(maintainType == Type.MATERIAL_MAINTAIN)
			{
				System.out.println("ɾ��������Ϣ");
				deleteMaterial();
				showTable();
				clear();
			}
			else if(maintainType == Type.TEAM_MAINTAIN)
			{
				System.out.println("ɾ��������Ϣ");
				deleteTeam();
				showTable();
				clear();
			}
		}
	}
	
	/**
	 * ���
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
			//������Ϣ
			List<Team> teamList = SwsQueryDBUtils.queryAllTeam();
			if(teamList.size() > 0)
			{
				//����
				String[] title = new String[]{"ID", "���", "�������", "��������", "�������", "���", "��������", "�޸�ʱ��"};
				//���ɱ��
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
		else if(maintainType == Type.MATERIAL_MAINTAIN)//����ά��
		{
			//������Ϣ
			List<Material> materialList = SwsQueryDBUtils.queryAllMaterial();
			//����
			String[] title = new String[]{"ID", "���", "���ϴ���", "��������", "���ϱ���", "���Ϲ��", "���ϴ��", "�⾶", "�ں�", "����", "��пϵ��", "֧��", "ÿ��֧��", "����", "��С����", "�������", "�Ƿ�ƥ��", "��������", "�޸�ʱ��"};
			//���ɱ��
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
	
	//��Ӱ���
	private void addMaterial()
	{
		if("".equals(materialCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("���ϴ��벻��Ϊ��!");
			return;
		}
		if("".equals(materialNameTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�������Ʋ���Ϊ��!");
			return;
		}
		if("".equals(materialAliasTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("���ϱ�����������Ϊ��!");
			return;
		}
		if("".equals(materialSpecifitionTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("���Ϲ����Ϊ�գ�");
			return;
		}
		if("".equals(outerDiameterTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�⾶����Ϊ�գ�");
			return;
		}
		if("".equals(wallThicknessTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�ں���Ϊ�գ�");
			return;
		}
		if("".equals(materialLengthTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("���Ȳ���Ϊ�գ�");
			return;
		}
		if("".equals(dxxsTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("��пϵ������Ϊ�գ�");
			return;
		}
		if("".equals(zzTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("֧�ز���Ϊ�գ�");
			return;
		}
		if("".equals(everyZsTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("ÿ��֧������Ϊ�գ�");
			return;
		}
		if("".equals(pieceWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("���ز���Ϊ�գ�");
			return;
		}
		if("".equals(minimalWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("��С��������Ϊ�գ�");
			return;
		}
		if("".equals(maximumWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�����������Ϊ�գ�");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.materialExist(materialCodeTextField.getText().trim());
		if(bExist)
		{
			SwsMessageDialog.errorDialog("�����ϴ����Ѵ��ڣ�����������!");
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
			String isMatch = "MATCH_NO".equals(SwsParameter.enumMatchIntervalType.key)?"��":"��";
			material.setIsMatch(isMatch);
			material.setModifyTime(CommonUtils.getCurrentTime());
			
			boolean bAdd = SwsSaveDBUtils.addMaterial(material);
			if(bAdd)
			{
				SwsMessageDialog.infoDialog("���������Ϣ�ɹ�!");
				SwsSaveDBUtils.saveLog("���������Ϣ", "���������Ϣ", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
			}
			else
			{
				SwsMessageDialog.errorDialog("���������Ϣʧ��!");
			}
		}
	}
	
	//��Ӱ���
	private void addTeam()
	{
		if("".equals(teamCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("������벻��Ϊ��!");
			return;
		}
		if("".equals(teamNameTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�������Ʋ���Ϊ��!");
			return;
		}
		if("".equals(teamAliasTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�����������Ϊ��!");
			return;
		}
		if("".equals(teamClassTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�����Ϊ�գ�");
			return;
		}
		if("".equals(produceUnitTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�������鲻��Ϊ��!");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.teamExist(teamCodeTextField.getText().trim());
		if(bExist)
		{
			SwsMessageDialog.errorDialog("�ð�������Ѵ��ڣ�����������!");
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
				SwsMessageDialog.infoDialog("��Ӱ�����Ϣ�ɹ�!");
				SwsSaveDBUtils.saveLog("��Ӱ�����Ϣ", "��Ӱ�����Ϣ", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
			}
			else
			{
				SwsMessageDialog.errorDialog("��Ӱ�����Ϣʧ��!");
			}
		}
	}
	
	//�޸İ���
	private void updateTeam()
	{
		if("".equals(teamCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("������벻��Ϊ��!");
			return;
		}
		if("".equals(teamNameTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�������Ʋ���Ϊ��!");
			return;
		}
		if("".equals(teamAliasTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�����������Ϊ��!");
			return;
		}
		if("".equals(teamClassTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�����Ϊ�գ�");
			return;
		}
		if("".equals(produceUnitTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�������鲻��Ϊ��!");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.teamExist(teamCodeTextField.getText().trim());
		if(bExist)
		{
			int option = SwsMessageDialog.confirmDialog("ȷ���޸�"+teamNameTextField.getText().trim()+"�İ�����Ϣ��", "�޸İ�����Ϣ��ʾ");
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
					SwsMessageDialog.infoDialog("�޸İ�����Ϣ�ɹ�!");
					SwsSaveDBUtils.saveLog("�޸İ�����Ϣ", "�޸İ�����Ϣ", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
				}
				else
				{
					SwsMessageDialog.errorDialog("�޸İ�����Ϣʧ��!");
				}
			}
		}
		else
		{
			SwsMessageDialog.errorDialog("�ð�����벻���ڣ�����������!");
		}
	}
	
	
	//�޸�����
	private void updateMaterial()
	{
		if("".equals(materialCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("���ϴ��벻��Ϊ��!");
			return;
		}
		if("".equals(materialNameTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�������Ʋ���Ϊ��!");
			return;
		}
		if("".equals(materialAliasTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("���ϱ�����������Ϊ��!");
			return;
		}
		if("".equals(materialSpecifitionTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("���Ϲ����Ϊ�գ�");
			return;
		}
		if("".endsWith(outerDiameterTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�⾶����Ϊ�գ�");
			return;
		}
		if("".endsWith(wallThicknessTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�ں���Ϊ�գ�");
			return;
		}
		if("".endsWith(materialLengthTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("���Ȳ���Ϊ�գ�");
			return;
		}
		if("".endsWith(dxxsTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("��пϵ������Ϊ�գ�");
			return;
		}
		if("".endsWith(zzTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("֧�ز���Ϊ�գ�");
			return;
		}
		if("".endsWith(everyZsTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("ÿ��֧������Ϊ�գ�");
			return;
		}
		if("".endsWith(pieceWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("���ز���Ϊ�գ�");
			return;
		}
		if("".endsWith(minimalWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("��С��������Ϊ�գ�");
			return;
		}
		if("".endsWith(maximumWeightTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("�����������Ϊ�գ�");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.materialExist(materialCodeTextField.getText().trim());
		if(bExist)
		{
			
			int option = SwsMessageDialog.confirmDialog("ȷ���޸���������Ϊ"+materialNameTextField.getText().trim()+"����Ϣ��", "�޸�������Ϣ��ʾ");
			if(option == SwsMessageDialog.OK_OPTION)
			{
				Material material = new Material();
				material.setMaterialCode(Integer.parseInt(materialCodeTextField.getText().trim()));
				material.setMaterialName(materialNameTextField.getText().trim());
				material.setMaterialAlias(materialAliasTextField.getText().trim());
				material.setMaterialSpecifition(materialSpecifitionTextField.getText().trim());
				material.setMaterialDimension("6��");
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
				String isMatch = "MATCH_NO".equals(SwsParameter.enumMatchIntervalType.key)?"��":"��";
				material.setIsMatch(isMatch);
				material.setModifyTime(CommonUtils.getCurrentTime());
				
				boolean bAdd = SwsUpdateDBUtils.updateMaterial(material);
				if(bAdd)
				{
					SwsMessageDialog.infoDialog("�޸�������Ϣ�ɹ�!");
					SwsSaveDBUtils.saveLog("�޸�������Ϣ", "�޸�������Ϣ", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
				}
				else
				{
					SwsMessageDialog.errorDialog("���������Ϣʧ��!");
				}
			}
		}
		else
		{
			SwsMessageDialog.errorDialog("�����ϴ��벻���ڣ�����������!");
		}
	}
	
	//ɾ������
	private void deleteTeam()
	{
		if("".equals(teamCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("������벻��Ϊ��!");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.teamExist(teamCodeTextField.getText().trim());
		
		if(bExist)
		{
			int option = SwsMessageDialog.confirmDialog("ȷ��ɾ��\" �������Ϊ"+teamCodeTextField.getText().trim()+"\" �İ�����Ϣ��", "ɾ��������Ϣ��ʾ");
			if(option == SwsMessageDialog.OK_OPTION)
			{
				boolean bUpdate = SwsDeleteDBUtils.deleteTeam(teamCodeTextField.getText().trim());
				if(bUpdate)
				{
					SwsMessageDialog.infoDialog("ɾ��������Ϣ�ɹ�!");
					SwsSaveDBUtils.saveLog("ɾ��������Ϣ", "ɾ��������Ϣ", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
				}
				else
				{
					SwsMessageDialog.errorDialog("ɾ��������Ϣʧ��!");
				}
			}
		}
		else
		{
			SwsMessageDialog.errorDialog("�ð�����벻���ڣ�����������!");
		}
	}
	
	//ɾ������
	private void deleteMaterial()
	{
		if("".equals(materialCodeTextField.getText().trim()))
		{
			SwsMessageDialog.warningDialog("���ϴ��벻��Ϊ��!");
			return;
		}
		
		boolean bExist = SwsQueryDBUtils.materialExist(materialCodeTextField.getText().trim());
		
		if(bExist)
		{
			int option = SwsMessageDialog.confirmDialog("ȷ��ɾ��\" ���ϴ���Ϊ"+materialCodeTextField.getText().trim()+"\" ��������Ϣ��", "ɾ��������Ϣ��ʾ");
			if(option == SwsMessageDialog.OK_OPTION)
			{
				boolean bUpdate = SwsDeleteDBUtils.deleteMaterial(materialCodeTextField.getText().trim());
				if(bUpdate)
				{
					SwsMessageDialog.infoDialog("ɾ��������Ϣ�ɹ�!");
					SwsSaveDBUtils.saveLog("ɾ��������Ϣ", "ɾ��������Ϣ", SwsParameter.loginUserName, CommonUtils.getCurrentTime(), "");
				}
				else
				{
					SwsMessageDialog.errorDialog("ɾ��������Ϣʧ��!");
				}
			}
		}
		else
		{
			SwsMessageDialog.errorDialog("�ð�����벻���ڣ�����������!");
		}
	}
}
