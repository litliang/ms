package com.yzb.wallet.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.yzb.wallet.R;
import com.yzb.wallet.gridpasswordview.GridPasswordView;
import com.yzb.wallet.loading.AVLoadingIndicatorView;
import com.yzb.wallet.openInterface.ForgetPwdBackListener;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

/**
 * 输入支付密码
 */
public class WalletPwdDialog extends Dialog {

    public WalletPwdDialog(Activity activity) {
        super(activity);
    }

    public WalletPwdDialog(Activity activity, int theme) {
        super(activity, theme);
    }

    public static class Builder {
        private Activity activity;
        private View contentView;
        private View layout;
        private WalletPwdDialog dialog;
        private KeyBoardClickListener keyBoardClickListener;// 键盘监听
        private OnDismissListener dismissListener;// 监听关闭dialog
        private ImageView key00, key01, key02, key03, key04, key05, key06,
                key07, key08, key09, keydel;
        public TableLayout keyboard;
        private GridPasswordView inputPwd;
        private Animation slide_in;
        private Animation slide_out;
        private Boolean showForgetPwd;// 是否显示忘记密码

        private ForgetPwdBackListener forgetPwdListener;

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

        public Builder setKeyBoardClick(KeyBoardClickListener listener) {
            this.keyBoardClickListener = listener;
            return this;
        }

        public void setForgetPWdCallBack(ForgetPwdBackListener forgetPwdListener) {
            this.forgetPwdListener = forgetPwdListener;
        }

        /**
         * 是否显示忘记密码
         * @param showForgetPwd
         */
        public void showForgetPwd(Boolean showForgetPwd){
            this.showForgetPwd = showForgetPwd;
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

        public WalletPwdDialog create() {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new WalletPwdDialog(activity, R.style.Dialog);

            System.out.println("=============>");

            // 弹框加载布局
            dialog.setContentView(R.layout.wallet_pwd);

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
            layout = inflater.inflate(R.layout.wallet_pwd, null);

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            dialog.setCanceledOnTouchOutside(false);

            // 加载动画
            String indicator=activity.getIntent().getStringExtra("indicator");
            ((AVLoadingIndicatorView) layout.findViewById(R.id.avi)).setIndicator(indicator);

            // 返回
            ((LinearLayout) layout.findViewById(R.id.pwdBack)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    layout.startAnimation(slide_out);
                    dialog.dismiss();
                }
            });

            // 取消
            ((LinearLayout) layout.findViewById(R.id.pwdCancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    layout.startAnimation(slide_out);
                    dialog.dismiss();
                }
            });

            // 忘记密码
            TextView payPwdForget = (TextView) layout.findViewById(R.id.payPwdForget);

            if(showForgetPwd == true) {
                payPwdForget.setVisibility(View.VISIBLE);
                payPwdForget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (forgetPwdListener!=null){
                            forgetPwdListener.callBack();
                        }
                    }
                });
            }

            // 密码控件
            inputPwd = (GridPasswordView) layout.findViewById(R.id.inputPwd);
            inputPwd.setOnFocusChangeListener(null);

            // 键盘设置
            keyBoard(layout);

            // 遮盖层,不弹出系统自带键盘
            ((EditText) layout.findViewById(R.id.shadowText)).clearFocus();

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

        private void keyBoard(View layout) {

            keyboard = (TableLayout) layout.findViewById(R.id.keyboard);
            keyboard.setVisibility(View.VISIBLE);
            keyboard.startAnimation(slide_in);

            key00 = (ImageView) layout.findViewById(R.id.key00);
            key01 = (ImageView) layout.findViewById(R.id.key01);
            key02 = (ImageView) layout.findViewById(R.id.key02);
            key03 = (ImageView) layout.findViewById(R.id.key03);
            key04 = (ImageView) layout.findViewById(R.id.key04);
            key05 = (ImageView) layout.findViewById(R.id.key05);
            key06 = (ImageView) layout.findViewById(R.id.key06);
            key07 = (ImageView) layout.findViewById(R.id.key07);
            key08 = (ImageView) layout.findViewById(R.id.key08);
            key09 = (ImageView) layout.findViewById(R.id.key09);
            keydel = (ImageView) layout.findViewById(R.id.keydel);

            key00.setOnClickListener(keyboardClicker);
            key01.setOnClickListener(keyboardClicker);
            key02.setOnClickListener(keyboardClicker);
            key03.setOnClickListener(keyboardClicker);
            key04.setOnClickListener(keyboardClicker);
            key05.setOnClickListener(keyboardClicker);
            key06.setOnClickListener(keyboardClicker);
            key07.setOnClickListener(keyboardClicker);
            key08.setOnClickListener(keyboardClicker);
            key09.setOnClickListener(keyboardClicker);
            keydel.setOnClickListener(keyboardClicker);

            key00.setTag(0);
            key01.setTag(1);
            key02.setTag(2);
            key03.setTag(3);
            key04.setTag(4);
            key05.setTag(5);
            key06.setTag(6);
            key07.setTag(7);
            key08.setTag(8);
            key09.setTag(9);

        }

        private View.OnClickListener keyboardClicker = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.keydel) {//删除最新一个
                    if (inputPwd.getPassWord().toString().length() > 0) {
                        String text = inputPwd.getPassWord().toString().substring(0, inputPwd.getPassWord().toString().length() - 1);
                        inputPwd.setPassword(text);
                    }
                } else {//输入
                    String pwd = inputPwd.getPassWord().toString() + Integer.parseInt(v.getTag().toString());
                    inputPwd.setPassword(pwd);

                    if (!StringUtil.isEmpty(pwd) && pwd.length() == WalletConstant.PAY_PWD_LENGTH) {

                        keyboard.setVisibility(View.GONE);
                        keyboard.startAnimation(slide_out);

                        ((LinearLayout) layout.findViewById(R.id.payPwdLinear)).setVisibility(View.GONE);
                        ((LinearLayout) layout.findViewById(R.id.waiting)).setVisibility(View.VISIBLE);

                        // 密码回调
                        keyBoardClickListener.backPassword(pwd);
                    }
                }
            }
        };
    }
}