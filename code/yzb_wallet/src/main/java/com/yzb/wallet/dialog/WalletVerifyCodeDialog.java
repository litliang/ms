package com.yzb.wallet.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TableLayout;
import android.widget.TextView;

import com.yzb.wallet.R;
import com.yzb.wallet.activity.PayPwdForgetActivity;
import com.yzb.wallet.gridpasswordview.GridPasswordView;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

/**
 * 输入验证码
 */
public class WalletVerifyCodeDialog extends Dialog {

    public WalletVerifyCodeDialog(Activity activity) {
        super(activity);
    }

    public WalletVerifyCodeDialog(Activity activity, int theme) {
        super(activity, theme);
    }

    public static class Builder {
        private Activity activity;
        private View contentView;
        private View layout;
        private WalletVerifyCodeDialog dialog;
        private OnClickListener verifyCodeListener;//获取验证码
        private OnClickListener payListener;//支付
        private OnDismissListener dismissListener;// 监听关闭dialog
        private Animation slide_in;
        private Animation slide_out;

        // 验证码
        private EditText verifyCode;
        private Button getVerifyCode;

        // 手机号
        private String mobile;

        // 倒计时
        private int recLen = 60;

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

        public Builder setVerifyCodeClick(OnClickListener listener) {
            this.verifyCodeListener = listener;
            return this;
        }

        public Builder setPayClick(OnClickListener listener) {
            this.payListener = listener;
            return this;
        }

        public Builder setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        /*验证码*/
        public TextView getVerifyCode() {
            return (TextView) layout.findViewById(R.id.verifyCode);
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

        public WalletVerifyCodeDialog create() {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new WalletVerifyCodeDialog(activity, R.style.Dialog);

            System.out.println("=============>");

            // 弹框加载布局
            dialog.setContentView(R.layout.wallet_verify_code);

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
            layout = inflater.inflate(R.layout.wallet_verify_code, null);

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            dialog.setCanceledOnTouchOutside(false);

            // 验证码
            verifyCode = (EditText) layout.findViewById(R.id.verifyCode);
            getVerifyCode = (Button) layout.findViewById(R.id.getVerifyCode);

            // 取消
            ((TextView) layout.findViewById(R.id.mobile)).setText(mobile);

            // 取消
            ((LinearLayout) layout.findViewById(R.id.pwdCancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout.startAnimation(slide_out);
                    dialog.dismiss();
                }
            });

            // 清空验证码
            ((ImageView) layout.findViewById(R.id.clearVerifyCode)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyCode.setText("");
                }
            });

            // 获取验证码
            if(recLen == 60){
                getVerifyCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verifyCodeListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        handler.postDelayed(runnable, 1000);
                    }
                });
            }

            ((Button)layout.findViewById(R.id.payBtn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    payListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
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

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recLen--;
                getVerifyCode.setText("重新获取(" + recLen + ")s");
                handler.postDelayed(this, 1000);
                if(recLen == 0){
                    recLen = 60;
                    getVerifyCode.setText("点击获取");
                    handler.removeCallbacks(runnable);
                }
            }
        };

    }
}