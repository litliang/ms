package com.yzb.card_wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.openInterface.PayPwdHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.Map;

public class PayPwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_paypwd);

        ((Button) findViewById(R.id.validateBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);
                TextView payPasswd = (TextView) findViewById(R.id.payPasswd);

                PayPwdHandle payHandle = new PayPwdHandle(PayPwdActivity.this);
                payHandle.validate(customerId.getText().toString(), payPasswd.getText().toString());
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======支付密码校验=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(PayPwdActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======支付密码校验=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(PayPwdActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.setBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);
                TextView payPasswd = (TextView) findViewById(R.id.payPasswd);

                PayPwdHandle payHandle = new PayPwdHandle(PayPwdActivity.this);
                payHandle.set(customerId.getText().toString(), payPasswd.getText().toString());
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======设置密码校验=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(PayPwdActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======设置密码校验=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(PayPwdActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
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
