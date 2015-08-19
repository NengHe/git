package com.hexin.icp.bean;

import java.io.Serializable;

public class Img implements Serializable {

	private static final long serialVersionUID = -4174425057989481124L;
	
	private Integer imgId;
	private String colImgPath;
	private String colImgCompressPath;
	
	
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}
	public String getColImgPath() {
		return colImgPath;
	}
	public void setColImgPath(String colImgPath) {
		this.colImgPath = colImgPath;
	}
	public String getColImgCompressPath() {
		return colImgCompressPath;
	}
	public void setColImgCompressPath(String colImgCompressPath) {
		this.colImgCompressPath = colImgCompressPath;
	}
	public Img() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Img(String colImgPath, String colImgCompressPath) {
		super();
		this.colImgPath = colImgPath;
		this.colImgCompressPath = colImgCompressPath;
	}

}
