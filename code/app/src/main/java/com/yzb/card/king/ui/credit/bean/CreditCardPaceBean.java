package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/28 10:17
 * 描述：
 */
public class CreditCardPaceBean implements Serializable{
    private long id;
    private String name;
    private String photo;
    //办卡进度url
    private String urlSchedule;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public String getUrlSchedule()
    {
        return urlSchedule;
    }

    public void setUrlSchedule(String urlSchedule)
    {
        this.urlSchedule = urlSchedule;
    }
}
