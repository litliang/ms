package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.bean.my.CouponsHomeBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.my.model.CustomCouponsModel;
import com.yzb.card.king.ui.my.view.CustomCouponsView;

import java.util.List;
import java.util.Map;

/**
 * 功能：客户优惠券列表；
 *
 * @author:gengqiyun
 * @date:2017/1/10
 */
public class CustomCouponsPresenter implements BaseMultiLoadListener
{
    private CustomCouponsModel model;
    private CustomCouponsView view;

    public CustomCouponsPresenter(CustomCouponsView view)
    {
        this.view = view;
        model = new CustomCouponsModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetCouponsSuc(event_tag, (List<CouponsHomeBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetCouponsFail(msg);
    }
}
