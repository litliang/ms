package com.yzb.logic_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.R;
import com.yzb.wallet.logic.comm.PayMethodDefaultLogic;
import com.yzb.wallet.logic.comm.PayMethodLimitLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

public class PayMethodLimitActivity extends AppCompatActivity {

    private TextView code;
    private TextView error;
    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pay_method_default);

        ((Button) findViewById(R.id.payBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                code = (TextView) findViewById(R.id.code);
                error = (TextView) findViewById(R.id.error);
                data = (TextView) findViewById(R.id.data);

                TextView mobile = (TextView) findViewById(R.id.mobile);
                TextView merchantNo = (TextView) findViewById(R.id.merchantNo);
                TextView sign = (TextView) findViewById(R.id.sign);

                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile.getText().toString());
                params.put("customerType", "P");// 用户类型，可为空 P个人 C公司
                params.put("merchantNo", merchantNo.getText().toString());
                params.put("sign", sign.getText().toString());

                PayMethodLimitLogic payHandle = new PayMethodLimitLogic (PayMethodLimitActivity.this);

                // 显示/隐藏 现金账户
                payHandle.showBalancePay(true);
                // 显示/隐藏 红包账户
                payHandle.showEnvelopPay(false);
                // 显示/隐藏 礼品卡账户
                payHandle.showGiftPay(false);
                // 显示/隐藏 积分账户
                payHandle.showIntegralPay(false);
                // 显示/隐藏 借记卡
                payHandle.showDebitCard(true);
                // 显示/隐藏 信用卡
                payHandle.showCreditCard(false);

                payHandle.payMethod(params);

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
                        data.setText(JSON.toJSONString(resultMap));
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
