package com.yzb.card.king.ui.film.model;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类名： MoveMainImpl
 * 作者： Lei Chao.
 * 日期： 2016-09-20
 * 描述：
 */
public class MoveMainImpl implements IMoveMain
{

    DataCallBack mOnDataLoadFinishl;

    @Override
    public void sendMovieGuideRequest(Map<String, Object> params, String serviceName)
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
                mOnDataLoadFinishl.requestSuccess( o,IMoveMain.TYPE_MOVIE_GUIDE);
            }

            @Override
            public void onFailed(Object o)
            {
                mOnDataLoadFinishl.requestFailedDataCall( o,IMoveMain.TYPE_MOVIE_GUIDE);
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
    public void sendRecommendedtheaterRequest(Map<String, Object> params, String serviceName)
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
                mOnDataLoadFinishl.requestSuccess( o,IMoveMain.TYPE_MOVIE_RECOMMEND);
            }

            @Override
            public void onFailed(Object o)
            {
                mOnDataLoadFinishl.requestFailedDataCall(o,IMoveMain.TYPE_MOVIE_RECOMMEND);
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
        this.mOnDataLoadFinishl = onDataLoadFinish;
    }
}