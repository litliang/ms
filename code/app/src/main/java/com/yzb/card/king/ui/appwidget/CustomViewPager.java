package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gengqiyun on 2016/5/12.
 */
public class CustomViewPager extends ViewPager
{
    private float preX = 0f;
    private int lines = 1;

    public CustomViewPager(Context context)
    {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * 设置每页中item的数量；
     *
     * @param lines
     */
    public void setLines(int lines)
    {
        this.lines = lines;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int height = 0;
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();
        for (int i = 0; i < getChildCount(); i++)
        {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height * lines + paddingBottom + paddingTop, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            preX = event.getX();
        } else
        {
            // 触摸的范围，太小会无法点击；
            if (Math.abs(event.getX() - preX) > 10)
            {
                return true;
            } else
            {
                preX = event.getX();
            }
        }
        return super.onInterceptTouchEvent(event);
    }

}
