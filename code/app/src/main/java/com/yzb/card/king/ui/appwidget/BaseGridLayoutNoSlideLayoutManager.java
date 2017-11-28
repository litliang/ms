package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * 类名： BaseGridLayoutNoSlideLayoutManager
 * 作者： Lei Chao.
 * 日期： 2016-10-15
 * 描述： 禁止recyclerview滑动 gridview
 */
public class BaseGridLayoutNoSlideLayoutManager extends GridLayoutManager
{

    @Override
    public boolean canScrollHorizontally()
    {
        return false;
    }

    @Override
    public boolean canScrollVertically()
    {
        return false;
    }

    public BaseGridLayoutNoSlideLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BaseGridLayoutNoSlideLayoutManager(Context context, int spanCount)
    {
        super(context, spanCount);
    }

    public BaseGridLayoutNoSlideLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout)
    {
        super(context, spanCount, orientation, reverseLayout);
    }
}