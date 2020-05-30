package com.sws.entity;

public class SwsLog {
    private String logEvent;
    private String logContent;
    private String logOperator;
    private String deviceIP;
    private String logTime;

    public String getLogEvent() {
        return logEvent;
    }

    public void setLogEvent(String logEvent) {
        this.logEvent = logEvent;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getLogOperator() {
        return logOperator;
    }

    public void setLogOperator(String logOperator) {
        this.logOperator = logOperator;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    @Override
    public String toString() {
        return "SwsLog{" +
                "logEvent='" + logEvent + '\'' +
                ", logContent='" + logContent + '\'' +
                ", logOperator='" + logOperator + '\'' +
                ", deviceIP='" + deviceIP + '\'' +
                ", logTime='" + logTime + '\'' +
                '}';
    }
}
