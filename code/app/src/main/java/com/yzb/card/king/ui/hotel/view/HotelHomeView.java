package com.yzb.card.king.ui.hotel.view;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/8/19
 * 描  述：
 */
public interface HotelHomeView extends BaseViewLayerInterface {

    /**
     * 采集酒店主题请求参数
     * @return
     */
    public String[] gatherHotelThemeRequestParam();

    /**
     *
     * @param o
     */
    public void callBackData(Object o);
}
