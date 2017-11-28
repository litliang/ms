package com.yzb.card.king.ui.hotel.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.yzb.card.king.bean.hotel.RoomInfoBean;
import com.yzb.card.king.ui.hotel.holder.HotelProductHolder;
import com.yzb.card.king.ui.hotel.holder.TodayLeftRoomHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：
 */
public class HotelTodayLeftRoomItemAdapter extends RecyclerAdapter<RoomInfoBean> {

    public HotelTodayLeftRoomItemAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<RoomInfoBean> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new TodayLeftRoomHolder(parent);
    }
}