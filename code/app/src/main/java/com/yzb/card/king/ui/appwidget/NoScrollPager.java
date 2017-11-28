package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yinsg on 2016/6/3.
 */
public class NoScrollPager extends ViewPager {
    public NoScrollPager(Context context) {
        super(context);
    }

    public NoScrollPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
