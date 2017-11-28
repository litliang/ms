package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/7 18:52
 * 描述：
 */
public class DiscountInfo implements Serializable {
    public String imageCode;
    public String bankId;
    public String bankName;
    public Date startDate;
    public Date endDate;
    public String activityCityId;
    public String activityCityName;
    public String activityName;
    public String activityType;
    public String activityId;
    public int praiseCount;
    public String features;//活动特色
    public boolean praiseStatus;
    public String storeId;
    public String detail;//活动说明
}
