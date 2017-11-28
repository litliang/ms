package com.yzb.card.king.ui.credit.activity;


import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseActivity;

/**
 * 导入账单(H5)
 */
public class TransferMailActivity extends BaseActivity
{

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        // 设置标题
        setHeader(R.mipmap.icon_back_white, "转发邮件");

        switchContentLeft(AppConstant.RES_IMPORT_BILL);

        init();

    }

    public void init() {

        webView = (WebView) findViewById(R.id.showWebView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setDefaultTextEncodingName("UTF-8");//字符编码

        //调用js方法
        //webView.loadUrl("javascript:");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        webView.addJavascriptInterface(new JavaScriptInterface(), "JsFunction");

        webView.loadUrl("file:///android_asset/transferMail.html");
    }

    /**
     * Js Function
     */
    final class JavaScriptInterface {

        JavaScriptInterface() {
        }

        @JavascriptInterface
        public void showTransferRule() {
            Intent intent = new Intent();
            intent.setClass(TransferMailActivity.this, TransferRuleActivity.class);
            startActivityForResult(intent, AppConstant.REQ_TRANSFER_RULE);
        }

    }
}
