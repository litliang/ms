package com.yzb.card.king.ui.other.adapter;

import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/1 15:48
 */
public class BaseListAdapter<T> extends AbsBaseListAdapter
{
    private Class holderClazz;
    public BaseListAdapter(List<T> list, Class holderClazz)
    {
        super(list);
        this.holderClazz = holderClazz;
    }

    @Override
    protected Holder getHolder(int position)
    {
        Holder<T> holder = null;
        try
        {
            holder = (Holder) holderClazz.newInstance();
        } catch (InstantiationException e)
        {
            LogUtil.e(e.getMessage());
        } catch (IllegalAccessException e)
        {
            LogUtil.e(e.getMessage());
        }
        return holder;
    }
}
