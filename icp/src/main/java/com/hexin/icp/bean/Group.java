package com.hexin.icp.bean;

import java.io.Serializable;


public class Group implements Serializable {
    private static final long serialVersionUID = -926605384992035992L;
    
    private Integer groupId;
    private String groupName;
    
    public Integer getGroupId() {
        return groupId;
    }
    
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    

}
