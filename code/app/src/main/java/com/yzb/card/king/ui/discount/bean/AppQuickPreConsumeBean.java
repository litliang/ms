package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * 类  名：他行验证码解析类
 * 作  者：Li Yubing
 * 日  期：2017/2/16
 * 描  述：
 */
public class AppQuickPreConsumeBean implements Serializable{
    /**
     *
     */
    private String customerId;
    /**
     *
     */
    private String token;

    public String getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
