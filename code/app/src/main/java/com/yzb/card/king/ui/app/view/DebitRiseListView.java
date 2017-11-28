package com.yzb.card.king.ui.app.view;

/**
 * 功能：设置--发票抬头列表；
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public interface DebitRiseListView
{
    /**
     * 获取成功；
     *
     * @param event_flag 下拉或上拉标志；
     * @param data
     */
    void onLoadDebitRisesSucess(boolean event_flag, Object data);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onLoadDebitRisesFail(String failMsg);
}
