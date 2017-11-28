package com.yzb.card.king.bean.ticket;

import java.util.List;

/**
 * 功能：每个商家的优惠活动；
 *
 * @author:gengqiyun
 * @date: 2016/10/16
 */
public class ShopGoodsActivityBean
{
    private List<GoodActivityBean> goodActivityBeans;

    public ShopGoodsActivityBean(List<GoodActivityBean> goodActivityBeans)
    {
        this.goodActivityBeans = goodActivityBeans;
    }

    public List<GoodActivityBean> getGoodActivityBeans()
    {
        return goodActivityBeans;
    }

    public void setGoodActivityBeans(List<GoodActivityBean> goodActivityBeans)
    {
        this.goodActivityBeans = goodActivityBeans;
    }
}
