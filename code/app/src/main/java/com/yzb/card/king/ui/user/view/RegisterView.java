package com.yzb.card.king.ui.user.view;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/8/9
 * 描  述：收集注册页面的数据
 */
public interface RegisterView {

    /**
     * 用户注册信息
     * @return
     */
    public String[] userRegisterInfor();

    /**
     * 注册请求成功后，回调到注册页面
     */
    public void registerCallBack();

}
