package com.yzb.card.king.bean.hotel;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/18
 * 描  述：
 */
public class GoodsPlusParam {
    /**
     * 加购项id
     */
    private String goodsid;

    /**
     * 购买数量
     */
    public int goodsQuantity;

    public String getGoodsid()
    {
        return goodsid;
    }

    public void setGoodsid(String goodsid)
    {
        this.goodsid = goodsid;
    }

    public int getGoodsQuantity()
    {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity)
    {
        this.goodsQuantity = goodsQuantity;
    }

}
