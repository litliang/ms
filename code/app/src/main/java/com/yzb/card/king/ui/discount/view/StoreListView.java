package com.yzb.card.king.ui.discount.view;

/**
 * 功能：美食筛选列表；
 *
 * @author:gengqiyun
 * @date: 2016/9/20
 */
public interface StoreListView
{
    /**
     * @param event_tag 下拉或上拉；
     * @param data
     */
    void onLoadListDataSucess(boolean event_tag, Object data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onLoadListDataFail(String failMsg);
}
