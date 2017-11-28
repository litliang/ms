package com.yzb.card.king.ui.order.model;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类名： ReasonForChangeImpl
 * 作者： Lei Chao.
 * 日期： 2016-10-12
 * 描述：
 */
public class ReasonForChangeImpl implements IReasonForChange {

    private DataCallBack onDataLoadFinish;

    public ReasonForChangeImpl(DataCallBack dataCallBack)
    {
        this.onDataLoadFinish = dataCallBack;
    }

    @Override
    public void sendReasonForChangeRequest(Map<String, Object> params, String serviceName)
    {
        new SimpleRequest(serviceName, params).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                onDataLoadFinish.requestSuccess(o, 0);
            }

            @Override
            public void onFailed(Object o)
            {
                onDataLoadFinish.requestFailedDataCall(o, 0);
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

}