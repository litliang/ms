package com.yzb.card.king.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 类名：UiUtils
 * 作者：殷曙光
 * 日期：2016/6/15 14:31
 * 描述：
 */
@SuppressWarnings({ "unchecked" })
public class UiUtils
{
    /**
     * 获取Context
     *
     * @author ysg
     * created at 2016/6/15 14:30
     */
    public static Context getContext()
    {
        return GlobalApp.getInstance().getContext();
    }

    /**
     * @author ysg
     * created at 2016/6/15 15:42
     */
    public static void shortToast(String msg)
    {

        ToastUtil.i(getContext(),msg);
    }

    public static void shortToast(int resId)
    {

        ToastUtil.i(getContext(), getString(resId));
    }

    /**
     * @author ysg
     * created at 2016/6/15 15:43
     */
    public static void longToast(String msg)
    {

        ToastUtil.i(getContext(),msg);
    }

    /**
     * @author ysg
     * created at 2016/6/15 16:43
     */
    public static View inflate(int id)
    {
        return View.inflate(getContext(), id, null);
    }

    public static int dp2px(int dp)
    {
        return CommonUtil.dip2px(getContext(), dp);
    }

    /**
     * @param context
     * @param spValue
     * @return
     * @author Timmy
     */
    public static int sp2px(Context context, float spValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * @author ysg
     * created at 2016/7/12 10:42
     */
    public static void startActivity(Class clazz)
    {
        Intent intent = new Intent(getContext(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    /**
     * @author ysg
     * created at 2016/7/12 10:42
     */
    public static void startActivity(Intent intent)
    {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    /**
     * @author ysg
     * created at 2016/7/12 10:42
     */
    public static void startActivity(Class clazz, Bundle bundle)
    {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    /**
     * @author ysg
     * created at 2016/7/12 10:42
     */
    public static void readyGoWithBundle(Class clazz, Bundle bundle)
    {
        Intent intent = new Intent(getContext(), clazz);
        if (bundle != null)
        {
            intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        }
        startActivity(intent);
    }

    public static int getColor(int color)
    {

        return getContext().getResources().getColor(color);
    }

    public static String[] getStringArray(int id)
    {
        return getContext().getResources().getStringArray(id);
    }

    public static String getString(int resId, Object... args)
    {
        return getContext().getResources().getString(resId, args);
    }

    public static ColorStateList getColorStateList(int resId)
    {
        return getContext().getResources().getColorStateList(resId);
    }


    public static Drawable getDrawable(int resId)
    {
        return getContext().getResources().getDrawable(resId);
    }

    /**
     * 替代findviewById方法
     */
    public static <T extends View> T find(View view, int id)
    {
        return (T) view.findViewById(id);
    }
}
