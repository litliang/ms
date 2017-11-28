package com.yzb.card.king.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;

/**
 * 类  名：所有DialogFragment的基类
 * 作  者：gengqiyun;
 * 日  期：2016/8/26
 * 描  述：
 */
public abstract class BaseDialogFragment extends DialogFragment
{
    protected Activity activity;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof Activity)
        {
            activity = (Activity) context;
        }
    }

    /**
     * 用户是否登录
     *
     * @return
     */
    protected boolean isLogin()
    {
        return UserManager.getInstance().isLogin();
    }

    protected UserBean getUserBean()
    {
        return UserManager.getInstance().getUserBean();
    }

    protected void toastCustom(String text)
    {
        if (getActivity() != null)
        ToastUtil.i(getActivity(),text);
    }

    protected void toastCustom(int resId)
    {
        if (getActivity() != null)
        ToastUtil.i(getActivity(),getActivity().getString(resId));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = new Dialog(getActivity(), getDialogStyle());
        View view = LayoutInflater.from(getActivity()).inflate(getLayoutResId(), null);
        dialog.setContentView(view);
        initView(view);

        Window window = dialog.getWindow();
        if (getWindowAnimation() != 0)
        {
            window.setWindowAnimations(getWindowAnimation());
        }
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setGravity(getGravity());
        layoutParams.width = getDialogWidth();
        if (getDialogHeight() > 0)
        {
            layoutParams.height = getDialogHeight();
        }
        window.setAttributes(layoutParams);

        return dialog;
    }

    protected int getDialogWidth()
    {
        return CommonUtil.getScreenWidth(getActivity());
    }


    /**
     * 获取window动画；
     */
    protected int getWindowAnimation()
    {
        return R.style.dialog_bottom_animation_style;
    }


    protected int getGravity()
    {
        return Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
    }

    protected int getDialogStyle()
    {
        return R.style.dialog_style;
    }

    /**
     * 获取dialog的高度；
     *
     * @return 0:自适应；
     */
    protected int getDialogHeight()
    {
        return 0;
    }

    /**
     * 获取布局文件资源id；
     *
     * @return
     */
    protected abstract int getLayoutResId();


    /**
     * 初始化；
     *
     * @param view
     */
    protected void initView(View view)
    {
    }


    protected boolean isEmpty(String text)
    {
        if (TextUtils.isEmpty(text))
        {
            return true;
        }
        return false;
    }

    /**
     * 显示进度对话框；
     *
     * @param msgResId 消息提示；
     */
    public void showPDialog(int msgResId)
    {
        ProgressDialogUtil.getInstance().showProgressDialogMsg(getString(msgResId), getActivity(), false);
    }

    /**
     * 显示进度对话框；
     *
     * @param msg 消息提示；
     */
    public void showPDialog(String msg)
    {
        ProgressDialogUtil.getInstance().showProgressDialogMsg(msg, getActivity(), false);
    }

    /**
     * 关闭进度对话框；
     */
    public void closePDialog()
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }
}
