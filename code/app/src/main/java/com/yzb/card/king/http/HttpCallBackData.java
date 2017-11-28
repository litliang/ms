package com.yzb.card.king.http;

/**
 * 类  名：请求回调接口
 * 作  者：Li Yubing
 * 日  期：2016/8/5
 * 描  述：
 */
public interface  HttpCallBackData {

    /**
     * 请求开始
     */
    public void onStart();
    /**
     * 成功信息
     * @param o
     */
    public void onSuccess( Object o);

    /**
     * 失败信息
     * @param o
     */
    public void onFailed( Object o);

    /**
     * 取消
     * @param o
     */
    public void onCancelled(Object o);

    /**
     * 请求结束
     */
    public void onFinished();


}
