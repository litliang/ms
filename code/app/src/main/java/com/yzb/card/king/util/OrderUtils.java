package com.yzb.card.king.util;

import com.yzb.card.king.bean.order.OrderBean;

/**
 * Created by Timmy on 16/7/23.
 */
public class OrderUtils
{
    public static String getOrderStatus(int orderTyle)
    {
        //订单状态（1待付款；2已付款；3已取消；4删除；5退票；6改签；7退订；8已发货；9已收货；10已退货；11待发货；12待退款；13已结算；
        // 14已出票;15已支付；16交易失败；17已确认,18交易成功，19,购票失败）
        LogUtil.LCi("orderType=" + orderTyle);
        switch (orderTyle)
        {
            case OrderBean.ORDER_STATUS_CANCEL:  //已取消
                return "已取消";
            case OrderBean.ORDER_STATUS_NO_PAY:  //待支付
                return "待支付";
            case OrderBean.ORDER_STATUS_COMPLETE:  //已支付
                return "已支付";
            case OrderBean.ORDER_STATUS_YIWANCHENG:  //已完成
                return "已成交";
            case OrderBean.ORDER_STATUS_YITUIKUAN:  //已退款
                return "已退款";
            case OrderBean.ORDER_STATUS_YIQUEREN:  //已确认
                return "已确认";
            case OrderBean.ORDER_STATUS_QUERENSHIBAI:  //确认失败
                return "确认失败";
            case OrderBean.ORDER_STATUS_DAITUIKUAN:
                return "待退款";
            case OrderBean.ORDER_STATUS_DAIFANXIAN:
                return "待返现";
            case OrderBean.ORDER_STATUS_YIFANXIAN:
                return "已返现";

            case OrderBean.ORDER_STATUS_DAIQUEREN:
                return "待确认";

            case OrderBean.ORDER_STATUS_YIRUZHU:
                return "已入住";

            case OrderBean.ORDER_STATUS_YILIDIAN:
                return "已离店";

            case OrderBean.ORDER_STATUS_YUDINGWEIRUZHU:
                return "预订未入住";

            case OrderBean.ORDER_STATUS_YIGUOQI:
                return "已过期";
            case OrderBean.ORDER_OPERATESTATUS_DELETE:
                return "已删除";
            case OrderBean.ORDER_STATUS_TRADE_SUCESS:  //交易成功
                return "交易成功";
            case OrderBean.ORDER_STATUS_TRADE_FAIL:  //购票失败
                return "购票失败";
        }
        return "";
    }

}
