package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.bean.user.UserBean;

/**
 * 功能：更新用户信息
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public interface UpdateUserInfoCallBack
{
    /**
     */
    void onListenSuccess(UserBean userBean);

    void onListenError(String msg);
}
