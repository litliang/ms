package com.yzb.card.king.ui.film.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.discount.bean.YpBean;
import com.yzb.card.king.ui.film.model.IMovieList;
import com.yzb.card.king.ui.film.model.MovieListImpl;
import com.yzb.card.king.ui.film.view.MovieListView;

import java.util.Map;

/**
 * 类名： MovieListPersenter
 * 作者： Lei Chao.
 * 日期： 2016-09-20
 * 描述： 榜单
 */
public class MovieListPersenter implements DataCallBack
{

    private IMovieList iMovieList;
    private MovieListView view;

    public MovieListPersenter(MovieListView view)
    {
        this.view = view;
        iMovieList = new MovieListImpl();
        iMovieList.setOnDataLoadFinish(this);
    }

    /**
     * 分类
     *
     * @param parameters
     * @param serviceName
     */
    public void sendCagegoryRequest(Map<String, Object> parameters, String serviceName)
    {
        this.iMovieList.sendCategoryRequest(parameters, serviceName);
    }

    /**
     * 影片列表
     *
     * @param params
     * @param serviceName
     */
    public void sendMovieListRequest(Map<String, Object> params, String serviceName)
    {
        this.iMovieList.sendMovieListRequest(params, serviceName);
    }

    /**
     * 查询电影榜单
     *
     * @param params
     * @param serviceName
     */
    public void sendHitListRequest(Map<String, Object> params, String serviceName)
    {
        this.iMovieList.sendHitListRequest(params, serviceName);
    }

    /**
     * 查询影院
     *
     * @param params
     * @param serviceName
     */
    public void sendCinemaRequest(Map<String, Object> params, String serviceName)
    {
        this.iMovieList.sendCinemaListRequest(params, serviceName);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == IMovieList.TYPE_HIT)
        {
            view.onHitListSuccess(JSON.parseObject(String.valueOf(o), Map.class));
        }
        if (type == IMovieList.TYPE_MOVIE_LIST)
        {
            view.getMovieList(JSON.parseArray(String.valueOf(o), YpBean.class));
        }
        if (type == IMovieList.TYPE_CATEGORY)
        {
            view.getCategory(JSON.parseArray(String.valueOf(o), CategoryBean.class));
        }
        if (type == IMovieList.TYPE_CINEMA)
        {
            view.getCinemaList(JSON.parseArray(String.valueOf(o), StoreBean.class));
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (o != null && o instanceof Map)
        {

            Map<String, String> map = (Map<String, String>) o;
            view.callFailedViewLogic(map.get(HttpConstant.SERVER_ERROR),-1);

        }
    }
}