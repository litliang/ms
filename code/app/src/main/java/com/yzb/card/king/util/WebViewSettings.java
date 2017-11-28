package com.yzb.card.king.util;

import android.graphics.Color;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by gengqiyun on 2016/4/20.
 * WebView相关的设置；
 */
public class WebViewSettings {

    public static abstract class IWvCallBack {
        public abstract void onProgressChanged(int newProgress);

        public void onReceiveTitle(String title) {

        }
    }

    public static void setting(WebView mWebView, final IWvCallBack callBack) {
//        mWebView.setInitialScale(100);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置WebView可触摸放大缩小
        webSettings.setBuiltInZoomControls(true);
        //支持缩放
        webSettings.setSupportZoom(true);

        webSettings.setDisplayZoomControls(false);
        webSettings.setTextZoom(100);
//        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.setBackgroundColor(Color.WHITE);

        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);//自适应屏幕：

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (callBack != null) {
                    callBack.onProgressChanged(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
    }
}
