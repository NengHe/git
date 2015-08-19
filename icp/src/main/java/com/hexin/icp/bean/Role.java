package com.hexin.icp.bean;

import java.io.Serializable;

public class Role implements Serializable {
	private static final long serialVersionUID = -8507882326583240167L;
	
	private Integer roleId;
	private Integer parentId;
	private String roleName;
	private String parentName;
	private String roleDesc;
	private String roleType;

	
	
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role [parentId=" + parentId + ", parentName=" + parentName
				+ ", roleDesc=" + roleDesc + ", roleId=" + roleId
				+ ", roleName=" + roleName + "]";
	}
	
	

}
