package com.yzb.card.king.bean.common;

import java.io.Serializable;

/**
 * 类  名：码尚付 二维码
 * 作  者：Li Yubing
 * 日  期：2017/1/20
 * 描  述：
 */
public class QrPayBean implements Serializable{
    /**
     * 收付方向  付款为-1，收款为
     */
    private String flag;
    /**
     * 金额
     */
    private String amount;
    /**
     * 生成时间
     */
    private String createTime;
    /**
     * 生成方账户
     */
    private String provideAccount;
    /**
     * 账户类型
     */
    private String customerType;
    /**
     * 收付款方式
     */
    private  String payType;
    /**
     * 网络信息
     */
    private boolean netStatus;

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getProvideAccount()
    {
        return provideAccount;
    }

    public void setProvideAccount(String provideAccount)
    {
        this.provideAccount = provideAccount;
    }


    public String getCustomerType()
    {
        return customerType;
    }

    public void setCustomerType(String customerType)
    {
        this.customerType = customerType;
    }

    public String getPayType()
    {
        return payType;
    }

    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public boolean isNetStatus()
    {
        return netStatus;
    }

    public void setNetStatus(boolean netStatus)
    {
        this.netStatus = netStatus;
    }
}
