package com.yzb.card_wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.openInterface.AccountChargeHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

public class AccountChargeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_account_charge);

        ((Button) findViewById(R.id.chargeBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customerId = (TextView) findViewById(R.id.customerId);
                TextView accountType = (TextView) findViewById(R.id.accountType);
                TextView amount = (TextView) findViewById(R.id.amount);

                Map<String, String> params = new HashMap<String, String>();
                params.put("customerId", customerId.getText().toString());
                params.put("accountType", accountType.getText().toString());
                params.put("amount", amount.getText().toString());

                AccountChargeHandle chargeHandle = new AccountChargeHandle(AccountChargeActivity.this);
                chargeHandle.charge(params);
                chargeHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("======收款操作=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(AccountChargeActivity.this, getWindow().peekDecorView(), "成功", 140);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======收款操作=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(AccountChargeActivity.this, getWindow().peekDecorView(), ERROR_MSG, 140);
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
