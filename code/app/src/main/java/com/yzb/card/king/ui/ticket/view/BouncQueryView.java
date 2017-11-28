package com.yzb.card.king.ui.ticket.view;


import com.yzb.card.king.bean.ticket.BouncQueryBean;

import java.util.List;

/**
 * 功能：退票查询；
 *
 * @author:gengqiyun
 * @date: 2016/12/2
 */
public interface BouncQueryView
{
    /**
     * 获取成功；
     * @param accountBeans
     */
    void onBouncQuerySucess(BouncQueryBean accountBeans);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onBouncQueryFail(String failMsg);
}
