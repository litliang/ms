package com.yzb.card.king.ui.user.model;

import com.yzb.card.king.bean.user.UserBean;

/**
 * 类  名：登录的业务数据接口
 * 作  者：Li Yubing
 * 日  期：2016/8/12
 * 描  述：
 */
public interface Ilogin {

    /**
     * 登录请求识别符
     */
    public static final int  LOGIN_CODE = 1;

    /**
     * 登出请求识别符
     */
    public static final int LOGOUT_CODE = 2;

    /**
     * 用户已绑卡请求识别符
     */
    public static final int BINDING_CARD_CODE = 3;

    /**
     * 存储用户信息
     * @param bean
     */
    public void saveUserInfo(UserBean bean);

    /**
     * 查询用户信息
     * @return
     */
    public UserBean checkUserInfo();

    /**
     * 删除用户信息
     */
    public void delUserInfo(UserBean bean);

    /**
     * 发起登录请求
     * @param loginName  登录名:手机号等
     * @param loginKey   登录关键：密码、验证码
     * @param type       登录类型
     */
    public void  sendLoginRequest(String loginName,String loginKey,int type);

    /**
     * 登出
     */
    public void sendLogoutRequest();

    /**
     * 发送自动登录
     */
    public void sendSelfLoginRequest();



}
