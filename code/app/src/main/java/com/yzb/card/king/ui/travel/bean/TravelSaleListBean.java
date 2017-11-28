package com.yzb.card.king.ui.travel.bean;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/23
 * 描  述：
 */
public class TravelSaleListBean implements Serializable {
    /**
     * industryId : 3
     * goodsId : 10001
     * price : 1100.0
     * inventoryQuantity : 8
     * goodsImageUrl : http://116.228.184.116/card/image/getImg/2017011311471817011243/0
     * shopId : 2
     * goodsName : 三亚5日4晚跟团游(5钻)·预售！180°豪 华海景房！纯玩口碑线！★★★★★
     */

    private String industryId;
    private String goodsId;
    private String price;
    private int inventoryQuantity;
    private String goodsImageUrl;
    private String shopId;
    private String goodsName;
    private String marketPrice;

    public String getMarketPrice()
    {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice)
    {
        this.marketPrice = marketPrice;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }


   /* *//**
     * productId : 10001
     * inventoryQuantity : 12
     * price : 11100.00
     * imageCode : 2016111509195016111016
     * productName : 自由乐园游+香港+澳门6月5日跟团海洋公园+全天迪士尼
     *//*

    private int productId;  //产品id
    private int inventoryQuantity; //库存量
    private String price;   //成人价
    private String imageCode; //产品图片
    private String productName; //产品名称

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public int getInventoryQuantity()
    {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity)
    {
        this.inventoryQuantity = inventoryQuantity;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getImageCode()
    {
        return imageCode;
    }

    public void setImageCode(String imageCode)
    {
        this.imageCode = imageCode;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }*/
}
