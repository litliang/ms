package com.yzb.card.king.ui.travel.bean;

/**
 * Created by gengqiyun on 2016/4/27.
 * 旅游实体；
 * "travelAgency": "携程国旅",
 * "travelImage": "2016052616185816050395",
 * "vote": 9.0,
 * "fromPlace": "合肥",
 * "travelDate": "1/1,1/8,1/15",
 * "travelId": 1,
 * "travelNum": 100,
 * "type": "国内游",
 * "travelName": "厦门永定洪坑土楼动车 4日游",
 * "bgnPrice": 1000
 */
public class TravelBean {
    public String travelAgency;
    public String productType; //产品类型；
    public String travelImage;
    public String vote;
    public String fromPlace;
    public String travelDate;

    public String travelId;
    public String travelNum;
    public String type;

    public String travelName;
    public String bgnPrice;

    public boolean isEmptyBean; //是否是空bean，只在客户端使用；

    public TravelBean() {
    }

    public TravelBean(boolean isEmptyBean) {
        this.isEmptyBean = isEmptyBean;
    }
}
