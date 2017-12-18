package com.yzb.wallet.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TextView;

import com.yzb.wallet.R;
import com.yzb.wallet.loading.AVLoadingIndicatorView;
import com.yzb.wallet.util.WalletConstant;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 等待加载dialog
 */
public class WalletOpen extends Dialog {

    public WalletOpen(Activity activity) {
        super(activity);
    }

    public WalletOpen(Activity activity, int theme) {
        super(activity, theme);
    }

    public static class Builder {

        private Activity activity;
        // dialog布局对象
        private View waiting;
        private WalletOpen walletOpen;
        private OnDismissListener dismissListener;// 监听关闭dialog

        private mTimerTask timerTask;
        private Timer timer;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder setDismiss(OnDismissListener listener) {
            this.dismissListener = listener;
            return this;
        }

        public void loading() {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            walletOpen = new WalletOpen(activity, R.style.Dialog);

            // 获取加载布局对象
            waiting = inflater.inflate(R.layout.wallet_waiting, null);

            ((TextView) waiting.findViewById(R.id.waitingText)).setText(WalletConstant.OPEN_WALLET);

            // 弹框加载布局
            walletOpen.setContentView(R.layout.wallet_waiting);
            Window dialogWindow = walletOpen.getWindow();

            // 弹出框弹出位置
            dialogWindow.setGravity(Gravity.CENTER);

            // 加载动画
            String indicator=activity.getIntent().getStringExtra("indicator");
            ((AVLoadingIndicatorView) waiting.findViewById(R.id.avi)).setIndicator(indicator);

            walletOpen.setCanceledOnTouchOutside(false);

            walletOpen.addContentView(waiting, new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            walletOpen.setOnKeyListener(keyListener);

            // 监听关闭dialog
            if (dismissListener != null) {
                walletOpen.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dismissListener.onDismiss(dialog);
                    }
                });
            }

            walletOpen.setContentView(waiting);

            walletOpen.show();

            // 定时关闭loading
            timerTask = new mTimerTask();
            timer = new Timer(true);
            timer.schedule(timerTask, 1000);

        }

        private class mTimerTask extends TimerTask {
            @Override
            public void run() {
                if (null != walletOpen || walletOpen.isShowing()) {
                    walletOpen.dismiss();
                }
            }
        }

        private OnKeyListener keyListener = new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };
    }
}