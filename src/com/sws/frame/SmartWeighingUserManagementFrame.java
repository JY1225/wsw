package com.sws.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import com.sws.base.SwsMessageDialog;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJLabel;
import com.sws.components.SwsJRadioButton;
import com.sws.components.SwsJTextField;
import com.sws.database.SwsDeleteDBUtils;
import com.sws.database.SwsQueryDBUtils;
import com.sws.database.SwsSaveDBUtils;
import com.sws.database.SwsUpdateDBUtils;
import com.sws.entity.User;

/**
 * �û�����
 * @author Ben
 *
 */
public class SmartWeighingUserManagementFrame implements ActionListener{

	
	private JRadioButton adminJRB;
	//private JRadioButton weightAdminJRB;
	private JRadioButton operatorJRB;
	//private JRadioButton monitorJRB;
	private JButton addUserBtn;
	private JButton deleteUserBtn;
	private JButton clearUserBtn;
	private JButton updatePasswordBtn;
	private JButton updateAuthorityBtn;
	private JButton switchUserBtn;
	
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	private JTextArea remarkTextArea;
	
	private JPanel tablePanel;
	
	public JPanel showFrame()
	{
		JPanel panel = new JPanel();
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300, 700};
		gridBagLayout.rowHeights = new int[]{700};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		JPanel buttonPanel = createConditionPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.insets = new Insets(0, 0, 0, 5);
		gbc_buttonPanel.gridx = gridX;
		gbc_buttonPanel.gridy = gridY;
		panel.add(buttonPanel,gbc_buttonPanel);
		
		gridX++;
		
		tablePanel = createTablePanel();
		GridBagConstraints gbc_tablePanel = new GridBagConstraints();
		gbc_tablePanel.fill = GridBagConstraints.BOTH;
		gbc_tablePanel.insets = new Insets(0, 0, 0, 0);
		gbc_tablePanel.gridx = gridX;
		gbc_tablePanel.gridy = gridY;
		panel.add(tablePanel,gbc_tablePanel);
		
		//��ѯ���е��û�
		showUser();
		return panel;
	}
	
	private JPanel createConditionPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 100, 50, 100, 50, 50, 150};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		new SwsJLabel(panel, GridBagConstraints.EAST, 5, 0, gridX, gridY, 1, 1, "�û�����");
		
		gridX++;

		usernameTextField = new SwsJTextField(panel, gridX, gridY, 2);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.EAST, 5, 0, gridX, gridY, 1, 1, "��  �룺");
		
		gridX++;
		
		passwordTextField = new SwsJTextField(panel, gridX, gridY, 2);
		passwordTextField.setText("123456");
		passwordTextField.setEditable(false);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.EAST, 5, 0, gridX, gridY, 1, 1, "����Ȩ�ޣ�");
		
		gridY++;
		
		JPanel authorityPanel = createAuthorityPanel();
		GridBagConstraints gbc_authorityPanel = new GridBagConstraints();
		gbc_authorityPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_authorityPanel.insets = new Insets(0, 5, 0, 5);
		gbc_authorityPanel.gridx = gridX;
		gbc_authorityPanel.gridy = gridY;
		gbc_authorityPanel.gridwidth = 3;
		panel.add(authorityPanel,gbc_authorityPanel);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.EAST, 5, 0, gridX, gridY, 1, 1, "��ע��Ϣ��");
		
		gridY++;
		
	    remarkTextArea = new JTextArea();
	    remarkTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		GridBagConstraints gbc_remarkMessageTextArea = new GridBagConstraints();
		gbc_remarkMessageTextArea.fill = GridBagConstraints.BOTH;
		gbc_remarkMessageTextArea.insets = new Insets(0, 5, 0, 5);
		gbc_remarkMessageTextArea.gridx = gridX;
		gbc_remarkMessageTextArea.gridy = gridY;
		gbc_remarkMessageTextArea.gridwidth = 3;
		panel.add(remarkTextArea,gbc_remarkMessageTextArea);
		
		gridY++;
		
		addUserBtn = new SwsJButton(panel, gridX, gridY, "���", this);
		
		gridX++;
		
		deleteUserBtn = new SwsJButton(panel, gridX, gridY, "ɾ��", this);
		
		gridX++;

		clearUserBtn = new SwsJButton(panel, gridX, gridY, "���", this);
		
		gridX=0;
		gridY++;
		
		updatePasswordBtn = new SwsJButton(panel, gridX, gridY, "�޸�����", this);
		
		gridX++;
		
		updateAuthorityBtn = new SwsJButton(panel, gridX, gridY, "�޸�Ȩ��", this);
		
		gridX++;
		
		switchUserBtn = new SwsJButton(panel, gridX, gridY, "�л��û�", this);
		
		return panel;
	}
	
	private JPanel createTablePanel()
	{
		tablePanel = new JPanel();
		tablePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{700};
		gridBagLayout.rowHeights = new int[]{600};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		tablePanel.setLayout(gridBagLayout);

		return tablePanel;
	}
	
	private JPanel createAuthorityPanel()
	{ 
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		//panel.setBackground(Color.GRAY);
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 100, 100, 50};
		gridBagLayout.rowHeights = new int[]{50, 50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		int gridX=1;
		int gridY=0;
		
		adminJRB = new SwsJRadioButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "����Ա", this);
		
		gridX++;
		
		//weightAdminJRB = new SwsJRadioButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "���ع���Ա", this);
		
		//gridX=1;
		//gridY++;
		
		operatorJRB = new SwsJRadioButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "����Ա", this);
		
		//gridX++;
		
		//monitorJRB = new SwsJRadioButton(panel, GridBagConstraints.HORIZONTAL, 0, 0, gridX, gridY, 1, 1, "����Ա", this);
		
		ButtonGroup group=new ButtonGroup();
        //���JRadioButton��ButtonGroup��
        group.add(adminJRB);
        //group.add(weightAdminJRB);
        group.add(operatorJRB);
        //group.add(monitorJRB);
        group.setSelected(operatorJRB.getModel(), true);
		
		return panel;
	}
	
	private void displayTable(String[] titles, Object[][] data)
	{
		tablePanel.removeAll();
		tablePanel.revalidate();
		System.out.println("��ʾ�б�");
		JTable table = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel(data, titles)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		table.setModel(tableModel);
		//����ID��
		TableColumnModel tcm = table.getColumnModel();  
		TableColumn tc = tcm.getColumn(0);
		tc.setMinWidth(0);
		tc.setMaxWidth(0);
		tc.setPreferredWidth(0);
		
		//-------���ø������ñ�����ɫ-----------------------------------------------------------------
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
		
		//��Ԫ�����ݾ���
		ter.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, ter);
		
        //��ѡ���еı�����ɫ
		table.setSelectionBackground(Color.GRAY);
		//�����и�
		table.setRowHeight(30);
		
		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		tablePanel.add(scrollPane,gbc_scrollPane);
		tablePanel.revalidate();
		
		//ע��˫��ĳ�е���Ӧ�¼�
		table.addMouseListener(new MouseAdapter()
		{ 
			public void mouseClicked(MouseEvent e) 
			{
                //ʵ��˫�� 
				if(e.getClickCount() == 2)
				{ 
					int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //�����λ�� 

					int id =(int) ((JTable)e.getSource()).getValueAt(row,0); //��õ����Ԫ������ 
					System.out.println("id");
					User user = SwsQueryDBUtils.queryUserByID(id);
					
					if(user != null)
					{
						usernameTextField.setText(user.getUsername());
						int power = user.getPower();
						if(power == 1)
						{
							adminJRB.setSelected(true);
						}
						/*else if(power == 2)
						{
							weightAdminJRB.setSelected(true);
						}*/
						else if(power == 2)
						{
							operatorJRB.setSelected(true);
						}
						/*else if(power == 4)
						{
							monitorJRB.setSelected(true);
						}*/
						
						remarkTextArea.setText(user.getRemark());
					}
				} 
			}
		});
	}
	
	private void showUser()
	{
		List<User> userList = SwsQueryDBUtils.queryAllUser();
		if(userList.size() > 0)
		{
			//����
			String[] title = new String[]{"ID", "���", "�û���", "Ȩ��", "����"};
			//���ɱ��
			Object[][] data = new Object[userList.size()][title.length];
			for(int i=0; i<userList.size(); i++)
			{
				User user = userList.get(i);
				data[i][0] = user.getId();
				data[i][1] = (i+1);
				data[i][2] = user.getUsername();
				String power = "����Ա";
				if(user.getPower() == 1)
				{
					power = "����Ա";
				}
				/*else if(user.getPower() == 2)
				{
					power = "���ع���Ա";
				}
				else if(user.getPower() == 4)
				{
					power = "����Ա";
				}*/
				else 
				{
					power = "����Ա";
				}
				data[i][3] = power;
				data[i][4] = user.getRemark();
			}
			
			displayTable(title, data);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
	    if(e.getSource().equals(addUserBtn))
		{
			System.out.println("����û�");
			addUser();
			clear();
		}
		else if(e.getSource().equals(deleteUserBtn))
		{
			System.out.println("ɾ���û�");
			deleteUser();
			clear();
		}
		else if(e.getSource().equals(clearUserBtn))
		{
			System.out.println("����û�");
			clear();
		}
		else if(e.getSource().equals(updatePasswordBtn))
		{
			System.out.println("�޸�����");
			SmartWeighingUpdatePasswordFrame.showFrame(usernameTextField.getText());
		}
		else if(e.getSource().equals(updateAuthorityBtn))
		{
			System.out.println("�޸�Ȩ��");
			updatePower();
			clear();
		}
		else if(e.getSource().equals(switchUserBtn))
		{
			//�л��û�
			new SmartWeighingLoginFrame();
		}
	}
	
	/**
	 * ���
	 */
	private void clear()
	{
		usernameTextField.setText("");
		operatorJRB.setSelected(true);
		remarkTextArea.setText("");
	}
	
	/**
	 * ����û�
	 */
	
	private void addUser()
	{
		if("".equals(usernameTextField.getText()))
		{
			SwsMessageDialog.warningDialog("�û�������Ϊ�գ�");
		}
		else 
		{
			String username = usernameTextField.getText();
			boolean bExist = SwsQueryDBUtils.userExist(username);
			if(bExist)
			{
				SwsMessageDialog.errorDialog("���û����Ѵ���!!!");
			}
			else
			{
				//����û�
				String password = passwordTextField.getText();
				String remark = remarkTextArea.getText();
				int power = 3;//Ĭ���ǲ���Ա��1��ʾ����Ա��2��ʾ���ع���Ա��3��ʾ����Ա��4��ʾ����Ա
				if(adminJRB.isSelected())
				{
					power = 1;
				}
				/*else if(weightAdminJRB.isSelected())
				{
					power = 2;
				}
				else if(monitorJRB.isSelected())
				{
					power = 4;
				}*/
				else 
				{
					power = 2;
				}
				
				boolean bSuccess = SwsSaveDBUtils.addUser(username, password, power, remark);
				if(bSuccess)
				{
					//ˢ�½���
					showUser();
					SwsMessageDialog.infoDialog("����û��ɹ�!");
				}
				else
				{
					SwsMessageDialog.errorDialog("����û�ʧ��!");
				}
			}
		}
		
	}
	
	/**
	 * ɾ���û�
	 */
	private void deleteUser()
	{
		if("".equals(usernameTextField.getText()))
		{
			SwsMessageDialog.warningDialog("�û�������Ϊ�գ�");
		}
		else 
		{
			String username = usernameTextField.getText();
			boolean bExist = SwsQueryDBUtils.userExist(username);
			if(!bExist)
			{
				SwsMessageDialog.errorDialog("���û���������!!!");
			}
			else
			{
				int option = SwsMessageDialog.confirmDialog("ȷ��ɾ���û� "+username+" ��", "ɾ���û���ʾ");
				if(option == SwsMessageDialog.OK_OPTION)
				{
					//ɾ���û�				
					boolean bSuccess = SwsDeleteDBUtils.deleteUser(username);
					if(bSuccess)
					{
						//ˢ�½���
						showUser();
						SwsMessageDialog.infoDialog("ɾ���û��ɹ�!");
					}
					else
					{
						SwsMessageDialog.errorDialog("ɾ���û�ʧ��!");
					}
				}
			}
		}
	}
	
	/**
	 * �޸�Ȩ��
	 */
	private void updatePower()
	{
		String username = usernameTextField.getText();
		if("".equals(username))
		{
			SwsMessageDialog.warningDialog("�û�������Ϊ�գ�");
		}
		else
		{
			String authority = "����Ա";
			int power = 3;//Ĭ���ǲ���Ա��1��ʾ����Ա��2��ʾ���ع���Ա��3��ʾ����Ա��4��ʾ����Ա
			if(adminJRB.isSelected())
			{
				power = 1;
				authority = "����Ա";
			}
			/*else if(weightAdminJRB.isSelected())
			{
				power = 2;
				authority = "���ع���Ա";
			}
			else if(monitorJRB.isSelected())
			{
				power = 4;
				authority = "����Ա";
			}*/
			else 
			{
				power = 2;
				authority = "����Ա";
			}
			
			int option = SwsMessageDialog.confirmDialog("ȷ���޸� "+username+" ��Ȩ��Ϊ "+authority+" ��", "�޸�Ȩ����ʾ");
			if(option == SwsMessageDialog.OK_OPTION)
			{
				boolean bSuccess = SwsUpdateDBUtils.updateUserPower(username, power);
				if(bSuccess)
				{
					showUser();
					SwsMessageDialog.infoDialog("�޸�Ȩ�޳ɹ���");
				}
				else
				{
					SwsMessageDialog.errorDialog("�޸�Ȩ��ʧ�ܣ�");
				}
			}
		}
	}
}
