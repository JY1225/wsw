package com.sws.database;

public class DB 
{
	//用户表名
	public final static String USER_TABLE_NAME = "swsUser";
	//日志表
	public final static String LOG_TABLE_NAME = "swsLog";
	//客户表
	public final static String CUSTOMER_TABLE_NAME = "swsCustomer";
	//设备表
	public final static String DEVICE_TABLE_NAME = "swsDevice";
	//班组表
	public final static String TEAM_TABLE_NAME = "swsTeam";
	//物料表
	public final static String MATERIAL_TABLE_NAME = "swsMaterial";
	//记录表
	public final static String RECORD_TABLE_NAME = "swsRecord";
	
	//用户表的字段
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";
	public final static String POWER = "power";//权限
	public final static String REMARK = "remark";//描述

	//日志表的字段
	public final static String LOG_EVENT = "logEvent";
	public final static String LOG_CONTENT = "logContent";
	public final static String LOG_OPERATOR = "logOperator";
	public final static String DEVICE_IP = "deviceIP";
	public final static String LOG_TIME = "logTime";
	
	//设备表的字段
	public final static String DEVICE_NUMBER = "deviceNumber";//设备编号
	public final static String DEVICE_NAME = "deviceName";//设备名称
	public final static String DEVICE_STATE = "deviceState";//设备状态
	
	//班组表的字段
	public final static String TEAM_CODE = "teamCode";//班组代码
	public final static String TEAM_NAME = "teamName";//班组名称
	public final static String TEAM_ALIAS = "teamAlias";//班组别名
	public final static String TEAM_CLASS = "teamClass";//班别
	public final static String PRODUCE_UNIT = "produceUnit";//生产机组
	public final static String MODIFY_TIME = "modifyTime";//修改时间
	
	//物料表的字段
	public final static String MATERIAL_TYPE = "materialType";//物料类型
	public final static String MATERIAL_CODE = "materialCode";//物料代码
	public final static String MATERIAL_NAME = "materialName";//物料名称
	//public final static String MATERIAL_SPECIFITION = "materialSpecifition";//物料规格
	public final static String MATERIAL_DIMENSION = "materialDimension";//物料寸别
	public final static String MATERIAL_ALIAS = "materialAlias";//物料别名
	public final static String OUTER_DIAMETER = "outerDiameter";//外径
	public final static String WALL_THICKNESS = "wallThickness";//壁厚
	public final static String MATERIAL_LENGTH = "materialLength";//长度
	public final static String GALVANIZATION_COEFFICIENT = "galvanizationCoefficient"; //镀锌系数
	public final static String BRANCH_WEIGHT = "branchWeight";//支重
	//public final static String BRANCH_NUMBER = "branchNumber";//每件支数
	public final static String MATERIAL_PIECE_WEIGHT = "materialPieceWeight";//件重
	public final static String MINIMAL_WEIGHT = "minimalWeight";//最小重量
	public final static String MAXIMUM_WEIGHT = "maximumWeight";//最大重量
	public final static String PRODUCE_DATE = "produceDate";//生产日期
	public final static String IS_MATCH = "isMatch"; //是否匹配
	
	//记录表的字段
	public final static String RECORD_NUMBER = "recordNumber";//记录编号
	public final static String CODE_LIST_SERIAL_NUMBER = "codeListSerialNumber";//码单序号
	public final static String CATEGORY = "category";//类别
	public final static String MATERIAL_QUALITY = "materialQuality";//材质
	public final static String PIECE_COUNT = "pieceCount";//件数
	//public final static String SCATTER = "scatter";//散支
	//public final static String TOTAL_COUNT = "totalCount";//总支数
	public final static String BRANCH_COUNT = "branchCount";//支数,根据整件或者散支区分
	public final static String MATERIAL_WEIGHT = "materialWeight";//物料重量
	public final static String IN_OUT_STOCK = "inOutStock";//出入库
	public final static String OPERATOR = "operator";//操作员
	public final static String WEIGHING_TIME = "weighingTime";//称重时间
	public final static String WEIGHER = "weigher";//称重人员
	public final static String MATERIAL_STATE = "materialState";//物料状态，整件还是散支
	public final static String NOZZLE_STATE = "nozzleState";//管口状态
	public final static String MEASURE_WAY = "measureWay";//计量方式
	
	//创建用户表的SQL语句
	public static String createUserSQL =
			"(" +
			"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			USERNAME+" varchar(50) COMMENT '用户名'," +
			PASSWORD+" varchar(50) COMMENT '密码'," +
			POWER+" mediumint COMMENT '权限代号'," +
			REMARK+" varchar(500) COMMENT '描述'"+
			")DEFAULT CHARSET=utf8";

	//创建日志表的SQL语句
	public static String createLogSQL =
			"(" +
			"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			LOG_EVENT+" varchar(50) COMMENT '日志事件'," +
			LOG_CONTENT+" varchar(500) COMMENT '日志内容'," +
			LOG_OPERATOR+" varchar(50) COMMENT '日志操作员'," +
			DEVICE_IP+" varchar(16) COMMENT '操作IP',"+
			LOG_TIME+" datetime COMMENT '日志时间'"+
			")DEFAULT CHARSET=utf8";
	
	//创建设备表的SQL语句
	public static String createDeviceSQL =
			"(" +
			"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			DEVICE_NUMBER+" varchar(50) COMMENT '设备编号'," +
			DEVICE_NAME+" varchar(50) COMMENT '设备名称'," +
			DEVICE_STATE+" varchar(50) COMMENT '设备状态'" +
			")DEFAULT CHARSET=utf8";
	
	//创建班组表的SQL语句
	public static String createTeamSQL =
			"(" +
			"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			TEAM_CODE+" varchar(50) COMMENT '班组代码'," +
			TEAM_NAME+" varchar(50) COMMENT '班组名称'," +
			TEAM_ALIAS+" varchar(50) COMMENT '班组别名'," +
			TEAM_CLASS+" varchar(50) COMMENT '班别',"+
			PRODUCE_UNIT+" varchar(50) COMMENT '生产机组',"+
			MODIFY_TIME+" datetime COMMENT '修改时间',"+
			"INDEX ("+TEAM_CODE+")" +
			")DEFAULT CHARSET=utf8";
	
	//创建物料表的SQL语句
	public static String createMaterialSQL =
			"(" +
			MATERIAL_CODE+" INT COMMENT '物料代码' NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			MATERIAL_TYPE+" mediumint COMMENT '物料类别'," +
			MATERIAL_ALIAS+" varchar(100) COMMENT '物料别名'," +
			MATERIAL_NAME+" varchar(50) COMMENT '物料名称'," +
			//MATERIAL_SPECIFITION+" varchar(50) COMMENT '物料规格'," +
			MATERIAL_DIMENSION+" varchar(50) COMMENT '物料寸别'," +
			OUTER_DIAMETER+" varchar(50) COMMENT '外径',"+
			WALL_THICKNESS+" varchar(50) COMMENT '壁厚',"+
			MATERIAL_LENGTH+" varchar(50) COMMENT '长度',"+
			GALVANIZATION_COEFFICIENT+" varchar(50) COMMENT '镀锌系数', "+
			BRANCH_WEIGHT+" varchar(50) COMMENT '支重', "+
			BRANCH_COUNT+" varchar(50) COMMENT '每件支数', "+
			MATERIAL_PIECE_WEIGHT+" varchar(50) COMMENT '件重', "+
			MINIMAL_WEIGHT+" varchar(50) COMMENT '最小重量', "+
			MAXIMUM_WEIGHT+" varchar(50) COMMENT '最大重量', "+
			PRODUCE_DATE+" datetime COMMENT '生产日期', "+
			IS_MATCH+" varchar(50) COMMENT '是否匹配', "+
			MODIFY_TIME+" datetime COMMENT '修改时间'"+
			")DEFAULT CHARSET=utf8";
	
	//创建物料表的SQL语句
	public static String createRecordSQL =
			"(" +
			"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			RECORD_NUMBER+" varchar(50) COMMENT '记录编号', "+
			DEVICE_NUMBER+" varchar(50) COMMENT '设备编号'," +
			DEVICE_NAME+" varchar(50) COMMENT '设备名称'," +
			CODE_LIST_SERIAL_NUMBER+" varchar(50) COMMENT '码单序号',"+
			MATERIAL_CODE+" varchar(50) COMMENT '物料代码'," +
			MATERIAL_NAME+" varchar(50) COMMENT '物料名称',"+
			MATERIAL_DIMENSION+" varchar(50) COMMENT '物料寸别'," +
			//MATERIAL_SPECIFITION+" varchar(50) COMMENT '物料规格',"+
			MATERIAL_WEIGHT+" varchar(50) COMMENT '物料重量',"+
			WEIGHING_TIME+" datetime COMMENT '称重时间',"+
			OPERATOR+" varchar(50) COMMENT '操作员',"+
			TEAM_CODE+" varchar(50) COMMENT '班组代号',"+
			TEAM_NAME+" varchar(50) COMMENT '班组名称',"+
			TEAM_CLASS+" varchar(50) COMMENT '班别',"+
			PRODUCE_UNIT+" varchar(50) COMMENT '生产机组',"+
			IN_OUT_STOCK+" varchar(50) COMMENT '出入库',"+
			MATERIAL_STATE+" varchar(50) COMMENT '物料状态',"+
			REMARK+" varchar(50) COMMENT '备注信息',"+
			OUTER_DIAMETER+" varchar(50) COMMENT '外径',"+
			WALL_THICKNESS+" varchar(50) COMMENT '壁厚',"+
			MATERIAL_LENGTH+" varchar(50) COMMENT '长度',"+
			GALVANIZATION_COEFFICIENT+" varchar(50) COMMENT '镀锌系数', "+
			//BRANCH_WEIGHT+" varchar(50) COMMENT '支重', "+
			//MATERIAL_PIECE_NUMBER+" varchar(50) COMMENT '每件支数', "+
			//MATERIAL_WEIGHT+" varchar(50) COMMENT '物料重量', "+
			MINIMAL_WEIGHT+" varchar(50) COMMENT '最小重量', "+
			MAXIMUM_WEIGHT+" varchar(50) COMMENT '最大重量', "+
			PRODUCE_DATE+" datetime COMMENT '生产日期', "+
			BRANCH_COUNT+" int COMMENT '支数',"+
			//TOTAL_COUNT+" int COMMENT '总支数',"+
			//SCATTER+" int COMMENT '散支数',"+
			PIECE_COUNT+" int COMMENT '件数',"+
			MEASURE_WAY+" varchar(50) COMMENT '计量方式',"+
			MATERIAL_QUALITY+" varchar(50) COMMENT '材质',"+
			WEIGHER+" varchar(50) COMMENT '称重人员',"+
			NOZZLE_STATE+" varchar(50) COMMENT '管口状态'"+
			")DEFAULT CHARSET=utf8";
}
