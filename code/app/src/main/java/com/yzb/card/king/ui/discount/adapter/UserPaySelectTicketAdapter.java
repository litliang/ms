package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;

import com.yzb.card.king.bean.common.BaseCouponBean;
import com.yzb.card.king.ui.discount.holder.UserPaySelectTicketHolder;
import com.yzb.card.king.ui.hotel.holder.GoldTicketHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/23
 * 描  述：
 */
public class UserPaySelectTicketAdapter  extends RecyclerAdapter<BaseCouponBean> {

    private Handler handler;

    private long actionId;

    private  double orderMoney;

    private int issuePlatform;

    public UserPaySelectTicketAdapter(Context context)
    {
        super(context);
    }


    public void setHandler(Handler handler)
    {
        this.handler = handler;
    }

    public void setOrderMoney(double orderMoney) {
        this.orderMoney = orderMoney;
    }

    @Override
    public BaseViewHolder<BaseCouponBean> onCreateBaseViewHolder(ViewGroup viewGroup, int i)
    {
        return new UserPaySelectTicketHolder(viewGroup,handler,actionId,orderMoney,issuePlatform);
    }

    public void setActionId(long actionId)
    {
        this.actionId = actionId;
    }

    public void setIssuePlatform(int issuePlatform) {
        this.issuePlatform = issuePlatform;
    }
}
