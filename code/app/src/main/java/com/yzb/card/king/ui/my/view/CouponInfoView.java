package com.yzb.card.king.ui.my.view;

import com.yzb.card.king.bean.my.CouponInfoBean;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/10/20
 * 描  述：
 */
public interface CouponInfoView {

    void onGetCouponSuc(boolean event_tag, List<CouponInfoBean> list);

    void onGetCouponFail(String failMsg);
}
