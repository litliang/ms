package com.yzb.logic_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.logic.pay.RechargeHandle;
import com.yzb.wallet.logic.pay.SettleHandle;
import com.yzb.wallet.openInterface.ForgetPwdBackListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SettleActivity extends AppCompatActivity {

    private TextView code;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);

        code = (TextView) findViewById(R.id.code);
        error = (TextView) findViewById(R.id.error);

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

                TextView mobile = (TextView) findViewById(R.id.mobile);
                TextView accountType = (TextView) findViewById(R.id.accountType);
                TextView amount = (TextView) findViewById(R.id.amount);
                TextView goodsName = (TextView) findViewById(R.id.goodsName);
                TextView sortCode = (TextView) findViewById(R.id.sortCode);
                TextView transIp = (TextView) findViewById(R.id.transIp);

                TextView merchantNo = (TextView) findViewById(R.id.merchantNo);
                TextView sign = (TextView) findViewById(R.id.sign);

                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile.getText().toString());
                params.put("customerType", "P");// 用户类型，可为空 P个人 C公司
                params.put("accountType", accountType.getText().toString());
                params.put("amount", amount.getText().toString());
                params.put("goodsName", goodsName.getText().toString());
                params.put("sortCode", sortCode.getText().toString());
                params.put("transIp", transIp.getText().toString());

                params.put("merchantNo", merchantNo.getText().toString());
                params.put("sign", sign.getText().toString());

                // 初始化
                SettleHandle payHandle = new SettleHandle(SettleActivity.this);

                // 充值操作 显示"忘记密码"：是true 否false或null
                payHandle.pay(params, true);

                // 操作结果
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("=返回结果=>code" + RESULT_CODE);
                        code.setText(RESULT_CODE);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("=返回结果=>code" + RESULT_CODE + "返回数据=>" + resultMap);
                        code.setText(RESULT_CODE);
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                        code.setText(RESULT_CODE);
                        error.setText(ERROR_MSG);
                    }
                });

                // 忘记密码回掉
                payHandle.setForgetPwdCallBack(new ForgetPwdBackListener() {
                    @Override
                    public void callBack() {

                        // TODO 跳转忘记密码页面
                        System.out.println("==跳转忘记密码页面==>");

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
