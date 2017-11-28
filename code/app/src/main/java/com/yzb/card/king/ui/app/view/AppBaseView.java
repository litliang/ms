package com.yzb.card.king.ui.app.view;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/6/8
 * 描  述：
 */
public interface AppBaseView {

    /**
     * 获取成功；
     *
     * @param data
     */
    void onViewCallBackSucess(Object data);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onViewCallBackFail(String failMsg);
}
