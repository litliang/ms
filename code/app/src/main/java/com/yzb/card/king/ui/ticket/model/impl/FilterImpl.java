package com.yzb.card.king.ui.ticket.model.impl;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.ticket.model.IFilter;

import java.util.Map;

/**
 * 类名： FilterImpl
 * 作者： Lei Chao.
 * 日期： 2016-10-11
 * 描述：
 */
public class FilterImpl implements IFilter
{

    private DataCallBack onDataLoadFinish;

    @Override
    public void sendFilterDataRequest(Map<String, Object> params, String serviceName)
    {
        new SimpleRequest(serviceName, params).sendRequest(new HttpCallBackData()
        {
            @Override
            public void onStart()
            {
            }

            @Override
            public void onSuccess(Object o)
            {
                onDataLoadFinish.requestSuccess( o,0);
            }

            @Override
            public void onFailed(Object o)
            {
                onDataLoadFinish.requestFailedDataCall( o,0);
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

    @Override
    public void setOnDataLoadFinish(DataCallBack onDataLoadFinish)
    {
        this.onDataLoadFinish = onDataLoadFinish;
    }
}