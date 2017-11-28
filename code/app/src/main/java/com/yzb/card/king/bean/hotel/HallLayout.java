package com.yzb.card.king.bean.hotel;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/5 18:30
 */
public class HallLayout implements Serializable
{
    private String imgUrl;
    private String style;

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public String getStyle()
    {
        return style;
    }

    public void setStyle(String style)
    {
        this.style = style;
    }
}
