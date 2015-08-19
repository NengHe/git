package com.hexin.icp.bean;

import java.io.Serializable;

public class PayHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7934574328612373966L;
	private Integer payHistoryId;
	private Integer colUserActId;
	private Integer colAppUserId;
	private String colPayNum;
	private String colPayAccount;
	private String colGetAccount;
	private Integer colPaymentType;
	private Integer colMoney;
	private String colTime;
	private Integer colPayType;
	private Integer colState;
	private String colStateTime;
	private String colPayTypeName;

	public Integer getPayHistoryId() {
		return payHistoryId;
	}

	public void setPayHistoryId(Integer payHistoryId) {
		this.payHistoryId = payHistoryId;
	}

	public String getColPayAccount() {
		return colPayAccount;
	}

	public void setColPayAccount(String colPayAccount) {
		this.colPayAccount = colPayAccount;
	}

	public String getColGetAccount() {
		return colGetAccount;
	}

	public void setColGetAccount(String colGetAccount) {
		this.colGetAccount = colGetAccount;
	}


	public Integer getColMoney() {
		return colMoney;
	}

	public void setColMoney(Integer colMoney) {
		this.colMoney = colMoney;
	}

	public String getColTime() {
		return colTime;
	}

	public void setColTime(String colTime) {
		this.colTime = colTime;
	}

	public Integer getColState() {
		return colState;
	}

	public void setColState(Integer colState) {
		this.colState = colState;
	}

	public String getColStateTime() {
		return colStateTime;
	}

	public void setColStateTime(String colStateTime) {
		this.colStateTime = colStateTime;
	}

	public Integer getColPayType() {
		return colPayType;
	}

	public void setColPayType(Integer colPayType) {
		this.colPayType = colPayType;
	}

	public String getColPayNum() {
		return colPayNum;
	}

	public void setColPayNum(String colPayNum) {
		this.colPayNum = colPayNum;
	}

	public Integer getColPaymentType() {
		return colPaymentType;
	}

	public void setColPaymentType(Integer colPaymentType) {
		this.colPaymentType = colPaymentType;
	}

	public Integer getColAppUserId() {
		return colAppUserId;
	}

	public void setColAppUserId(Integer colAppUserId) {
		this.colAppUserId = colAppUserId;
	}

	public Integer getColUserActId() {
		return colUserActId;
	}

	public void setColUserActId(Integer colUserActId) {
		this.colUserActId = colUserActId;
	}

	public String getColPayTypeName() {
		return colPayTypeName;
	}

	public void setColPayTypeName(String colPayTypeName) {
		this.colPayTypeName = colPayTypeName;
	}

}
