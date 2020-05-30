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
		//��ʼ��
		init();
	}
	
	private void init()
	{
		try {
			//��ϵͳ������ʾ
			//setTrayIcon(this);
			//���ñ���logoͼƬ
			setIconImage(ImageIO.read(getClass().getResource("/java.jpg")));
			//���ñ���
			setTitle("���������ܳ���ϵͳ");
			//���ô����С
			setSize(1000, 700);
			//���ô������
			setLocationRelativeTo(null);
			//��ֹ���
			setResizable(true);
			//���ùرշ�ʽ
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//���沼��
			setFrameLayout();
			//�������ɼ�
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
		
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT); //����ѡ��Ĳ��ַ�ʽ��
		
		tabbedPane.addTab("���������", new SmartWeighingMainFrame().showFrame());
		tabbedPane.addTab("ϵͳ��������", new SmartWeighingSystemSettingFrame().showFrame());
		tabbedPane.addTab("�û�����", new SmartWeighingUserManagementFrame().showFrame());
		tabbedPane.addTab("����ά��", new SmartWeighingMaintainFrame().showFrame());
		tabbedPane.addTab("���ݼ�¼����", new SmartWeighingSearchFrame().showFrame());
		tabbedPane.addTab("ͳ�Ʊ���", new SmartWeighingStatisticalReportFrame().showFrame());
		tabbedPane.addTab("��־����", new SmartWeighingLogManagementFrame().showFrame());
		tabbedPane.addTab("����", new SmartWeighingAboutFrame().showFrame());
	}
	
	private void setTrayIcon(JFrame frame)
	{
		//ʹ��ϵͳ������ʾ
		SystemTray systemTray;
		TrayIcon trayIcon = null;
		if (SystemTray.isSupported()) { //��ǰƽ̨�Ƿ�֧��ϵͳ����   
			frame.setType(java.awt.Window.Type.UTILITY); //ʹӦ�ó���ͼ�겻��ϵͳ״̬����ʾ

			//����һ���Ҽ������˵�
			PopupMenu popupMenu = new PopupMenu();
			MenuItem mi = new MenuItem("�������");
			MenuItem exit = new MenuItem("�˳�");
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
			//��������ͼ��
			try {
				trayIcon = new TrayIcon(ImageIO.read(getClass().getResource("/java.jpg")), "���ܳ��زɼ�ϵͳ", popupMenu);
				//trayIcon = new TrayIcon(null, "���ܳ��زɼ�ϵͳ", popupMenu);
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
			
			
			//��ȡ���̲˵�
			systemTray = SystemTray.getSystemTray();
			//�������ͼ��
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
