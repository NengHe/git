package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.Date;

public class News implements Serializable{

	private static final long serialVersionUID = 17715437631135782L;
	
	
	private Integer newsId;
	private Integer colNewsIssuerId;
	private String colNewsIssuerUsername;
	private String colNewsTitle;
	private String colNewsBrief;
	private String colNewsContent;
	private String colNewsContentDetailUrl;
	private String colNewsIssuerType;
	private Integer colNewsTypeId;
	private String colNewsTypeName;
	private String colNewsSource;
	private Integer colCreateBy;
	private Date colCreateTime;
	private Date colNewsIssueTime;
	private Integer colSharableFlag;
	private Integer colDelFlag;
	
	private String colOrgCode;
	private String colOrgName;
	private Integer newsTitleImgId;
	private String newsTitleImgPath;
	private String newsTitleImgCompressPath;
	
	
	private String colNewsOrientedType;
	private String colNewsTitleImgUrl;
	
	
	
	
    public String getColNewsTitleImgUrl() {
        return colNewsTitleImgUrl;
    }
    
    public void setColNewsTitleImgUrl(String colNewsTitleImgUrl) {
        this.colNewsTitleImgUrl = colNewsTitleImgUrl;
    }
    public String getColNewsOrientedType() {
		return colNewsOrientedType;
	}
	public void setColNewsOrientedType(String colNewsOrientedType) {
		this.colNewsOrientedType = colNewsOrientedType;
	}
	public String getNewsTitleImgPath() {
		return newsTitleImgPath;
	}
	public void setNewsTitleImgPath(String newsTitleImgPath) {
		this.newsTitleImgPath = newsTitleImgPath;
	}
	public Date getColNewsIssueTime() {
		return colNewsIssueTime;
	}
	public void setColNewsIssueTime(Date colNewsIssueTime) {
		this.colNewsIssueTime = colNewsIssueTime;
	}
	public Integer getColSharableFlag() {
		return colSharableFlag;
	}
	public void setColSharableFlag(Integer colSharableFlag) {
		this.colSharableFlag = colSharableFlag;
	}
	public Integer getColDelFlag() {
		return colDelFlag;
	}
	public void setColDelFlag(Integer colDelFlag) {
		this.colDelFlag = colDelFlag;
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
	public Integer getColCreateBy() {
		return colCreateBy;
	}
	public void setColCreateBy(Integer colCreateBy) {
		this.colCreateBy = colCreateBy;
	}
	public Integer getNewsTitleImgId() {
		return newsTitleImgId;
	}
	public void setNewsTitleImgId(Integer newsTitleImgId) {
		this.newsTitleImgId = newsTitleImgId;
	}
	public String getNewsTitleImgCompressPath() {
		return newsTitleImgCompressPath;
	}
	public void setNewsTitleImgCompressPath(String newsTitleImgCompressPath) {
		this.newsTitleImgCompressPath = newsTitleImgCompressPath;
	}
	public String getColNewsBrief() {
		return colNewsBrief;
	}
	public void setColNewsBrief(String colNewsBrief) {
		this.colNewsBrief = colNewsBrief;
	}
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public Integer getColNewsIssuerId() {
		return colNewsIssuerId;
	}
	public void setColNewsIssuerId(Integer colNewsIssuerId) {
		this.colNewsIssuerId = colNewsIssuerId;
	}
	public String getColNewsIssuerUsername() {
		return colNewsIssuerUsername;
	}
	public void setColNewsIssuerUsername(String colNewsIssuerUsername) {
		this.colNewsIssuerUsername = colNewsIssuerUsername;
	}
	public String getColNewsTitle() {
		return colNewsTitle;
	}
	public void setColNewsTitle(String colNewsTitle) {
		this.colNewsTitle = colNewsTitle;
	}
	public String getColNewsContent() {
		return colNewsContent;
	}
	public void setColNewsContent(String colNewsContent) {
		this.colNewsContent = colNewsContent;
	}
	public String getColNewsContentDetailUrl() {
		return colNewsContentDetailUrl;
	}
	public void setColNewsContentDetailUrl(String colNewsContentDetailUrl) {
		this.colNewsContentDetailUrl = colNewsContentDetailUrl;
	}
	public String getColNewsIssuerType() {
		return colNewsIssuerType;
	}
	public void setColNewsIssuerType(String colNewsIssuerType) {
		this.colNewsIssuerType = colNewsIssuerType;
	}
	public Integer getColNewsTypeId() {
		return colNewsTypeId;
	}
	public void setColNewsTypeId(Integer colNewsTypeId) {
		this.colNewsTypeId = colNewsTypeId;
	}
	public String getColNewsTypeName() {
		return colNewsTypeName;
	}
	public void setColNewsTypeName(String colNewsTypeName) {
		this.colNewsTypeName = colNewsTypeName;
	}
	public String getColNewsSource() {
		return colNewsSource;
	}
	public void setColNewsSource(String colNewsSource) {
		this.colNewsSource = colNewsSource;
	}
	public Date getColCreateTime() {
		return colCreateTime;
	}
	public void setColCreateTime(Date colCreateTime) {
		this.colCreateTime = colCreateTime;
	}
	
	
	
	

}
