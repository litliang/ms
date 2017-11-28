package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * Created by injun on 2016/6/23.
 * <p/>
 * 礼品卡实体类
 */
public class GiftCardBean implements Serializable {


    /**
     * cardImage : 2016041317461816040383
     * cardName : 生日卡
     * cardImageLittle : 2016041317461816040383
     * id : 5
     * cardDesc : 遥远处送来一份深深的祝福......
     * cardClass : 2
     */
    private String cardImage;
    private String cardName;
    private String cardImageLittle;
    private int id;
    private String cardDesc;
    private String cardClass;




    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardImageLittle() {
        return cardImageLittle;
    }

    public void setCardImageLittle(String cardImageLittle) {
        this.cardImageLittle = cardImageLittle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardDesc() {
        return cardDesc;
    }

    public void setCardDesc(String cardDesc) {
        this.cardDesc = cardDesc;
    }

    public String getCardClass() {
        return cardClass;
    }

    public void setCardClass(String cardClass) {
        this.cardClass = cardClass;
    }

    /**
     * 产品id
     */
    private long productId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 种类
     */
    private String category;
    /**
     * 种类描述
     */
    private String categoryDesc;
    /**
     * 分类id
     */
    private long typeId;
    /**
     * 分类名称
     */
    private String typeName;
    /**
     * 祝福语
     */
    private String blessWord;
    /**
     * 卡样图
     */
    private String imageCode;
    /**
     * 卡样小图
     */
    private String imageCodeLittle;
    /**
     * 订购数量
     */
    private String orderQuantity;

    public long getProductId()
    {
        return productId;
    }

    public void setProductId(long productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategoryDesc()
    {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc)
    {
        this.categoryDesc = categoryDesc;
    }

    public long getTypeId()
    {
        return typeId;
    }

    public void setTypeId(long typeId)
    {
        this.typeId = typeId;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getBlessWord()
    {
        return blessWord;
    }

    public void setBlessWord(String blessWord)
    {
        this.blessWord = blessWord;
    }

    public String getImageCode()
    {
        return imageCode;
    }

    public void setImageCode(String imageCode)
    {
        this.imageCode = imageCode;
    }

    public String getImageCodeLittle()
    {
        return imageCodeLittle;
    }

    public void setImageCodeLittle(String imageCodeLittle)
    {
        this.imageCodeLittle = imageCodeLittle;
    }

    public String getOrderQuantity()
    {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity)
    {
        this.orderQuantity = orderQuantity;
    }
}
