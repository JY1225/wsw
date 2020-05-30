package com.sws.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.sws.base.SwsMessageDialog;
import com.sws.components.JTimeChooserTextField;
import com.sws.database.SwsQueryDBUtils;
import com.sws.entity.SwsLog;

/**
 * 日志管理
 * @author Ben
 *
 */
public class SmartWeighingLogManagementFrame implements ActionListener {

	private JTextField beginTimeTextField;
	private JTextField endTimeTextField;
	private JPanel tablePanel;
	public JPanel showFrame()
	{
		JPanel panel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1000};
		gridBagLayout.rowHeights = new int[]{100, 600};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		JPanel queryConditionPanel = createQueryConditionPanel();
		GridBagConstraints gbc_queryConditionPanel = new GridBagConstraints();
		gbc_queryConditionPanel.fill = GridBagConstraints.BOTH;
		gbc_queryConditionPanel.insets = new Insets(0, 0, 0, 0);
		gbc_queryConditionPanel.gridx = gridX;
		gbc_queryConditionPanel.gridy = gridY;
		panel.add(queryConditionPanel,gbc_queryConditionPanel);

		gridY++;

		tablePanel = createTablePanel();
		GridBagConstraints gbc_tablePanel = new GridBagConstraints();
		gbc_tablePanel.fill = GridBagConstraints.BOTH;
		gbc_tablePanel.insets = new Insets(0, 0, 0, 0);
		gbc_tablePanel.gridx = gridX;
		gbc_tablePanel.gridy = gridY;
		panel.add(tablePanel,gbc_tablePanel);
		
		return panel;
	}
	
	private JPanel createQueryConditionPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 70, 200, 20, 70, 200, 20, 100, 200};
		gridBagLayout.rowHeights = new int[]{50,};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		int gridX=1;
		int gridY=0;
		
		JLabel beginTimeLabel = new JLabel("开始时间：");
		GridBagConstraints gbc_beginTimeLabel = new GridBagConstraints();
		gbc_beginTimeLabel.fill = GridBagConstraints.BOTH;
		gbc_beginTimeLabel.insets = new Insets(0, 0, 0, 0);
		gbc_beginTimeLabel.gridx = gridX;
		gbc_beginTimeLabel.gridy = gridY;
		panel.add(beginTimeLabel,gbc_beginTimeLabel);
		
		gridX++;

		beginTimeTextField = new JTimeChooserTextField();
		GridBagConstraints gbc_beginTimeTextField = new GridBagConstraints();
		gbc_beginTimeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_beginTimeTextField.insets = new Insets(0, 0, 0, 0);
		gbc_beginTimeTextField.gridx = gridX;
		gbc_beginTimeTextField.gridy = gridY;
		panel.add(beginTimeTextField,gbc_beginTimeTextField);
		
		gridX+=2;
		
		JLabel endTimeLabel = new JLabel("结束时间：");
		GridBagConstraints gbc_endTimeLabel = new GridBagConstraints();
		gbc_endTimeLabel.fill = GridBagConstraints.BOTH;
		gbc_endTimeLabel.insets = new Insets(0, 0, 0, 0);
		gbc_endTimeLabel.gridx = gridX;
		gbc_endTimeLabel.gridy = gridY;
		panel.add(endTimeLabel,gbc_endTimeLabel);
		
		gridX++;
		
		endTimeTextField = new JTimeChooserTextField();
		GridBagConstraints gbc_endTimeTextField = new GridBagConstraints();
		gbc_endTimeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_endTimeTextField.insets = new Insets(0, 0, 0, 0);
		gbc_endTimeTextField.gridx = gridX;
		gbc_endTimeTextField.gridy = gridY;
		panel.add(endTimeTextField,gbc_endTimeTextField);
		
		gridX+=2;
		
		JButton queryBtn = new JButton("查询");
		queryBtn.addActionListener(this);
		GridBagConstraints gbc_queryBtn = new GridBagConstraints();
		gbc_queryBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_queryBtn.insets = new Insets(0, 0, 0, 0);
		gbc_queryBtn.gridx = gridX;
		gbc_queryBtn.gridy = gridY;
		panel.add(queryBtn,gbc_queryBtn);
		
		return panel;
	}

	private JPanel createTablePanel()
	{
		tablePanel = new JPanel();
		tablePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1000};
		gridBagLayout.rowHeights = new int[]{600};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		tablePanel.setLayout(gridBagLayout);

		return tablePanel;
	}

	//private JTable table;
	private void displayTable(String[] titles, Object[][] data)
	{
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("查询".equals(e.getActionCommand()))
		{
			List<SwsLog> list = SwsQueryDBUtils.queryLog(beginTimeTextField.getText(), endTimeTextField.getText());

			if(list != null && list.size() > 0) {

				//标题
				String[] title = new String[]{"时间", "事件", "内容", "操作员"};
				//生成表格
				Object[][] data = new Object[list.size()][4];
				for(int i=0; i<list.size(); i++)
				{
					SwsLog log = list.get(i);
					data[i][0] = log.getLogTime();
					data[i][1] = log.getLogEvent();
					data[i][2] = log.getLogContent();
					data[i][3] = log.getLogOperator();
				}

				displayTable(title, data);
			}
			else
			{
				//JOptionPane.showMessageDialog(null, "暂无相关记录，请重新查询!!!");
				SwsMessageDialog.warningDialog("暂无相关记录，请重新查询!!!");
			}
		}
	}
}
