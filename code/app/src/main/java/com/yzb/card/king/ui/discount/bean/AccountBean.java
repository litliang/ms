package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * Created by FuChengRongZi on 2016/7/4.
 * <p/>
 * 账户为余额，红包，礼品卡，积分时的出参
 */
public class AccountBean implements Serializable
{

    /**
     * accountType : 0
     * bankId : 3
     * bankName : 交通银行借记卡(尾号6668)
     * customerBankId : 28
     * fullNo : 4984511111116668
     * reservedMobile : 15956912650
     */

    private String accountId;
    private String accountType;
    private String bankName;
    private String customerBankId;
    private String fullNo;
    private String reservedMobile;

    public int imgId; // 图片的logo 的id；
    public String bankId;//银行卡id；
    public String name;
    public String availableYe;
    public String balance; //账户余额；

    public AccountBean()
    {

    }

    public String getAccountId()
    {
        return accountId;
    }

    public String getBalance()
    {
        return balance;
    }

    public void setBalance(String balance)
    {
        this.balance = balance;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public String getBankId()
    {
        return bankId;
    }

    public void setBankId(String bankId)
    {
        this.bankId = bankId;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getCustomerBankId()
    {
        return customerBankId;
    }

    public void setCustomerBankId(String customerBankId)
    {
        this.customerBankId = customerBankId;
    }

    public String getFullNo()
    {
        return fullNo;
    }

    public void setFullNo(String fullNo)
    {
        this.fullNo = fullNo;
    }

    public String getReservedMobile()
    {
        return reservedMobile;
    }

    public void setReservedMobile(String reservedMobile)
    {
        this.reservedMobile = reservedMobile;
    }


    public AccountBean(String name, String availableYe, int imgId, String bankId)
    {
        this.name = name;
        this.availableYe = availableYe;
        this.imgId = imgId;
    }
}
