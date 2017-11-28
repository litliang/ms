package com.yzb.card.king.ui.user;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;

/**
 * 类名：ProtocolActivity
 * 作者：殷曙光
 * 日期：2016/7/12 17:24
 * 描述：
 */

public class ProtocolActivity extends BaseActivity
{

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);

        if (getIntent().hasExtra("titleName")) {

            setHeader(R.mipmap.icon_back_white, getIntent().getStringExtra("titleName"));

        } else {

            setHeader(R.mipmap.icon_back_white, "协议内容");//

        }


        wv = (WebView) findViewById(R.id.wv);
        WebSettings webSettings = wv.getSettings();

//		webSettings.setLoadWithOverviewMode(true);
//		webSettings.setUseWideViewPort(true);

        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setTextZoom(100);

        wv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        wv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wv.setBackgroundColor(Color.TRANSPARENT);
        wv.loadUrl("file:///android_asset/argment.html");
        switchContentLeft(0);
    }
}
