package com.yzb.card.king.ui.user.model;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.user.UserRegisterRequest;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.Map;

/**
 * 类  名：实现注册业务的具体方法
 * 作  者：Li Yubing
 * 日  期：2016/8/16
 * 描  述：
 */
public class RegisterImpl implements Iregister {

    private DataCallBack dataCallBack;

    public RegisterImpl(DataCallBack dataCallBack)
    {

        this.dataCallBack = dataCallBack;

    }

    @Override
    public void sendRegisterRequest(String[] strArray)
    {

        final String str1 = strArray[0];

        String str2 = strArray[1];

        String str3 = strArray[2];

        new UserRegisterRequest(str1, str3, str2).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {
                ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);
            }

            @Override
            public void onSuccess(Object o)
            {

                ToastUtil.i(GlobalApp.getInstance().getContext(), "注册成功");
                //             String customerId = String.valueOf(o);
                //注册成功后，发起开户请求

                dataCallBack.requestSuccess(null, 1);
            }

            @Override
            public void onFailed(Object o)
            {

                if (o != null && o instanceof Map) {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                }

            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {
                ProgressDialogUtil.getInstance().closeProgressDialog();

            }
        });

    }
}
