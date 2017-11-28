package com.yzb.card.king.ui.travel.view;

import com.yzb.card.king.bean.travel.TravelLineBean;

import java.util.List;

/**
 * 作  者：gengqiyun
 * 日  期：2016/11/23
 * 描  述：旅游线路列表；
 */
public interface TravelLineView
{
    /**
     * 成功；
     *
     * @param data
     */
    void onGetTravelLineSucess(boolean event_tag, List<TravelLineBean> data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetTravelLineFail(String failMsg);
}
