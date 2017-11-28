package com.yzb.card.king.ui.transport.view;

/**
 * 功能：船票代理商
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public interface ShipAgentView
{
    /**
     * @param event_tag 下拉或上拉；
     * @param data
     */
    void onLoadShipAgentListSucess(boolean event_tag, Object data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onLoadShipAgentListFail(String failMsg);
}
