package com.yzb.card.king.ui.transport.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yinsg on 2016/5/30.
 */
public class ShipAgent implements Serializable
{
    public String seatName;
    public int price;
    public int allowance;

    public boolean isExpand;//是否展开，只在客户端使用；  false:合并；true:展开；
    public List<Supplier> supplierList;

    public class Supplier implements Serializable
    {
        public String supplierName;
        public int price;
        public int allowance;
    }
}
