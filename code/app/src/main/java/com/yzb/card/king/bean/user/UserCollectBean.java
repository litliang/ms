package com.yzb.card.king.bean.user;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/6/23
 * 描  述：
 */
public class UserCollectBean implements Serializable {

    private String storeName;
    private String vote;
    private String shopLogo;
    private double avgPrice;
    private int storeId;

    private String type;
    private String category;
    private String introduction;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private int collectionsId;


    public int getCollectionsId() {
        return collectionsId;
    }

    public void setCollectionsId(int collectionsId) {
        this.collectionsId = collectionsId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }


}
