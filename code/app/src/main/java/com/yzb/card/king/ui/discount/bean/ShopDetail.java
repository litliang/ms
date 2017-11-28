package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/15 17:47
 * 描述：
 */
public class ShopDetail implements Serializable{
    public String storeId;
    public String sotreName;
    public boolean collectStatus;
    public List<Active> actList;
    public List<GoodsType> typeList;
}
