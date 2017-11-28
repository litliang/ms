package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.ui.app.interfaces.DiscountListener;
import com.yzb.card.king.ui.ticket.model.impl.ShopCouponModel;
import com.yzb.card.king.ui.ticket.view.DiscountView;

import java.util.Map;

/**
 * 功能：商家优惠券列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/12
 */
public class ShopCouponPresenter implements DiscountListener
{
    private ShopCouponModel model;
    private DiscountView view;

    public ShopCouponPresenter(DiscountView view)
    {
        this.view = view;
        model = new ShopCouponModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.commit(paramMap);
    }

    @Override
    public void onListenSuccess(String req_flag, Object data)
    {
        if (data != null)
        {
            view.onGetDiscountSucess(req_flag, data);
        }
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetDiscountFail(msg);
    }
}
