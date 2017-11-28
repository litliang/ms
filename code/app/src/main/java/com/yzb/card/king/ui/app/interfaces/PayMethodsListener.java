package com.yzb.card.king.ui.app.interfaces;

import com.yzb.card.king.bean.common.PayMethod;

import java.util.List;

/**
 * 功能：付款方式listener；
 *
 * @author:gengqiyun
 * @date: 2016/10/21
 */
public interface PayMethodsListener
{
    /**
     * 获取成功；
     *
     * @param tag  0：默认；1：有序；
     * @param data
     */
    void onListenSuccess(String tag, List<PayMethod> data);

    void onListenError(String failMsg);
}
