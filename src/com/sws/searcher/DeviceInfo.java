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
	public int checkDigit;//У��λ
	public int dataBit;   //����λ
	public int stopBit;   //ֹͣλ 
	public int hardwareFlow; //Ӳ������
	public byte[] deviceId = new byte[12];   //�豸ΨһID
	public int mac[] = new int[6]; //�豸MAC
	public int duplex;   //��˫��
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
