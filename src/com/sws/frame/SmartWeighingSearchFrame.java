package com.sws.frame;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import com.sws.components.JDateChooserTextField;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJCheckBox;
import com.sws.components.SwsJComboBox;
import com.sws.components.SwsJLabel;
import com.sws.database.DB;
import com.sws.database.SwsQueryDBUtils;
import com.sws.entity.Material;
import com.sws.entity.Record;
import com.sws.entity.Team;
import com.sws.entity.User;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.utils.CommonUtils;

/**
 * ���ݼ�¼�������
 * @author Ben
 *
 */
public class SmartWeighingSearchFrame implements ActionListener, SWSEventListener{
	
	private JCheckBox produceDateBox;
	private JCheckBox deviceNameBox;
	private JCheckBox classBox;
	private JCheckBox teamNameBox;
	private JCheckBox materialNameBox;
	private JCheckBox materialSpecificationBox;
	private JCheckBox operatorBox;
	private JCheckBox updateRecordBox;
	private JCheckBox invalidRecordBox;
	private JCheckBox deleteRecordBox;
	
	private JButton queryBtn;
	private JButton updateBtn;
	private JButton deleteBtn;
	private JButton invalidRecordBtn;
	private JButton printBtn;
	private JButton selectAllBtn;
	private JButton antiSelectBtn;
	private JButton cancelSelectBtn;
	
	private JTextField produceDateTextField;
	private JComboBox deviceNameComboBox;
	private JComboBox classComboBox;
	private JComboBox teamNameComboBox;
	private JComboBox materialNameComboBox;
	private JComboBox materialSpecificationComboBox;
	private JComboBox operatorComboBox;
	
	//������26
	private String[] titles = new String[] {"ID", "���","ѡ��","��¼���","�豸����","�豸����","�뵥���","��������","��������","���","��������","��������","���Ϲ��","�⾶","�ں�","����","����","����","ɢ֧","��֧��","��������","�����","����Ա","����ʱ��","������Ա","��ע��Ϣ"};
	
	private JPanel tablePanel;
	
	public JPanel showFrame()
	{
		JPanel panel = new JPanel();
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1000};
		gridBagLayout.rowHeights = new int[]{200, 500};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		JPanel queryConditionPanel = createQueryConditionPanel();
		GridBagConstraints gbc_queryConditionPanel = new GridBagConstraints();
		gbc_queryConditionPanel.fill = GridBagConstraints.BOTH;
		gbc_queryConditionPanel.insets = new Insets(0, 0, 0, 0);
		gbc_queryConditionPanel.gridx = gridX;
		gbc_queryConditionPanel.gridy = gridY;
		panel.add(queryConditionPanel,gbc_queryConditionPanel);
		
		gridY++;
		
		tablePanel = createTablePanel();
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.fill = GridBagConstraints.BOTH;
		gbc_rightPanel.insets = new Insets(0, 0, 0, 0);
		gbc_rightPanel.gridx = gridX;
		gbc_rightPanel.gridy = gridY;
		panel.add(tablePanel,gbc_rightPanel);
		
		//������
		bindingData();
		
		return panel;
	}
	
	private JPanel createQueryConditionPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 70, 100, 100, 20, 30, 70, 30, 70, 30, 70, 20, 30, 70, 200};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0,1.0, 1.0, 1.0,1.0, 1.0, 1.0,1.0, 1.0, 1.0,1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0,1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		produceDateBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "�������ڣ�");
		
		gridX++;
		
		produceDateTextField = new JDateChooserTextField();
		GridBagConstraints gbc_produceDateTextField = new GridBagConstraints();
		gbc_produceDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_produceDateTextField.gridwidth = 2;
		gbc_produceDateTextField.insets = new Insets(0, 0, 0, 0);
		gbc_produceDateTextField.gridx = gridX;
		gbc_produceDateTextField.gridy = gridY;
		panel.add(produceDateTextField,gbc_produceDateTextField);
		
		gridX+=3;
		
		deviceNameBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "�豸���ƣ�");
		
		gridX++;
		
		deviceNameComboBox = new SwsJComboBox(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 4, 1);
		deviceNameComboBox.addItem("һ���豸");
		gridX+=5;
		
		classBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "���");
		
		gridX++;
		
		classComboBox = new SwsJComboBox(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1);
		
		gridX=0;
		gridY++;
		
		teamNameBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "�������ƣ�");
		
		gridX++;
		
		teamNameComboBox = new SwsJComboBox(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1);
		
		gridX+=3;
		
		materialNameBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "�������ƣ�");
		
		gridX++;
		
		materialNameComboBox = new SwsJComboBox(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 4, 1);
		
		gridX+=5;
		
		materialSpecificationBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "���Ϲ��");
		
		gridX++;
		
		materialSpecificationComboBox = new SwsJComboBox(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1);
		
		gridX=0;
		gridY++;
		
		operatorBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "����Ա��");
		
		gridX++;
		
		operatorComboBox = new SwsJComboBox(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1);
		
		gridX+=3;
		
		updateRecordBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "�޸ĵļ�¼");
		
		gridX++;
		
		invalidRecordBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "���ϵļ�¼");
		
		gridX++;
		
		deleteRecordBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "ɾ���ļ�¼");
		
		gridX=0;
		gridY++;
		
		queryBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1, "��ѯ��¼", this);
		
		gridX+=2;
		
		updateBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "�޸ļ�¼", this);
		
		gridX++;

		deleteBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "ɾ����¼", this);
		
		gridX+=2;
		
		invalidRecordBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1, "��¼����", this);
		
		gridX+=2;
		
		printBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1, "��ӡ��ǩ", this);
		
		gridX+=2;
		
		selectAllBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1, "ȫѡ", this);
		
		gridX+=3;
		
		antiSelectBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1, "��ѡ", this);
		
		gridX+=2;
		
		cancelSelectBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "ȡ��ѡ��", this);
		
		return panel;
	}
	
	private JPanel createTablePanel()
	{
		tablePanel = new JPanel();
		tablePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{700};
		gridBagLayout.rowHeights = new int[]{500};
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

					/*if(maintainType == Type.TEAM_MAINTAIN)
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
					}*/
				} 
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(queryBtn))
		{
			//��ѯ��¼
			queryRecord();
		}
		else if(e.getSource().equals(updateBtn))
		{
			//�޸ļ�¼
		}
		else if(e.getSource().equals(deleteBtn))
		{
			//ɾ����¼
		}
		else if(e.getSource().equals(invalidRecordBtn))
		{
			//��¼����
		}
		else if(e.getSource().equals(printBtn))
		{
			//��ӡ��ǩ
		}
		else if(e.getSource().equals(selectAllBtn))
		{
			//ȫѡ
			setCheckBox(true);
		}
		else if(e.getSource().equals(antiSelectBtn))
		{
			//��ѡ
			produceDateBox.setSelected(!produceDateBox.isSelected());
			deviceNameBox.setSelected(!deviceNameBox.isSelected());
			classBox.setSelected(!classBox.isSelected());
			teamNameBox.setSelected(!teamNameBox.isSelected());
			materialNameBox.setSelected(!materialNameBox.isSelected());
			materialSpecificationBox.setSelected(!materialSpecificationBox.isSelected());
			operatorBox.setSelected(!operatorBox.isSelected());
			updateRecordBox.setSelected(!updateRecordBox.isSelected());
			invalidRecordBox.setSelected(!invalidRecordBox.isSelected());
			deleteRecordBox.setSelected(!deleteRecordBox.isSelected());
		}
		else if(e.getSource().equals(cancelSelectBtn))
		{
			//ȡ��ѡ��
			setCheckBox(false);
		}
	}
	
	private void setCheckBox(boolean bSelect)
	{
		produceDateBox.setSelected(bSelect);
		deviceNameBox.setSelected(bSelect);
		classBox.setSelected(bSelect);
		teamNameBox.setSelected(bSelect);
		materialNameBox.setSelected(bSelect);
		materialSpecificationBox.setSelected(bSelect);
		operatorBox.setSelected(bSelect);
		updateRecordBox.setSelected(bSelect);
		invalidRecordBox.setSelected(bSelect);
		deleteRecordBox.setSelected(bSelect);
	}
	
	/**
	 * ������
	 */
	private void bindingData() 
	{
		//��ѯ���а�����Ϣ
		List<Team> teamList = SwsQueryDBUtils.queryAllTeam();
		
		for(int i=0; i<teamList.size(); i++)
		{
			Team team = teamList.get(i);
			teamNameComboBox.addItem(team.getTeamName());
			classComboBox.addItem(team.getTeamClass());
			//produceUnitComboBox.addItem(team.getProduceUnit());
		}
		
		//��ѯ�����û�
		List<User> userList = SwsQueryDBUtils.queryAllUser();
		for(int i=0; i<userList.size(); i++)
		{
			User user = userList.get(i);
			operatorComboBox.addItem(user.getUsername());
		}
		
		//��ѯ������������
		List<String> materialNameList = SwsQueryDBUtils.queryMaterialName();
		for(String name : materialNameList)
		{
			materialNameComboBox.addItem(name);
		}
		//��ѯ�������Ϲ��
		List<String> cunList = CommonUtils.getAllCun();
		for(String cun : cunList)
		{
			materialSpecificationComboBox.addItem(cun);
		}
	}
	
	private void queryRecord()
	{
		String querySQL = " where 1=1";
		boolean isSelected = false;
		if(produceDateBox.isSelected())
		{
			querySQL += " and "+DB.PRODUCE_DATE+" = '"+produceDateTextField.getText()+"'";
			isSelected = true;
		}
		if(deviceNameBox.isSelected())
		{
			isSelected = true;
			querySQL += " and "+DB.DEVICE_NAME+" = '"+deviceNameComboBox.getSelectedItem()+"'";
		}
		if(classBox.isSelected())
		{
			isSelected = true;
			querySQL += " and "+DB.TEAM_CLASS+" = '"+classComboBox.getSelectedItem()+"'";
		}
		if(teamNameBox.isSelected())
		{
			isSelected = true;
			querySQL += " and "+DB.TEAM_NAME+" = '"+teamNameComboBox.getSelectedItem()+"'";
		}
		if(materialNameBox.isSelected())
		{
			isSelected = true;
			querySQL += " and "+DB.MATERIAL_NAME+" = '"+materialNameComboBox.getSelectedItem()+"'";
		}
		if(materialSpecificationBox.isSelected())
		{
			isSelected = true;
			//querySQL += " and "+DB.MATERIAL_SPECIFITION+" = '"+materialSpecificationComboBox.getSelectedItem()+"'";
			querySQL += " and "+DB.MATERIAL_DIMENSION+" = '"+materialSpecificationComboBox.getSelectedItem()+"'";
		}
		if(operatorBox.isSelected())
		{
			isSelected = true;
			querySQL += " and "+DB.OPERATOR+" = '"+operatorComboBox.getSelectedItem()+"'";
		}
		if(updateRecordBox.isSelected())
		{
			isSelected = true;
			//querySQL += " and "+DB.MATERIAL_SPECIFITION+" = '"+materialSpecificationComboBox.getSelectedItem()+"'";
		}
		if(invalidRecordBox.isSelected())
		{
			isSelected = true;
			//querySQL += " and "+DB.MATERIAL_SPECIFITION+" = '"+materialSpecificationComboBox.getSelectedItem()+"'";
		}
		if(deleteRecordBox.isSelected())
		{
			isSelected = true;
			//querySQL += " and "+DB.MATERIAL_SPECIFITION+" = '"+materialSpecificationComboBox.getSelectedItem()+"'";
		}
		
		if(!isSelected)
		{
			SwsMessageDialog.warningDialog("������ѡ��һ���ѯ����!");
			return;
		}
		
		List<Record> list = SwsQueryDBUtils.queryRecordByCondition(querySQL);
		
		Object[][] data = new Object[list.size()][titles.length];
		int[] columnWidth = new int[] {0, 100, 100, 100, 100, 100, 100, 200, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 200, 100, 100};
		
		for(int i=0; i<list.size(); i++)
		{
			Record record = list.get(i);
			data[i][0] = record.getId();
			data[i][1] = (i+1);
			data[i][2] = new Boolean(false);
			data[i][3] = record.getRecordNumber();
			data[i][4] = record.getDeviceNumber();
			data[i][5] = record.getDeviceName();
			data[i][6] = record.getCodeListSerialNumber();
			data[i][7] = record.getProduceDate();
			data[i][8] = record.getTeam().getProduceUnit();
			data[i][9] = record.getTeam().getTeamClass();
			data[i][10] = record.getTeam().getTeamName();
			data[i][11] = record.getMaterial().getMaterialName();
			data[i][12] = record.getMaterial().getMaterialDimension();
			data[i][13] = record.getMaterial().getOuterDiameter();
			data[i][14] = record.getMaterial().getWallThickness();
			data[i][15] = record.getMaterial().getLength();
			data[i][16] = record.getMaterial().getMaterialQuality();
			data[i][17] = record.getMaterial().getJs();
			data[i][18] = record.getMaterial().getZs();
			data[i][19] = record.getMaterial().getZs();
			data[i][20] = record.getMaterial().getWeight();
			data[i][21] = "����";
			data[i][22] = record.getOperator();
			data[i][23] = record.getWeighingTime();
			data[i][24] = record.getWeigher();
			data[i][25] = record.getRemark();

		}
		
		displayTable(titles, data, columnWidth);
	}

	@Override
	public void lmsTransferEvent(SWSEvent event) {
		// TODO Auto-generated method stub
		String eventType = event.getEventType();
		HashMap eventExtra = event.getEventExtra();
		String nvramStr = null;

		int sensorID = 0;

		if(SwsParameter.DATABASE_UPDATE_EVENT.equals(eventType))
		{
			clear();
			bindingData();
		}
	}
	
	private void clear()
	{
		teamNameComboBox.removeAllItems();
		classComboBox.removeAllItems();
		materialNameComboBox.removeAllItems();
		materialSpecificationComboBox.removeAllItems();
		operatorComboBox.removeAllItems();
	}
}
