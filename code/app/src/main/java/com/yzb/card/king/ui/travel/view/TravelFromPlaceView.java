package com.yzb.card.king.ui.travel.view;

import com.yzb.card.king.bean.travel.FromPlaceBean;

import java.util.List;

/**
 * 作  者：gengqiyun
 * 日  期：2016/11/23
 * 描  述：旅游出发地列表；
 */
public interface TravelFromPlaceView
{
    /**
     * 成功；
     *
     * @param data
     */
    void onGetFromPlaceSucess(boolean event_tag, List<FromPlaceBean> data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetFromPlaceFail(String failMsg);
}
