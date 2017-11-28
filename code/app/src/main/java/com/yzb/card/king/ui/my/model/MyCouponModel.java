package com.yzb.card.king.ui.my.model;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.coupon.MyCouponRequest;

/**
 * Created by 玉兵 on 2017/10/29.
 */

public class MyCouponModel {

    private DataCallBack dataCallBack;

    public MyCouponModel(DataCallBack dataCallBack){

        this.dataCallBack = dataCallBack;

    }

    /**
     * 发送优惠券请求
     * @param flag true:我的优惠券；false：我的代金券
     */
    public  void sendCouponRequest(boolean flag){


        MyCouponRequest request  = new MyCouponRequest(flag);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                dataCallBack.requestSuccess(o,-1);
            }

            @Override
            public void onFailed(Object o)
            {

                dataCallBack.requestFailedDataCall(o,-1);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }
}
