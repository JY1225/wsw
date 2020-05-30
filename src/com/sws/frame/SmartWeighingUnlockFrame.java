package com.sws.frame;

import com.sws.base.SwsMessageDialog;
import com.sws.base.SwsParameter;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJLabel;
import com.sws.components.SwsJPasswordField;
import com.sws.components.SwsJTextField;
import com.sws.database.SwsQueryDBUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 解锁界面
 * @author Ben
 *
 */
public class SmartWeighingUnlockFrame extends JFrame implements ActionListener {

	private JTextField passwordTextField;
	private static SmartWeighingUnlockFrame smartWeighingUnlockFrame = null;
	public static boolean unlock = false;

	public SmartWeighingUnlockFrame()
	{
		setTitle("解锁界面");
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
		
		//setVisible(true);
		//setAlwaysOnTop(true);
	}

	public static void showFrame()
	{
		if(smartWeighingUnlockFrame == null)
		{
			smartWeighingUnlockFrame = new SmartWeighingUnlockFrame();
			smartWeighingUnlockFrame.setVisible(true);
		}
		else if(!smartWeighingUnlockFrame.isVisible())
		{
			smartWeighingUnlockFrame.setVisible(true);
		}
	}

	private JPanel createLoginPanel()
	{
		JPanel panel = new JPanel();

		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 200, 200, 50};
		gridBagLayout.rowHeights = new int[]{100, 100};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0,1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);

		int gridX = 1,gridY = 0;

		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "请您输入解锁密码");

		gridX++;

		passwordTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY,1,1);

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
			String password = passwordTextField.getText();
			if("admin".equals(password))
			{
				this.dispose();
				SwsParameter.swsEventManager.sendEvent(SwsParameter.UNLOCK_EVENT);
			}
			else
			{
				SwsMessageDialog.errorDialog("密码错误,请重新输入！！！");
			}
		}
		else
		{
			this.dispose();
		}
	}

}
