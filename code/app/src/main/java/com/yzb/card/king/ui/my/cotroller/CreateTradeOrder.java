package com.yzb.card.king.ui.my.cotroller;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;

import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/16 14:33
 */
public class CreateTradeOrder
{
    public CreateTradeOrder(Map<String, Object> param, BaseCallBack listener)
    {
        SimpleRequest<Map<String, String>> request
                = new SimpleRequest<Map<String, String>>(CardConstant.CreateTradeOrder)
        {
            @Override
            protected Map<String, String> parseData(String data)
            {
                return JSON.parseObject(data, Map.class);
            }
        };

        request.setParam(param);

        request.sendRequestNew(listener);
    }
}
