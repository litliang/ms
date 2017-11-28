package com.yzb.card.king.ui.travel.view;

/**
 * 类  名：(机票购票)
 * 作  者：gengqiyun
 * 日  期：2016/11/29
 * 描  述：
 */
public interface FlightBookView
{

    /**
     * 成功；
     */
    void onFlightBookSucess();

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onFlightBookFail(String failMsg);
}
