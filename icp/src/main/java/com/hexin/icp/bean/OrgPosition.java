package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrgPosition implements Serializable {
	private static final long serialVersionUID = 3778328993704944676L;

	private Integer orgId;
	private String colOrgCode;
	private String colOrgName;

	private Integer positionId;
	private String colPosName;
	
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
