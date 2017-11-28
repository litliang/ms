package com.yzb.card.king.bean;

import java.io.Serializable;

/**
 * 类  名：超值加购
 * 作  者：Li Yubing
 * 日  期：2017/8/7
 * 描  述：
 */
public class OverbalanceGoodsBean implements Serializable {
    /**
     * 活动id
     */
    private long actId;
    /**
     * 活动名称
     */
    private String actName;

    public String goodsName;
    /**
     * 价格
     */
    public String price;
    /**
     * 规格
     */
    private  String unit;
    /**
     * 购买数量
     */
    public int number = 0;
    /**
     * 有效日期描述
     */
    private String effDateDesc;
    /**
     * 退款规则说明
     */
    private String cancelDesc;
    /**
     * 条款说明
     */
    private String intro;

    public long getActId()
    {
        return actId;
    }

    public void setActId(long actId)
    {
        this.actId = actId;
    }

    public String getActName()
    {
        return actName;
    }

    public void setActName(String actName)
    {
        this.actName = actName;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public String getEffDateDesc()
    {
        return effDateDesc;
    }

    public void setEffDateDesc(String effDateDesc)
    {
        this.effDateDesc = effDateDesc;
    }

    public String getCancelDesc()
    {
        return cancelDesc;
    }

    public void setCancelDesc(String cancelDesc)
    {
        this.cancelDesc = cancelDesc;
    }

    public String getIntro()
    {
        return intro;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }
}
