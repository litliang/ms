/**
 * <pre>
 * 文件名：	AppBaseDataBean.java
 * 作　者：	Administrator
 * 时　间：	2015年11月30日下午1:59:01
 * 公    司：
 * 描　述：
 *
 * </pre>
 */
package com.yzb.card.king.bean;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;


import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.encryption.RsaUtil;

import org.xutils.common.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 * 文件名：	AppBaseDataBean.java
 * 作　者：	Li Yubing
 * 时　间：
 * 描　述：收集app和手机的一些基本信息
 *
 * </pre>
 */
public class AppBaseDataBean {


    /**
     * 版本号
     */
    private int versionCode = 1;

    /**
     * 版本名字
     */
    private String versionName = "1.0.0";

    /**
     * 导航栏目高度
     */
    private int navigationBarHeight;

    /**
     * 状态栏目高度
     */
    private int statusBarHeight;

    /**
     * actionBar高度
     */
    private int actionBarHeight;
    /**
     * 屏幕宽度
     */
    private int screenWith;
    /**
     * 屏幕高度
     */
    private int screenHeight;
    /**
     * 屏幕密度
     */
    private float screenDensity;
    /**
     * 型號
     *
     */
    private String BOARD;

    /**
     * 品牌
     */
    private String BRAND;

    /**
     * App文件的缓存文件夹路径
     */
    private String APP_CACHE_PATH;

    public String getAPP_CACHE_PATH()
    {
        return APP_CACHE_PATH;
    }

    public void setAPP_CACHE_PATH(String APP_CACHE_PATH)
    {
        this.APP_CACHE_PATH = APP_CACHE_PATH;
    }

    /**
     * @return the versionCode
     */
    public int getVersionCode()
    {
        return versionCode;
    }

    /**
     * @param versionCode
     *            the versionCode to set
     */
    public void setVersionCode(int versionCode)
    {
        this.versionCode = versionCode;
    }

    /**
     * @return the versionName
     */
    public String getVersionName()
    {
        return versionName;
    }

    /**
     * @param versionName
     *            the versionName to set
     */
    public void setVersionName(String versionName)
    {
        this.versionName = versionName;
    }

    /**
     * @return the navigationBarHeight
     */
    public int getNavigationBarHeight()
    {
        return navigationBarHeight;
    }

    /**
     * @param navigationBarHeight
     *            the navigationBarHeight to set
     */
    public void setNavigationBarHeight(int navigationBarHeight)
    {
        this.navigationBarHeight = navigationBarHeight;
    }

    /**
     * @return the statusBarHeight
     */
    public int getStatusBarHeight()
    {
        return statusBarHeight;
    }

    /**
     * @param statusBarHeight
     *            the statusBarHeight to set
     */
    public void setStatusBarHeight(int statusBarHeight)
    {
        this.statusBarHeight = statusBarHeight;
    }

    /**
     * @return the actionBarHeight
     */
    public int getActionBarHeight()
    {
        return actionBarHeight;
    }

    /**
     * @param actionBarHeight
     *            the actionBarHeight to set
     */
    public void setActionBarHeight(int actionBarHeight)
    {
        this.actionBarHeight = actionBarHeight;
    }

    /**
     * @return the screenWith
     */
    public int getScreenWith()
    {
        return screenWith;
    }

    /**
     * @param screenWith
     *            the screenWith to set
     */
    public void setScreenWith(int screenWith)
    {
        this.screenWith = screenWith;
    }

    /**
     * @return the screenHeight
     */
    public int getScreenHeight()
    {
        return screenHeight;
    }

    /**
     * @param screenHeight
     *            the screenHeight to set
     */
    public void setScreenHeight(int screenHeight)
    {
        this.screenHeight = screenHeight;
    }

    /**
     * @return the screenDensity
     */
    public float getScreenDensity()
    {
        return screenDensity;
    }

    /**
     * @param screenDensity
     *            the screenDensity to set
     */
    public void setScreenDensity(float screenDensity)
    {
        this.screenDensity = screenDensity;
    }

    /**
     * @return the bOARD
     */
    public String getBOARD()
    {
        return BOARD;
    }

    /**
     * @param bOARD
     *            the bOARD to set
     */
    public void setBOARD(String bOARD)
    {
        BOARD = bOARD;
    }

    /**
     * @return the bRAND
     */
    public String getBRAND()
    {
        return BRAND;
    }

    /**
     * @param bRAND
     *            the bRAND to set
     */
    public void setBRAND(String bRAND)
    {
        BRAND = bRAND;
    }

    public void init(Activity context)
    {
        // 屏幕信息
        DisplayMetrics dm = new DisplayMetrics();

        dm = context.getResources().getDisplayMetrics();

        screenWith = dm.widthPixels;

        screenHeight = dm.heightPixels;

        screenDensity = dm.density;

        SystemBarTintManager tintManager = new SystemBarTintManager(context);

        SystemBarTintManager.SystemBarConfig sBc = tintManager.getConfig();

        navigationBarHeight = sBc.getNavigationBarHeight();// 导航栏目高度

        statusBarHeight = sBc.getStatusBarHeight();// 状态栏目高度

        actionBarHeight = sBc.getActionBarHeight();// actionBar高度

        collectDeviceInfo(context);

        File file = FileUtil.getCacheDir("");

        APP_CACHE_PATH = file.getAbsolutePath() + "/";

        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imeiCode = manager.getDeviceId();

        AppConstant.UUID = imeiCode;

        //手机序列号
        if(TextUtils.isEmpty(AppConstant.UUID)){
            String serial = null;
            try {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serial = (String) get.invoke(c, "ro.serialno");
                AppConstant.UUID = serial;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    private void  collectDeviceInfo( Context ctx)
    {

        synchronized (ctx){

        try {

            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), 0);

            if (pi != null) {

                versionName = pi.versionName == null ? "null" : pi.versionName;

                versionCode = pi.versionCode;


            }

        } catch (Exception e) {

            e.printStackTrace();


        }

        Field[] fields = Build.class.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);


                String fieldName = field.getName();

                String fieldNameValue = field.get(null).toString();

                if (fieldName.equals("BOARD")) {// 型号

                    BOARD = fieldNameValue;

                } else if (fieldName.equals("BRAND")) {// 品牌

                    BRAND = fieldNameValue;
                }
            //    LogUtil.e("BRAND="+BRAND);
            } catch (Exception e) {

            }
        }
        }
    }
}
