package com.yzb.card.king.ui.other.listeners;

import com.yzb.card.king.ui.app.activity.VerifyIdentificationActivity;
import com.yzb.card.king.util.UiUtils;
import com.yzb.wallet.openInterface.ForgetPwdBackListener;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/13 17:50
 */
public class ForgetPwdImpl implements ForgetPwdBackListener
{
    @Override
    public void callBack()
    {
        UiUtils.startActivity(VerifyIdentificationActivity.class);
    }
}
