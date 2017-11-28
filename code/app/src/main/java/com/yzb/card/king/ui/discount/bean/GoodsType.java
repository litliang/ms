package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/15 17:46
 * 描述：
 */
public class GoodsType implements Serializable {
    public String goodsTypeId;
    public String typeName;

    public GoodsType() {
    }

    public GoodsType(String goodsTypeId, String typeName) {
        this.goodsTypeId = goodsTypeId;
        this.typeName = typeName;
    }
}
