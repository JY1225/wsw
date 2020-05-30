package com.sws.device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import com.sws.base.Log;
import com.sws.base.SwsParameter;
import com.sws.database.SwsSaveDBUtils;
import com.sws.entity.Record;
import com.sws.events.SWSEvent;
import com.sws.events.SWSEventListener;
import com.sws.utils.CommonUtils;

public class Device_JDQ008W implements Runnable, SWSEventListener{

	private String deviceIP;
	private int devicePort;
	private Socket socket = null;

	private BufferedReader bufferedReader;
	private InputStreamReader inputStreamReader;
	protected static OutputStream outputStream;
	protected static InputStream inputStream;
	private boolean bConnect = false;
	private boolean bStopThread = false;
	
	public Device_JDQ008W(String ip, int port)
	{
		deviceIP = ip;
		devicePort = port;
		SwsParameter.swsEventManager.addListener(this);
		SwsParameter.sendDeviceConnected(SwsParameter.CONTROLL_CONNECTION_EVENT, false);
		connectSocket();
		new Thread(this).start();
	}

	@Override
	public void run() 
	{
		while (!bStopThread) 
		{
			try
			{
				if(isServerClose(socket))
				{
					//System.out.println("连接成功......");
					byte[] buffer = new byte[1024 * 2];
					int count = 0;
					
					count = inputStream.available();
					if (count == 0)
					{									
						Thread.sleep(100);
					}
					else
					{	
						inputStream.read(buffer);
						
						String str = CommonUtils.BinaryToHexString(buffer, count);
						
						System.out.println("接收数据="+str);
						
						if(str != null && str.contains("EEFFC00100010DCF9E"))
						{
							Record record = new Record();
							
							SwsSaveDBUtils.addRecord(record);
//							byte[] sendBuffer = new byte[10];
//							sendBuffer[0] = (byte)0xCC;
//							sendBuffer[1] = (byte)0xDD;
//							sendBuffer[2] = (byte)0xA1;
//							sendBuffer[3] = (byte)0x01;
//							sendBuffer[4] = (byte)0xFF;
//							sendBuffer[5] = (byte)0xFF;
//							sendBuffer[6] = (byte)0xFF;
//							sendBuffer[7] = (byte)0xFF;
//							sendBuffer[8] = (byte)0x9E;
//							sendBuffer[9] = (byte)0x3C;
//							
//							System.out.println("发送="+CommonUtils.BinaryToHexString(sendBuffer, sendBuffer.length));
//							Thread.sleep(1000);//延时1s再发送，不然指令不起效
//							socketSend(sendBuffer);
						}
					}
				}
				else
				{
					SwsParameter.sendDeviceConnected(SwsParameter.CONTROLL_CONNECTION_EVENT, false);
					disconnectSocket();
					connectSocket();
				}
				
				Thread.sleep(1000);
			}
			catch (SocketException e)
			{
				//LMSLog.exceptionDialog("socket客户端错误", e);		
				//e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				//LMSLog.exceptionDialog("异常", e);
				//e.printStackTrace();
			}
			catch (IOException e)
			{
				//LMSLog.exceptionDialog(DEBUG_TAG, e);
				//e.printStackTrace();
			}		
		}
	}
	
	private boolean connectSocket()
	{
		try
		{			
			socket = new Socket();
			socket.setReceiveBufferSize(8 * 1024);//必须在连接前设置
			SocketAddress remoteAddr = new InetSocketAddress(deviceIP, devicePort);
			socket.connect(remoteAddr, 1000);//连接超时时间  
			outputStream = socket.getOutputStream();  
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()),200*1024);
			inputStream = socket.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			
			SwsParameter.sendDeviceConnected(SwsParameter.CONTROLL_CONNECTION_EVENT, true);
			//System.out.println("连接成功");
			return true;
		}
		catch (SocketException e)
		{
			e.printStackTrace();
			Log.exception(e);
		}
		catch (IOException e)
		{	
			e.printStackTrace();
			Log.exception(e);
		}

		return false;
	}
	
	public Boolean isServerClose(Socket socket)
	{
		try
		{
			if(socket != null)
			{
				socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信 
				//System.out.println("socket 连接正常！！");
			}
			
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			//System.out.println("socket 连接断开！");
			disconnectSocket();
			return false;
		}
	}
	
	public void disconnectSocket()
	{
		try 
		{
        	if(socket != null && !socket.isClosed())
        	{
        		socket.shutdownInput();	//正常跳出read 阻塞
        		socket.shutdownOutput();	
        		socket.close();
        		socket = null;
        		
	        	if(outputStream != null)
	        	{
	        		outputStream.close();
	        		outputStream = null;
	        	}
	        	if(bufferedReader != null)
	        		bufferedReader.close();
        	}
		} 
		catch (IOException e) 
		{
			Log.exception(e);
		}					
	}
	
	public void socketSend(String str)
	{
		try
		{
			if (outputStream != null)
			{
				outputStream.write((str).getBytes("utf-8"));
				outputStream.flush();
			}
		}
		catch (IOException e)
		{
			Log.exception(e);
		}
	}
	
	public void socketSend(byte[] buffer)
	{
		try
		{
			if (outputStream != null)
			{
				outputStream.write(buffer);
				outputStream.flush();
			}
		}
		catch (IOException e)
		{
			Log.exception(e);
		}
	}

	@Override
	public void lmsTransferEvent(SWSEvent event) 
	{
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
					disconnectSocket();
					bStopThread = true;
					if(SwsParameter.enumControllDeviceType.key.equals(SwsParameter.enumControllDeviceType.DEVICE_NOT))
					{
						SwsParameter.sendDeviceConnected(SwsParameter.CONTROLL_CONNECTION_EVENT, false);
					}
					SwsParameter.swsEventManager.removeListener(this);
				}
				else if(nvramStr.equals(SwsParameter.sNvramControllSendMessage.nvramStr))
				{
					String sendMessage = SwsParameter.sNvramControllSendMessage.sValue;
					System.out.println("sendMessage="+sendMessage);
					byte[] buffer = CommonUtils.hexStringToBytes(sendMessage);
					socketSend(buffer);
				}
			}
		}
	}
}
