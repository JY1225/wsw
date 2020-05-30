package com.sws.device;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.TooManyListenersException;

import com.sws.base.Log;
import com.sws.base.SwsParameter;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.utils.CommonUtils;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class SerialPortBase extends Thread implements SerialPortEventListener, SWSEventListener{

	protected CommPortIdentifier commPort;  
	public SerialPort serialPort;  
	public Object tokenSerialPort = new Object();
	public static Object tokenRXTX = new Object();
	SerialPortBase tollStationSerialPort;
	public byte[] receiveBytes;
	
	private String portName;
	private int baudRate;
	private DeviceEventManager eventManager;
	
	//TollStationPhysicLayer physicLayer;
	
	public SerialPortBase(String _portName, int _baudRate)
	{
		portName = _portName;
		baudRate = _baudRate;
		
        SwsParameter.swsEventManager.addListener(this);
	}
	
	@Override
	public void run() {
		
		while(true)
		{
			try {
				boolean bConnectOK = false;
				if(SwsParameter.enumWeightDeviceType.key.equals(SwsParameter.enumWeightDeviceType.DEVICE_NOT))
				{
					SwsParameter.sendDeviceConnected(SwsParameter.WEIGHT_CONNECTION_EVENT, false);
					synchronized (tokenSerialPort) 
					{
						tokenSerialPort.wait();
					}
				}
				else if(CommonUtils.isValidCOM(portName))
				{
					
					bConnectOK = openPort(portName, baudRate);
					System.out.println("���ӱ�־bConnectOK="+bConnectOK);
					if(bConnectOK)
            		{
            			synchronized(tokenSerialPort)
            			{
            				tokenSerialPort.wait();	
            			}
            		}
					else
					{
						Thread.sleep(1000);
					}
				}
				
				Thread.sleep(200);
			} 
			catch (Exception e) 
			{
				Log.exception(e);
			}
		}
		
	}
	
	/**
	 * �򿪴���
	 * @param portName
	 * @param baudrate
	 * @return
	 */
	public boolean openPort(String portName,int baudrate){
		
		try {
			List<String> portList = findPort();//��ѯ���������õĴ��ڶ˿�
			for(String port: portList)
			{
				if(port.equals(portName))
				{
					//ͨ���˿���ʶ��˿�
					CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
					
					//�򿪶˿ڣ������˿����ֺ�һ��timeout���򿪲����ĳ�ʱʱ�䣩
					CommPort commPort = portIdentifier.open(portName, 2000);
					
					//�ж��ǲ��Ǵ���
					if(commPort instanceof SerialPort){
						serialPort = (SerialPort) commPort;
						//����һ�´��ڵĲ����ʵȲ���
						serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
						
						serialPort.addEventListener(this);
						serialPort.notifyOnDataAvailable(true);
						
						//sendPortConnected(nvramType, true);
						SwsParameter.sendDeviceConnected(SwsParameter.WEIGHT_CONNECTION_EVENT, true);
						
						startRead();
		
						return true;
					}else{
						//Log.warningDialog("��������ʧ����ʾ", "�ö˿ڲ��Ǵ���");
						//sendPortConnected(nvramType, false);
						SwsParameter.sendDeviceConnected(SwsParameter.WEIGHT_CONNECTION_EVENT, false);
					}
					break;
				}
			}
			
			SwsParameter.sendDeviceConnected(SwsParameter.WEIGHT_CONNECTION_EVENT, false);
			
		} 
		catch (NoSuchPortException e) 
		{
			Log.exception(e);
			//Log.exceptionDialog("�򿪴���ʧ��", e);
			SwsParameter.sendDeviceConnected(SwsParameter.WEIGHT_CONNECTION_EVENT, false);
		} 
		catch (PortInUseException e) 
		{
			Log.exception(e);
			//Log.errorDialog("�򿪴���ʧ����ʾ", "�ô�������ʹ����");
			SwsParameter.sendDeviceConnected(SwsParameter.WEIGHT_CONNECTION_EVENT, false);
		} 
		catch (UnsupportedCommOperationException e) 
		{
			Log.exception(e);
			//Log.errorDialog("�򿪴���ʧ����ʾ","���ô��ڲ���ʧ�ܣ��򿪴��ڲ���δ��ɣ�");
			SwsParameter.sendDeviceConnected(SwsParameter.WEIGHT_CONNECTION_EVENT, false);
		}
		catch (TooManyListenersException e)
		{
			Log.exception(e);
		}
		
		return false;
	}
	
	/**
	 * �رմ���
	 * @param serialPort ���رյĴ��ڶ���
	 */
	public void closePort(SerialPort serialPort){
		if(serialPort != null){
			serialPort.removeEventListener();
			serialPort.close();
			serialPort = null;
		}
		
		SwsParameter.swsEventManager.removeListener(eventManager.eventListener);
		if(eventManager!=null)
		{
    		try{
				if (eventManager._outputStream != null)
				{
					eventManager._outputStream.close();
					eventManager._outputStream = null;
				}
				
				SwsParameter.swsEventManager.removeListener(eventManager.eventListener);
    		} catch (IOException e) {
        		Log.exception(e);
    		}
		}
		eventManager = null;
	}
	
	/**
	 * �������п��ö˿�
	 * @return portNameList
	 */
	public static final List<String> findPort(){
		//��õ�ǰ���п��ô���
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		
		ArrayList<String> portNameList = new ArrayList<String>();
		
		//�����ô���������ӵ�List�����ظ�List
		while(portList.hasMoreElements()){
			String portName = portList.nextElement().getName();
			portNameList.add(portName);
		}
		
		return portNameList;
	}
	
	/**
	 * �����ڷ�������
	 * @param serialPort
	 * @param bytes
	 */
	public void writeFromPort(byte[] bytes){
		OutputStream os = null;
		
		try {
			os = serialPort.getOutputStream();
			os.write(bytes);
			os.flush();
		} catch (Exception e) {
			Log.exception(e);
			//Log.exceptionDialog("���ڷ������ݴ�����ʾ", e);
		} finally {
			try {
				if(os != null){
					os.close();
					os = null;
				}
			} catch (IOException e) {
				Log.exception(e);
				//Log.exceptionDialog(DEBUG_TAG, e);
			}
		}
	}
	
	/**
	 * ��ȡ��������
	 * @param serialPort
	 * @return
	 * @throws IOException 
	 */
	public byte[] readFromPort(){
		InputStream in = null;
		int count = 0;
		byte[] bs = null;
		
		try {
			in = serialPort.getInputStream();
			
			int bufflength = in.available();
			if(bufflength == 0){
				Thread.sleep(30);
				bufflength = in.available();
			}
			
			while(bufflength != 0){
				bs = new byte[bufflength];
				in.read(bs);
				bufflength = in.available();
			}
		} catch (IOException e) {
			Log.exceptionDialog(e);
		} catch (InterruptedException e) {
			Log.exception(e);
		} finally {
			try {
				if(in != null){
					in.close();
					in = null;
				}
			} catch (IOException e) {
				Log.exception(e);
			}
		}
		
		return bs;
		
	}
	
	public void startRead()
	{
		if(serialPort != null)
    	{
			if(SwsParameter.enumWeightDeviceType.key.equals(SwsParameter.enumWeightDeviceType.WEIGHT_ZZWZ))
			{
				//physicLayer = new TollStation_ZG(portName, baudRate);
				//physicLayer = new TollStation_ZG();
				eventManager = new Device_DZDC09P();
			}
			
			if(eventManager != null)
			{
        		try
				{
        			eventManager._outputStream = serialPort.getOutputStream();
				}
				catch (IOException e)
				{
					Log.exception(e);
				}  
			}
			//tollStationSerialPort.serialPort = serialPort;
			//tollStationSerialPort.receiveBytes = receiveBytes;
    	}
	}

	@Override
	public void serialEvent(SerialPortEvent event) {

		switch (event.getEventType()) {
		case SerialPortEvent.BI:/*ͨѶ�ж�*/
		case SerialPortEvent.OE:/*��λ����*/
		case SerialPortEvent.FE:/*��֡����*/
		case SerialPortEvent.PE:/*У�����*/
		case SerialPortEvent.CD:/*�ز����*/
		case SerialPortEvent.CTS:/*�������*/
		case SerialPortEvent.DSR:/*�����豸����*/
		case SerialPortEvent.RI:/*����ָʾ*/
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:/*������������*/
			break;
		case SerialPortEvent.DATA_AVAILABLE:/*�˿��п�������*/
			receiveBytes = readFromPort();
			eventManager.dataReceive(receiveBytes);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void lmsTransferEvent(SWSEvent event) {
		// TODO Auto-generated method stub
		String eventType = event.getEventType();
		HashMap eventExtra = event.getEventExtra();

		int sensorID = 0;
	
        if(SwsParameter.SETTING_EVENT.equals(eventType)) 
        {	    	
			String nvram = (String) eventExtra.get(SwsParameter.EXTRA_SETTING_NVRAM); 

			if(
	        	(nvram.equals(SwsParameter.eNvramWeightDeviceType.nvramStr)
	        	||nvram.equals(SwsParameter.sNvramWeightSerialPort.nvramStr)
	        	||nvram.equals(SwsParameter.eNvramEnumProtocolBaudrate.nvramStr))
        	)
        	{
	            closePort(serialPort);
	            
	            portName = SwsParameter.sNvramWeightSerialPort.sValue;
	            baudRate = Integer.parseInt(SwsParameter.enumProtocolBaudrateType.getValueFromKey(SwsParameter.enumProtocolBaudrateType.key));
		        	
				synchronized(tokenSerialPort)
				{
					tokenSerialPort.notify();
				}
        	}
        }    	 
	}

}
