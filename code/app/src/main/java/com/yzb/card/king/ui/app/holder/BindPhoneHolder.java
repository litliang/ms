package com.yzb.card.king.ui.app.holder;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.user.IdentifyingCodeRequest;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.controller.ValidCodeController;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/21 13:09
 * 描述：
 */
public class BindPhoneHolder extends Holder implements View.OnClickListener
{
    private View view;
    public EditText etPhone;
    public EditText etCode;
    public TextView tvGetCode;

    public String phone;
    public String code;
    public TextView tvMobileLabel;

    public Activity activity;
    private View llLost;
    private View btNext;

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

    @Override
    public View initView()
    {
        view = UiUtils.inflate(R.layout.settings_bind_phone_content);
        etPhone = (EditText) view.findViewById(R.id.etPhone);
        etCode = (EditText) view.findViewById(R.id.etCode);
        tvGetCode = (TextView) view.findViewById(R.id.tvGetCode);
        tvMobileLabel = (TextView) view.findViewById(R.id.tvMobileLabel);
        tvGetCode.setOnClickListener(this);
        llLost = view.findViewById(R.id.llLost);
        btNext = view.findViewById(R.id.btNext);
        initListener();
        return view;
    }

    private void initListener()
    {
        llLost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        btNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }

    @Override
    public void refreshView(Object data)
    {
        phone = UserManager.getInstance().getUserBean().getAccount();
        etPhone.setText(phone);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvGetCode:
                if (RegexUtil.validPhoneNum(phone))
                {
                    IdentifyingCodeRequest request = new IdentifyingCodeRequest(phone, "1",   ValidCodeController.VALID_MOBILE);

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

                    tvGetCode.setEnabled(false);
                    runTask();
                }
                break;
        }
    }

    public int time;


    boolean flag = true;

    public boolean valid()
    {
        flag = true;
        code = String.valueOf(etCode.getText());
        phone = etPhone.isEnabled() ? String.valueOf(etPhone.getText()) :
                UserManager.getInstance().getUserBean().getAccount();
        if (!RegexUtil.validPhoneNum(phone))
        {
            return false;
        }
        if (TextUtils.isEmpty(code))
        {
            flag = false;
            UiUtils.shortToast("请输入验证码");
        }
        return flag;
    }

    public void emptyData()
    {
        etPhone.setText(null);
        etCode.setText(null);
    }

    public Map<String, Object> saveBindNum()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("account",phone);
        return param;
    }
}
