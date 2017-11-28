package com.yzb.card.king.ui.app.view;

/**
 * 功能：设置--支付密码验证；
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public interface ExistPayPwdView
{
    /**
     * 支付密码验证成功；
     */
    void onExistPayPwdSucess();

    /**
     */
    void onExistPayPwdFail(String code, String msg);
}
