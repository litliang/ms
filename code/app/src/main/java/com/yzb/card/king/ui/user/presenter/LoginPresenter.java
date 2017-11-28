package com.yzb.card.king.ui.user.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.manage.CreditCardManager;
import com.yzb.card.king.util.PreferencesService;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.user.model.Ilogin;
import com.yzb.card.king.ui.user.model.LoginImpl;
import com.yzb.card.king.ui.user.view.LoginView;
import com.yzb.card.king.util.ProgressDialogUtil;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 类  名：登录观察者
 * 作  者：Li Yubing
 * 日  期：2016/8/9
 * 描  述：管理登录、登出业务
 */
public class LoginPresenter implements DataCallBack {

    private LoginView loginView;

    protected BaseViewLayerInterface baseView;

    public Activity activity;

    private LoginImpl impl;

    public LoginPresenter(LoginView loginView, Activity activity) {

        this.loginView = loginView;

        this.activity = activity;

        impl = new LoginImpl(this);

    }

    public LoginPresenter(Activity activity) {

        this.activity = activity;
        impl = new LoginImpl(this);
    }

    public LoginPresenter(BaseViewLayerInterface baseView) {

        this.baseView = baseView;

        impl = new LoginImpl(this);

    }

    /**
     * 登录动作
     *
     * @param type 1：账号登录；2：手机登录
     */
    public void loginAction(int type) {

        sendLoginRequest(type);
    }

    /**
     * 发送登录请求
     *
     * @param type
     */
    private void sendLoginRequest(int type) {

        String[] userInfo = loginView.loginInfo();

        String str1 = userInfo[0];

        String str2 = userInfo[1];

        impl.sendLoginRequest(str1, str2, type);

    }


    /**
     * 发送登录者账户请求
     */
    public void sendPersonInfoRequest() {
        //检测是否自动登录
        if (!UserManager.getInstance().isLogin()) {
            //检测是否保存session
            PreferencesService pre = new PreferencesService(GlobalApp.getInstance().getContext());

            String sessionIdTemp = pre.getValue("sessionId");

            if (!TextUtils.isEmpty(sessionIdTemp)) {

                AppConstant.sessionId = sessionIdTemp;

                impl.sendSelfLoginRequest();

            }
        }
    }
    /**
     * 发送登出请求
     */
    public void sendLogOutRequest() {

        impl.sendLogoutRequest();

    }


    @Override
    public void requestSuccess(Object o, int type) {

        if (type == Ilogin.LOGOUT_CODE) {

            //清理用户数据
            //  impl.delUserInfo(impl.checkUserInfo());
            baseView.callSuccessViewLogic(null,1);

        } else if (type == Ilogin.LOGIN_CODE) {


            CreditCardManager.getInstance().setIfRefresh(true);

            UserBean userBean = (UserBean) o;

            userBean.setCreditCardNum(0);

            UserManager.getInstance().setUserBean(userBean);

            //保存账户类型
            //存储
            PreferencesService pre = new PreferencesService(GlobalApp.getInstance().getContext());
            pre.updatePreferences("customerType", userBean.getCustomerType());

            //发送请求下载用户的keyStore文件，放在app_keystore文件夹下，并以用户的手机号保存

            //发送更新推送标记请求
            Map<String, Object> paramMap = new HashMap<String, Object>();

            String sysCode = null;

            if( GlobalApp.regIdList.size()>1){

                sysCode = GlobalApp.regIdList.get(0).get("regId");
            }

            if (TextUtils.isEmpty(sysCode)) {

                sysCode = JPushInterface.getRegistrationID(GlobalApp.getInstance().getContext());
            }
            paramMap.put("pushCode", sysCode);

            new SimpleRequest(CardConstant.card_app_updatePerson, paramMap, new BaseRequest.Listener()
            {
                @Override
                public void onSuccess(String data)
                {
                }
                @Override
                public void onFail(String failMsg)
                {
                }
            }).sendPostRequest();


        } else if (type == Ilogin.BINDING_CARD_CODE) {

            UserBean userBean = UserManager.getInstance().getUserBean();

            int a = (int) o;

            userBean.setCreditCardNum(a);


            if (loginView != null) {


                loginView.loginCallBack();
            }
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {

    }
}

