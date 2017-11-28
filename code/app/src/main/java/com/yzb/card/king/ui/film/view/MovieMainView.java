package com.yzb.card.king.ui.film.view;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.FilmBean;
import com.yzb.card.king.ui.discount.bean.StoreBean;

import java.util.List;

/**
 * 类名： MovieMainView
 * 作者： Lei Chao.
 * 日期： 2016-09-20
 * 描述： 电影首页
 */
public interface MovieMainView extends BaseViewLayerInterface
{

    /**
     * 电影指南返回数据
     * @param FilmBeans
     */
    void onMovieGuideSuccess(List<FilmBean> FilmBeans);

    /**
     * 推荐影院数据
     * @param shopBeans
     */
    void onRecommendedTheaterSuccess(List<StoreBean> shopBeans);
}