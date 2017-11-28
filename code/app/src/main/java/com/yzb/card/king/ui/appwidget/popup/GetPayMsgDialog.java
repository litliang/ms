package com.yzb.card.king.ui.appwidget.popup;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.util.StringUtils;

/**
 * 收付款提示dialog
 */
public class GetPayMsgDialog extends Dialog {

    public GetPayMsgDialog(Context context) {
        super(context);
    }

    public GetPayMsgDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private View contentView;
        private GetPayMsgDialog dialog;
        private OnDismissListener dismissListener;// 监听关闭dialog
        private String title;
        private String sysOrderNo;
        private String orderTime;
        private String payMethod;
        private String status;
        private String amount;
        private String color;
        private String bankId;
        private View layout;
        private int exchangeMoneyTitle = 0;

        private TextView tvPayWay;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public void dismiss() {
            dialog.dismiss();
        }

        public Builder setMsg(String title, String sysOrderNo, String orderTime, String payMethod, String status, String amount) {
            this.title = title;
            this.sysOrderNo = sysOrderNo;
            this.orderTime = orderTime;
            this.payMethod = payMethod;
            this.status = status;
            this.amount = amount;
            return this;
        }

        public void setExchangeMoneyTitle(int exchangeMoneyTitle){

            this.exchangeMoneyTitle = exchangeMoneyTitle;

        }

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Builder setBankLogo(String bankId) {
            this.bankId = bankId;
            return this;
        }

        public Builder setDismiss(OnDismissListener listener) {
            this.dismissListener = listener;
            return this;
        }

        public GetPayMsgDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new GetPayMsgDialog(context, R.style.Dialog);

            // 弹框加载布局
            dialog.setContentView(R.layout.dialog_getpaymsg);

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
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams wmParams = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            wmParams.width = display.getWidth();
            dialogWindow.setAttributes(wmParams);

            // 获取加载布局对象
            layout = inflater.inflate(R.layout.dialog_getpaymsg, null);

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            dialog.setCanceledOnTouchOutside(false);

            if (dismissListener != null) {
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dismissListener.onDismiss(dialog);
                    }
                });
            }

            // 修改标题颜色
            if ("1".equals(color))
                ((LinearLayout) layout.findViewById(R.id.header)).setBackgroundResource(R.drawable.style_bg_blue);
            else if ("2".equals(color))
                ((LinearLayout) layout.findViewById(R.id.header)).setBackgroundResource(R.drawable.style_bg_red);
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            ((TextView) layout.findViewById(R.id.sysOrderNo)).setText(sysOrderNo);
            ((TextView) layout.findViewById(R.id.orderTime)).setText(orderTime);
            if (!StringUtils.isEmpty(payMethod)) {
                if (!StringUtils.isEmpty(bankId)) {
                    //((ImageView) layout.findViewById(R.id.bankLogo)).setImageResource(ImageUtils.getBankFont(Integer.parseInt(bankId)));
                    ((TextView) layout.findViewById(R.id.bankCardNo)).setText(payMethod);
                } else {
                    ((TextView) layout.findViewById(R.id.payMethod)).setText(payMethod);
                }
            } else {
                ((LinearLayout) layout.findViewById(R.id.payMethodLayout)).setVisibility(View.GONE);
            }

            ((TextView) layout.findViewById(R.id.status)).setText(status);

            String handledAmount= String.format("%.2f", Float.parseFloat(amount));
            ((TextView) layout.findViewById(R.id.amount)).setText("¥" + handledAmount);

            tvPayWay = (TextView) layout.findViewById(R.id.tvPayWay);

            if(exchangeMoneyTitle != 0){
                tvPayWay.setText(exchangeMoneyTitle);
            }


            dialog.setContentView(layout);

            return dialog;
        }

    }
}