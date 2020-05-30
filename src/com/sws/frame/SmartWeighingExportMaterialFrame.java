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
	private JButton downloadTemplateBtn;//下载模板
	
	private String[] dxTitles = new String[]{"外径", "壁厚", "长度", "镀锌系数", "支重", "支数", "件重", "区间范围"};//镀锌
	private String[] hgTitles = new String[]{"外径", "壁厚", "长度", "支重", "支数", "件重", "区间范围"};//焊管

	public SmartWeighingExportMaterialFrame()
	{
		EventListener eListener = new EventListener();
		SwsParameter.swsEventManager.addListener(eListener);
		init();
	}
	
	private void init()
	{
		setTitle("导入物料信息");
		
		setSize(600, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		
		//设置布局
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
		
		//excelPathTextField = new JSettingLabelTextField(panel, gridX, gridY, 2, 4, "请选择Excel路径：", true, true, false, SwsParameter.iNvramTeamCode);
		new SwsJLabel(panel,GridBagConstraints.EAST, 0, 0, gridX, gridY, 2, 1, "请选择Excel路径：");
		
		gridX+=2;
		excelPathTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 3, 1);
		
		gridX+=3;
		
		selectFileBtn = new SwsJButton(panel, gridX, gridY, 1, "选择Excel文件", this);
		
		gridY++;
		gridX=1;
		
		downloadTemplateBtn = new SwsJButton(panel, gridX, gridY, 2, "下载Excel模板", this);
		
		gridX+=2;
		
		//new JButtonBoolean(panel, gridX, gridY, 2, "导入信息", "导入信息", false, false, SwsParameter.bNvramMaterialsMessageMaintain);
		exportExcelBtn = new SwsJButton(panel, gridX, gridY, 2, "导入信息", this);
		
		gridX=0;
		gridY++;
		
		JLabel showLabel = new SwsJLabel(panel, GridBagConstraints.BOTH, 70, 0, gridX, gridY, 6, 1, "说明：导入前先下载Excel模板，按照模板来填写内容，避免导入失败！");
		showLabel.setForeground(Color.RED);
		
		gridY++;
		
		JLabel showLabel2 = new SwsJLabel(panel, GridBagConstraints.BOTH, 70, 0, gridX, gridY, 6, 1, "物料信息只能导入一次，以后在物料信息维护里面进行维护，否则容易出现问题");
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
					String path = CommonUtils.selectFilePath("D:\\", "选择Excel文件");
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
			String path = CommonUtils.selectFilePath("D:\\", "选择Excel文件", FileType.EXCEL);
			System.out.println("path="+path);
			excelPathTextField.setText(path);
		}
		else if(e.getSource().equals(exportExcelBtn))
		{
			String path = excelPathTextField.getText().trim();
			if("".equals(path))
			{
				SwsMessageDialog.warningDialog("请选择要导入的Excel文件");
				return;
			}
			
			File file = new File(path);
			
			if(file.exists())
			{
				List<String[]> dxList = ExcelUtil.readExcel(file, 0, dxTitles.length);
				
				//从第二行开始读，第一行是标题
				for(int i=1; i<dxList.size(); i++)
				{
					String[] materials = dxList.get(i);
					if("".equals(materials[0]))
						continue;
					String materialName = "热镀锌管";
					
					String outerDiameter = materials[0];//外径
					String wallThickness = materials[1];//壁厚
					String length = materials[2];//长度
					String dxxs = materials[3];//镀锌系数
					String zz = materials[4];//支重
					String zs = materials[5];//支数
					String jz = materials[6];//件重
					String qjWeight = materials[7];//区间重量
					
					//解析出最小和最大重量
					String minWeight = qjWeight.split("-")[0];
					String maxWeight = qjWeight.split("-")[1];
					if("以下".equals(minWeight))
					{
						minWeight = "0";
					}
					else if("以上".equals(maxWeight))
					{
						maxWeight = "999999";
					}
					
					float pieweight = Float.parseFloat(jz);
					float minW = Float.parseFloat(minWeight);
					float maxW = Float.parseFloat(maxWeight);
					
					String isMatch = "否";
					
					if(pieweight >= minW && pieweight <= maxW)
					{
						isMatch = "是";
					}
					
					//计算
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
					System.out.println("外径="+outerDiameter+", 壁厚="+wallThickness+", 长度="+length+", 镀锌系数="+dxxs+", 支重="+zz+", 支数="+zs+", 件重="+jz+", 区间范围="+qjWeight+", 最小重量="+minWeight+", 最大重量="+maxWeight);
					
					/*boolean bExist = SwsQueryDBUtils.teamExist(teamCode);
					if(bExist)
					{
						int option = SwsMessageDialog.confirmDialog("班组代码 "+teamCode+" 已存在，是否替换数据库的班组信息？", "修改班组信息提示");
						if(option == SwsMessageDialog.OK_OPTION)
						{
							//写入数据库
							boolean bUpdate= SwsUpdateDBUtils.updateTeam(team);
							while(!bUpdate)
							{
								bUpdate = SwsUpdateDBUtils.updateTeam(team);
							}
						}
					}
					else*/
					{
						//写入数据库
						boolean bSave = SwsSaveDBUtils.addMaterial(material);
						while(!bSave)
						{
							bSave = SwsSaveDBUtils.addMaterial(material);
						}
					}
				}
				
				//导入焊管信息
				List<String[]> hgList = ExcelUtil.readExcel(file, 1, hgTitles.length);
				
				//从第二行开始读，第一行是标题
				for(int i=1; i<hgList.size(); i++)
				{
					String[] materials = hgList.get(i);
					if("".equals(materials[0]))
						continue;
					String materialName = "焊管";
					String outerDiameter = materials[0];//外径
					String wallThickness = materials[1];//壁厚
					String length = materials[2];//长度
					//String dxxs = materials[3];//镀锌系数
					String zz = materials[3];//支重
					String zs = materials[4];//支数
					String jz = materials[5];//件重
					if("".equals(jz.trim()))
						continue;
					String qjWeight = materials[6];//区间重量
					
					System.out.println("qjWeight="+qjWeight);
					//解析出最小和最大重量
					String minWeight = qjWeight.split("-")[0];
					String maxWeight = qjWeight.split("-")[1];
					if("以下".equals(minWeight))
					{
						minWeight = "0";
					}
					else if("以上".equals(maxWeight))
					{
						maxWeight = "999999";
					}
					
					float pieweight = Float.parseFloat(jz);
					float minW = Float.parseFloat(minWeight);
					float maxW = Float.parseFloat(maxWeight);
					
					String isMatch = "否";
					
					if(pieweight >= minW && pieweight <= maxW)
					{
						isMatch = "是";
					}
					
					//计算
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
					
					System.out.println("外径="+outerDiameter+", 壁厚="+wallThickness+", 长度="+length/*+", 镀锌系数="+dxxs*/+", 支重="+zz+", 支数="+zs+", 件重="+jz+", 区间范围="+qjWeight+", 最小重量="+minWeight+", 最大重量="+maxWeight);
					
					/*boolean bExist = SwsQueryDBUtils.teamExist(teamCode);
					if(bExist)
					{
						int option = SwsMessageDialog.confirmDialog("班组代码 "+teamCode+" 已存在，是否替换数据库的班组信息？", "修改班组信息提示");
						if(option == SwsMessageDialog.OK_OPTION)
						{
							//写入数据库
							boolean bUpdate= SwsUpdateDBUtils.updateTeam(team);
							while(!bUpdate)
							{
								bUpdate = SwsUpdateDBUtils.updateTeam(team);
							}
						}
					}
					else*/
					{
						//写入数据库
						boolean bSave = SwsSaveDBUtils.addMaterial(material);
						while(!bSave)
						{
							bSave = SwsSaveDBUtils.addMaterial(material);
						}
					}
				}
				
				SwsParameter.swsEventManager.sendEvent(SwsParameter.DATABASE_UPDATE_EVENT);
				SwsMessageDialog.infoDialog("导入完成!");
			}
			else
			{
				SwsMessageDialog.errorDialog(path+" 不存在!请重新导入！");
			}
		}
		else if(e.getSource().equals(downloadTemplateBtn))
		{
			
			List<List<String>> cells = new ArrayList<List<String>>();
			cells.add(Arrays.asList(dxTitles));
			cells.add(Arrays.asList(hgTitles));
			
			List<String> sheetNames = new ArrayList<String>();
			sheetNames.add("热镀理论重量表");
			sheetNames.add("焊管理论重量表");
			
			ExcelUtil.exportExcel(null, cells, sheetNames, sheetNames.size(), "物料信息导入模板表", "保存物料信息");
		}
		else 
		{
			System.out.println("其他按钮触发.........");
		}
	}
}
