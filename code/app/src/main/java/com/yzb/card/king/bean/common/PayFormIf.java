package com.yzb.card.king.bean.common;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/5
 * 描  述： 支付方式 、取消方式常量
 */
public interface PayFormIf {
    /**
     * 到店付款
     */
    public static final int PAYMENT_TYPE_DAODIANFU = 1;
    /**
     * 在线付
     */
    public static final int PAYMENT_TYPE_ONLINE = 2;
    /**
     * 担保付
     */
    public static final int PAYMENT_TYPE_DANBAO = 3;
    /**
     * 免费取消
     */
    public static final int CANCEL_TYPE_FREE = 1;
    /**
     * 限时取消
     */
    public static final int  CANCEL_TYPE_TIMELIMIT=2;
    /**
     * 付费取消
     */
    public static final int CANCEL_TYPE_PAID = 3;
}
