package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/13 17:00
 * 描述：
 */
public class Goods implements Serializable {
    public String storeId;
    public float goodsPrice;
    public String goodsName;
    public String goodsId;
    public String goodsPhoto;
    public String specName;//规格名称
    public Specification specification;//选择的规格
    public List<Specification> specList;
    public int monthSales;//月销售
    public int stockNum;//库存
    public List<Active> actList;//商品活动
    public String goodsDesc;//介绍
    public String storeName;

    public Goods() {
    }

    public Goods(float price, String name, String id, List<Specification> specifications, int stockNum) {
        this.goodsPrice = price;
        this.goodsName = name;
        this.goodsId = id;
        this.specList = specifications;
        this.stockNum = stockNum;
    }
}
