package com.sws.frame;

import com.sws.base.SwsMessageDialog;
import com.sws.base.SwsParameter;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJLabel;
import com.sws.components.SwsJPasswordField;
import com.sws.components.SwsJTextField;
import com.sws.database.SwsUpdateDBUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 修改密码界面
 * @author Ben
 *
 */
public class SmartWeighingUpdatePasswordFrame extends JFrame implements ActionListener, WindowListener {

	
	private static SmartWeighingUpdatePasswordFrame updatePasswordFrame = null;
	private JTextField usernameTextField;
	private JPasswordField originalPasswordTextField;
	private JPasswordField newPasswordTextField;
	private JPasswordField confirmPasswordTextField;
	private String username;

	public SmartWeighingUpdatePasswordFrame(String username)
	{
		this.username = username;
		setTitle("修改密码界面");
		setSize(500, 200);
		setLocationRelativeTo(null);

		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500};
		gridBagLayout.rowHeights = new int[]{200};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JPanel teamSetPanel = createLoginPanel();
		GridBagConstraints gbc_teamSetPanel = new GridBagConstraints();
		gbc_teamSetPanel.fill = GridBagConstraints.BOTH;
		gbc_teamSetPanel.insets = new Insets(0, 0, 0, 2);
		gbc_teamSetPanel.gridx = 0;
		gbc_teamSetPanel.gridy = 0;
		add(teamSetPanel,gbc_teamSetPanel);
		
		addWindowListener(this);
	}

	public static void showFrame(String username)
	{
		if(updatePasswordFrame == null)
		{
			updatePasswordFrame = new SmartWeighingUpdatePasswordFrame(username);
			updatePasswordFrame.setVisible(true);
		}
		else if(!updatePasswordFrame.isVisible())
		{
			updatePasswordFrame.setVisible(true);
		}
	}

	private JPanel createLoginPanel()
	{
		JPanel panel = new JPanel();

		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 100, 200, 100, 50};
		gridBagLayout.rowHeights = new int[]{25, 50, 50, 50, 50, 50, 25};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);

		int gridX = 1,gridY = 1;

		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "用户名：");

		gridX++;

		usernameTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY,1,1);
		usernameTextField.setText(SwsParameter.loginUserName);
		usernameTextField.setEditable(false);
		
		gridX=1;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "原密码：");

		gridX++;

		originalPasswordTextField = new SwsJPasswordField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY,1,1);
		
		gridX=1;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "新密码：");

		gridX++;

		newPasswordTextField = new SwsJPasswordField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY,1,1);
		
		gridX=1;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "确认新密码：");

		gridX++;

		confirmPasswordTextField = new SwsJPasswordField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY,1,1);

		gridX=1;
		gridY++;

		new SwsJButton(panel, GridBagConstraints.HORIZONTAL, gridX, gridY,1, 1, "确定", this::actionPerformed);

		gridX++;

		new SwsJButton(panel, GridBagConstraints.HORIZONTAL, gridX, gridY,1, 1, "取消", this::actionPerformed);

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if("确定".equals(e.getActionCommand()))
		{
			String originalPassword = new String(originalPasswordTextField.getPassword());
			String newPassword = new String(newPasswordTextField.getPassword());
			String confirmPassword = new String(confirmPasswordTextField.getPassword());
			if("".equals(originalPassword))
			{
				SwsMessageDialog.warningDialog("原密码不能为空!");
			}
			else if("".equals(newPassword))
			{
				SwsMessageDialog.warningDialog("新密码不能为空!");
			}
			else if("".equals(confirmPassword))
			{
				SwsMessageDialog.warningDialog("确认新密码不能为空!");
			}
			else if(!newPassword.equals(confirmPassword))
			{
				SwsMessageDialog.warningDialog("两次输入的新密码不一致，请重新输入!");
			}
			else if(newPassword.equals(confirmPassword))
			{
				boolean bSuccess = SwsUpdateDBUtils.updatePassword(SwsParameter.loginUserName, originalPassword, newPassword);
				if(bSuccess)
				{
					SwsMessageDialog.infoDialog("密码修改成功，请记住您的新密码!");
				}
				else 
				{
					SwsMessageDialog.errorDialog("密码修改失败，请检查原密码是否正确!");
				}
			}
			else 
			{
				System.out.println("其他操作..............");
			}
		}
		else
		{
			this.dispose();
			updatePasswordFrame = null;
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("关闭窗口事件");
		updatePasswordFrame = null;
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
