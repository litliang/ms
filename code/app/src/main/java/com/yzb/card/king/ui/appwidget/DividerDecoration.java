package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yzb.card.king.R;

/**
 * 功能：RecyclerView分割线；
 *
 * @author:gengqiyun
 * @date: 2016/9/8
 */
public class DividerDecoration extends android.support.v7.widget.RecyclerView.ItemDecoration
{
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider; //分割线；

    private int mOrientation; //分割线的方向；
    private int mDividerResId = R.drawable.listview_divider;

    public DividerDecoration(Context context, int orientation)
    {
        setDividerResId(context, mDividerResId);
        setOrientation(orientation);
    }

    public DividerDecoration(Context context, int orientation, int dividerResId)
    {
        setDividerResId(context, dividerResId);
        setOrientation(orientation);
    }

    public void setDividerResId(Context context, int dividerResId)
    {
        this.mDividerResId = dividerResId;
        mDivider = context.getResources().getDrawable(mDividerResId);
    }

    public void setOrientation(int orientation)
    {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST)
        {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, android.support.v7.widget.RecyclerView parent)
    {
        if (mOrientation == VERTICAL_LIST)
        {
            drawVertical(c, parent);
        } else
        {
            drawHorizontal(c, parent);
        }
    }

    /**
     * 绘制垂直分割线；
     *
     * @param c
     * @param parent
     */
    public void drawVertical(Canvas c, android.support.v7.widget.RecyclerView parent)
    {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 绘制水平分割线；
     *
     * @param c
     * @param parent
     */
    public void drawHorizontal(Canvas c, android.support.v7.widget.RecyclerView parent)
    {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, android.support.v7.widget.RecyclerView parent)
    {
        if (mOrientation == VERTICAL_LIST)
        {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else
        {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

}