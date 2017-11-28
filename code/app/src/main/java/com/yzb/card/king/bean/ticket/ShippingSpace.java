package com.yzb.card.king.bean.ticket;

import java.io.Serializable;

/**
 * 描述：仓位
 * 作者：殷曙光
 * 日期：2016/10/10 15:20
 */
public class ShippingSpace implements Serializable {

    private String qabinName;

    public String getQabinCode()
    {
        return qabinCode;
    }

    public void setQabinCode(String qabinCode)
    {
        this.qabinCode = qabinCode;
    }

    public String getQabinName()
    {
        return qabinName;
    }

    public void setQabinName(String qabinName)
    {
        this.qabinName = qabinName;
    }

    private String qabinCode;

    public ShippingSpace()
    {
    }

    public ShippingSpace(String shippingId, String shippingName)
    {
        this.qabinCode = shippingId;
        this.qabinName = shippingName;
    }

}
