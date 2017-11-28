package com.yzb.card.king.ui.bonus.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 类 名： 红包领取记录
 * 作 者： gaolei
 * 日 期：2017年01月04日
 * 描 述：红包领取记录
 */

public class RedBagRecOrSendBean implements Serializable
{
    private int noReceiveAmount;
    private String blessWord;
    private int orderId;

    private String orderNo; //订单号；
    private String orderStatusDesc;
    private String orderStatus;
    private int receiveQuantity;
    private double receiveAmount;
    private String orderAmount;
    private String orderTime;
    private int totalQuantity;
    private String issueImageCode;
    private String issuePerson;
    private String issuePersonPhoto;
    private String closeImageCode;
    private String openImageCode;
    private List<ReceiveListBean> receiveList;

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



    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getIssuePersonPhoto()
    {
        return issuePersonPhoto;
    }

    public void setIssuePersonPhoto(String issuePersonPhoto)
    {
        this.issuePersonPhoto = issuePersonPhoto;
    }

    public int getNoReceiveAmount()
    {
        return noReceiveAmount;
    }

    public void setNoReceiveAmount(int noReceiveAmount)
    {
        this.noReceiveAmount = noReceiveAmount;
    }

    public String getBlessWord()
    {
        return blessWord;
    }

    public void setBlessWord(String blessWord)
    {
        this.blessWord = blessWord;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderStatusDesc()
    {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc)
    {
        this.orderStatusDesc = orderStatusDesc;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public int getReceiveQuantity()
    {
        return receiveQuantity;
    }

    public void setReceiveQuantity(int receiveQuantity)
    {
        this.receiveQuantity = receiveQuantity;
    }

    public double getReceiveAmount()
    {
        return receiveAmount;
    }

    public void setReceiveAmount(double receiveAmount)
    {
        this.receiveAmount = receiveAmount;
    }

    public String getOrderAmount()
    {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount)
    {
        this.orderAmount = orderAmount;
    }

    public String getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(String orderTime)
    {
        this.orderTime = orderTime;
    }

    public int getTotalQuantity()
    {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity)
    {
        this.totalQuantity = totalQuantity;
    }

    public String getIssueImageCode()
    {
        return issueImageCode;
    }

    public void setIssueImageCode(String issueImageCode)
    {
        this.issueImageCode = issueImageCode;
    }

    public String getIssuePerson()
    {
        return issuePerson;
    }

    public void setIssuePerson(String issuePerson)
    {
        this.issuePerson = issuePerson;
    }

    public List<ReceiveListBean> getReceiveList()
    {
        return receiveList;
    }

    public void setReceiveList(List<ReceiveListBean> receiveList)
    {
        this.receiveList = receiveList;
    }

    public static class ReceiveListBean
    {

        /**
         * 领取人时间
         */
        private String receiveTime;
        /**
         * 领取金额
         */
        private double receiveAmount;
        /**
         * 领取人昵称
         */
        private String receivePerson;
        /**
         * 领取人头像
         */
        private String receivePicUrl;

        public String getReceiveTime()
        {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime)
        {
            this.receiveTime = receiveTime;
        }

        public double getReceiveAmount()
        {
            return receiveAmount;
        }

        public void setReceiveAmount(double receiveAmount)
        {
            this.receiveAmount = receiveAmount;
        }

        public String getReceivePerson()
        {
            return receivePerson;
        }

        public void setReceivePerson(String receivePerson)
        {
            this.receivePerson = receivePerson;
        }

        public String getReceivePicUrl()
        {
            return receivePicUrl;
        }

        public void setReceivePicUrl(String receivePicUrl)
        {
            this.receivePicUrl = receivePicUrl;
        }
    }
}
