package com.sws.entity;

public class Team {

	private int teamId;
	private String teamCode;
	private String teamName;
	private String teamAlias;
	private String teamClass;
	private String produceUnit;
	private String modifyTime;
	
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamAlias() {
		return teamAlias;
	}
	public void setTeamAlias(String teamAlias) {
		this.teamAlias = teamAlias;
	}
	public String getTeamClass() {
		return teamClass;
	}
	public void setTeamClass(String teamClass) {
		this.teamClass = teamClass;
	}
	public String getProduceUnit() {
		return produceUnit;
	}
	public void setProduceUnit(String produceUnit) {
		this.produceUnit = produceUnit;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Override
	public String toString() {
		return "Team [teamId=" + teamId + ", teamCode=" + teamCode + ", teamName=" + teamName + ", teamAlias="
				+ teamAlias + ", teamClass=" + teamClass + ", produceUnit=" + produceUnit + ", modifyTime=" + modifyTime
				+ "]";
	}
	
}
