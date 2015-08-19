package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.Date;

public class Activity implements Serializable {

	private static final long serialVersionUID = 6019980209872676055L;

	private Integer activityId;
	private String colActivityType;
	private String colActivityOrgnizer;
	private String colActivityTitle;
	private String colActivityBrief;
	private Date colActivityStartTime;
	private Date colActivityEndTime;
	private String colActivityPlace;
	private Float colActivityPlaceLatitude;
	private Float colActivityPlaceLongitude;
	private String colActivityBackground;
	private String colActivityAgenda;
	private String colActivityGuest;
	private String colActivityContent;
	private String colActivityContentDetailUrl;
	private Float colActivityCost;
	private String colActivitySharableFlag;
	private String colDelFlag;
	
	private Integer colActivityIndexId;
	private String colActivityIndexLabel;
	
	private Integer colActivityIssueOrgId;
	private String colActivityIssueOrgType;
	private String colOrgCode;
	private String colOrgName;
	private Integer actTitleImgId;
	private String actTitleImgPath;
	private String actTitleImgCompressPath;
	
	private String colActivityTitleImgUrl;
	private String colActivityBriefImgUrl;
	private Date colActivityEnrollDeadline;
    private String colActivityAuditableFlag;
    private String colActivityNeedPayFlag;
	
    private String colActivityStatus;
	
    
    
    
	
    
    
    public String getColActivityStatus() {
        return colActivityStatus;
    }


    
    public void setColActivityStatus(String colActivityStatus) {
        this.colActivityStatus = colActivityStatus;
    }


    public String getColActivityTitleImgUrl() {
        return colActivityTitleImgUrl;
    }

    
    public void setColActivityTitleImgUrl(String colActivityTitleImgUrl) {
        this.colActivityTitleImgUrl = colActivityTitleImgUrl;
    }

    
    public String getColActivityBriefImgUrl() {
        return colActivityBriefImgUrl;
    }

    
    public void setColActivityBriefImgUrl(String colActivityBriefImgUrl) {
        this.colActivityBriefImgUrl = colActivityBriefImgUrl;
    }

    public Date getColActivityEnrollDeadline() {
        return colActivityEnrollDeadline;
    }
    
    public void setColActivityEnrollDeadline(Date colActivityEnrollDeadline) {
        this.colActivityEnrollDeadline = colActivityEnrollDeadline;
    }
    
    public String getColActivityAuditableFlag() {
        return colActivityAuditableFlag;
    }
    
    public void setColActivityAuditableFlag(String colActivityAuditableFlag) {
        this.colActivityAuditableFlag = colActivityAuditableFlag;
    }
    
    public String getColActivityNeedPayFlag() {
        return colActivityNeedPayFlag;
    }
    
    public void setColActivityNeedPayFlag(String colActivityNeedPayFlag) {
        this.colActivityNeedPayFlag = colActivityNeedPayFlag;
    }
    public String getColActivityContentDetailUrl() {
		return colActivityContentDetailUrl;
	}
	public void setColActivityContentDetailUrl(String colActivityContentDetailUrl) {
		this.colActivityContentDetailUrl = colActivityContentDetailUrl;
	}
	public String getColActivityBrief() {
		return colActivityBrief;
	}
	public void setColActivityBrief(String colActivityBrief) {
		this.colActivityBrief = colActivityBrief;
	}
	public String getColActivityIndexLabel() {
		return colActivityIndexLabel;
	}
	public void setColActivityIndexLabel(String colActivityIndexLabel) {
		this.colActivityIndexLabel = colActivityIndexLabel;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getColActivityType() {
		return colActivityType;
	}
	public void setColActivityType(String colActivityType) {
		this.colActivityType = colActivityType;
	}
	public Integer getColActivityIndexId() {
		return colActivityIndexId;
	}
	public void setColActivityIndexId(Integer colActivityIndexId) {
		this.colActivityIndexId = colActivityIndexId;
	}
	public String getColActivityOrgnizer() {
		return colActivityOrgnizer;
	}
	public void setColActivityOrgnizer(String colActivityOrgnizer) {
		this.colActivityOrgnizer = colActivityOrgnizer;
	}
	public String getColActivityTitle() {
		return colActivityTitle;
	}
	public void setColActivityTitle(String colActivityTitle) {
		this.colActivityTitle = colActivityTitle;
	}
	public Date getColActivityStartTime() {
		return colActivityStartTime;
	}
	public void setColActivityStartTime(Date colActivityStartTime) {
		this.colActivityStartTime = colActivityStartTime;
	}
	public Date getColActivityEndTime() {
		return colActivityEndTime;
	}
	public void setColActivityEndTime(Date colActivityEndTime) {
		this.colActivityEndTime = colActivityEndTime;
	}
	public String getColActivityPlace() {
		return colActivityPlace;
	}
	public void setColActivityPlace(String colActivityPlace) {
		this.colActivityPlace = colActivityPlace;
	}
	public Float getColActivityPlaceLatitude() {
		return colActivityPlaceLatitude;
	}
	public void setColActivityPlaceLatitude(Float colActivityPlaceLatitude) {
		this.colActivityPlaceLatitude = colActivityPlaceLatitude;
	}
	public Float getColActivityPlaceLongitude() {
		return colActivityPlaceLongitude;
	}
	public void setColActivityPlaceLongitude(Float colActivityPlaceLongitude) {
		this.colActivityPlaceLongitude = colActivityPlaceLongitude;
	}
	public String getColActivityBackground() {
		return colActivityBackground;
	}
	public void setColActivityBackground(String colActivityBackground) {
		this.colActivityBackground = colActivityBackground;
	}
	public String getColActivityAgenda() {
		return colActivityAgenda;
	}
	public void setColActivityAgenda(String colActivityAgenda) {
		this.colActivityAgenda = colActivityAgenda;
	}
	public String getColActivityGuest() {
		return colActivityGuest;
	}
	public void setColActivityGuest(String colActivityGuest) {
		this.colActivityGuest = colActivityGuest;
	}
	public String getColActivityContent() {
		return colActivityContent;
	}
	public void setColActivityContent(String colActivityContent) {
		this.colActivityContent = colActivityContent;
	}
	public Float getColActivityCost() {
		return colActivityCost;
	}
	public void setColActivityCost(Float colActivityCost) {
		this.colActivityCost = colActivityCost;
	}
	public String getColActivitySharableFlag() {
		return colActivitySharableFlag;
	}
	public void setColActivitySharableFlag(String colActivitySharableFlag) {
		this.colActivitySharableFlag = colActivitySharableFlag;
	}
	public String getColDelFlag() {
		return colDelFlag;
	}
	public void setColDelFlag(String colDelFlag) {
		this.colDelFlag = colDelFlag;
	}
	public Integer getColActivityIssueOrgId() {
		return colActivityIssueOrgId;
	}
	public void setColActivityIssueOrgId(Integer colActivityIssueOrgId) {
		this.colActivityIssueOrgId = colActivityIssueOrgId;
	}
	public String getColActivityIssueOrgType() {
		return colActivityIssueOrgType;
	}
	public void setColActivityIssueOrgType(String colActivityIssueOrgType) {
		this.colActivityIssueOrgType = colActivityIssueOrgType;
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
	public Integer getActTitleImgId() {
		return actTitleImgId;
	}
	public void setActTitleImgId(Integer actTitleImgId) {
		this.actTitleImgId = actTitleImgId;
	}
	public String getActTitleImgPath() {
		return actTitleImgPath;
	}
	public void setActTitleImgPath(String actTitleImgPath) {
		this.actTitleImgPath = actTitleImgPath;
	}
	public String getActTitleImgCompressPath() {
		return actTitleImgCompressPath;
	}
	public void setActTitleImgCompressPath(String actTitleImgCompressPath) {
		this.actTitleImgCompressPath = actTitleImgCompressPath;
	}
	
	
}
