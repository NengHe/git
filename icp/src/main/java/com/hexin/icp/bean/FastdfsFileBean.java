package com.hexin.icp.bean;

public class FastdfsFileBean {
	private String groupName;
	private String fileId;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	@Override
	public String toString() {
		return "FastdfsFileBean [groupName=" + groupName + ", fileId=" + fileId
				+ "]";
	}
	
}
