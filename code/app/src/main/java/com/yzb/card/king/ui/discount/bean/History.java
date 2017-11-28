package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/8/18 14:15
 * 描述：
 */
public class History implements Serializable {
    public String id;
    public String key;
    public String storeId;
    public String parentType;

    public History(String key, String storeId, String parentType) {
        this.key = key;
        this.storeId = storeId;
        this.parentType = parentType;
    }
}
