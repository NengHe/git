package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.Date;

public class CommentReplyDTO implements Serializable {
	private static final long serialVersionUID = -6678712069605182228L;

	private Integer id;
	private String colUserName;
	private String colCurReplyUserName;
	private String content;
	private String remark;
	private Integer type;		//1-评论，2-回复
	private Integer replyStatus;	//0-未回复，1-已回复
	private Integer showStatus;
	private Date createTime;
	

	
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getColUserName() {
		return colUserName;
	}

	public void setColUserName(String colUserName) {
		this.colUserName = colUserName;
	}

	public String getColCurReplyUserName() {
		return colCurReplyUserName;
	}

	public void setColCurReplyUserName(String colCurReplyUserName) {
		this.colCurReplyUserName = colCurReplyUserName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(Integer replyStatus) {
		this.replyStatus = replyStatus;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}


	
}
