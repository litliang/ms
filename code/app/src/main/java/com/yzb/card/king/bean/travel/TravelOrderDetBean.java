package com.yzb.card.king.bean.travel;

/**
 * 功能：旅游订单详情；
 *
 * @author:gengqiyun
 * @date: 2016/11/28
 */
public class TravelOrderDetBean
{
    private String title;
    private String details;
    private String bedIntro; //儿童，占床说明；
    private String price; //价格；


    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public String getBedIntro()
    {
        return bedIntro;
    }

    public void setBedIntro(String bedIntro)
    {
        this.bedIntro = bedIntro;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }
}
