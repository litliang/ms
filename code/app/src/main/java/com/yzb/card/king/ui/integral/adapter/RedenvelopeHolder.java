package com.yzb.card.king.ui.integral.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/2/17
 * 描  述：
 */
public class RedenvelopeHolder extends RecyclerView.ViewHolder
{
    @ViewInject(R.id.tvTitle)
    public TextView tvTitle;
    @ViewInject(R.id.tvDate)
    public TextView tvDate;
    @ViewInject(R.id.tvCardType)
    public TextView tvCardType;

    @ViewInject(R.id.tvRecvPercent)
    public TextView tvRecvPercent;
    @ViewInject(R.id.tvDelete)
    public TextView tvDelete;
    @ViewInject(R.id.tvShare)
    public TextView tvShare;
    @ViewInject(R.id.tvReBuy)
    public TextView tvReBuy;
    @ViewInject(R.id.tvCancel)
    public TextView tvCancel;

    @ViewInject(R.id.tvPay)
    public TextView tvPay;
    @ViewInject(R.id.tvOrderAmount)
    public TextView tvOrderAmount;
    @ViewInject(R.id.tvStatus)
    public TextView tvStatus;

    public RedenvelopeHolder(View itemView)
    {
        super(itemView);
        x.view().inject(this, itemView);
    }
}

