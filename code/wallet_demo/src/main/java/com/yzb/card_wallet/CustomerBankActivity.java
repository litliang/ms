package com.yzb.card_wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.openInterface.CustomerBankHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

public class CustomerBankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_customer_bank);

        ((Button) findViewById(R.id.bindDebitBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);
                TextView bankName = (TextView) findViewById(R.id.bankName);
                TextView bankId = (TextView) findViewById(R.id.bankId);
                TextView fullNo = (TextView) findViewById(R.id.fullNo);
                TextView reservedMobile = (TextView) findViewById(R.id.reservedMobile);
                TextView name = (TextView) findViewById(R.id.name);
                TextView certNo = (TextView) findViewById(R.id.certNo);

                Map<String, String> params = new HashMap<String, String>();
                params.put("customerId", customerId.getText().toString());
                params.put("bankName", bankName.getText().toString());
                params.put("bankId", bankId.getText().toString());
                params.put("fullNo", fullNo.getText().toString());
                params.put("reservedMobile", reservedMobile.getText().toString());
                params.put("name", name.getText().toString());
                params.put("certNo", certNo.getText().toString());

                CustomerBankHandle customerBankHandle = new CustomerBankHandle(CustomerBankActivity.this);
                customerBankHandle.debitCardBind(params);
                customerBankHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======绑定借记卡=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(CustomerBankActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======绑定借记卡=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(CustomerBankActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.bindCreditBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);
                TextView bankName = (TextView) findViewById(R.id.bankName);
                TextView bankId = (TextView) findViewById(R.id.bankId);
                TextView fullNo = (TextView) findViewById(R.id.fullNo);
                TextView reservedMobile = (TextView) findViewById(R.id.reservedMobile);
                TextView name = (TextView) findViewById(R.id.name);
                TextView certNo = (TextView) findViewById(R.id.certNo);

                Map<String, String> params = new HashMap<String, String>();
                params.put("customerId", customerId.getText().toString());
                params.put("bankName", bankName.getText().toString());
                params.put("bankId", bankId.getText().toString());
                params.put("fullNo", fullNo.getText().toString());
                params.put("reservedMobile", reservedMobile.getText().toString());
                params.put("name", name.getText().toString());
                params.put("certNo", certNo.getText().toString());

                CustomerBankHandle customerBankHandle = new CustomerBankHandle(CustomerBankActivity.this);
                // 绑定信用卡和借记卡不同在，调用方法不同
                customerBankHandle.creditCardBind(params);
                customerBankHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======绑定信用卡=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(CustomerBankActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======绑定信用卡=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(CustomerBankActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.relieveBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);
                TextView fullNo = (TextView) findViewById(R.id.fullNo);

                Map<String, String> params = new HashMap<String, String>();
                params.put("customerId", customerId.getText().toString());
                params.put("fullNo", fullNo.getText().toString());

                CustomerBankHandle customerBankHandle = new CustomerBankHandle(CustomerBankActivity.this);
                customerBankHandle.relieve(params);
                customerBankHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======解绑银行卡=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(CustomerBankActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======解绑银行卡=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(CustomerBankActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
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
