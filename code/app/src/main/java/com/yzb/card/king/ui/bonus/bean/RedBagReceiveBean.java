package com.yzb.card.king.ui.bonus.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类 名： 收红包记录
 * 作 者： gaolei
 * 日 期：2017年01月04日
 * 描 述：收红包记录
 */
public class RedBagReceiveBean implements Serializable{

    /**
     * 订单id
     */
    private int orderId;



    /**
     * 主题名称
     */
    private String themeName;
    /**
     * 未拆开图片
     */
    private String closeImageCode;
    /**
     * 拆开图片
     */
    private String openImageCode;
    /**
     * 发送人头像
     */
    private String issuePersonPhoto;
    /**
     * 发送人昵称
     */
    private String issuePerson;
    /**
     * 领取金额
     */
    private BigDecimal receiveAmount;
    /**
     * 领取人时间
     */
    private String receiveTime;
    /**
     * 是否领完
     */
    private String endStatus;


    public String getIssuePersonPhoto() {
        return issuePersonPhoto;
    }

    public void setIssuePersonPhoto(String issuePersonPhoto) {
        this.issuePersonPhoto = issuePersonPhoto;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
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

    public String getIssuePerson() {
        return issuePerson;
    }

    public void setIssuePerson(String issuePerson) {
        this.issuePerson = issuePerson;
    }

    public String getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(String endStatus) {
        this.endStatus = endStatus;
    }
}
