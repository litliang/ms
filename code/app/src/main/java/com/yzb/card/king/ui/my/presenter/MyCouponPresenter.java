package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.model.MyCouponModel;

/**
 * 我的优惠券
 * Created by 玉兵 on 2017/10/29.
 */

public class MyCouponPresenter implements DataCallBack{

    private BaseViewLayerInterface baseViewLayerInterface;

    private MyCouponModel  myCouponModel;


    public MyCouponPresenter( BaseViewLayerInterface baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;

        myCouponModel = new MyCouponModel(this);

    }

    /**
     * 发送我的优惠券
     * @param flag
     */
    public void sendMyCouponAction(boolean flag){

        myCouponModel.sendCouponRequest(flag);
    }


    @Override
    public void requestSuccess(Object o, int type) {

        baseViewLayerInterface.callSuccessViewLogic(o,type);
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {

        baseViewLayerInterface.callFailedViewLogic(o,type);

    }
}
