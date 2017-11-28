package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gengqiyun on 2016/5/12.
 */
public class CustomViewPager2 extends ViewPager {
    private float preX = 0f;
    private float preY = 0f;
    private int size;

    public CustomViewPager2(Context context) {
        super(context);
    }

    public CustomViewPager2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean res = super.onInterceptTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            preX = event.getX();
            preY = event.getY();
        } else {

            // 触摸的范围，太小会无法点击；
            if (Math.abs(event.getX() - preX) > 30 && Math.abs(event.getX() - preX) >= Math.abs(event.getY() - preY)) {
                return true;
            } else {
                preX = event.getX();
                preY = event.getY();
            }
        }
        return res;
    }

}
