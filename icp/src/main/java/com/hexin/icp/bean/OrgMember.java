package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.List;

public class OrgMember implements Serializable {
	private static final long serialVersionUID = 3778328993704944670L;

	private Integer userId;
	private String colUserName;
	private String colUserTel;
	private String colUserMobile;
	private String colUserEmail;
	private String colUserAddress;
	private String colUserCompany;
	private String colUserJob;
	private String colReceiveMsgFlag;
	private String colFriendInvite;

	private Integer orgId;
	private String colOrgCode;
	private String colOrgName;

	private Integer orgMemInternalId;
	private Integer colShowDetailInner;
	private Integer colShowDetailOutter;
	private Integer colOrgUserStatus;

	private Integer positionId;
	private String colPosName;
	
	private List<OrgPosition> orgPositions;
	
	private String colUserIndustry;
	
	
	
    
    public String getColUserIndustry() {
        return colUserIndustry;
    }

    
    public void setColUserIndustry(String colUserIndustry) {
        this.colUserIndustry = colUserIndustry;
    }

    public String getColFriendInvite() {
        return colFriendInvite;
    }
    
    public void setColFriendInvite(String colFriendInvite) {
        this.colFriendInvite = colFriendInvite;
    }
    public String getColUserJob() {
		return colUserJob;
	}
	public void setColUserJob(String colUserJob) {
		this.colUserJob = colUserJob;
	}
	public List<OrgPosition> getOrgPositions() {
		return orgPositions;
	}
	public void setOrgPositions(List<OrgPosition> orgPositions) {
		this.orgPositions = orgPositions;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public String getColReceiveMsgFlag() {
		return colReceiveMsgFlag;
	}
	public void setColReceiveMsgFlag(String colReceiveMsgFlag) {
		this.colReceiveMsgFlag = colReceiveMsgFlag;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
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
	public Integer getOrgMemInternalId() {
		return orgMemInternalId;
	}
	public void setOrgMemInternalId(Integer orgMemInternalId) {
		this.orgMemInternalId = orgMemInternalId;
	}
	public Integer getColShowDetailInner() {
		return colShowDetailInner;
	}
	public void setColShowDetailInner(Integer colShowDetailInner) {
		this.colShowDetailInner = colShowDetailInner;
	}
	public Integer getColShowDetailOutter() {
		return colShowDetailOutter;
	}
	public void setColShowDetailOutter(Integer colShowDetailOutter) {
		this.colShowDetailOutter = colShowDetailOutter;
	}
	public Integer getColOrgUserStatus() {
		return colOrgUserStatus;
	}
	public void setColOrgUserStatus(Integer colOrgUserStatus) {
		this.colOrgUserStatus = colOrgUserStatus;
	}
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public String getColPosName() {
		return colPosName;
	}
	public void setColPosName(String colPosName) {
		this.colPosName = colPosName;
	}

	
	

}
