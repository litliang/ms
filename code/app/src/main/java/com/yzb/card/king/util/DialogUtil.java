package com.yzb.card.king.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.yzb.card.king.R;

/**
 * 类  名：Dilog工具
 * 作  者：Li Yubing
 * 日  期：2016/7/11
 * 描  述：
 */
public class DialogUtil
{
    private static DialogUtil instance;
    private AlertDialog dialog;

    private DialogUtil()
    {
    }

    public static DialogUtil getInstance()
    {
        if (instance == null)
        {
            instance = new DialogUtil();
        }
        return instance;
    }

    /**
     * 展示单个按钮的Dialog
     *
     * @param act
     * @param titleStr
     * @param msgStr
     */
    public void showSingleButtonDialog(Activity act, String titleStr, String msgStr, String buttonName)
    {
        if (dialog == null)
        {
        } else
        {
            if (dialog.isShowing())
            {
                dialog.dismiss();
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage(msgStr);
        builder.setTitle(titleStr);
        builder.setPositiveButton(buttonName, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    /**
     * 展示2个按钮的Dialog
     */
    public void showDialog(Context context, String title, String msg, DialogCallBack callBack)
    {
        if (dialog == null)
        {
            createDialog(context, title, msg, callBack);
        }
        closeDialog();
        dialog.show();
    }

    public void closeDialog()
    {
        if (dialog != null && dialog.isShowing())
        {

            dialog.dismiss();
        }
    }


    /**
     * 创建dialog；
     *
     * @param context
     * @param title
     * @param msg
     */
    private void createDialog(Context context, String title, String msg, final DialogCallBack callBack)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).
                setMessage(msg).
                setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (callBack != null)
                        {
                            callBack.yes();
                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (callBack != null)
                {
                    callBack.no();
                }
            }
        });
        dialog = builder.create();
    }

    public static  abstract class DialogCallBack
    {
        /**
         * 确定；
         */
        public void yes()
        {
        }

        /**
         * 取消；
         */
        public void no()
        {
        }
    }
}
