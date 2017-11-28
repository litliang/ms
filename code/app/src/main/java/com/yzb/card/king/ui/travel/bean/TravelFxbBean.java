package com.yzb.card.king.ui.travel.bean;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/23
 * 描  述：
 */
public class TravelFxbBean implements Serializable {


    /**
     * productId : 10001
     * bgnPrice : 11100.0
     * imageCode : 2016111509195016111016
     * weight : 2150
     * productName : 自由乐园游+香港+澳门6月5日跟团海洋公园+全天迪士尼
     * createDate : 2016-11-22
     */

    private int productId;
    private double bgnPrice;
    private String productImageUrl;
    private String productName;

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public double getBgnPrice()
    {
        return bgnPrice;
    }

    public void setBgnPrice(double bgnPrice)
    {
        this.bgnPrice = bgnPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

}
