package com.sws.frame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sws.base.SwsMessageDialog;
import com.sws.components.JDateChooserTextField;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJCheckBox;
import com.sws.components.SwsJComboBox;
import com.sws.components.SwsJLabel;
import com.sws.database.DB;
import com.sws.database.SwsQueryDBUtils;
import com.sws.entity.Record;

/**
 * ͳ�Ʊ������
 * @author Ben
 *
 */
public class SmartWeighingStatisticalReportFrame implements ActionListener{
	
	private JCheckBox produceDateBox;
	private JCheckBox deviceNameBox;
	private JCheckBox classBox;
	private JCheckBox teamNameBox;
	private JCheckBox materialNameBox;
	private JCheckBox materialSpecificationBox;
	private JCheckBox operatorBox;
	private JCheckBox produceUnitRecordBox;
	
	private JButton queryWeightDetailBtn;//������ϸ
	private JButton queryWeightSummaryBtn;//���ػ���
	private JButton queryInOutDetailBtn;  //������ϸ
	private JButton queryInOutSummaryBtn; //��������
	private JButton exportExcelBtn; //����Excel
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
	private JComboBox produceUnitComboBox;
	
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
		
		return panel;
	}
	
	private JPanel createQueryConditionPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 70, 100, 100, 20, 30, 70, 30, 70, 30, 70, 20, 30, 70, 100, 100};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0,1.0, 1.0, 1.0,1.0, 1.0, 1.0,1.0, 1.0, 1.0,1.0,1.0, Double.MIN_VALUE};
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
		
		gridX+=5;
		
		classBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "���");
		
		gridX++;
		
		classComboBox = new SwsJComboBox(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1);
		
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
		
		materialSpecificationComboBox = new SwsJComboBox(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1);
		
		gridX=0;
		gridY++;
		
		operatorBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "����Ա��");
		
		gridX++;
		
		operatorComboBox = new SwsJComboBox(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1);
		
		gridX+=3;
		
		produceUnitRecordBox = new SwsJCheckBox(panel, GridBagConstraints.EAST,  0,  0, gridX, gridY, 1, 1);
		
		gridX++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "��������");
		
		gridX++;
		
		produceUnitComboBox = new SwsJComboBox(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 4, 1);
		
		gridX+=5;
		
		selectAllBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1, "ȫѡ", this);
		
		gridX+=2;
		
		antiSelectBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "��ѡ", this);
		
		gridX++;
		
		cancelSelectBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "ȡ��ѡ��", this);
		
		gridX=0;
		gridY++;
		
		queryWeightDetailBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 3, 1, "���ɳ���������ϸ��", this);
		
		gridX+=3;
		
		queryWeightSummaryBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 3, 1, "���ɳ������ݻ��ܱ�", this);
		
		gridX+=3;

		queryInOutDetailBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 4, 1, "���ɽ�������������ϸ��", this);
		
		gridX+=4;
		
		queryInOutSummaryBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 4, 1, "���ɽ����������ݻ��ܱ�", this);
		
		gridX+=4;
		
		exportExcelBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 2, 1, "����Excel����", this);
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(queryWeightDetailBtn))
		{
			//���ɳ�����ϸ��
			queryRecord();
		}
		else if(e.getSource().equals(queryWeightSummaryBtn))
		{
			//���ɳ��ػ��ܱ�
		}
		else if(e.getSource().equals(queryInOutDetailBtn))
		{
			//���ɽ���������ϸ��
		}
		else if(e.getSource().equals(queryInOutSummaryBtn))
		{
			//���ɽ������Ļ��ܱ�
		}
		else if(e.getSource().equals(exportExcelBtn))
		{
			//����Excel����
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
			produceUnitRecordBox.setSelected(!produceUnitRecordBox.isSelected());
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
		produceUnitRecordBox.setSelected(bSelect);
	}
	
	private void queryRecord()
	{
		System.out.println("���е�����");
		String querySQL = " where 1=1";
		if(produceDateBox.isSelected())
		{
			querySQL += " and "+DB.PRODUCE_DATE+" = '"+produceDateTextField.getText()+"'";
		}
		else if(deviceNameBox.isSelected())
		{
			querySQL += " and "+DB.DEVICE_NAME+" = '"+deviceNameComboBox.getSelectedItem()+"'";
		}
		else if(classBox.isSelected())
		{
			querySQL += " and "+DB.TEAM_CLASS+" = '"+classComboBox.getSelectedItem()+"'";
		}
		else if(teamNameBox.isSelected())
		{
			querySQL += " and "+DB.TEAM_NAME+" = '"+teamNameComboBox.getSelectedItem()+"'";
		}
		else if(materialNameBox.isSelected())
		{
			querySQL += " and "+DB.MATERIAL_NAME+" = '"+materialNameComboBox.getSelectedItem()+"'";
		}
		else if(materialSpecificationBox.isSelected())
		{
			//querySQL += " and "+DB.MATERIAL_SPECIFITION+" = '"+materialSpecificationComboBox.getSelectedItem()+"'";
			querySQL += " and "+DB.MATERIAL_DIMENSION+" = '"+materialSpecificationComboBox.getSelectedItem()+"'";
		}
		else if(operatorBox.isSelected())
		{
			querySQL += " and "+DB.OPERATOR+" = '"+operatorComboBox.getSelectedItem()+"'";
		}
		else if(produceUnitRecordBox.isSelected())
		{
			//querySQL += " and "+DB.MATERIAL_SPECIFITION+" = '"+materialSpecificationComboBox.getSelectedItem()+"'";
		}
		else 
		{
			SwsMessageDialog.warningDialog("������ѡ��һ���ѯ����!");
		}
		
		List<Record> list = SwsQueryDBUtils.queryRecordByCondition(querySQL);
		
		
	}
}
