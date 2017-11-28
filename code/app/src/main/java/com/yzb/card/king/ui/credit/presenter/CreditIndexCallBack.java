package com.yzb.card.king.ui.credit.presenter;

import com.yzb.card.king.http.DataCallBack;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/8/16
 * 描  述：
 */
public interface CreditIndexCallBack extends DataCallBack{

    /**
     * 网络请求出现异常，数据回调方法
     * @param o
     * @param type
     */
    public void requestFailedDataCall(Object o,int type);

}
