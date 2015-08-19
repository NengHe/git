package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Position implements Serializable {
	private static final long serialVersionUID = 3778328993704944672L;
	
	private Integer positionId;
	private Integer colPosOrgId;
	private String colPosName;
	private String colPosLevel;
	
	private String colOrgCode;
	private String colOrgName;
	
	private String colPosShowDetailInner;
	private String colPosShowDetailOutter;
	
	
	
	public String getColPosShowDetailInner() {
		return colPosShowDetailInner;
	}
	public void setColPosShowDetailInner(String colPosShowDetailInner) {
		this.colPosShowDetailInner = colPosShowDetailInner;
	}
	public String getColPosShowDetailOutter() {
		return colPosShowDetailOutter;
	}
	public void setColPosShowDetailOutter(String colPosShowDetailOutter) {
		this.colPosShowDetailOutter = colPosShowDetailOutter;
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
	public Integer getColPosOrgId() {
		return colPosOrgId;
	}
	public void setColPosOrgId(Integer colPosOrgId) {
		this.colPosOrgId = colPosOrgId;
	}
	public String getColPosName() {
		return colPosName;
	}
	public void setColPosName(String colPosName) {
		this.colPosName = colPosName;
	}
	public String getColPosLevel() {
		return colPosLevel;
	}
	public void setColPosLevel(String colPosLevel) {
		this.colPosLevel = colPosLevel;
	}

}
