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
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sws.base.SwsMessageDialog;
import com.sws.base.SwsParameter;
import com.sws.components.AutoCloseDialog;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJComboBox;
import com.sws.components.SwsJLabel;
import com.sws.components.SwsJTextField;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.events.SWSEventManager;
import com.sws.searcher.DeviceInfo;
import com.sws.searcher.DeviceSearcher;
import com.sws.searcher.DeviceSearcherUDP;
import com.sws.utils.CommonUtils;

/**
 * 控制器调试设置界面
 * @author Ben
 *
 */
public class ControllDebugSettingFrame extends JFrame implements ActionListener,SWSEventListener{

	private static ControllDebugSettingFrame debugSettingFrame;
	private JButton searchDeviceBtn;
	private JButton openAllBtn;//全开
	private JButton closeAllBtn;//全关
	private JButton openBtn;
	private JButton closeBtn;
	private JComboBox<String> channelNumberComboBox;//通道号
	
	
	public ControllDebugSettingFrame()
	{
		SWSEventManager.addListener(this);
		
		setTitle("调试设置界面");
		setSize(300, 300);
		setLocationRelativeTo(null);

		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300};
		gridBagLayout.rowHeights = new int[]{300};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		int gridX = 0;
		int gridY = 0;
		
		JPanel settingPanel = createSettingPanel();
		GridBagConstraints gbc_settingPanel = new GridBagConstraints();
		gbc_settingPanel.fill = GridBagConstraints.BOTH;
		gbc_settingPanel.insets = new Insets(0, 0, 0, 2);
		gbc_settingPanel.gridx = gridX;
		gbc_settingPanel.gridy = gridY;
		add(settingPanel,gbc_settingPanel);
	}
	
	private JPanel createSettingPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{150, 150};
		gridBagLayout.rowHeights = new int[]{35, 35, 35};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		//searchDeviceBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "搜索设备", this);
		new SwsJLabel(panel, gridX, gridY, "通道号：");
		
		gridX++;
		
		//channelNumberTextField = new SwsJTextField(panel, gridX, gridY, 1);
		channelNumberComboBox = new SwsJComboBox(panel, gridX, gridY);
		channelNumberComboBox.addItem("1");
		channelNumberComboBox.addItem("2");
		channelNumberComboBox.addItem("3");
		channelNumberComboBox.addItem("4");
		
		gridX=0;
		gridY++;
		
		openBtn = new SwsJButton(panel, gridX, gridY, "打开", this);
		
		gridX++;
		
		closeBtn = new SwsJButton(panel, gridX, gridY, "关闭", this);
		
		gridX=0;
		gridY++;
		
		openAllBtn = new SwsJButton(panel, gridX, gridY, "全部打开", this);
		
		gridX++;
		
		closeAllBtn = new SwsJButton(panel, gridX, gridY, "全部关闭", this);
		
		return panel;
	}
	
	public static void showFrame()
	{
		if(debugSettingFrame == null)
		{
			debugSettingFrame = new ControllDebugSettingFrame();
			debugSettingFrame.setVisible(true);
		}
		else if(!debugSettingFrame.isVisible())
		{
			debugSettingFrame.setVisible(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(openBtn))
		{
			//String channelNumber = channelNumberTextField.getText().trim();
			String channelNumber = (String) channelNumberComboBox.getSelectedItem();

			byte[] buffer = new byte[10];
			buffer[0] = (byte)0xCC;
			buffer[1] = (byte)0xDD;
			buffer[2] = (byte)0xA1;
			buffer[3] = (byte)0x01;
			buffer[4] = (byte)0x00;
			if("1".equals(channelNumber))
			{
				buffer[5] = (byte)0x01;
				buffer[6] = (byte)0x00;
				buffer[7] = (byte)0x01;
				buffer[8] = (byte)0xA4;
				buffer[9] = (byte)0x48;
			}
			else if("2".equals(channelNumber))
			{
				buffer[5] = (byte)0x02;
				buffer[6] = (byte)0x00;
				buffer[7] = (byte)0x02;
				buffer[8] = (byte)0xA6;
				buffer[9] = (byte)0x4C;
			}
			else if("3".equals(channelNumber))
			{
				buffer[5] = (byte)0x04;
				buffer[6] = (byte)0x00;
				buffer[7] = (byte)0x04;
				buffer[8] = (byte)0xAA;
				buffer[9] = (byte)0x54;
			}
			else if("4".equals(channelNumber))
			{
				buffer[5] = (byte)0x08;
				buffer[6] = (byte)0x00;
				buffer[7] = (byte)0x08;
				buffer[8] = (byte)0xB2;
				buffer[9] = (byte)0x64;
			}
			else 
			{
				SwsMessageDialog.warningDialog("通道号只能输入 1至4的数字!");
				return;
			}
			
			String message = CommonUtils.BinaryToHexString(buffer, buffer.length);
			
			SwsParameter.sendNvram(SwsParameter.SETTING_EVENT, SwsParameter.sNvramControllSendMessage, message);
		}
		else if(e.getSource().equals(closeBtn))
		{
			String channelNumber = (String) channelNumberComboBox.getSelectedItem();
			
			byte[] buffer = new byte[10];
			buffer[0] = (byte)0xCC;
			buffer[1] = (byte)0xDD;
			buffer[2] = (byte)0xA1;
			buffer[3] = (byte)0x01;
			buffer[4] = (byte)0x00;
			if("1".equals(channelNumber))
			{
				buffer[5] = (byte)0x00;
				buffer[6] = (byte)0x00;
				buffer[7] = (byte)0x01;
				buffer[8] = (byte)0xA3;
				buffer[9] = (byte)0x46;
			}
			else if("2".equals(channelNumber))
			{
				buffer[5] = (byte)0x00;
				buffer[6] = (byte)0x00;
				buffer[7] = (byte)0x02;
				buffer[8] = (byte)0xA4;
				buffer[9] = (byte)0x48;
			}
			else if("3".equals(channelNumber))
			{
				buffer[5] = (byte)0x00;
				buffer[6] = (byte)0x00;
				buffer[7] = (byte)0x04;
				buffer[8] = (byte)0xA6;
				buffer[9] = (byte)0x4C;
			}
			else if("4".equals(channelNumber))
			{
				buffer[5] = (byte)0x00;
				buffer[6] = (byte)0x00;
				buffer[7] = (byte)0x08;
				buffer[8] = (byte)0xAA;
				buffer[9] = (byte)0x54;
			}
			else 
			{
				SwsMessageDialog.warningDialog("通道号只能输入 1至4的数字!");
				return;
			}
			
			String message = CommonUtils.BinaryToHexString(buffer, buffer.length);
			
			SwsParameter.sendNvram(SwsParameter.SETTING_EVENT, SwsParameter.sNvramControllSendMessage, message);
		}
		else if(e.getSource().equals(openAllBtn))
		{
			byte[] buffer = new byte[10];
			buffer[0] = (byte)0xCC;
			buffer[1] = (byte)0xDD;
			buffer[2] = (byte)0xA1;
			buffer[3] = (byte)0x01;
			buffer[4] = (byte)0xFF;
			buffer[5] = (byte)0xFF;
			buffer[6] = (byte)0xFF;
			buffer[7] = (byte)0xFF;
			buffer[8] = (byte)0x9E;
			buffer[9] = (byte)0x3C;
			
			String message = CommonUtils.BinaryToHexString(buffer, buffer.length);
			System.out.println("message="+message);
			SwsParameter.sendNvram(SwsParameter.SETTING_EVENT, SwsParameter.sNvramControllSendMessage, message);
		}
		else if(e.getSource().equals(closeAllBtn))
		{
			byte[] buffer = new byte[10];
			buffer[0] = (byte)0xCC;
			buffer[1] = (byte)0xDD;
			buffer[2] = (byte)0xA1;
			buffer[3] = (byte)0x01;
			buffer[4] = (byte)0x00;
			buffer[5] = (byte)0x00;
			buffer[6] = (byte)0xFF;
			buffer[7] = (byte)0xFF;
			buffer[8] = (byte)0xA0;
			buffer[9] = (byte)0x40;
			
			String message = CommonUtils.BinaryToHexString(buffer, buffer.length);
			
			SwsParameter.sendNvram(SwsParameter.SETTING_EVENT, SwsParameter.sNvramControllSendMessage, message);
		}
	}

	@Override
	public void lmsTransferEvent(SWSEvent event) {
		// TODO Auto-generated method stub
		String eventType = event.getEventType();
		HashMap eventExtra = event.getEventExtra();
	}
}
