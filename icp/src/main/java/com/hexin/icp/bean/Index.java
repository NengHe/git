package com.hexin.icp.bean;

import java.io.Serializable;

public class Index implements Serializable {
	private static final long serialVersionUID = 623807778725296588L;
	private Integer dictId;
	private String colLabelClass;
	private String colLabelText;
	private String colDelFlag;
	
	
	
	public String getColLabelClass() {
		return colLabelClass;
	}
	public void setColLabelClass(String colLabelClass) {
		this.colLabelClass = colLabelClass;
	}
	public String getColDelFlag() {
		return colDelFlag;
	}
	public void setColDelFlag(String colDelFlag) {
		this.colDelFlag = colDelFlag;
	}
	public Integer getDictId() {
		return dictId;
	}
	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}
	public String getColLabelText() {
		return colLabelText;
	}
	public void setColLabelText(String colLabelText) {
		this.colLabelText = colLabelText;
	}
	
	
	
}
