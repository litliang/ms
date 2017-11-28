package com.yzb.card.king.ui.app.view;

/**
 * 功能：搜索；
 *
 * @author:gengqiyun
 * @date: 2016/10/31
 */
public interface SearchInputView
{
    /**
     * 获取成功；
     *
     * @param event_tag true:下拉刷新；false：加载更多
     * @param data
     */
    void onSearchInputSucess(boolean event_tag, Object data);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onSearchInputFail(String failMsg);
}
