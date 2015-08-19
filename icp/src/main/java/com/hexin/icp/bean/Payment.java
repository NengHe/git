package com.hexin.icp.bean;

import java.io.Serializable;

public class Payment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8208250028240590010L;

	private Integer payId;
	private Integer colPaymentType;
	private String colPayAccount;
	private String colPayPsw;
	private String colCode;
	private String colPayTypeName;

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}


	public String getColPayAccount() {
		return colPayAccount;
	}

	public void setColPayAccount(String colPayAccount) {
		this.colPayAccount = colPayAccount;
	}

	public String getColPayPsw() {
		return colPayPsw;
	}

	public void setColPayPsw(String colPayPsw) {
		this.colPayPsw = colPayPsw;
	}

	public String getColCode() {
		return colCode;
	}

	public void setColCode(String colCode) {
		this.colCode = colCode;
	}

	public Integer getColPaymentType() {
		return colPaymentType;
	}

	public void setColPaymentType(Integer colPaymentType) {
		this.colPaymentType = colPaymentType;
	}

	public String getColPayTypeName() {
		return colPayTypeName;
	}

	public void setColPayTypeName(String colPayTypeName) {
		this.colPayTypeName = colPayTypeName;
	}

}
