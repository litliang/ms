package com.yzb.card_wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.openInterface.PayMethod;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

public class PayMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_pay_method);

        ((LinearLayout) findViewById(R.id.chosePayMethod)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TextView accountType = (TextView) findViewById(R.id.accountType);
                final TextView customerBankId = (TextView) findViewById(R.id.customerBankId);
                final TextView bankName = (TextView) findViewById(R.id.bankName);
                TextView sessionId = (TextView) findViewById(R.id.sessionId);
                TextView UUID = (TextView) findViewById(R.id.UUID);

                Map<String, String> params = new HashMap<String, String>();
                // 付款方式
                params.put("sessionId", sessionId.getText().toString());
                params.put("UUID", UUID.getText().toString());
                PayMethod payHandle = new PayMethod(PayMethodActivity.this);
                payHandle.chosePayMethod(params);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======付款方式=======>" + resultMap);
                        accountType.setText(resultMap.get("accountType"));
                        customerBankId.setText(resultMap.get("customerBankId"));
                        bankName.setText(resultMap.get("bankName"));
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======付款方式=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(PayMethodActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.payMethodListBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView sessionId = (TextView) findViewById(R.id.sessionId);
                TextView UUID = (TextView) findViewById(R.id.UUID);

                Map<String, String> params = new HashMap<String, String>();
                // 付款方式
                params.put("sessionId", sessionId.getText().toString());
                params.put("UUID", UUID.getText().toString());
                PayMethod payHandle = new PayMethod(PayMethodActivity.this);
                payHandle.payMethod(params);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======付款方式=======>" + resultMap);
                        WalletToastCustom.sendDialog(PayMethodActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======付款方式=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(PayMethodActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.payMethodSeqBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);

                Map<String, String> params = new HashMap<String, String>();
                // 付款方式(排序)
                params.put("customerId", customerId.getText().toString());
                PayMethod payHandle = new PayMethod(PayMethodActivity.this);
                payHandle.payMethodSeq(params);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======付款方式=======>" + resultMap);
                        WalletToastCustom.sendDialog(PayMethodActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======付款方式=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(PayMethodActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.payMethodSeqBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);

                Map<String, String> params = new HashMap<String, String>();
                // 付款方式(排序)
                params.put("customerId", customerId.getText().toString());
                PayMethod payHandle = new PayMethod(PayMethodActivity.this);
                payHandle.payMethodSeq(params);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======付款方式(排序)=======>" + resultMap);
                        WalletToastCustom.sendDialog(PayMethodActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======付款方式(排序)=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(PayMethodActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.addPayMethodBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);

                Map<String, String> params = new HashMap<String, String>();
                // 添加付款方式
                params.put("customerId", customerId.getText().toString());
                params.put("data", "[{\"id\":null,\"customerId\":null,\"accountType\":0,\"detailsId\":33,\"detailsName\":\"招商银行(1211)\",\"bankId\":1,\"seq\":1,\"createTime\":null},{\"id\":null,\"customerId\":null,\"accountType\":2,\"detailsId\":200000109,\"detailsName\":\"\",\"bankId\":null,\"seq\":2,\"createTime\":null},{\"id\":null,\"customerId\":null,\"accountType\":0,\"detailsId\":35,\"detailsName\":\"招商银行(1213)\",\"bankId\":1,\"seq\":3,\"createTime\":null},{\"id\":null,\"customerId\":null,\"accountType\":0,\"detailsId\":34,\"detailsName\":\"招商银行信用卡(1212)\",\"bankId\":1,\"seq\":98,\"createTime\":null},{\"id\":null,\"customerId\":null,\"accountType\":1,\"detailsId\":100000108,\"detailsName\":\"\",\"bankId\":0,\"seq\":99,\"createTime\":null},{\"id\":null,\"customerId\":null,\"accountType\":3,\"detailsId\":300000110,\"detailsName\":\"\",\"bankId\":0,\"seq\":99,\"createTime\":null},{\"id\":null,\"customerId\":null,\"accountType\":4,\"detailsId\":400000111,\"detailsName\":\"\",\"bankId\":0,\"seq\":99,\"createTime\":null}]");
                PayMethod payHandle = new PayMethod(PayMethodActivity.this);
                payHandle.addPayMethod(params);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        WalletToastCustom.sendDialog(PayMethodActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======添加付款方式=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(PayMethodActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
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
