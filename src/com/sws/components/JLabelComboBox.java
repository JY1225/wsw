package com.sws.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sws.base.EnumType;
import com.sws.base.NvramType;
import com.sws.base.SwsParameter;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.events.SWSEventManager;

public class JLabelComboBox{	
	private String DEBUG_TAG="JLabelComboBox";

	int _sensorID;
	int _index;

	private String labelText;
	
	NvramType nvramType;
	EnumType enumType;
	
	public JComboBox comboBox;
	
	public JLabelComboBox(
		JPanel panel,
		int gridX,int gridY,
		int labelWidth,
		int comboBoxWidth,
		String labelStr,
		boolean bSave,
		NvramType nvram, EnumType enumValue
	)
	{    	
		nvramType = nvram;
		nvramType.bSave = bSave;
		enumType = enumValue;
		labelText = labelStr;
		
		//==================================================================
		EventListener eventListener = new EventListener();
		SWSEventManager.addListener(eventListener);

		//==============================================================================
		if(labelStr != null)
		{
			JLabel label = new JLabel(labelStr);
	    	GridBagConstraints gbc_label = new GridBagConstraints();
	    	//gbc_label.fill = GridBagConstraints.EAST;
	    	gbc_label.fill = GridBagConstraints.BOTH;
	    	gbc_label.insets = new Insets(0, 5, 5, 0);
	    	gbc_label.gridx = gridX;
	    	gbc_label.gridy = gridY;
	    	gbc_label.gridwidth = labelWidth;
	    	panel.add(label, gbc_label);
	    	gridX+=gbc_label.gridwidth;
		}
    	
    	comboBox = new JComboBox();
    	GridBagConstraints gbc_comboBox = new GridBagConstraints();
    	gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboBox.insets = new Insets(0, 0, 5, 0);
    	gbc_comboBox.gridx = gridX;
    	gbc_comboBox.gridy = gridY;
    	gbc_comboBox.gridwidth = comboBoxWidth;
    	panel.add(comboBox, gbc_comboBox);
    	gridX++;
    	
    	//=================================================================================================
    	resetItem(); 
	}
	
	public void autoSetSelectedItem(String strItem)
	{
    	ArrayList<String> enumString = enumType.getValueArray();
    	for(int i=0;i<enumString.size();i++)
    	{
			if(strItem.equals(enumType.getKeyFromIndex(i)))
			{
		    	comboBox.setSelectedIndex(i);		
		    	break;
			}
    	}
	}
	
	int lastSelectedIndex = -1;
	ItemListener itemListener = new ItemListener() {  
        @Override  
        public void itemStateChanged(ItemEvent e) {  
            // TODO Auto-generated method stub  
            String str = e.getItem().toString(); 
            int stateChange = e.getStateChange();
            
            if (stateChange == ItemEvent.SELECTED) {  
				int selectedIndex = comboBox.getSelectedIndex();
				if(lastSelectedIndex != selectedIndex)
				{
		        	enumType.key = enumType.getKeyFromIndex(selectedIndex);
					nvramType.sValue = enumType.key;

					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
					eventExtra.put(SwsParameter.EXTRA_SETTING_NVRAM, nvramType.nvramStr);
					eventExtra.put(SwsParameter.EXTRA_SETTING_VALUE, enumType.key);
					eventExtra.put(SwsParameter.EXTRA_SETTING_SAVE, nvramType.bSave);
					eventExtra.put(SwsParameter.EXTRA_SETTING_EVENT_TYPE, nvramType.type);
					//eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, _sensorID);
					SwsParameter.swsEventManager.sendEvent(SwsParameter.SETTING_EVENT,eventExtra);    
					
					lastSelectedIndex = selectedIndex;
				}  					
            }else if (stateChange == ItemEvent.DESELECTED) {  

            }else {  

            }  
        }  
    };
	
	void resetItem()
	{
		comboBox.removeItemListener(itemListener);
		comboBox.removeAllItems();
				
		int selectedItemIndex = 0;
    	ArrayList<String> enumString = enumType.getValueArray();
    	for(int i=0;i<enumString.size();i++)
    	{
    		String str= enumString.get(i);
    		comboBox.addItem(str);
			if(enumType.key.equals(enumType.getKeyFromIndex(i)))
			{
				selectedItemIndex = i;
			}
    	}
    	
    	//=================================================================================================
    	comboBox.addItemListener(itemListener); 
    	comboBox.setSelectedIndex(selectedItemIndex);
	}

	/*void setEnableSenorType(int sensorID)
	{
		if(sensorID >= 0) 
		{
			if(nvramType.equals(LMSConstValue.nvramEmbededTransmission))
			{
				if(
					!LMSConstValue.getSensorType(_sensorID).equals(LMSConstValue.SensorType.EMBEDED_UPLOADER)
					&&!LMSConstValue.getSensorType(_sensorID).equals(LMSConstValue.SensorType.EMBEDED_SETTLER)
					&&!LMSConstValue.getSensorType(_sensorID).equals(LMSConstValue.SensorType.UNKNOW)
				)
				{
					comboBox.setEnabled(true);	
										
					autoSetSelectedItem(LMSConstValue.enumEmbededTransmission[sensorID].TRANSMISSION_DIRECT);						
				}
				else
				{
					comboBox.setEnabled(false);
				}
			}
		}
	}*/
	
	private class EventListener implements SWSEventListener {
		public void lmsTransferEvent(SWSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();
			String nvram = null;
			
			int sensorID = 0;

			if (SwsParameter.SETTING_EVENT.equals(eventType))
			{
				nvram = (String) eventExtra.get(SwsParameter.EXTRA_SETTING_NVRAM);
				
				if(nvram == null)
					return;
			}
			
			if(nvramType != null && nvram != null)
			{
				

				/*if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
				{
					sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
					if(sensorID != _sensorID)
					{
						return;
					}
				}*/
	
				if(nvram.equals(nvramType))
				{
					autoSetSelectedItem(enumType.key);		    	
				}
				
				/*if(nvram.equals(LMSConstValue.eNvramSensorType.nvramStr))
				{
					setEnableSenorType(sensorID);					
				}	*/
	        }
		}
	}
}
