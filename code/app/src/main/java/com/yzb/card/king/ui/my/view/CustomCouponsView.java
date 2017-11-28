package com.yzb.card.king.ui.my.view;

import com.yzb.card.king.bean.my.CouponsHomeBean;

import java.util.List;

/**
 * 功能：客户优惠券列表
 *
 * @author:gengqiyun
 * @date: 2017/1/10
 */
public interface CustomCouponsView
{
    void onGetCouponsSuc(boolean event_tag, List<CouponsHomeBean> obj);

    void onGetCouponsFail(String failMsg);
}
