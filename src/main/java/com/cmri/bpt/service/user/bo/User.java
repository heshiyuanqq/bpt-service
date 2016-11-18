package com.cmri.bpt.service.user.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.cmri.bpt.common.json.JsonDateTimeSerializer;
import com.cmri.bpt.common.util.EnumUtil;
import com.cmri.bpt.common.util.StrUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class User implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8720619956130138157L;

	private Integer id;

	private String nickname;

	private String phoneNumber;

	private String password;

	private String salt;

	private String sessionId;

	private Integer cityId;

	@JsonIgnore
	private Date registerTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	@JsonIgnore
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId == null ? null : sessionId.trim();
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	@JsonIgnore
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
}