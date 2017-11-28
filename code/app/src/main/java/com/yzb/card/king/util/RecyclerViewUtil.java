package com.yzb.card.king.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yzb.card.king.ui.appwidget.DividerDecoration;

/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2016/11/19
 */
public class RecyclerViewUtil
{

    /**
     * @param context
     * @param recyclerView
     * @param orientation  方向；
     * @param dividerResId 分割线；
     */
    public static void init(Context context, RecyclerView recyclerView, int orientation, int dividerResId)
    {
        if (recyclerView != null && context != null)
        {
            //设置水平方向；
            LinearLayoutManager llm = new LinearLayoutManager(context, orientation, false);
            recyclerView.setLayoutManager(llm);
            if (dividerResId > 0)
            {
                DividerDecoration divider1 = new DividerDecoration(context, orientation, dividerResId);
                recyclerView.addItemDecoration(divider1);
            }else{

            }
        }
    }
}
