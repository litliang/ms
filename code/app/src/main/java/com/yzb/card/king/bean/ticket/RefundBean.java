package com.yzb.card.king.bean.ticket;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/11/19
 * 描  述：
 */
public class RefundBean implements Serializable{

    /**
     * 大订单号
     */
    private String orderNo;
    /**
     * 退款人员id（要退的成人乘机人id，多个以逗号分隔，形式为：paxId1,paxId2...）
     */
    private String paxIdsAdt;
    /**
     * 要退的儿童乘机人id，多个以逗号分隔，形式为：paxId1,paxId2...
     */
    private String paxIdsChd;
    /**
     * 要退的婴儿乘机人id，多个以逗号分隔，形式为：paxId1,paxId2...
     */
    private String paxIdsInf;

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getPaxIdsAdt()
    {
        return paxIdsAdt;
    }

    public void setPaxIdsAdt(String paxIdsAdt)
    {
        this.paxIdsAdt = paxIdsAdt;
    }

    public String getPaxIdsChd()
    {
        return paxIdsChd;
    }

    public void setPaxIdsChd(String paxIdsChd)
    {
        this.paxIdsChd = paxIdsChd;
    }

    public String getPaxIdsInf()
    {
        return paxIdsInf;
    }

    public void setPaxIdsInf(String paxIdsInf)
    {
        this.paxIdsInf = paxIdsInf;
    }
}
