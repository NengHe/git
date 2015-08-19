package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Org implements Serializable {
	private static final long serialVersionUID = 3778328993704944671L;
	private Integer orgId;
	private Integer colOrgParentId;
	private String colOrgType;
	private String colOrgTypeName;
	private String colOrgCode;
	private String colOrgName;
	private String colOrgNote;
	private String colOrgIntro;
	private String colOrgTel;
	private String colOrgMobile;
	private String colOrgEmail;
	private String colOrgAddress;
	private String colOrgOthers;
	private String colOrgFax;
	private String colDelFlag;
	private Integer colOrgShowMemberFlag;
	private String colOrgLatitude;
	private String colOrgLongitude;
	private Integer colOrgAllowJoinFlag;
	
	private Integer imgId;
	private String colImgPath;
	private String colImgCompressPath;
	
	
	
	
	public String getColOrgTypeName() {
		return colOrgTypeName;
	}
	public void setColOrgTypeName(String colOrgTypeName) {
		this.colOrgTypeName = colOrgTypeName;
	}
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}
	public String getColImgPath() {
		return colImgPath;
	}
	public void setColImgPath(String colImgPath) {
		this.colImgPath = colImgPath;
	}
	public String getColImgCompressPath() {
		return colImgCompressPath;
	}
	public void setColImgCompressPath(String colImgCompressPath) {
		this.colImgCompressPath = colImgCompressPath;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getColOrgParentId() {
		return colOrgParentId;
	}
	public void setColOrgParentId(Integer colOrgParentId) {
		this.colOrgParentId = colOrgParentId;
	}
	public String getColOrgType() {
		return colOrgType;
	}
	public void setColOrgType(String colOrgType) {
		this.colOrgType = colOrgType;
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
	public String getColOrgNote() {
		return colOrgNote;
	}
	public void setColOrgNote(String colOrgNote) {
		this.colOrgNote = colOrgNote;
	}
	public String getColOrgIntro() {
		return colOrgIntro;
	}
	public void setColOrgIntro(String colOrgIntro) {
		this.colOrgIntro = colOrgIntro;
	}
	public String getColOrgTel() {
		return colOrgTel;
	}
	public void setColOrgTel(String colOrgTel) {
		this.colOrgTel = colOrgTel;
	}
	public String getColOrgMobile() {
		return colOrgMobile;
	}
	public void setColOrgMobile(String colOrgMobile) {
		this.colOrgMobile = colOrgMobile;
	}
	public String getColOrgEmail() {
		return colOrgEmail;
	}
	public void setColOrgEmail(String colOrgEmail) {
		this.colOrgEmail = colOrgEmail;
	}
	public String getColOrgAddress() {
		return colOrgAddress;
	}
	public void setColOrgAddress(String colOrgAddress) {
		this.colOrgAddress = colOrgAddress;
	}
	public String getColOrgOthers() {
		return colOrgOthers;
	}
	public void setColOrgOthers(String colOrgOthers) {
		this.colOrgOthers = colOrgOthers;
	}
	public String getColOrgFax() {
		return colOrgFax;
	}
	public void setColOrgFax(String colOrgFax) {
		this.colOrgFax = colOrgFax;
	}
	public String getColDelFlag() {
		return colDelFlag;
	}
	public void setColDelFlag(String colDelFlag) {
		this.colDelFlag = colDelFlag;
	}
	public Integer getColOrgShowMemberFlag() {
		return colOrgShowMemberFlag;
	}
	public void setColOrgShowMemberFlag(Integer colOrgShowMemberFlag) {
		this.colOrgShowMemberFlag = colOrgShowMemberFlag;
	}
	public String getColOrgLatitude() {
		return colOrgLatitude;
	}
	public void setColOrgLatitude(String colOrgLatitude) {
		this.colOrgLatitude = colOrgLatitude;
	}
	public String getColOrgLongitude() {
		return colOrgLongitude;
	}
	public void setColOrgLongitude(String colOrgLongitude) {
		this.colOrgLongitude = colOrgLongitude;
	}
	public Integer getColOrgAllowJoinFlag() {
		return colOrgAllowJoinFlag;
	}
	public void setColOrgAllowJoinFlag(Integer colOrgAllowJoinFlag) {
		this.colOrgAllowJoinFlag = colOrgAllowJoinFlag;
	}

}
