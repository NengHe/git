package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.List;

public class Resource implements Serializable {
	private static final long serialVersionUID = -6678712069605182224L;

	// private Integer resourceId;
	// private Integer parentId;
	// private String name;
	// private String label;
	// private String attr;
	// private String attrDesc;
	private String permission;

	private Integer resourceId;
	private Integer colResourceParentId;
	private String colResourceName;
	private String colResouceLabel;
	private String colResourceDescribe;
	private String colResourceAttr;
	private String colResourceAttrDesc;

	private Integer resourceOperationID;
	private String operationName;
	private String isOperationEnabled;

	private List<Operation> operationList;
	private String disabled;

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public List<Operation> getOperationList() {
		return operationList;
	}

	public void setOperationList(List<Operation> operationList) {
		this.operationList = operationList;
	}

	public Integer getResourceOperationID() {
		return resourceOperationID;
	}

	public void setResourceOperationID(Integer resourceOperationID) {
		this.resourceOperationID = resourceOperationID;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getIsOperationEnabled() {
		return isOperationEnabled;
	}

	public void setIsOperationEnabled(String isOperationEnabled) {
		this.isOperationEnabled = isOperationEnabled;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getColResourceParentId() {
		return colResourceParentId;
	}

	public void setColResourceParentId(Integer colResourceParentId) {
		this.colResourceParentId = colResourceParentId;
	}

	public String getColResourceName() {
		return colResourceName;
	}

	public void setColResourceName(String colResourceName) {
		this.colResourceName = colResourceName;
	}

	public String getColResouceLabel() {
		return colResouceLabel;
	}

	public void setColResouceLabel(String colResouceLabel) {
		this.colResouceLabel = colResouceLabel;
	}

	public String getColResourceDescribe() {
		return colResourceDescribe;
	}

	public void setColResourceDescribe(String colResourceDescribe) {
		this.colResourceDescribe = colResourceDescribe;
	}

	public String getColResourceAttr() {
		return colResourceAttr;
	}

	public void setColResourceAttr(String colResourceAttr) {
		this.colResourceAttr = colResourceAttr;
	}

	public String getColResourceAttrDesc() {
		return colResourceAttrDesc;
	}

	public void setColResourceAttrDesc(String colResourceAttrDesc) {
		this.colResourceAttrDesc = colResourceAttrDesc;
	}

}
