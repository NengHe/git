package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.Date;

public class CommentReply implements Serializable {
	private static final long serialVersionUID = -6678712069605182226L;

	private Integer colActCommentId;
	private Integer colUserId;
	private String colUserName;
	private String colUserImageHeader;
	private Integer colActivityId;
	private String colCommentContent;
	private Date colCommentCreateTime;
	private Integer colCommentShowStatus;

	private Integer replyId;
	private String colReplyUserType;
	private Integer colCommentId;
	private Integer colPreReplyUserId;
	private Integer colCurReplyUserId;
	private String colCurReplyUserName;
	private String colReplyContent;
	private Date colReplyCreateTime;

	
	
	public Integer getColCommentShowStatus() {
		return colCommentShowStatus;
	}

	public void setColCommentShowStatus(Integer colCommentShowStatus) {
		this.colCommentShowStatus = colCommentShowStatus;
	}

	public Integer getColActCommentId() {
		return colActCommentId;
	}

	public void setColActCommentId(Integer colActCommentId) {
		this.colActCommentId = colActCommentId;
	}

	public Integer getColUserId() {
		return colUserId;
	}

	public void setColUserId(Integer colUserId) {
		this.colUserId = colUserId;
	}

	public String getColUserName() {
		return colUserName;
	}

	public void setColUserName(String colUserName) {
		this.colUserName = colUserName;
	}

	public String getColUserImageHeader() {
		return colUserImageHeader;
	}

	public void setColUserImageHeader(String colUserImageHeader) {
		this.colUserImageHeader = colUserImageHeader;
	}

	public Integer getColActivityId() {
		return colActivityId;
	}

	public void setColActivityId(Integer colActivityId) {
		this.colActivityId = colActivityId;
	}

	public String getColCommentContent() {
		return colCommentContent;
	}

	public void setColCommentContent(String colCommentContent) {
		this.colCommentContent = colCommentContent;
	}

	public Date getColCommentCreateTime() {
		return colCommentCreateTime;
	}

	public void setColCommentCreateTime(Date colCommentCreateTime) {
		this.colCommentCreateTime = colCommentCreateTime;
	}

	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public String getColReplyUserType() {
		return colReplyUserType;
	}

	public void setColReplyUserType(String colReplyUserType) {
		this.colReplyUserType = colReplyUserType;
	}

	public Integer getColCommentId() {
		return colCommentId;
	}

	public void setColCommentId(Integer colCommentId) {
		this.colCommentId = colCommentId;
	}

	public Integer getColPreReplyUserId() {
		return colPreReplyUserId;
	}

	public void setColPreReplyUserId(Integer colPreReplyUserId) {
		this.colPreReplyUserId = colPreReplyUserId;
	}

	public Integer getColCurReplyUserId() {
		return colCurReplyUserId;
	}

	public void setColCurReplyUserId(Integer colCurReplyUserId) {
		this.colCurReplyUserId = colCurReplyUserId;
	}

	public String getColCurReplyUserName() {
		return colCurReplyUserName;
	}

	public void setColCurReplyUserName(String colCurReplyUserName) {
		this.colCurReplyUserName = colCurReplyUserName;
	}

	public String getColReplyContent() {
		return colReplyContent;
	}

	public void setColReplyContent(String colReplyContent) {
		this.colReplyContent = colReplyContent;
	}

	public Date getColReplyCreateTime() {
		return colReplyCreateTime;
	}

	public void setColReplyCreateTime(Date colReplyCreateTime) {
		this.colReplyCreateTime = colReplyCreateTime;
	}

}
