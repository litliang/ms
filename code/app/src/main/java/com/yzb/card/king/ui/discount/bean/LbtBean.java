package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * Created by gengqiyun on 2016/5/9.
 * 轮播图bean；
 * <p/>
 * "id": 1,
 * "cityId": 1116,
 * "pic": "2016050909570916050421",
 * "url": "http://www.baidu.com",
 * "createTime": 1451368232000,
 * "invalidTime": 1475214635000,
 * "status": "1",
 * "typeId": 1
 */
public class LbtBean implements Serializable
{
    public String id;
    public String cityId;

    public String adId;

    public String pageCode;

    public String picCode;

    public String pic;
    public String url;
    public String createTime;
    public String invalidTime;

    public String status;
    public String typeId;
    public String actionIds;
    public String h5Detail;

    public String title;

    public LbtBean()
    {
    }

    public LbtBean(String url)
    {
        this.picCode = url;
    }
}
