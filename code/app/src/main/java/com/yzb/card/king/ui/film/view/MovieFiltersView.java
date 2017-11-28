package com.yzb.card.king.ui.film.view;

import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.ui.discount.bean.FilterBean;
import com.yzb.card.king.ui.discount.bean.FjTjOutBean;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.discount.bean.YpBean;

import java.util.List;

/**
 * 类名： MovieFiltersView
 * 作者： Lei Chao.
 * 日期： 2016-09-20
 * 描述：
 */
public interface MovieFiltersView
{

    /**
     * 筛选条件
     *
     * @param sxBeans
     */
    void getFilters(List<FilterBean> sxBeans);


    /**
     * 分类
     */
    void getCategory(List<CategoryBean> categoryBeans);


    /**
     * 附近条件
     */
    void getNearby(FjTjOutBean fjTjOutBean);


    /**
     * 我的银行
     *
     * @param myBanks
     */
    void getBank(List<BankBean> myBanks);

    /**
     * 所有银行
     */
    void getAllBank(List<BankBean> allBanks);

    /**
     * 影片
     */
    void getMovies(List<YpBean> ypBeans);


    /**
     * 根据条件筛选门店列表
     *
     * @param storeBeans
     */
    void getNearbyList(List<StoreBean> storeBeans);

    /**
     * 错误信息
     * @param o
     */
    void callDataFailedMsg(Object o);

}