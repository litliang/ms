package com.yzb.card.king.ui.appwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yzb.card.king.util.CommonUtil;

/**
 * Created by gengqiyun on 2016/4/21.
 * 最大高度为屏高1/2 的View；
 */
public class LiearWithMaxSize extends LinearLayout {

    public LiearWithMaxSize(Context context) {
        super(context);
    }

    public LiearWithMaxSize(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public LiearWithMaxSize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int maxHeightMs = MeasureSpec.makeMeasureSpec(CommonUtil.getScreenHeight(getContext()) * 2 / 5,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, maxHeightMs);
    }
}
