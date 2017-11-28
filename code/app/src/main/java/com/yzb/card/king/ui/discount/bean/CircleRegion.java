package com.yzb.card.king.ui.discount.bean;

import java.util.List;

/**
 * Created by gengqiyun on 2016/5/3.
 */
public class CircleRegion {
    public String id;
    public String circleName;
    public String cityId;
    public String regionId;
    public String airName;

    public String parentId;
    public String cityName;
    public String metroName;
    public String spotName; //热门地标名称；
    public String lat; //经度；
    public String lng; //纬度；
    public String cityLevel;
    public List<CircleRegion> circle;

    public String name; // 附近中第一列 的名称字段；
    public boolean isSelected; // 是否选中，客户端使用；

//    public String uuid; // bean的唯一标识；


}
