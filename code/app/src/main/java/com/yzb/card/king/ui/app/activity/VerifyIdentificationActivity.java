package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.popup.IdentificationsPopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.CertType;
import com.yzb.card.king.ui.other.controller.ValidCodeController;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.SMSBroadcastHelper;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.ViewUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.19
 * 验证身份证件；
 */
public class VerifyIdentificationActivity extends BaseActivity implements View.OnClickListener,
        OnItemClickListener<CertType> {
    private EditText etName;
    private EditText etIdNumber;
    private EditText etBindedPhone;
    private EditText etMsgCode;

    private IdentificationsPopup identificationsPopup;
    private CertType certType;
    private View llIdentification;
    private TextView tvCardName;
    private TextView tvGetCode;
    private ValidCodeController codeController;
    private String source;//上个Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_identification);
        getIntentData();
        assignViews();
        SMSBroadcastHelper.registerBroadcast(etMsgCode);
        initData();
    }

    private void getIntentData() {
        source = getIntent().getStringExtra("source");
    }

    private void initData() {
        identificationsPopup = new IdentificationsPopup(this);
        certType = identificationsPopup.getDefaultValue();

        codeController = new ValidCodeController(ValidCodeController.VALID_MOBILE);
    }

    private void assignViews() {
        setTitleNmae(getHeader());

        etName = (EditText) findViewById(R.id.etName);
        etIdNumber = (EditText) findViewById(R.id.etCardNum);
        tvCardName = (TextView) findViewById(R.id.tvCardName);
        ViewUtil.showPwdText(etIdNumber);

        etBindedPhone = (EditText) findViewById(R.id.tv_binded_phone);

        UserBean userBean = UserManager.getInstance().getUserBean();
        if (userBean != null && !TextUtils.isEmpty(userBean.getAccount())) {
            etBindedPhone.setText(userBean.getAccount());
        }
        etMsgCode = (EditText) findViewById(R.id.et_msg_code);


        findViewById(R.id.tv_safe_verify).setOnClickListener(this);
        tvGetCode = (TextView) findViewById(R.id.tv_get_msg_code);
        llIdentification = findViewById(R.id.llIdentification);
        llIdentification.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
    }

    private String getHeader() {
        if ("ResetPayPwdActivity".equals(source)) {
            return getString(R.string.setting_reset_pay_pwd);
        } else {
            return getString(R.string.setting_reset_login_pwd);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_get_msg_code: // 获取验证码；
                if (isInputRight()) {
                    safeVerify();
                }
                break;
            case R.id.tv_safe_verify:// 进行安全验证；
                validMsgCode();
                break;
            case R.id.llIdentification://选择证件类型
                identificationsPopup.setWidth(llIdentification.getWidth());
                identificationsPopup.showAsDropDown(v, 0, 5);
                break;
        }
    }

    private void validMsgCode() {
        if (validCode()) {
            codeController.validCode(getCode(), new ValidCodeController.OnSuccessListener() {
                @Override
                public void onSuccess(Map<String, String> result) {
                    nextActivity();
                }

                @Override
                public void onFail() {
                    UiUtils.shortToast("验证码错误！");
                }
            });
        }
    }

    private void nextActivity() {
        if ("ResetPayPwdActivity".equals(source)) {
            Intent it = new Intent(VerifyIdentificationActivity.this, ResetLoginPwdActivity.class);
            it.putExtra("userPhone", etBindedPhone.getText().toString().trim());
            startActivity(it);
        } else {
            Intent it = new Intent(VerifyIdentificationActivity.this, ResetLoginPwdActivity.class);
            it.putExtra("userPhone", etBindedPhone.getText().toString().trim());
            startActivity(it);
        }
    }

    private String getCode() {
        return etMsgCode.getText().toString().trim();
    }

    /**
     * 登录密码--安全验证；
     * 1，先验证姓名，身份证号；
     * 2, 成功后再验证手机号码；
     */

    private void safeVerify() {

        getVerifyCode();
        if (true) {
            return;
        }

        showPDialog("");
        final String name = etName.getText().toString().trim();
        final String idNumber = etIdNumber.getText().toString().trim();

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("type", "2");//类别（1密码；2证件）
        params.put("certType", certType.getType());//证件类型（1身份证）
        params.put("certNo", idNumber);//证件号码
        params.put("provingMobile", etBindedPhone.getText().toString().trim());

        SimpleRequest<String> request = new SimpleRequest<String>(CardConstant.setting_provingcustomerinfo) {
            @Override
            protected String parseData(String data) {
                return data;
            }
        };
        request.setParam(params);
        request.sendRequestNew(new BaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                closePDialog();

            }

            @Override
            public void onFail(Error error) {
                closePDialog();
                UiUtils.shortToast("身份信息验证失败");
            }
        });
    }


    /**
     * 获取验证码；
     */
    private void getVerifyCode() {
        String etPhone = etBindedPhone.getText().toString().trim();
        codeController.getCode(etPhone);
        codeController.startTask(tvGetCode);
    }

    private boolean isInputRight() {
        String name = etName.getText().toString().trim();
//        if (TextUtils.isEmpty(name)) {
//            toastCustom("请输入姓名");
//            return false;
//        }
//        String idNumber = etIdNumber.getText().toString().trim();
//        if (TextUtils.isEmpty(idNumber)) {
//            toastCustom("请输入证件号码");
//            return false;
//        }

        String bindedPhone = etBindedPhone.getText().toString().trim();
        if (TextUtils.isEmpty(bindedPhone)) {
            toastCustom(R.string.setting_phone_not_empty);
            return false;
        }

        if (!(bindedPhone.contains("*") ? (bindedPhone.length() == 11) : RegexUtil.validPhoneNum(bindedPhone))) {
            toastCustom(R.string.toast_chech_your_phone_number);
            return false;
        }

        return true;
    }

    private boolean validCode() {
        String msgCode = etMsgCode.getText().toString().trim();
        if (TextUtils.isEmpty(msgCode)) {
            toastCustom("请输入验证码");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSBroadcastHelper.unRegisterBroadcast();
    }

    @Override
    public void onItemClick(CertType data) {
        setCertType(data);
    }

    public void setCertType(CertType certType) {
        this.certType = certType;
        tvCardName.setText(certType.getName());
    }
}
