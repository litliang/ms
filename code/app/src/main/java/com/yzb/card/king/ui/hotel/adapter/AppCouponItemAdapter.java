package com.yzb.card.king.ui.hotel.adapter;


import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;

import com.yzb.card.king.bean.common.BaseCouponBean;
import com.yzb.card.king.ui.hotel.holder.GoldTicketHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/23
 * 描  述：
 */
public class AppCouponItemAdapter extends RecyclerAdapter<BaseCouponBean> {

    private Handler handler;

    public AppCouponItemAdapter(Context context)
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
        return new GoldTicketHolder(viewGroup,handler);
    }


}
