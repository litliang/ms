package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.UiUtils;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/8 18:25
 * 描述：
 */
public class GoLoginDailog extends AlertDialog.Builder {

    private Context context;
    public GoLoginDailog(Context context) {
        super(context, R.style.lightDialog);
        this.context = context;
        init();
    }

    private void init() {
        setTitle("提示");
        setMessage("您还没登录，是否现在登录？");
        setPositiveButton("立即登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             //   UiUtils.startActivity(LoginActivity.class);
                Intent it = new Intent(context,LoginActivity.class);

                ((Activity)context).startActivityForResult(it,0);

            }
        });
        setNegativeButton("不登录",null);
    }


}
