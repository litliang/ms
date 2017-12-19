package com.yzb.logic_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.logic.comm.EarnLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

public class EarnActivity extends AppCompatActivity {

    private TextView code;
    private TextView error;
    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn);

        code = (TextView) findViewById(R.id.code);
        error = (TextView) findViewById(R.id.error);
        data = (TextView) findViewById(R.id.data);

        ((Button) findViewById(R.id.rechargeBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView type = (TextView) findViewById(R.id.type);
                TextView startTime = (TextView) findViewById(R.id.startTime);
                TextView endTime = (TextView) findViewById(R.id.endTime);
                TextView merchantNo = (TextView) findViewById(R.id.merchantNo);
                TextView sign = (TextView) findViewById(R.id.sign);

                Map<String, String> params = new HashMap<String, String>();

                params.put("type", type.getText().toString());
                params.put("startTime", startTime.getText().toString());
                params.put("endTime", endTime.getText().toString());
                params.put("merchantNo", merchantNo.getText().toString());
                params.put("sign", sign.getText().toString());

                // 初始化
                EarnLogic earn = new EarnLogic(EarnActivity.this);

                earn.query(params);

                // 操作结果
                earn.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("=返回结果=>code" + RESULT_CODE);
                        code.setText(RESULT_CODE);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("=返回结果=>code" + RESULT_CODE + "返回数据=>" + resultMap);
                        code.setText(RESULT_CODE);
                        data.setText(resultMap.get("data"));
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
