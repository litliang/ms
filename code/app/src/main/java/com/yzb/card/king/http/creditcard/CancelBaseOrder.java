package com.yzb.card.king.http.creditcard;

import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/2/16 14:09
 */
public class CancelBaseOrder
{

    public CancelBaseOrder(Long orderId)
    {
        cancelOrder(orderId);
    }

    private void cancelOrder(Long orderId)
    {
        if (orderId == null) return;
        SimpleRequest request = new SimpleRequest(CardConstant.card_app_updateorderstatus)
        {
            @Override
            protected Object parseData(String data)
            {
                return data;
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("orderId", orderId);
        param.put("operateStatus", "100");
        request.setParam(param);
        request.sendRequestNew(null);
    }

}
