package com.yzb.card.king.ui.other.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.app.presenter.H5Presenter;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.ValidatorUtil;
import com.yzb.card.king.util.WebViewSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.22
 * WebView加载；
 */
public class WebViewClientActivity extends BaseActivity implements AppBaseView {
    public static final String CATEGORY = "category";
    public static final String TITLE_NAME = "titleName";
    private WebView webView;
    private String category;//类别；
    private String titleName = "";
    private String url;
    private String content;
    private H5Presenter h5Presenter;
    private ProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_protocol);
        recvIntentData();
        assignViews();

        h5Presenter = new H5Presenter(this);

        initData();

    }

    private void initData()
    {

        // 如果有url，直接加载url即可；
        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);


        } else if (!TextUtils.isEmpty(content)) {
            webView.loadData(content, "text/html;charset=utf-8", null);
        } else if (!TextUtils.isEmpty(category)) {
            getData();
        } else {
            UiUtils.shortToast("url不能为空");
        }
    }

    private void recvIntentData()
    {
        Bundle bundle = getIntent().getBundleExtra(AppConstant.INTENT_BUNDLE);
        if (bundle != null) {
            category = bundle.getString(CATEGORY);
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
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    myProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.INVISIBLE == myProgressBar.getVisibility()) {

                        myProgressBar.setVisibility(View.VISIBLE);
                    }
                    myProgressBar.setProgress(newProgress);
                }
            }

        });
        settings.setJavaScriptEnabled(true);



    }

    public void getData()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("category", category);
        h5Presenter.loadData(param);
    }

    @Override
    public void onViewCallBackSucess(Object data)
    {
        if (data != null) {
            JSONObject jsonObject = JSON.parseObject(String.valueOf(data));
            if (jsonObject != null) {
                //这种写法可以正确解码
                String content = jsonObject.getString("content");
                //检测是否是链接
                if (ValidatorUtil.isUrl(content)) {
                    webView.loadUrl(content);
                } else {
                    webView.loadData(content, "text/html;charset=utf-8", null);
                }
            }
        }
    }

    @Override
    public void onViewCallBackFail(String failMsg)
    {
        LogUtil.i("failMsg==" + failMsg);
        //toastCustom(failMsg);
    }


}
