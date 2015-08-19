package com.hexin.icp.bean;

import java.io.Serializable;
import java.util.List;

public class ResourceTree implements Serializable {
    private static final long serialVersionUID = -6678712069605182225L;

    private Integer resourceId;
    private String name;
    private String label;
    private String attr;
    private String attrDesc;
    private String permission;

    private List<ResourceTree> children;
    
    private List<Operation> operationList;

    private Integer parentResourceID;

    private boolean isShow;
    
	public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<ResourceTree> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceTree> children) {
        this.children = children;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getAttrDesc() {
        return attrDesc;
    }

    public void setAttrDesc(String attrDesc) {
        this.attrDesc = attrDesc;
    }

    public List<Operation> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<Operation> operationList) {
        this.operationList = operationList;
    }

    public Integer getParentResourceID() {
        return parentResourceID;
    }

    public void setParentResourceID(Integer parentResourceID) {
        this.parentResourceID = parentResourceID;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

}
