package com.sws.device;

import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

import com.sws.base.SwsParameter;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;

public abstract class DeviceEventManager
{
	public abstract void lmsTransferEventSensor(SWSEvent event);
	public abstract void dataReceive(byte [] bytes);
	public EventListener eventListener;
	public OutputStream _outputStream;
	
	public DeviceEventManager()
	{
		eventListener = new EventListener();
        SwsParameter.swsEventManager.addListener(eventListener);
	}
	
	public class EventListener implements SWSEventListener {

		@Override
		public void lmsTransferEvent(SWSEvent event) {
			// TODO Auto-generated method stub
			lmsTransferEvent(event);
		}
	}
}
