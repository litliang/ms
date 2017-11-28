package com.yzb.card.king.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 类 名：状态栏设置
 * 作 者： gaolei
 * 日 期：2016年12月8日
 * 描 述：将系统字体变暗
 * 设置状态栏黑色字体图标
 */

public class SetStutasUtils {

    //修改状态栏字体为深色,限制版本为6.0或以上
    public static void StatusBarLightMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    //修改状态栏为颜色,限制版本为4.4或以上,可以根据自己的业务修改颜色
    public static void StatusBarColor(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        }

    }
}
