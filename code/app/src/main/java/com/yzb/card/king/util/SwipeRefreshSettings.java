package com.yzb.card.king.util;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.yzb.card.king.R;

/**
 * Created by gengqiyun on 2016/4/12.
 */
public class SwipeRefreshSettings {

    /**
     * SwipeRefreshLayout属性设置；
     *
     * @param swipeRefreshLayout
     */
    public static void setAttrbutes(Context context, SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeColors(
                    context.getResources().getColor(R.color.swipe_color0));
            swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        }
    }
}
