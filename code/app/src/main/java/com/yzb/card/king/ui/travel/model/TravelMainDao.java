package com.yzb.card.king.ui.travel.model;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/9/1
 * 描  述：
 */
public interface TravelMainDao {
    /**
     * 用户频道请求识别符
     */
    public static final int USERCHANNEL_CODE = 1;

    /**
     * 特卖惠请求识别符
     */
    public static final int SPECIALSALE_CODE = 2;

    /**
     * 旅游风向标识别符
     */
    public static final int TRAVELFXB_CODE = 3;

    /**
     * 旅游风向标加载更多标识
     */
    public static final int TRAVELFXB_MORE_CODE=4;

    /**
     * 用户频道请求方法
     *
     * @param parentId
     * @param category
     */
    public void userChannelRequest(String parentId, String category);

    /**
     * 特卖惠请求方法
     *
     */
    public void specialSaleRequest();

    /**
     * 旅游风向标请求方法
     *
     */
    public void travelFxbRequest(Map<String,Object> map,String serViceName,int code);

}
