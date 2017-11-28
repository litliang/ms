package com.yzb.card.king.ui.film.model;

import java.util.Map;

/**
 * 类名： ICinemaDetails
 * 作者： Lei Chao.
 * 日期： 2016-09-21
 * 描述： 影院详情接口
 */
public interface ICinemaDetails extends BaseDataLoadFinish
{

    /**
     * 收藏
     */
    int TYPE_COLLECT = 3;

    /**
     * 影院详情
     */
    int TYPE_CINEMA_DETAILS = 1;


    /**
     * 电影列表
     */
    int TYPE_MOVIE_LIST = 2;

    /**
     * 电影列表
     *
     * @param params
     * @param serviceName
     */
    void sendMovieListRequest(Map<String, Object> params, String serviceName);

    /**
     * 影院详情
     *
     * @param params
     * @param serviceName
     */
    void sendCinemaDetailRequest(Map<String, Object> params, String serviceName);


    /**
     * 影院收藏
     *
     * @param params
     * @param serviceName
     */
    void sendCollectReqeust(Map<String, Object> params, String serviceName);

}

