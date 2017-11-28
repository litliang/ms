package com.yzb.card.king.ui.travel.bean;

import java.util.List;

/**
 * Created by gengqiyun on 2016/4/27.
 * 旅游详情实体；
 */
public class TravelDetailBean {
    public String travelId;
    public String travelName;
    public String travelImage;
    public String travelDate;
    public String travelNum;
    public String travelAgency;
    public String summary;
    public String storeId; //商家id；
    public int collectCount;
    public int eatNum;
    public String trafficImage;
    public double lat;  //纬度；
    public double lng;  //经度；
    public String storeName;
    public String cityId; //商家的cityId；
    public String spotName;
    public float vote;
    public String fromPlace;
    public String collectionStatus; // 收藏状态；

    public String storeTel;
    public String eatType;
    public String classicSpotNum;
    public String trafficType;
    public String storeAddr;
    public String storeImage;
    public String url; //行程详情url
    public String toPlace;
    public float trafficTime;
    public String spotNum;
    public int voteCount; // 评价的数量
    public String hotel;
    public String imageCodes;
    public String shopIntro; //商户信息；
    public String bgnPrice;
    public String intro;//行程介绍url；

    public List<SevenDataBean> sevenData;

    public List<DayScheduleBean> daySchedule;

    public static class SevenDataBean {
        public String lineId; //线路id；
        public float price; //价格；
        public String date; //出发日期；
        public int tickets; //库存；
        public String file;//行程详情url；
    }

    public static class DayScheduleBean {
        public String days;
        public String travel;
        public List<TimeScheduleBean> timeSchedule;

        public static class TimeScheduleBean {
            public String content;
            public String schedule;
            public String time;
            public String trafficImage;
        }
    }
}
