package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 类  名：自定义的gridview上下不滚动
 * 作  者：Li Yubing
 * 日  期：2017/3/30
 * 描  述：
 */
public class NoRollGridView extends GridView {


    public NoRollGridView (Context context) {
        super(context);
    }

    public NoRollGridView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoRollGridView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    /**
     * 设置上下不滚动
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;//true:禁止滚动
        }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}