package com.yzb.card.king.ui.discount.view;

/**
 * 功能：美食推荐主题
 *
 * @author:gengqiyun
 * @date: 2016/9/20
 */
public interface FoodThemeListView
{
    /**
     * @param event_tag 下拉或上拉；
     * @param data
     */
    void onLoadFoodThemeListSucess(boolean event_tag, Object data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onLoadFoodThemeListFail(String failMsg);
}
