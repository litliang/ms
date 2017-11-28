package com.yzb.card.king.ui.order.view;

import com.yzb.card.king.bean.order.OrderBean;

import java.util.List;

/**
 * 类名： OrderManagerListsView
 * 作者： Lei Chao.
 * 日期： 2016-10-11
 * 描述：
 */

public interface OrderManagerListsView
{

    /**
     * 得到订单信息集合
     * @param maps
     */
    public void getOrderLists(List<OrderBean> maps);

    /**
     * 加载更多数据集合
     * @param moreDataList
     */
    public void getMoreDataList(List<OrderBean>  moreDataList);

    /**
     * 错误信息
     * @param o
     */
    public void onError(Object o);


}
