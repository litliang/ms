package com.yzb.card.king.bean.my;

/**
 * 功能：红包首页item数据源；
 *
 * @author:gengqiyun
 * @date: 2016/12/21
 */
public class CouponChannelBean
{
    private int imgResId;

    private String text;

    private boolean showNumber = false;

    private int numberValue;

    public int getNumberValue()
    {
        return numberValue;
    }

    public void setNumberValue(int numberValue)
    {
        this.numberValue = numberValue;
    }

    public boolean isShowNumber()
    {
        return showNumber;
    }

    public void setShowNumber(boolean showNumber)
    {
        this.showNumber = showNumber;
    }

    public int getImgResId()
    {
        return imgResId;
    }

    public void setImgResId(int imgResId)
    {
        this.imgResId = imgResId;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
