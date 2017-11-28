package com.yzb.card.king.util;

import android.content.Context;

import com.yzb.card.king.ui.appwidget.appdialog.SimpleProgressDialog;


/**
 * <pre>
 * 文件名：	ProgressDialogUtil.java
 * 作　者：	lijianqiang
 * 描　述：	ProgressDialog工具类
 *
 * </pre>
 */
public class ProgressDialogUtil {

    private SimpleProgressDialog dialog = null;

    private static ProgressDialogUtil instance;

    public static ProgressDialogUtil getInstance() {
        if (instance == null) {
            instance = new ProgressDialogUtil();
        }
        return instance;
    }

    /**
     * 显示ProgressDialog
     *
     * @param context
     * @param needCancle 当前请求是否可以取消
     */
    public void showProgressDialog(Context context, boolean needCancle) {
        closeProgressDialog();
        try {
            dialog = new SimpleProgressDialog(context, needCancle);
            dialog.setCanceledOnTouchOutside(needCancle);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPercent(String percent) {
        if (dialog != null) {
            dialog.setPercent(percent);
        }
    }

    /**
     * 显示带msg的ProgressDialog
     *
     * @param msg
     * @param context
     * @param needCancle
     */
    public void showProgressDialogMsg(String msg, Context context, boolean needCancle) {
        closeProgressDialog();
        dialog = new SimpleProgressDialog(msg, context, needCancle);
        dialog.setCanceledOnTouchOutside(needCancle);
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭ProgressDialog
     */
    public void closeProgressDialog() {
        try {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public SimpleProgressDialog getDialog()
    {
        return dialog;
    }
}
