package com.yzb.card.king.util;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;

/**
 * Created by gengqiyun on 2016/4/12.
 */
public class ToastUtil
{

    public static void i(Context context, String msg) {

        Toast toast =   Toast.makeText(context, msg, Toast.LENGTH_LONG);

        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.show();
    }

    public static void i(Context context, int stringResId) {

        Toast toast =   Toast.makeText(context, stringResId, Toast.LENGTH_LONG);

        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.show();


    }


}
