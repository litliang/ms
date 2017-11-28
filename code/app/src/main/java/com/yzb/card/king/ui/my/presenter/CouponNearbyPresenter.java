package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.bean.my.CouponNearbyBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.my.model.CouponNearbyModel;
import com.yzb.card.king.ui.my.view.CouponNearbyView;

import java.util.Map;

/**
 * 功能：优惠券附近筛选条件；
 *
 * @author:gengqiyun
 * @date: 2017/1/17
 */
public class CouponNearbyPresenter implements BaseMultiLoadListener
{
    private CouponNearbyModel model;
    private CouponNearbyView view;

    public CouponNearbyPresenter(CouponNearbyView view)
    {
        this.view = view;
        model = new CouponNearbyModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetCouponNearbySuc((CouponNearbyBean) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetCouponNearbyFail(msg);
    }
}
