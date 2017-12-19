package com.yzb.card_wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.openInterface.PayAuthorizeHandle;
import com.yzb.wallet.openInterface.WalletBackListener;
import com.yzb.wallet.util.StringUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AuthorizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_authorize);

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

        ((Button) findViewById(R.id.settleBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView creditId = (TextView) findViewById(R.id.creditId);
                TextView amount = (TextView) findViewById(R.id.amount);
                TextView summary = (TextView) findViewById(R.id.summary);
                TextView sessionId = (TextView) findViewById(R.id.sessionId);
                TextView UUID = (TextView) findViewById(R.id.UUID);

                Map<String, String> params = new HashMap<String, String>();
                // 授权支付
                params.put("creditId", creditId.getText().toString());

                String amountStr = amount.getText().toString();
                amountStr = StringUtil.isEmpty(amountStr) ? "" : new BigDecimal(amountStr).setScale(2).toString();
                params.put("amount", amountStr);
                params.put("summary", summary.getText().toString());
                params.put("sessionId", sessionId.getText().toString());
                params.put("UUID", UUID.getText().toString());

                PayAuthorizeHandle payHandle = new PayAuthorizeHandle(AuthorizeActivity.this);
                payHandle.pay(params);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======授权支付=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(AuthorizeActivity.this, getWindow().peekDecorView(), "支付成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {

                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======授权支付=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(AuthorizeActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
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
