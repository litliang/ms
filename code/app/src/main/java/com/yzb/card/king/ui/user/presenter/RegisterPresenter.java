package com.yzb.card.king.ui.user.presenter;

import android.app.Activity;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.user.model.RegisterImpl;
import com.yzb.card.king.ui.user.model.Iregister;
import com.yzb.card.king.ui.user.view.RegisterView;

/**
 * 类  名：注册观察者
 * 作  者：Li Yubing
 * 日  期：2016/8/9
 * 描  述：
 */
public class RegisterPresenter implements DataCallBack {

    private RegisterView registerView;

    private Activity activity ;

    private Iregister dao ;

    public RegisterPresenter(RegisterView registerView, Activity activity){

        this.registerView = registerView;

        this.activity = activity;

        dao = new RegisterImpl(this);

    }

    /**
     * 注册动作
     */
    public void registerAction(){

        String[] strArray = registerView.userRegisterInfor();

        dao.sendRegisterRequest(strArray);

    }

    @Override
    public void requestSuccess(Object o, int type) {

        if( type == 1){

            registerView.registerCallBack();
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {

    }
}
