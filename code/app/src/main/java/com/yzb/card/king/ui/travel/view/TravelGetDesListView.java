package com.yzb.card.king.ui.travel.view;

import com.yzb.card.king.bean.travel.FilterTwoBean;

import java.util.List;

/**
 * 作  者：gengqiyun
 * 日  期：2016/11/23
 * 描  述：旅游目的地列表
 */
public interface TravelGetDesListView
{
    /**
     * 成功；
     *
     * @param data
     */
    void onGetDesListSucess(boolean event_tag, List<FilterTwoBean> data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetDesListFail(String failMsg);
}
