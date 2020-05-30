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
import com.sws.components.JButtonBoolean;
import com.sws.components.JSettingLabelTextField;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJLabel;
import com.sws.components.SwsJPanel;
import com.sws.components.SwsJTextField;
import com.sws.database.SwsQueryDBUtils;
import com.sws.database.SwsSaveDBUtils;
import com.sws.database.SwsUpdateDBUtils;
import com.sws.entity.Team;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.utils.CommonUtils;
import com.sws.utils.ExcelUtil;
import com.sws.utils.FileType;
/**
 * 导入班组信息
 * @author Ben
 *
 */

public class SmartWeighingExportTeamFrame extends JFrame implements ActionListener{
	
	private static SmartWeighingExportTeamFrame exportTeamFrame = null;
	//private JSettingLabelTextField excelPathTextField;
	private JTextField excelPathTextField;
	private JButton selectFileBtn;
	private JButton exportExcelBtn;
	private JButton downloadTemplateBtn;//下载模板
	private String[] titles = new String[]{"班组代码", "班组名称", "班组别名", "班别", "生产机组"};

	public SmartWeighingExportTeamFrame()
	{
		EventListener eListener = new EventListener();
		SwsParameter.swsEventManager.addListener(eListener);
		init();
	}
	
	private void init()
	{
		setTitle("导入班组信息");
		
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
		gridBagLayout.rowWeights = new double[]{1.0,1.0,1.0, 1.0, Double.MIN_VALUE};
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
		if(exportTeamFrame == null)
		{
			exportTeamFrame = new SmartWeighingExportTeamFrame();
			exportTeamFrame.setVisible(true);
		}
		else if(!exportTeamFrame.isVisible())
		{
			exportTeamFrame.setVisible(true);
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
				List<String[]> list = ExcelUtil.readExcel(file, 0, titles.length);
				
				//从第二行开始读，第一行是标题
				for(int i=1; i<list.size(); i++)
				{
					String[] teams = list.get(i);
					String teamCode = teams[0];
					String teamName = teams[1];
					String teamAlias = teams[2];
					String teamClass = teams[3];
					String produceUnit = teams[4];
					Team team = new Team();
					team.setTeamCode(teamCode);
					team.setTeamName(teamName);
					team.setTeamAlias(teamAlias);
					team.setTeamClass(teamClass);
					team.setProduceUnit(produceUnit);
					team.setModifyTime(CommonUtils.getCurrentTime());
					System.out.println("班组代码="+teamCode+", 班组名称="+teamName+", 班组别名="+teamAlias+", 班别="+teamClass+", 生产机组="+produceUnit);
					
					boolean bExist = SwsQueryDBUtils.teamExist(teamCode);
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
					else
					{
						//写入数据库
						boolean bSave = SwsSaveDBUtils.addTeam(team);
						while(!bSave)
						{
							bSave = SwsSaveDBUtils.addTeam(team);
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
			
			
			ExcelUtil.exportExcel(null, Arrays.asList(titles), "班组信息导入模板表", "保存班组信息");
		}
		else 
		{
			System.out.println("其他按钮触发.........");
		}
	}
}
