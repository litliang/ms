package com.yzb.card.king.ui.order.model;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类名： OrderManagerListsImpl
 * 作者： Lei Chao.
 * 日期： 2016-10-11
 * 描述：
 */
public class OrderManagerListsImpl
{

    private DataCallBack mOnDataLoadFinish;

    public void setOnDataLoadFinish(DataCallBack onDataLoadFinish)
    {
        this.mOnDataLoadFinish = onDataLoadFinish;
    }

    public void sendOrderListsRequest(Map<String, Object> params, String serviceName)
    {
        new SimpleRequest(serviceName,params).sendRequest(new HttpCallBackData()
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
                mOnDataLoadFinish.requestFailedDataCall(o,0);
            }

            @Override
            public void onFinished()
            {

            }
        });
    }


}