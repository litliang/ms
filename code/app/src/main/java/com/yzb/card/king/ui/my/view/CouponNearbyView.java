package com.yzb.card.king.ui.my.view;

import com.yzb.card.king.bean.my.CouponNearbyBean;

/**
 * 功能：优惠券附近筛选条件；
 *
 * @author:gengqiyun
 * @date: 2017/1/17
 */
public interface CouponNearbyView
{
    void onGetCouponNearbySuc(CouponNearbyBean data);

    void onGetCouponNearbyFail(String failMsg);
}
