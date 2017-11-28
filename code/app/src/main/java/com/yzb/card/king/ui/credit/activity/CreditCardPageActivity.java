package com.yzb.card.king.ui.credit.activity;


import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;

/**
 * 办卡进度(H5)
 */
public class CreditCardPageActivity extends BaseActivity
{

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        // 设置标题
        setHeader(R.mipmap.icon_back_white, "办卡进度");

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

        webView.loadUrl("file:///android_asset/creditCardPace.html");
    }

    /**
     * Js Function
     */
    final class JavaScriptInterface {

        JavaScriptInterface() {
        }

        @JavascriptInterface
        public void query() {

        }

    }
}
