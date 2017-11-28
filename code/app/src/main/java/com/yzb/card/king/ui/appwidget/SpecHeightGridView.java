package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * Created by gengqiyun on 2016/5/17.
 * 解决ScrollView潜逃ListView的问题；
 * 此方法不是最佳方法；
 */
public class SpecHeightGridView extends GridView {
    public SpecHeightGridView(Context context) {
        super(context);
    }

    public SpecHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpecHeightGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
