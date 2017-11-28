package com.yzb.card.king.ui.app.activity.ia;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.UserAuthenticationBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.interfaces.OnSelectedListener;
import com.yzb.card.king.ui.credit.popup.ValidityDayDialog;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import static com.yzb.card.king.ui.app.activity.ia.ExamineIaActivity.USER_AUTHENTICATION_DATA;
import static com.yzb.card.king.ui.credit.bean.CardBin.CARD_CREDIT;
import static com.yzb.card.king.ui.credit.bean.CardBin.CARD_DEBIT;

/**
 * 类  名：银行信息
 * 作  者：Li Yubing
 * 日  期：2017/10/14
 * 描  述：储蓄卡、信用卡
 */
@ContentView(R.layout.activity_ia_bankinfo)
public class IaBankInfoActivity extends BaseActivity {

    @ViewInject(R.id.tvCarNo)
    private TextView tvCarNo;

    @ViewInject(R.id.llBankDepositCard)
    private LinearLayout llBankDepositCard;//储蓄卡布局

    @ViewInject(R.id.etPhoneNumberOne)
    private EditText etPhoneNumberOne;

    @ViewInject(R.id.llBankCreditCard)
    private LinearLayout llBankCreditCard;//信用卡布局

    @ViewInject(R.id.tvBankCardNoTwo)
    private TextView tvBankCardNoTwo;

    @ViewInject(R.id.tvRealName)
    private TextView tvRealName;

    @ViewInject(R.id.tvYouxiaoqi)
    private TextView tvYouxiaoqi;

    @ViewInject(R.id.tvCertTypeName)
    private TextView tvCertTypeName;

    @ViewInject(R.id.tvCertNoTwo)
    private TextView tvCertNoTwo;

    @ViewInject(R.id.etPhoneNumberTwo)
    private EditText etPhoneNumberTwo;

    @ViewInject(R.id.tvAnquanma)
    private EditText tvAnquanma;

    private UserAuthenticationBean userAuthenticationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        initData();
    }

    private void initData()
    {
        if(getIntent().hasExtra(USER_AUTHENTICATION_DATA)){

            userAuthenticationBean = (UserAuthenticationBean) getIntent().getSerializableExtra(USER_AUTHENTICATION_DATA);

            int bankTypeCode = userAuthenticationBean.getCardType();

            if(bankTypeCode == CARD_DEBIT){//储蓄卡

                llBankDepositCard.setVisibility(View.VISIBLE);

                llBankCreditCard.setVisibility(View.GONE);

                tvCarNo.setText(userAuthenticationBean.getCardNo());

            }else if(bankTypeCode == CARD_CREDIT){//信用卡

                llBankCreditCard.setVisibility(View.VISIBLE);

                llBankDepositCard.setVisibility(View.GONE);

                tvBankCardNoTwo.setText(userAuthenticationBean.getCardNo());

                tvRealName.setText(userAuthenticationBean.getRealName());

                tvCertTypeName.setText(userAuthenticationBean.getCertTypeName());

                tvCertNoTwo.setText(userAuthenticationBean.getCertNo());
            }
        }


    }

    @Event(R.id.tvNextStep)
    private void tvNextStep(View view){

        if(userAuthenticationBean == null){

            return;
        }

        int bankTypeCode = userAuthenticationBean.getCardType();

        if(bankTypeCode == CARD_DEBIT) {//储蓄卡

            String etPhoneNumberOneStr = etPhoneNumberOne.getText().toString().trim();

            if(TextUtils.isEmpty(etPhoneNumberOneStr)){
                ToastUtil.i(this,"请输入预留手机号");
                return;
            }

            userAuthenticationBean.setMobile(etPhoneNumberOneStr);


        }else if(bankTypeCode == CARD_CREDIT) {//信用卡

            String etPhoneNumberTwoStr = etPhoneNumberTwo.getText().toString().trim();

            if(TextUtils.isEmpty(etPhoneNumberTwoStr)){
                ToastUtil.i(this,"请输入预留手机号");
                return;
            }

            userAuthenticationBean.setMobile(etPhoneNumberTwoStr);

            String tvYouxiaoqiStr = tvYouxiaoqi.getText().toString().trim();

            if(TextUtils.isEmpty(tvYouxiaoqiStr)){
                ToastUtil.i(this,"请输设置有效期");
                return;
            }

            userAuthenticationBean.setValidityPeriod(tvYouxiaoqiStr);

            String tvAnquanmaStr = tvAnquanma.getText().toString().trim();

            if(TextUtils.isEmpty(tvAnquanmaStr)){
                ToastUtil.i(this,"请输入安全码");
                return;
            }

            userAuthenticationBean.setCvv2(tvAnquanmaStr);
        }

        Intent intent = new Intent(this,VerifyPhoneNumberActivity.class);

        intent.putExtra(USER_AUTHENTICATION_DATA,userAuthenticationBean);

        startActivityForResult(intent,VerifyResultActivity.RESULT_REQUEST_CODE);

    }

    @Event(R.id.llYouxiaoqi)
    private void llYouxiaoDate(View view){

        ValidityDayDialog dialog = new ValidityDayDialog();
        dialog.setListener(new OnSelectedListener()
        {
            @Override
            public void onSelected(String year, String day)
            {
                tvYouxiaoqi.setText(year + "/" + (day.length() == 1 ? "0" + day : day));
            }
        });
        dialog.show(getSupportFragmentManager(), "ValidityDialog");
    }

    private void initView()
    {
        setTitleNmae("信用卡信息");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == VerifyResultActivity.RESULT_REQUEST_CODE){

            if(resultCode == VerifyResultActivity.RESULT_RESULT_SUCCESS){

                setResult(resultCode);

                finish();
            }else    if(resultCode == VerifyResultActivity.RESULT_RESULT_REVERIFY){

                setResult(resultCode);
                finish();
            }

        }

    }
}
