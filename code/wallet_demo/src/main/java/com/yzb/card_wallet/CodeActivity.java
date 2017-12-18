package com.yzb.card_wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.openInterface.CodeHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.Map;

public class CodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_code);

        ((Button) findViewById(R.id.debitPayCodeBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);

                CodeHandle codeHandle = new CodeHandle(CodeActivity.this);
                codeHandle.payCode(customerId.getText().toString());
                codeHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======获取付款支付码=======>" + RESULT_CODE);
                        System.out.println("======获取付款支付码=======>" + resultMap);
                        WalletToastCustom.sendDialog(CodeActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======获取付款支付码=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(CodeActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.addKeyBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);

                CodeHandle codeHandle = new CodeHandle(CodeActivity.this);
                codeHandle.addKey(customerId.getText().toString());
                codeHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======添加秘钥=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(CodeActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {

                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======添加秘钥=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(CodeActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
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
