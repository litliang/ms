package com.yzb.card.king.ui.ticket.view;


import com.yzb.card.king.bean.ticket.OrderIdBean;

import java.util.List;

/**
 * 功能：改签确认
 * @author:gengqiyun
 * @date: 2016/12/2
 */
public interface ReBookView
{
    /**
     * 获取成功；
     *
     * @param orderIdBeans
     */
    void onReBookSucess(OrderIdBean orderIdBeans);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onReBookFail(String failMsg);
}
