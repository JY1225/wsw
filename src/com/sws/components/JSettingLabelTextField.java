package com.sws.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.sws.base.Log;
import com.sws.base.NvramType;
import com.sws.base.SwsParameter;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.events.SWSEventManager;

public class JSettingLabelTextField
{
	int _sensorID;
	int _index;
	
	public boolean bDisplayOptionDialog = true;	//数值改变时,是否需要弹出确认框
	
	private String textFieldStr = "";
	
	private NvramType nvramType;

	public JLabel label;
	public JTextField textField;
	
	public JSettingLabelTextField(
		JPanel panel, 
		int gridX, int gridY, int labelWidth, int textFieldWidth,
		String labelText,boolean bInitTextFieldDisplay, boolean bSave,
		boolean bFill,NvramType nvram)
	{
		
		nvramType = nvram;
		nvramType.bSave = bSave;
		
		EventListener eventListener = new EventListener();
		SWSEventManager.addListener(eventListener);

		//==================================================================
		if(labelText != null)
		{
			label = new JLabel(labelText);
			GridBagConstraints gbcLabel = new GridBagConstraints();
			gbcLabel.gridwidth = labelWidth;
			gbcLabel.insets = new Insets(0, 5, 5, 5);
			gbcLabel.fill = GridBagConstraints.HORIZONTAL;
			gbcLabel.gridx = gridX;
			gbcLabel.gridy = gridY;
			panel.add(label, gbcLabel);
			gridX+=gbcLabel.gridwidth;
		}
		
		textField = new JTextField();
		GridBagConstraints gbcTextField = new GridBagConstraints();
		gbcTextField.gridwidth = textFieldWidth;
		gbcTextField.insets = new Insets(0, 0, 5, 5);
		if(bFill)
		{
			gbcTextField.fill = GridBagConstraints.HORIZONTAL;
		}
		else
		{
			gbcTextField.fill = GridBagConstraints.HORIZONTAL;
		}
		gbcTextField.gridx = gridX;
		gbcTextField.gridy = gridY;
		if(textFieldWidth>0)
		{
			panel.add(textField, gbcTextField);
		}
		
		if(bInitTextFieldDisplay)
		{
			_resetTextFieldText();
		}

		textField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				textField.setForeground(Color.RED);
			}
		});
		textField.addFocusListener(new FocusListener()
		{
			public void focusLost(FocusEvent event)
			{
				setNvramValue(true);
			}

			@Override
			public void focusGained(FocusEvent arg0)
			{
			}
		});	
	}

	public void setVisible(boolean bVisible)
	{
		label.setVisible(bVisible);
		textField.setVisible(bVisible);
	}
	
	public void setKeyNumOnly()
	{
		textField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				int keyChar = e.getKeyChar();

				if (nvramType.type == NvramType.Type.INTEGER_TYPE)
				{
					if (keyChar == KeyEvent.VK_MINUS || keyChar == KeyEvent.VK_PERIOD || (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9))
					{
						textField.setForeground(Color.RED);
					}
					else
					{
						e.consume(); //关键，屏蔽掉非法输入   
					}
				}
			}
		});
	}
	
	public void setPositiveKeyNumOnly()
	{
		textField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{				
				int keyChar = e.getKeyChar();

				if (nvramType.type == NvramType.Type.INTEGER_TYPE)
				{
					if (keyChar == KeyEvent.VK_PERIOD || (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9))
					{
						textField.setForeground(Color.RED);
					}
					else
					{
						e.consume(); //关键，屏蔽掉非法输入   
					}
				}
			}
		});
	}
	
	public void setEditable(boolean bEditable)
	{
		textField.setEditable(bEditable);
	}
	
	void _resetTextFieldText()
	{
		if (nvramType!= null &&nvramType.type == NvramType.Type.INTEGER_TYPE)
		{
			textField.setText( "" + nvramType.iValue);
		}
		else if (nvramType!= null &&nvramType.type == NvramType.Type.FLOAT_TYPE)
		{
			textField.setText("" + nvramType.fValue);
		}
		else if (nvramType!= null &&nvramType.type == NvramType.Type.DOUBLE_TYPE)
		{
			textField.setText("" + nvramType.dValue);
		}
		else if (nvramType!= null &&nvramType.type == NvramType.Type.STRING_TYPE)
		{
			textField.setText("" + nvramType.sValue);
		}
		
		setNvramValue(true);
		textFieldStr = textField.getText();
	}
	
	public String getTextFieldText()
	{
		return textField.getText();
	}
	
	public void setTextFieldText(String str)
	{
		textField.setText(str);

		//setNvramValue(false);
		
		_resetTextFieldText();
	}
	
	/**
	 * 只改变labe的值,不会改变NVRAM的值
	 * @param str
	 */
	public void setLabelText(String str)
	{
		label.setText(str+":");
	}
		
	public void setFont(Font font)
	{
		if(label != null)
			label.setFont(font);
		if(textField != null)
			textField.setFont(font);
	}
	
	public void setTextFieldFontColor(Color color)
	{
		if(textField != null)
			textField.setForeground(color);
	}
	
	void setNvramValue(boolean bLostFocuse)
	{
		textField.setForeground(Color.BLACK);

		String str = textField.getText().toString();
		
		if (
			nvramType != null &&
			(
				(str.equals("") && nvramType.type == NvramType.Type.STRING_TYPE)
				||!str.equals("")	
			)
			&& !str.equals(textFieldStr)
		)
		{
			textFieldStr = str;
			//==========================================================================
			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(SwsParameter.EXTRA_SETTING_NVRAM, nvramType.nvramStr);
			try
			{
				if (nvramType.type == NvramType.Type.INTEGER_TYPE)
				{
					int iValue = Integer.valueOf(str, 10);
					nvramType.iValue = iValue;
					eventExtra.put(SwsParameter.EXTRA_SETTING_VALUE, nvramType.iValue);
				}
				else if (nvramType.type == NvramType.Type.FLOAT_TYPE)
				{
					float fValue = Float.valueOf(str);
					nvramType.fValue = fValue;
					float fSaveValue = nvramType.fValue;
					eventExtra.put(SwsParameter.EXTRA_SETTING_VALUE, fSaveValue);
				}
				else if (nvramType.type == NvramType.Type.DOUBLE_TYPE)
				{
					double dValue = Double.valueOf(str);
					nvramType.dValue = dValue;
					double dSaveValue = nvramType.dValue;
					eventExtra.put(SwsParameter.EXTRA_SETTING_VALUE, dSaveValue);
				}
				else if (nvramType.type == NvramType.Type.STRING_TYPE)
				{
					nvramType.sValue = str;
					eventExtra.put(SwsParameter.EXTRA_SETTING_VALUE, nvramType.sValue);
				}
				
//				LMSLog.d(DEBUG_TAG,"UUUUUUUUUUUUUUUUUUUUUUUUUUU4="+nvramType.iValue);	
        	
				//eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_PROPERTY, _propertyType);
				//eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, _sensorID);
				eventExtra.put(SwsParameter.EXTRA_SETTING_SAVE, nvramType.bSave);
				eventExtra.put(SwsParameter.EXTRA_SETTING_EVENT_TYPE, nvramType.type);
				SwsParameter.swsEventManager.sendEvent(SwsParameter.SETTING_EVENT, eventExtra);				
			}
			catch (NumberFormatException e)
			{
				Log.exceptionDialog(e);
			}
		}
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
				
				if(nvramStr == null)
					return;
			}

			/*if (eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID);
				if (sensorID != _sensorID)
				{
					return;
				}
			}*/
				
			if(nvramStr != null && nvramType != null)
			{
				if (nvramStr.equals(nvramType.nvramStr))
				{
					_resetTextFieldText();
				}
			}
		}
	}
}
