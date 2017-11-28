package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.base.BaseValidMobileActivity;
import com.yzb.card.king.ui.app.holder.BindPhoneHolder;
import com.yzb.card.king.ui.app.presenter.UpdateUserInfoPresenter;
import com.yzb.card.king.ui.app.presenter.ValidPhonePresenter;
import com.yzb.card.king.ui.app.presenter.VerifyCodeValidityPresenter;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.other.controller.ValidCodeController;
import com.yzb.card.king.util.SMSBroadcastHelper;
import com.yzb.card.king.util.UiUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名：ModifyBindPhoneActivity
 * 作者：殷曙光
 * 日期：2016/6/21 17:23
 * 描述：
 */
public class ModifyBindPhoneActivity extends BaseValidMobileActivity implements BaseViewLayerInterface
{
    protected int step = 0;

    protected BindPhoneHolder holder;

    private ValidPhonePresenter validPhonePresenter;//验证手机号；

    private VerifyCodeValidityPresenter verifyMobilePresenter;//验证验证码是否正确；

    private UpdateUserInfoPresenter updateUserInfoPresenter;

    @Override
    protected View getContent()
    {
        validPhonePresenter = new ValidPhonePresenter(this);
        verifyMobilePresenter = new VerifyCodeValidityPresenter(this);
        updateUserInfoPresenter = new UpdateUserInfoPresenter(this);
        holder = new BindPhoneHolder();
        holder.activity = this;
        holder.setData(new Object());
        SMSBroadcastHelper.registerBroadcast(holder.etCode);
        return holder.getConvertView();
    }

    @Override
    protected void initData()
    {
        step = getIntent().getIntExtra("step", 1);
        setStep(step);
        if (step == 1)
        {
            goStep1();
        } else
        {
            goStep2();
        }

    }

    @Override
    protected String setMidTitle()
    {
        return getString(R.string.setting_modify_binding_phone);
    }

    @Override
    protected String getButtonText()
    {
        return getString(R.string.setting_next_step);
    }

    @Override
    protected void onTvButtonClick()
    {
        if (!holder.valid()) return;
        Map<String, Object> params = new HashMap<>();
        params.put("smsCode", holder.code);
        params.put("mobile", holder.phone);
        params.put("type", ValidCodeController.VALID_MOBILE);
        verifyMobilePresenter.loadData(params);
    }


    private void goStep2()
    {
        setTvButton(getString(R.string.finish));
        holder.etPhone.setEnabled(true);
        holder.etPhone.addTextChangedListener(new MyTextWatcher()
        {
            @Override
            public void afterTextChange(String s)
            {
                if (s.length() == 11)
                {
                    startValidPhone(s);
                }
            }
        });
        holder.time = 0;
        holder.emptyData();
        holder.tvMobileLabel.setText(getString(R.string.setting_new_binded_phone));
        llForget.setVisibility(View.GONE);
    }

    /**
     * 开始验证手机号；
     *
     * @param phone
     */
    private void startValidPhone(String phone)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", phone);
        validPhonePresenter.loadData(params);
    }


    private void goStep1()
    {
        llForget.setVisibility(View.VISIBLE);
        llForget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ModifyBindPhoneActivity.this,
                        VerifyIdentificationNeedBankActivity.class);
                intent.putExtra(VerifyIdentificationNeedBankActivity.INTENT_KEY,
                        VerifyIdentificationNeedBankActivity.MODIFY_BIND_PHONE_ACTIVITY);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        if (holder != null)
            SMSBroadcastHelper.registerBroadcast(holder.etCode);
    }

    @Override
    protected void onDestroy()
    {
        SMSBroadcastHelper.unRegisterBroadcast();
        super.onDestroy();
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if(type == 1){
            UiUtils.shortToast(getString(R.string.setting_verify_code_right));

            if (currentStep == 1)
            {
                setStep(2);

                goStep2();

            } else if (currentStep == 2)
            {

                Map<String, Object> param = holder.saveBindNum();

                updateUserInfoPresenter.update(param);

                startActivity(new Intent(ModifyBindPhoneActivity.this, ValidFinishActivity.class));
            }
        }else if( type == 3){

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if( type == 1){
            toastCustom(R.string.setting_verifycode_error);
        }else if(type==2){
            toastCustom(o+"");
            holder.etPhone.setText("");
        }else if( type==3){

        }

    }
}
