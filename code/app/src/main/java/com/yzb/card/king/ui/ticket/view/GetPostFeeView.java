package com.yzb.card.king.ui.ticket.view;


/**
 * 功能：邮费列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public interface GetPostFeeView
{
    /**
     * 获取成功；
     */
    void onGetPostFeeSucess(Object data);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetPostFeeFail(String failMsg);
}
