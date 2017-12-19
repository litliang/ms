package com.yzb.wallet.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.wallet.R;
import com.yzb.wallet.activity.PayPwdForgetActivity;
import com.yzb.wallet.util.WalletConstant;

import java.math.BigDecimal;

/**
 * 付款详情
 */
public class WalletPayInfoDialog extends Dialog {

    public WalletPayInfoDialog(Activity activity) {
        super(activity);
    }

    public WalletPayInfoDialog(Activity activity, int theme) {
        super(activity, theme);
    }

    public static class Builder {
        private Activity activity;
        private View contentView;
        private View layout;
        private WalletPayInfoDialog dialog;
        private OnDismissListener dismissListener;// 监听关闭dialog
        private OnClickListener payMethodListener;// 付款方式
        private OnClickListener payListener;// 确认付款
        private OnClickListener backListener;// 返回

        private String summary;
        private String amount;
        private String accountType;
        private String sortCode;
        private String payMethodName;
        private boolean back = false;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /*付款方式*/
        public Builder setPayMethodListener(OnClickListener listener) {
            this.payMethodListener = listener;
            return this;
        }

        /**
         * 设置订单信息
         * @param summary
         * @return
         */
        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        /**
         * 设置支付方式
         * @param accountType
         * @return
         */
        public Builder setAccountType(String accountType) {
            this.accountType = accountType;
            return this;
        }

        /**
         * 设置银行卡关联码
         * @param sortCode
         * @return
         */
        public Builder setSortCode(String sortCode) {
            this.sortCode = sortCode;
            return this;
        }

        /**
         * 设置付款方式名称
         * @param payMethodName
         * @return
         */
        public Builder setPayMethodName(String payMethodName) {
            this.payMethodName = payMethodName;
            return this;
        }

        /**
         * 设置金额
         * @param amount
         * @return
         */
        public Builder setAmount(String amount) {
            this.amount = amount;
            return this;
        }

        /*付款方式*/
        public TextView getAccountTypeView() {
            return (TextView) layout.findViewById(R.id.accountType);
        }

        /*付款方式名称*/
        public TextView getPayMethodNameView() {
            return (TextView) layout.findViewById(R.id.payMethodName);
        }

        /*银行卡关联码*/
        public TextView getSortCodeView() {
            return (TextView) layout.findViewById(R.id.sortCode);
        }

        /*确认付款*/
        public Builder setPayListener(OnClickListener listener) {
            this.payListener = listener;
            return this;
        }

        /*返回*/
        public Builder setBackListener(OnClickListener listener) {
            this.backListener = listener;
            return this;
        }

        public void dismiss() {
            dialog.dismiss();
        }

        public Builder setDismiss(OnDismissListener listener) {
            this.dismissListener = listener;
            return this;
        }

        public WalletPayInfoDialog create() {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new WalletPayInfoDialog(activity, R.style.Dialog);

            // 弹框加载布局
            dialog.setContentView(R.layout.wallet_pay_info);

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

            // 获取加载布局对象
            layout = inflater.inflate(R.layout.wallet_pay_info, null);

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            dialog.setCanceledOnTouchOutside(false);

            // 返回
            ((LinearLayout) layout.findViewById(R.id.pwdBack)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(backListener!=null)
                        backListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    dialog.dismiss();
                }
            });

            // 取消
            ((LinearLayout) layout.findViewById(R.id.pwdCancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(backListener!=null)
                        backListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    dialog.dismiss();
                }
            });

            // 付款内容
            ((TextView) layout.findViewById(R.id.summary)).setText(summary);

            // 支付方式
            ((TextView) layout.findViewById(R.id.accountType)).setText(accountType);
            ((TextView) layout.findViewById(R.id.sortCode)).setText(sortCode);
            ((TextView) layout.findViewById(R.id.payMethodName)).setText(payMethodName);

            // 付款金额
            ((TextView) layout.findViewById(R.id.amount)).setText(new BigDecimal(amount).setScale(2).toString());

            // 付款方式
            ((LinearLayout) layout.findViewById(R.id.payMethodLayout)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payMethodListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                }
            });

            // 确认付款
            ((Button) layout.findViewById(R.id.payBtn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

            // 物理按键返回
            dialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {

                    if (keyCode == KeyEvent.KEYCODE_BACK ) {

                        // 防止2次调用
                        if(back)
                            return false;

                        back = true;

                       if(backListener!=null)
                            backListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        return false;
                    }
                    return false;
                }
            });

            dialog.setContentView(layout);

            return dialog;
        }

    }
}