package com.yzb.card.king.ui.travel.view;

import com.yzb.card.king.bean.travel.DateBean;

import java.util.List;

/**
 * 作  者：gengqiyun
 * 日  期：2016/11/24
 * 描  述：查询旅游线路出发日期；
 */
public interface TravelGetDatesView
{
    /**
     * 成功；
     *
     * @param data
     */
    void onGetDatesSucess(boolean event_tag, List<DateBean> data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetDatesFail(String failMsg);
}
