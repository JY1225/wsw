package com.sws.base;

import java.util.HashMap;

public class NvramType {
	public enum Type {
		BOOLEAN_TYPE,
		INTEGER_TYPE, 
		FLOAT_TYPE, 
		DOUBLE_TYPE, 
		STRING_TYPE,
	}

	public boolean isValid;

	public boolean bValue;
	public int iValue;
	public float fValue;
	public double dValue;
	public String sValue;

	String value;
	
	public String nvramStr;
	public Type type;
	public int index = -1;
	public boolean bSave = false;	//默认不保存;只有该NVRAM有做读配置文件初始化才自动设为保存;参考:LMSPlatform
	
	public NvramType(String nvramStr, Type type) {
		this.nvramStr = nvramStr;
		this.type = type;
	}

	private void sendEvent(int sensorID)
	{
		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(SwsParameter.EXTRA_SETTING_NVRAM, nvramStr);
		eventExtra.put(SwsParameter.EXTRA_SETTING_VALUE, value);
		eventExtra.put(SwsParameter.EXTRA_SETTING_SAVE, bSave);
		SwsParameter.swsEventManager.sendEvent(SwsParameter.SETTING_EVENT, eventExtra);		
	}
	
	/*private void sendEventUIOnly(int sensorID)
	{
		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_UI_ONLY, true);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, nvramStr);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, value);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, bSave);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT, eventExtra);		
	}*/
	
	public void setValue(int sensorID, boolean _bValue)
	{
		bValue = _bValue;   		
		value = String.valueOf(bValue);
		
		sendEvent(sensorID);
	}
	
	public void setValue(int sensorID, int _iValue)
	{
		iValue = _iValue;   		
		value = String.valueOf(iValue);

		sendEvent(sensorID);
	}
	
	public void setValue(int sensorID, float _fValue)
	{
		fValue = _fValue;   		
		value = String.valueOf(fValue);

		sendEvent(sensorID);
	}
	
	public void setValue(int sensorID, double _dValue)
	{
		dValue = _dValue;   		
		value = String.valueOf(dValue);

		sendEvent(sensorID);
	}
	
	public void setValue(int sensorID, String _sValue)
	{
		sValue = _sValue;   		
		value = sValue;

		sendEvent(sensorID);
	}

	//----------------------------------------------------------
	public void setValueMD5(int sensorID, int _iValue)
	{
		iValue = _iValue; 
		//value = iValue;
		//value = Md5.convertMD5(String.valueOf(iValue));

		//sendEvent(sensorID);
	}
	
	//----------------------------------------------------------
	public void setValueUIOnly(int sensorID, boolean _bValue)
	{
		bValue = _bValue;   		
		
		//sendEventUIOnly(sensorID);
	}
	
	public void setValueUIOnly(int sensorID, int _iValue)
	{
		iValue = _iValue;   		
		value = String.valueOf(iValue);

		//sendEventUIOnly(sensorID);
	}
	
	public void setValueUIOnly(int sensorID, float _fValue)
	{
		fValue = _fValue;   		
		value = String.valueOf(fValue);

		//sendEventUIOnly(sensorID);
	}
	
	public void setValueUIOnly(int sensorID, double _dValue)
	{
		dValue = _dValue;   		
		value = String.valueOf(dValue);

		//sendEventUIOnly(sensorID);
	}
	
	public void setValueUIOnly(int sensorID, String _sValue)
	{
		sValue = _sValue;   		
		value = sValue;

		//sendEventUIOnly(sensorID);
	}
}
