package com.yzb.card.king.bean;


import com.yzb.card.king.ui.discount.bean.Active;

import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/8/22 16:48
 * 描述：
 */
public class SearchResultItem {
    public String id;//商铺id
    public String parentType;
    public String pic;
    public float vote;
    public int SaleNum;
    public float price;
    public long latitude;
    public long longitude;
    public List<Active> activityList;

}
