package com.yzb.wallet.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.yzb.wallet.R;
import com.yzb.wallet.util.StringUtil;


/**
 * Dialog类似Comfirm的弹出框
 */
public class ConfirmDialog extends Dialog {

    public ConfirmDialog(Context context) {
        super(context);
    }

    public ConfirmDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Activity activity;
        private ConfirmDialog dialog;
        private String title;
        private String cancel;
        private String positive;
        private OnClickListener positiveButtonClickListener;

        public Builder(Activity activity) {
            this.activity = activity;
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
                                         OnClickListener listener) {
            this.positive = positive;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 初始化dialog
         */
        public ConfirmDialog create() {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 实例化自定义dialog
            dialog = new ConfirmDialog(activity, R.style.Dialog);

            View layout = inflater.inflate(R.layout.wallet_confirm, null);

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            // 获取标题对象并添加数据
            ((TextView) layout.findViewById(R.id.title)).setText(title);

            // 设置取消按钮
            TextView cancelBtn = (TextView) layout.findViewById(R.id.cancel);
            if (!StringUtil.isEmpty(cancel)) {
                cancelBtn.setVisibility(View.VISIBLE);
                cancelBtn.setText(cancel);
            }

            // 设置确认按钮
            TextView positiveBtn = (TextView) layout.findViewById(R.id.positive);
            if (!StringUtil.isEmpty(positive) && positiveButtonClickListener != null) {
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
            if (!StringUtil.isEmpty(cancel)) {
                ((TextView) layout.findViewById(R.id.cancel)).setVisibility(View.VISIBLE);
                ((TextView) layout.findViewById(R.id.cancel))
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
            }

            dialog.setCanceledOnTouchOutside(false);

            dialog.setContentView(layout);

            return dialog;
        }
    }
}