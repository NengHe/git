package com.hexin.icp.bean;

import java.io.Serializable;

public class ZTreeDTO implements Serializable {
	private static final long serialVersionUID = -6678712069605182224L;

	private Integer id;
	private Integer pId = 0;
	private String name;
	private String icon;
	private boolean open = true;
	
	private boolean checked=false;

	private String type;
	
	private String resourceName; 
	private String permission; 
	private String resourceAttrDesc; 

	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResourceAttrDesc() {
		return resourceAttrDesc;
	}

	public void setResourceAttrDesc(String resourceAttrDesc) {
		this.resourceAttrDesc = resourceAttrDesc;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

}
