package com.yzb.card.king.ui.film.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.ui.discount.bean.FilterBean;
import com.yzb.card.king.ui.discount.bean.FjTjOutBean;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.discount.bean.YpBean;
import com.yzb.card.king.ui.discount.bean.YpdetailBean;
import com.yzb.card.king.ui.film.model.IMovieDetail;
import com.yzb.card.king.ui.film.model.MovieDetailsImpl;
import com.yzb.card.king.ui.film.view.MovieDetailView;
import com.yzb.card.king.ui.film.view.MovieFiltersView;

import java.util.Map;

/**
 * 类名： MovieDetailsPersenter
 * 作者： Lei Chao.
 * 日期： 2016-09-20
 * 描述： 筛选条件
 */
public class MovieDetailsPersenter implements DataCallBack
{

    private IMovieDetail iMovieDetail;

    private MovieDetailView view;

    private MovieFiltersView filtersView;


    public MovieDetailsPersenter(MovieDetailView view, MovieFiltersView filtersView)
    {
        this.iMovieDetail = new MovieDetailsImpl();
        this.iMovieDetail.setOnDataLoadFinish(this);
        this.view = view;
        this.filtersView = filtersView;
    }

    /**
     * 所有银行
     *
     * @param params
     * @param serviceName
     */
    public void sendAllBankRequest(Map<String, Object> params, String serviceName)
    {
        this.iMovieDetail.sendAllBankRequest(params, serviceName);
    }

    /**
     * 我的银行
     *
     * @param params
     * @param serviceName
     */
    public void sendBankRequest(Map<String, Object> params, String serviceName)
    {
        this.iMovieDetail.sendBankRequest(params, serviceName);
    }

    /**
     * 筛选条件
     *
     * @param params
     * @param serviceName
     */
    public void sendFiltersRequest(Map<String, Object> params, String serviceName)
    {
        this.iMovieDetail.sendFiltersRequest(params, serviceName);
    }

    /**
     * 电影详情
     *
     * @param params
     * @param serviceName
     */
    public void sendDetailsRequest(Map<String, Object> params, String serviceName)
    {
        this.iMovieDetail.sendDetailsRequest(params, serviceName);
    }

    /**
     * 收藏请求
     *
     * @param params
     * @param serviceName
     */
    public void sendCollectRequest(Map<String, Object> params, String serviceName)
    {
        this.iMovieDetail.sendMovieCollectRequest(params, serviceName);
    }

    /**
     * 查询类别
     *
     * @param params
     * @param serviceName
     */
    public void sendCategoryRequest(Map<String, Object> params, String serviceName)
    {
        this.iMovieDetail.sendCategoryRequest(params, serviceName);
    }

    /**
     * 距离
     *
     * @param params
     * @param serviceName
     */
    public void sendNearbyRequest(Map<String, Object> params, String serviceName)
    {
        this.iMovieDetail.sendNearbyRequest(params, serviceName);
    }

    /**
     * 电影列表
     *
     * @param params
     * @param serviceName
     */
    public void sendMoviesRequest(Map<String, Object> params, String serviceName)
    {
        this.iMovieDetail.sendMoviesRequest(params, serviceName);
    }

    /**
     * 距离列表
     *
     * @param params
     * @param sercieName
     */
    public void sendNeaybyListRequest(Map<String, Object> params, String sercieName)
    {
        this.iMovieDetail.sendNearbyListRequest(params, sercieName);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == IMovieDetail.TYPE_DETAILS)
        {
            view.callSuccessViewLogic(JSON.parseObject(String.valueOf(o), YpdetailBean.class),type);
        }
        if (type == IMovieDetail.TYPE_COLLECT)
        {
            view.getCollectStatus();
        }
        if (type == IMovieDetail.TYPE_FILTERS)
        {
            filtersView.getFilters(JSON.parseArray(String.valueOf(o), FilterBean.class));
        }
        if (type == IMovieDetail.TYPE_CATEGORY)
        {
            filtersView.getCategory(JSON.parseArray(String.valueOf(o), CategoryBean.class));
        }
        if (type == IMovieDetail.TYPE_NEARBY)
        {
            filtersView.getNearby(JSON.parseObject(String.valueOf(o), FjTjOutBean.class));
        }
        if (type == IMovieDetail.TYPE_BANK)
        {
            filtersView.getBank(JSON.parseArray(String.valueOf(o), BankBean.class));
        }
        if (type == IMovieDetail.TYPE_ALLBANK)
        {
            filtersView.getAllBank(JSON.parseArray(String.valueOf(o), BankBean.class));
        }
        if (type == IMovieDetail.TYPE_MOVIES)
        {
            filtersView.getMovies(JSON.parseArray(String.valueOf(o), YpBean.class));
        }
        if (type == IMovieDetail.TYPE_LIST)
        {
            filtersView.getNearbyList(JSON.parseArray(String.valueOf(o), StoreBean.class));
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (o != null && o instanceof Map)
        {
            Map<String, String> map = (Map<String, String>) o;
            if (view != null)
            {
                view.callFailedViewLogic(map.get(HttpConstant.SERVER_ERROR),type);
            }
            if (filtersView != null)
            {
                filtersView.callDataFailedMsg(map.get(HttpConstant.SERVER_ERROR));
            }
        }
    }
}