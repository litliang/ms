package com.yzb.card.king.ui.appwidget.popup;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.Mail;

import javax.mail.internet.MimeMultipart;

/**
 * 发送邮箱dialog
 */
public class SendMailDialog extends Dialog {

    public SendMailDialog(Context context)
    {
        super(context);
    }

    public SendMailDialog(Context context, int theme)
    {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private View contentView;
        private SendMailDialog dialog;
        private String title;
        private View layout;
        private MimeMultipart multipart;

        public Builder(Context context)
        {
            this.context = context;
        }

        public Builder setContentView(View v)
        {
            this.contentView = v;
            return this;
        }

        public void dismiss()
        {
            dialog.dismiss();
        }

        public Builder setTitle(String title)
        {
            this.title = title;
            return this;
        }

        public Builder setMultiPart(MimeMultipart multipart)
        {
            this.multipart = multipart;
            return this;
        }

        public SendMailDialog create()
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new SendMailDialog(context, R.style.Dialog);

            // 弹框加载布局
            dialog.setContentView(R.layout.dialog_send_mail);

            Window dialogWindow = dialog.getWindow();

            /* 弹出框弹出位置
             * Gravity.BOTTOM底部显示
             * Gravity.LEFT靠左显示
             * Gravity.RIGHT靠右显示
             * Gravity.TOP顶部显示
             */
            dialogWindow.setGravity(Gravity.CENTER);

            // 弹出、消失动画
            dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);

            // 将对话框的大小按屏幕大小的百分比设置
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams wmParams = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            wmParams.width = display.getWidth();
            dialogWindow.setAttributes(wmParams);

            // 获取加载布局对象
            layout = inflater.inflate(R.layout.dialog_send_mail, null);

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            dialog.setCanceledOnTouchOutside(false);

            if (title != null)
            {
                ((TextView) layout.findViewById(R.id.title)).setText(title);
            }

            // 取消
            ((TextView) layout.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    dialog.dismiss();
                }
            });

            // 确定
            ((TextView) layout.findViewById(R.id.positive)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    final EditText sendTo = (EditText) layout.findViewById(R.id.inputContent);
                    new Thread(new Runnable() {
                        @Override
                        public void run()
                        {
                            boolean flag = Mail.sendMail(context, sendTo.getText().toString(), AppConstant.MAIL_TITLE_RULE, multipart);
                            if (flag)
                            {
                                dialog.dismiss();
                            } else
                            {
                                ((TextView) layout.findViewById(R.id.message)).setText("发送失败");
                            }
                        }
                    }).start();


                }
            });

            dialog.setContentView(layout);

            return dialog;
        }

    }
}