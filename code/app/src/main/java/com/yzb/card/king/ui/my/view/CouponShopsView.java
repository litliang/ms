package com.yzb.card.king.ui.my.view;

import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.bean.my.CouponShopBean;

import java.util.List;

/**
 * 功能：优惠券对应商家
 *
 * @author:gengqiyun
 * @date: 2017/1/12
 */
public interface CouponShopsView
{
    void onGetCouponShopsSuc(boolean event_tag, List<CouponInfoBean> list);

    void onGetCouponShopsFail(String failMsg);
}
