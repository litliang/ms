package com.yzb.card.king.ui.order.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;

import com.yzb.card.king.bean.hotel.HotelProductObjectBean;
import com.yzb.card.king.bean.order.HotelOrderServeBean;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/16
 * 描  述：
 */
public class HotelOrderServeAdapter extends RecyclerAdapter<HotelOrderServeBean> {

    private Handler handler;

    public HotelOrderServeAdapter(Context context, Handler handler)
    {
        super(context);

        this.handler = handler;
    }

    @Override
    public BaseViewHolder<HotelOrderServeBean> onCreateBaseViewHolder(ViewGroup viewGroup, int i)
    {
        return new HotelOrderServeHolder(viewGroup,handler);
    }
}
