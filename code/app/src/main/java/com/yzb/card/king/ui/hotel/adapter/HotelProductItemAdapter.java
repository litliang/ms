package com.yzb.card.king.ui.hotel.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.yzb.card.king.bean.hotel.HotelBean;
import com.yzb.card.king.bean.hotel.HotelProductObjectBean;
import com.yzb.card.king.ui.hotel.holder.HotelProductHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;


/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：
 */
public class HotelProductItemAdapter extends RecyclerAdapter<HotelProductObjectBean> {

    public HotelProductItemAdapter(Context context)
    {
        super(context);
    }


//    @Override
//    public int getItemViewType(int position)
//    {
//
//
//        if (getData() == null || getData().size() == 0) {
//            return super.getItemViewType(position);
//        } else {
//
//            if(position<getItemCount()-3){
//                HotelBean bean = getData().get(position);
//
//                if (bean.getType() == 1) {
//                    return 10000;
//                } else {
//                    return 10001;
//                }
//            }else{
//                return super.getItemViewType(position);
//            }
//
//
//        }
//    }

    @Override
    public BaseViewHolder<HotelProductObjectBean> onCreateBaseViewHolder(ViewGroup parent, int viewType)
    {
//        if (viewType == 10000) {
            return new HotelProductHolder(parent);
//        } else {
//            return new HotelProductHolderTemp(parent);
//
//        }

    }

}
