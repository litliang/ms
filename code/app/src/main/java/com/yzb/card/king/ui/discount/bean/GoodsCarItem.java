package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/14 10:15
 * 描述：
 */
public class GoodsCarItem implements Serializable{
    public Goods goods;
    public Specification specification;//选择的规格
    public int goodsNum;

    public GoodsCarItem() {
    }

    public GoodsCarItem(Goods goods, int goodsNum, Specification specification) {
        this.goods = goods;
        this.goodsNum = goodsNum;
        this.specification = specification;
    }
}
