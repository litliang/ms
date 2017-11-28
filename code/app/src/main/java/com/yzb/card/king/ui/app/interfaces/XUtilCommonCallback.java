package com.yzb.card.king.ui.app.interfaces;

import org.xutils.common.Callback;

/**
 * 功能：xutil 图片加载回调适配器；
 *
 * @author:gengqiyun
 * @date: 2017/3/2
 */
public class XUtilCommonCallback<T> implements Callback.CommonCallback<T>
{

    @Override
    public void onSuccess(T o)
    {
        success(o);
    }

    @Override
    public void onError(Throwable throwable, boolean b)
    {

    }

    @Override
    public void onCancelled(CancelledException e)
    {

    }

    @Override
    public void onFinished()
    {

    }

    protected void success(T t)
    {

    }
}
