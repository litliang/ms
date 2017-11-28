package com.yzb.card.king.ui.bonus.bean;

import java.io.Serializable;

/**
 * 功能：红包；
 *
 * @author:gengqiyun
 * @date: 2016/12/30
 */
public class BounsBean implements Serializable
{
    private String orderId;// Long	订单id	N
    private String themeName;//	String	主题名称	N
    private String receiveImageCode;    //String	接收方图片	N
    private String issueImageCode;
    private String issuePerson; //	String	发送方昵称	Y
    private String closeImageCode;
    private String openImageCode;

    public String getOrderId()
    {
        return orderId;
    }

    public String getCloseImageCode()
    {
        return closeImageCode;
    }

    public void setCloseImageCode(String closeImageCode)
    {
        this.closeImageCode = closeImageCode;
    }

    public String getOpenImageCode()
    {
        return openImageCode;
    }

    public void setOpenImageCode(String openImageCode)
    {
        this.openImageCode = openImageCode;
    }

    public String getIssueImageCode()
    {
        return issueImageCode;
    }

    public void setIssueImageCode(String issueImageCode)
    {
        this.issueImageCode = issueImageCode;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getThemeName()
    {
        return themeName;
    }

    public void setThemeName(String themeName)
    {
        this.themeName = themeName;
    }

    public String getReceiveImageCode()
    {
        return receiveImageCode;
    }

    public void setReceiveImageCode(String receiveImageCode)
    {
        this.receiveImageCode = receiveImageCode;
    }

    public String getIssuePerson()
    {
        return issuePerson;
    }

    public void setIssuePerson(String issuePerson)
    {
        this.issuePerson = issuePerson;
    }
}
