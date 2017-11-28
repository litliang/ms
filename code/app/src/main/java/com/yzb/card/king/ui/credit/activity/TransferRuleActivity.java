package com.yzb.card.king.ui.credit.activity;


import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.popup.SendMailDialog;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseActivity;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 * 转发设定介绍(H5)
 */
public class TransferRuleActivity extends BaseActivity
{

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        // 设置标题
        setHeader(R.mipmap.icon_back_white, "转发邮件");

        switchContentLeft(AppConstant.RES_TRANSFER_MAIL);

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

        webView.loadUrl("file:///android_asset/transferRule.html");
    }

    /**
     * Js Function
     */
    final class JavaScriptInterface {

        JavaScriptInterface() {
        }

        @JavascriptInterface
        public void sendMail() {

            try {
                // HTMl格式+图片
                MimeMultipart multipart = new MimeMultipart("related");
                // 1、拼接HTML
                BodyPart messageBodyPart = new MimeBodyPart();
                String htmlText = "<h4>转发邮件设定步骤：</h4>"
                        + "<p>1、经由网页登入ＱＱ邮箱</p>"
                        + "<img src=\""+ ServiceDispatcher.base_update_path+"mail-rule-pic-01.jpg\">"
                        + "<p>2、登入后点选页面左上方的设置键，如图所示</p>"
                        + "<img src=\""+ ServiceDispatcher.base_update_path+"mail-rule-pic-02.jpg\">"
                        + "<p>3、先点选收件规则，在选择创建收件规则，进行设定。</p>"
                        + "<img src=\""+ ServiceDispatcher.base_update_path+"mail-rule-pic-03.jpg\">";

                messageBodyPart.setContent(htmlText, "text/html;charset=utf-8");
                multipart.addBodyPart(messageBodyPart);

                final SendMailDialog.Builder builder = new SendMailDialog.Builder(TransferRuleActivity.this);

                builder.setTitle("内容发送至");

                builder.setMultiPart(multipart);

                builder.create().show();

            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }

        @JavascriptInterface
        public void share() {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, "分享内容");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(Intent.createChooser(intent, getTitle()), AppConstant.REQ_SHARE);
        }

    }
}
