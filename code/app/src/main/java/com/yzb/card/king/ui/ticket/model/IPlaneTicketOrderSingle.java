package com.yzb.card.king.ui.ticket.model;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/5
 * 描  述：
 */
public interface IPlaneTicketOrderSingle {

    /**
     * 保险列表识别码
     */
    public final static int BXLIST_CODE=1;

    /**
     * 退改签规则识别码
     */
    public final static int ROLETICKET_CODE=2;

    /**
     * 默认地址识别码
     */
    public final static int DEFAULTADDRESS_CODE=3;

    /**
     * 默认发票抬头
     */
    public final static int DEFAULTDEBIT_CODE=4;

    /**
     * 保险列表
     * @param params
     */
    void getBxList(Map<String, Object> params);

    /**
     * 退改签规则
     */
    void getRoleTicket();

    /**
     * 获取默认收货地址
     */
    void getDefaultAddress();

    /**
     * 获取默认发票抬头
     */
    void getDefaultDebit();
}
