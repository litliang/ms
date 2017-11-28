package com.yzb.card.king.ui.other.controller;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.user.IdentifyingCodeRequest;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.Map;

/**
 * 描述：验证码控制器
 * 作者：殷曙光
 * 日期：2016/12/30 11:57
 */
public class ValidCodeController
{
    public static final String REGISTER = "1";//注册
    public static final String RESET_LOGIN_PASSWORD = "2";//重置登录密码
    public static final String SEND_NEW_PASSWORD = "3";//发送新密码
    public static final String BIND_CARD = "4";//绑定信用卡
    public static final String VALID_MOBILE = "5";//验证手机

    private TextView tvGetCode;
    private String mobile;
    private String type;
    private int remainSeconds = 60;
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            remainSeconds--;
            tvGetCode.setText(getText());
        }

        @NonNull
        private String getText()
        {
            if (remainSeconds == 0)
            {
                tvGetCode.setEnabled(true);
                remainSeconds = 60;
                return "重新发送";
            } else
            {
                runTask();
                return remainSeconds + "秒后重发";
            }
        }
    };

    private void runTask()
    {
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    /**
     *
     * @param type 验证类型
     */
    public ValidCodeController(String type)
    {
        this.type = type;
    }

    /**
     * 获取平台验证码
     * @param mobile
     */
    public void getCode(String mobile)
    {
        this.mobile = mobile;
        if(RegexUtil.validPhoneNum(mobile))
        {
            IdentifyingCodeRequest request = new IdentifyingCodeRequest(mobile, "1",   type);

            request.sendRequest(new HttpCallBackData() {
                @Override
                public void onStart()
                {

                }

                @Override
                public void onSuccess(Object o)
                {

                    ToastUtil.i(GlobalApp.getInstance().getContext(),"验证码发送成功");
                }

                @Override
                public void onFailed(Object o)
                {

                    ToastUtil.i(GlobalApp.getInstance().getContext(),"验证码发失败");
                }

                @Override
                public void onCancelled(Object o)
                {
                    ToastUtil.i(GlobalApp.getInstance().getContext(),"验证码发失败");
                }

                @Override
                public void onFinished()
                {

                }
            });

        }
    }

    /**
     * 发送按钮60s倒计时任务
     *
     * @param tvGetCode 发送按钮
     */
    public void startTask(TextView tvGetCode)
    {
        this.tvGetCode = tvGetCode;
        tvGetCode.setEnabled(false);
        runTask();
    }

    /**
     * 验证验证码是否正确
     *
     * @param code            验证码
     * @param successListener 验证成功回调
     */
    public void validCode(String code,final OnSuccessListener successListener)
    {
        if (!TextUtils.isEmpty(code))
        {

            new IdentifyingCodeRequest(code, mobile, "1", type).sendRequest(new HttpCallBackData() {
                @Override
                public void onStart()
                {

                }

                @Override
                public void onSuccess(Object o)
                {
                    if (successListener != null)
                    {
                        successListener.onSuccess(null);
                    }
                }

                @Override
                public void onFailed(Object o)
                {
                    if (successListener != null)
                    {
                        successListener.onFail();
                    }
                }

                @Override
                public void onCancelled(Object o)
                {

                }

                @Override
                public void onFinished()
                {

                }
            });


        } else
        {
            UiUtils.shortToast("请输入验证码");
        }
    }

    public interface OnSuccessListener
    {
        void onSuccess(Map<String, String> result);

        void onFail();
    }
}
