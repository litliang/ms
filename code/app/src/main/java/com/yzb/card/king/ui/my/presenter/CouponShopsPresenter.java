package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.bean.my.CouponShopBean;
import com.yzb.card.king.bean.my.CouponsHomeBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.my.model.CouponRecommendModel;
import com.yzb.card.king.ui.my.model.CouponShopsModel;
import com.yzb.card.king.ui.my.model.VoucherShopsModel;
import com.yzb.card.king.ui.my.view.CouponShopsView;

import java.util.List;
import java.util.Map;

/**
 * 功能：优惠券对应商家；
 *
 * @author:gengqiyun
 * @date:2017/1/12
 */
public class CouponShopsPresenter implements BaseMultiLoadListener
{
    private CouponShopsModel model;

    private VoucherShopsModel voucherShopsModel;

    private CouponShopsView view;

    public CouponShopsPresenter(CouponShopsView view)
    {
        this.view = view;
        model = new CouponShopsModel(this);
        voucherShopsModel = new VoucherShopsModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }


    public void loadVoucherData(boolean event_tag, Map<String, Object> paramMap)
    {
        voucherShopsModel.loadData(event_tag, paramMap);
    }


    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetCouponShopsSuc(event_tag, (List<CouponInfoBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetCouponShopsFail(msg);
    }
}
