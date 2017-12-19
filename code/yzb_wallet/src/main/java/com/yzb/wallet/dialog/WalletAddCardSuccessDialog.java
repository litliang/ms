package com.yzb.wallet.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.wallet.R;

/**
 * 添加银行卡成功
 */
public class WalletAddCardSuccessDialog extends Dialog {

    public WalletAddCardSuccessDialog(Activity activity) {
        super(activity);
    }

    public WalletAddCardSuccessDialog(Activity activity, int theme) {
        super(activity, theme);
    }

    public static class Builder {
        private Activity activity;
        private View contentView;
        private View layout;
        private WalletAddCardSuccessDialog dialog;
        private OnDismissListener dismissListener;// 监听关闭dialog
        private OnClickListener nextListener;// 继续支付
        private Animation slide_in;
        private Animation slide_out;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public void dismiss() {
            dialog.dismiss();
        }

        public Builder setDismiss(OnDismissListener listener) {
            this.dismissListener = listener;
            return this;
        }

        public Builder setNextClick(OnClickListener listener) {
            this.nextListener = listener;
            return this;
        }

        /**
         * 自定义Dialog监听器
         */
        public interface KeyBoardClickListener {
            /**
             * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
             */
            public void backPassword(String pwd);
        }

        public WalletAddCardSuccessDialog create() {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new WalletAddCardSuccessDialog(activity, R.style.Dialog);

            System.out.println("=============>");

            // 弹框加载布局
            dialog.setContentView(R.layout.wallet_add_card_success);

            Window dialogWindow = dialog.getWindow();

            /* 弹出框弹出位置
             * Gravity.BOTTOM底部显示
             * Gravity.LEFT靠左显示
             * Gravity.RIGHT靠右显示
             * Gravity.TOP顶部显示
             */
            dialogWindow.setGravity(Gravity.BOTTOM);

            // 弹出、消失动画
            dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);

            // 将对话框的大小按屏幕大小的百分比设置
            WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams wmParams = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            wmParams.width = display.getWidth();
            dialogWindow.setAttributes(wmParams);

            // 动画
            slide_in = AnimationUtils.loadAnimation(activity, R.anim.slide_in_right);
            slide_out = AnimationUtils.loadAnimation(activity, R.anim.slide_out_left);

            // 获取加载布局对象
            layout = inflater.inflate(R.layout.wallet_add_card_success, null);

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            dialog.setCanceledOnTouchOutside(false);


            // 取消
            ((LinearLayout) layout.findViewById(R.id.pwdCancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout.startAnimation(slide_out);
                    dialog.dismiss();
                }
            });

            // 继续支付
            ((LinearLayout)layout.findViewById(R.id.nextLinear)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                }
            });

            // 监听关闭dialog
            if (dismissListener != null) {
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dismissListener.onDismiss(dialog);
                    }
                });
            }

            dialog.setContentView(layout);

            return dialog;
        }

    }
}