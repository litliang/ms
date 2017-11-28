package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.idcard.CardInfo;
import com.idcard.TFieldID;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.common.UserAuthenticationBean;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.ui.app.activity.ia.VerifyPhoneNumberActivity;
import com.yzb.card.king.ui.app.activity.ia.VerifyResultActivity;
import com.yzb.card.king.ui.app.fragment.CertificateDialogFragment;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.CardBin;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.credit.interfaces.OnSelectedListener;
import com.yzb.card.king.ui.credit.popup.ValidityDayDialog;
import com.yzb.card.king.ui.credit.watcher.CardTextWatcher;
import com.yzb.card.king.ui.other.controller.ScanCardController;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;

import static com.yzb.card.king.ui.app.activity.ia.ExamineIaActivity.USER_AUTHENTICATION_DATA;
import static com.yzb.card.king.ui.credit.bean.CardBin.CARD_CREDIT;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/10/15
 * 描  述：
 */
@ContentView(R.layout.activity_repayment_credite_info)
public class RepaymentCrediteInfoActivity extends BaseActivity {

    @ViewInject(R.id.tvCarNo)
    private EditText tvCarNo;

    @ViewInject(R.id.tvBankCardNoTwo)
    private EditText tvBankCardNoTwo;

    @ViewInject(R.id.tvRealName)
    private EditText tvRealName;

    @ViewInject(R.id.tvYouxiaoqi)
    private TextView tvYouxiaoqi;

    @ViewInject(R.id.tvCertTypeName)
    private TextView tvCertTypeName;

    @ViewInject(R.id.tvCertNoTwo)
    private EditText tvCertNoTwo;

    @ViewInject(R.id.etPhoneNumberTwo)
    private EditText etPhoneNumberTwo;

    @ViewInject(R.id.tvAnquanma)
    private EditText tvAnquanma;

    private UserAuthenticationBean userAuthenticationBean;

    private ScanCardController cardController;

    public CardBin cardBin;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        initData();

        initScanController();

        initListener();
    }

    private void initListener()
    {
        CardTextWatcher watcher = new CardTextWatcher(tvBankCardNoTwo);
        watcher.setListener(new BaseCallBack<CardBin>() {

            @Override
            public void onSuccess(CardBin data)
            {
                cardBin = data;
            }

            @Override
            public void onFail(Error error)
            {

            }
        });
        tvBankCardNoTwo.addTextChangedListener(watcher);
    }


    private void initScanController()
    {
        cardController = new ScanCardController(this);

        cardController.setCallBack(new BaseCallBack<CardInfo>() {
            @Override
            public void onSuccess(CardInfo data)
            {
                tvBankCardNoTwo.setText(data.getFieldString(TFieldID.TBANK_NUM));
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private String amount;

    private String fee;

    private void initData()
    {

        userAuthenticationBean = new UserAuthenticationBean();

        userAuthenticationBean.setPaymentMoneyIf(true);

        if (getIntent().hasExtra("creditCard")) {

            CreditCard data = (CreditCard) getIntent().getSerializableExtra("creditCard");

            userAuthenticationBean.setData(data);

            amount = getIntent().getStringExtra("amount");

            fee = getIntent().getStringExtra("fee");

        }


    }

    @Event(R.id.tvNextStep)
    private void tvNextStep(View view)
    {

        if (userAuthenticationBean == null) {

            return;
        }

        String tvBankCardNoTwoStr = tvBankCardNoTwo.getText().toString().trim();

        if (TextUtils.isEmpty(tvBankCardNoTwoStr)) {
            ToastUtil.i(this, "请输入卡号");
            return;
        }

        userAuthenticationBean.setCardNo(tvBankCardNoTwoStr);

        String tvYouxiaoqiStr = tvYouxiaoqi.getText().toString().trim();

        if (TextUtils.isEmpty(tvYouxiaoqiStr)) {
            ToastUtil.i(this, "请输设置有效期");
            return;
        }

        userAuthenticationBean.setValidityPeriod(tvYouxiaoqiStr);

        String tvAnquanmaStr = tvAnquanma.getText().toString().trim();

        if (TextUtils.isEmpty(tvAnquanmaStr)) {
            ToastUtil.i(this, "请输入安全码");
            return;
        }

        userAuthenticationBean.setCvv2(tvAnquanmaStr);


        String tvRealNameStr = tvRealName.getText().toString().trim();

        if (TextUtils.isEmpty(tvRealNameStr)) {
            ToastUtil.i(this, "请输入真实姓名");
            return;
        }

        userAuthenticationBean.setRealName(tvRealNameStr);


        String tvCertTypeNameStr = tvCertTypeName.getText().toString().trim();

        if (TextUtils.isEmpty(tvCertTypeNameStr)) {
            ToastUtil.i(this, "请输入真实姓名");
            return;
        }

        userAuthenticationBean.setCertTypeName(tvCertTypeNameStr);

        String tvCertNoTwoStr = tvCertNoTwo.getText().toString().trim();

        if (TextUtils.isEmpty(tvCertNoTwoStr)) {
            ToastUtil.i(this, "请输入证件号码");
            return;
        }

        userAuthenticationBean.setCertNo(tvCertNoTwoStr);

        String etPhoneNumberTwoStr = etPhoneNumberTwo.getText().toString().trim();

        if (TextUtils.isEmpty(etPhoneNumberTwoStr)) {
            ToastUtil.i(this, "请输入预留手机号");
            return;
        }

        userAuthenticationBean.setMobile(etPhoneNumberTwoStr);

        userAuthenticationBean.setBankId(cardBin.getBankId());

        userAuthenticationBean.setCardType(Integer.parseInt(cardBin.getType()));

        Intent intent = new Intent(this, VerifyPhoneNumberActivity.class);

        intent.putExtra(USER_AUTHENTICATION_DATA, userAuthenticationBean);

        intent.putExtra("amount", amount);

        intent.putExtra("fee", fee);

        startActivityForResult(intent, VerifyResultActivity.RESULT_REQUEST_CODE);

    }

    @Event(R.id.llCertType)
    private void llCertType(View view)
    {
        CertificateDialogFragment cdf = CertificateDialogFragment.getInstance();
        cdf.setCallBack(new CertificateDialogFragment.ICerificateCallBack() {
            @Override
            public void callBack(String name, int certificateType)
            {
                int certType = certificateType;
                userAuthenticationBean.setCertType(certType);
                tvCertTypeName.setText(name);
            }
        });
        cdf.setDataList(Arrays.asList(getResources().getStringArray(R.array.setting_certificate_type)));
        cdf.show(getSupportFragmentManager(), "");
    }

    @Event(R.id.llYouxiaoqi)
    private void llYouxiaoDate(View view)
    {

        ValidityDayDialog dialog = new ValidityDayDialog();
        dialog.setListener(new OnSelectedListener() {
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
        //super.onActivityResult(requestCode, resultCode, data);
        cardController.onActivityResult(requestCode, resultCode, data);

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
}
