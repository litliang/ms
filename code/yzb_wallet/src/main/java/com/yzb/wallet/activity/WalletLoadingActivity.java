package com.yzb.wallet.activity;


import android.os.Bundle;
import android.widget.EditText;

import com.yzb.wallet.R;
import com.yzb.wallet.loading.AVLoadingIndicatorView;

/**
 * 动画加载
 */
public class WalletLoadingActivity extends BaseActivity {

    private AVLoadingIndicatorView avi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_loading);

        String indicator=getIntent().getStringExtra("indicator");
        avi= (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator(indicator);
    }

}
