package com.yzb.card.king.ui.discount.bean;

import java.util.List;

/**
 * Created by gengqiyun on 2016/5/17.
 * "filmName": "藏龙卧虎：青冥宝剑",
 * "remainEvents": 89,
 * "weight": 80,
 * "collectionCount": 80,
 * "vote": 6,
 * "cinemaCount": 32,
 * "activityList": [{
 * "timeLimit": 1,
 * "id": 1,
 * "otherTitle": "平日买一送一",
 * "bankId": "1"
 * }],
 * "lng": 21,
 * "imageCode": "2016051616174816050432",
 * "duration": 120,
 * "distance": "120m",
 * "price": 45,
 * "sales": 60,
 * "lat": 22,
 * "filmId": 1
 * },
 * 影片；
 */
public class YpBean {
    public String filmId;
    public String filmName;
    public String remainEvents;
    public String weight;
    public String collectionCount;
    public int vote;

    public String cinemaCount;

    public long lng;
    public long lat;
    public String imageCode;
    public String duration;

    public String distance;
    public String price;

    public List<ActivityList> activityList;

    public static class ActivityList {
        public String id;
        public int bankId;
        public String timeLimit;
        public String otherTitle;
    }
}
