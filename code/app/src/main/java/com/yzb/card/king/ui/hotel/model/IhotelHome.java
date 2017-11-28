package com.yzb.card.king.ui.hotel.model;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/9/5
 * 描  述：
 */
public interface IhotelHome {

    /**
     * 用户频道请求识别符
     */
     int USERCHANNEL_CODE = 1;

    /**
     * 酒店请求识别符
     */
     int HOTELTHEME_CODE = 2;


    /**
     * 发送用户关于酒店的频道请求
     */
    public void sendUserChannelRequest();

    /**
     * 发送酒店主题请求
     * @param startSize
     */
    public void sendHotelThemeRequest(String startSize);
}
