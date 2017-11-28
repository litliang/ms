package com.yzb.card.king.ui.app.activity.ia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.UserAuthenticationBean;
import com.yzb.card.king.ui.appwidget.AuthCodeView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import static com.yzb.card.king.ui.app.activity.ia.ExamineIaActivity.USER_AUTHENTICATION_DATA;
import static com.yzb.card.king.ui.app.activity.ia.VerifyResultActivity.VERIFY_RESULT_TAG;
import static com.yzb.card.king.ui.credit.bean.CardBin.CARD_CREDIT;

/**
 * 类  名：验证手机号
 * 作  者：Li Yubing
 * 日  期：2017/10/14
 * 描  述：
 */
@ContentView(R.layout.activity_verify_phone_number)
public class VerifyPhoneNumberActivity extends BaseActivity {

    @ViewInject(R.id.tvPhoneNumber)
    private TextView tvPhoneNumber;

    @ViewInject(R.id.tvGetCode)
    private TextView tvGetCode;

    @ViewInject(R.id.etPhoneCode)
    private EditText etPhoneCode;

    private AuthCodeView authCodeView;

    private UserAuthenticationBean userAuthenticationBean;

    private  String amount;

    private   String fee;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData()
    {
        if (getIntent().hasExtra(USER_AUTHENTICATION_DATA)) {

            userAuthenticationBean = (UserAuthenticationBean) getIntent().getSerializableExtra(USER_AUTHENTICATION_DATA);

            tvPhoneNumber.setText(RegexUtil.hide5PhoneNum(userAuthenticationBean.getMobile()));

            if(userAuthenticationBean.getData() != null && userAuthenticationBean.isPaymentMoneyIf()){

                amount = getIntent().getStringExtra("amount");

                fee = getIntent().getStringExtra("fee");
            }
        }
    }

    @Event(R.id.tvNextStep)
    private void tvNextStep(View view)
    {

        String etPhoneCodeStr = etPhoneCode.getText().toString().trim();

        if (TextUtils.isEmpty(etPhoneCodeStr)) {

            ToastUtil.i(this, "请输入验证码");

            return;
        }

        ProgressDialogUtil.getInstance().showProgressDialogMsg("验证信息……", this, false);

        boolean paymentMoneyIf = userAuthenticationBean.isPaymentMoneyIf();

        if (paymentMoneyIf && userAuthenticationBean.getData() != null) {
            //他人代换
            authCodeView.quickPayRequest(userAuthenticationBean,amount,fee,etPhoneCodeStr);

        } else {

            int cardType = userAuthenticationBean.getCardType();


            authCodeView.checkPhoneCodeRequest(etPhoneCodeStr, cardType, userAuthenticationBean.getCertNo(), userAuthenticationBean.getBin(), userAuthenticationBean.getBankName(), userAuthenticationBean.getBankCode());
        }
    }

    private void initView()
    {
        setTitleNmae("验证手机号");

        authCodeView = new AuthCodeView(tvGetCode);

        authCodeView.setDataHandler(dataHandler);
    }

    @Event(R.id.tvGetCode)
    private void tvGetCode(View v)
    {
        String str1 = userAuthenticationBean.getMobile();

        authCodeView.sendOtherBankValidCodeRequest(str1, userAuthenticationBean.getBankId(), userAuthenticationBean.getCardNo(), userAuthenticationBean.getCardType(), userAuthenticationBean.getCertType(), userAuthenticationBean.getRealName(), userAuthenticationBean.getValidityPeriod(), userAuthenticationBean.getCvv2(), "0.01", userAuthenticationBean.getBankCode(), userAuthenticationBean.getCertNo());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VerifyResultActivity.RESULT_REQUEST_CODE) {

            if (resultCode == VerifyResultActivity.RESULT_RESULT_SUCCESS) {

                setResult(resultCode);

                finish();
            } else if (resultCode == VerifyResultActivity.RESULT_RESULT_REVERIFY) {
                setResult(resultCode);
                finish();
            }

        }

    }

    private Handler dataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            ProgressDialogUtil.getInstance().closeProgressDialog();

            int what = msg.what;

            boolean paymentMoneyIf = userAuthenticationBean.isPaymentMoneyIf();

            if (paymentMoneyIf) {
                //还款

                return;
            }

            if (what == 0) {//成功

                Intent intent = new Intent(VerifyPhoneNumberActivity.this, VerifyResultActivity.class);

                intent.putExtra(VERIFY_RESULT_TAG, 0);
                intent.putExtra(USER_AUTHENTICATION_DATA, userAuthenticationBean);
                startActivityForResult(intent, VerifyResultActivity.RESULT_REQUEST_CODE);

            } else if (what == 1) {//失败
                Intent intent = new Intent(VerifyPhoneNumberActivity.this, VerifyResultActivity.class);

                intent.putExtra(VERIFY_RESULT_TAG, 1);

                intent.putExtra(USER_AUTHENTICATION_DATA, userAuthenticationBean);
                startActivityForResult(intent, VerifyResultActivity.RESULT_REQUEST_CODE);
            }

        }
    };
}
