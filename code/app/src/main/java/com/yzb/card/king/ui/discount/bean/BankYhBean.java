package com.yzb.card.king.ui.discount.bean;

import java.util.List;

/**
 * Created by gengqiyun on 2016/4/27.
 * 银行卡优惠实体；
 */
public class BankYhBean {
    public String id;
    public int bankId;
    public String detail;
    public String discount;
    public String category;
    public String specialPrice;
    public String otherTitle;
    public String keyWords;
    public String features;
    public String endTime;// 结束时间；
    public String createTime;// 开始时间；
    public int discountCount;// 今日优惠数量；>0时有优惠；
    public String status;
    public String photo;
    public String fileName;
    public String code;
    public String name;
    public String address;
    public String type;
    public String level;
    public String tel;
    public String bankName;
    public String storeName;
    public String storeAddr;
    public String storeTel;
    public String enjoyNum;
    public List<ActivityBankStoreList> activityBankStore;

    public class ActivityBankStoreList {

        public String id;
        public int activityId;
        public String shopId;
        public String storeId;
        public String title;
        public String category;
        public String discount;
        public String specialPrice;
        public String endTime;
        public String createTime;

        public String status;
        public String name;
        public String address;
        public String type;
        public String storeName;
        public String storeAddr;
        public String storeTel;

        public String cityId;
        public String cityName;
    }
}
