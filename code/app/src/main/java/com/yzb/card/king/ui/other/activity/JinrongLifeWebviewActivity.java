package com.yzb.card.king.ui.other.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.app.presenter.H5Presenter;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.WebViewSettings;

/**
 * 类  名：金融生活 webview加载页面
 * 作  者：Li Yubing
 * 日  期：2017/10/13
 * 描  述：
 */
public class JinrongLifeWebviewActivity extends BaseActivity {
    public static final String CATEGORY = "category";
    public static final String TITLE_NAME = "titleName";
    private WebView webView;
    private String titleName = "";
    private String url;
    private String content;
    private ProgressBar myProgressBar;

    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

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

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("xxx提示").setMessage(message).setPositiveButton("确定", null);
                builder.setCancelable(false);
                builder.setIcon(R.mipmap.ic_launcher);
                AlertDialog dialog = builder.create();
                dialog.show();
                result.confirm();
                return true;
            }

            //扩展浏览器上传文件
            //3.0++版本
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType)
            {
                openFileChooserImpl(uploadMsg);
            }

            //3.0--版本
            public void openFileChooser(ValueCallback<Uri> uploadMsg)
            {
                openFileChooserImpl(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
            {
                openFileChooserImpl(uploadMsg);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams)
            {
                onenFileChooseImpleForAndroid(filePathCallback);
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
        settings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new MyJSInterface(), "app");

        settings.setUserAgentString("com.yzb.card.king");


    }


    class MyJSInterface {

        @JavascriptInterface
        public void appUserLogin() //提供给js调用的方法
        {

            Intent intent = new Intent(JinrongLifeWebviewActivity.this, LoginActivity.class);

            startActivity(intent);

            finish();

        }

        @JavascriptInterface
        public void callPhone(String phoneNumber) //提供给js调用的方法
        {

            Uri uri=Uri.parse("tel:"+phoneNumber);
            Intent intent=new Intent(Intent.ACTION_CALL,uri);
            startActivity(intent);
        }
    }

    public ValueCallback<Uri> mUploadMessage;

    public void openFileChooserImpl(ValueCallback<Uri> uploadMsg)
    {
        mUploadMessage = uploadMsg;
//        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//        i.addCategory(Intent.CATEGORY_OPENABLE);
//        i.setType("image/*");
//        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, FILECHOOSER_RESULTCODE);
    }

    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    public void onenFileChooseImpleForAndroid(ValueCallback<Uri[]> filePathCallback)
    {
        mUploadMessageForAndroid5 = filePathCallback;

//        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
//        contentSelectionIntent.setType("image/*");
//
//        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
//        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
//        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
//
//        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
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
