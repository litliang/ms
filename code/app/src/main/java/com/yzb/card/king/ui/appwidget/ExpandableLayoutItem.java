/***********************************************************************************
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2014 Robin Chutaux
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.util.CommonUtil;


public class ExpandableLayoutItem extends RelativeLayout {
    private Boolean isAnimationRunning = false;
    private Boolean isOpened = false;
    private Integer duration;
    private FrameLayout contentLayout;
    private FrameLayout headerLayout;
    private Boolean closeByUser = true;
    private View headerView;

    public ExpandableLayoutItem(Context context) {
        super(context);
    }

    public ExpandableLayoutItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableLayoutItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        final View rootView = View.inflate(context, R.layout.view_expandable, this);

        headerLayout = (FrameLayout) rootView.findViewById(R.id.view_expandable_headerlayout);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLayout);
        final int headerID = typedArray.getResourceId(R.styleable.ExpandableLayout_el_headerLayout, -1);
        final int contentID = typedArray.getResourceId(R.styleable.ExpandableLayout_el_contentLayout, -1);
        contentLayout = (FrameLayout) rootView.findViewById(R.id.view_expandable_contentLayout);

        if (headerID == -1 || contentID == -1)
            throw new IllegalArgumentException("HeaderLayout and ContentLayout cannot be null!");

        if (isInEditMode())
            return;

        duration = typedArray.getInt(R.styleable.ExpandableLayout_el_duration, getContext().getResources().getInteger(android.R.integer.config_shortAnimTime));
        headerView = View.inflate(context, headerID, null);

        headerView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        if (headerView != null) {
            View view = headerView.findViewById(R.id.arrow);
            resetWhenHide(view);
        }

        headerLayout.addView(headerView);

        setTag(ExpandableLayoutItem.class.getName());

        final View contentView = View.inflate(context, contentID, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        contentLayout.addView(contentView);

        contentLayout.setVisibility(GONE);

        /**********************设置onTouch事件，return true：不会执行onClick事件，否则执行onClick**************************/
        headerLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //如果手指弹起时，且为打开状态，隐藏contentLayout；
                if (isOpened() && event.getAction() == MotionEvent.ACTION_UP) {
                    hide();
                    closeByUser = true;
                }
                //打开状态，且是按下时，拦截，不再执行onClick方法；
                return isOpened() && event.getAction() == MotionEvent.ACTION_DOWN;
            }
        });
        /************************************************/
    }

    private void expand(final View v) {
        isOpened = true;
        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = (interpolatedTime == 1) ? LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }


            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        v.startAnimation(animation);
    }

    private void collapse(final View v) {
        isOpened = false;
        final int initialHeight = v.getMeasuredHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                    isOpened = false;
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration(duration);
        v.startAnimation(animation);
    }

    public void hideNow() {
        contentLayout.getLayoutParams().height = 0;
        contentLayout.invalidate();
        contentLayout.setVisibility(View.GONE);
        isOpened = false;
    }

    public void showNow() {
        if (!this.isOpened()) {
            contentLayout.setVisibility(VISIBLE);
            this.isOpened = true;
            contentLayout.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
            contentLayout.invalidate();
        }
    }

    public Boolean isOpened() {
        return isOpened;
    }

    public void show() {
        if (!isAnimationRunning) {
            expand(contentLayout);
            isAnimationRunning = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isAnimationRunning = false;
                }
            }, duration);
        }

        if (headerView != null) {
            View view = headerView.findViewById(R.id.arrow);
            resetWhenShow(view);
        }
    }

    public FrameLayout getHeaderLayout() {
        return headerLayout;
    }

    public FrameLayout getContentLayout() {
        return contentLayout;
    }

    public void hide() {
        if (!isAnimationRunning) {
            collapse(contentLayout);
            isAnimationRunning = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isAnimationRunning = false;
                }
            }, duration);
        }

        if (headerView != null) {
            View view = headerView.findViewById(R.id.arrow);
            if (view != null) {
                resetWhenHide(view);
            }
        }
        closeByUser = false;
    }

    public void resetWhenHide(View view) {

        ViewGroup.LayoutParams vl = view.getLayoutParams();
        vl.width = CommonUtil.dip2px(getContext(), 9);
        vl.height = CommonUtil.dip2px(getContext(), 17);
        view.setLayoutParams(vl);
        view.setBackgroundResource(R.mipmap.arrow_small_right);
    }

    public void resetWhenShow(View view) {
//        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate_animation_start2));
        ViewGroup.LayoutParams vl = view.getLayoutParams();
        vl.width = CommonUtil.dip2px(getContext(), 17);
        vl.height = CommonUtil.dip2px(getContext(), 9);
        view.setLayoutParams(vl);
        view.setBackgroundResource(R.mipmap.arrow_small_down);
    }

    public Boolean getCloseByUser() {
        return closeByUser;
    }
}
