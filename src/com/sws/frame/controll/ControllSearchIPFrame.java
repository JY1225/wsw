package com.sws.frame.controll;

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
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.sws.base.SwsMessageDialog;
import com.sws.base.SwsParameter;
import com.sws.components.AutoCloseDialog;
import com.sws.components.SwsJButton;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.events.SWSEventManager;
import com.sws.searcher.DeviceInfo;
import com.sws.searcher.DeviceSearcher;
import com.sws.searcher.DeviceSearcherUDP;

public class ControllSearchIPFrame extends JFrame implements ActionListener,SWSEventListener{

	private static ControllSearchIPFrame searchIPFrame;
	private JPanel tablePanel;
	private JButton searchDeviceBtn;
	private AutoCloseDialog autoCloseDialog;
	
	private String[] titles = new String[]{"序号", "设备IP", "设备MAC", "本地IP"};
	
	public ControllSearchIPFrame()
	{
		SWSEventManager.addListener(this);
		
		setTitle("搜索IP配置界面");
		setSize(700, 300);
		setLocationRelativeTo(null);

		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500, 200};
		gridBagLayout.rowHeights = new int[]{300};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		int gridX = 0;
		int gridY = 0;
		
		tablePanel = createTablePanel();
		GridBagConstraints gbc_tablePanel = new GridBagConstraints();
		gbc_tablePanel.fill = GridBagConstraints.BOTH;
		gbc_tablePanel.insets = new Insets(0, 0, 0, 2);
		gbc_tablePanel.gridx = gridX;
		gbc_tablePanel.gridy = gridY;
		add(tablePanel,gbc_tablePanel);
		
		gridX++;
		
		JPanel settingPanel = createSettingPanel();
		GridBagConstraints gbc_settingPanel = new GridBagConstraints();
		gbc_settingPanel.fill = GridBagConstraints.BOTH;
		gbc_settingPanel.insets = new Insets(0, 0, 0, 2);
		gbc_settingPanel.gridx = gridX;
		gbc_settingPanel.gridy = gridY;
		add(settingPanel,gbc_settingPanel);
		
		displayTable(titles, null);
	}
	
	private JPanel createTablePanel()
	{
		tablePanel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500};
		gridBagLayout.rowHeights = new int[]{300};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		tablePanel.setLayout(gridBagLayout);
		
		return tablePanel;
	}
	
	private JPanel createSettingPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100};
		gridBagLayout.rowHeights = new int[]{35, 35, 35,35, 35, 35, 35, 35};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		searchDeviceBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "搜索设备", this);
		
		return panel;
	}
	
	private void displayTable(String[] titles, Object[][] data)
	{
		tablePanel.removeAll();
		tablePanel.revalidate();

		JTable table = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel(data, titles)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		table.setModel(tableModel);
		
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
				} 
			}
		});
	}
	
	public static void showFrame()
	{
		if(searchIPFrame == null)
		{
			searchIPFrame = new ControllSearchIPFrame();
			searchIPFrame.setVisible(true);
		}
		else if(!searchIPFrame.isVisible())
		{
			searchIPFrame.setVisible(true);
		}
	}
	
	public void setTips()
	{
		searchDeviceBtn.setEnabled(false);
		DeviceSearcherUDP.infoList.clear();
		autoCloseDialog = new AutoCloseDialog("提示", "正在搜索中...");
		setTimerTask();
	}
	
	TimerTask task = null;
	private void setTimerTask()
	{
		task = new TimerTask()
		{
			@Override
			public void run()
			{
				if(autoCloseDialog != null)
				{
					autoCloseDialog.dialog.dispose();
					searchDeviceBtn.setEnabled(true);
					SwsMessageDialog.infoDialog("搜索完成!");
				}
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(task, 3000);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(searchDeviceBtn))
		{
			setTips();
			new DeviceSearcher();
		}
	}

	@Override
	public void lmsTransferEvent(SWSEvent event) {
		// TODO Auto-generated method stub
		String eventType = event.getEventType();
		HashMap eventExtra = event.getEventExtra();
		
		if(SwsParameter.DEVICE_SEARCH_RESULT_EVENT.endsWith(eventType))
		{
			Object[][] data = null;
			if(DeviceSearcherUDP.infoList.size() > 0)
			{
				//生成表格
				data = new Object[DeviceSearcherUDP.infoList.size()][titles.length];
				for(int i=0; i<DeviceSearcherUDP.infoList.size(); i++)
				{
					DeviceInfo deviceInfo = DeviceSearcherUDP.infoList.get(i);
					data[i][0] = (i+1);
					data[i][1] = deviceInfo.getDeviceIP();
					data[i][2] = deviceInfo.getDeviceMac();
					data[i][3] = deviceInfo.getLocalIP();
				}
			}
			
			displayTable(titles, data);
		}
	}
}
