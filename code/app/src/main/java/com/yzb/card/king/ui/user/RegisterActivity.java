package com.yzb.card.king.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.AuthCodeView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.user.presenter.RegisterPresenter;
import com.yzb.card.king.ui.user.view.RegisterView;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ValidatorUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by 玉兵 on 2016/6/19.
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity implements RegisterView {
    ///控件
    @ViewInject(R.id.tvTitle)
    private TextView tvTitle;

    @ViewInject(R.id.etPhone)
    private EditText etPhone;

    @ViewInject(R.id.etYanzhenMa)
    private EditText etYanzhenMa;

    @ViewInject(R.id.surepassword)
    private EditText surepassword;

    @ViewInject(R.id.tvGetCode)
    private TextView tvGetCode;

    @ViewInject(R.id.etMima)
    private EditText etMima;

    @ViewInject(R.id.ibPasswordVisible)
    private ImageButton ibPasswordVisible;

    private AuthCodeView authCodeView;

    //数据
    private boolean isVisible = false;

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        initData();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        registerPresenter = null;
    }

    private void initData()
    {

        registerPresenter = new RegisterPresenter(this, this);
        setPasswordVisible(isVisible);
    }

    private void initView()
    {

        tvTitle.setText(R.string.txt_register_str);

        authCodeView = new AuthCodeView(tvGetCode);
    }


    @Event(R.id.tvGetCode)
    private void tvGetCode(View v)
    {
        String str1 = etPhone.getText().toString().trim();

        authCodeView.sendCodeRequest(str1,"1", "1");

    }


    @Event(R.id.btRegister)
    private void btRegister(View v)
    {

        if (chechData())
        {

            registerPresenter.registerAction();

        }
    }


    private boolean chechData()
    {

        boolean flag = true;

        int str = 0;

        String str1 = etPhone.getText().toString().trim();

        String str2 = etYanzhenMa.getText().toString().trim();

        String str3 = etMima.getText().toString().trim();

        String str4 = surepassword.getText().toString().trim();

        if (TextUtils.isEmpty(str1))
        {

            flag = false;

            str = R.string.hint_phone_number;

        } else if (TextUtils.isEmpty(str2))
        {

            flag = false;

            str = R.string.hint_yanzhengma;

        } else if (TextUtils.isEmpty(str3))
        {

            flag = false;

            str = R.string.hint_mima;
        } else if (!ValidatorUtil.isMobile(str1))
        {

            flag = false;

            str = R.string.toast_chech_your_phone_number;

        } else if (!ValidatorUtil.isZcPassword(str3))
        {
            flag = false;

            str = R.string.toast_password_isSure;

        } else if (str3.equals(str1))
        {
            flag = false;

            str = R.string.txt_mima_equlas;
        } else if (!str3.equals(str4))
        {
            flag = false;

            str = R.string.txt_mima_two;
        }

        if (!flag)
        {
            ToastUtil.i(this,str);
        }

        return flag;
    }

    @Event(R.id.rlEye)
    private void rlEye(View v)
    {

        if (isVisible)
        {
            isVisible = false;
        } else
        {
            isVisible = true;
        }

        setPasswordVisible(isVisible);
    }

    @Event(R.id.ibPasswordVisible)
    private void ibPasswordVisible( View v){

        if (isVisible)
        {

            isVisible = false;
        } else
        {
            isVisible = true;
        }

        setPasswordVisible(isVisible);
    }


    /**
     * 设置密码显示状态
     *
     * @param isChecked
     */
    private void setPasswordVisible(boolean isChecked)
    {

        if (isChecked)
        {
            // 显示密码
            etMima.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            surepassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ibPasswordVisible.setBackgroundResource(R.mipmap.icon_eye_grey);
        } else
        {
            // 隐藏密码
            etMima.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            surepassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            ibPasswordVisible.setBackgroundResource(R.mipmap.password_black);
        }
    }


    @Override
    public String[] userRegisterInfor()
    {

        String str1 = etPhone.getText().toString().trim();

        String str2 = etYanzhenMa.getText().toString().trim();

        String str3 = etMima.getText().toString().trim();

        String[] strArray = new String[]{str1, str2, str3};

        return strArray;
    }

    @Override
    public void registerCallBack()
    {

        Intent data = new Intent();

        String str1 = etPhone.getText().toString().trim();

        data.putExtra("newAccountData",str1);

        setResult(1019,data);

        finish();

    }
}
