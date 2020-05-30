package com.sws.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.sws.base.NvramType;
import com.sws.base.SwsParameter;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.events.SWSEventManager;

public class JButtonBoolean extends JButton{
	
	String _labelTrueStrID;
	String _labelFalseStrID;
	String _labelStrTrue;
	String _labelStrFalse;
	
	Color _backGroundColorTrue;
	Color _backGroundColorFalse;
	
	NvramType nvramType;
	
	public JButtonBoolean(
		JPanel panel,
		int gridX,int gridY,int width,
		String labelTrueStrID, 
		String labelFalseStrID, 
		boolean bSave,boolean bFill,
		NvramType nvram)
	{
		nvramType = nvram;
		nvramType.bSave = bSave;
		_labelStrTrue = labelTrueStrID;
		_labelStrFalse = labelFalseStrID;
		//===========================================================
		setText(false);
		
		//==================================================================
		EventListener eventListener = new EventListener();
		SWSEventManager.addListener(eventListener);

		//==================================================================
		GridBagConstraints gbc = new GridBagConstraints();
		if(bFill)
		{
			gbc.fill = GridBagConstraints.BOTH;
		}
		else
		{
			gbc.fill = GridBagConstraints.HORIZONTAL;
		}
		//gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 5, 5);
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		gbc.gridwidth = width;
		panel.add(this, gbc);

		addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
	        	nvramType.bValue = !nvramType.bValue;
	        	setNvram();					
			}
		});
	}
	
	public void resetTextBackGroundColor(Color colorTrue,Color colorFalse)
	{
		_backGroundColorTrue = colorTrue;
		_backGroundColorFalse = colorFalse;
		setText(false);
	}
	
	public void setText(boolean bSetNvram)
	{
		if(nvramType.bValue == true)
		{
			setText(_labelStrTrue);		
			if(_backGroundColorTrue != null)
			{
				setForeground(_backGroundColorTrue);
			}
		}
		else
		{
			setText(_labelStrFalse);			    								
			if(_backGroundColorFalse != null)
			{
				setForeground(_backGroundColorFalse);
			}
		}
		
		if(bSetNvram)
		{
			setNvram();
		}
	}	
	
	private void setNvram()
	{
		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(SwsParameter.EXTRA_SETTING_NVRAM, nvramType.nvramStr);
		eventExtra.put(SwsParameter.EXTRA_SETTING_VALUE, nvramType.bValue);
		eventExtra.put(SwsParameter.EXTRA_SETTING_SAVE, nvramType.bSave);
		eventExtra.put(SwsParameter.EXTRA_SETTING_EVENT_TYPE, nvramType.type);
		//eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, _sensorID);
		//eventExtra.put(LMSConstValue.INTENT_EXTRA_OWNER_STRING, sOwnerString);
	    SwsParameter.swsEventManager.sendEvent(SwsParameter.SETTING_EVENT,eventExtra);
	}
	
	private class EventListener implements SWSEventListener {
		public void lmsTransferEvent(SWSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			String nvramStr = null;
			int sensorID = 0;

			if (SwsParameter.SETTING_EVENT.equals(eventType))
			{
				nvramStr = (String) eventExtra.get(SwsParameter.EXTRA_SETTING_NVRAM);
				
				if(nvramStr == null)
					return;
				
				if(nvramStr != null && nvramType != null)
				{				
					if(nvramStr.equals(nvramType.nvramStr))
					{
						setText(false);
					}
		        }
			}
			
			/*if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
				if(_sensorID >=0 && sensorID != _sensorID)
				{
					return;
				}
			}*/

			
		}
	}
}
