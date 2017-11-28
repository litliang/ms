package com.yzb.card.king.ui.travel.bean;

import java.io.Serializable;

/**
 * Created by injun on 2016/6/24.
 */
public class DiscountRecommendBean implements Serializable {


    /**
     * bgColor : 222
     * shopCount : 2
     * imageCode : 2016062315193816060617
     * sort : 3
     * categoryName : 奢饰品
     * categoryId : 2
     * url : www.baidu.com
     */

    private String bgColor;
    private String shopCount;
    private String imageCode;
    private int sort;
    private String categoryName;
    private int categoryId;
    private String url;

    private int imageCodeId;

    public int getImageCodeId()
    {
        return imageCodeId;
    }

    public void setImageCodeId(int imageCodeId)
    {
        this.imageCodeId = imageCodeId;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getShopCount() {
        return shopCount;
    }

    public void setShopCount(String shopCount) {
        this.shopCount = shopCount;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
