package com.yzb.card.king.bean.ticket;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：机票订单账单基本类
 * 作  者：Li Yubing
 * 日  期：2016/11/19
 * 描  述：用来统计、管理退票、改签机票
 */
public class TicketOrderBillsBean implements Serializable {

    /**
     * 退票金额（退还给用户的金额）
     */
    private String refundAmount;

    /**
     * 退票手续费（收取用户的手续费）
     */
    private String refundFee;

    /**
     * 退票信息
     */
    private List<RefundBean>  refundList;


    public String getRefundAmount()
    {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount)
    {
        this.refundAmount = refundAmount;
    }

    public String getRefundFee()
    {
        return refundFee;
    }

    public void setRefundFee(String refundFee)
    {
        this.refundFee = refundFee;
    }

    public List<RefundBean> getRefundList()
    {
        return refundList;
    }

    public void setRefundList(List<RefundBean> refundList)
    {
        this.refundList = refundList;
    }
}
