package com.yzb.card.king.ui.my.view;


/**
 * 功能：口令生成；
 *
 * @author:gengqiyun
 * @date: 2017/1/19
 */
public interface CommandView
{
    void onGetCommandSuc(String commandAndUrl);

    void onGetCommandFail(String failMsg);
}
