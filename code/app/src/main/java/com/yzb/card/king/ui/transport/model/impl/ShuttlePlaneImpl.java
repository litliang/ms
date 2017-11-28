package com.yzb.card.king.ui.transport.model.impl;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.landtransport.CarTypeRequest;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.transport.model.IShuttlePlane;

import java.util.Map;

/**
 * 类名： ShuttlePlaneImpl
 * 作者： Lei Chao.
 * 日期： 2016-09-05
 * 描述：
 */
public class ShuttlePlaneImpl implements IShuttlePlane
{

    private DataCallBack mOnDataLoadFinish;

    @Override
    public void sendCardTypeRequest(Map<String, Object> params, String serviceName)
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
                mOnDataLoadFinish.requestSuccess(o,0);
            }

            @Override
            public void onFailed(Object o)
            {
                mOnDataLoadFinish.requestFailedDataCall(o,0);
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