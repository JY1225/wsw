package com.sws.frame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sws.base.SwsMessageDialog;
import com.sws.base.SwsParameter;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJLabel;
import com.sws.components.SwsJTextField;
import com.sws.database.SwsQueryDBUtils;
import com.sws.database.SwsSaveDBUtils;
import com.sws.database.SwsUpdateDBUtils;
import com.sws.entity.Material;
import com.sws.entity.Team;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.utils.CommonUtils;
import com.sws.utils.ExcelUtil;
import com.sws.utils.FileType;

public class SmartWeighingExportMaterialFrame extends JFrame implements ActionListener{
	
	private static SmartWeighingExportMaterialFrame exportMaterialFrame = null;
	//private JSettingLabelTextField excelPathTextField;
	private JTextField excelPathTextField;
	private JButton selectFileBtn;
	private JButton exportExcelBtn;
	private JButton downloadTemplateBtn;//����ģ��
	
	private String[] dxTitles = new String[]{"�⾶", "�ں�", "����", "��пϵ��", "֧��", "֧��", "����", "���䷶Χ"};//��п
	private String[] hgTitles = new String[]{"�⾶", "�ں�", "����", "֧��", "֧��", "����", "���䷶Χ"};//����

	public SmartWeighingExportMaterialFrame()
	{
		EventListener eListener = new EventListener();
		SwsParameter.swsEventManager.addListener(eListener);
		init();
	}
	
	private void init()
	{
		setTitle("����������Ϣ");
		
		setSize(600, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{600};
		gridBagLayout.rowHeights = new int[]{200};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JPanel teamSetPanel = createMessagePanel();
		GridBagConstraints gbc_teamSetPanel = new GridBagConstraints();
		gbc_teamSetPanel.fill = GridBagConstraints.BOTH;
		gbc_teamSetPanel.insets = new Insets(0, 0, 0, 2);
		gbc_teamSetPanel.gridx = 0;
		gbc_teamSetPanel.gridy = 0;
		add(teamSetPanel,gbc_teamSetPanel);
	}
	
	private JPanel createMessagePanel()
	{
		JPanel panel = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 100, 100, 100, 100, 150};
		gridBagLayout.rowHeights = new int[]{30, 30, 30, 30};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
	    panel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		//excelPathTextField = new JSettingLabelTextField(panel, gridX, gridY, 2, 4, "��ѡ��Excel·����", true, true, false, SwsParameter.iNvramTeamCode);
		new SwsJLabel(panel,GridBagConstraints.EAST, 0, 0, gridX, gridY, 2, 1, "��ѡ��Excel·����");
		
		gridX+=2;
		excelPathTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 3, 1);
		
		gridX+=3;
		
		selectFileBtn = new SwsJButton(panel, gridX, gridY, 1, "ѡ��Excel�ļ�", this);
		
		gridY++;
		gridX=1;
		
		downloadTemplateBtn = new SwsJButton(panel, gridX, gridY, 2, "����Excelģ��", this);
		
		gridX+=2;
		
		//new JButtonBoolean(panel, gridX, gridY, 2, "������Ϣ", "������Ϣ", false, false, SwsParameter.bNvramMaterialsMessageMaintain);
		exportExcelBtn = new SwsJButton(panel, gridX, gridY, 2, "������Ϣ", this);
		
		gridX=0;
		gridY++;
		
		JLabel showLabel = new SwsJLabel(panel, GridBagConstraints.BOTH, 70, 0, gridX, gridY, 6, 1, "˵��������ǰ������Excelģ�壬����ģ������д���ݣ����⵼��ʧ�ܣ�");
		showLabel.setForeground(Color.RED);
		
		gridY++;
		
		JLabel showLabel2 = new SwsJLabel(panel, GridBagConstraints.BOTH, 70, 0, gridX, gridY, 6, 1, "������Ϣֻ�ܵ���һ�Σ��Ժ���������Ϣά���������ά�����������׳�������");
		showLabel2.setForeground(Color.RED);
		
		return panel;
	}
	
	public static void showFrame()
	{
		if(exportMaterialFrame == null)
		{
			exportMaterialFrame = new SmartWeighingExportMaterialFrame();
			exportMaterialFrame.setVisible(true);
		}
		else if(!exportMaterialFrame.isVisible())
		{
			exportMaterialFrame.setVisible(true);
		}
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
				
				/*if(nvramStr.equals(SwsParameter.bNvramSelectExcel.nvramStr))
				{
					String path = CommonUtils.selectFilePath("D:\\", "ѡ��Excel�ļ�");
					System.out.println("path="+path);
					excelPathTextField.textField.setText(path);
				}*/
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(selectFileBtn))
		{
			String path = CommonUtils.selectFilePath("D:\\", "ѡ��Excel�ļ�", FileType.EXCEL);
			System.out.println("path="+path);
			excelPathTextField.setText(path);
		}
		else if(e.getSource().equals(exportExcelBtn))
		{
			String path = excelPathTextField.getText().trim();
			if("".equals(path))
			{
				SwsMessageDialog.warningDialog("��ѡ��Ҫ�����Excel�ļ�");
				return;
			}
			
			File file = new File(path);
			
			if(file.exists())
			{
				List<String[]> dxList = ExcelUtil.readExcel(file, 0, dxTitles.length);
				
				//�ӵڶ��п�ʼ������һ���Ǳ���
				for(int i=1; i<dxList.size(); i++)
				{
					String[] materials = dxList.get(i);
					if("".equals(materials[0]))
						continue;
					String materialName = "�ȶ�п��";
					
					String outerDiameter = materials[0];//�⾶
					String wallThickness = materials[1];//�ں�
					String length = materials[2];//����
					String dxxs = materials[3];//��пϵ��
					String zz = materials[4];//֧��
					String zs = materials[5];//֧��
					String jz = materials[6];//����
					String qjWeight = materials[7];//��������
					
					//��������С���������
					String minWeight = qjWeight.split("-")[0];
					String maxWeight = qjWeight.split("-")[1];
					if("����".equals(minWeight))
					{
						minWeight = "0";
					}
					else if("����".equals(maxWeight))
					{
						maxWeight = "999999";
					}
					
					float pieweight = Float.parseFloat(jz);
					float minW = Float.parseFloat(minWeight);
					float maxW = Float.parseFloat(maxWeight);
					
					String isMatch = "��";
					
					if(pieweight >= minW && pieweight <= maxW)
					{
						isMatch = "��";
					}
					
					//����
					String cun = CommonUtils.getCunByOuterDiameter(Float.parseFloat(outerDiameter));
					String materialAlias = materialName+cun+"-"+outerDiameter+"*"+wallThickness;
					String materialSpecifition = outerDiameter+"*"+wallThickness+"*"+length;
					
					Material material = new Material();
					material.setMaterialName(materialName);
					material.setMaterialAlias(materialAlias);
					material.setMaterialSpecifition(materialSpecifition);
					material.setMaterialDimension(cun);
					material.setOuterDiameter(Float.parseFloat(outerDiameter));
					material.setWallThickness(Float.parseFloat(wallThickness));
					material.setLength(Float.parseFloat(length));
					material.setDxxs(Float.parseFloat(dxxs));
					material.setZz(Float.parseFloat(zz));
					material.setZs((int)Float.parseFloat(zs));
					material.setPieceWeight(Float.parseFloat(jz));
					material.setMinWeight(Float.parseFloat(minWeight));
					material.setMaxWeight(Float.parseFloat(maxWeight));
					material.setProduceDate(CommonUtils.getCurrentDate());
					material.setIsMatch(isMatch);
					material.setModifyTime(CommonUtils.getCurrentTime());
					System.out.println("�⾶="+outerDiameter+", �ں�="+wallThickness+", ����="+length+", ��пϵ��="+dxxs+", ֧��="+zz+", ֧��="+zs+", ����="+jz+", ���䷶Χ="+qjWeight+", ��С����="+minWeight+", �������="+maxWeight);
					
					/*boolean bExist = SwsQueryDBUtils.teamExist(teamCode);
					if(bExist)
					{
						int option = SwsMessageDialog.confirmDialog("������� "+teamCode+" �Ѵ��ڣ��Ƿ��滻���ݿ�İ�����Ϣ��", "�޸İ�����Ϣ��ʾ");
						if(option == SwsMessageDialog.OK_OPTION)
						{
							//д�����ݿ�
							boolean bUpdate= SwsUpdateDBUtils.updateTeam(team);
							while(!bUpdate)
							{
								bUpdate = SwsUpdateDBUtils.updateTeam(team);
							}
						}
					}
					else*/
					{
						//д�����ݿ�
						boolean bSave = SwsSaveDBUtils.addMaterial(material);
						while(!bSave)
						{
							bSave = SwsSaveDBUtils.addMaterial(material);
						}
					}
				}
				
				//���뺸����Ϣ
				List<String[]> hgList = ExcelUtil.readExcel(file, 1, hgTitles.length);
				
				//�ӵڶ��п�ʼ������һ���Ǳ���
				for(int i=1; i<hgList.size(); i++)
				{
					String[] materials = hgList.get(i);
					if("".equals(materials[0]))
						continue;
					String materialName = "����";
					String outerDiameter = materials[0];//�⾶
					String wallThickness = materials[1];//�ں�
					String length = materials[2];//����
					//String dxxs = materials[3];//��пϵ��
					String zz = materials[3];//֧��
					String zs = materials[4];//֧��
					String jz = materials[5];//����
					if("".equals(jz.trim()))
						continue;
					String qjWeight = materials[6];//��������
					
					System.out.println("qjWeight="+qjWeight);
					//��������С���������
					String minWeight = qjWeight.split("-")[0];
					String maxWeight = qjWeight.split("-")[1];
					if("����".equals(minWeight))
					{
						minWeight = "0";
					}
					else if("����".equals(maxWeight))
					{
						maxWeight = "999999";
					}
					
					float pieweight = Float.parseFloat(jz);
					float minW = Float.parseFloat(minWeight);
					float maxW = Float.parseFloat(maxWeight);
					
					String isMatch = "��";
					
					if(pieweight >= minW && pieweight <= maxW)
					{
						isMatch = "��";
					}
					
					//����
					String cun = CommonUtils.getCunByOuterDiameter(Float.parseFloat(outerDiameter));
					String materialAlias = materialName+cun+"-"+outerDiameter+"*"+wallThickness;
					String materialSpecifition = outerDiameter+"*"+wallThickness+"*"+length;
					
					Material material = new Material();
					material.setMaterialName(materialName);
					material.setMaterialAlias(materialAlias);
					material.setMaterialSpecifition(materialSpecifition);
					material.setMaterialDimension(cun);
					material.setOuterDiameter(Float.parseFloat(outerDiameter));
					material.setWallThickness(Float.parseFloat(wallThickness));
					material.setLength(Float.parseFloat(length));
					//material.setDxxs(Float.parseFloat(dxxs));
					material.setZz(Float.parseFloat(zz));
					material.setZs((int)Float.parseFloat(zs));
					material.setPieceWeight(Float.parseFloat(jz));
					material.setMinWeight(Float.parseFloat(minWeight));
					material.setMaxWeight(Float.parseFloat(maxWeight));
					material.setProduceDate(CommonUtils.getCurrentDate());
					material.setIsMatch(isMatch);
					material.setModifyTime(CommonUtils.getCurrentTime());
					
					System.out.println("�⾶="+outerDiameter+", �ں�="+wallThickness+", ����="+length/*+", ��пϵ��="+dxxs*/+", ֧��="+zz+", ֧��="+zs+", ����="+jz+", ���䷶Χ="+qjWeight+", ��С����="+minWeight+", �������="+maxWeight);
					
					/*boolean bExist = SwsQueryDBUtils.teamExist(teamCode);
					if(bExist)
					{
						int option = SwsMessageDialog.confirmDialog("������� "+teamCode+" �Ѵ��ڣ��Ƿ��滻���ݿ�İ�����Ϣ��", "�޸İ�����Ϣ��ʾ");
						if(option == SwsMessageDialog.OK_OPTION)
						{
							//д�����ݿ�
							boolean bUpdate= SwsUpdateDBUtils.updateTeam(team);
							while(!bUpdate)
							{
								bUpdate = SwsUpdateDBUtils.updateTeam(team);
							}
						}
					}
					else*/
					{
						//д�����ݿ�
						boolean bSave = SwsSaveDBUtils.addMaterial(material);
						while(!bSave)
						{
							bSave = SwsSaveDBUtils.addMaterial(material);
						}
					}
				}
				
				SwsParameter.swsEventManager.sendEvent(SwsParameter.DATABASE_UPDATE_EVENT);
				SwsMessageDialog.infoDialog("�������!");
			}
			else
			{
				SwsMessageDialog.errorDialog(path+" ������!�����µ��룡");
			}
		}
		else if(e.getSource().equals(downloadTemplateBtn))
		{
			
			List<List<String>> cells = new ArrayList<List<String>>();
			cells.add(Arrays.asList(dxTitles));
			cells.add(Arrays.asList(hgTitles));
			
			List<String> sheetNames = new ArrayList<String>();
			sheetNames.add("�ȶ�����������");
			sheetNames.add("��������������");
			
			ExcelUtil.exportExcel(null, cells, sheetNames, sheetNames.size(), "������Ϣ����ģ���", "����������Ϣ");
		}
		else 
		{
			System.out.println("������ť����.........");
		}
	}
}
