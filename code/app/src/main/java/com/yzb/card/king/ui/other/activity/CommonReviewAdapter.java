package com.yzb.card.king.ui.other.activity;

import android.content.Context;
import android.view.ViewGroup;

import com.yzb.card.king.ui.discount.bean.PjBean;
import com.yzb.card.king.ui.other.holder.CommonReviewHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * 类  名：评论适配器
 * 作  者：Li Yubing
 * 日  期：2017/9/7
 * 描  述：
 */
public class CommonReviewAdapter extends RecyclerAdapter<PjBean> {


    public CommonReviewAdapter(Context context)
    {
        super(context);
    }

    @Override
    public BaseViewHolder<PjBean> onCreateBaseViewHolder(ViewGroup viewGroup, int i)
    {
        return new CommonReviewHolder(viewGroup);
    }
}
