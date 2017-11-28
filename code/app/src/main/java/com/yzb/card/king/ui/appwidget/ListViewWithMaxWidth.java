package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by gengqiyun on 2016/4/23.
 */
public class ListViewWithMaxWidth extends ListView {
    public ListViewWithMaxWidth(Context context) {
        super(context);
    }

    public ListViewWithMaxWidth(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewWithMaxWidth(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = getChildsMaxWidth() + getPaddingLeft() + getPaddingRight();
        int widthMs = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.EXACTLY);
        super.onMeasure(widthMs, heightMeasureSpec);
    }

    /**
     * 获取子View的最大width；
     *
     * @return
     */
    private int getChildsMaxWidth() {
        int maxWidth = 0;
        View view = null;
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return 0;
        }

        for (int i = 0, j = adapter.getCount(); i < j; i++) {
            view = adapter.getView(i, view, this);

            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            if (view.getMeasuredWidth() > maxWidth) {
                maxWidth = view.getMeasuredWidth();
            }
        }
        return maxWidth;
    }


}
