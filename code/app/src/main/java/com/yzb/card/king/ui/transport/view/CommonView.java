package com.yzb.card.king.ui.transport.view;


/**
 * 功能：机票；
 *
 * @author:gengqiyun
 * @date: 2016/8/31
 */
public interface CommonView
{
    /**
     * @param event_tag 下拉或上拉；
     * @param data
     */
    void onLoadSucess(boolean event_tag, Object data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onLoadFail(String failMsg);
}
