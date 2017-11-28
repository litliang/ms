package com.yzb.card.king.ui.my.view;

import com.yzb.card.king.bean.my.KeyInfoBean;

/**
 * 功能：口令信息；
 *
 * @author:gengqiyun
 * @date: 2016/12/28
 */
public interface KeyInfoView
{
    void onGetKeyInfoSuc(KeyInfoBean keyInfoBean);

    void onGetKeyInfoFail(String failMsg);
}
