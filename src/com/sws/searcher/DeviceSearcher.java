package com.sws.searcher;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class DeviceSearcher {
	public static int I_MAX_NET = 20;
	public static DeviceSearcherUDP sensorSearcherUDP[] = new DeviceSearcherUDP[20];
	public DeviceSearcher()
	{
		try {
		    Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		    InetAddress ip = null;
		    int iCarIndex = 0;
		    while (allNetInterfaces.hasMoreElements()) {
		        NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
		        if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint() || !netInterface.isUp()) {
		            continue;
		        } else {
		            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
		            System.out.println("找到网卡");
		            while (addresses.hasMoreElements()) {
		                ip = addresses.nextElement();
		                if (ip != null && ip instanceof Inet4Address) {
		                	String strIP = ip.getHostAddress();
		                	if(DeviceSearcherTool.isValidIP(strIP))
				        	{
					        	System.out.println("valid IP ADDRESS="+strIP);

					        	if(sensorSearcherUDP[iCarIndex] == null)
					        		sensorSearcherUDP[iCarIndex] = new DeviceSearcherUDP(ip, iCarIndex);
					        	
					        	sensorSearcherUDP[iCarIndex].initDatagramSocket();
					        	if(sensorSearcherUDP[iCarIndex].datagramSocket != null)
					        	{
						        	//启动监听的线程(会阻塞)
					        		Thread receiveThread = new Thread(sensorSearcherUDP[iCarIndex].receiveThread());//60000
					        		receiveThread.start();
					        		 
					        		//开始发送查询的数据包
					        		sensorSearcherUDP[iCarIndex].udpSend_JDQ008W();
					        	}
					        	
					        	break;	//一个网卡支持多个IP,为不重复搜索,跳出
				        	}
				        	else
				        	{
					        	System.out.println("inValid IP ADDRESS="+strIP);
				        	}
		                    System.out.println(strIP);
		                }
		            }
		            
		            iCarIndex++;
		        }
		    }
		} catch (Exception e) {
            System.out.println("IP地址获取失败");
		}
	}
}
