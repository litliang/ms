package com.yzb.card.king.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2016/11/16
 */
public abstract class BaseRecyAdapter<E> extends RecyclerView.Adapter
{
    protected List<E> mList = new ArrayList<>();
    protected Context context;
    protected LayoutInflater inflater;

    public BaseRecyAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public BaseRecyAdapter(List<E> lineBeans, Context context)
    {
        this.mList = lineBeans;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void clearAll()
    {
        if (mList != null)
        {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    public void setList(List<E> mList)
    {
        this.mList = mList;
        notifyDataSetChanged();
    }


    protected void toastCustom(String text)
    {
        ToastUtil.i(context,  text);
    }

    protected void toastCustom(int resId)
    {
        ToastUtil.i(context,  context.getResources().getString(resId));
    }

    public List<E> getData()
    {
        return mList;
    }

    public void appendALL(List<E> lists)
    {
        if (lists == null || lists.size() == 0)
        {
            return;
        }
        mList.addAll(lists);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return mList == null ? 0 : mList.size();
    }

}
