package com.yzb.card.king.ui.integral.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.integral.UserCouponBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.integral.model.ICoupon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 15:30
 */
public class CouponImpl implements ICoupon
{
    private HttpCallBackData callBack;

    public CouponImpl(HttpCallBackData callBack)
    {
        this.callBack = callBack;
    }

    @Override
    public void loadData(String startIndex, String pageSize)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", startIndex);
        param.put("pageSize", pageSize);
        SimpleRequest request = new SimpleRequest(CardConstant.INTEGRAL_USERCOUPONLIST, param);
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                Map<String, String> resultMap = (Map<String, String>) o;
                String data = resultMap.get("data");
                String sessionId = resultMap.get("sessionId");
                AppConstant.handleSessionId(sessionId);
                List<UserCouponBean> userBean = JSON.parseArray(data, UserCouponBean.class);
                if (callBack != null)
                {
                    callBack.onSuccess(userBean);
                }
            }

            @Override
            public void onFailed(Object o)
            {
                if (callBack != null)
                {
                    callBack.onFailed(o);
                }
            }
        });
    }
}
