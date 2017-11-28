package com.yzb.card.king.bean.common;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/13 14:58
 */
public class PayMethod implements Serializable
{
    private String accountType;//支付类型 0银行卡快捷支付 ,1(余额)，2，3，4，5支付账号类型 9999:支付宝;10000:添加新支付

    private String typeName;//支付名称

    private String sortCode;//银行卡关联码(快捷支付)

    private double limitSingle;//单笔额度

    private double limitDay;//当日可用额度(快捷支付)

    private double limitMonth;//当月可用额度(快捷支付)

    private String logo;//银行logo(快捷支付)

    private Long bankId;

    private String bankName;

    private String bankNo;//卡号

    private String name;

    private String sortNo;

    private String cardNo;//卡号

    private String reservedMobile;//持卡人手机

    private Integer cardType;//银行卡类型 1、借记卡 2信用卡

    private String account;

    private String amount;

    private  int accountLogo;

    /**
     * 平台账户时为空，银行卡时显示编码（浦发为spdb）
     */
    private String bankCode;

    private boolean selected =false;

    private String payMsg;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getSortCode()
    {
        return sortCode;
    }

    public void setSortCode(String sortCode)
    {
        this.sortCode = sortCode;
    }

    public double getLimitSingle()
    {
        return limitSingle;
    }

    public void setLimitSingle(double limitSingle)
    {
        this.limitSingle = limitSingle;
    }

    public double getLimitDay()
    {
        return limitDay;
    }

    public void setLimitDay(double limitDay)
    {
        this.limitDay = limitDay;
    }

    public double getLimitMonth()
    {
        return limitMonth;
    }

    public void setLimitMonth(double limitMonth)
    {
        this.limitMonth = limitMonth;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSortNo()
    {
        return sortNo;
    }

    public void setSortNo(String sortNo)
    {
        this.sortNo = sortNo;
    }

    public String getBankCode()
    {
        return bankCode;
    }

    public void setBankCode(String bankCode)
    {
        this.bankCode = bankCode;
    }

    public Long getBankId()
    {
        return bankId;
    }

    public void setBankId(Long bankId)
    {
        this.bankId = bankId;
    }

    public String getBankNo()
    {
        return bankNo;
    }

    public void setBankNo(String bankNo)
    {
        this.bankNo = bankNo;
    }

    public String getReservedMobile()
    {
        return reservedMobile;
    }

    public void setReservedMobile(String reservedMobile)
    {
        this.reservedMobile = reservedMobile;
    }

    public Integer getCardType()
    {
        return cardType;
    }

    public void setCardType(Integer cardType)
    {
        this.cardType = cardType;
    }

    public String getCardNo()
    {
        return cardNo;
    }

    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }

    public void setAccountLogo(int accountLogo)
    {
        this.accountLogo = accountLogo;
    }

    public int getAccountLogo()
    {
        return accountLogo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayMsg()
    {
        return payMsg;
    }

    public void setPayMsg(String payMsg)
    {
        this.payMsg = payMsg;
    }
}
