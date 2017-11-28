package com.yzb.card.king.ui.my.holder;

import android.view.View;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/16 10:38
 */
public interface IHolder<T>
{
    View getView();
    void setData(T data);
}
