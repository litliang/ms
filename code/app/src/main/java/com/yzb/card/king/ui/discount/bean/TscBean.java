package com.yzb.card.king.ui.discount.bean;

import java.util.List;

/**
 * Created by gengqiyun on 2016/4/29.
 */
public class TscBean {
    public int id;
    public String cityId;
    public String dishName;
    public String dishImage;

    public String description;
    public String createTime;

    public List<StoreList> storeList;

    public static class StoreList {
        public int id;
        public String signId;
        public String storeId;//门店的id
        public String createTime;
        public String storeName;
        public double lat;
        public double lng;
        public String shopTypeId;
        public String typeName;
        public String parentId;
        public String grandParentId;
        public float vote; //评分；
        public String avgPrice; //价格；
    }

}
