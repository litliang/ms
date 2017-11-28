package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.my.model.CouponRecommendModel;
import com.yzb.card.king.ui.my.view.CouponInfoView;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/10/20
 * 描  述：
 */
public class CouponInfoPresenter implements BaseMultiLoadListener
{

    private CouponRecommendModel couponRecommendModel;

    private CouponInfoView view;

    public CouponInfoPresenter(CouponInfoView view)
    {
        this.view = view;

        couponRecommendModel = new CouponRecommendModel(this);
    }


    /**
     * 发送推荐优惠券请求
     * @param event_tag
     * @param paramMap
     */
    public void requestRecommendCoponRequest(boolean event_tag, Map<String, Object> paramMap){

        couponRecommendModel.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetCouponSuc(event_tag, (List<CouponInfoBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetCouponFail(msg);
    }
}
