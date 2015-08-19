package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.Date;

public class Dict implements Serializable {

	private static final long serialVersionUID = 4944553240069288821L;
	
	private Integer dictId;
	private String colDictClass;
	private String colDictKey;
	private String colDictVal;
	private String colDictText;
	private Date colDictCreateTime;
	private Integer colDictCreateBy;

	public Integer getDictId() {
		return dictId;
	}

	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}

	public String getColDictClass() {
		return colDictClass;
	}

	public void setColDictClass(String colDictClass) {
		this.colDictClass = colDictClass;
	}

	public String getColDictKey() {
		return colDictKey;
	}

	public void setColDictKey(String colDictKey) {
		this.colDictKey = colDictKey;
	}

	public String getColDictVal() {
		return colDictVal;
	}

	public void setColDictVal(String colDictVal) {
		this.colDictVal = colDictVal;
	}

	public String getColDictText() {
		return colDictText;
	}

	public void setColDictText(String colDictText) {
		this.colDictText = colDictText;
	}

	public Date getColDictCreateTime() {
		return colDictCreateTime;
	}

	public void setColDictCreateTime(Date colDictCreateTime) {
		this.colDictCreateTime = colDictCreateTime;
	}

	public Integer getColDictCreateBy() {
		return colDictCreateBy;
	}

	public void setColDictCreateBy(Integer colDictCreateBy) {
		this.colDictCreateBy = colDictCreateBy;
	}

}
