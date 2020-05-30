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
					System.out.println("连接标志bConnectOK="+bConnectOK);
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
	 * 打开串口
	 * @param portName
	 * @param baudrate
	 * @return
	 */
	public boolean openPort(String portName,int baudrate){
		
		try {
			List<String> portList = findPort();//查询到所有有用的串口端口
			for(String port: portList)
			{
				if(port.equals(portName))
				{
					//通过端口名识别端口
					CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
					
					//打开端口，并给端口名字和一个timeout（打开操作的超时时间）
					CommPort commPort = portIdentifier.open(portName, 2000);
					
					//判断是不是串口
					if(commPort instanceof SerialPort){
						serialPort = (SerialPort) commPort;
						//设置一下串口的波特率等参数
						serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
						
						serialPort.addEventListener(this);
						serialPort.notifyOnDataAvailable(true);
						
						//sendPortConnected(nvramType, true);
						SwsParameter.sendDeviceConnected(SwsParameter.WEIGHT_CONNECTION_EVENT, true);
						
						startRead();
		
						return true;
					}else{
						//Log.warningDialog("串口设置失败提示", "该端口不是串口");
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
			//Log.exceptionDialog("打开串口失败", e);
			SwsParameter.sendDeviceConnected(SwsParameter.WEIGHT_CONNECTION_EVENT, false);
		} 
		catch (PortInUseException e) 
		{
			Log.exception(e);
			//Log.errorDialog("打开串口失败提示", "该串口正在使用中");
			SwsParameter.sendDeviceConnected(SwsParameter.WEIGHT_CONNECTION_EVENT, false);
		} 
		catch (UnsupportedCommOperationException e) 
		{
			Log.exception(e);
			//Log.errorDialog("打开串口失败提示","设置串口参数失败！打开串口操作未完成！");
			SwsParameter.sendDeviceConnected(SwsParameter.WEIGHT_CONNECTION_EVENT, false);
		}
		catch (TooManyListenersException e)
		{
			Log.exception(e);
		}
		
		return false;
	}
	
	/**
	 * 关闭串口
	 * @param serialPort 待关闭的串口对象
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
	 * 查找所有可用端口
	 * @return portNameList
	 */
	public static final List<String> findPort(){
		//获得当前所有可用串口
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		
		ArrayList<String> portNameList = new ArrayList<String>();
		
		//将可用串口名称添加到List并返回该List
		while(portList.hasMoreElements()){
			String portName = portList.nextElement().getName();
			portNameList.add(portName);
		}
		
		return portNameList;
	}
	
	/**
	 * 往串口发送数据
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
			//Log.exceptionDialog("串口发送数据错误提示", e);
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
	 * 获取串口数据
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
		case SerialPortEvent.BI:/*通讯中断*/
		case SerialPortEvent.OE:/*溢位错误*/
		case SerialPortEvent.FE:/*传帧错误*/
		case SerialPortEvent.PE:/*校验错误*/
		case SerialPortEvent.CD:/*载波检测*/
		case SerialPortEvent.CTS:/*清除发送*/
		case SerialPortEvent.DSR:/*数据设备就绪*/
		case SerialPortEvent.RI:/*响铃指示*/
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:/*输出缓冲区清空*/
			break;
		case SerialPortEvent.DATA_AVAILABLE:/*端口有可用数据*/
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
