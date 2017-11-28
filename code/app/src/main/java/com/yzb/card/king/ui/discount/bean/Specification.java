package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/15 10:59
 * 描述：
 */
public class Specification implements Serializable {
    public String specName;
    public float specPrice;
    public String specId;
    public int stockNum;
    public Specification() {
    }

    public Specification(String name, float price, String id) {
        this.specName = name;
        this.specPrice = price;
        this.specId = id;
    }
}
