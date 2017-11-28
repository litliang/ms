package com.yzb.card.king.ui.hotel.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.yzb.card.king.bean.BankActivityInfoBean;
import com.yzb.card.king.ui.hotel.holder.ProductCardLifeStagesHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：生活分期
 */
public class ProductCardLifeStagesAdapter  extends RecyclerAdapter<BankActivityInfoBean> {

    public ProductCardLifeStagesAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseViewHolder<BankActivityInfoBean> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ProductCardLifeStagesHolder(parent);
    }
}