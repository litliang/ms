package com.yzb.card.king.util;

/**
 * Created by gqy on 2015/4/23.
 */

import android.text.TextUtils;
import android.util.Log;

/**
 * 打印log的工具类
 */
public class LogUtil
{
    public static final String LOG_TAG = "LogUtil";

    public static final int NONE = 0;
    public static final int ERROR_ONLY = 1;
    public static final int ERROR_WARN = 2;
    public static final int ERROR_WARN_INFO = 3;
    public static final int ERROR_WARN_INFO_DEBUG = 4;


    public static void LCi(String message)
    {
        if (LOGGING_LEVEL >= ERROR_ONLY)
            Log.i("LCZ", message);
    }

    /**
     * 日志输出开关
     */
    private static final int LOGGING_LEVEL = ERROR_WARN_INFO_DEBUG;


    // Errors + warnings + info + debug (default)
    public static void e(String msg)
    {
        if (LOGGING_LEVEL >= ERROR_ONLY && !TextUtils.isEmpty(msg))
            Log.e(LOG_TAG, msg);
    }

    // Errors + warnings + info + debug (default)
    public static void e(String tag, String msg)
    {
        if (LOGGING_LEVEL >= ERROR_ONLY && !TextUtils.isEmpty(msg))
            Log.e(tag, msg);
    }


    public static void e(String msg, Throwable e)
    {
        if (LOGGING_LEVEL >= ERROR_ONLY && !TextUtils.isEmpty(msg))
            Log.e(LOG_TAG, msg, e);
    }

    public static void w(String msg)
    {
        if (LOGGING_LEVEL >= ERROR_WARN && !TextUtils.isEmpty(msg))
            Log.w(LOG_TAG, msg);
    }

    public static void w(String msg, Throwable e)
    {
        if (LOGGING_LEVEL >= ERROR_WARN && !TextUtils.isEmpty(msg))
            Log.w(LOG_TAG, msg, e);
    }

    public static void i(String msg)
    {
        if (LOGGING_LEVEL >= ERROR_WARN_INFO && !TextUtils.isEmpty(msg))
            Log.i(LOG_TAG, msg);
    }

    public static void i(String msg, Throwable e)
    {
        if (LOGGING_LEVEL >= ERROR_WARN_INFO)
            Log.i(LOG_TAG, msg, e);
    }

    public static void d(String msg)
    {
        if (LOGGING_LEVEL >= ERROR_WARN_INFO_DEBUG)
            Log.d(LOG_TAG, msg);
    }

    public static void d(String msg, Throwable e)
    {
        if (LOGGING_LEVEL >= ERROR_WARN_INFO_DEBUG)
            Log.d(LOG_TAG, msg, e);
    }
}

