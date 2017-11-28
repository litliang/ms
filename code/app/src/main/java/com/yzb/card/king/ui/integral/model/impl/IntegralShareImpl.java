package com.yzb.card.king.ui.integral.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.integral.IntegralShareLinkman;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.integral.model.IIntegralShare;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 15:49
 */
public class IntegralShareImpl implements IIntegralShare
{
    private HttpCallBackData callBack;

    public IntegralShareImpl(HttpCallBackData callBack)
    {
        this.callBack = callBack;
    }

    @Override
    public void loadContacts(String contactType)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", contactType);
        SimpleRequest request = new SimpleRequest(CardConstant.INTEGRAL_CONTACTSLIST, param);
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                List<IntegralShareLinkman> contactsBeenList = JSON.parseArray((String)o, IntegralShareLinkman.class);
                if(callBack!= null){
                    callBack.onSuccess(contactsBeenList);
                }
            }

            @Override
            public void onFailed(Object o)
            {
                if(callBack!= null){
                    callBack.onFailed(o);
                }
            }
        });
    }
}
