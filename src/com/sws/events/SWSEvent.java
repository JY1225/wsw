package com.sws.events;

import java.util.EventObject;
import java.util.HashMap;

public class SWSEvent extends EventObject
{
	private static final long serialVersionUID = 1L;
	
	private String eventType = "";
	private HashMap eventExtra = new HashMap();

	public SWSEvent(Object source,String eventType) {
		super(source);
		this.eventType = eventType;
	}

	public String getEventType() {
        return this.eventType;
    }
	
	public void putEventExtra(HashMap eventExtra) {
		this.eventExtra = eventExtra;
    }
	
	public HashMap getEventExtra() {
		return eventExtra;
    }

	public int getEventIntExtra(String extraType)
	{
		return Integer.valueOf(eventExtra.get(extraType).toString());
	}

	public boolean getEventBooleanExtra(String extraType)
	{
		return Boolean.valueOf(eventExtra.get(extraType).toString());
	}

	public String getEventStringExtra(String extraType)
	{
		return (String) eventExtra.get(extraType);
	}
}
