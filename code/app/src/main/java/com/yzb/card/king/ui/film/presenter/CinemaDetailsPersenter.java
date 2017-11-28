package com.yzb.card.king.ui.film.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.film.model.CinemaDetailsImpl;
import com.yzb.card.king.ui.film.model.ICinemaDetails;
import com.yzb.card.king.ui.film.view.CinemaDetailsView;

import java.util.Map;

/**
 * 类名： CinemaDetailsPersenter
 * 作者： Lei Chao.
 * 日期： 2016-09-21
 * 描述：
 */
public class CinemaDetailsPersenter implements DataCallBack
{

    private ICinemaDetails iCinemaDetails;

    private CinemaDetailsView view;

    public CinemaDetailsPersenter(CinemaDetailsView view)
    {
        this.iCinemaDetails = new CinemaDetailsImpl();
        this.iCinemaDetails.setOnDataLoadFinish(this);
        this.view = view;
    }

    /**
     * 查询影院详情
     *
     * @param params
     * @param serviceName
     */
    public void sendCinemaDetailsRequest(Map<String, Object> params, String serviceName)
    {
        this.iCinemaDetails.sendCinemaDetailRequest(params, serviceName);
    }

    /**
     * 查询电影列表
     *
     * @param params
     * @param serviceName
     */
    public void sendMovieListRequest(Map<String, Object> params, String serviceName)
    {
        this.iCinemaDetails.sendMovieListRequest(params, serviceName);
    }


    /**
     * 影院收藏
     *
     * @param params
     * @param serviceName
     */
    public void sendCollectRequest(Map<String, Object> params, String serviceName)
    {
        this.iCinemaDetails.sendCollectReqeust(params, serviceName);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == ICinemaDetails.TYPE_CINEMA_DETAILS)
        {
            view.getCinemaDetails(JSON.parseObject(String.valueOf(o), Map.class));
        }
        if (type == ICinemaDetails.TYPE_MOVIE_LIST)
        {
            view.getMovieList(JSON.parseArray(String.valueOf(o), Map.class));
        }
        if (type == ICinemaDetails.TYPE_COLLECT)
        {
            view.getCollectStatus();
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (o != null && o instanceof Map)
        {
            Map<String, String> map = (Map<String, String>) o;
            view.callFailedViewLogic(map.get(HttpConstant.SERVER_ERROR),1);
        }
    }
}