package com.yzb.card.king.ui.film.model;

import java.util.Map;

/**
 * 类名： IMovieList
 * 作者： Lei Chao.
 * 日期： 2016-09-20
 * 描述： 榜单列表
 */
public interface IMovieList extends BaseDataLoadFinish
{

    /**
     * 热映列表
     */
    int TYPE_HIT = 0;

    /**
     * 影片列表
     */
    int TYPE_MOVIE_LIST = 1;


    /**
     * 分类
     */
    int TYPE_CATEGORY = 2;


    /**
     * 影院
     */
    int TYPE_CINEMA = 3;

    /**
     * 热映先锋榜
     *
     * @param params
     * @param serviceName
     */
    void sendHitListRequest(Map<String, Object> params, String serviceName);


    /**
     * 影片列表
     *
     * @param params
     * @param serviceName
     */
    void sendMovieListRequest(Map<String, Object> params, String serviceName);


    /**
     * 分类
     *
     * @param params
     * @param serviceName
     */
    void sendCategoryRequest(Map<String, Object> params, String serviceName);


    /**
     * 影院列表
     *
     * @param params
     * @param serviceName
     */
    void sendCinemaListRequest(Map<String, Object> params, String serviceName);


}