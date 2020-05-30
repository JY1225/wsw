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
        	//�����˿�
        	if(datagramSocket == null)
        	{
        		datagramSocket = new DatagramSocket(60001, inetAddress);// ���������������ݱ������׽���  
        	}
        } catch (BindException e) { 
        	JOptionPane.showMessageDialog(null, 
				"����CMDָ��:netstat -ano|findstr \"60001\" "
				+ "\n���������ռ�õĽ���,��������������йر���Ӧ����", "����:�״������˿�60001��ռ��", JOptionPane.ERROR_MESSAGE); 
        } catch (IOException e) { 
			JOptionPane.showMessageDialog(null, "����:"+e, "����", JOptionPane.ERROR_MESSAGE); 
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
		
		//�ڼ�������
		com002Info.carIndex = carIndex;
		
		int num = 7;
		//�豸ID
		for(int j=0; j<12; j++,num++)
		{
			com002Info.deviceId[j] = data[num];
		}
		
		int macIndex = 17;
		//�豸MAC
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
				
		//�豸IP
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

		//�豸��
		int indexStart = strInfo.indexOf(ip)+ip.length()+1;
		com002Info.dname = strInfo.substring(indexStart, indexStart+(iLength_IP_DEVICENAME-(ip.length()+2)));
		System.out.println("�豸����="+com002Info.dname);

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
			SearchIpFrame.tableModel.addRow(rowValues);//���һ��
			System.out.println("����IP:"+inetAddress.getHostAddress());
			System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
		}
		

	}*/
	
	/*void parseCH9121_Config(String deviceModel, String deviceName, String receiveMac, byte data[])
	{
		int index = 62;
		
		int i = 0xFF&data[IP_START_INDEX];
		Com002Info com002Info = infoList.get(SearchIpFrame.row);;
		
		//�ڼ�������
		com002Info.carIndex = carIndex;
		
		int num = 7;
		//�豸ID
		for(int j=0; j<12; j++,num++)
		{
			com002Info.deviceId[j] = data[num];
		}
		
		int macIndex = 17;
		//�豸MAC
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
		
		System.out.println("�豸����="+deviceName);
		
		//��˫��
		com002Info.duplex = 0xFF&data[macIndex];
		macIndex++;
		
		//�豸��
		com002Info.dname = deviceName;
		
		//�豸IP
		com002Info.ip[0] = 0xFF&data[index];
		index++;
		com002Info.ip[1] = 0xFF&data[index];
		index++;
		com002Info.ip[2] = 0xFF&data[index];
		index++;
		com002Info.ip[3] = 0xFF&data[index];
		index++;
		
		//����
		com002Info.gate[0] = 0xFF&data[index];
		index++;
		com002Info.gate[1] = 0xFF&data[index];
		index++;
		com002Info.gate[2] = 0xFF&data[index];
		index++;
		com002Info.gate[3] = 0xFF&data[index];
		index++;

		//��������
		com002Info.sub[0] = 0xFF&data[index];
		index++;
		com002Info.sub[1] = 0xFF&data[index];
		index++;
		com002Info.sub[2] = 0xFF&data[index];
		index++;
		com002Info.sub[3] = 0xFF&data[index];
		index+=100;
		
		//�˿ں�
		com002Info.por = (((0xFF&data[index+1])<<8)|(0xFF&data[index]));
		index+=8;
		
		//���ڲ�����
		com002Info.bau = (((0xFF&data[index+2])<<16)|((0xFF&data[index+1])<<8)|(0xFF&data[index]));
		index+=4;
		
		System.out.println("��ȡ����λ="+data[index]);
		//����λ
		com002Info.dataBit = 0xFF&data[index];
		index++;

		System.out.println("��ȡֹͣλ="+data[index]);
		//ֹͣλ
		com002Info.stopBit = 0xFF&data[index];
		index++;
		
		//У��λ
		com002Info.checkDigit = 0xFF&data[index];
		index++;
		
		//===============================================================================================
		System.out.println("����IP:"+inetAddress.getHostAddress());
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
	}*/
	
	/*void parseYTL(String deviceModel, String deviceName, String receiveMac, byte data[], String ips)
	{
		Com002Info com002Info = new Com002Info();
		//�ڼ�������
		com002Info.carIndex = carIndex;
				
		String[] ipss = ips.split("\\.");
		
		//�豸IP
		com002Info.ip[0] = Integer.valueOf(ipss[0]);
		com002Info.ip[1] = Integer.valueOf(ipss[1]);
		com002Info.ip[2] = Integer.valueOf(ipss[2]);
		com002Info.ip[3] = Integer.valueOf(ipss[3]);
		
		//�˿ں�
		com002Info.por = ((0xFF&data[9])<<8) | (0XFF&data[10]);
		
		//�豸��
		com002Info.dname = deviceName;
		
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];

		System.out.println("����IP:"+inetAddress.getHostAddress());
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
	}
	
	void parseBigRadar(String deviceModel, String deviceName, String receiveMac, byte data[], String ips)
	{
		Com002Info com002Info = new Com002Info();
		//�ڼ�������
		com002Info.carIndex = carIndex;
				
		String[] ipss = ips.split("\\.");
				
		//�豸IP
		com002Info.ip[0] = Integer.valueOf(ipss[0]);
		com002Info.ip[1] = Integer.valueOf(ipss[1]);
		com002Info.ip[2] = Integer.valueOf(ipss[2]);
		com002Info.ip[3] = Integer.valueOf(ipss[3]);
		
		//�豸��
		com002Info.dname = deviceName;
		
		//�˿ں�
		com002Info.por = 2111;
		
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];

		System.out.println("����IP:"+inetAddress.getHostAddress());
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
		
	}
	
	void parseLidar(String deviceModel, String deviceName, String receiveMac, byte data[], String ips)
	{
		Com002Info com002Info = new Com002Info();
		//�ڼ�������
		com002Info.carIndex = carIndex;
				
		String[] ipss = ips.split("\\.");
				
		//�豸IP
		com002Info.ip[0] = Integer.valueOf(ipss[0]);
		com002Info.ip[1] = Integer.valueOf(ipss[1]);
		com002Info.ip[2] = Integer.valueOf(ipss[2]);
		com002Info.ip[3] = Integer.valueOf(ipss[3]);
		
		//�豸��
		com002Info.dname = deviceName;
		
		//�˿ں�
		com002Info.por = 2111;
		
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];

		System.out.println("����IP:"+inetAddress.getHostAddress());
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
		
	}
	
	void parseIMI5500(String deviceModel, String deviceName, String receiveMac, byte data[])
	{
		//�յ���ָ�7�ֽ�֡ͷ(IMI5500) + 10�ֽ��豸��(IMI_UT_921) + 4�ֽ��豸IP + 4�ֽ��������� + 4�ֽ����� + 2�ֽڶ˿ں� + 3�ֽڴ��ڲ����� + 2�ֽ�У���
		int index = IP_START_INDEX;
		
		int i = 0xFF&data[IP_START_INDEX];
		Com002Info com002Info = new Com002Info();
		
		//�ڼ�������
		com002Info.carIndex = carIndex;
		
		//�豸��
		com002Info.dname = deviceName;
		
		//�豸IP
		com002Info.ip[0] = 0xFF&data[index];
		index++;
		com002Info.ip[1] = 0xFF&data[index];
		index++;
		com002Info.ip[2] = 0xFF&data[index];
		index++;
		com002Info.ip[3] = 0xFF&data[index];
		index++;
		
		//��������
		com002Info.sub[0] = 0xFF&data[index];
		index++;
		com002Info.sub[1] = 0xFF&data[index];
		index++;
		com002Info.sub[2] = 0xFF&data[index];
		index++;
		com002Info.sub[3] = 0xFF&data[index];
		index++;
		
		//����
		com002Info.gate[0] = 0xFF&data[index];
		index++;
		com002Info.gate[1] = 0xFF&data[index];
		index++;
		com002Info.gate[2] = 0xFF&data[index];
		index++;
		com002Info.gate[3] = 0xFF&data[index];
		index++;
		
		//�˿ں�
		com002Info.por = (((0xFF&data[index])<<8)|(0xFF&data[index+1]));
		index+=2;
		
		//���ڲ�����
		com002Info.bau = (((0xFF&data[index])<<16)|((0xFF&data[index+1])<<8)|(0xFF&data[index+2]));
		index+=3;
		
		//===============================================================================================
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];
		String []rowValues = {deviceModel, deviceName, ip, "", receiveMac};
		SearchIpFrame.tableModel.addRow(rowValues);//���һ��

		System.out.println("����IP:"+inetAddress.getHostAddress());
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
	}
	
	void parseIMI5501(String deviceModel, String receiveMac, byte data[])
	{
		//�յ���ָ�7�ֽ�֡ͷ(IMI5501) + 12�ֽ�ΨһID + 10�ֽ��豸��(IMI_UT_921) + 4�ֽ��豸IP + 4�ֽ��������� + 4�ֽ����� + 2�ֽڶ˿ں� + 3�ֽڴ��ڲ����� + 1�ֽ�����λ + 1�ֽ�ֹͣλ + 1�ֽ�У��λ + 1�ֽ�Ӳ������ + 2�ֽ�У���
		int index = IP_START_INDEX+12;
		
		//int i = 0xFF&data[IP_START_INDEX+12];
		Com002Info com002Info = new Com002Info();
		
		//�ڼ�������
		com002Info.carIndex = carIndex;
		
		int num = 7;
		//�豸ID
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
		System.out.println("�豸����="+deviceName);
		
		//�豸��
		com002Info.dname = deviceName;
		
		//�豸IP
		com002Info.ip[0] = 0xFF&data[index];
		index++;
		com002Info.ip[1] = 0xFF&data[index];
		index++;
		com002Info.ip[2] = 0xFF&data[index];
		index++;
		com002Info.ip[3] = 0xFF&data[index];
		index++;
		
		//��������
		com002Info.sub[0] = 0xFF&data[index];
		index++;
		com002Info.sub[1] = 0xFF&data[index];
		index++;
		com002Info.sub[2] = 0xFF&data[index];
		index++;
		com002Info.sub[3] = 0xFF&data[index];
		index++;
		
		//����
		com002Info.gate[0] = 0xFF&data[index];
		index++;
		com002Info.gate[1] = 0xFF&data[index];
		index++;
		com002Info.gate[2] = 0xFF&data[index];
		index++;
		com002Info.gate[3] = 0xFF&data[index];
		index++;
		
		//�˿ں�
		com002Info.por = (((0xFF&data[index])<<8)|(0xFF&data[index+1]));
		index+=2;
		
		//���ڲ�����
		com002Info.bau = (((0xFF&data[index])<<16)|((0xFF&data[index+1])<<8)|(0xFF&data[index+2]));
		index+=3;
		
		System.out.println("��ȡ����λ="+data[index]);
		//����λ
		com002Info.dataBit = 0xFF&data[index];
		index++;
		
		System.out.println("��ȡֹͣλ="+data[index]);
		//ֹͣλ
		com002Info.stopBit = 0xFF&data[index];
		index++;
		
		//У��λ
		com002Info.checkDigit = 0xFF&data[index];
		if (com002Info.checkDigit == 0){
			com002Info.checkDigit = 4;
		}
		else if(com002Info.checkDigit == 2){
			com002Info.checkDigit = 0;
		}
		index++;
		
		//Ӳ������
		com002Info.hardwareFlow = 0xFF&data[index];
		index++;
		
		//===============================================================================================
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];
		String []rowValues = {deviceModel, deviceName, ip, "", receiveMac}; 
		SearchIpFrame.tableModel.addRow(rowValues);//���һ��
		
		System.out.println("����IP:"+inetAddress.getHostAddress());
		System.out.println("SENSOR IP:"+ip+" port:"+com002Info.por);
	}
	
	void parseIMI5502(String deviceModel, String receiveIP, byte data[])
	{
		//�յ���ָ�7�ֽ�֡ͷ(IMI5502) + 12�ֽ�ΨһID + 6�ֽ�MAC��ַ + 1�ֽڰ�˫�� + 10�ֽ��豸��(IMI_UT_921) + 4�ֽ��豸IP + 4�ֽ��������� + 4�ֽ����� + 2�ֽڶ˿ں� + 3�ֽڴ��ڲ����� + 1�ֽ�����λ + 1�ֽ�ֹͣλ + 1�ֽ�У��λ + 1�ֽ�Ӳ������ + 2�ֽ�У���
		int index = IP_START_INDEX+12+6+1;
		
		//int i = 0xFF&data[IP_START_INDEX+12];
		Com002Info com002Info = new Com002Info();
		
		//�ڼ�������
		com002Info.carIndex = carIndex;
		
		int num = 7;
		//�豸ID
		for(int j=0; j<12; j++,num++)
		{
			com002Info.deviceId[j] = data[num];
		}
		
		int macIndex = 19;
		//�豸MAC
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
		
		//��˫��
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
		System.out.println("�豸����="+deviceName);
		
		//�豸��
		com002Info.dname = deviceName;
		
		//�豸IP
		com002Info.ip[0] = 0xFF&data[index];
		index++;
		com002Info.ip[1] = 0xFF&data[index];
		index++;
		com002Info.ip[2] = 0xFF&data[index];
		index++;
		com002Info.ip[3] = 0xFF&data[index];
		index++;
		
		//��������
		com002Info.sub[0] = 0xFF&data[index];
		index++;
		com002Info.sub[1] = 0xFF&data[index];
		index++;
		com002Info.sub[2] = 0xFF&data[index];
		index++;
		com002Info.sub[3] = 0xFF&data[index];
		index++;
		
		//����
		com002Info.gate[0] = 0xFF&data[index];
		index++;
		com002Info.gate[1] = 0xFF&data[index];
		index++;
		com002Info.gate[2] = 0xFF&data[index];
		index++;
		com002Info.gate[3] = 0xFF&data[index];
		index++;
		
		//�˿ں�
		com002Info.por = (((0xFF&data[index])<<8)|(0xFF&data[index+1]));
		index+=2;
		
		//���ڲ�����
		com002Info.bau = (((0xFF&data[index])<<16)|((0xFF&data[index+1])<<8)|(0xFF&data[index+2]));
		index+=3;
		
		System.out.println("��ȡ����λ="+data[index]);
		//����λ
		com002Info.dataBit = 0xFF&data[index];
		index++;
		
		System.out.println("��ȡֹͣλ="+data[index]);
		//ֹͣλ
		com002Info.stopBit = 0xFF&data[index];
		index++;
		
		//У��λ
		com002Info.checkDigit = 0xFF&data[index];
		if (com002Info.checkDigit == 0){
			com002Info.checkDigit = 4;
		}
		else if(com002Info.checkDigit == 2){
			com002Info.checkDigit = 0;
		}
		index++;
		
		//Ӳ������
		com002Info.hardwareFlow = 0xFF&data[index];
		index++;
		
		//===============================================================================================
		infoList.add(com002Info);
		
		String ip = com002Info.ip[0]+"."+com002Info.ip[1]+"."+com002Info.ip[2]+"."+com002Info.ip[3];
		String mac = getHexString(com002Info.mac[0])+"."+getHexString(com002Info.mac[1])+"."+getHexString(com002Info.mac[2])+"."+getHexString(com002Info.mac[3])+"."+getHexString(com002Info.mac[4])+"."+getHexString(com002Info.mac[5]);
		String []rowValues = {deviceModel, deviceName, ip, mac, receiveIP}; 
		SearchIpFrame.tableModel.addRow(rowValues);//���һ��

		SearchIpFrame.macList.add(mac);
		//�ж�MAC��ַ�Ƿ��ͻ
		if(SearchIpFrame.macList.size() > 1)//��ʾ����������
		{
			Set<String> s = new HashSet<String>();
			for(String macStr : SearchIpFrame.macList)
			{
				boolean bRepeat = s.add(macStr);
				if(!bRepeat)
				{
					MyJOptionpane.showErrorMessage("������ʾ", "���棺MAC��ַ��ͻ");
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
			            byte[] data =new byte[1024];//�����ֽ����飬ָ�����յ����ݰ��Ĵ�С

			            if(datagramSocket != null)
			            {
			            	DatagramPacket datagramPacketReceive = new DatagramPacket(data, data.length);  
			            	datagramSocket.receive(datagramPacketReceive);//�˷����ڽ��յ����ݱ�֮ǰ��һֱ����
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
			    		System.out.println("�豸IP="+deviceIP);
			    		
						String deviceMac = "00-"+byteToHex(data[2])+"-"+byteToHex(data[3])+"-"+byteToHex(data[4])+"-"+byteToHex(data[5])+"-"+byteToHex(data[6]);
						System.out.println("�豸mac="+deviceMac);
						
						//����IP
						String localIP = inetAddress.getHostAddress();
						System.out.println("����IP="+localIP);
						
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
	            InetAddress.getByName("255.255.255.255"), 60000);  //�Է��˿�
        			 
	        datagramSocket.send(datagramPacketSend);  // �������ݱ���������������Ϊ length �İ����͵�ָ�������ϵ�ָ���˿ں�  
        } catch (IOException e) {  
        	//LMSLog.exception(e);
        	e.printStackTrace();
        }
    }
} 