package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/20 10:53
 * 描述：
 */
public class MemberSeekBar extends SeekBar {
    public MemberSeekBar(Context context) {
        super(context);
    }

    public MemberSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MemberSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public synchronized void setProgress(int progress) {
//        if(progress<999){
//            progress = 100*progress/(999*3);
//        }else if(progress<4999){
//            progress = (progress-999)/60+100/3;
//        }else {
//            progress = 100;
//        }

        super.setProgress(progress);
    }
}
