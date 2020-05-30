package com.sws.frame;

import java.awt.AWTException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class SmartWeightingFrame extends JFrame{

	public SmartWeightingFrame() 
	{
		//初始化
		init();
	}
	
	private void init()
	{
		try {
			//在系统托盘显示
			//setTrayIcon(this);
			//设置标题logo图片
			setIconImage(ImageIO.read(getClass().getResource("/java.jpg")));
			//设置标题
			setTitle("物联网智能称重系统");
			//设置窗体大小
			setSize(1000, 700);
			//设置窗体居中
			setLocationRelativeTo(null);
			//禁止最大化
			setResizable(true);
			//设置关闭方式
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//界面布局
			setFrameLayout();
			//设置面板可见
			setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setFrameLayout()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT); //设置选项卡的布局方式。
		
		tabbedPane.addTab("检测主界面", new SmartWeighingMainFrame().showFrame());
		tabbedPane.addTab("系统参数设置", new SmartWeighingSystemSettingFrame().showFrame());
		tabbedPane.addTab("用户管理", new SmartWeighingUserManagementFrame().showFrame());
		tabbedPane.addTab("数据维护", new SmartWeighingMaintainFrame().showFrame());
		tabbedPane.addTab("数据记录管理", new SmartWeighingSearchFrame().showFrame());
		tabbedPane.addTab("统计报表", new SmartWeighingStatisticalReportFrame().showFrame());
		tabbedPane.addTab("日志管理", new SmartWeighingLogManagementFrame().showFrame());
		tabbedPane.addTab("关于", new SmartWeighingAboutFrame().showFrame());
	}
	
	private void setTrayIcon(JFrame frame)
	{
		//使在系统托盘显示
		SystemTray systemTray;
		TrayIcon trayIcon = null;
		if (SystemTray.isSupported()) { //当前平台是否支持系统托盘   
			frame.setType(java.awt.Window.Type.UTILITY); //使应用程序图标不在系统状态栏显示

			//创建一个右键弹出菜单
			PopupMenu popupMenu = new PopupMenu();
			MenuItem mi = new MenuItem("打开主面板");
			MenuItem exit = new MenuItem("退出");
			popupMenu.add(mi);
			popupMenu.add(exit);
			mi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(true);
				}

			});
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					System.exit(0);
				}
			});
			//创建托盘图标
			try {
				trayIcon = new TrayIcon(ImageIO.read(getClass().getResource("/java.jpg")), "智能称重采集系统", popupMenu);
				//trayIcon = new TrayIcon(null, "智能称重采集系统", popupMenu);
				trayIcon.setImageAutoSize(true);
				trayIcon.addMouseListener(
				new MouseAdapter()
				{
					@Override
					public void mouseClicked(MouseEvent e)
					{
						if(frame.isVisible())
							frame.setVisible(false);
						else
							frame.setVisible(true);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			//获取托盘菜单
			systemTray = SystemTray.getSystemTray();
			//添加托盘图标
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}

			frame.setVisible(false);
		}
		else
		{
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
}
