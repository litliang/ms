package com.yzb.card.king.ui.travel.view;



/**
 * 功能：获取默认 联系人；
 *
 * @author:gengqiyun
 * @date: 2016/8/31
 */
public interface GetDefaultConnectorView
{
    /**
     * 成功；
     *
     * @param defaultConnector 默认 联系人；
     */
    void onGetConnectorSucess(boolean event_tag, Object defaultConnector);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetConnectorFail(String failMsg);
}
