package com.sws.device;

import java.util.HashMap;
import com.sws.base.SwsParameter;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;

public class SocketClientBase extends Thread implements SWSEventListener
{
	private String DEBUG_TAG="PhysicLayerSocket";

   	public Object tokenSocket = new Object();
	public SocketClientBase()
	{
		SwsParameter.swsEventManager.addListener(this);
	}
	
    @Override
	public void run() 
    {
		while(true)
		{
			try
			{
				if(SwsParameter.enumControllDeviceType.key.equals(SwsParameter.enumControllDeviceType.CONTROLL_KXHL))
				{
					new Device_JDQ008W(SwsParameter.sNvramControllIP.sValue, SwsParameter.iNvramControllPort.iValue);
					synchronized(tokenSocket)
    				{
						tokenSocket.wait();
    				}
				}
				else
				{
					synchronized(tokenSocket)
        			{
        				tokenSocket.wait();	
        			}
				}
				
				Thread.sleep(200);
				
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
    
	@Override
	public void lmsTransferEvent(SWSEvent event) {
		// TODO Auto-generated method stub
		String eventType = event.getEventType();
		HashMap eventExtra = event.getEventExtra();
		
		if(SwsParameter.SETTING_EVENT.endsWith(eventType))
		{
			String nvramStr = (String) eventExtra.get(SwsParameter.EXTRA_SETTING_NVRAM);
			if(nvramStr != null)
			{
				if(nvramStr.equals(SwsParameter.eNvramControllDeviceType.nvramStr)
				  ||nvramStr.equals(SwsParameter.sNvramControllIP.nvramStr)
				  ||nvramStr.equals(SwsParameter.iNvramControllPort.nvramStr))
				{
					synchronized(tokenSocket)
    				{
						tokenSocket.notify();
    				}
				}
			}
		}
	}
}