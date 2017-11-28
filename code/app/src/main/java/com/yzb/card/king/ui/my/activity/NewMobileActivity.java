package com.yzb.card.king.ui.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.presenter.UpdateUserInfoPresenter;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.other.controller.ValidCodeController;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：输入新手机号
 * 作者：殷曙光
 * 日期：2016/12/30 11:37
 */
@ContentView(R.layout.activity_new_mobile)
public class NewMobileActivity extends BaseActivity implements BaseViewLayerInterface {

    @ViewInject(R.id.etMobile)
    private EditText etMobile;

    @ViewInject(R.id.etCode)
    private EditText etCode;

    @ViewInject(R.id.tvGetCode)
    private TextView tvGetCode;

    private ValidCodeController codeController;
    private UpdateUserInfoPresenter presenter;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData()
    {
        setTitleNmae("修改生活号");
        codeController = new ValidCodeController(ValidCodeController.VALID_MOBILE);
        presenter = new UpdateUserInfoPresenter(this);
    }

    @Event(R.id.tvGetCode)
    private void getCode(View view)
    {
        mobile = etMobile.getText().toString();
        codeController.getCode(mobile);
        codeController.startTask(tvGetCode);
    }

    @Event(R.id.btNext)
    private void next(View view)
    {
        codeController.validCode(getValidCode(), new ValidCodeController.OnSuccessListener() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("account", mobile);
                presenter.update(param);
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


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        UiUtils.shortToast(o + "");
    }
}
