package com.sws.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configure {
	
	private static String configName = "config.ini";
	
	public static String getStringValue(String key, String defaultValue)
	{
		String value = readProperty(key);
		System.out.println("读取的value="+value+", key="+key);
		if(value == null || "null".equals(value))
		{
			return defaultValue;
		}
		
		return value;
	}
	
	public static int getIntValue(String key, int defaultValue)
	{
		String value = readProperty(key);
		System.out.println("读取的value="+value+", key="+key);
		if(value == null || "null".equals(value))
		{
			return defaultValue;
		}
		
		return Integer.parseInt(value);
	}
	
	public static float getFloatValue(String key, float defaultValue)
	{
		String value = readProperty(key);
		System.out.println("读取的value="+value+", key="+key);
		if(value == null || "null".equals(value))
		{
			return defaultValue;
		}
		
		return Float.parseFloat(value);
	}
	
	public static boolean getBooleanValue(String key, boolean defaultValue)
	{
		String value = readProperty(key);
		System.out.println("读取的value="+value+", key="+key);
		if(value == null || "null".equals(value))
		{
			return defaultValue;
		}
		
		return Boolean.parseBoolean(value);
	}

	public static void saveProperty(String key, String value)
	{
		//System.out.println("运行到这里，key="+key+", value="+value);
		File configFile = new File(configName);
		FileInputStream pInStream=null;
		
		try {
			pInStream = new FileInputStream(configFile);
		} catch (FileNotFoundException e) {
			Log.exceptionDialog(e);
		}
		
		Properties props = new Properties();
        try {
        	props.load(pInStream );       //Properties 对象已生成，包括文件中的数据
        } catch (IOException e) {
    		Log.exception(e);
        }
      
        props.setProperty(key, value);
        try {
			props.store(new FileOutputStream(configFile), null);
		} catch (FileNotFoundException e) {
       		Log.exceptionDialog(e);
		} catch (IOException e) {
       		Log.exceptionDialog(e);
		}
        
        if(pInStream != null)
        {
			try {
				pInStream.close();
			} catch (IOException e) {
				Log.exceptionDialog(e);
			}
        }
	}
	
	private static String readProperty(String key)
	{
		File configFile = new File(configName);
		Properties prop = new Properties();
        try {
        	FileInputStream pInStream = new FileInputStream(configFile);
        	prop.load(pInStream);
        } catch (Exception e) {
        	Log.exception(e);
        }
		
        String strValue = prop.getProperty(key);

        return strValue;
	}
}
