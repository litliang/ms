package com.yzb.card.king.ui.travel.view;

import com.yzb.card.king.bean.travel.TravelProduDetailBean;

/**
 * 作  者：gengqiyun
 * 日  期：2016/11/23
 * 描  述：旅游产品详情
 */
public interface TravelProductDetView
{
    /**
     * 成功；
     *
     * @param data
     */
    void onProductDetSucess(boolean event_tag, TravelProduDetailBean data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onProductDetFail(String failMsg);
}
