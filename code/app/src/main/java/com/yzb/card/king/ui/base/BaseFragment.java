package com.yzb.card.king.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.activity.IDAuthenticationActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.pop.RealNameCertificationDialog;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 类  名：所有模块和实现类的基类
 * 作  者：Li Yubing
 * 日  期：2016/6/16
 * 描  述：
 */
public class BaseFragment extends Fragment
{
    protected String cityId;
    protected String cityName;
    protected GlobalApp app;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if( GlobalApp.getSelectedCity() != null){
            cityId = GlobalApp.getSelectedCity().cityId;
            cityName = GlobalApp.getSelectedCity().cityName;
        }

        app = (GlobalApp) context.getApplicationContext();
    }

    protected void toastCustom(String text)
    {
        if (getActivity() != null)
        {
            ToastUtil.i(getActivity(), text);
        }
    }

    protected void toastCustom(int resId)
    {
        if (getActivity() != null)
        {
            ToastUtil.i(getActivity(), resId);
        }
    }

    protected String getCityId(Context context)
    {
        return GlobalApp.getSelectedCity().cityId;
    }


    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if (!injected)
        {
            x.view().inject(this, this.getView());
        }
    }

    public void log(String msg)
    {
        LogUtil.i(msg);
    }

    /**
     * 设置沉浸式状态栏；
     *
     * @param on
     */
    public void setTranslucentStatus(boolean on, int color)
    {
        //>=API 4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window win = getActivity().getWindow();
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
        return R.color.stateWhite;
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
            SystemBarTintManager mTintManager = new SystemBarTintManager(getActivity());
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

    /**
     * header设置，中间为文字
     */
    protected void setHeader(View view, String midTitle)
    {
        this.setHeaderContent(view, 0, null, midTitle, 0, null);
    }

    /**
     * header设置，左侧，中间文字，无右侧
     */
    protected void setHeader(View view, String leftTitle, String midTitle)
    {
        this.setHeaderContent(view, 0, leftTitle, midTitle, 0, null);
    }

    /**
     * header设置，左侧图片，中间文字，无右侧
     */
    protected void setHeader(View view, int leftResourse, String midTitle)
    {
        this.setHeaderContent(view, leftResourse, null, midTitle, 0, null);
    }

    /**
     * header设置，左侧，中间及右侧文字
     */
    protected void setHeader(View view, String leftTitle, String midTitle, String rightTitle)
    {
        this.setHeaderContent(view, 0, leftTitle, midTitle, 0, rightTitle);
    }

    /**
     * header设置，左侧图片，中间及右侧文字
     */
    protected void setHeader(View view, int leftResourse, String midTitle, String rightTitle)
    {
        this.setHeaderContent(view, leftResourse, null, midTitle, 0, rightTitle);
    }

    /**
     * header设置，左侧图片，中间文字，右侧图片
     */
    protected void setHeader(View view, int leftResourse, String midTitle, int rightResourse)
    {
        this.setHeaderContent(view, leftResourse, null, midTitle, rightResourse, null);
    }

    /**
     * header设置内容
     */
    private void setHeaderContent(View view, int leftResourse, String leftTitle,
                                  String midTitle, int rightResourse, String rightTitle)
    {

        // 左侧图片设置(默认不显示)
        ImageView headerLeftImage = (ImageView) view.findViewById(R.id.headerLeftImage);
        if (leftResourse != 0)
        {
            headerLeftImage.setImageResource(leftResourse);
            headerLeftImage.setVisibility(View.VISIBLE);
        }

        // 左侧内容设置
        TextView headerLeftText = (TextView) view.findViewById(R.id.headerLeftText);
        if (!TextUtils.isEmpty(leftTitle))
        {// 文字
            headerLeftText.setText(leftTitle);
            headerLeftText.setTextColor(getResources().getColor(R.color.wordWhite));
        } else
        {
            headerLeftText.setVisibility(View.INVISIBLE);
        }

        // 中间内容设置
        TextView headerTitle = (TextView) view.findViewById(R.id.headerTitle);
        if (!TextUtils.isEmpty(midTitle))
        {
            headerTitle.setText(midTitle);
            headerTitle.setTextColor(getResources().getColor(R.color.wordWhite));
        } else
        {
            headerTitle.setVisibility(View.INVISIBLE);
        }

        // 右侧图片设置(默认不显示)
        ImageView headerRightImage = (ImageView) view.findViewById(R.id.headerRightImage);
        if (rightResourse != 0)
        {
            headerRightImage.setImageResource(rightResourse);
            headerRightImage.setVisibility(View.VISIBLE);
        }

        //右侧内容设置
        TextView headerRightText = (TextView) view.findViewById(R.id.headerRightText);
        if (!TextUtils.isEmpty(rightTitle))
        {
            headerRightText.setText(rightTitle);
            headerRightText.setTextColor(getResources().getColor(R.color.wordWhite));
        } else
        {
            headerRightText.setVisibility(View.INVISIBLE);
        }

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
            intent.putExtra("intent_bundle", bundle);
        }
        context.startActivity(intent);
    }


    //系统自动销毁Fragment前保存必要的数据
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
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

    public String getValidStatus()
    {
        return UserManager.getInstance().getUserBean().getAuthenticationStatus();
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
            RealNameCertificationDialog.getInstance().setDataHandler(validHandler).show(getFragmentManager(), "");
        }
    }

    public interface OnIDValid
    {
        void onValid();
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
                Intent it = new Intent(getContext(), IDAuthenticationActivity.class);
                startActivity(it);

            }
        }
    };

}
