package com.cmri.bpt.service.ue.bo;

import java.io.Serializable;

public class AppStatus  implements Serializable{

	private Integer id;

	private String mode;

	private String cell;

	private double rxlevel;

	private String task;
	
	private String cmd;
	
	private String cmdState;

	private String progress;

	private String status;

	private Integer appSessionId;
	
	private Integer sysId;
	
	private String tag;
	
	private String phoneType;
	
	private Boolean online;
	
	private String ipv4;
	
	private String ipv6;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public double getRxlevel() {
		return rxlevel;
	}

	public void setRxlevel(double rxlevel) {
		this.rxlevel = rxlevel;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAppSessionId() {
		return appSessionId;
	}

	public void setAppSessionId(Integer appSessionId) {
		this.appSessionId = appSessionId;
	}

	public Integer getSysId() {
		return sysId;
	}

	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getCmdState() {
		return cmdState;
	}

	public void setCmdState(String cmdState) {
		this.cmdState = cmdState;
	}

	public String getIpv4() {
		return ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public String getIpv6() {
		return ipv6;
	}

	public void setIpv6(String ipv6) {
		this.ipv6 = ipv6;
	}
	
	
	
	

}
