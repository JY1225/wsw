package com.sws.searcher;

public class DeviceInfo {
	
	private int index;
	private String deviceName;
	private String deviceIP;
	private String deviceMac;
	private String localIP;
	/*public int gate[] = new int[4];
	public int por;
	public int bau;
	public int checkDigit;//校验位
	public int dataBit;   //数据位
	public int stopBit;   //停止位 
	public int hardwareFlow; //硬件流控
	public byte[] deviceId = new byte[12];   //设备唯一ID
	public int mac[] = new int[6]; //设备MAC
	public int duplex;   //半双工
*/
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceIP() {
		return deviceIP;
	}
	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	public String getLocalIP() {
		return localIP;
	}
	public void setLocalIP(String localIP) {
		this.localIP = localIP;
	}	
	
}
