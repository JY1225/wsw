package com.sws.database;

public class DB 
{
	//�û�����
	public final static String USER_TABLE_NAME = "swsUser";
	//��־��
	public final static String LOG_TABLE_NAME = "swsLog";
	//�ͻ���
	public final static String CUSTOMER_TABLE_NAME = "swsCustomer";
	//�豸��
	public final static String DEVICE_TABLE_NAME = "swsDevice";
	//�����
	public final static String TEAM_TABLE_NAME = "swsTeam";
	//���ϱ�
	public final static String MATERIAL_TABLE_NAME = "swsMaterial";
	//��¼��
	public final static String RECORD_TABLE_NAME = "swsRecord";
	
	//�û�����ֶ�
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";
	public final static String POWER = "power";//Ȩ��
	public final static String REMARK = "remark";//����

	//��־����ֶ�
	public final static String LOG_EVENT = "logEvent";
	public final static String LOG_CONTENT = "logContent";
	public final static String LOG_OPERATOR = "logOperator";
	public final static String DEVICE_IP = "deviceIP";
	public final static String LOG_TIME = "logTime";
	
	//�豸����ֶ�
	public final static String DEVICE_NUMBER = "deviceNumber";//�豸���
	public final static String DEVICE_NAME = "deviceName";//�豸����
	public final static String DEVICE_STATE = "deviceState";//�豸״̬
	
	//�������ֶ�
	public final static String TEAM_CODE = "teamCode";//�������
	public final static String TEAM_NAME = "teamName";//��������
	public final static String TEAM_ALIAS = "teamAlias";//�������
	public final static String TEAM_CLASS = "teamClass";//���
	public final static String PRODUCE_UNIT = "produceUnit";//��������
	public final static String MODIFY_TIME = "modifyTime";//�޸�ʱ��
	
	//���ϱ���ֶ�
	public final static String MATERIAL_TYPE = "materialType";//��������
	public final static String MATERIAL_CODE = "materialCode";//���ϴ���
	public final static String MATERIAL_NAME = "materialName";//��������
	//public final static String MATERIAL_SPECIFITION = "materialSpecifition";//���Ϲ��
	public final static String MATERIAL_DIMENSION = "materialDimension";//���ϴ��
	public final static String MATERIAL_ALIAS = "materialAlias";//���ϱ���
	public final static String OUTER_DIAMETER = "outerDiameter";//�⾶
	public final static String WALL_THICKNESS = "wallThickness";//�ں�
	public final static String MATERIAL_LENGTH = "materialLength";//����
	public final static String GALVANIZATION_COEFFICIENT = "galvanizationCoefficient"; //��пϵ��
	public final static String BRANCH_WEIGHT = "branchWeight";//֧��
	//public final static String BRANCH_NUMBER = "branchNumber";//ÿ��֧��
	public final static String MATERIAL_PIECE_WEIGHT = "materialPieceWeight";//����
	public final static String MINIMAL_WEIGHT = "minimalWeight";//��С����
	public final static String MAXIMUM_WEIGHT = "maximumWeight";//�������
	public final static String PRODUCE_DATE = "produceDate";//��������
	public final static String IS_MATCH = "isMatch"; //�Ƿ�ƥ��
	
	//��¼����ֶ�
	public final static String RECORD_NUMBER = "recordNumber";//��¼���
	public final static String CODE_LIST_SERIAL_NUMBER = "codeListSerialNumber";//�뵥���
	public final static String CATEGORY = "category";//���
	public final static String MATERIAL_QUALITY = "materialQuality";//����
	public final static String PIECE_COUNT = "pieceCount";//����
	//public final static String SCATTER = "scatter";//ɢ֧
	//public final static String TOTAL_COUNT = "totalCount";//��֧��
	public final static String BRANCH_COUNT = "branchCount";//֧��,������������ɢ֧����
	public final static String MATERIAL_WEIGHT = "materialWeight";//��������
	public final static String IN_OUT_STOCK = "inOutStock";//�����
	public final static String OPERATOR = "operator";//����Ա
	public final static String WEIGHING_TIME = "weighingTime";//����ʱ��
	public final static String WEIGHER = "weigher";//������Ա
	public final static String MATERIAL_STATE = "materialState";//����״̬����������ɢ֧
	public final static String NOZZLE_STATE = "nozzleState";//�ܿ�״̬
	public final static String MEASURE_WAY = "measureWay";//������ʽ
	
	//�����û����SQL���
	public static String createUserSQL =
			"(" +
			"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			USERNAME+" varchar(50) COMMENT '�û���'," +
			PASSWORD+" varchar(50) COMMENT '����'," +
			POWER+" mediumint COMMENT 'Ȩ�޴���'," +
			REMARK+" varchar(500) COMMENT '����'"+
			")DEFAULT CHARSET=utf8";

	//������־���SQL���
	public static String createLogSQL =
			"(" +
			"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			LOG_EVENT+" varchar(50) COMMENT '��־�¼�'," +
			LOG_CONTENT+" varchar(500) COMMENT '��־����'," +
			LOG_OPERATOR+" varchar(50) COMMENT '��־����Ա'," +
			DEVICE_IP+" varchar(16) COMMENT '����IP',"+
			LOG_TIME+" datetime COMMENT '��־ʱ��'"+
			")DEFAULT CHARSET=utf8";
	
	//�����豸���SQL���
	public static String createDeviceSQL =
			"(" +
			"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			DEVICE_NUMBER+" varchar(50) COMMENT '�豸���'," +
			DEVICE_NAME+" varchar(50) COMMENT '�豸����'," +
			DEVICE_STATE+" varchar(50) COMMENT '�豸״̬'" +
			")DEFAULT CHARSET=utf8";
	
	//����������SQL���
	public static String createTeamSQL =
			"(" +
			"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			TEAM_CODE+" varchar(50) COMMENT '�������'," +
			TEAM_NAME+" varchar(50) COMMENT '��������'," +
			TEAM_ALIAS+" varchar(50) COMMENT '�������'," +
			TEAM_CLASS+" varchar(50) COMMENT '���',"+
			PRODUCE_UNIT+" varchar(50) COMMENT '��������',"+
			MODIFY_TIME+" datetime COMMENT '�޸�ʱ��',"+
			"INDEX ("+TEAM_CODE+")" +
			")DEFAULT CHARSET=utf8";
	
	//�������ϱ��SQL���
	public static String createMaterialSQL =
			"(" +
			MATERIAL_CODE+" INT COMMENT '���ϴ���' NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			MATERIAL_TYPE+" mediumint COMMENT '�������'," +
			MATERIAL_ALIAS+" varchar(100) COMMENT '���ϱ���'," +
			MATERIAL_NAME+" varchar(50) COMMENT '��������'," +
			//MATERIAL_SPECIFITION+" varchar(50) COMMENT '���Ϲ��'," +
			MATERIAL_DIMENSION+" varchar(50) COMMENT '���ϴ��'," +
			OUTER_DIAMETER+" varchar(50) COMMENT '�⾶',"+
			WALL_THICKNESS+" varchar(50) COMMENT '�ں�',"+
			MATERIAL_LENGTH+" varchar(50) COMMENT '����',"+
			GALVANIZATION_COEFFICIENT+" varchar(50) COMMENT '��пϵ��', "+
			BRANCH_WEIGHT+" varchar(50) COMMENT '֧��', "+
			BRANCH_COUNT+" varchar(50) COMMENT 'ÿ��֧��', "+
			MATERIAL_PIECE_WEIGHT+" varchar(50) COMMENT '����', "+
			MINIMAL_WEIGHT+" varchar(50) COMMENT '��С����', "+
			MAXIMUM_WEIGHT+" varchar(50) COMMENT '�������', "+
			PRODUCE_DATE+" datetime COMMENT '��������', "+
			IS_MATCH+" varchar(50) COMMENT '�Ƿ�ƥ��', "+
			MODIFY_TIME+" datetime COMMENT '�޸�ʱ��'"+
			")DEFAULT CHARSET=utf8";
	
	//�������ϱ��SQL���
	public static String createRecordSQL =
			"(" +
			"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			//-------------------------------------------------------------------
			RECORD_NUMBER+" varchar(50) COMMENT '��¼���', "+
			DEVICE_NUMBER+" varchar(50) COMMENT '�豸���'," +
			DEVICE_NAME+" varchar(50) COMMENT '�豸����'," +
			CODE_LIST_SERIAL_NUMBER+" varchar(50) COMMENT '�뵥���',"+
			MATERIAL_CODE+" varchar(50) COMMENT '���ϴ���'," +
			MATERIAL_NAME+" varchar(50) COMMENT '��������',"+
			MATERIAL_DIMENSION+" varchar(50) COMMENT '���ϴ��'," +
			//MATERIAL_SPECIFITION+" varchar(50) COMMENT '���Ϲ��',"+
			MATERIAL_WEIGHT+" varchar(50) COMMENT '��������',"+
			WEIGHING_TIME+" datetime COMMENT '����ʱ��',"+
			OPERATOR+" varchar(50) COMMENT '����Ա',"+
			TEAM_CODE+" varchar(50) COMMENT '�������',"+
			TEAM_NAME+" varchar(50) COMMENT '��������',"+
			TEAM_CLASS+" varchar(50) COMMENT '���',"+
			PRODUCE_UNIT+" varchar(50) COMMENT '��������',"+
			IN_OUT_STOCK+" varchar(50) COMMENT '�����',"+
			MATERIAL_STATE+" varchar(50) COMMENT '����״̬',"+
			REMARK+" varchar(50) COMMENT '��ע��Ϣ',"+
			OUTER_DIAMETER+" varchar(50) COMMENT '�⾶',"+
			WALL_THICKNESS+" varchar(50) COMMENT '�ں�',"+
			MATERIAL_LENGTH+" varchar(50) COMMENT '����',"+
			GALVANIZATION_COEFFICIENT+" varchar(50) COMMENT '��пϵ��', "+
			//BRANCH_WEIGHT+" varchar(50) COMMENT '֧��', "+
			//MATERIAL_PIECE_NUMBER+" varchar(50) COMMENT 'ÿ��֧��', "+
			//MATERIAL_WEIGHT+" varchar(50) COMMENT '��������', "+
			MINIMAL_WEIGHT+" varchar(50) COMMENT '��С����', "+
			MAXIMUM_WEIGHT+" varchar(50) COMMENT '�������', "+
			PRODUCE_DATE+" datetime COMMENT '��������', "+
			BRANCH_COUNT+" int COMMENT '֧��',"+
			//TOTAL_COUNT+" int COMMENT '��֧��',"+
			//SCATTER+" int COMMENT 'ɢ֧��',"+
			PIECE_COUNT+" int COMMENT '����',"+
			MEASURE_WAY+" varchar(50) COMMENT '������ʽ',"+
			MATERIAL_QUALITY+" varchar(50) COMMENT '����',"+
			WEIGHER+" varchar(50) COMMENT '������Ա',"+
			NOZZLE_STATE+" varchar(50) COMMENT '�ܿ�״̬'"+
			")DEFAULT CHARSET=utf8";
}
