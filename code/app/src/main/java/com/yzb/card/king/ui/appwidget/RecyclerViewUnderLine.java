package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * 类名： RecyclerViewUnderLine
 * 作者： Lei Chao.
 * 日期： 2016-09-12
 * 描述： RecyclerView的分割线
 */
public class RecyclerViewUnderLine extends RecyclerView.ItemDecoration
{

    public static final int horizontal = LinearLayoutManager.HORIZONTAL;

    public static final int vertical = LinearLayoutManager.VERTICAL;


    private int mOrientation;

    private Drawable mDrawable;

    public static final int ATTRS[] =
            {
                    android.R.attr.listDivider
            };

    public RecyclerViewUnderLine(Context context, int orientation)
    {
        this.mOrientation = orientation;
        TypedArray ta = context.obtainStyledAttributes(ATTRS);
        this.mDrawable = ta.getDrawable(0);
        ta.recycle();
        setOrientation(this.mOrientation);

    }

    private void setOrientation(int orientation)
    {
        if (mOrientation != vertical && mOrientation != horizontal)
            throw new IllegalArgumentException("需要指定Orientation模式");
        this.mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {

        super.getItemOffsets(outRect, view, parent, state);

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        // 水平
        if (mOrientation == horizontal)
        {
            drawLine(c, parent, state);
        } else
        {
            drawHorizontalLine(c, parent, state);
        }
    }

    private void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + this.mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
            //Log.d("wnw", left + " " + top + " "+right+"   "+bottom+" "+i);
        }
    }

    private void drawLine(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            final View child = parent.getChildAt(i);


            // 获取Child的布局信息
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDrawable.getIntrinsicWidth();
            mDrawable.setBounds(left, top, right, bottom);
            this.mDrawable.draw(c);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent)
    {
        if (this.mOrientation == horizontal)
        {
            outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
        } else
        {
            outRect.set(0, 0, mDrawable.getIntrinsicWidth(), 0);
        }

    }
}