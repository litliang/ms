package com.yzb.card_wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.openInterface.AccountHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_account);

        ((Button) findViewById(R.id.balanceBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView accountId = (TextView) findViewById(R.id.accountId);
                TextView accountType = (TextView) findViewById(R.id.accountType);

                AccountHandle payHandle = new AccountHandle(AccountActivity.this);
                payHandle.handleBalance(accountId.getText().toString(), accountType.getText().toString());
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======获取余额=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(AccountActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======获取余额=======>" + RESULT_CODE);
                        System.out.println("======获取余额=======>" + resultMap);
                        WalletToastCustom.sendDialog(AccountActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======获取余额=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(AccountActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.accountBalanceBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.custId);
                TextView accountType = (TextView) findViewById(R.id.accountType);

                AccountHandle payHandle = new AccountHandle(AccountActivity.this);
                payHandle.accountBalance(customerId.getText().toString(), accountType.getText().toString());
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======获取余额=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(AccountActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======获取余额=======>" + RESULT_CODE);
                        System.out.println("======获取余额=======>" + resultMap);
                        WalletToastCustom.sendDialog(AccountActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======获取余额=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(AccountActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.accountBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);

                AccountHandle payHandle = new AccountHandle(AccountActivity.this);
                payHandle.handleAccount(customerId.getText().toString());
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======获取账户=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(AccountActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======获取账户=======>" + RESULT_CODE);
                        System.out.println("======获取账户=======>" + resultMap);
                        WalletToastCustom.sendDialog(AccountActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======获取账户=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(AccountActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.createBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);
                TextView mobile = (TextView) findViewById(R.id.mobile);

                Map<String, String> params = new HashMap<String, String>();
                params.put("customerId", customerId.getText().toString());
                params.put("mobile", mobile.getText().toString());

                AccountHandle payHandle = new AccountHandle(AccountActivity.this);
                payHandle.create(params);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======创建账户=======>" + RESULT_CODE);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======创建账户=======>" + RESULT_CODE);
                        System.out.println("======创建账户=======>" + resultMap);
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======创建账户=======>" + RESULT_CODE + "," + ERROR_MSG);
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
