package com.yzb.card.king.ui.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView适配器基类；
 *
 * @param <E>
 */
public abstract class BaseListAdapter<E> extends BaseAdapter
{
    protected List<E> mList = new ArrayList<>();
    protected Context mContext;
    protected LayoutInflater mInflater;

    protected void toast(int resId)
    {
        ToastUtil.i(mContext, resId);
    }

    protected boolean isEmpty(String msg)
    {
        if (TextUtils.isEmpty(msg))
        {
            return true;
        }
        return false;
    }

    protected void toast(String msg)
    {
        ToastUtil.i(mContext, msg);
    }

    public BaseListAdapter(Context context)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public BaseListAdapter(Context context, List<E> list)
    {
        this(context);
        mList = list;
    }

    public void setList(List<E> mList)
    {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return mList == null ? 0 : mList.size();
    }

    public void clearAll()
    {
        if (mList != null)
        {
            mList.clear();
        }
        notifyDataSetChanged();
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

    public void add(E item)
    {
        if (mList == null)
        {
            mList = new ArrayList<>();
        }
        mList.add(item);
        notifyDataSetChanged();
    }

    protected void toastCustom(String text)
    {
        ToastUtil.i(mContext,  text);
    }

    protected void toastCustom(int resId)
    {
        ToastUtil.i(mContext,  mContext.getResources().getString(resId));
    }

    @Override
    public E getItem(int position)
    {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    /**
     * 删除某个bean；
     *
     * @param e
     */
    public void removeBean(E e)
    {
        if (mList != null)
        {
            mList.remove(e);
            notifyDataSetChanged();
        }
    }

}
