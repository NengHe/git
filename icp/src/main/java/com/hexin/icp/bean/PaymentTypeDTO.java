package com.hexin.icp.bean;

import java.io.Serializable;

public class PaymentTypeDTO implements Serializable {

    private static final long serialVersionUID = -1917772850570601855L;

    private Integer           payId;
    private Integer           colPaymentType;
    private String            colPayAccount;

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Integer getColPaymentType() {
        return colPaymentType;
    }

    public void setColPaymentType(Integer colPaymentType) {
        this.colPaymentType = colPaymentType;
    }

    public String getColPayAccount() {
        return colPayAccount;
    }

    public void setColPayAccount(String colPayAccount) {
        this.colPayAccount = colPayAccount;
    }

}
