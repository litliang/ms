package com.yzb.card.king.ui.discount.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;


/**
 * Created by gengqiyun on 2016/4/20.
 */
public class BaseFragment extends Fragment
{
    protected String cityId;

    protected int cityLevel;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        cityId = GlobalApp.getSelectedCity().cityId;
        cityLevel= GlobalApp.getSelectedCity().cityLevel;
    }

    protected void toastCustom(String text)
    {
        ToastUtil.i(getActivity(),   text);
    }

    protected void toastCustom(int resId)
    {
        ToastUtil.i(getActivity(),   getString(resId));
    }

    protected String getCityId(Context context)
    {
        return GlobalApp.getSelectedCity().cityId;
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

    protected void readyGoWithBundleForResult(Activity context, Class claz, Bundle bundle, int reqCode)
    {
        Intent intent = new Intent(context, claz);
        if (bundle != null)
        {
            intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        }
        context.startActivityForResult(intent, reqCode);
    }

    @Override
    public void onDetach()
    {
        cancelReqByTag();
        super.onDetach();
    }

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

    public void log(String msg)
    {
        LogUtil.i(msg);
    }

    /**
     * 此处取消网络请求；
     */
    protected void cancelReqByTag()
    {
        String[] reqTagList = getReqTagList();
        if (reqTagList != null && reqTagList.length > 0)
        {
            for (String req_tag : reqTagList)
            {
//                HttpProxy.cancelSpecificRequest(getActivity(), req_tag);
            }
//            LogUtil.i("取消的tag-list=====>" + Arrays.toString(reqTagList));
        }
    }


    /**
     * 关闭进度对话框；
     */
    public void closePDialog()
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
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
     * 显示进度对话框；
     *
     * @param msgResId 消息提示；
     */
    public void showPDialog(int msgResId)
    {
        ProgressDialogUtil.getInstance().showProgressDialogMsg(getString(msgResId), getActivity(), false);
    }

    /**
     * 获得请求的标签列表；
     *
     * @return
     */
    protected String[] getReqTagList()
    {
        return null;
    }
}
