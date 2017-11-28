package com.yzb.card.king.http.coupon;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： CouponDataRequest
 * 作者： Lei Chao.
 * 日期： 2016-08-08
 * 描述：
 */
public class CouponDataRequest extends BaseRequest {

    Map<String, Object> params = new HashMap<>();

    private String serviceName = CardConstant.card_app_queryCoupon;

    public CouponDataRequest() {
//        params.put("pageStart", "0");
//        params.put("pageSize", "6");
    }

    @Override
    public void sendRequest(HttpCallBackData callBack) {
        sendPostRequest(setParams(AppConstant.sessionId, serviceName, AppConstant.UUID, params), callBack);
    }
}