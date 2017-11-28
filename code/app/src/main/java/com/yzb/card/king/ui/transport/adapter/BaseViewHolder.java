package com.yzb.card.king.ui.transport.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;

/**
 * 类名： BaseViewHolder
 * 作者： Lei Chao.
 * 日期： 2016-09-07
 * 描述：
 */
public class BaseViewHolder extends RecyclerView.ViewHolder
{
    TextView tvCarTypeAdapter;
    TextView tvCarMoneyAdapter;
    TextView tvFen;
    TextView tvSuccessRate;

    LinearLayout llSelectedItem;

    public BaseViewHolder(View convertView)
    {
        super(convertView);

        tvCarTypeAdapter = (TextView) convertView.findViewById(R.id.tvCarTypeAdapter);

        tvCarMoneyAdapter = (TextView) convertView.findViewById(R.id.tvCarMoneyAdapter);

        tvFen = (TextView) convertView.findViewById(R.id.tvFen);

        tvSuccessRate = (TextView) convertView.findViewById(R.id.tvSuccessRate);

        llSelectedItem = (LinearLayout) convertView.findViewById(R.id.llSelectedItem);
    }
}