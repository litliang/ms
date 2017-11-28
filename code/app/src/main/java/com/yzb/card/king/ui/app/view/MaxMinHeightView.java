package com.yzb.card.king.ui.app.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.yzb.card.king.util.CommonUtil;

/**
 * 功能：有最大和最小高度的view；
 *
 * @author:gengqiyun
 * @date: 2016/10/26
 */
public class MaxMinHeightView extends LinearLayout
{
    public static int MIN_HEIGHT = 0; //最小值；
    public static int MAX_HEIGHT = 0;//最大值；
    private int curHeight = 0; //当前的高度；

    public static final int OP_TYPE_UP = 0; //上移操作；
    public static final int OP_TYPE_DOWN = 1; //下移操作；
    public static final int OP_TYPE_UP_FROM_BOTTOM = 2; //从最底部上移操作；

    private ObjectAnimator mAnimator;

    public MaxMinHeightView(Context context)
    {
        this(context, null);
    }

    public MaxMinHeightView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        int sceenHeight = CommonUtil.getScreenHeight(getContext());
        MIN_HEIGHT = sceenHeight * 2 / 5 - CommonUtil.dip2px(getContext(), 10);
        MAX_HEIGHT = sceenHeight * 4 / 5;
//        curHeight = MIN_HEIGHT;
    }

    /**
     * 上移动画；
     *
     * @param opType 移动类型；
     */
    public void startTranslate(int opType)
    {
        if (opType == OP_TYPE_UP && curHeight >= MAX_HEIGHT)
        {
            return;
        }
        if (opType == OP_TYPE_DOWN && curHeight <= MIN_HEIGHT)
        {
            return;
        }
        if (opType == OP_TYPE_UP_FROM_BOTTOM && curHeight > MIN_HEIGHT)
        {
            return;
        }

        if (mAnimator == null || !mAnimator.isRunning())
        {
            if (opType == OP_TYPE_UP)
            {
                mAnimator = ObjectAnimator.ofFloat(this, "curHeight", MIN_HEIGHT, MAX_HEIGHT);
            } else if (opType == OP_TYPE_DOWN)
            {
                mAnimator = ObjectAnimator.ofFloat(this, "curHeight", MAX_HEIGHT, MIN_HEIGHT);
            } else if (opType == OP_TYPE_UP_FROM_BOTTOM)
            {
                mAnimator = ObjectAnimator.ofFloat(this, "curHeight", getHeight(), MIN_HEIGHT);
            }
            mAnimator.setDuration(250);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator)
                {
                    //>=API 11;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    {
                        curHeight = (int) Float.parseFloat(String.valueOf(valueAnimator.getAnimatedValue()));
                        getLayoutParams().height = curHeight;
                        requestLayout();
                    }
                }
            });
            mAnimator.start();
        }
    }

}
