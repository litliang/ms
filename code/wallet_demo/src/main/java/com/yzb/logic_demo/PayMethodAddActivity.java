package com.yzb.logic_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.R;
import com.yzb.wallet.logic.comm.PayMethodAddLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayMethodAddActivity extends AppCompatActivity {

    private TextView code;
    private TextView error;
    private TextView data;
    private TextView addData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pay_method_add);

        addData = (TextView) findViewById(R.id.addData);

        addData.setText("[{\"accountType\":\"1\",\"limitDay\":269308.07,\"limitMonth\":0,\"logo\":\"\",\"sortCode\":\"\",\"typeName\":\"现金余额\"},{\"accountType\":\"0\",\"limitDay\":5000,\"limitMonth\":0,\"logo\":\"20170222150311679117021677\",\"sortCode\":\"20f0bb48241941a6fa53f2d2066e250c\",\"typeName\":\"储蓄卡\"},{\"accountType\":\"3\",\"limitDay\":0.64,\"limitMonth\":0,\"logo\":\"\",\"sortCode\":\"\",\"typeName\":\"礼品卡余额\"},{\"accountType\":\"4\",\"limitDay\":0,\"limitMonth\":0,\"logo\":\"\",\"sortCode\":\"\",\"typeName\":\"平台积分余额\"},{\"accountType\":\"5\",\"limitDay\":998978,\"limitMonth\":0,\"logo\":\"\",\"sortCode\":\"\",\"typeName\":\"商家积分余额\"},{\"accountType\":\"2\",\"limitDay\":0,\"limitMonth\":0,\"logo\":\"\",\"sortCode\":\"\",\"typeName\":\"红包余额\"}]");


        ((Button) findViewById(R.id.addPayMethodBtn)).setOnClickListener(new View.OnClickListener() {
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

                params.put("data", addData.getText().toString());
                PayMethodAddLogic payHandle = new PayMethodAddLogic(PayMethodAddActivity.this);
                payHandle.addPayMethod(params);
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
