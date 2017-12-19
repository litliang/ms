package com.yzb.wallet.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.R;
import com.yzb.wallet.adapter.PayMethodAdapter;
import com.yzb.wallet.openInterface.WalletBackListener;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.WalletConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 付款方式
 */
public class WalletPayMethodDialog extends Dialog {

    public WalletPayMethodDialog(Activity activity) {
        super(activity);
    }

    public WalletPayMethodDialog(Activity activity, int theme) {
        super(activity, theme);
    }

    public static class Builder {
        private Activity activity;
        private View contentView;
        private View layout;
        private WalletPayMethodDialog dialog;
        private OnDismissListener dismissListener;// 监听关闭dialog
        private chosePayMethod chosePayMethod;
        private OnClickListener addCardClickListener;// 添加新卡

        private List<Map> payMethodList;// 付款方式

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

        public Builder setChosePayMethod(chosePayMethod chosePayMethod) {
            this.chosePayMethod = chosePayMethod;
            return this;
        }

        /**
         * 设置付款方式
         * @param payMethodList
         * @return
         */
        public Builder setPayMethodList(List<Map> payMethodList) {
            this.payMethodList = payMethodList;
            return this;
        }

        /**
         * 添加新卡
         * @param listener
         * @return
         */
        public Builder addCard(OnClickListener listener) {
            this.addCardClickListener = listener;
            return this;
        }

        /**
         * 自定义Dialog监听器
         */
        public interface chosePayMethod {
            /**
             * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
             */
            public void getPayMethod(Map<String, String> payMethod);
        }

        public WalletPayMethodDialog create() {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new WalletPayMethodDialog(activity, R.style.Dialog);

            System.out.println("=============>");

            // 弹框加载布局
            dialog.setContentView(R.layout.wallet_pay_method);

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
            layout = inflater.inflate(R.layout.wallet_pay_method, null);

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            dialog.setCanceledOnTouchOutside(true);

            // 取消
            ((LinearLayout) layout.findViewById(R.id.pwdCancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            // 添加新卡
            if (addCardClickListener != null) {
                ((LinearLayout) layout.findViewById(R.id.addCard)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        addCardClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }

            // 加载付款方式
            addData(payMethodList);

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

        /**
         * 加载数据
         */
        public void addData(List<Map> payMethodList) {

            PayMethodAdapter payMethodAdapter = new PayMethodAdapter(activity, payMethodList, R.layout.wallet_pay_method_view);
            ListView paymentList = (ListView) layout.findViewById(R.id.payMethodList);
            paymentList.setAdapter(payMethodAdapter);
            paymentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView accountType = (TextView) view.findViewById(R.id.accountType);
                    TextView typeName = (TextView) view.findViewById(R.id.typeName);
                    TextView sortCode = (TextView) view.findViewById(R.id.sortCode);
                    TextView cardType = (TextView) view.findViewById(R.id.cardType);
                    TextView payType = (TextView) view.findViewById(R.id.payType);
                    Map<String, String> payMethod = new HashMap<>();
                    payMethod.put("accountType", accountType.getText().toString());
                    payMethod.put("typeName", typeName.getText().toString());
                    payMethod.put("sortCode", sortCode.getText().toString());
                    payMethod.put("cardType", cardType.getText().toString());
                    payMethod.put("payType", payType.getText().toString());
                    chosePayMethod.getPayMethod(payMethod);
                }
            });
        }
    }
}