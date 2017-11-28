package com.yzb.card.king.ui.film.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.FilmBean;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.film.model.IMoveMain;
import com.yzb.card.king.ui.film.model.MoveMainImpl;
import com.yzb.card.king.ui.film.view.MovieMainView;

import java.util.Map;

/**
 * 类名： MovieMainPersenter
 * 作者： Lei Chao.
 * 日期： 2016-09-20
 * 描述： 电影主页
 */
public class MovieMainPersenter implements DataCallBack
{

    private IMoveMain iMoveMain;

    private MovieMainView view;

    public MovieMainPersenter(MovieMainView movieMainView)
    {
        this.view = movieMainView;
        this.iMoveMain = new MoveMainImpl();
        this.iMoveMain.setOnDataLoadFinish(this);
    }


    /**
     * 电影推荐
     *
     * @param serviceName
     * @param params
     */
    public void sendMovieGuideRequest(String serviceName, Map<String, Object> params)
    {
        iMoveMain.sendMovieGuideRequest(params, serviceName);
    }

    /**
     * 推荐影院
     *
     * @param params
     * @param serviceName
     */
    public void sendRecommendedtheaterRequest(Map<String, Object> params, String serviceName)
    {
        iMoveMain.sendRecommendedtheaterRequest(params, serviceName);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == IMoveMain.TYPE_MOVIE_GUIDE)
        {
            view.onMovieGuideSuccess(JSON.parseArray(String.valueOf(o), FilmBean.class));
        }
        if (type == IMoveMain.TYPE_MOVIE_RECOMMEND)
        {
            view.onRecommendedTheaterSuccess(JSON.parseArray(String.valueOf(o), StoreBean.class));
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (o != null && o instanceof Map)
        {
            Map<String, String> map = (Map) o;

            view.callFailedViewLogic(map.get(HttpConstant.SERVER_ERROR),1);
        }
    }
}