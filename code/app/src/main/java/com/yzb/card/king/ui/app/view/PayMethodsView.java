package com.yzb.card.king.ui.app.view;

import com.yzb.card.king.bean.common.PayMethod;

import java.util.List;

/**
 * 功能：支付方式；
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public interface PayMethodsView
{
    /**
     * @param data
     * @data flag
     */
    void onGetPayMethodListSucess(String flag, List<PayMethod> data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetPayMethodListFail(String failMsg);
}
