package com.yzb.card.king.bean.common;

import java.io.Serializable;

/**
 * 类  名：保险
 * 作  者：Li Yubing
 * 日  期：2017/8/25
 * 描  述：
 */
public class InsuranceBean implements Serializable{
    /**
     * 保险名称
     */
    private  String goodsName;
    /**
     * 购买数量
     */
    private int goodsQuantity;
    /**
     * 购买金额
     */
    private  float goodsAmount;


    public String getGoodsName()
    {
        return goodsName;
    }

    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
    }

    public int getGoodsQuantity()
    {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity)
    {
        this.goodsQuantity = goodsQuantity;
    }

    public float getGoodsAmount()
    {
        return goodsAmount;
    }

    public void setGoodsAmount(float goodsAmount)
    {
        this.goodsAmount = goodsAmount;
    }
}
