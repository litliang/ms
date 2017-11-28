package com.yzb.card.king.bean.common;

import com.yzb.card.king.ui.discount.bean.ChildTypeBean;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/2/20
 * 描  述：
 */
public class ActivityMessageEvent implements Serializable{
    /**
     * activity名称
     */
    private String activityName;

    private ChildTypeBean childTypeBean;


    public ChildTypeBean getChildTypeBean()
    {
        return childTypeBean;
    }

    public void setChildTypeBean(ChildTypeBean childTypeBean)
    {
        this.childTypeBean = childTypeBean;
    }

    public String getActivityName()
    {
        return activityName;
    }

    public void setActivityName(String activityName)
    {
        this.activityName = activityName;
    }
}
