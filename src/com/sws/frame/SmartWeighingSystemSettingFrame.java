package com.sws.frame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import com.sws.base.SwsParameter;
import com.sws.components.JButtonBoolean;
import com.sws.components.JLabelComboBox;
import com.sws.components.JSettingLabelTextArea;
import com.sws.components.JSettingLabelTextField;
import com.sws.components.SwsJButton;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.frame.controll.ControllDebugSettingFrame;
import com.sws.frame.controll.ControllSearchIPFrame;

/**
 * ϵͳ��������
 * @author Ben
 *
 */
public class SmartWeighingSystemSettingFrame implements ActionListener, ItemListener, SWSEventListener{

	private JComboBox<String> controllComboBox;
	private JSettingLabelTextField controllIpTextField;
	private JSettingLabelTextField controllPortTextField;
	private JSettingLabelTextField weightPortTextField;
	private JButton searchControllIpBtn;
	private JButton debugSetBtn;
	public Object tokenSocket = new Object();
	
	public JPanel showFrame()
	{
		SwsParameter.swsEventManager.addListener(this);
		
		JPanel panel = new JPanel();
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{600, 400};
		gridBagLayout.rowHeights = new int[]{300, 400};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		JPanel companyPanel = createCompanySetPanel();
		GridBagConstraints gbc_companyPanel = new GridBagConstraints();
		gbc_companyPanel.fill = GridBagConstraints.BOTH;
		gbc_companyPanel.gridwidth = 1;
		gbc_companyPanel.insets = new Insets(0, 0, 0, 5);
		gbc_companyPanel.gridx = gridX;
		gbc_companyPanel.gridy = gridY;
		panel.add(companyPanel,gbc_companyPanel);
		
		gridX++;
		
		//==============================================================================
		JPanel printPanel = createPrintSetPanel();
		GridBagConstraints gbc_printPanel = new GridBagConstraints();
		gbc_printPanel.fill = GridBagConstraints.BOTH;
		gbc_printPanel.gridwidth = 1;
		gbc_printPanel.insets = new Insets(0, 0, 0, 5);
		gbc_printPanel.gridx = gridX;
		gbc_printPanel.gridy = gridY;
		panel.add(printPanel,gbc_printPanel);
		
		gridX=0;
		gridY++;
		
		JPanel devicePanel = createDeviceSetPanel();
		GridBagConstraints gbc_devicePanel = new GridBagConstraints();
		gbc_devicePanel.fill = GridBagConstraints.BOTH;
		gbc_devicePanel.gridwidth = 1;
		gbc_devicePanel.insets = new Insets(0, 0, 0, 5);
		gbc_devicePanel.gridx = gridX;
		gbc_devicePanel.gridy = gridY;
		panel.add(devicePanel,gbc_devicePanel);
		
		return panel;
	}
	
	//��˾��Ϣ����
	private JPanel createCompanySetPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "��˾��Ϣ����"));
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 500};
		gridBagLayout.rowHeights = new int[]{50, 100, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "��˾���ƣ�", true, true, false, SwsParameter.sNvramCompanyName);
		
		gridY++;
		
		new JSettingLabelTextArea(panel, gridX, gridY, 1, 1, "��˾��ַ��", true, true, SwsParameter.sNvramCompanyAddress);
		
		gridY++;
		
		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "��˾�绰��", true, true, false, SwsParameter.sNvramCompanyPhone);
		
		gridY++;
		
		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "��˾���棺", true, true, false, SwsParameter.sNvramCompanyFax);
		
		return panel;
	}
	
	//��ӡ��ǩ����
	private JPanel createPrintSetPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "��ӡ��ǩ����"));
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100, 100};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "���(cm)��", true, true, false, SwsParameter.fNvramPrintPaperWidth);
		
		gridX+=2;
		
		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "�߶�(cm)��", true, true, false, SwsParameter.fNvramPrintPaperHeight);
		
		gridX=0;
		gridY++;
		
		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "��߾�(cm)��", true, true, false, SwsParameter.fNvramPrintLeftMargin);
		
		gridX+=2;
		
		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "�ϱ߾�(cm)��", true, true, false, SwsParameter.fNvramPrintTopMargin);
		
		gridY++;
		gridX=0;
		
		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "ÿ�и߶ȣ�", true, true, false, SwsParameter.fNvramPrintRowHeight);
		
		gridX+=2;
		
		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "�����С��", true, true, false, SwsParameter.iNvramPrintFontSize);
		
		gridX=0;
		gridY++;
		
		new JButtonBoolean(panel, gridX, gridY, 2, "�Զ���ӡ(��)", "�Զ���ӡ(�ر�)", true, false, SwsParameter.bNvramAutoPrint);
		
		gridX+=2;
		
		new JButtonBoolean(panel, gridX, gridY, 1, "��ӡ����", "��ӡ����", false, false, SwsParameter.bNvramPrintTest);
		
		return panel;
	}
	
	//�豸��������
	private JPanel createDeviceSetPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "�豸��������"));
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{60, 100, 50, 120, 50, 120};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		new JLabelComboBox(panel, gridX, gridY, 1, 1, "�������ͣ�", true, SwsParameter.eNvramWeightDeviceType, SwsParameter.enumWeightDeviceType);
		
		gridX+=2;
		
		weightPortTextField = new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "���ںţ�", true, true, false, SwsParameter.sNvramWeightSerialPort);
		weightPortTextField.label.setOpaque(true);
		weightPortTextField.label.setBackground(Color.red);
		
		gridX+=2;
		
		new JLabelComboBox(panel, gridX, gridY, 1, 1, "�����ʣ�", true, SwsParameter.eNvramEnumProtocolBaudrate, SwsParameter.enumProtocolBaudrateType);
		
		gridX=0;
		gridY++;
		
		new JLabelComboBox(panel, gridX, gridY, 1, 1, "�ŵ��ţ�", true, SwsParameter.eNvramChannelNumber, SwsParameter.enumChannelNumberType);
		
		gridX+=2;
		
		new JButtonBoolean(panel, gridX, gridY, 2, "�л��ŵ�", "�л��ŵ�", false, false, SwsParameter.bNvramPrintTest);
		
		gridX+=2;
		
		new JButtonBoolean(panel, gridX, gridY, 2, "��ѯ�ŵ�", "��ѯ�ŵ�", false, false, SwsParameter.bNvramPrintTest);
		
		gridX=0;
		gridY++;
		
		/*new SwsJLabel(panel, gridX, gridY, "��������");
		
		gridX++;
		
		controllComboBox = new SwsJComboBox(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1);
		controllComboBox.addItem("δ֪����");
		controllComboBox.addItem("������");
		controllComboBox.addItemListener(this);*/
		new JLabelComboBox(panel, gridX, gridY, 1, 1, "��������", true, SwsParameter.eNvramControllDeviceType, SwsParameter.enumControllDeviceType);
		
		gridX+=2;
		
		//new SwsJLabel(panel, gridX, gridY, "ͨѶ��ַ��");
		
		//gridX++;
		
		//controllIpTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 1, 1);
		controllIpTextField = new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "ͨѶ��ַ��", true, true, false, SwsParameter.sNvramControllIP);
		controllIpTextField.label.setOpaque(true);
		controllIpTextField.label.setBackground(Color.red);
		
		gridX+=2;
		
		//new SwsJLabel(panel, gridX, gridY, "�˿ںţ�");
		
		//gridX++;
		
		controllPortTextField =  new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "�˿ںţ�", true, true, false, SwsParameter.iNvramControllPort);
		
		gridX=0;
		gridY++;
		
		searchControllIpBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "����IP", this);
		
		gridX++;
		
		debugSetBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "��������", this);
		
		return panel;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(searchControllIpBtn))
		{
			//����IP
			ControllSearchIPFrame.showFrame();
		}
		else if(e.getSource().equals(debugSetBtn))
		{
			//��������
			ControllDebugSettingFrame.showFrame();
		}
	}

	@Override
	public void lmsTransferEvent(SWSEvent event) {
		// TODO Auto-generated method stub
		String eventType = event.getEventType();
		HashMap eventExtra = event.getEventExtra();
		
		if(SwsParameter.SETTING_EVENT.equals(eventType))
		{
			String nvramStr = (String) eventExtra.get(SwsParameter.EXTRA_SETTING_NVRAM);
			if(nvramStr != null)
			{
				if(nvramStr.equals(SwsParameter.eNvramControllDeviceType.nvramStr))
				{
					
				}
			}
		}
		else if(SwsParameter.CONTROLL_CONNECTION_EVENT.equals(eventType))
		{
			String nvramStr = (String) eventExtra.get(SwsParameter.EXTRA_SETTING_NVRAM);
			if(nvramStr != null && nvramStr.equals(SwsParameter.bNvramDeviceConnection.nvramStr))
			{
				boolean bConnection = SwsParameter.bNvramDeviceConnection.bValue;
				if(bConnection)
				{
					controllIpTextField.label.setBackground(Color.GREEN);
				}
				else
				{
					controllIpTextField.label.setBackground(Color.RED);
				}
			}
		}
		else if(SwsParameter.WEIGHT_CONNECTION_EVENT.equals(eventType))
		{
			String nvramStr = (String) eventExtra.get(SwsParameter.EXTRA_SETTING_NVRAM);
			if(nvramStr != null && nvramStr.equals(SwsParameter.bNvramDeviceConnection.nvramStr))
			{
				boolean bConnection = SwsParameter.bNvramDeviceConnection.bValue;
				if(bConnection)
				{
					weightPortTextField.label.setBackground(Color.GREEN);
				}
				else
				{
					weightPortTextField.label.setBackground(Color.RED);
				}
			}
		}
	}
}
