package com.hexin.icp.bean;

import java.io.Serializable;

public class PaymentHistoryDTO implements Serializable {

    private static final long serialVersionUID = -1917772850570601855L;

    private String           colReceiverAccount;
    private String           colMoney;
    
    public String getColReceiverAccount() {
        return colReceiverAccount;
    }
    
    public void setColReceiverAccount(String colReceiverAccount) {
        this.colReceiverAccount = colReceiverAccount;
    }
    
    public String getColMoney() {
        return colMoney;
    }
    
    public void setColMoney(String colMoney) {
        this.colMoney = colMoney;
    }


    
}
