package com.yzb.card.king.ui.base;

/**
 * 支付密码验证；
 *
 * @author gengqiyun
 * @date 2016/10/20
 */
public interface ExistPayPwdListener
{
    /**
     * 密码验证成功；
     */
    void onListenSuccess();

    /**
     * @param code 结果码
     * @param msg  错误信息；
     */
    void onListenError(String code, String msg);
}
