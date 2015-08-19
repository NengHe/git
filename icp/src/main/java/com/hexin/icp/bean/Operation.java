package com.hexin.icp.bean;

import java.io.Serializable;

public class Operation implements Serializable{
	private static final long serialVersionUID = 2689931711696850654L;
	
	private Integer resourceOperationID;
	private String operationName;
	private String isOperationEnabled;
	private String disabled;		//表示隐含权限，即子角色拥有的权限

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
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

	public Integer getResourceOperationID() {
		return resourceOperationID;
	}

	public void setResourceOperationID(Integer resourceOperationID) {
		this.resourceOperationID = resourceOperationID;
	}

}
