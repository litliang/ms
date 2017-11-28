package com.yzb.card.king.jpush.bean;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/1/19
 * 描  述：
 */
public class JpushLogicBean implements Serializable
{
    /**
     * 订单详情id
     */
    private String orderId;
    /**
     * 产品详情id
     */
    private String productId;
    /**
     * 链接url
     */
    private String url;

    /**
     * 领取记录id
     */
    private String recordId;
    /**
     * 领取id
     */
    private String codeNo;

    private String hotelType;


    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
    }

    public String getRecordId()
    {
        return recordId;
    }

    public String getCodeNo()
    {
        return codeNo;
    }

    public void setCodeNo(String codeNo)
    {
        this.codeNo = codeNo;
    }

    public void setRecordId(String recordId)
    {
        this.recordId = recordId;
    }

    public String getOrderId()
    {
        return orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
