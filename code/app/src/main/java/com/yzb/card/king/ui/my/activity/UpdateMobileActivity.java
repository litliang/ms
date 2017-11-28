package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.controller.ValidCodeController;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * 描述：绑定手机
 * 作者：殷曙光
 * 日期：2016/12/29 18:00
 */
@ContentView(R.layout.activity_update_mobile)
public class UpdateMobileActivity extends BaseActivity
{
    public static final int REQ_NEW_MOBILE = 2;
    private static final int REQ_LOST_PHONE = 3;

    @ViewInject(R.id.etPhone)
    private EditText etPhone;

    @ViewInject(R.id.etCode)
    private EditText etCode;

    @ViewInject(R.id.tvGetCode)
    private TextView tvGetCode;

    private String phone;

    private ValidCodeController codeController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView()
    {

    }

    private void initData()
    {
        setTitleNmae("修改生活号");

        phone = UserManager.getInstance().getUserBean().getAccount();
        etPhone.setText(phone);
        codeController = new ValidCodeController(ValidCodeController.VALID_MOBILE);
    }

    @Event(R.id.btNext)
    private void next(View v)
    {
        codeController.validCode(getValidCode(), new ValidCodeController.OnSuccessListener()
        {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                Intent intent = new Intent(UpdateMobileActivity.this, NewMobileActivity.class);
                startActivityForResult(intent, REQ_NEW_MOBILE);
            }

            @Override
            public void onFail()
            {
                UiUtils.shortToast("验证码错误");
            }
        });
    }

    private String getValidCode()
    {
        return etCode.getText().toString();
    }

    @Event(R.id.llLost)
    private void lostPhone(View v)
    {
        startActivityForResult(new Intent(this,LostPhoneActivity.class),REQ_LOST_PHONE);
    }


    @Event(R.id.tvGetCode)
    private void getCode(View v)
    {
        codeController.getCode(phone);
        codeController.startTask(tvGetCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_NEW_MOBILE:
                case REQ_LOST_PHONE:
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }
}
