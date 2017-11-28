package com.yzb.card.king.ui.credit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.popup.SendMailDialog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 * 转发邮箱
 */
public class ForwardMailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout panel_back;
    private ImageView imgsend, imgshare;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forward_mail);
        initView();
    }

    private void initView()
    {
        panel_back = (LinearLayout) findViewById(R.id.panel_back);
        imgsend = (ImageView) findViewById(R.id.imgsend);
        imgshare = (ImageView) findViewById(R.id.imgshare);
        panel_back.setOnClickListener(this);
        imgsend.setOnClickListener(this);
        imgshare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.panel_back:
                finish();
                break;
            case R.id.imgsend:
                showSendEmail();
                break;
            case R.id.imgshare:
                ShareDialogFragment.getInstance("", "").show(getSupportFragmentManager(), "");
                break;
        }
    }

    private void showSendEmail()
    {
        try
        {
            // HTMl格式+图片
            MimeMultipart multipart = new MimeMultipart("related");
            // 1、拼接HTML
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<h4>转发邮件设定步骤：</h4>"
                    + "<p>1、经由网页登入ＱＱ邮箱</p>"
                    + "<img src=\"" + ServiceDispatcher.base_update_path + "mail-rule-pic-01.jpg\">"
                    + "<p>2、登入后点选页面左上方的设置键，如图所示</p>"
                    + "<img src=\"" + ServiceDispatcher.base_update_path + "mail-rule-pic-02.jpg\">"
                    + "<p>3、先点选收件规则，在选择创建收件规则，进行设定。</p>"
                    + "<img src=\"" + ServiceDispatcher.base_update_path + "mail-rule-pic-03.jpg\">";

            messageBodyPart.setContent(htmlText, "text/html;charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            final SendMailDialog.Builder builder = new SendMailDialog.Builder(ForwardMailActivity.this);

            builder.setTitle("内容发送至");

            builder.setMultiPart(multipart);

            builder.create().show();
        } catch (MessagingException e)
        {
            e.printStackTrace();
        }

    }
}
