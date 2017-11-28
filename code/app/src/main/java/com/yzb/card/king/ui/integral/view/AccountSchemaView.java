package com.yzb.card.king.ui.integral.view;

import android.graphics.drawable.Drawable;

import com.yzb.card.king.bean.integral.AccountSchemaBean;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 11:11
 */
public interface AccountSchemaView
{
    void updateUi(AccountSchemaBean bean);

    void userIntegralCallBack(Object o);

    void userGiftCallBack(Object o);

    void userMoneyCallBack(Object o);

    void userRedPackCallBack(Object o);

    void onImageLoadUp(Drawable result);
}
