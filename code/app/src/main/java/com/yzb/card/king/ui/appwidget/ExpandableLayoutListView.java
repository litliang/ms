package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 可伸展的ListView；
 *
 */
public class ExpandableLayoutListView extends ListView {
    private Integer position = -1;

    public ExpandableLayoutListView(Context context) {
        super(context);
        setOnScrollListener(new OnExpandableLayoutScrollListener());
    }

    public ExpandableLayoutListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(new OnExpandableLayoutScrollListener());
    }

    public ExpandableLayoutListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnScrollListener(new OnExpandableLayoutScrollListener());
    }


    /*ExpandableLayoutItem的点击事件*/
    @Override
    public boolean performItemClick(View view, int position, long id) {
        this.position = position;

        /*迭代ListView 可见部分item元素；*/
        for (int index = 0; index < getChildCount(); ++index) {

            /*关闭非点击item的其它item   index == position - getFirstVisiblePosition()时，index就是用户点击的item*/
            if (index != (position - getFirstVisiblePosition())) {
                ExpandableLayoutItem currentExpandableLayout = (ExpandableLayoutItem) getChildAt(index)
                        .findViewWithTag(ExpandableLayoutItem.class.getName());
                currentExpandableLayout.hide();
            }
        }

        /*得到用户点击的item，getChild获得的是可见范围内的child */
        ExpandableLayoutItem expandableLayout = (ExpandableLayoutItem)
                getChildAt(position - getFirstVisiblePosition()).findViewWithTag(ExpandableLayoutItem.class.getName());

        if (expandableLayout.isOpened()) {
            expandableLayout.hide();
        } else {
            expandableLayout.show();
        }
        return super.performItemClick(view, position, id);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        if (!(l instanceof OnExpandableLayoutScrollListener))
            throw new IllegalArgumentException("OnScrollListner must be an OnExpandableLayoutScrollListener");

        super.setOnScrollListener(l);
    }

    public class OnExpandableLayoutScrollListener implements OnScrollListener {
        private int scrollState = 0;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            this.scrollState = scrollState;
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (scrollState != SCROLL_STATE_IDLE) {
                for (int index = 0; index < getChildCount(); ++index) {
                    ExpandableLayoutItem currentExpandableLayout = (ExpandableLayoutItem) getChildAt(index).findViewWithTag(ExpandableLayoutItem.class.getName());
                    if (currentExpandableLayout.isOpened() && index != (position - getFirstVisiblePosition())) {
                        currentExpandableLayout.hideNow();
                    } else if (!currentExpandableLayout.getCloseByUser() && !currentExpandableLayout.isOpened() && index == (position - getFirstVisiblePosition())) {
                        currentExpandableLayout.showNow();
                    }
                }
            }
        }
    }
}
