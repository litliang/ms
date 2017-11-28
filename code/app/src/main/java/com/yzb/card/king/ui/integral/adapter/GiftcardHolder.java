package com.yzb.card.king.ui.integral.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 功能：礼品卡ViewHolder；
 *
 * @author:gengqiyun
 * @date: 2016/12/20
 */
public class GiftcardHolder extends RecyclerView.ViewHolder
{
    @ViewInject(R.id.tvTitle)
    public TextView tvTitle;
    @ViewInject(R.id.tvDate)
    public TextView tvDate;
    @ViewInject(R.id.tvCardType)
    public TextView tvCardType;

    @ViewInject(R.id.tvRecvPercent)
    public TextView tvRecvPercent;
    @ViewInject(R.id.tvRestAmount)
    public TextView tvRestAmount;
    @ViewInject(R.id.tvDelete)
    public TextView tvDelete;
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

    @ViewInject(R.id.tvAgainSend)
    public  TextView tvAgainSend;

    public GiftcardHolder(View itemView)
    {
        super(itemView);
        x.view().inject(this, itemView);
    }
}
