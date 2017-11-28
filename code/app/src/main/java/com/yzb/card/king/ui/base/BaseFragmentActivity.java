package com.yzb.card.king.ui.base;

import android.Manifest;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.manage.UserManager;

import org.xutils.x;

/**
 * 类  名：所有模块和实现类的基类
 * 作  者：Li Yubing
 * 日  期：2016/6/16
 * 描  述：
 */
public class BaseFragmentActivity extends FragmentActivity{

    private boolean defaultFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //配置xUtil jar包
        x.view().inject(this);

        setTranslucentStatus(isApplyStatusBarTranslucency());
        //6.0授权限
        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS,Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(BaseFragmentActivity.this,mPermissionList, 100);
    }
    /**
     * 是否应用状态栏一体化；
     *
     * @return
     */
    protected boolean isApplyStatusBarTranslucency() {
        return defaultFlag;
    }

    /**
     * 设置系统状态栏的drawable；
     *
     * @param tintDrawable
     */
    private void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }

    }


    /**
     * 设置沉浸式状态栏；
     * 适用于=API 4.4；
     *
     * @param on
     */
    private void setTranslucentStatus(boolean on) {
        //>=API 4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);

            setSystemBarTintDrawable(new ColorDrawable(getResources().getColor(R.color.stateWhite)));
        }
    }


    /**
     * 返回按钮
     * @param v
     */
    public void backAction(View v){

        finish();
    }

    /**
     * 用户是否登录
     * @return
     */
    private boolean isLogin(){

        return UserManager.getInstance().isLogin();
    }
}
