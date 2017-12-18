package com.yzb.wallet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.yzb.wallet.gridpasswordview.GridPasswordView;
import com.yzb.wallet.R;

/**
 * 重置支付密码
 */
public class WalletPayPwdDialog extends Dialog {

    public WalletPayPwdDialog(Context context) {
        super(context);
    }

    public WalletPayPwdDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private String payPwdMsg;// 重置密码信息提示

        private Context context;
        private View contentView;
        private WalletPayPwdDialog dialog;
        private GridPasswordView.OnPasswordChangedListener pwdChangedListener;// 监听密码输入
        private OnClickListener positiveBtnClickListener;// 确认按钮
        private OnDismissListener dismissListener;
        private View layoutPwd;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPayPwdMsg(String payPwdMsg) {
            this.payPwdMsg = payPwdMsg;
            return this;
        }

        public Builder setPositiveBtn(OnClickListener listener) {
            this.positiveBtnClickListener = listener;
            return this;
        }

        public Builder setPayPwd(GridPasswordView.OnPasswordChangedListener listener) {
            this.pwdChangedListener = listener;
            return this;
        }

        public Builder setDismissListener(OnDismissListener listener) {
            this.dismissListener = listener;
            return this;
        }

        public Button getPositiveBtn() {
            return (Button) layoutPwd.findViewById(R.id.positiveBtn);
        }

        /**
         * 关闭dialog
         */
        public void dismiss() {
            dialog.dismiss();
        }

        public WalletPayPwdDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new WalletPayPwdDialog(context, R.style.Dialog);

            // 弹框加载布局
            dialog.setContentView(R.layout.wallet_pay_pwd);

            Window dialogWindow = dialog.getWindow();

            /* 弹出框弹出位置
             * Gravity.BOTTOM底部显示
             * Gravity.LEFT靠左显示
             * Gravity.RIGHT靠右显示
             * Gravity.TOP顶部显示
             */
            dialogWindow.setGravity(Gravity.CENTER);

            // 获取输入密码布局
            layoutPwd = inflater.inflate(R.layout.wallet_pay_pwd, null);

            // 设置银行名称
            ((TextView) layoutPwd.findViewById(R.id.payPwdMsg)).setText(payPwdMsg);

            dialog.addContentView(layoutPwd, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));


            // 取消按钮
            ((Button) layoutPwd.findViewById(R.id.cancelBtn))
                    .setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dismiss();
                        }
                    });

            // 确认按钮
            if (positiveBtnClickListener != null) {
                ((Button) layoutPwd.findViewById(R.id.positiveBtn))
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                positiveBtnClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);
                            }
                        });
            }

            // 监听密码输入
            GridPasswordView goodsPwd = (GridPasswordView) layoutPwd.findViewById(R.id.payPwd);
            goodsPwd.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
                @Override
                public void onChanged(String psw) {
                    pwdChangedListener.onChanged(psw);
                }

                @Override
                public void onMaxLength(String psw) {
                    pwdChangedListener.onMaxLength(psw);
                }
            });

            // 监听dialog关闭
            if (dismissListener != null) {
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dismissListener.onDismiss(dialog);
                    }
                });
            }


            dialog.setContentView(layoutPwd);
            return dialog;
        }
    }
}