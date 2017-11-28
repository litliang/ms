package com.yzb.card.king.bean.ticket;

/**
 * 功能：机票改签  更改条件；
 *
 * @author:gengqiyun
 * @date: 2016/12/3
 */
public class RescheCondition
{
    private String timeIntro;
    private float price;
    private boolean isSelect;

    public boolean isSelect()
    {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect)
    {
        this.isSelect = isSelect;
    }

    public String getTimeIntro()
    {
        return timeIntro;
    }

    public void setTimeIntro(String timeIntro)
    {
        this.timeIntro = timeIntro;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }
}
