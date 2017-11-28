package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.fragment.CertificateDialogFragment;
import com.yzb.card.king.ui.app.presenter.ValidBankCardPresenter;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.watcher.CardNumWatcher;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



/**
 * @author gengqiyun
 * @date 2016.6.19
 * 验证身份证件(需要银行卡)；
 */
public class VerifyIdentificationNeedBankActivity extends BaseActivity implements View.OnClickListener, AppBaseView
{
    public static final String INTENT_KEY = "sourceActivity";
    public static final int MODIFY_BIND_PHONE_ACTIVITY = 1;

    private static final int MY_SCAN_REQUEST_CODE = 0x0001;
    private EditText etBankId; // 银行卡号；
    private TextView tvBankName; //银行名称；
    private TextView tv_sf_type; //证件类型；
    private EditText et_identification_id; //身份证号；
    private EditText etCardHolderName; //持卡人姓名；
    private CheckBox checkbox_protocal;
    private int certType = 1; // 证件类型；默认身份证；
    private int sourceActivty = 0;
    private String identification_id;
    private String name;
    private String idNumber;
    private String cardHolderName;
    private String bankName;

    private ValidBankCardPresenter validBankCardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_identification_need_bank);
        sourceActivty = getIntent().getIntExtra(INTENT_KEY, 0);

        validBankCardPresenter = new ValidBankCardPresenter(this);
        assignViews();
    }

    private void assignViews()
    {

        setTitleNmae(getString(R.string.setting_sf_verify));

        etBankId = (EditText) findViewById(R.id.et_bank_id);
        etBankId.addTextChangedListener(new Watcher());

        tvBankName = (TextView) findViewById(R.id.tv_id_bank_name);
        tv_sf_type = (TextView) findViewById(R.id.tv_sf_type);
        tv_sf_type.setOnClickListener(this);
        findViewById(R.id.iv_passport_nav_right).setOnClickListener(this);

        et_identification_id = (EditText) findViewById(R.id.et_identification_id);
        etCardHolderName = (EditText) findViewById(R.id.et_card_holder_name);

        checkbox_protocal = (CheckBox) findViewById(R.id.checkbox_protocal);
        checkbox_protocal.setChecked(false);

        findViewById(R.id.tv_agree).setOnClickListener(this);
        findViewById(R.id.tvProtocol1).setOnClickListener(this);
        findViewById(R.id.tvProtocol2).setOnClickListener(this);

        findViewById(R.id.iv_capture).setOnClickListener(this);
        findViewById(R.id.tv_safe_verify).setOnClickListener(this);
    }


    class Watcher extends CardNumWatcher
    {

        public Watcher()
        {
            super(etBankId);
        }

        @Override
        protected void parseData(String data)
        {
            Map<String, String> map = JSON.parseObject(data, Map.class);
            bankName = String.valueOf(map.get("bankName"));
            if (!TextUtils.isEmpty(bankName))
            {
                tvBankName.setText(bankName);
                tvBankName.setFocusable(false);
                tvBankName.setFocusableInTouchMode(false);
            } else
            {
                tvBankName.setText("");
            }
        }

        @Override
        protected void fail()
        {
            tvBankName.setText("");
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.tv_sf_type: //身份证类型；
            case R.id.iv_passport_nav_right: //身份证类型；
                CertificateDialogFragment cdf = CertificateDialogFragment.getInstance();
                cdf.setCallBack(new CertificateDialogFragment.ICerificateCallBack()
                {
                    @Override
                    public void callBack(String name, int certificateType)
                    {
                        certType = certificateType;
                        tv_sf_type.setText(name);
                    }
                });
                cdf.setDataList(Arrays.asList(getResources().getStringArray(R.array.setting_certificate_type)));
                cdf.show(getSupportFragmentManager(), "");
                break;

            case R.id.tv_agree:
                checkbox_protocal.setChecked(!checkbox_protocal.isChecked());
                break;
            case R.id.iv_capture:// 拍照；
                scanCard();
                break;
            case R.id.tvProtocol1://
                break;
            case R.id.tvProtocol2://
                break;
            case R.id.tv_safe_verify:// 进行安全验证；
                if (isVailid())
                {
                    verifyIdentification();
                }
                break;
        }
    }

    /**
     * 银行卡扫描；
     */
    private void scanCard()
    {
//        Intent scanIntent = new Intent(this, CardIOActivity.class);
//        // 自定义返回值
//        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true);
//        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false);
//        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false);
//
//        // 隐藏手动输入按钮
//        // 如果设置，需提供手动输入机制
//        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false);
//
//        // 匹配主题
//        scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, false);
//
//        // MY_SCAN_REQUEST_CODE提供唯一值
//        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case MY_SCAN_REQUEST_CODE:
                setCardNum(data);
                break;
        }
    }

    private void setCardNum(Intent data)
    {
//        String cardNumber;
//        String cardMsg;
//        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT))
//        {
//            CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
//            // 获取编辑后的银行卡号，显示完整卡号
//            cardNumber = scanResult.getFormattedCardNumber();
//            etBankId.setText(cardNumber);
//            cardMsg = "扫描成功.";
//
//        } else
//        {
//            cardMsg = "扫描关闭";
//        }
//        if (!StringUtils.isEmpty(cardMsg))
//        {
//            ToastCustom.sendDialog(this, getWindow().peekDecorView(), cardMsg, 140);
//        }
    }

    @Override
    public void onViewCallBackSucess(Object data)
    {
        if (sourceActivty == MODIFY_BIND_PHONE_ACTIVITY)
        {
            Intent intent = new Intent(UiUtils.getContext(), ModifyBindPhoneActivity.class);
            intent.putExtra("step", 2);
            startActivity(intent);
        } else
        {
            readyGo(VerifyIdentificationNeedBankActivity.this, ResetNewPayPwdActivity.class);
        }
    }

    @Override
    public void onViewCallBackFail(String failMsg)
    {

    }

    /**
     * 支付密码--验证身份；
     */
    private void verifyIdentification()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("certNo", idNumber);
        params.put("name", name);
        params.put("fullNo", cardHolderName);

        validBankCardPresenter.loadData(params);
    }

    private boolean isVailid()
    {
        name = etBankId.getText().toString().trim();
        if (TextUtils.isEmpty(name))
        {
            ToastUtil.i(this, getString(R.string.debit_rise_not_allow_empty));
            return false;
        }
        idNumber = tvBankName.getText().toString().trim();
        if (TextUtils.isEmpty(idNumber))
        {
            ToastUtil.i(this, getString(R.string.debit_rise_not_allow_empty));
            return false;
        }
        identification_id = et_identification_id.getText().toString().trim();
        if (TextUtils.isEmpty(identification_id))
        {
            ToastUtil.i(this, getString(R.string.debit_rise_not_allow_empty));
            return false;
        }
        // 身份证；
        if (certType == 1)
        {
            if (!RegexUtil.validIdNumNoToast(identification_id))
            {
                toastCustom(R.string.setting_id_number_format_error);
                return false;
            }
        }

        cardHolderName = etCardHolderName.getText().toString().trim();
        if (TextUtils.isEmpty(cardHolderName))
        {
            ToastUtil.i(this, getString(R.string.debit_rise_not_allow_empty));
            return false;
        }

        if (!checkbox_protocal.isChecked())
        {
            ToastUtil.i(this, getString(R.string.setting_agree_protocol_notice));
            return false;
        }
        return true;
    }
}
