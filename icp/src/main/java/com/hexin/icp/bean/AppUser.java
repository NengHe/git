package com.hexin.icp.bean;

import java.io.Serializable;

public class AppUser implements Serializable {

	private static final long serialVersionUID = 6637354952835805212L;
	
	private Integer userId;
	private String colUserLoginId;
	private String colUserName;
	private String colUserTel;
	private String colUserMobile;
	private String colUserEmail;
	private String colUserAddress;
	private String colUserOthers;
	private String colUserJob;
	private String colUserIndustry;
	private String colUserCompany;
	private Integer colReceiveMsgFlag;
	private Integer colFriendInvite;
	private Integer colDelFlag;
	private String userLoginDeviceMac;
	private String createTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getColUserLoginId() {
		return colUserLoginId;
	}

	public void setColUserLoginId(String colUserLoginId) {
		this.colUserLoginId = colUserLoginId;
	}

	public String getColUserName() {
		return colUserName;
	}

	public void setColUserName(String colUserName) {
		this.colUserName = colUserName;
	}

	public String getColUserTel() {
		return colUserTel;
	}

	public void setColUserTel(String colUserTel) {
		this.colUserTel = colUserTel;
	}

	public String getColUserMobile() {
		return colUserMobile;
	}

	public void setColUserMobile(String colUserMobile) {
		this.colUserMobile = colUserMobile;
	}

	public String getColUserEmail() {
		return colUserEmail;
	}

	public void setColUserEmail(String colUserEmail) {
		this.colUserEmail = colUserEmail;
	}

	public String getColUserAddress() {
		return colUserAddress;
	}

	public void setColUserAddress(String colUserAddress) {
		this.colUserAddress = colUserAddress;
	}

	public String getColUserOthers() {
		return colUserOthers;
	}

	public void setColUserOthers(String colUserOthers) {
		this.colUserOthers = colUserOthers;
	}

	public String getColUserJob() {
		return colUserJob;
	}

	public void setColUserJob(String colUserJob) {
		this.colUserJob = colUserJob;
	}

	public String getColUserIndustry() {
		return colUserIndustry;
	}

	public void setColUserIndustry(String colUserIndustry) {
		this.colUserIndustry = colUserIndustry;
	}

	public String getColUserCompany() {
		return colUserCompany;
	}

	public void setColUserCompany(String colUserCompany) {
		this.colUserCompany = colUserCompany;
	}

	public Integer getColReceiveMsgFlag() {
		return colReceiveMsgFlag;
	}

	public void setColReceiveMsgFlag(Integer colReceiveMsgFlag) {
		this.colReceiveMsgFlag = colReceiveMsgFlag;
	}

	public Integer getColFriendInvite() {
		return colFriendInvite;
	}

	public void setColFriendInvite(Integer colFriendInvite) {
		this.colFriendInvite = colFriendInvite;
	}

	public Integer getColDelFlag() {
		return colDelFlag;
	}

	public void setColDelFlag(Integer colDelFlag) {
		this.colDelFlag = colDelFlag;
	}

	public String getUserLoginDeviceMac() {
		return userLoginDeviceMac;
	}

	public void setUserLoginDeviceMac(String userLoginDeviceMac) {
		this.userLoginDeviceMac = userLoginDeviceMac;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
