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
import com.yzb.wallet.logic.comm.FreePayLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FreePayActivity extends AppCompatActivity {

    private TextView code;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_pay);

        code = (TextView) findViewById(R.id.code);
        error = (TextView) findViewById(R.id.error);

        final EditText amount = (EditText) findViewById(R.id.freeAmount);

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

        ((Button) findViewById(R.id.setBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView mobile = (TextView) findViewById(R.id.mobile);
                TextView freePay = (TextView) findViewById(R.id.freePay);
                TextView freeAmount = (TextView) findViewById(R.id.freeAmount);

                TextView merchantNo = (TextView) findViewById(R.id.merchantNo);
                TextView sign = (TextView) findViewById(R.id.sign);

                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile.getText().toString());
                params.put("customerType", "P");// 用户类型，可为空 P个人 C公司
                params.put("freePay", freePay.getText().toString());
                params.put("freeAmount", freeAmount.getText().toString());

                params.put("merchantNo", merchantNo.getText().toString());
                params.put("sign", sign.getText().toString());

                // 初始化
                FreePayLogic payHandle = new FreePayLogic(FreePayActivity.this);

                payHandle.set(params);

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

            }
        });

        ((Button) findViewById(R.id.queryBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView mobile = (TextView) findViewById(R.id.mobile);

                TextView merchantNo = (TextView) findViewById(R.id.merchantNo);
                TextView sign = (TextView) findViewById(R.id.sign);

                // 初始化
                FreePayLogic payHandle = new FreePayLogic(FreePayActivity.this);

                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile.getText().toString());
                params.put("customerType", "P");// 用户类型，可为空 P个人 C公司

                params.put("merchantNo", merchantNo.getText().toString());
                params.put("sign", sign.getText().toString());

                payHandle.validate(params);

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
