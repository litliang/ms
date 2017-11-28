package com.yzb.card.king.ui.app.view;

/**
 * 功能：设置--收货地址列表；
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public interface AddressListView
{
    /**
     * 获取成功；
     *
     * @param event_flag 下拉或上拉标志；
     * @param data
     */
    void onLoadAddressListSucess(boolean event_flag, Object data);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onLoadAddressListFail(String failMsg);
}
