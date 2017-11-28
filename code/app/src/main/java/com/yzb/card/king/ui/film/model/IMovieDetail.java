package com.yzb.card.king.ui.film.model;

import java.util.Map;

/**
 * 类名： IMovieDetail
 * 作者： Lei Chao.
 * 日期： 2016-09-20
 * 描述： 影片详情
 */
public interface IMovieDetail extends BaseDataLoadFinish
{

    /**
     * 根据条件筛选门店列表；
     */
    int TYPE_LIST = 9;

    /**
     * 影片
     */
    int TYPE_MOVIES = 8;

    /**
     * 所有银行
     */
    int TYPE_ALLBANK = 7;

    /**
     * 我的银行
     */
    int TYPE_BANK = 6;

    /**
     * 附近条件
     */
    int TYPE_NEARBY = 5;

    /**
     * 获取分类
     */
    int TYPE_CATEGORY = 4;


    /**
     * 筛选条件
     */
    int TYPE_FILTERS = 3;

    /**
     * 收藏
     */
    int TYPE_COLLECT = 2;

    /**
     * 详情
     */
    int TYPE_DETAILS = 1;

    /**
     * 获取详情
     *
     * @param params
     * @param serviceName
     */
    void sendDetailsRequest(Map<String, Object> params, String serviceName);


    /**
     * 收藏请求
     *
     * @param params
     * @param serviceName
     */
    void sendMovieCollectRequest(Map<String, Object> params, String serviceName);


    /**
     * 筛选条件
     *
     * @param params
     * @param serviceName
     */
    void sendFiltersRequest(Map<String, Object> params, String serviceName);


    /**
     * 分类
     *
     * @param params
     * @param serviceName
     */
    void sendCategoryRequest(Map<String, Object> params, String serviceName);


    /**
     * 附近条件
     *
     * @param params
     * @param serviceName
     */
    void sendNearbyRequest(Map<String, Object> params, String serviceName);


    /**
     * 我的银行
     *
     * @param params
     * @param serviceName
     */
    void sendBankRequest(Map<String, Object> params, String serviceName);

    /**
     * 获取所有银行
     *
     * @param params
     * @param serviceName
     */
    void sendAllBankRequest(Map<String, Object> params, String serviceName);


    /**
     * 影片
     *
     * @param params
     * @param serviceName
     */
    void sendMoviesRequest(Map<String, Object> params, String serviceName);

    /**
     * 根据条件筛选门店列表；
     *
     * @param params
     * @param serviceName
     */
    void sendNearbyListRequest(Map<String, Object> params, String serviceName);

}