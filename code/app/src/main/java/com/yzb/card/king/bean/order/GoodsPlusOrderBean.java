package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/25
 * 描  述：
 */
public class GoodsPlusOrderBean implements Serializable{
    /**
     * 加购项名称
     */
    private String goodsName;
    /**
     * 购买数量
     */
    private int goodsQuantity;
    /**
     * 购买金额
     */
    private int goodsAmount;

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

    public int getGoodsAmount()
    {
        return goodsAmount;
    }

    public void setGoodsAmount(int goodsAmount)
    {
        this.goodsAmount = goodsAmount;
    }
}
