package com.yzb.card.king.bean.shop;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/6/22
 * 描  述：
 */
public class ShopInfo {

    private String shopPicture;

    public void setShopPicture(String shopPicture) {
        this.shopPicture = shopPicture;
    }

    public String getShopPicture() {
        return shopPicture;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    private String shopTitle;

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public void setShopMoney(String shopMoney) {
        this.shopMoney = shopMoney;
    }

    public String getShopMoney() {
        return shopMoney;
    }

    private String shopContent;
    private String shopMoney;

    public void setShopContent(String shopContent) {
        this.shopContent = shopContent;
    }

    public String getShopContent() {
        return shopContent;

    }
}
