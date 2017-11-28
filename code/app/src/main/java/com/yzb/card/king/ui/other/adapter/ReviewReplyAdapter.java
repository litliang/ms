package com.yzb.card.king.ui.other.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.yzb.card.king.ui.discount.bean.PjReplyBean;
import com.yzb.card.king.ui.other.holder.ReviewReplyViewHold;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/7
 * 描  述：
 */
public class ReviewReplyAdapter extends RecyclerAdapter<PjReplyBean> {

    public ReviewReplyAdapter(Context context)
    {
        super(context);
    }

    @Override
    public BaseViewHolder<PjReplyBean> onCreateBaseViewHolder(ViewGroup viewGroup, int i)
    {
        return new ReviewReplyViewHold(viewGroup);
    }
}
