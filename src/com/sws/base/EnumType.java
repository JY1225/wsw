package com.sws.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class EnumType 
{
	public String key;
	public LinkedHashMap<String , String> map = new LinkedHashMap<String , String>();    

	public String getValueFromIndex(int index)
	{	
		int i = 0;
		Iterator<String> it = map.keySet().iterator();  
		while(it.hasNext()) {  
			String _key = (String)it.next();  

			if(i==index)
			{
				return map.get(_key);
			}
			
			i++;
		}  
		
		return null;
	}
	
	public String getKeyFromIndex(int index)
	{	
		int i = 0;
		Iterator<String> it = map.keySet().iterator();  
		while(it.hasNext()) { 
			String _key = (String)it.next();  

			if(i==index)
			{
				return _key;  
			}
			
			i++;
		}  
		
		return null;
	}
	
	public String getValueFromKey(String inKey)
	{
		Iterator<String> it = map.keySet().iterator();  
		while(it.hasNext()) {  
			String _key = (String)it.next();  
			if(_key.equals(inKey))
				return map.get(_key);
		}  
		
		return null;
	}

	public ArrayList<String> getValueArray()
	{
		ArrayList<String> array=new ArrayList<String>();
		
		Iterator<String> it = map.keySet().iterator();  
		while(it.hasNext()) {  
			String key = (String)it.next();  
			array.add(map.get(key));
		}  
		 
		return array;
	}

	public ArrayList<String> getKeyArray()
	{
		ArrayList<String> array=new ArrayList<String>();
		
		Iterator<String> it = map.keySet().iterator();  
		while(it.hasNext()) {  
			String key = (String)it.next();  
			array.add(key);
		}  
		 
		return array;
	}
	
	public LinkedHashMap<String , String> getMap()
	{		 
		return map;
	}
}
