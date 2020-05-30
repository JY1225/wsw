package com.sws.entity;

public class Record {

	private int id;
	private String recordNumber;//��¼���
	private String deviceNumber;//�豸���
	private String deviceName;//�豸����
	private Material material;//������Ϣ
	private String materalState;//����״̬����������ɢ֧
	private String operator;  //����Ա
	private Team team;        //������Ϣ
	private String remark;    //��ע��Ϣ
	private String recordState; //��¼״̬�����ϣ�ɾ�����޸�
	private String weighingTime; //����ʱ��
	private String codeListSerialNumber;//�뵥���
	private String produceDate; //��������
	private String weigher; //����Ա
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRecordNumber() {
		return recordNumber;
	}
	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRecordState() {
		return recordState;
	}
	public void setRecordState(String recordState) {
		this.recordState = recordState;
	}
	public String getWeighingTime() {
		return weighingTime;
	}
	public void setWeighingTime(String weighingTime) {
		this.weighingTime = weighingTime;
	}
	public String getCodeListSerialNumber() {
		return codeListSerialNumber;
	}
	public void setCodeListSerialNumber(String codeListSerialNumber) {
		this.codeListSerialNumber = codeListSerialNumber;
	}
	public String getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}
	public String getWeigher() {
		return weigher;
	}
	public void setWeigher(String weigher) {
		this.weigher = weigher;
	}
	public String getMateralState() {
		return materalState;
	}
	public void setMateralState(String materalState) {
		this.materalState = materalState;
	}
	
}
