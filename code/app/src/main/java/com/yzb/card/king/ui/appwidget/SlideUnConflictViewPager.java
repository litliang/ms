package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gengqiyun on 2016/4/23.
 */
public class SlideUnConflictViewPager extends ViewPager {
    private float preX = 0f;
    private float preY = 0f;

    public SlideUnConflictViewPager(Context context) {
        super(context);
    }

    public SlideUnConflictViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
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
