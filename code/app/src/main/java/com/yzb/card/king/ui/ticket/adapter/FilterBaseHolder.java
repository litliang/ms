package com.yzb.card.king.ui.ticket.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.yzb.card.king.R;

/**
 * 类名： FilterBaseHolder
 * 作者： Lei Chao.
 * 日期： 2016-10-15
 * 描述：
 */
public class FilterBaseHolder extends RecyclerView.ViewHolder
{

    CheckBox checkBox;

    public FilterBaseHolder(View itemView)
    {
        super(itemView);
        checkBox = (CheckBox) itemView.findViewById(R.id.cb_filter);
    }
}