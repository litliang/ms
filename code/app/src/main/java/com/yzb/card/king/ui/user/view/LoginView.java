package com.yzb.card.king.ui.user.view;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/8/9
 * 描  述：负责收集登录页面数据，和回调服务器返回数据
 */
public interface LoginView {

    /**
     * 用户登录信息
     * @return
     */
    public String[] loginInfo();

    /**
     * 发送登录请求，成功后回调登录view
     */
    public void loginCallBack();

}
