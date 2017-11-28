package com.yzb.card.king.ui.luxury.view;


/**
 * 功能：过滤条件列表；
 *
 * @author:gengqiyun
 * @date: 2016/9/23
 */
public interface FilterMenuView
{
    /**
     */
    void onLoadFilterMenuSucess(Object data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onLoadFilterMenuFail(String failMsg);
}
