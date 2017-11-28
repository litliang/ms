package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/29
 */

public class CreditCard implements Serializable
{
    private long id;
    //信用卡图片
    private String photo;
    //最后还款日yyyy-mm-dd
    private String lastRepaymentDate;
    //距离还款日
    private int repaymentDays;
    //账单日
    private String billDay;
    //持卡人
    private String userName;
    //尾号
    private String sortNo;
    //还款提醒(1开启 0关闭)
    private boolean remindStatus;
    //提醒日
    private String remindDay;
    //自动还款(1开启 0关闭)
    private boolean autoStatus;
    //自动还款金额(1全额 2最低)
    private String autoType;
    //
    private String bankName;
    private Long bankId;
    //银行logo
    private String logo;
    //卡号
    private String cardNo;
    //持卡人手机
    private String reservedMobile;

    //所属银行的手机，用于查询账单
    private String mobile;
    private boolean selected;

    private String bankMark;//用于提现

    private boolean payType;//true 可支付

    public boolean isPayType() {
        return payType;
    }

    public void setPayType(boolean payType) {
        this.payType = payType;
    }

    public String getBankMark()
    {
        return bankMark;
    }

    public void setBankMark(String bankMark)
    {
        this.bankMark = bankMark;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public String getLastRepaymentDate()
    {
        return lastRepaymentDate;
    }

    public void setLastRepaymentDate(String lastRepaymentDate)
    {
        this.lastRepaymentDate = lastRepaymentDate;
    }

    public int getRepaymentDays()
    {
        return repaymentDays;
    }

    public void setRepaymentDays(int repaymentDays)
    {
        this.repaymentDays = repaymentDays;
    }

    public String getBillDay()
    {
        return billDay;
    }

    public void setBillDay(String billDay)
    {
        this.billDay = billDay;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getSortNo()
    {
        return sortNo;
    }

    public void setSortNo(String sortNo)
    {
        this.sortNo = sortNo;
    }

    public boolean isRemindStatus()
    {
        return remindStatus;
    }

    public void setRemindStatus(boolean remindStatus)
    {
        this.remindStatus = remindStatus;
    }

    public String getRemindDay()
    {
        return remindDay;
    }

    public void setRemindDay(String remindDay)
    {
        this.remindDay = remindDay;
    }

    public boolean isAutoStatus()
    {
        return autoStatus;
    }

    public void setAutoStatus(boolean autoStatus)
    {
        this.autoStatus = autoStatus;
    }

    public String getAutoType()
    {
        return autoType;
    }

    public void setAutoType(String autoType)
    {
        this.autoType = autoType;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public Long getBankId()
    {
        return bankId;
    }

    public void setBankId(Long bankId)
    {
        this.bankId = bankId;
    }

    public String getCardNo()
    {
        return cardNo;
    }

    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }

    public String getReservedMobile()
    {
        return reservedMobile;
    }

    public void setReservedMobile(String reservedMobile)
    {
        this.reservedMobile = reservedMobile;
    }
}
