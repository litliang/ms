package com.yzb.card.king.ui.transport.view;

/**
 * 功能：航线view；
 *
 * @author:gengqiyun
 * @date: 2016/9/8
 */
public interface QueryLineView
{
    /**
     * @param event_tag 下拉或上拉；
     * @param data
     */
    void onQueryLineSucess(boolean event_tag, Object data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onQueryLineFail(String failMsg);
}
