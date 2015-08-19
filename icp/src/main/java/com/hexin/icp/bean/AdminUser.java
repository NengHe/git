package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AdminUser implements Serializable {
	private static final long serialVersionUID = 3778328993704944670L;

	private Integer adminUserId;
	private Integer colOrgId;
	private String colAdminUsername;
	private String colAdminUserPassword;
	private String colAdminUserType;
	private Integer createBy;
	private Integer lastUpdateBy;
	private String colDelFlag;

	private String colOrgCode;
	private String colOrgName;

	private Integer roleId;
	private String roleName;

	private List<Role> roleList;
	private List<Resource> resourceList;

	
	
	public String getColDelFlag() {
		return colDelFlag;
	}

	public void setColDelFlag(String colDelFlag) {
		this.colDelFlag = colDelFlag;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public Integer getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(Integer adminUserId) {
		this.adminUserId = adminUserId;
	}

	public Integer getColOrgId() {
		return colOrgId;
	}

	public void setColOrgId(Integer colOrgId) {
		this.colOrgId = colOrgId;
	}

	public String getColAdminUsername() {
		return colAdminUsername;
	}

	public void setColAdminUsername(String colAdminUsername) {
		this.colAdminUsername = colAdminUsername;
	}

	public String getColAdminUserPassword() {
		return colAdminUserPassword;
	}

	public void setColAdminUserPassword(String colAdminUserPassword) {
		this.colAdminUserPassword = colAdminUserPassword;
	}

	public String getColAdminUserType() {
		return colAdminUserType;
	}

	public void setColAdminUserType(String colAdminUserType) {
		this.colAdminUserType = colAdminUserType;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Integer getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Integer lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}

}
