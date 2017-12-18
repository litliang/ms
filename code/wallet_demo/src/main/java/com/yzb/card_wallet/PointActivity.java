package com.yzb.card_wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.openInterface.PayPointHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

public class PointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_point);

        ((Button) findViewById(R.id.pointBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);
                TextView point = (TextView) findViewById(R.id.point);
                TextView summary = (TextView) findViewById(R.id.summary);
                TextView sessionId = (TextView) findViewById(R.id.sessionId);
                TextView UUID = (TextView) findViewById(R.id.UUID);

                Map<String, String> params = new HashMap<String, String>();
                params.put("customerId", customerId.getText().toString());
                params.put("point", point.getText().toString());
                params.put("summary", summary.getText().toString());
                params.put("sessionId", sessionId.getText().toString());
                params.put("UUID", UUID.getText().toString());

                PayPointHandle payHandle = new PayPointHandle(PointActivity.this);
                payHandle.pay(params);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======积分付款=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(PointActivity.this, getWindow().peekDecorView(), "支付成功", 140);
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======积分付款=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(PointActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
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
