package com.yzb.card.king.ui.gift.bean;

/**
 * 类 名： 礼品卡产品信息类
 * 作 者： gaolei
 * 日 期：2017年02月07日
 * 描 述：记录实体卡、心意卡的基本信息
 */

public class GiftCardProductBean {
    /**
     * 产品id
     */
    private int productId;
    /**
     * 祝福语
     */
    private String blessWord;
    /**
     * 分类名称
     */
    private String typeName;
    /**
     * 卡样图
     */
    private String imageCode;
    /**
     * 订购数量
     */
    private int orderQuantity;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getBlessWord() {
        return blessWord;
    }

    public void setBlessWord(String blessWord) {
        this.blessWord = blessWord;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }


    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }


}
