package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by yinsg on 2016/6/3.
 */
public class TouchableTabView extends LinearLayout {
    private int currentPosition = 0;
    private int currentX, downX;

    public TouchableTabView(Context context) {
        super(context);
        init();
    }

    public TouchableTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchableTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int position = currentPosition;
                if (currentX - downX > 72) {
                    position--;
                }
                if (currentX - downX < -72) {
                    position++;
                }
                if (position < 0) position = 0;
                if (position > getChildCount() - 1) position = getChildCount() - 1;
                if (position != currentPosition) {
                    currentPosition = position;
                    refreshTab(currentPosition);
                    if (listener != null) {
                        listener.onTabChange(currentPosition);
                    }
                }
                break;
        }
        return true;
    }

//    public void addTab(View view){
//        tabs.add(view);
//    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    private void refreshTab(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            LinearLayout child = (LinearLayout) getChildAt(i);
            View view1 = child.getChildAt(1);
            view1.setEnabled(i == position);
            View view2 = child.getChildAt(2);
            view2.setEnabled(i == position);
        }
    }

    private void refreshTab(View v) {
        for (int i = 0; i < getChildCount(); i++) {
            LinearLayout child = (LinearLayout) getChildAt(i);
            View view1 = child.getChildAt(1);
            view1.setEnabled(child == v);
            View view2 = child.getChildAt(2);
            view2.setEnabled(child == v);
        }
    }

    private OnTabChangeListener listener;

    public void setOnTabChangeListener(OnTabChangeListener listener) {
        this.listener = listener;
    }

    public interface OnTabChangeListener {
        void onTabChange(int position);
    }
}
