package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.Date;

public class OrgReq implements Serializable {
	private static final long serialVersionUID = 3778328993704944672L;
	private Integer orgJoinReqId;
	private Integer colOrgId;
	private Integer colReqUserId;
	private String colRequestMemo;
	private String colReqAuthStatus;
	private Date colCreateTime;

	private String colOrgCode;
	private String colOrgName;

	private String colUserName;
	private String colUserTel;
	private String colUserMobile;
	private String colUserEmail;
	private String colUserAddress;
	private String colUserCompany;
	
	public Integer getOrgJoinReqId() {
		return orgJoinReqId;
	}
	public void setOrgJoinReqId(Integer orgJoinReqId) {
		this.orgJoinReqId = orgJoinReqId;
	}
	public Integer getColOrgId() {
		return colOrgId;
	}
	public void setColOrgId(Integer colOrgId) {
		this.colOrgId = colOrgId;
	}
	public Integer getColReqUserId() {
		return colReqUserId;
	}
	public void setColReqUserId(Integer colReqUserId) {
		this.colReqUserId = colReqUserId;
	}
	public String getColRequestMemo() {
		return colRequestMemo;
	}
	public void setColRequestMemo(String colRequestMemo) {
		this.colRequestMemo = colRequestMemo;
	}
	public String getColReqAuthStatus() {
		return colReqAuthStatus;
	}
	public void setColReqAuthStatus(String colReqAuthStatus) {
		this.colReqAuthStatus = colReqAuthStatus;
	}
	public Date getColCreateTime() {
		return colCreateTime;
	}
	public void setColCreateTime(Date colCreateTime) {
		this.colCreateTime = colCreateTime;
	}
	public String getColOrgCode() {
		return colOrgCode;
	}
	public void setColOrgCode(String colOrgCode) {
		this.colOrgCode = colOrgCode;
	}
	public String getColOrgName() {
		return colOrgName;
	}
	public void setColOrgName(String colOrgName) {
		this.colOrgName = colOrgName;
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
	public String getColUserCompany() {
		return colUserCompany;
	}
	public void setColUserCompany(String colUserCompany) {
		this.colUserCompany = colUserCompany;
	}

	

}
