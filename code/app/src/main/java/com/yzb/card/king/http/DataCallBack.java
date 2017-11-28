package com.yzb.card.king.http;

/**
 * 类  名：MVP模式M层公共的数据回调接口类
 * 作  者：Li Yubing
 * 日  期：2016/8/16
 * 描  述：
 */
public interface DataCallBack {

    /**
     * 请求成功回调数据
     * @param o
     * @param type
     */
    void requestSuccess(Object o,int type);

    /**
     * 网络请求出现异常，数据回调方法
     * @param o
     * @param type
     */
    public void requestFailedDataCall(Object o,int type);


}
