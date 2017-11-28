package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by gengqiyun on 2016/5/16.
 */
public class SmoothScrollView extends ScrollView {

    public SmoothScrollView(Context context) {
        super(context);
    }

    public SmoothScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmoothScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private OnScrollistener onScrollistener;

    public void setOnScrollistener(OnScrollistener onScrollistener) {
        this.onScrollistener = onScrollistener;
    }

    public interface OnScrollistener {
        void onScroll(int scrollY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollistener != null) {
            onScrollistener.onScroll(getScrollY());
        }
    }

}
