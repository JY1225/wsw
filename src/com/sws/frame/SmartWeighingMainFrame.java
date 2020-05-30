package com.sws.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sws.base.SwsParameter;
import com.sws.components.JDateChooserTextField;
import com.sws.components.SwsJButton;
import com.sws.components.SwsJLabel;
import com.sws.components.SwsJTextField;
import com.sws.database.SwsQueryDBUtils;
import com.sws.entity.Material;
import com.sws.entity.Record;
import com.sws.entity.Team;
import com.sws.entity.User;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.utils.CommonUtils;

/**
 * 检测主界面
 * @author Ben
 *
 */
public class SmartWeighingMainFrame implements ActionListener
{
	private JButton selectBtn;
	private JButton lockBtn;
	private JTextField produceDateTextField;
	private JComboBox<String> produceUnitComboBox;
	private JComboBox<String> classNameComboBox;
	private JComboBox<String> teamNameComboBox;
	private JComboBox<String> weighingPersonComboBox;

	private JComboBox<String> materialNameComboBox;
	private JComboBox<String> materialSpecificationComboBox;
	private JComboBox<String> materialDxxsComboBox;
	private JComboBox<String> inOutComboBox;
	private JComboBox<String> materialComboBox;
	private JComboBox<String> measureWayComboBox;
	private JComboBox<String> nozzleStatusComboBox;
	private JComboBox<String> customerNameComboBox;
	private JTextField materialCodeTextField;
	private JTextField outerDiameterTextField;
	private JTextField wallThicknessTextField;
	private JTextField materialLengthTextField;
	private JTextField materialCountTextField;
	
	public JPanel showFrame()
	{
		JPanel panel = new JPanel();

		EventListener eListener = new EventListener();
		SwsParameter.swsEventManager.addListener(eListener);
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{700, 300};
		gridBagLayout.rowHeights = new int[]{1000};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		JPanel leftPanel = createLeftPanel();
		GridBagConstraints gbc_leftPanel = new GridBagConstraints();
		gbc_leftPanel.fill = GridBagConstraints.BOTH;
		gbc_leftPanel.insets = new Insets(0, 2, 0, 2);
		gbc_leftPanel.gridx = gridX;
		gbc_leftPanel.gridy = gridY;
		panel.add(leftPanel,gbc_leftPanel);
		
		gridX++;
		
		JPanel rightPanel = createRightPanel();
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.fill = GridBagConstraints.BOTH;
		gbc_rightPanel.insets = new Insets(0, 0, 0, 2);
		gbc_rightPanel.gridx = gridX;
		gbc_rightPanel.gridy = gridY;
		panel.add(rightPanel,gbc_rightPanel);
		
		//绑定数据
		bindingData();
		
		return panel;
	}

	private JPanel createLeftPanel()
	{
		JPanel panel = new JPanel();
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{700};
		gridBagLayout.rowHeights = new int[]{350, 350};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		JPanel screenPanel = createScreenPanel();
		GridBagConstraints gbc_screenPanel = new GridBagConstraints();
		gbc_screenPanel.fill = GridBagConstraints.BOTH;
		gbc_screenPanel.insets = new Insets(0, 2, 0, 2);
		gbc_screenPanel.gridx = gridX;
		gbc_screenPanel.gridy = gridY;
		panel.add(screenPanel,gbc_screenPanel);
		
		gridY++;
		
		JPanel listPanel = createListPanel();
		GridBagConstraints gbc_listPanel = new GridBagConstraints();
		gbc_listPanel.fill = GridBagConstraints.BOTH;
		gbc_listPanel.insets = new Insets(2, 2, 0, 2);
		gbc_listPanel.gridx = gridX;
		gbc_listPanel.gridy = gridY;
		gbc_listPanel.gridwidth = 2;
		panel.add(listPanel,gbc_listPanel);
		
		return panel;
	}
	
	private JPanel createRightPanel()
	{
		JPanel panel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300};
		gridBagLayout.rowHeights = new int[]{250, 450};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		JPanel teamSetPanel = createTeamSetPanel();
		GridBagConstraints gbc_teamSetPanel = new GridBagConstraints();
		gbc_teamSetPanel.fill = GridBagConstraints.BOTH;
		gbc_teamSetPanel.insets = new Insets(0, 0, 0, 2);
		gbc_teamSetPanel.gridx = gridX;
		gbc_teamSetPanel.gridy = gridY;
		panel.add(teamSetPanel,gbc_teamSetPanel);
		
		gridY++;
		
		JPanel produceSetPanel = createProduceSetPanel();
		GridBagConstraints gbc_produceSetPanel = new GridBagConstraints();
		gbc_produceSetPanel.fill = GridBagConstraints.BOTH;
		gbc_produceSetPanel.insets = new Insets(2, 2, 0, 2);
		gbc_produceSetPanel.gridx = gridX;
		gbc_produceSetPanel.gridy = gridY;
		panel.add(produceSetPanel,gbc_produceSetPanel);
		
		return panel;
	}
	
	
	private JPanel createScreenPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		panel.setBackground(Color.BLACK);
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100, 100, 100, 100, 100};
		gridBagLayout.rowHeights = new int[]{80, 80, 80, 110};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		Font font = new Font("宋体", Font.BOLD, 50);
		
		JLabel oneLineLabel = new SwsJLabel(panel, GridBagConstraints.BOTH, 30, 0, gridX, gridY, 4, 1, "一班张三");
		oneLineLabel.setFont(font);
		oneLineLabel.setForeground(Color.RED);
		
		gridY++;
		
		JLabel twoLineLabel = new SwsJLabel(panel, GridBagConstraints.BOTH, 30, 0, gridX, gridY, 5, 1, "热镀锌管1.5寸");
		twoLineLabel.setFont(font);
		twoLineLabel.setForeground(Color.RED);
		
		/*gridX+=3;
		
		JLabel materialSpecificationLabel = new SwsJLabel(panel, gridX, gridY, 4, "1.5寸");
		materialSpecificationLabel.setFont(font);
		materialSpecificationLabel.setForeground(Color.RED);*/
		
		gridX=0;
		gridY++;
		
		JLabel threeLineLabel = new SwsJLabel(panel, GridBagConstraints.BOTH, 30, 0, gridX, gridY, 3, 1, "入库总重");
		threeLineLabel.setFont(font);
		threeLineLabel.setForeground(Color.RED);
		
		gridX+=4;
		
		JLabel totalWeightLabel = new SwsJLabel(panel, gridX, gridY, 3, "1269");
		totalWeightLabel.setFont(font);
		totalWeightLabel.setForeground(Color.RED);
		
		gridX=0;
		gridY++;
		
		JLabel fourLineLabel = new SwsJLabel(panel, GridBagConstraints.BOTH, 30, 0, gridX, gridY, 3, 1, "W003");
		fourLineLabel.setFont(font);
		fourLineLabel.setForeground(Color.RED);
		
		gridX+=4;
		
		JLabel currentWeightLabel = new SwsJLabel(panel, gridX, gridY, 3, "1206");
		currentWeightLabel.setFont(new Font("宋体", Font.BOLD, 80));
		currentWeightLabel.setForeground(Color.RED);
		
		return panel;
	}
	
	/*private JPanel createOhterStatusPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100};
		gridBagLayout.rowHeights = new int[]{70, 70, 70, 70, 70};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		Font font = new Font("宋体", Font.BOLD, 16);
		
		JLabel operatorLabel = new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "操作员：");
		operatorLabel.setFont(font);
		
		gridX++;
		
		JLabel operator = new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "张三");
		operator.setFont(font);
		
		gridX++;
		
		JButton switchUserBtn = new SwsJButton(panel, gridX, gridY, "切换用户", this);
		
		gridX=0;
		gridY++;
		
		JLabel teamLabel = new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "班  组：");
		teamLabel.setFont(font);
		
		gridX++;
		
		JLabel team = new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "一组");
		team.setFont(font);
		
		gridX=0;
		gridY++;
		
		JLabel deviceStatusLabel = new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "设备状态：");
		deviceStatusLabel.setFont(font);
		
		gridX++;
		
		JLabel deviceStatus = new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "正常");
		deviceStatus.setFont(font);
		deviceStatus.setForeground(Color.GREEN);
		
		gridX=0;
		gridY++;
		
		JLabel systemTimeLabel = new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "系统时间：");
		systemTimeLabel.setFont(font);
		
		gridY++;
		
		JLabel systemTime = new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 3, 1, "2020年05月07日 10:53:27");
		systemTime.setFont(font);
		
		return panel;
	}*/
	
	//班组信息设置界面
	private JPanel createTeamSetPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "班组信息设置"));
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 200};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "生产日期：");
		
		gridX++;
		
		produceDateTextField = new JDateChooserTextField();
		GridBagConstraints gbc_produceDateTextField = new GridBagConstraints();
		gbc_produceDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_produceDateTextField.insets = new Insets(0, 0, 0, 5);
		gbc_produceDateTextField.gridx = gridX;
		gbc_produceDateTextField.gridy = gridY;
		panel.add(produceDateTextField,gbc_produceDateTextField);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "生产机组：");
		
		gridX++;
		
		produceUnitComboBox = new JComboBox<String>();
		GridBagConstraints gbc_produceUnitComboBox = new GridBagConstraints();
		gbc_produceUnitComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_produceUnitComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_produceUnitComboBox.gridx = gridX;
		gbc_produceUnitComboBox.gridy = gridY;
		panel.add(produceUnitComboBox,gbc_produceUnitComboBox);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "班别名称：");
		
		gridX++;
		
		classNameComboBox = new JComboBox<String>();
		GridBagConstraints gbc_classNameComboBox = new GridBagConstraints();
		gbc_classNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_classNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_classNameComboBox.gridx = gridX;
		gbc_classNameComboBox.gridy = gridY;
		panel.add(classNameComboBox,gbc_classNameComboBox);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "班组名称：");
		
		gridX++;
		
		teamNameComboBox = new JComboBox<String>();
		GridBagConstraints gbc_teamNameComboBox = new GridBagConstraints();
		gbc_teamNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_teamNameComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_teamNameComboBox.gridx = gridX;
		gbc_teamNameComboBox.gridy = gridY;
		panel.add(teamNameComboBox,gbc_teamNameComboBox);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "称重人员：");
		
		gridX++;
		
		weighingPersonComboBox = new JComboBox<String>();
		GridBagConstraints gbc_weighingPersonComboBox = new GridBagConstraints();
		gbc_weighingPersonComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_weighingPersonComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_weighingPersonComboBox.gridx = gridX;
		gbc_weighingPersonComboBox.gridy = gridY;
		panel.add(weighingPersonComboBox,gbc_weighingPersonComboBox);
		
		return panel;
	}
	
	//生产信息设置面板
	private JPanel createProduceSetPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "生产信息设置"));
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 40, 50, 40, 50, 70};
		gridBagLayout.rowHeights = new int[]{45, 45, 45, 45, 45, 45, 45, 45, 45, 45};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0,1.0, 1.0,1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0,1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		int gridX=0;
		int gridY=0;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 2, 1, "计量方式：");
		
		gridX+=2;
		
		measureWayComboBox = new JComboBox<String>();
		GridBagConstraints gbc_measureWayComboBox = new GridBagConstraints();
		gbc_measureWayComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_measureWayComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_measureWayComboBox.gridx = gridX;
		gbc_measureWayComboBox.gridy = gridY;
		gbc_measureWayComboBox.gridwidth = 4;
		panel.add(measureWayComboBox,gbc_measureWayComboBox);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 2, 1, "物料代码：");
		
		gridX+=2;
		
		materialCodeTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 3, 1);
		
		gridX+=3;
		
		selectBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 2, 2, gridX, gridY, 1, 1, "选择", this);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 2, 1, "物料名称：");
		
		gridX+=2;
		
		materialNameComboBox = new JComboBox<String>();
		GridBagConstraints gbc_materialNameComboBox = new GridBagConstraints();
		gbc_materialNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_materialNameComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_materialNameComboBox.gridx = gridX;
		gbc_materialNameComboBox.gridy = gridY;
		gbc_materialNameComboBox.gridwidth = 4;
		panel.add(materialNameComboBox,gbc_materialNameComboBox);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 2, 1, "物料寸别：");
		
		gridX+=2;
		
		materialSpecificationComboBox = new JComboBox<String>();
		GridBagConstraints gbc_materialSpecificationComboBox = new GridBagConstraints();
		gbc_materialSpecificationComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_materialSpecificationComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_materialSpecificationComboBox.gridx = gridX;
		gbc_materialSpecificationComboBox.gridy = gridY;
		gbc_materialSpecificationComboBox.gridwidth = 4;
		panel.add(materialSpecificationComboBox,gbc_materialSpecificationComboBox);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 2, 1, "镀锌系数：");
		
		gridX+=2;
		
		materialDxxsComboBox = new JComboBox<String>();
		GridBagConstraints gbc_materialDxxsComboBox = new GridBagConstraints();
		gbc_materialDxxsComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_materialDxxsComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_materialDxxsComboBox.gridx = gridX;
		gbc_materialDxxsComboBox.gridy = gridY;
		gbc_materialDxxsComboBox.gridwidth = 4;
		panel.add(materialDxxsComboBox,gbc_materialDxxsComboBox);
			
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "外径：");
		
		gridX++;
		
		outerDiameterTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 2, 1);
		
		gridX+=2;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "壁厚：");
		
		gridX++;
		
		wallThicknessTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 2, 1);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 1, 1, "长度：");
		
		gridX++;
		
		materialLengthTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 2, 1);
		
		gridX+=2;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "数量：");
		
		gridX++;
		
		materialCountTextField = new SwsJTextField(panel, GridBagConstraints.HORIZONTAL, gridX, gridY, 2, 1);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 5, 0, gridX, gridY, 1, 1, "出入库");
		
		gridX++;
		
		inOutComboBox = new JComboBox<String>();
		GridBagConstraints gbc_inOutComboBox = new GridBagConstraints();
		gbc_inOutComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_inOutComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_inOutComboBox.gridx = gridX;
		gbc_inOutComboBox.gridy = gridY;
		gbc_inOutComboBox.gridwidth = 2;
		panel.add(inOutComboBox,gbc_inOutComboBox);
		
		gridX+=2;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 0, 0, gridX, gridY, 1, 1, "材质：");
		
		gridX++;
		
		materialComboBox = new JComboBox<String>();
		GridBagConstraints gbc_materialComboBox = new GridBagConstraints();
		gbc_materialComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_materialComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_materialComboBox.gridx = gridX;
		gbc_materialComboBox.gridy = gridY;
		gbc_materialComboBox.gridwidth = 2;
		panel.add(materialComboBox,gbc_materialComboBox);
		
		gridX=0;
		gridY++;
		
		new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 2, 1, "管口状态：");
		
		gridX+=2;
		
		nozzleStatusComboBox = new JComboBox<String>();
		GridBagConstraints gbc_nozzleStatusComboBox = new GridBagConstraints();
		gbc_nozzleStatusComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_nozzleStatusComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_nozzleStatusComboBox.gridx = gridX;
		gbc_nozzleStatusComboBox.gridy = gridY;
		gbc_nozzleStatusComboBox.gridwidth = 4;
		panel.add(nozzleStatusComboBox,gbc_nozzleStatusComboBox);
		
		gridX=0;
		gridY++;
		
		/*new SwsJLabel(panel, GridBagConstraints.BOTH, 10, 0, gridX, gridY, 2, 1, "客户名称：");
		
		gridX+=2;
		
		customerNameComboBox = new JComboBox<String>();
		GridBagConstraints gbc_customerNameComboBox = new GridBagConstraints();
		gbc_customerNameComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_customerNameComboBox.insets = new Insets(0, 5, 0, 5);
		gbc_customerNameComboBox.gridx = gridX;
		gbc_customerNameComboBox.gridy = gridY;
		gbc_customerNameComboBox.gridwidth = 4;
		panel.add(customerNameComboBox,gbc_customerNameComboBox);*/
		
		gridX=0;
		gridY++;
		
		new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 5, 5, gridX, gridY, 3, 1, "标签打印", this);
		
		gridX+=3;
		
		lockBtn = new SwsJButton(panel, GridBagConstraints.HORIZONTAL, 5, 5, gridX, gridY, 2, 1, "锁定", this);
		lockBtn.setForeground(Color.red);
		
		return panel;
	}
	
	private JPanel createListPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300};
		gridBagLayout.rowHeights = new int[]{350};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		return panel;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if("选择".equals(e.getActionCommand()))
		{
			SmartWeighingMaterialSelectFrame.showFrame();
		}
		else if("锁定".equals(e.getActionCommand()))
		{
			lockBtn.setText("解锁");
			setComponentsStatus(false);

			String[] data = new String[]{"7号机组", "12号机组", "15号机组"};

			produceUnitComboBox.addItem(data[0]);
			produceUnitComboBox.addItem(data[1]);
			produceUnitComboBox.addItem(data[2]);
		}
		else if("解锁".equals(e.getActionCommand()))
		{
			SmartWeighingUnlockFrame.showFrame();
		}
		else if("标签打印".equals(e.getActionCommand()))
		{
			
		}
	}

	
	private void setComponentsStatus(boolean bEdite)
	{
		produceDateTextField.setEditable(bEdite);
		produceUnitComboBox.setEnabled(bEdite);
		classNameComboBox.setEnabled(bEdite);
		teamNameComboBox.setEnabled(bEdite);
		weighingPersonComboBox.setEnabled(bEdite);
		selectBtn.setEnabled(bEdite);
		materialNameComboBox.setEnabled(bEdite);
		//customerNameComboBox.setEnabled(bEdite);
		inOutComboBox.setEnabled(bEdite);
		materialComboBox.setEnabled(bEdite);
		materialSpecificationComboBox.setEnabled(bEdite);
		measureWayComboBox.setEnabled(bEdite);
		nozzleStatusComboBox.setEnabled(bEdite);
		materialCodeTextField.setEditable(bEdite);
		outerDiameterTextField.setEditable(bEdite);
		wallThicknessTextField.setEditable(bEdite);
		materialLengthTextField.setEditable(bEdite);
		materialCountTextField.setEditable(bEdite);
	}
	
	/**
	 * 绑定数据
	 */
	private void bindingData() 
	{
		//查询所有班组信息
		List<Team> teamList = SwsQueryDBUtils.queryAllTeam();
		
		for(int i=0; i<teamList.size(); i++)
		{
			Team team = teamList.get(i);
			teamNameComboBox.addItem(team.getTeamName());
			classNameComboBox.addItem(team.getTeamClass());
			produceUnitComboBox.addItem(team.getProduceUnit());
		}
		
		
		//查询所有用户
		List<User> userList = SwsQueryDBUtils.queryAllUser();
		for(int i=0; i<userList.size(); i++)
		{
			User user = userList.get(i);
			weighingPersonComboBox.addItem(user.getUsername());
		}
		
		//查询所有计量方式
		List<String> measureWayList = CommonUtils.getAllMeasureWay();
		for(int i=0; i<measureWayList.size(); i++)
		{
			String measureWay = measureWayList.get(i);
			measureWayComboBox.addItem(measureWay);
		}
		
		//查询所有物料信息
		//List<Material> materialList = SwsQueryDBUtils.queryAllMaterial();
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
			materialSpecificationComboBox.addItem(cun);
		}
		
		//获取所有管口状态
		List<String> nozzleList = CommonUtils.getAllNozzleStatus();
		for(String nozzle:nozzleList)
		{
			nozzleStatusComboBox.addItem(nozzle);
		}
		
		//获取所有出入库状态
		List<String> inOutList = CommonUtils.getAllInOutStatus();
		for(String inOut:inOutList)
		{
			inOutComboBox.addItem(inOut);
		}
		
		//获取所有出入库状态
		List<String> materialQualityList = CommonUtils.getAllMaterialQuality();
		for(String materialQuality:materialQualityList)
		{
			materialComboBox.addItem(materialQuality);
		}

	}
	
	private void clear()
	{
		teamNameComboBox.removeAllItems();
		classNameComboBox.removeAllItems();
		materialNameComboBox.removeAllItems();
		produceUnitComboBox.removeAllItems();
		materialSpecificationComboBox.removeAllItems();
		measureWayComboBox.removeAllItems();
		nozzleStatusComboBox.removeAllItems();
		materialComboBox.removeAllItems();
		inOutComboBox.removeAllItems();
		weighingPersonComboBox.removeAllItems();
	}

	private class EventListener implements SWSEventListener
	{
		public void lmsTransferEvent(SWSEvent event)
		{
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();
			String nvramStr = null;

			int sensorID = 0;

			if (SwsParameter.UNLOCK_EVENT.equals(eventType))
			{
				lockBtn.setText("锁定");
				setComponentsStatus(true);
			}
			else if(SwsParameter.DATABASE_UPDATE_EVENT.equals(eventType))
			{
				clear();
				bindingData();
			}
		}
	}
	
	private Record getRecord() {
		Record record = new Record();
		Material material = new Material();	
		//物料代码
		material.setMaterialCode(Integer.valueOf(materialCodeTextField.getText().toString()));		
		return record;
		
	}
}
