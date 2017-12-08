package com.yzb.card.king.ui.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.service.MsgIntentService;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.activity.IDAuthenticationActivity;
import com.yzb.card.king.ui.appwidget.appdialog.SimpleProgressDialog;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.pop.RealNameCertificationDialog;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.encryption.RsaUtil;

import org.xutils.x;

/**
 * 类  名：所有模块和实现类的基类
 * 作  者：Li Yubing
 * 日  期：2016/6/16
 * 描  述：
 */
public class BaseActivity extends AppCompatActivity
{
    protected Location positionedCity, selectedCity;

    protected double positionLatitude,positionLongitude;

    protected boolean defaultFlag = true;

    protected String cityId;

    public  String cityName;

    protected  int colorStatusResId = R.color.stateWhite;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //配置xUtil jar包
        x.view().inject(this);
        GlobalApp.getInstance().setPublicActivity(this);

        setTranslucentStatus(isApplyStatusBarTranslucency());

       boolean mIsm = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (mIsm && GlobalApp.ifOpenPermission)
        {
            //6.0授权限
            String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS, Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(BaseActivity.this, mPermissionList, 100);

            GlobalApp.ifOpenPermission= false;
        }
        // 手机app和手机信息
        if (GlobalApp.getInstance().getAppBaseDataBean() == null)
        {

            AppBaseDataBean appBaseDataBean = new AppBaseDataBean();

            appBaseDataBean.init(this);

            GlobalApp.getInstance().setAppBaseDataBean(appBaseDataBean);

            RsaUtil.init();

        }
        reSetCityInfo();
    }

    /**
     * 重新设置城市信息
     */
    public void reSetCityInfo(){

        positionedCity = GlobalApp.getPositionedCity();

        selectedCity = GlobalApp.getSelectedCity();

        if(selectedCity != null){

            positionLatitude = selectedCity.getLatitude();

            positionLongitude = selectedCity.getLongitude();
        }



        if(selectedCity != null){

            cityId = isEmpty(selectedCity.cityId) ? positionedCity.cityId : selectedCity.cityId;

            cityName = selectedCity.getCityName();
        }

    }



    public void setRightImage(int resId, View.OnClickListener listener)
    {
        View headerRightImage = findViewById(R.id.headerRightImage);
        headerRightImage.setVisibility(View.VISIBLE);
        headerRightImage.setBackgroundResource(resId);
        headerRightImage.setOnClickListener(listener);
    }

    public void setTitleNmae(int titleName){

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);

        tvTitle.setText(titleName);

    }

    public void setTitleNmae(int titleName,int rightFunction){

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);

        tvTitle.setText(titleName);

        TextView ivRight = (TextView) findViewById(R.id.ivRight);
        ivRight.setText(rightFunction);
    }

    public void setTitleNmae(String titleName){

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);

        tvTitle.setText(titleName);

    }

    /**
     * header设置，左侧图片，中间文字，右侧图片
     */
    protected void setHeader(int leftResource, String leftTitle, String midTitle, int rightResource, int rightResource2, String rightTitle)
    {
        this.setHeaderContent(leftResource, leftTitle, midTitle, rightResource, rightResource2, rightTitle);
    }

    /**
     * header设置，左侧图片，中间文字，右侧图片
     */
    protected void setHeader(int leftResource, String leftTitle, int leftResource2, String midTitle, int midResource, int rightResource, int rightResource2, String rightTitle)
    {
        this.setHeaderContent(leftResource, leftTitle, leftResource2, midTitle, midResource, rightResource, rightResource2, rightTitle);
    }

    /**
     * header设置内容
     */
    private void setHeaderContent(int leftResource, String leftTitle, int leftResource2,
                                  String midTitle, int midResource, int rightResource, int rightResource2, String rightTitle)
    {

        // 左侧图片设置(默认不显示)
        ImageView headerLeftImage = (ImageView) findViewById(R.id.headerLeftImage);
        if (leftResource != 0)
        {
            headerLeftImage.setImageResource(leftResource);
            headerLeftImage.setVisibility(View.VISIBLE);
        }

        // 左侧图片设置(默认不显示)
        ImageView headerLeftImage2 = (ImageView) findViewById(R.id.headerLeftImage2);
        if (leftResource != 0)
        {
            headerLeftImage2.setImageResource(leftResource2);
            headerLeftImage2.setVisibility(View.VISIBLE);
        }

        // 左侧内容设置
        TextView headerLeftText = (TextView) findViewById(R.id.headerLeftText);
        if (!TextUtils.isEmpty(leftTitle))
        {// 文字
            headerLeftText.setText(leftTitle);
        } else
        {
            headerLeftText.setVisibility(View.GONE);
        }

        // 中间内容设置
        TextView headerTitle = (TextView) findViewById(R.id.headerTitle);
        if (!TextUtils.isEmpty(midTitle))
        {
            headerTitle.setText(midTitle);
        } else
        {
            headerTitle.setVisibility(View.INVISIBLE);
        }

        // 中间搜索设置(默认不显示)
        ImageView headerMiddleImageView = (ImageView) findViewById(R.id.headerMiddleImageView);
        LinearLayout headerMiddleLayout = (LinearLayout) findViewById(R.id.headerMiddleLayout);
        if (midResource != 0)
        {
            headerMiddleLayout.setVisibility(View.VISIBLE);
            headerTitle.setVisibility(View.GONE);
        }

        // 右侧图片设置(默认不显示)
        ImageView headerRightImage = (ImageView) findViewById(R.id.headerRightImage);
        if (rightResource != 0)
        {
            headerRightImage.setImageResource(rightResource);
            headerRightImage.setVisibility(View.VISIBLE);
        }

        // 右侧图片设置(默认不显示)
        ImageView headerRightImage2 = (ImageView) findViewById(R.id.headerRightImage2);
        if (rightResource2 != 0)
        {
            headerRightImage2.setImageResource(rightResource2);
            headerRightImage2.setVisibility(View.VISIBLE);
        }

        //右侧内容设置
        TextView headerRightText = (TextView) findViewById(R.id.headerRightText);
        if (!TextUtils.isEmpty(rightTitle))
        {
            headerRightText.setText(rightTitle);
        } else
        {
            headerRightText.setVisibility(View.GONE);
        }

    }

    /**
     * 获取用户信息；
     */
    protected UserBean getUserBean()
    {
        return UserManager.getInstance().getUserBean();
    }

    /**
     * 关闭进度对话框；
     */
    public void closePDialog()
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }

    protected boolean checkLogin()
    {
        if (!isLogin())
        {
            readyGo(this, LoginActivity.class);
            return false;
        }
        return true;
    }

    public void setDefaultHeader(String title)
    {
        ImageView headerLeftImage = (ImageView) findViewById(R.id.headerLeftImage);
        headerLeftImage.setImageResource(R.mipmap.icon_back_white);
        headerLeftImage.setVisibility(View.VISIBLE);
        TextView headerTitle = (TextView) findViewById(R.id.headerTitle);
        headerTitle.setText(title);
        findViewById(R.id.headerLeft).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    /**
     * 进度条是否显示；
     *
     * @return
     */
    protected boolean isDialogShowing()
    {
        SimpleProgressDialog dialog = ProgressDialogUtil.getInstance().getDialog();
        if (dialog != null)
        {
            return dialog.isShowing();
        }
        return false;
    }

    /**
     * 标题左侧返回
     */
    public void switchContentLeft(final int resultCode)
    {

        LinearLayout headerLeft = (LinearLayout) findViewById(R.id.headerLeft);

        // 返回首页
        headerLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                setResult(resultCode);
                finish();
            }
        });
    }

    /**
     * 显示进度对话框；
     *
     * @param msg 消息提示；
     */
    public void showPDialog(String msg)
    {
        ProgressDialogUtil.getInstance().showProgressDialogMsg(msg, this, true);
    }

    /**
     * 显示不取消的进度对话框；
     *
     * @param msgResId 消息提示；
     */
    public void showNoCancelPDialog(int msgResId)
    {
        ProgressDialogUtil.getInstance().showProgressDialogMsg(getString(msgResId), this, false);
    }

    /**
     * 显示进度对话框；
     *
     * @param msgResId 消息提示；
     */
    public void showPDialog(int msgResId)
    {
        ProgressDialogUtil.getInstance().showProgressDialogMsg(getString(msgResId), this, true);
    }

    /**
     * 关闭对话框
     */
    public  void dimissPdialog(){
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        //礼品卡和红包消息监听；
        MsgIntentService.listenGiftAndBouns(getApplicationContext());

        positionedCity = GlobalApp.getPositionedCity();
        selectedCity = GlobalApp.getSelectedCity();
        if(GlobalApp.getSelectedCity() != null)
        cityId = isEmpty(selectedCity.cityId) ? positionedCity.cityId : selectedCity.cityId;
    }

    /**
     * 是否应用状态栏一体化；
     *
     * @return
     */
    protected boolean isApplyStatusBarTranslucency()
    {
        return defaultFlag;
    }

    protected void toastCustom(String text)
    {
        ToastUtil.i(this,text);
    }

    protected void toastCustom(int resId)
    {
        ToastUtil.i(this,getString(resId));
    }

    /**
     * 设置系统状态栏的drawable；
     *
     * @param tintDrawable
     */
    private void setSystemBarTintDrawable(Drawable tintDrawable)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null)
            {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else
            {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }

    }

    protected void readyGoThenFinish(Activity context, Class claz)
    {
        Intent intent = new Intent(context, claz);
        context.startActivity(intent);
        context.finish();
    }

    protected void readyGo(Activity context, Class claz)
    {
        Intent intent = new Intent(context, claz);
        context.startActivity(intent);
    }

    protected void readyGoForResult(Activity context, Class claz, int reqCode)
    {
        Intent intent = new Intent(context, claz);
        startActivityForResult(intent, reqCode);
    }

    protected void readyGoWithBundle(Activity context, Class claz, Bundle bundle)
    {
        Intent intent = new Intent(context, claz);
        if (bundle != null)
        {
            intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        }
        context.startActivity(intent);
    }

    protected void readyGo(Intent intent)
    {
        this.startActivity(intent);
    }

    /**
     * 设置沉浸式状态栏；
     * 适用于=API 4.4；
     *
     * @param on
     */
    private void setTranslucentStatus(boolean on)
    {
        //>=API 4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on)
            {
                winParams.flags |= bits;
            } else
            {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);

            setSystemBarTintDrawable(new ColorDrawable(getResources().getColor(getColorResId())));
        }
    }

    /**
     * 获取状态栏色值；
     *
     * @return
     */
    protected int getColorResId()
    {
        return colorStatusResId;
    }

    /**
     * 返回按钮
     *
     * @param v
     */
    public void backAction(View v)
    {

        finish();
    }

    public void setHeader(String title)
    {
        ImageView headerLeftImage = (ImageView) findViewById(R.id.headerLeftImage);
        headerLeftImage.setImageResource(R.mipmap.icon_back_white);
        headerLeftImage.setVisibility(View.VISIBLE);
        TextView headerTitle = (TextView) findViewById(R.id.headerTitle);
        headerTitle.setText(title);
        findViewById(R.id.headerLeft).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    public void setTitleRight(int icon, View.OnClickListener listener)
    {
        ImageView headerRightImage = (ImageView) findViewById(R.id.headerRightImage);
        headerRightImage.setImageResource(icon);
        headerRightImage.setOnClickListener(listener);
        headerRightImage.setVisibility(View.VISIBLE);
    }

    //系统自动销毁Activity前保存必要的数据
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        //存储当前缓存数据

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        //加载当前缓存数据
    }

    /**
     * 用户是否登录
     *
     * @return
     */
    public boolean isLogin()
    {

        return UserManager.getInstance().isLogin();
    }

    /**
     * 检测是否为空
     * @param input
     * @return
     */
    protected boolean isEmpty(String input)
    {
        if (input == null || "".equals(input))
            return true;
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n')
            {
                return false;
            }
        }
        return false;
    }

    //加载数据时的Handler；
    protected Handler loadDataHandler = new Handler();

    /**
     * header设置，左侧，中间文字，无右侧
     */
    protected void setHeader(String leftTitle, String midTitle)
    {
        this.setHeaderContent(0, leftTitle, midTitle, 0, null);
    }

    /**
     * header设置，左侧图片，中间文字，无右侧
     */
    protected void setHeader(int leftResource, String midTitle)
    {
        this.setHeaderContent(leftResource, null, midTitle, 0, null);
    }

    /**
     * header设置，左侧图片，中间文字，右侧图片
     */
    protected void setHeader(int leftResource, String midTitle, int rightResource)
    {
        this.setHeaderContent(leftResource, null, midTitle, rightResource, null);
    }

    /**
     * header设置，左侧图片，中间及右侧文字
     */
    protected void setHeader(int leftResource, String midTitle, String rightTitle)
    {
        this.setHeaderContent(leftResource, null, midTitle, 0, rightTitle);
    }

    /**
     * header设置内容
     */
    private void setHeaderContent(int leftResource, String leftTitle,
                                  String midTitle, int rightResource, int rightResource2, String rightTitle)
    {

        // 左侧图片设置(默认不显示)
        ImageView headerLeftImage = (ImageView) findViewById(R.id.headerLeftImage);
        if (leftResource != 0)
        {
            headerLeftImage.setImageResource(leftResource);
            headerLeftImage.setVisibility(View.VISIBLE);
        }

        // 左侧内容设置
        TextView headerLeftText = (TextView) findViewById(R.id.headerLeftText);
        if (!TextUtils.isEmpty(leftTitle))
        {// 文字
            headerLeftText.setText(leftTitle);
        } else
        {
            headerLeftText.setVisibility(View.GONE);
        }

        // 中间内容设置
        TextView headerTitle = (TextView) findViewById(R.id.headerTitle);
        if (!TextUtils.isEmpty(midTitle))
        {
            headerTitle.setText(midTitle);
        } else
        {
            headerTitle.setVisibility(View.INVISIBLE);
        }

        // 右侧图片设置(默认不显示)
        ImageView headerRightImage = (ImageView) findViewById(R.id.headerRightImage);
        if (rightResource != 0)
        {
            headerRightImage.setImageResource(rightResource);
            headerRightImage.setVisibility(View.VISIBLE);
        }

        // 右侧图片设置(默认不显示)
        ImageView headerRightImage2 = (ImageView) findViewById(R.id.headerRightImage2);
        if (rightResource2 != 0)
        {
            headerRightImage2.setImageResource(rightResource2);
            headerRightImage2.setVisibility(View.VISIBLE);
        }

        //右侧内容设置
        TextView headerRightText = (TextView) findViewById(R.id.headerRightText);
        if (!TextUtils.isEmpty(rightTitle))
        {
            headerRightText.setText(rightTitle);
        } else
        {
            headerRightText.setVisibility(View.GONE);
        }

    }

    /**
     * header设置内容
     */
    private void setHeaderContent(int leftResource, String leftTitle,
                                  String midTitle, int rightResource, String rightTitle)
    {

        // 左侧图片设置(默认不显示)
        ImageView headerLeftImage = (ImageView) findViewById(R.id.headerLeftImage);
        if (leftResource != 0)
        {
            headerLeftImage.setImageResource(leftResource);
            headerLeftImage.setVisibility(View.VISIBLE);
        }

        // 左侧内容设置
        TextView headerLeftText = (TextView) findViewById(R.id.headerLeftText);
        if (!TextUtils.isEmpty(leftTitle))
        {// 文字
            headerLeftText.setText(leftTitle);
        } else
        {
            headerLeftText.setVisibility(View.GONE);
        }

        // 中间内容设置
        TextView headerTitle = (TextView) findViewById(R.id.headerTitle);
        if (!TextUtils.isEmpty(midTitle))
        {
            headerTitle.setText(midTitle);
        } else
        {
            headerTitle.setVisibility(View.INVISIBLE);
        }

        // 右侧图片设置(默认不显示)
        ImageView headerRightImage = (ImageView) findViewById(R.id.headerRightImage);
        if (rightResource != 0)
        {
            headerRightImage.setImageResource(rightResource);
            headerRightImage.setVisibility(View.VISIBLE);
        } else
        {
            headerRightImage.setVisibility(View.INVISIBLE);
        }

        //右侧内容设置
        TextView headerRightText = (TextView) findViewById(R.id.headerRightText);
        if (!TextUtils.isEmpty(rightTitle))
        {
            headerRightText.setText(rightTitle);
        } else
        {
            headerRightText.setVisibility(View.GONE);
        }

    }

    /**
     * 验证用户是否身份认证
     *
     * @param valid
     */
    public void checkUserValid(OnIDValid valid)
    {

        if (valid == null) return;
        if ("1".equals(getValidStatus()))
        {
            valid.onValid();
        } else if ("3".equals(getValidStatus()))
        {
            UiUtils.longToast("身份信息正在审核中，审核通过才能访问");
        } else
        {
            RealNameCertificationDialog.getInstance().setDataHandler(validHandler).show(getSupportFragmentManager(), "");
        }
    }

    public interface OnIDValid
    {
        void onValid();
    }


    public String getValidStatus()
    {
        if (isLogin())
        {
            return getUserBean().getAuthenticationStatus();
        }
        return "";
    }

    private Handler validHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            if (RealNameCertificationDialog.WHAT_LOOK == msg.what)
            {
                //进入实名认证流程
                Intent it = new Intent(GlobalApp.getInstance().getContext(), IDAuthenticationActivity.class);
                startActivity(it);

            }
        }
    };
}
