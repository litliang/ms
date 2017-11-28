package com.yzb.card.king.ui.film.model;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类名： MovieListImpl
 * 作者： Lei Chao.
 * 日期： 2016-09-20
 * 描述： 电影榜单
 */
public class MovieListImpl implements IMovieList
{

    private DataCallBack mOnDataLoadFinish;

    @Override
    public void sendHitListRequest(Map<String, Object> params, String serviceName)
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
                mOnDataLoadFinish.requestSuccess(o,IMovieList.TYPE_HIT);
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
            public void onFinished(){
            }
        });
    }

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
                mOnDataLoadFinish.requestSuccess(o,IMovieList.TYPE_MOVIE_LIST);
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
    public void sendCategoryRequest(Map<String, Object> params, String serviceName)
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
                mOnDataLoadFinish.requestSuccess(o,IMovieList.TYPE_CATEGORY);
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
    public void sendCinemaListRequest(Map<String, Object> params, String serviceName)
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
                mOnDataLoadFinish.requestSuccess(o,IMovieList.TYPE_CINEMA);
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
        mOnDataLoadFinish = onDataLoadFinish;
    }
}