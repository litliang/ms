package com.yzb.card.king.ui.discount.model;

/**
 * 类  名：首页商户优惠接口
 * 作  者：Li Yubing
 * 日  期：2016/8/16
 * 描  述：
 */
public interface DiscountImpl {

    /**
     * 发送优惠信息请求方法
     * @param cityId
     */
    public void sendPrivilegetInfoRequest(String cityId);

    /**
     * 发送个人频道请求
     */
    public void sendCustomerChannelListRequest();

    /**
     * 发送获取优惠卷请求
     *
     */
    public void sendCouponRequest();

    /**
     * 发送优惠推荐请求
     */
    public void sendRrivilegeRecommendRequest(String cityId, int pageStart);

}
