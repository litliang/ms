package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.util.StringUtils;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/12/1
 * 描  述：
 */
public class PayMentDialog  extends Dialog{

    public PayMentDialog(Context context) {
        super(context);
    }

    public PayMentDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private ConfirmDialog dialog;
        private String title;
        private String cancel;
        private String positive;
        private DialogInterface.OnClickListener positiveButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public void dismiss() {
            dialog.dismiss();
        }

        /**
         * 设置文字标题
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置取消按钮
         */
        public Builder setCancelButton(String cancel) {
            this.cancel = cancel;
            return this;
        }

        /**
         * 设置确认按钮
         */
        public Builder setPositiveButton(String positive,
                                         DialogInterface.OnClickListener listener) {
            this.positive = positive;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 初始化dialog
         */
        public ConfirmDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 实例化自定义dialog
            dialog = new ConfirmDialog(context, R.style.Dialog);

            View layout = inflater.inflate(R.layout.unbundling_dialog, null);

            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // 获取标题对象并添加数据
            ((TextView) layout.findViewById(R.id.title)).setText(title);

            // 设置取消按钮
            TextView cancelBtn = (TextView) layout.findViewById(R.id.cancel);
            if (!StringUtils.isEmpty(cancel)) {
                cancelBtn.setVisibility(View.VISIBLE);
                cancelBtn.setText(cancel);
            }

            // 设置确认按钮
            TextView positiveBtn = (TextView) layout.findViewById(R.id.positive);
            if (!StringUtils.isEmpty(positive) && positiveButtonClickListener != null) {
                positiveBtn.setVisibility(View.VISIBLE);
                positiveBtn.setText(positive);
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        positiveButtonClickListener.onClick(dialog,
                                DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }

            // 设置取消按钮
            ((TextView) layout.findViewById(R.id.cancel))
                    .setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

            dialog.setCanceledOnTouchOutside(false);

            dialog.setContentView(layout);

            return dialog;
        }
    }
}
