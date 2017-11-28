package com.yzb.card.king.ui.other.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.WebViewSettings;

import java.lang.reflect.InvocationTargetException;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/10/20
 * 描  述：
 */
public class VideoWebviewActivity extends BaseActivity {
    public static final String CATEGORY = "category";
    public static final String TITLE_NAME = "titleName";
    private WebView webView;
    private String titleName = "";
    private String url;
    private String content;
    private ProgressBar myProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_protocol);
        recvIntentData();
        assignViews();
        initData();

    }

    private void initData()
    {

        // 如果有url，直接加载url即可；
        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);

        } else if (!TextUtils.isEmpty(content)) {

            webView.loadData(content, "text/html;charset=utf-8", null);

        } else {
            UiUtils.shortToast("url不能为空");
        }
    }

    private void recvIntentData()
    {
        Bundle bundle = getIntent().getBundleExtra(AppConstant.INTENT_BUNDLE);
        if (bundle != null) {
            content = bundle.getString("content");
            if (bundle.containsKey(TITLE_NAME)) {
                titleName = bundle.getString(TITLE_NAME);
            }
            url = bundle.getString("url");
        }
    }

    private void assignViews()
    {
        setTitleNmae("");
        if (!TextUtils.isEmpty(titleName)) {
            setTitleNmae(titleName);
        }
        myProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);
        webView = (WebView) findViewById(R.id.webView);
        WebViewSettings.setting(webView, null);
        WebSettings settings = webView.getSettings();

//        webView.setWebChromeClient(new WebChromeClient() {
//
//            @Override
//            public void onProgressChanged(WebView view, int newProgress)
//            {
//                super.onProgressChanged(view, newProgress);
//                if (newProgress == 100) {
//                    myProgressBar.setVisibility(View.GONE);
//                } else {
//                    if (View.INVISIBLE == myProgressBar.getVisibility()) {
//
//                        myProgressBar.setVisibility(View.VISIBLE);
//                    }
//                    myProgressBar.setProgress(newProgress);
//                }
//            }
//
//        });


        settings.setDefaultTextEncodingName("utf-8") ;//这句话去掉也没事。。只是设置了编码格式
        settings.setJavaScriptEnabled(true);  //这句话必须保留。。不解释
        settings.setDomStorageEnabled(true);//这句话必须保留。。否则无法播放优酷视频网页。。其他的可以
        webView.setWebChromeClient(new WebChromeClient());//重写一下。有的时候可能会出现问题
        webView.setWebViewClient(new WebViewClient(){//不写的话自动跳到默认浏览器了。。跳出APP了。。怎么能不写？
            public boolean shouldOverrideUrlLoading(WebView view, String url) {//这个方法必须重写。否则会出现优酷视频周末无法播放。周一-周五可以播放的问题
                if(url.startsWith("intent")||url.startsWith("youku")){
                    return true;
                }else{
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }
        });


    }

    @Override
    protected void onPause()
    {
        super.onPause();

        try {
            webView.getClass().getMethod("onPause").invoke(webView,(Object[])null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        try {
            webView.getClass().getMethod("onResume").invoke(webView,(Object[])null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (webView.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //获取历史列表
            WebBackForwardList mWebBackForwardList = webView
                    .copyBackForwardList();
            //判断当前历史列表是否最顶端,其实canGoBack已经判断过
            if (mWebBackForwardList.getCurrentIndex() > 0) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
