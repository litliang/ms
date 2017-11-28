package com.yzb.card.king.ui.hotel.view;

/**
 * 功能：酒店地图列表；
 *
 * @author:gengqiyun
 * @date: 2016/11/1
 */
public interface HotelMapView
{
    /**
     * 查询成功；
     */
    void onQueryMapSucess(boolean event_tag, Object object);

    /**
     * @param failMsg 错误消息；
     */
    void onQueryMapFail(String failMsg);
}
