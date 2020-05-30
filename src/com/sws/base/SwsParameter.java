package com.sws.base;

import java.util.HashMap;
import com.sws.events.SWSEventManager;

public class SwsParameter 
{
	public static SWSEventManager swsEventManager;
	public static String loginUserName="";
	public static SwsParameter innerObj = new SwsParameter();
	public final static String EXTRA_SETTING_NVRAM = "EXTRA_SETTING_NVRAM";
	public final static String EXTRA_SETTING_VALUE = "EXTRA_SETTING_VALUE";
	public final static String EXTRA_SETTING_SAVE = "EXTRA_SETTING_SAVE";
	public final static String EXTRA_SETTING_EVENT_TYPE = "EXTRA_SETTING_EVENT_TYPE";
	public final static String SETTING_EVENT = "SETTING_EVENT";
	public final static String UNLOCK_EVENT = "UNLOCK_EVENT";
	//public final static String EXTRA_SETTING_NVRAM = "EXTRA_SETTING_NVRAM";
	//public final static String EXTRA_SETTING_NVRAM = "EXTRA_SETTING_NVRAM";
	public final static String DEVICE_SEARCH_RESULT_EVENT = "DEVICE_SEARCH_RESULT_EVENT";
	public final static String CONTROLL_CONNECTION_EVENT = "CONTROLL_CONNECTION_EVENT";
	public final static String WEIGHT_CONNECTION_EVENT = "WEIGHT_CONNECTION_EVENT";
	public final static String DATABASE_UPDATE_EVENT = "DATABASE_UPDATE_EVENT";
	
	public static NvramType sNvramCompanyName = new NvramType("nvram_sCompanyName", NvramType.Type.STRING_TYPE);
	public static NvramType sNvramCompanyAddress = new NvramType("nvram_sCompanyAddress", NvramType.Type.STRING_TYPE);
	public static NvramType sNvramCompanyPhone = new NvramType("nvram_sCompanyPhone", NvramType.Type.STRING_TYPE);
	public static NvramType sNvramCompanyFax = new NvramType("nvram_sCompanyFax", NvramType.Type.STRING_TYPE);
	//public static NvramType sNvramCompanyName = new NvramType("nvram_sCompanyName", NvramType.Type.STRING_TYPE);
	public static NvramType fNvramPrintPaperWidth = new NvramType("nvram_fPrintPaperWidth", NvramType.Type.FLOAT_TYPE);
	public static NvramType fNvramPrintPaperHeight = new NvramType("nvram_fPrintPaperHeight", NvramType.Type.FLOAT_TYPE);
	public static NvramType fNvramPrintTopMargin = new NvramType("nvram_fPrintTopMargin", NvramType.Type.FLOAT_TYPE);
	public static NvramType fNvramPrintLeftMargin = new NvramType("nvram_fPrintLeftMargin", NvramType.Type.FLOAT_TYPE);
	public static NvramType fNvramPrintRowHeight = new NvramType("nvram_fPrintRowHeight", NvramType.Type.FLOAT_TYPE);
	public static NvramType iNvramPrintFontSize = new NvramType("nvram_iPrintFontSize", NvramType.Type.INTEGER_TYPE);
	public static NvramType bNvramAutoPrint = new NvramType("nvram_bAutoPrint", NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramPrintTest = new NvramType("nvram_bPrintTest", NvramType.Type.BOOLEAN_TYPE);
	
	//称重类型
	public class EnumWeightDeviceType extends EnumType{		
		public static final String DEVICE_NOT = "DEVICE_NOT";
		public static final String WEIGHT_ZZWZ = "WEIGHT_ZZWZ";

		public EnumWeightDeviceType() {
			map.put(DEVICE_NOT, "未知类型");  
			map.put(WEIGHT_ZZWZ, "WEIGHT_ZZWZ");//郑州沃众  
		}	
	}
	public static EnumWeightDeviceType enumWeightDeviceType = innerObj.new EnumWeightDeviceType(); 
	public static NvramType eNvramWeightDeviceType = new NvramType("nvram_weightDeviceType",NvramType.Type.STRING_TYPE);
	
	//串口号
	public static NvramType sNvramWeightSerialPort = new NvramType("nvram_sWeightSerialPort", NvramType.Type.STRING_TYPE);
	
	//波特率
	public class EnumProtocolBaudrateType extends EnumType{		
		
		public static final String BAUDRATE_110 = "BAUDRATE_110";
		public static final String BAUDRATE_300 = "BAUDRATE_300";
		public static final String BAUDRATE_600 = "BAUDRATE_600";
		public static final String BAUDRATE_1200 = "BAUDRATE_1200";
		public static final String BAUDRATE_2400 = "BAUDRATE_2400";
		public static final String BAUDRATE_4800 = "BAUDRATE_4800";
		public static final String BAUDRATE_9600 = "BAUDRATE_9600";
		public static final String BAUDRATE_14400 = "BAUDRATE_14400";
		public static final String BAUDRATE_19200 = "BAUDRATE_19200";
		public static final String BAUDRATE_38400 = "BAUDRATE_38400";
		public static final String BAUDRATE_56000 = "BAUDRATE_56000";
		public static final String BAUDRATE_57600 = "BAUDRATE_57600";
		public static final String BAUDRATE_115200 = "BAUDRATE_115200";
		public static final String BAUDRATE_128000 = "BAUDRATE_128000";
		public static final String BAUDRATE_256000 = "BAUDRATE_256000";
		
		public EnumProtocolBaudrateType() {
			map.put(BAUDRATE_110,"110");  
			map.put(BAUDRATE_300,"300");  
			map.put(BAUDRATE_600,"600");  
			map.put(BAUDRATE_1200,"1200"); 
			map.put(BAUDRATE_2400,"2400");  
			map.put(BAUDRATE_4800,"4800");  
			map.put(BAUDRATE_9600,"9600");  
			map.put(BAUDRATE_14400,"14400"); 
			map.put(BAUDRATE_19200,"19200");  
			map.put(BAUDRATE_38400,"38400");  
			map.put(BAUDRATE_56000,"56000");  
			map.put(BAUDRATE_57600,"57600"); 
			map.put(BAUDRATE_115200,"115200");  
			map.put(BAUDRATE_128000,"128000");  
			map.put(BAUDRATE_256000,"256000");  
		}	
	}
	
	public static EnumProtocolBaudrateType enumProtocolBaudrateType = innerObj.new EnumProtocolBaudrateType(); 
	public static NvramType eNvramEnumProtocolBaudrate = new NvramType("nvram_EnumProtocolBaudrate", NvramType.Type.STRING_TYPE);
	
	//信道号
	public class EnumChannelNumberType extends EnumType{		
		
		public static final String CHANNEL_1 = "CHANNEL_1";
		public static final String CHANNEL_2 = "CHANNEL_2";
		public static final String CHANNEL_3 = "CHANNEL_3";
		public static final String CHANNEL_4 = "CHANNEL_4";
		public static final String CHANNEL_5 = "CHANNEL_5";
		public static final String CHANNEL_6 = "CHANNEL_6";
		public static final String CHANNEL_7 = "CHANNEL_7";
		public static final String CHANNEL_8 = "CHANNEL_8";
		public static final String CHANNEL_9 = "CHANNEL_9";
		public static final String CHANNEL_10 = "CHANNEL_10";
		public static final String CHANNEL_11 = "CHANNEL_11";
		public static final String CHANNEL_12 = "CHANNEL_12";
		public static final String CHANNEL_13 = "CHANNEL_13";
		public static final String CHANNEL_14 = "CHANNEL_14";
		public static final String CHANNEL_15 = "CHANNEL_15";
		public static final String CHANNEL_16 = "CHANNEL_16";
		public static final String CHANNEL_17 = "CHANNEL_17";
		public static final String CHANNEL_18 = "CHANNEL_18";
		public static final String CHANNEL_19 = "CHANNEL_19";
		public static final String CHANNEL_20 = "CHANNEL_20";
		public static final String CHANNEL_21 = "CHANNEL_21";
		public static final String CHANNEL_22 = "CHANNEL_22";
		public static final String CHANNEL_23 = "CHANNEL_23";
		public static final String CHANNEL_24 = "CHANNEL_24";
		public static final String CHANNEL_25 = "CHANNEL_25";
		public static final String CHANNEL_26 = "CHANNEL_26";
		public static final String CHANNEL_27 = "CHANNEL_27";
		public static final String CHANNEL_28 = "CHANNEL_28";
		public static final String CHANNEL_29 = "CHANNEL_29";
		public static final String CHANNEL_30 = "CHANNEL_30";
		public static final String CHANNEL_31 = "CHANNEL_31";
		public static final String CHANNEL_32 = "CHANNEL_32";
		public static final String CHANNEL_33 = "CHANNEL_33";
		public static final String CHANNEL_34 = "CHANNEL_34";
		public static final String CHANNEL_35 = "CHANNEL_35";
		public static final String CHANNEL_36 = "CHANNEL_36";
		public static final String CHANNEL_37 = "CHANNEL_37";
		public static final String CHANNEL_38 = "CHANNEL_38";
		public static final String CHANNEL_39 = "CHANNEL_39";
		public static final String CHANNEL_40 = "CHANNEL_40";
		public static final String CHANNEL_41 = "CHANNEL_41";
		public static final String CHANNEL_42 = "CHANNEL_42";
		public static final String CHANNEL_43 = "CHANNEL_43";
		public static final String CHANNEL_44 = "CHANNEL_44";
		public static final String CHANNEL_45 = "CHANNEL_45";
		public static final String CHANNEL_46 = "CHANNEL_46";
		public static final String CHANNEL_47 = "CHANNEL_47";
		public static final String CHANNEL_48 = "CHANNEL_48";
		public static final String CHANNEL_49 = "CHANNEL_49";
		public static final String CHANNEL_50 = "CHANNEL_50";
		public static final String CHANNEL_51 = "CHANNEL_51";
		public static final String CHANNEL_52 = "CHANNEL_52";
		public static final String CHANNEL_53 = "CHANNEL_53";
		public static final String CHANNEL_54 = "CHANNEL_54";
		public static final String CHANNEL_55 = "CHANNEL_55";
		public static final String CHANNEL_56 = "CHANNEL_56";
		public static final String CHANNEL_57 = "CHANNEL_57";
		public static final String CHANNEL_58 = "CHANNEL_58";
		public static final String CHANNEL_59 = "CHANNEL_59";
		public static final String CHANNEL_60 = "CHANNEL_60";
		public static final String CHANNEL_61 = "CHANNEL_61";
		public static final String CHANNEL_62 = "CHANNEL_62";
		public static final String CHANNEL_63 = "CHANNEL_63";
		public static final String CHANNEL_64 = "CHANNEL_64";
		
		public EnumChannelNumberType() {
			map.put(CHANNEL_1,"1");  
			map.put(CHANNEL_2,"2");  
			map.put(CHANNEL_3,"3");  
			map.put(CHANNEL_4,"4");  
			map.put(CHANNEL_5,"5");  
			map.put(CHANNEL_6,"6");  
			map.put(CHANNEL_7,"7");  
			map.put(CHANNEL_8,"8");  
			map.put(CHANNEL_9,"9");  
			map.put(CHANNEL_10,"10");  
			map.put(CHANNEL_11,"11");  
			map.put(CHANNEL_12,"12");  
			map.put(CHANNEL_13,"13");  
			map.put(CHANNEL_14,"14");  
			map.put(CHANNEL_15,"15");  
			map.put(CHANNEL_16,"16");  
			map.put(CHANNEL_17,"17");  
			map.put(CHANNEL_18,"18");  
			map.put(CHANNEL_19,"19");  
			map.put(CHANNEL_20,"20");
			map.put(CHANNEL_21,"21");  
			map.put(CHANNEL_22,"22");  
			map.put(CHANNEL_23,"23");  
			map.put(CHANNEL_24,"24");  
			map.put(CHANNEL_25,"25");  
			map.put(CHANNEL_26,"26");  
			map.put(CHANNEL_27,"27");  
			map.put(CHANNEL_28,"28");  
			map.put(CHANNEL_29,"29");  
			map.put(CHANNEL_30,"10");
			map.put(CHANNEL_31,"31");  
			map.put(CHANNEL_32,"32");  
			map.put(CHANNEL_33,"33");  
			map.put(CHANNEL_34,"34");  
			map.put(CHANNEL_35,"35");  
			map.put(CHANNEL_36,"36");  
			map.put(CHANNEL_37,"37");  
			map.put(CHANNEL_38,"38");  
			map.put(CHANNEL_39,"39");  
			map.put(CHANNEL_40,"40");
			map.put(CHANNEL_41,"41");  
			map.put(CHANNEL_42,"42");  
			map.put(CHANNEL_43,"43");  
			map.put(CHANNEL_44,"44");  
			map.put(CHANNEL_45,"45");  
			map.put(CHANNEL_46,"46");  
			map.put(CHANNEL_47,"47");  
			map.put(CHANNEL_48,"48");  
			map.put(CHANNEL_49,"49");  
			map.put(CHANNEL_50,"50");
			map.put(CHANNEL_51,"51");  
			map.put(CHANNEL_52,"52");  
			map.put(CHANNEL_53,"53");  
			map.put(CHANNEL_54,"54");  
			map.put(CHANNEL_55,"55");  
			map.put(CHANNEL_56,"56");  
			map.put(CHANNEL_57,"57");  
			map.put(CHANNEL_58,"58");  
			map.put(CHANNEL_59,"59");  
			map.put(CHANNEL_60,"60");
			map.put(CHANNEL_61,"61");  
			map.put(CHANNEL_62,"62");  
			map.put(CHANNEL_63,"63");  
			map.put(CHANNEL_64,"64"); 
		}	
	}
	
	public static EnumChannelNumberType enumChannelNumberType = innerObj.new EnumChannelNumberType(); 
	public static NvramType eNvramChannelNumber = new NvramType("nvram_channelNumber", NvramType.Type.STRING_TYPE);
	
	//匹配区间
	public class EnumMatchIntervalType extends EnumType{		
		public static final String MATCH_YES = "MATCH_YES";
		public static final String MATCH_NO = "MATCH_NO";

		public EnumMatchIntervalType() {
			map.put(MATCH_YES, "是");  
			map.put(MATCH_NO, "否");  
		}	
	}
	public static EnumMatchIntervalType enumMatchIntervalType = innerObj.new EnumMatchIntervalType(); 
	public static NvramType eNvramMatchIntervalType = new NvramType("nvram_matchIntervalType",NvramType.Type.STRING_TYPE);
		
	//货物代码
	public static NvramType iNvramCargoCode = new NvramType("nvram_iCargoCode", NvramType.Type.INTEGER_TYPE);
	//货物别名
	public static NvramType sNvramCargoAlias = new NvramType("nvram_sCargoAlias", NvramType.Type.STRING_TYPE);
	//货物名称
	public static NvramType sNvramCargoName = new NvramType("nvram_sCargoName", NvramType.Type.STRING_TYPE);
	//货物规格
	public static NvramType sNvramCargoSpecifications = new NvramType("nvram_sCargoSpecifications", NvramType.Type.STRING_TYPE);
	//外径
	public static NvramType fNvramOuterDiameter = new NvramType("nvram_fOuterDiameter", NvramType.Type.FLOAT_TYPE);
	//壁厚
	public static NvramType fNvramWallThickness = new NvramType("nvram_fWallThickness", NvramType.Type.FLOAT_TYPE);
	//长度
	public static NvramType fNvramCargoLength = new NvramType("nvram_fCargoLength", NvramType.Type.FLOAT_TYPE);
	//镀锌系数
	public static NvramType fNvramGalvanizationCoefficient = new NvramType("nvram_fGalvanizationCoefficient", NvramType.Type.FLOAT_TYPE);
	//支重
	public static NvramType fNvramCargoWeight = new NvramType("nvram_fCargoWeight", NvramType.Type.FLOAT_TYPE);
	//每件支数
	public static NvramType iNvramEveryCount = new NvramType("nvram_iEveryCount", NvramType.Type.INTEGER_TYPE);
	//件重
	public static NvramType iNvramPieceWeight = new NvramType("nvram_iPieceWeight", NvramType.Type.INTEGER_TYPE);
	//最小重量
	public static NvramType iNvramMinimalWeight= new NvramType("nvram_iMinimalWeight", NvramType.Type.INTEGER_TYPE);
	//最大重量
	public static NvramType iNvramMaximumWeight = new NvramType("nvram_iMaximumWeight", NvramType.Type.INTEGER_TYPE);
	//班组代码
	public static NvramType iNvramTeamCode = new NvramType("nvram_iTeamCode", NvramType.Type.INTEGER_TYPE);
	//班组别名
	public static NvramType sNvramTeamAlias = new NvramType("nvram_sTeamAlias", NvramType.Type.STRING_TYPE);
	//班组名称
	public static NvramType sNvramTeamName = new NvramType("nvram_sTeamName", NvramType.Type.STRING_TYPE);
	//班别
	public static NvramType sNvramTeamClass = new NvramType("nvram_sTeamClass", NvramType.Type.STRING_TYPE);
	//生产机组
	public static NvramType sNvramProduceUnit = new NvramType("nvram_sProduceUnit", NvramType.Type.STRING_TYPE);
	
	//控制器ip
	public static NvramType sNvramControllIP = new NvramType("nvram_sControllIP", NvramType.Type.STRING_TYPE);
	//控制器端口号
	public static NvramType iNvramControllPort = new NvramType("nvram_iControllPort", NvramType.Type.INTEGER_TYPE);
	//控制器类型
	public class EnumControllDeviceType extends EnumType{		
		public static final String DEVICE_NOT = "DEVICE_NOT";
		public static final String CONTROLL_KXHL = "CONTROLL_KXHL";//科星互联

		public EnumControllDeviceType() {
			map.put(DEVICE_NOT, "未知类型");  
			map.put(CONTROLL_KXHL, "CONTROLL_KXHL");  
		}	
	}
	public static EnumControllDeviceType enumControllDeviceType = innerObj.new EnumControllDeviceType(); 
	public static NvramType eNvramControllDeviceType = new NvramType("nvram_controllDeviceType",NvramType.Type.STRING_TYPE);
	
	public static NvramType sNvramControllSendMessage = new NvramType("nvram_sControllSendMessage", NvramType.Type.STRING_TYPE);
	
	//串口状态
	
	
	public static void initParameter()
	{
		sNvramCompanyName.sValue = Configure.getStringValue(sNvramCompanyName.nvramStr, "XXXXXX有限公司");
		sNvramCompanyAddress.sValue = Configure.getStringValue(sNvramCompanyAddress.nvramStr, "XX市XX区XX街道XX号");
		sNvramCompanyPhone.sValue = Configure.getStringValue(sNvramCompanyPhone.nvramStr, "131XXXXXXXX");
		sNvramCompanyFax.sValue = Configure.getStringValue(sNvramCompanyFax.nvramStr, "123");
		fNvramPrintPaperWidth.fValue = Configure.getFloatValue(fNvramPrintPaperWidth.nvramStr, 0);
		fNvramPrintPaperHeight.fValue = Configure.getFloatValue(fNvramPrintPaperHeight.nvramStr, 0);
		fNvramPrintTopMargin.fValue = Configure.getFloatValue(fNvramPrintTopMargin.nvramStr, 0);
		fNvramPrintLeftMargin.fValue = Configure.getFloatValue(fNvramPrintLeftMargin.nvramStr, 0);
		fNvramPrintRowHeight.fValue = Configure.getFloatValue(fNvramPrintRowHeight.nvramStr, 0);
		iNvramPrintFontSize.iValue = Configure.getIntValue(iNvramPrintFontSize.nvramStr, 12);
		bNvramAutoPrint.bValue = Configure.getBooleanValue(bNvramAutoPrint.nvramStr, true);
		enumWeightDeviceType.key = Configure.getStringValue(eNvramWeightDeviceType.nvramStr, enumWeightDeviceType.DEVICE_NOT);
		sNvramWeightSerialPort.sValue = Configure.getStringValue(sNvramWeightSerialPort.nvramStr, "COM1");
		enumProtocolBaudrateType.key = Configure.getStringValue(eNvramEnumProtocolBaudrate.nvramStr, enumProtocolBaudrateType.BAUDRATE_9600);
		enumChannelNumberType.key = Configure.getStringValue(eNvramChannelNumber.nvramStr, enumChannelNumberType.CHANNEL_1);
		enumMatchIntervalType.key = Configure.getStringValue(eNvramMatchIntervalType.nvramStr, enumMatchIntervalType.MATCH_NO);
		
		sNvramCargoAlias.sValue = Configure.getStringValue(sNvramCargoAlias.nvramStr, "");
		sNvramCargoName.sValue = Configure.getStringValue(sNvramCargoName.nvramStr, "");
		sNvramCargoSpecifications.sValue = Configure.getStringValue(sNvramCargoSpecifications.nvramStr, "");
		sNvramTeamAlias.sValue = Configure.getStringValue(sNvramTeamAlias.nvramStr, "");
		sNvramTeamName.sValue = Configure.getStringValue(sNvramTeamName.nvramStr, "");
		sNvramTeamClass.sValue = Configure.getStringValue(sNvramTeamClass.nvramStr, "");
		sNvramProduceUnit.sValue = Configure.getStringValue(sNvramProduceUnit.nvramStr, "");
		
		sNvramControllIP.sValue = Configure.getStringValue(sNvramControllIP.nvramStr, "192.168.1.100");
		iNvramControllPort.iValue = Configure.getIntValue(iNvramControllPort.nvramStr, 50000);
		enumControllDeviceType.key = Configure.getStringValue(eNvramControllDeviceType.nvramStr, enumControllDeviceType.DEVICE_NOT);
		
	}
	
	public static NvramType bNvramDeviceConnection = new NvramType("nvram_bDeviceConnection", NvramType.Type.BOOLEAN_TYPE);
	
	public static void sendDeviceConnected(String eventType, boolean cameraConnect)
	{
		 //CameraParameter.bNvramCameraConnected[sensorID].bValue = cameraConnect;
		 bNvramDeviceConnection.bValue = cameraConnect;
		 HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		 eventExtra.put(EXTRA_SETTING_NVRAM,  bNvramDeviceConnection.nvramStr);
		 eventExtra.put(EXTRA_SETTING_VALUE,  bNvramDeviceConnection.bValue);
		 eventExtra.put(EXTRA_SETTING_SAVE, false);
		 //eventExtra.put(EXTRA_SENSOR_ID, sensorID);
		 swsEventManager.sendEvent(eventType,eventExtra);	
	}
	
	public static void sendNvram(String eventType, NvramType nvramType, Object value)
	{
		 HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		 eventExtra.put(EXTRA_SETTING_NVRAM,  nvramType.nvramStr);
		 
		 if(nvramType.type == NvramType.Type.INTEGER_TYPE)
		 {
			 nvramType.iValue = (int) value;
			 eventExtra.put(EXTRA_SETTING_VALUE, nvramType.iValue);
		 }
		 else if(nvramType.type == NvramType.Type.FLOAT_TYPE)
		 {
			 nvramType.fValue = (float) value;
			 eventExtra.put(EXTRA_SETTING_VALUE,  nvramType.fValue);
		 } 
		 else if(nvramType.type == NvramType.Type.DOUBLE_TYPE)
		 {
			 nvramType.dValue = (double) value;
			 eventExtra.put(EXTRA_SETTING_VALUE,  nvramType.dValue);
		 }
		 else if(nvramType.type == NvramType.Type.STRING_TYPE)
		 {
			 nvramType.sValue = (String) value;
			 eventExtra.put(EXTRA_SETTING_VALUE,  nvramType.sValue);
		 }
		 else if(nvramType.type == NvramType.Type.BOOLEAN_TYPE)
		 {
			 nvramType.bValue = (boolean) value;
			 eventExtra.put(EXTRA_SETTING_VALUE,  nvramType.bValue);
		 }
		 
		 eventExtra.put(EXTRA_SETTING_SAVE, false);
		 //eventExtra.put(EXTRA_SENSOR_ID, sensorID);
		 swsEventManager.sendEvent(eventType,eventExtra);	
	}
}
