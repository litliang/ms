package com.yzb.card.king.ui.film.model;

import java.util.Map;

/**
 * 类名： IMoveMain
 * 作者： Lei Chao.
 * 日期： 2016-09-20
 * 描述： 电影首页接口
 */
public interface IMoveMain extends BaseDataLoadFinish
{

    /**
     * 电影指南
     */
    int TYPE_MOVIE_GUIDE = 0;

    /**
     * 推荐影院
     */
    int TYPE_MOVIE_RECOMMEND = 1;

    /**
     * 电影指南
     *
     * @param params
     * @param serviceName
     */
    void sendMovieGuideRequest(Map<String, Object> params, String serviceName);


    /**
     * 推荐影院
     *
     * @param params
     * @param serviceName
     */
    void sendRecommendedtheaterRequest(Map<String, Object> params, String serviceName);

}