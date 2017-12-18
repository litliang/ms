package com.yzb.card_wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.openInterface.OrderHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_order);

        ((Button) findViewById(R.id.statBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView custId = (TextView) findViewById(R.id.custId);

                OrderHandle payHandle = new OrderHandle(OrderActivity.this);
                payHandle.stat(custId.getText().toString());
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======订单汇总=======>" + RESULT_CODE);
                        System.out.println("======订单汇总=======>" + resultMap);
                        WalletToastCustom.sendDialog(OrderActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======订单汇总=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(OrderActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
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
