package com.yzb.card.king.ui.appwidget.appdialog;

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

import com.yzb.card.king.R;
import com.yzb.card.king.util.StringUtils;


/**
 * 等待加载dialog
 */
public class WaitingDialog extends Dialog {
    private static Context context;
    // dialog布局对象
    private static View waiting;
    private static String waitingText;
    private static WaitingDialog waitingDialog;

    private WaitingDialog(Context context, String waitingText) {
        super(context, R.style.Dialog);
        this.context = context;
        this.waitingText = waitingText;
    }

    private static void init() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 获取加载布局对象
        waiting = inflater.inflate(R.layout.dialog_waiting, null);

        if (!StringUtils.isEmpty(waitingText)) {
            TextView textView = ((TextView) waiting.findViewById(R.id.waitingText));
            textView.setText(waitingText);
        }

        // 弹框加载布局
        waitingDialog.setContentView(R.layout.dialog_waiting);
        Window dialogWindow = waitingDialog.getWindow();

        // 弹出框弹出位置
        dialogWindow.setGravity(Gravity.CENTER);

        waitingDialog.setCanceledOnTouchOutside(false);

        waitingDialog.addContentView(waiting, new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        waitingDialog.setOnKeyListener(keyListener);

        waitingDialog.setContentView(waiting);

        waitingDialog.show();

    }

    public static void create(Context context, String waitingText) {

        waitingDialog = new WaitingDialog(context, waitingText);

        init();
    }

    public static void close() {
        if (null != waitingDialog && waitingDialog.isShowing()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitingDialog.dismiss();
        }
    }

    private static OnKeyListener keyListener = new OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//                return true;
//            } else {
                return false;
//            }
        }
    };

}