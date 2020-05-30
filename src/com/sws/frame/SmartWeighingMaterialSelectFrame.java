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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.sws.base.SwsParameter;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJLabel;
import com.sws.components.SwsJTextField;
import com.sws.database.SwsQueryDBUtils;
import com.sws.entity.Material;
import com.sws.entity.Team;
import com.sws.entity.User;
import com.sws.utils.CommonUtils;

/**
 * 选择物料信息界面
 * @author Ben
 *
 */
public class SmartWeighingMaterialSelectFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static SmartWeighingMaterialSelectFrame selectFrame=null;
	private JPanel tablePanel;
	private JButton queryBtn;
	private JButton okBtn;
	private JButton cancelBtn;
	
	private JTextField materialCodeTextField;
	private JComboBox<String> materialNameComboBox;
	private JComboBox<String> materialCunComboBox;
	private JTextField outerDiameterTextField;
	private JTextField wallThicknessTextField;
	private JTextField lengthTextField;
	
	public SmartWeighingMaterialSelectFrame()
	{
		setTitle("选择物料信息界面");
		setSize(800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300, 600};
		gridBagLayout.rowHeights = new int[]{500};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		JPanel leftPanel = createLeftPanel();
		GridBagConstraints gbc_leftPanel = new GridBagConstraints();
		gbc_leftPanel.fill = GridBagConstraints.BOTH;
		gbc_leftPanel.insets = new Insets(0, 0, 0, 0);
		gbc_leftPanel.gridx = gridX;
		gbc_leftPanel.gridy = gridY;
		getContentPane().add(leftPanel,gbc_leftPanel);
		
		gridX++;
		
		tablePanel = createRightPanel();
		GridBagConstraints gbc_rightPanell = new GridBagConstraints();
		gbc_rightPanell.fill = GridBagConstraints.BOTH;
		gbc_rightPanell.insets = new Insets(0, 0, 0, 5);
		gbc_rightPanell.gridx = gridX;
		gbc_rightPanell.gridy = gridY;
		getContentPane().add(tablePanel,gbc_rightPanell);
	}
	
	private JPanel createLeftPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100};
		gridBagLayout.rowHeights = new int[]{10, 50, 50, 50, 50, 50, 50, 50, 200};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 1;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "货物代码：");
		
		gridX++;
		
		materialCodeTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 2, 1);
		materialCodeTextField.setEditable(false);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "货物名称：");
		
		gridX++;
		
		materialNameComboBox = new JComboBox<String>();
		GridBagConstraints gbc_materialNameComboBox = new GridBagConstraints();
		gbc_materialNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_materialNameComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_materialNameComboBox.gridx = gridX;
		gbc_materialNameComboBox.gridy = gridY;
		gbc_materialNameComboBox.gridwidth = 2;
		panel.add(materialNameComboBox,gbc_materialNameComboBox);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "货物规格：");
		
		gridX++;
		
		materialCunComboBox = new JComboBox<String>();
		GridBagConstraints gbc_materialSpecificationComboBox = new GridBagConstraints();
		gbc_materialSpecificationComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_materialSpecificationComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_materialSpecificationComboBox.gridx = gridX;
		gbc_materialSpecificationComboBox.gridy = gridY;
		gbc_materialSpecificationComboBox.gridwidth = 2;
		panel.add(materialCunComboBox,gbc_materialSpecificationComboBox);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "外    径：");
		
		gridX++;
		
		outerDiameterTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 2, 1);
		
		/*JComboBox<String> outerDiameterComboBox = new JComboBox<String>();
		GridBagConstraints gbc_outerDiameterComboBox = new GridBagConstraints();
		gbc_outerDiameterComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_outerDiameterComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_outerDiameterComboBox.gridx = gridX;
		gbc_outerDiameterComboBox.gridy = gridY;
		gbc_outerDiameterComboBox.gridwidth = 2;
		panel.add(outerDiameterComboBox,gbc_outerDiameterComboBox);*/
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "壁    厚：");
		
		gridX++;
		
		wallThicknessTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 2, 1);
		/*JComboBox<String> wallThicknessComboBox = new JComboBox<String>();
		GridBagConstraints gbc_wallThicknessComboBox = new GridBagConstraints();
		gbc_wallThicknessComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_wallThicknessComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_wallThicknessComboBox.gridx = gridX;
		gbc_wallThicknessComboBox.gridy = gridY;
		gbc_wallThicknessComboBox.gridwidth = 2;
		panel.add(wallThicknessComboBox,gbc_wallThicknessComboBox);*/
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "长    度：");
		
		gridX++;
		
		lengthTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 2, 1);
		/*JComboBox<String> materialLengthComboBox = new JComboBox<String>();
		GridBagConstraints gbc_materialLengthComboBox = new GridBagConstraints();
		gbc_materialLengthComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_materialLengthComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_materialLengthComboBox.gridx = gridX;
		gbc_materialLengthComboBox.gridy = gridY;
		gbc_materialLengthComboBox.gridwidth = 2;
		panel.add(materialLengthComboBox,gbc_materialLengthComboBox);*/
		
		gridY++;
		gridX=0;
		
		queryBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 1, 1, "查询", this);
		
		gridX++;
		
		okBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 1, 1, "确定", this);
		
		gridX++;
		
		cancelBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 1, 1, "取消", this);
		
		return panel;
	}
	
	private JPanel createRightPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0};
        gridBagLayout.rowHeights = new int[]{0};
        gridBagLayout.columnWeights = new double[]{1.0};
        gridBagLayout.rowWeights = new double[]{1.0};
        panel.setLayout(gridBagLayout);
		
		return panel;
	}
	
	private void displayTable(String[] titles, Object[][] data, int[] columnWidth)
	{
		tablePanel.removeAll();
		tablePanel.revalidate();
		System.out.println("显示列表");
		JTable table = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel(data, titles)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		table.setModel(tableModel);
		
		for(int i=0; i<columnWidth.length; i++)
		{
			int width = columnWidth[i];
			//隐藏ID列
			TableColumnModel tcm = table.getColumnModel();  
			TableColumn tc = tcm.getColumn(i);
			tc.setMinWidth(width);
			tc.setMaxWidth(width);
			tc.setPreferredWidth(width);
		}
		
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
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
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

					
					int materialCode = (int) ((JTable)e.getSource()).getValueAt(row,2); //获得点击单元格数据 
					//显示班组详细信息
					Material material = SwsQueryDBUtils.queryMaterialByCode(materialCode);
					if(material != null)
					{
						materialCodeTextField.setText(material.getMaterialCode()+"");
						materialNameComboBox.setSelectedItem(material.getMaterialName());
						//materialAliasTextField.setText(material.getMaterialAlias());
						materialCunComboBox.setSelectedItem(material.getMaterialDimension());
						outerDiameterTextField.setText(material.getOuterDiameter()+"");
						wallThicknessTextField.setText(material.getWallThickness()+"");
						lengthTextField.setText(material.getLength()+"");
					}
					
				} 
			}
		});
	}
	
	public static void showFrame()
	{
		if(selectFrame == null)
		{
			selectFrame = new SmartWeighingMaterialSelectFrame();
			selectFrame.setVisible(true);
		}
		else if(!selectFrame.isVisible())
		{
			selectFrame.setVisible(true);
		}
		
		selectFrame.bindingData();
		
		selectFrame.showData();
	}
	
	/**
	 * 绑定数据
	 */
	private void bindingData() 
	{
		//获取所有物料名称
		List<String> materialNames = SwsQueryDBUtils.queryMaterialName();
		
		for(int i=0; i<materialNames.size(); i++)
		{
			materialNameComboBox.addItem(materialNames.get(i));
		}
		
		//获取所有物料规格
		List<String> cunList = CommonUtils.getAllCun();
		
		for(int i=0; i<cunList.size(); i++)
		{
			String cun = cunList.get(i);
			materialCunComboBox.addItem(cun);
		}
	}
	
	private void showData()
	{
		//班组信息
		List<Material> materialList = SwsQueryDBUtils.queryAllMaterial();
		//标题
		String[] title = new String[]{"ID", "序号", "物料代码", "物料名称", "物料别名", "物料规格", "物料寸别", "外径", "壁厚", "长度", "镀锌系数", "支重", "每件支数", "件重", "最小重量", "最大重量", "是否匹配", "生产日期", "修改时间"};
		//生成表格
		Object[][] data = new Object[materialList.size()][title.length];
		
		int[] columnWidth = new int[]{0, 0, 100, 100, 150, 0, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 200, 200};
		
		if(materialList.size() > 0)
		{
			for(int i=0; i<materialList.size(); i++)
			{
				Material material = materialList.get(i);
				data[i][0] = material.getMaterialCode();
				data[i][1] = (i+1);
				data[i][2] = material.getMaterialCode();
				data[i][3] = material.getMaterialName();
				data[i][4] = material.getMaterialAlias();
				data[i][5] = material.getMaterialSpecifition();
				data[i][6] = material.getMaterialDimension();
				data[i][7] = material.getOuterDiameter();
				data[i][8] = material.getWallThickness();
				data[i][9] = material.getLength();
				data[i][10] = material.getDxxs();
				data[i][11] = material.getZz();
				data[i][12] = material.getZs();
				data[i][13] = material.getPieceWeight();
				data[i][14] = material.getMinWeight();
				data[i][15] = material.getMaxWeight();
				data[i][16] = material.getIsMatch();
				data[i][17] = material.getProduceDate();
				data[i][18] = material.getModifyTime();
			}
		}
		
		displayTable(title, data, columnWidth);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(queryBtn))
		{
			
		}
		else if(e.getSource().equals(okBtn))
		{
			
		}
		else if(e.getSource().equals(cancelBtn))
		{
			
		}
		else {
			
		}
	}
}
