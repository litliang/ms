package com.yzb.wallet.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.gridpasswordview.GridPasswordView;
import com.yzb.wallet.R;
import com.yzb.wallet.dialog.WalletPayPwdDialog;
import com.yzb.wallet.dialog.WalletToastCustom;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.HashUtil;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 重置支付密码(忘记支付密码)
 */
public class PayPwdForgetActivity extends BaseActivity implements View.OnClickListener {

    private Button payPwdNext;
    private EditText realName;
    private EditText idCard;
    private String newPayPwd;
    private String reNewPayPwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paypwd_forget);

        // 设置标题
        setHeader("关闭", "身份验证");

        // 返回
        switchContentLeft(WalletConstant.RES_BACK);

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == AppConstant.REQUEST_USER_PAYPWD_OTHER && resultCode == AppConstant.RESULT_USER_SAFE) {
//
//            setResult(AppConstant.RESULT_USER_SAFE, data);
//
//            finish();
//        }
    }

    /**
     * 初始化
     */
    public void init() {

        ((Button) findViewById(R.id.payPwdNext)).setOnClickListener(this);
//        ((TextView) findViewById(R.id.showOtherValidate)).setOnClickListener(this);

        payPwdNext = (Button) findViewById(R.id.payPwdNext);
        realName = (EditText) findViewById(R.id.realName);
        idCard = (EditText) findViewById(R.id.idCard);

        realName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                activePositiveBtn();
            }
        });

        idCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                activePositiveBtn();
            }
        });
    }

    /**
     * 激活下一步按钮
     */
    public void activePositiveBtn() {

        String name = realName.getText().toString();
        String certNo = idCard.getText().toString();
        if (!StringUtil.isEmpty(name) && !StringUtil.isEmpty(certNo)) {
            payPwdNext.setBackgroundResource(R.drawable.style_btn_blue_deep);
            payPwdNext.setEnabled(true);
        } else {
            payPwdNext.setBackgroundResource(R.drawable.style_btn_blue_light);
            payPwdNext.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.payPwdNext) {
            authentication();
        }
//        else if (i == R.id.showOtherValidate) {
//            Intent intent = new Intent();
//            intent.setClass(UserPayPwdForgetActivity.this, UserPayPwdOtherActivity.class);
//            startActivityForResult(intent, AppConstant.REQUEST_USER_PAYPWD_OTHER);
//        }
    }

    /**
     * 身份验证
     */
    public void authentication() {

        Map<String, String> param = new HashMap<String, String>();

        param.put("name", realName.getText().toString());
        param.put("certNo", idCard.getText().toString());

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.call(PayPwdForgetActivity.this, ServiceDispatcher.setParams(WalletConstant.sessionId, CardConstant.app_api_cust_info, WalletConstant.UUID, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (null == result || result.isEmpty()) return;
                if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    if (StringUtil.isEmpty(result.get("data"))) {
                        WalletToastCustom.sendDialog(PayPwdForgetActivity.this, getWindow().peekDecorView(), "身份验证失败,请核实后重试", 140);
                    } else {
                        showPayPwdDialog("设置6位数字支付密码", 0x1);
                    }
                } else {
                    WalletToastCustom.sendDialog(PayPwdForgetActivity.this, getWindow().peekDecorView(), result.get("error"), 140);
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 显示支付密码dialog
     */
    public void showPayPwdDialog(String msg, final int flag) {

        final WalletPayPwdDialog.Builder builder = new WalletPayPwdDialog.Builder(PayPwdForgetActivity.this);

        builder.setPayPwdMsg(msg);

        builder.setPayPwd(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onChanged(String psw) {
                Button positiveBtn = builder.getPositiveBtn();
                if (psw.length() == 6) {
                    positiveBtn.setEnabled(true);
                    positiveBtn.setBackgroundResource(R.drawable.style_btn_positive_deep);
                } else {
                    // 输入支付密码小于6位时，确认按钮不可用
                    positiveBtn.setEnabled(false);
                    positiveBtn.setBackgroundResource(R.drawable.style_btn_positive_light);
                }

            }

            @Override
            public void onMaxLength(String psw) {
                if (flag == 0x1) {
                    // 第一次输入支付密码
                    newPayPwd = HashUtil.getMD5(psw);
                } else if (flag == 0x2) {
                    // 重复输入支付密码
                    reNewPayPwd = HashUtil.getMD5(psw);
                }
            }
        });

        builder.setPositiveBtn(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (flag == 0x1) {
                    showPayPwdDialog("请再次输入以确认", 0x2);
                    builder.dismiss();
                } else if (flag == 0x2) {
                    // 校验俩次输入是否一致
                    if (!newPayPwd.equals(reNewPayPwd)) {
                        WalletToastCustom.sendDialog(PayPwdForgetActivity.this, getWindow().peekDecorView(), "密码不一致,请重新输入", 140);
                        builder.dismiss();
                    } else {
                        // 设置支付密码
                        setValidate(newPayPwd, builder);
                    }
                }
            }
        });

        // 监听dialog关闭
        builder.setDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        builder.create().show();
    }

    /**
     * 设置支付密码
     */
    public void setValidate(String psw, final WalletPayPwdDialog.Builder builder) {

        Map<String, String> param = new HashMap<String, String>();

        param.put("payPasswd", psw);

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                // 支付密码校验
                return ServiceDispatcher.call(ServiceDispatcher.setParams(WalletConstant.sessionId, CardConstant.app_api_payment_pswd, WalletConstant.UUID, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (null == result || result.isEmpty()) return;
                if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    // 设置支付密码成功返回
                    Intent intent = new Intent();
                    intent.putExtra("isSuccess", true);
                    setResult(WalletConstant.RES_BACK, intent);
                    finish();
                } else {
                    WalletToastCustom.sendDialog(PayPwdForgetActivity.this, getWindow().peekDecorView(), result.get("error"), 140);
                    builder.dismiss();
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

}
