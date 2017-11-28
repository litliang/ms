package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.WebViewSettings;

/**
 * 旅游详情；
 *
 * @author gengqiyun
 * @date 2016.6.22
 * 功能：在线浏览pdf文档；
 * 用法：传入一个url即可；
 */
public class PdfActivity extends BaseActivity implements View.OnClickListener {
    private WebView webView;
    private String title;
    public static final String ARG_TITLE = "ARG_TITLE";
    public static final String ARG_URL = "ARG_URL";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        recvIntentData();
        assignViews();
    }

    private void recvIntentData() {
        Intent intent = getIntent();
        url = intent.getStringExtra(ARG_URL);
        title = intent.getStringExtra(ARG_TITLE);
    }

    private void assignViews() {
        setHeader(R.mipmap.icon_back_white, TextUtils.isEmpty(title) ? "" : title);
        findViewById(R.id.headerLeft).setOnClickListener(this);
        webView = (WebView) findViewById(R.id.webView);
        WebViewSettings.setting(webView, null);
        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeft:
                finish();
                break;
        }
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }


}
