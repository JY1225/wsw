package com.sws.entity;

public class Record {

	private int id;
	private String recordNumber;//记录编号
	private String deviceNumber;//设备编号
	private String deviceName;//设备名称
	private Material material;//物料信息
	private String materalState;//物料状态，整件还是散支
	private String operator;  //操作员
	private Team team;        //班组信息
	private String remark;    //备注信息
	private String recordState; //记录状态，作废，删除，修改
	private String weighingTime; //称重时间
	private String codeListSerialNumber;//码单序号
	private String produceDate; //生产日期
	private String weigher; //称重员
	
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
