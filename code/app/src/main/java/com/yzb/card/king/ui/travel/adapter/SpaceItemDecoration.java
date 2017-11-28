package com.yzb.card.king.ui.travel.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yzb.card.king.util.LogUtil;

/**
 * 类  名：兩排网格分隔线
 * 作  者：Li Yubing
 * 日  期：2017/3/28
 * 描  述：
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    private int defaultIndex = 2;

    private int hInt = 2;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    public void setHInt(int hInt){

        this.hInt = hInt;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.right = space/hInt;
        outRect.bottom = space/defaultIndex;
        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) %2!=0) {
            outRect.right = 0;
            outRect.left = space/hInt;
        }
    }
}
