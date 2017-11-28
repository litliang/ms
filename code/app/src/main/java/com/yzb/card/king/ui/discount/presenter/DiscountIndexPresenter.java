package com.yzb.card.king.ui.discount.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.model.DiscountDao;
import com.yzb.card.king.ui.discount.model.DiscountImpl;

/**
 * 类  名：商户优惠观察者
 * 作  者：Li Yubing
 * 日  期：2016/8/11
 * 描  述：
 */
public class DiscountIndexPresenter implements DiscountCallBack
{

    private Fragment fragment;

    private BaseViewLayerInterface view;

    private DiscountImpl impl;

    public DiscountIndexPresenter(BaseViewLayerInterface view, Fragment fragment)
    {

        this.fragment = fragment;

        this.view = view;

        impl = new DiscountDao(this);
    }


    /**
     * 获取个人频道列表；
     * 修改人：gengqiyun
     *
     * @date:2016.8.11
     */
    public void getUserChannel(final Context context)
    {
        impl.sendCustomerChannelListRequest();
    }


    /**
     * 获取优惠券
     */
    public void getCouponData()
    {

        impl.sendCouponRequest();

    }


    @Override
    public void requestFailedDataCall(Object o, int type)
    {

        view.callFailedViewLogic(o,type);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {

        view.callSuccessViewLogic(o,type);
    }
}

