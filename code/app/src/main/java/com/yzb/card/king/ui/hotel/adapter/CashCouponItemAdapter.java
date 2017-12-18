package com.yzb.card.king.ui.hotel.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;

import com.yzb.card.king.bean.common.BaseCouponBean;
import com.yzb.card.king.ui.hotel.holder.CashCouponItemHolder;
import com.yzb.card.king.ui.hotel.holder.GoldTicketHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * 代金券
 * Created by 玉兵 on 2017/12/18.
 */

public class CashCouponItemAdapter extends RecyclerAdapter<BaseCouponBean> {

    private Handler handler;

    public CashCouponItemAdapter(Context context)
    {
        super(context);
    }


    public void setHandler(Handler handler)
    {
        this.handler = handler;
    }

    @Override
    public BaseViewHolder<BaseCouponBean> onCreateBaseViewHolder(ViewGroup viewGroup, int i)
    {
        return new CashCouponItemHolder(viewGroup,handler);
    }


}