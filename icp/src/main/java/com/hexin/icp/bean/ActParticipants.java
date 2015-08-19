package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.Date;

public class ActParticipants implements Serializable {
	private static final long serialVersionUID = 3778328993704944673L;
	private Integer colUserActivityId;
	private Integer colActivityId;
	private Integer colUserId;
	private String colAuthStatus;
	private Date colCreateTime;

	private String colUserName;
	private String colUserMobile;
	private String colUserEmail;
	private String colUserAddress;
	private String colUserCompany;
	
	private String colOrgCode;
	private String colOrgName;
	private String colPaymentStatus;
	
	
	
	public String getColOrgCode() {
		return colOrgCode;
	}
	
    public String getColPaymentStatus() {
        return colPaymentStatus;
    }
    
    public void setColPaymentStatus(String colPaymentStatus) {
        this.colPaymentStatus = colPaymentStatus;
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
	public Integer getColUserActivityId() {
		return colUserActivityId;
	}
	public void setColUserActivityId(Integer colUserActivityId) {
		this.colUserActivityId = colUserActivityId;
	}
	public Integer getColActivityId() {
		return colActivityId;
	}
	public void setColActivityId(Integer colActivityId) {
		this.colActivityId = colActivityId;
	}
	public Integer getColUserId() {
		return colUserId;
	}
	public void setColUserId(Integer colUserId) {
		this.colUserId = colUserId;
	}
	public String getColAuthStatus() {
		return colAuthStatus;
	}
	public void setColAuthStatus(String colAuthStatus) {
		this.colAuthStatus = colAuthStatus;
	}
	public Date getColCreateTime() {
		return colCreateTime;
	}
	public void setColCreateTime(Date colCreateTime) {
		this.colCreateTime = colCreateTime;
	}
	public String getColUserName() {
		return colUserName;
	}
	public void setColUserName(String colUserName) {
		this.colUserName = colUserName;
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
