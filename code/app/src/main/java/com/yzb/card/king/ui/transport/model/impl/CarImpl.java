package com.yzb.card.king.ui.transport.model.impl;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.landtransport.CarTypeRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类名： CarImpl
 * 作者： Lei Chao.
 * 日期： 2016-09-07
 * 描述：
 */
public class CarImpl implements ICar
{


    private DataCallBack mOnDataLoad;

    @Override
    public void sendSelfDriveRequest(Map<String, Object> params, String serviceName)
    {
        new CarTypeRequest(params, serviceName).sendRequest(new HttpCallBackData()
        {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                mOnDataLoad.requestSuccess(o,ICar.TYPE_SELF);
            }

            @Override
            public void onFailed(Object o)
            {
                mOnDataLoad.requestFailedDataCall(o,ICar.TYPE_SELF);
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
    public void sendDailyRentRequest(Map<String, Object> params, String serviceName)
    {
        new CarTypeRequest(params, serviceName).sendRequest(new HttpCallBackData()
        {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                mOnDataLoad.requestSuccess(o,ICar.TYPE_DAILY);
            }

            @Override
            public void onFailed(Object o)
            {
                mOnDataLoad.requestFailedDataCall(o,ICar.TYPE_DAILY);
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
        this.mOnDataLoad = onDataLoadFinish;
    }
}