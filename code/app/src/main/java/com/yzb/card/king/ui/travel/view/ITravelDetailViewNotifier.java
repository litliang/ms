package com.yzb.card.king.ui.travel.view;


/**
 * 功能：旅游详情数据通知更新统一接口；
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public interface ITravelDetailViewNotifier
{
    /**
     * 通知数据发生变化；
     */
    void notifyDataChanged();

    /**
     * 注入数据监听；
     */
    void setDataProvider(ITravelDetailDataProvider provider);
}
