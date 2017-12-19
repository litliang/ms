package com.yzb.card_wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.openInterface.PayMethod;
import com.yzb.wallet.openInterface.PayOrdinaryHandle;
import com.yzb.wallet.openInterface.WalletBackListener;
import com.yzb.wallet.util.StringUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class OrdinaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_ordinary);

        final EditText amount = (EditText) findViewById(R.id.amount);

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String amountStr = amount.getText().toString();

                String temp = s.toString();
                int len = temp.length();

                // 如果第一位是0则第二位不允许输入数字
                if (len == 2 && "0".equals(temp.substring(0, 1))) {
                    if (Pattern.matches("(\\d)", temp.substring(1, 2)))
                        s.delete(1, 2);
                }

                // 保留2位小数
                int posDot = temp.indexOf(".");
                if (posDot == 0) s.clear();
                if (posDot < 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });

        final TextView accountType = (TextView) findViewById(R.id.accountType);
        final TextView customerBankId = (TextView) findViewById(R.id.customerBankId);
        final TextView bankName = (TextView) findViewById(R.id.bankName);

        ((Button) findViewById(R.id.ordinaryBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView creditId = (TextView) findViewById(R.id.creditId);
                TextView amount = (TextView) findViewById(R.id.amount);
                TextView summary = (TextView) findViewById(R.id.summary);
                TextView sessionId = (TextView) findViewById(R.id.sessionId);
                TextView UUID = (TextView) findViewById(R.id.UUID);

                Map<String, String> params = new HashMap<String, String>();
                // 普通支付(有支付方式)
                String accountTypeText = accountType.getText().toString();
                // 默认余额付款
                accountTypeText = StringUtil.isEmpty(accountTypeText) ? "1" : accountTypeText;
                params.put("accountType", accountTypeText);// 支付方式
                params.put("customerBankId", customerBankId.getText().toString());// 用户银行卡id（accountType支付方式为0使用银行卡支付时参数可用）
                params.put("creditId", creditId.getText().toString());

                String amountStr = amount.getText().toString();
                amountStr = StringUtil.isEmpty(amountStr) ? "" : new BigDecimal(amountStr).setScale(2).toString();
                params.put("amount", amountStr);
                params.put("summary", summary.getText().toString());
                params.put("sessionId", sessionId.getText().toString());
                params.put("UUID", UUID.getText().toString());

                PayOrdinaryHandle payHandle = new PayOrdinaryHandle(OrdinaryActivity.this);
                payHandle.pay(params);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======普通支付(有支付方式)=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(OrdinaryActivity.this, getWindow().peekDecorView(), "支付成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {

                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======普通支付(有支付方式)=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(OrdinaryActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((LinearLayout) findViewById(R.id.chosePayMethod)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> params = new HashMap<String, String>();
                // 付款方式
                params.put("sessionId", "42896282221762663891453882110841");
                params.put("UUID", "1");
                PayMethod payHandle = new PayMethod(OrdinaryActivity.this);
                payHandle.chosePayMethod(params);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======付款方式=======>" + resultMap);
                        accountType.setText(resultMap.get("accountType"));
                        customerBankId.setText(resultMap.get("customerBankId"));
                        bankName.setText(resultMap.get("bankName"));
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======付款方式=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(OrdinaryActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(0x0);
                finish();
            }
        });
    }
}
