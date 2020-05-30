package com.sws.frame;

import com.sun.javafx.webkit.ThemeClientImpl;
import com.sws.base.EnumType;
import com.sws.base.SwsMessageDialog;
import com.sws.base.SwsParameter;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJLabel;
import com.sws.components.SwsJPasswordField;
import com.sws.components.SwsJTextField;
import com.sws.database.SwsQueryDBUtils;
import com.sws.database.SwsSaveDBUtils;
import com.sws.device.SerialPortBase;
import com.sws.device.SocketClientBase;
import com.sws.utils.CommonUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * ��¼����
 * @author Ben
 *
 */
public class SmartWeighingLoginFrame extends JFrame implements ActionListener {

	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private JButton loginBtn;
	private JButton cancelBtn;

	public SmartWeighingLoginFrame()
	{
		setTitle("ϵͳ��¼����");
		setSize(310, 300);
		setLocationRelativeTo(null);

		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{310};
		gridBagLayout.rowHeights = new int[]{300};
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
		
		setVisible(true);
	}

	private JPanel createLoginPanel()
	{
		JPanel panel = new JPanel();

		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 70, 70, 70, 50};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);

		int gridX = 1,gridY = 0;
		
		JLabel titleLabel = new SwsJLabel(panel, GridBagConstraints.BOTH, 40, 0, gridX, gridY, 3, 1, "�û���¼");
		titleLabel.setFont(new Font("����", Font.BOLD, 30));
		
		gridX=1;
		gridY++;

		new SwsJLabel(panel, GridBagConstraints.EAST, 0, 0, gridX, gridY, 1, 1, "�û�����");

		gridX++;

		usernameTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY,2,1);

		gridX=1;
		gridY++;

		new SwsJLabel(panel, GridBagConstraints.EAST, 0, 0, gridX, gridY, 1, 1, "��  �룺");

		gridX++;

		passwordTextField = new SwsJPasswordField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY,2,1);

		gridX=1;
		gridY++;

		loginBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, gridX, gridY,1, 1, "��¼", this);
		loginBtn.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		gridX+=2;

		cancelBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, gridX, gridY,1, 1, "ȡ��", this);

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("���е�����.........");
		if(e.getSource().equals(loginBtn))
		{
			System.out.println("���е������½");
			if("".equals(usernameTextField.getText()))
			{
				SwsMessageDialog.warningDialog("�û�������Ϊ��!");
			}
			else if("".equals(new String(passwordTextField.getPassword())))
			{
				SwsMessageDialog.warningDialog("���벻��Ϊ��!");
			}
			else 
			{
				//��¼
				boolean blogin = SwsQueryDBUtils.login(usernameTextField.getText(), new String(passwordTextField.getPassword()));
				if(blogin) {
					SwsParameter.loginUserName = usernameTextField.getText();
					SwsSaveDBUtils.saveLog("��¼ϵͳ", "��¼ϵͳ", usernameTextField.getText(),CommonUtils.getCurrentTime(),"");
					this.dispose();
					new SmartWeightingFrame();
					//���������������߳�
					new SocketClientBase().start();
					String baudrate = SwsParameter.enumProtocolBaudrateType.key;
					//�������������ֳ�
					new SerialPortBase(SwsParameter.sNvramWeightSerialPort.sValue, Integer.parseInt(SwsParameter.enumProtocolBaudrateType.getValueFromKey(baudrate))).start();
				}
				else
				{
					SwsMessageDialog.errorDialog("�û��������������,�����µ�¼��");
				}
			}
		}
		else
		{
			this.dispose();
		}
	}

}
