package com.yzb.card.king.ui.app.presenter;

import android.app.Activity;
import android.graphics.Bitmap;

import com.yzb.card.king.ui.app.model.IUserCenter;
import com.yzb.card.king.ui.app.model.UserCenterImpl;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/14 9:57
 */
public class UserCenterPres implements UserCenterImpl.CallBack
{

    private IUserCenter model;

    public UserCenterPres()
    {
        model = new UserCenterImpl(this);
    }

    public void uploadImage(Bitmap bitmap)
    {
        model.upLoadImage(bitmap);
    }

    public void getConsume(Activity activity)
    {
        model.getConsume(activity);
    }

    public void getUserInfo()
    {
        model.getUserInfo();
    }

    @Override
    public void notifyDataChanged()
    {

    }

}
