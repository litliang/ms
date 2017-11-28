package com.yzb.card.king.ui.film.view;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.discount.bean.YpBean;

import java.util.List;
import java.util.Map;

/**
 * 类名： MovieListView
 * 作者： Lei Chao.
 * 日期： 2016-09-20
 * 描述： 热映榜单列表
 */
public interface MovieListView extends BaseViewLayerInterface
{

    /**
     * 分类
     *
     * @param categoryBeans
     */
    void getCategory(List<CategoryBean> categoryBeans);

    /**
     * 热映影片
     *
     * @param data
     */
    void onHitListSuccess(Map<String, Object> data);


    /**
     * 影片列表
     */
    void getMovieList(List<YpBean> ypBeans);


    /**
     * 影院列表
     *
     * @param storeBeans
     */
    void getCinemaList(List<StoreBean> storeBeans);



}