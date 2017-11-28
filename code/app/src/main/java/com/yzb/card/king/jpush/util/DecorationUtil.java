package com.yzb.card.king.jpush.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 1 on 2016/12/5.
 * 类 名： RecyclerVeiw的工具类
 * 作 者： gaolei
 * 日 期：2016年12月6日
 * 描 述：改变每个item的间隔线
 */


public class DecorationUtil extends RecyclerView.ItemDecoration {

    private int space;

    public DecorationUtil(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
        outRect.top = space;
        outRect.left = space;
        outRect.right = space;
    }
}
