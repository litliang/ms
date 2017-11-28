package com.yzb.card.king.ui.film.view;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;

import java.util.List;
import java.util.Map;

/**
 * 类名： CinemaDetailsView
 * 作者： Lei Chao.
 * 日期： 2016-09-21
 * 描述： 影院详情
 */
public interface CinemaDetailsView extends BaseViewLayerInterface
{

    /**
     * 影院详情
     *
     * @param data
     */
    void getCinemaDetails(Map<String, Object> data);


    /**
     * 电影列表
     *
     * @param mFilmLists
     */
    void getMovieList(List<Map> mFilmLists);


    /**
     * 收藏状态
     */
    void getCollectStatus();

}