package com.yzb.card.king.jpush.bean;

import java.io.Serializable;

/**
 * 类 名： Jpush串口
 * 作 者： gaolei
 * 日 期：2017年01月17日
 * 描 述：Jpush串口
 */

public  class JpushNumBean implements Serializable{

    /**
     * targetActivity : 2001
     * activityData : &orderId=626
     */

    private String targetActivity;
    private String activityData;

    public String getTargetActivity() {
        return targetActivity;
    }

    public void setTargetActivity(String targetActivity) {
        this.targetActivity = targetActivity;
    }

    public String getActivityData()
    {
        return activityData;
    }

    public void setActivityData(String activityData)
    {
        this.activityData = activityData;
    }

}
