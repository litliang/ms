package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 类名： WholeRecyclerView
 * 作者： Lei Chao.
 * 日期： 2016-09-05
 * 描述：
 */
public class WholeRecyclerView extends android.support.v7.widget.RecyclerView
{
    public WholeRecyclerView(Context context)
    {
        super(context);
    }

    public WholeRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public WholeRecyclerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }

    /**
     * 设置分割线；
     */
    public void setNeedDivider(boolean isNeedDivider)
    {
        if (isNeedDivider)
        {
            //添加分割线；
            addItemDecoration(new DividerDecoration(getContext(), DividerDecoration.VERTICAL_LIST));
        }
    }
}