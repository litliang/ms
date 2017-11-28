package com.yzb.card.king.ui.travel.model;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/22
 * 描  述：
 */
public interface ITravelSale {

    //获取列表信息
    public static final int SALE_INFO = 0;

    //上拉加载更多
    public static final int SALE_INFO_MORE = 1;

    /**
     * 获取特卖会信息
     *
     * @param map
     * @param service
     */
    void getTravelSaleInfo(Map<String, Object> map, String service,int code);

}
