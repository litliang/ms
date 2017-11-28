package com.yzb.card.king.http;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/11/22
 * 描  述：
 */
public class CommonServerRequest {

    private DataCallBack mOnDataLoad;

    private int timeOut = 5*1000;

    public void setTimeOut(int timeOut)
    {
        this.timeOut = timeOut;
    }

    public void sendReuqest(Map<String, Object> map, String serviceName, final int type)
    {
        new SimpleRequest(serviceName,map){
            @Override
            protected int getConnectTimeOut()
            {
                return timeOut;
            }
        }.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                mOnDataLoad.requestSuccess(o,type);
            }

            @Override
            public void onFailed(Object o)
            {
                mOnDataLoad.requestFailedDataCall(o,type);
            }

            @Override
            public void onCancelled(Object o)
            {
                mOnDataLoad.requestFailedDataCall(o,type);
            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    public void setOnDataLoadFinish(DataCallBack onDataLoadFinish)
    {
        this.mOnDataLoad = onDataLoadFinish;
    }


}
