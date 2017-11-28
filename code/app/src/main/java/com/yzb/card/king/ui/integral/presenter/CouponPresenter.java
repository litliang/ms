package com.yzb.card.king.ui.integral.presenter;

import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.integral.model.ICoupon;
import com.yzb.card.king.ui.integral.model.impl.CouponImpl;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 15:30
 */
public class CouponPresenter
{
    private BaseViewLayerInterface view;
    private ICoupon model;
    public CouponPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new CouponImpl(new OnLoadCallBack());
    }

    public void loadData(String startIndex, String pageSize)
    {
        model.loadData(startIndex,pageSize);
    }

    private class OnLoadCallBack extends HttpCallBackImpl{
        @Override
        public void onSuccess(Object o)
        {
            view.callSuccessViewLogic(o,1);
        }

        @Override
        public void onFailed(Object o)
        {
            view.callFailedViewLogic(o,1);
        }
    }
}
