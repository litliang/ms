package com.yzb.card.king.ui.film.model;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类名： CinemaDetailsImpl
 * 作者： Lei Chao.
 * 日期： 2016-09-21
 * 描述：
 */
public class CinemaDetailsImpl implements ICinemaDetails
{
    private DataCallBack mOnDataload;

    @Override
    public void sendMovieListRequest(Map<String, Object> params, String serviceName)
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
                mOnDataload.requestSuccess( o,ICinemaDetails.TYPE_MOVIE_LIST);
            }

            @Override
            public void onFailed(Object o)
            {
                mOnDataload.requestFailedDataCall(o,0);
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
    public void sendCinemaDetailRequest(Map<String, Object> params, String serviceName)
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
                mOnDataload.requestSuccess(o,ICinemaDetails.TYPE_CINEMA_DETAILS);
            }

            @Override
            public void onFailed(Object o)
            {
                mOnDataload.requestFailedDataCall(o,0);
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
    public void sendCollectReqeust(Map<String, Object> params, String serviceName)
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
                mOnDataload.requestSuccess(o,ICinemaDetails.TYPE_COLLECT);
            }

            @Override
            public void onFailed(Object o)
            {
                mOnDataload.requestFailedDataCall(o,0);
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
        this.mOnDataload = onDataLoadFinish;
    }
}