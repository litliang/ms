package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 展示全部的条目信息
 * Created by tarena on 2016/5/31.
 */
public class WholeListView extends ListView {

    public WholeListView (Context context) {
        super(context);
    }

    public WholeListView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WholeListView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
