package com.yzb.card.king.ui.ticket.model.impl;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.ticket.model.IFindLow;

import java.util.Map;

/**
 * 类名： FindLowImpl
 * 作者： Lei Chao.
 * 日期： 2016-10-11
 * 描述：
 */
public class FindLowImpl implements IFindLow
{

    private DataCallBack mOnDataLoadFinish;

    @Override
    public void sendFindLowRequest(Map<String, Object> params, String serviceName)
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
                mOnDataLoadFinish.requestSuccess(o,IFindLow.SING_TYPE);
            }

            @Override
            public void onFailed(Object o)
            {
                mOnDataLoadFinish.requestFailedDataCall(o,IFindLow.SING_TYPE);
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
    public void sendRoundLowRequest(Map<String, Object> params, String serviceName)
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
                mOnDataLoadFinish.requestSuccess(o,IFindLow.ROUND_TYPE);
            }

            @Override
            public void onFailed(Object o)
            {
                mOnDataLoadFinish.requestFailedDataCall(o,IFindLow.ROUND_TYPE);
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
        this.mOnDataLoadFinish = onDataLoadFinish;
    }
}