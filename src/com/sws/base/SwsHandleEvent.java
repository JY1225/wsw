package com.sws.base;

import java.util.HashMap;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;

public class SwsHandleEvent {

	public SwsHandleEvent() 
	{
		EventListener eventListener = new EventListener();
		SwsParameter.swsEventManager.addListener(eventListener);
	}
	
	private class EventListener implements SWSEventListener
	{
		public void lmsTransferEvent(SWSEvent event)
		{
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();
			String nvramStr = null;
			
			int sensorID = 0;

			if (SwsParameter.SETTING_EVENT.equals(eventType))
			{
				nvramStr = (String) eventExtra.get(SwsParameter.EXTRA_SETTING_NVRAM);
				//System.out.println("nvramStr="+nvramStr);
				if(nvramStr == null)
					return;
				boolean bSave = (boolean) eventExtra.get(SwsParameter.EXTRA_SETTING_SAVE);
				if(bSave)
				{
					String value = "";
				
					Enum nvramType = (Enum) eventExtra.get(SwsParameter.EXTRA_SETTING_EVENT_TYPE);
					if(nvramType == NvramType.Type.STRING_TYPE)
					{
						value = (String) eventExtra.get(SwsParameter.EXTRA_SETTING_VALUE);
					}
					else if(nvramType == NvramType.Type.INTEGER_TYPE)
					{
						int iValue = (int) eventExtra.get(SwsParameter.EXTRA_SETTING_VALUE);
						value = String.valueOf(iValue);
					}
					else if(nvramType == NvramType.Type.FLOAT_TYPE)
					{
						float fValue = (float) eventExtra.get(SwsParameter.EXTRA_SETTING_VALUE);
						value = String.valueOf(fValue);
					}
					else if(nvramType == NvramType.Type.BOOLEAN_TYPE)
					{
						boolean bValue = (boolean) eventExtra.get(SwsParameter.EXTRA_SETTING_VALUE);
						value = String.valueOf(bValue);
					}
					//存入文件中
					Configure.saveProperty(nvramStr, value);
				}
			}
		}
	}
}
