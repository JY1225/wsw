package com.sws.searcher;

import javax.swing.*;

import com.sws.base.SwsParameter;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class DeviceSearcherUDP {  
	
	private final static String DEBUG_TAG="SensorSearcherUDP";
	
	InetAddress inetAddress;
	public DatagramSocket datagramSocket,datagramSocket2;
	public static String strCH9121 = "CH9121";
	int carIndex;
	public DeviceSearcherUDP(InetAddress _inetAddress, int _carIndex)
	{
    	inetAddress = _inetAddress;
    	carIndex = _carIndex;
	}
	
	public void initDatagramSocket()
	{
        try {  
        	//本机端口
        	if(datagramSocket == null)
        	{
        		datagramSocket = new DatagramSocket(60001, inetAddress);// 创建用来发送数据报包的套接字  
        	}
        } catch (BindException e) { 
        	JOptionPane.showMessageDialog(null, 
				"请用CMD指令:netstat -ano|findstr \"60001\" "
				+ "\n搜索到相关占用的进程,并在任务管理器中关闭相应进程", "错误:雷达搜索端口60001被占用", JOptionPane.ERROR_MESSAGE); 
        } catch (IOException e) { 
			JOptionPane.showMessageDialog(null, "错误:"+e, "错误", JOptionPane.ERROR_MESSAGE); 
        }
	}

	int IP_START_INDEX = 17;
	
	public static List<DeviceInfo> infoList = new ArrayList<DeviceInfo>(); 
	
	private void parseJDQ008W(String deviceIP, String deviceMac, String localIP)
	{
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setIndex(carIndex);
		deviceInfo.setDeviceIP(deviceIP);
		deviceInfo.setDeviceMac(deviceMac);
		deviceInfo.setLocalIP(localIP);
		infoList.add(deviceInfo);
	}
	/*//CH9121
	void parseCH9121_Serach(String deviceModel, String deviceName, String receiveMac, String strInfo, byte data[])
	{		
		Com002Info com002Info = new Com002Info();
		
		//第几张网卡
		com002Info.carIndex = carIndex;
		
		int num = 7;
		//设备ID
		for(int j=0; j<12; j++,num++)
		{
			com002Info.deviceId[j] = data[num];
		}
		
		int macIndex = 17;
		//设备MAC
		com002Info.mac[0] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[1] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[2] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[3] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[4] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[5] = 0xFF&data[macIndex];
		macIndex++;
				
		//设备IP
		final int I_DATA_BEGIN = 29;
		int iLength_IP_DEVICENAME = data[29];
		int indexIPEnd = 0;
		for(int i=I_DATA_BEGIN+1;i<I_DATA_BEGIN+iLength_IP_DEVICENAME;i++)
		{
			if(data[i]==0x00)
			{
				indexIPEnd = i;
				break;
			}
		}
		int iFoundNum = 0;
		String strIP = "";
		for(int i=I_DATA_BEGIN+1;i<indexIPEnd;i++)
		{
			if(data[i]==0x2E)
			{
				com002Info.ip[iFoundNum] = Integer.valueOf(strIP);
				System.out.println("found!");	
				
				strIP = "";
				iFoundNum++;
			}
			else
			{
				strIP+=(data[i]-48);
//				System.out.println("strIP="+strIP);
			}
		}
		com002Info.ip[iFoundNum] = Integer.valueOf(strIP);
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];

		//设备名
		int indexStart = strInfo.indexOf(ip)+ip.length()+1;
		com002Info.dname = strInfo.substring(indexStart, indexStart+(iLength_IP_DEVICENAME-(ip.length()+2)));
		System.out.println("设备名称="+com002Info.dname);

		//===============================================================================================
        infoList.add(com002Info);
		
		String mac = getHexString(com002Info.mac[0])+"."+getHexString(com002Info.mac[1])+"."+getHexString(com002Info.mac[2])+"."+getHexString(com002Info.mac[3])+"."+getHexString(com002Info.mac[4])+"."+getHexString(com002Info.mac[5]);
		boolean addFlag = true;
		for(int i=0;i<SearchIpFrame.ip_mac_list.size();i++)
		{
			if(SearchIpFrame.ip_mac_list.get(i).equals(ip+mac))
			{
				addFlag = false;
				break;
			}
		}
		SearchIpFrame.ip_mac_list.add(ip+mac);
		String []rowValues = {deviceModel, deviceName, ip, mac, receiveMac};

		if(addFlag)
		{
			SearchIpFrame.tableModel.addRow(rowValues);//添加一行
			System.out.println("本机IP:"+inetAddress.getHostAddress());
			System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
		}
		

	}*/
	
	/*void parseCH9121_Config(String deviceModel, String deviceName, String receiveMac, byte data[])
	{
		int index = 62;
		
		int i = 0xFF&data[IP_START_INDEX];
		Com002Info com002Info = infoList.get(SearchIpFrame.row);;
		
		//第几张网卡
		com002Info.carIndex = carIndex;
		
		int num = 7;
		//设备ID
		for(int j=0; j<12; j++,num++)
		{
			com002Info.deviceId[j] = data[num];
		}
		
		int macIndex = 17;
		//设备MAC
		com002Info.mac[0] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[1] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[2] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[3] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[4] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[5] = 0xFF&data[macIndex];
		macIndex++;
		
		System.out.println("设备名称="+deviceName);
		
		//半双工
		com002Info.duplex = 0xFF&data[macIndex];
		macIndex++;
		
		//设备名
		com002Info.dname = deviceName;
		
		//设备IP
		com002Info.ip[0] = 0xFF&data[index];
		index++;
		com002Info.ip[1] = 0xFF&data[index];
		index++;
		com002Info.ip[2] = 0xFF&data[index];
		index++;
		com002Info.ip[3] = 0xFF&data[index];
		index++;
		
		//网关
		com002Info.gate[0] = 0xFF&data[index];
		index++;
		com002Info.gate[1] = 0xFF&data[index];
		index++;
		com002Info.gate[2] = 0xFF&data[index];
		index++;
		com002Info.gate[3] = 0xFF&data[index];
		index++;

		//子网掩码
		com002Info.sub[0] = 0xFF&data[index];
		index++;
		com002Info.sub[1] = 0xFF&data[index];
		index++;
		com002Info.sub[2] = 0xFF&data[index];
		index++;
		com002Info.sub[3] = 0xFF&data[index];
		index+=100;
		
		//端口号
		com002Info.por = (((0xFF&data[index+1])<<8)|(0xFF&data[index]));
		index+=8;
		
		//串口波特率
		com002Info.bau = (((0xFF&data[index+2])<<16)|((0xFF&data[index+1])<<8)|(0xFF&data[index]));
		index+=4;
		
		System.out.println("获取数据位="+data[index]);
		//数据位
		com002Info.dataBit = 0xFF&data[index];
		index++;

		System.out.println("获取停止位="+data[index]);
		//停止位
		com002Info.stopBit = 0xFF&data[index];
		index++;
		
		//校验位
		com002Info.checkDigit = 0xFF&data[index];
		index++;
		
		//===============================================================================================
		System.out.println("本机IP:"+inetAddress.getHostAddress());
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
	}*/
	
	/*void parseYTL(String deviceModel, String deviceName, String receiveMac, byte data[], String ips)
	{
		Com002Info com002Info = new Com002Info();
		//第几张网卡
		com002Info.carIndex = carIndex;
				
		String[] ipss = ips.split("\\.");
		
		//设备IP
		com002Info.ip[0] = Integer.valueOf(ipss[0]);
		com002Info.ip[1] = Integer.valueOf(ipss[1]);
		com002Info.ip[2] = Integer.valueOf(ipss[2]);
		com002Info.ip[3] = Integer.valueOf(ipss[3]);
		
		//端口号
		com002Info.por = ((0xFF&data[9])<<8) | (0XFF&data[10]);
		
		//设备名
		com002Info.dname = deviceName;
		
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];

		System.out.println("本机IP:"+inetAddress.getHostAddress());
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
	}
	
	void parseBigRadar(String deviceModel, String deviceName, String receiveMac, byte data[], String ips)
	{
		Com002Info com002Info = new Com002Info();
		//第几张网卡
		com002Info.carIndex = carIndex;
				
		String[] ipss = ips.split("\\.");
				
		//设备IP
		com002Info.ip[0] = Integer.valueOf(ipss[0]);
		com002Info.ip[1] = Integer.valueOf(ipss[1]);
		com002Info.ip[2] = Integer.valueOf(ipss[2]);
		com002Info.ip[3] = Integer.valueOf(ipss[3]);
		
		//设备名
		com002Info.dname = deviceName;
		
		//端口号
		com002Info.por = 2111;
		
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];

		System.out.println("本机IP:"+inetAddress.getHostAddress());
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
		
	}
	
	void parseLidar(String deviceModel, String deviceName, String receiveMac, byte data[], String ips)
	{
		Com002Info com002Info = new Com002Info();
		//第几张网卡
		com002Info.carIndex = carIndex;
				
		String[] ipss = ips.split("\\.");
				
		//设备IP
		com002Info.ip[0] = Integer.valueOf(ipss[0]);
		com002Info.ip[1] = Integer.valueOf(ipss[1]);
		com002Info.ip[2] = Integer.valueOf(ipss[2]);
		com002Info.ip[3] = Integer.valueOf(ipss[3]);
		
		//设备名
		com002Info.dname = deviceName;
		
		//端口号
		com002Info.por = 2111;
		
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];

		System.out.println("本机IP:"+inetAddress.getHostAddress());
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
		
	}
	
	void parseIMI5500(String deviceModel, String deviceName, String receiveMac, byte data[])
	{
		//收到的指令：7字节帧头(IMI5500) + 10字节设备名(IMI_UT_921) + 4字节设备IP + 4字节子网掩码 + 4字节网关 + 2字节端口号 + 3字节串口波特率 + 2字节校验和
		int index = IP_START_INDEX;
		
		int i = 0xFF&data[IP_START_INDEX];
		Com002Info com002Info = new Com002Info();
		
		//第几张网卡
		com002Info.carIndex = carIndex;
		
		//设备名
		com002Info.dname = deviceName;
		
		//设备IP
		com002Info.ip[0] = 0xFF&data[index];
		index++;
		com002Info.ip[1] = 0xFF&data[index];
		index++;
		com002Info.ip[2] = 0xFF&data[index];
		index++;
		com002Info.ip[3] = 0xFF&data[index];
		index++;
		
		//子网掩码
		com002Info.sub[0] = 0xFF&data[index];
		index++;
		com002Info.sub[1] = 0xFF&data[index];
		index++;
		com002Info.sub[2] = 0xFF&data[index];
		index++;
		com002Info.sub[3] = 0xFF&data[index];
		index++;
		
		//网关
		com002Info.gate[0] = 0xFF&data[index];
		index++;
		com002Info.gate[1] = 0xFF&data[index];
		index++;
		com002Info.gate[2] = 0xFF&data[index];
		index++;
		com002Info.gate[3] = 0xFF&data[index];
		index++;
		
		//端口号
		com002Info.por = (((0xFF&data[index])<<8)|(0xFF&data[index+1]));
		index+=2;
		
		//串口波特率
		com002Info.bau = (((0xFF&data[index])<<16)|((0xFF&data[index+1])<<8)|(0xFF&data[index+2]));
		index+=3;
		
		//===============================================================================================
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];
		String []rowValues = {deviceModel, deviceName, ip, "", receiveMac};
		SearchIpFrame.tableModel.addRow(rowValues);//添加一行

		System.out.println("本机IP:"+inetAddress.getHostAddress());
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
	}
	
	void parseIMI5501(String deviceModel, String receiveMac, byte data[])
	{
		//收到的指令：7字节帧头(IMI5501) + 12字节唯一ID + 10字节设备名(IMI_UT_921) + 4字节设备IP + 4字节子网掩码 + 4字节网关 + 2字节端口号 + 3字节串口波特率 + 1字节数据位 + 1字节停止位 + 1字节校验位 + 1字节硬件流控 + 2字节校验和
		int index = IP_START_INDEX+12;
		
		//int i = 0xFF&data[IP_START_INDEX+12];
		Com002Info com002Info = new Com002Info();
		
		//第几张网卡
		com002Info.carIndex = carIndex;
		
		int num = 7;
		//设备ID
		for(int j=0; j< 12; j++,num++)
		{
			com002Info.deviceId[j] = data[num];
			System.out.println("ID"+j+" = "+data[num]);
		}
		
		byte[] nameByte = new byte[10];

		for(int j=0; j<10; j++,num++)
		{
			nameByte[j] = data[num];
		}

		String deviceName = new String(nameByte);
		System.out.println("设备名称="+deviceName);
		
		//设备名
		com002Info.dname = deviceName;
		
		//设备IP
		com002Info.ip[0] = 0xFF&data[index];
		index++;
		com002Info.ip[1] = 0xFF&data[index];
		index++;
		com002Info.ip[2] = 0xFF&data[index];
		index++;
		com002Info.ip[3] = 0xFF&data[index];
		index++;
		
		//子网掩码
		com002Info.sub[0] = 0xFF&data[index];
		index++;
		com002Info.sub[1] = 0xFF&data[index];
		index++;
		com002Info.sub[2] = 0xFF&data[index];
		index++;
		com002Info.sub[3] = 0xFF&data[index];
		index++;
		
		//网关
		com002Info.gate[0] = 0xFF&data[index];
		index++;
		com002Info.gate[1] = 0xFF&data[index];
		index++;
		com002Info.gate[2] = 0xFF&data[index];
		index++;
		com002Info.gate[3] = 0xFF&data[index];
		index++;
		
		//端口号
		com002Info.por = (((0xFF&data[index])<<8)|(0xFF&data[index+1]));
		index+=2;
		
		//串口波特率
		com002Info.bau = (((0xFF&data[index])<<16)|((0xFF&data[index+1])<<8)|(0xFF&data[index+2]));
		index+=3;
		
		System.out.println("获取数据位="+data[index]);
		//数据位
		com002Info.dataBit = 0xFF&data[index];
		index++;
		
		System.out.println("获取停止位="+data[index]);
		//停止位
		com002Info.stopBit = 0xFF&data[index];
		index++;
		
		//校验位
		com002Info.checkDigit = 0xFF&data[index];
		if (com002Info.checkDigit == 0){
			com002Info.checkDigit = 4;
		}
		else if(com002Info.checkDigit == 2){
			com002Info.checkDigit = 0;
		}
		index++;
		
		//硬件流控
		com002Info.hardwareFlow = 0xFF&data[index];
		index++;
		
		//===============================================================================================
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];
		String []rowValues = {deviceModel, deviceName, ip, "", receiveMac}; 
		SearchIpFrame.tableModel.addRow(rowValues);//添加一行
		
		System.out.println("本机IP:"+inetAddress.getHostAddress());
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
	}
	
	void parseIMI5502(String deviceModel, String receiveIP, byte data[])
	{
		//收到的指令：7字节帧头(IMI5502) + 12字节唯一ID + 6字节MAC地址 + 1字节半双工 + 10字节设备名(IMI_UT_921) + 4字节设备IP + 4字节子网掩码 + 4字节网关 + 2字节端口号 + 3字节串口波特率 + 1字节数据位 + 1字节停止位 + 1字节校验位 + 1字节硬件流控 + 2字节校验和
		int index = IP_START_INDEX+12+6+1;
		
		//int i = 0xFF&data[IP_START_INDEX+12];
		Com002Info com002Info = new Com002Info();
		
		//第几张网卡
		com002Info.carIndex = carIndex;
		
		int num = 7;
		//设备ID
		for(int j=0; j<12; j++,num++)
		{
			com002Info.deviceId[j] = data[num];
		}
		
		int macIndex = 19;
		//设备MAC
		com002Info.mac[0] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[1] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[2] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[3] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[4] = 0xFF&data[macIndex];
		macIndex++;
		com002Info.mac[5] = 0xFF&data[macIndex];
		macIndex++;
		
		//半双工
		com002Info.duplex = 0xFF&data[macIndex];
		macIndex++;
		
		byte[] nameByte = new byte[10];
		String outStr = "";
		for(int j=0; j<10; j++,macIndex++)
		{
			nameByte[j] = data[macIndex];
			String hex = Integer.toHexString(nameByte[j] & 0xFF); 
			if (hex.length() == 1) {        
				hex = '0' + hex;      
			}      
			outStr+=hex.toUpperCase();
		}
		System.out.println("name="+outStr);
		String deviceName = new String(nameByte);
		System.out.println("设备名称="+deviceName);
		
		//设备名
		com002Info.dname = deviceName;
		
		//设备IP
		com002Info.ip[0] = 0xFF&data[index];
		index++;
		com002Info.ip[1] = 0xFF&data[index];
		index++;
		com002Info.ip[2] = 0xFF&data[index];
		index++;
		com002Info.ip[3] = 0xFF&data[index];
		index++;
		
		//子网掩码
		com002Info.sub[0] = 0xFF&data[index];
		index++;
		com002Info.sub[1] = 0xFF&data[index];
		index++;
		com002Info.sub[2] = 0xFF&data[index];
		index++;
		com002Info.sub[3] = 0xFF&data[index];
		index++;
		
		//网关
		com002Info.gate[0] = 0xFF&data[index];
		index++;
		com002Info.gate[1] = 0xFF&data[index];
		index++;
		com002Info.gate[2] = 0xFF&data[index];
		index++;
		com002Info.gate[3] = 0xFF&data[index];
		index++;
		
		//端口号
		com002Info.por = (((0xFF&data[index])<<8)|(0xFF&data[index+1]));
		index+=2;
		
		//串口波特率
		com002Info.bau = (((0xFF&data[index])<<16)|((0xFF&data[index+1])<<8)|(0xFF&data[index+2]));
		index+=3;
		
		System.out.println("获取数据位="+data[index]);
		//数据位
		com002Info.dataBit = 0xFF&data[index];
		index++;
		
		System.out.println("获取停止位="+data[index]);
		//停止位
		com002Info.stopBit = 0xFF&data[index];
		index++;
		
		//校验位
		com002Info.checkDigit = 0xFF&data[index];
		if (com002Info.checkDigit == 0){
			com002Info.checkDigit = 4;
		}
		else if(com002Info.checkDigit == 2){
			com002Info.checkDigit = 0;
		}
		index++;
		
		//硬件流控
		com002Info.hardwareFlow = 0xFF&data[index];
		index++;
		
		//===============================================================================================
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];
		String mac = getHexString(com002Info.mac[0])+"."+getHexString(com002Info.mac[1])+"."+getHexString(com002Info.mac[2])+"."+getHexString(com002Info.mac[3])+"."+getHexString(com002Info.mac[4])+"."+getHexString(com002Info.mac[5]);
		String []rowValues = {deviceModel, deviceName, ip, mac, receiveIP}; 
		SearchIpFrame.tableModel.addRow(rowValues);//添加一行

		SearchIpFrame.macList.add(mac);
		//判断MAC地址是否冲突
		if(SearchIpFrame.macList.size() > 1)//表示有两条数据
		{
			Set<String> s = new HashSet<String>();
			for(String macStr : SearchIpFrame.macList)
			{
				boolean bRepeat = s.add(macStr);
				if(!bRepeat)
				{
					MyJOptionpane.showErrorMessage("警告提示", "警告：MAC地址冲突");
					break;
				}
			}
		}
	}*/
	
	public Runnable receiveThread()
    {    		
		return new Runnable(){    		
       		@Override
    		public void run() {
		        try {  
		        	while(true)
		        	{
			            byte[] data =new byte[1024];//创建字节数组，指定接收的数据包的大小

			            if(datagramSocket != null)
			            {
			            	DatagramPacket datagramPacketReceive = new DatagramPacket(data, data.length);  
			            	datagramSocket.receive(datagramPacketReceive);//此方法在接收到数据报之前会一直阻塞
			            	new Thread(udpReceiveThread(datagramPacketReceive, data)).start();
			            }
		        	}
		    		
		        } catch (IOException e) {  
		        	//LMSLog.exception(e);
		        	e.printStackTrace();
		        }  
       		}
		};
    }
	
    public Runnable udpReceiveThread(final DatagramPacket packet, final byte[] data)
    {    		
		return new Runnable(){    		
       		@Override
    		public void run() {
		        try {  
			        InetAddress inetAddressReceive = packet.getAddress();
		    		
		    		String hexString = byteToHexString(data, packet.getLength());
					System.out.println("byte receive:"+hexString);
					
					if(data[0] == (byte)0x00 && data[1] == (byte)0x01)
			    	{
						String deviceIP = inetAddressReceive.getHostAddress();
			    		System.out.println("设备IP="+deviceIP);
			    		
						String deviceMac = "00-"+byteToHex(data[2])+"-"+byteToHex(data[3])+"-"+byteToHex(data[4])+"-"+byteToHex(data[5])+"-"+byteToHex(data[6]);
						System.out.println("设备mac="+deviceMac);
						
						//本地IP
						String localIP = inetAddress.getHostAddress();
						System.out.println("本地IP="+localIP);
						
						parseJDQ008W(deviceIP, deviceMac, localIP);
						
						SwsParameter.swsEventManager.sendEvent(SwsParameter.DEVICE_SEARCH_RESULT_EVENT);
			    	}
		    		
		        } catch (Exception e) {  
		        	e.printStackTrace();
		        }  
       		}
		};
    }
 
    public byte[] hexStringToByte(String hex) { 
    	if (hex == null || hex.equals("")) { 
    		return null;
    	} 

        int len = (hex.length() / 2);   
        byte[] result = new byte[len];   
        char[] achar = hex.toUpperCase().toCharArray();   
        for (int i = 0; i < len; i++) {   
         int pos = i * 2;   
         result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
        }   
        return result;   
    }  
      
    private byte toByte(char c) {   
        byte b = (byte) "0123456789ABCDEF".indexOf(c);   
        return b;   
    } 
    
    private final static String[] hexArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
   
    public String byteToHex(int n) {
        if (n < 0) {
            n = n + 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return (hexArray[d1] + hexArray[d2]).toUpperCase();
    }
    
    public String byteToHexString(byte[] data, int len) { 
    	String outStr = "";
		for (int i = 0; i < len; i++) {
			String hex = Integer.toHexString(data[i] & 0xFF); 
			if (hex.length() == 1) {        
				hex = '0' + hex;      
			}      
			outStr+=hex.toUpperCase();
		}
		
		return outStr;
    }  
    
    public void udpSend_JDQ008W()
    { 		  
    	String str = "00";
    	
        try {  
	        DatagramPacket datagramPacketSend = new DatagramPacket(
	    		hexStringToByte(str),  
	    		hexStringToByte(str).length,  
	            InetAddress.getByName("255.255.255.255"), 60000);  //对方端口
        			 
	        datagramSocket.send(datagramPacketSend);  // 构造数据报包，用来将长度为 length 的包发送到指定主机上的指定端口号  
        } catch (IOException e) {  
        	//LMSLog.exception(e);
        	e.printStackTrace();
        }
    }
} 