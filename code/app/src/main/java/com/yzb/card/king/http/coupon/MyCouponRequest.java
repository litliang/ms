package com.yzb.card.king.http.coupon;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 玉兵 on 2017/10/29.
 */

public class MyCouponRequest extends BaseRequest {

    Map<String, Object> params = new HashMap<>();

    private String serviceName = CardConstant.CARD_APP_QUERYCUSTCOUPON;

    public MyCouponRequest(boolean flag) {

        if(flag){
            serviceName = CardConstant.CARD_APP_QUERYCUSTCOUPON;
        }else {

            serviceName = CardConstant.CARD_APP_QUERYCUSTCASHCOUPON;
        }

    }


    @Override
    public void sendRequest(HttpCallBackData callBack) {
        sendPostRequest(setParams(AppConstant.sessionId, serviceName, AppConstant.UUID, params), callBack);
    }
}
