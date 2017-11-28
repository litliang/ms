package com.yzb.card.king.ui.integral.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;

/**
 * 类  名：常用订单ViewHolder
 * 作  者：Li Yubing
 * 日  期：2017/2/18
 * 描  述：
 */
public class OrderManageHolder extends RecyclerView.ViewHolder
{
    ImageView orderTypeLogo;
    TextView hotelname;
    TextView content2;
    TextView orderAmount;
    TextView title;
    TextView status;
    TextView delete;
    TextView again;
    TextView comment;
    TextView share;
    TextView tvPay;
    TextView cancle;
    TextView detailCount;
    TextView tv_exite_money;
    TextView addressTv, timeInterval;
    LinearLayout llOne,llTwo;

    public OrderManageHolder(View itemView)
    {
        super(itemView);
        timeInterval = (TextView) itemView.findViewById(R.id.time_interval);
        tvPay = (TextView) itemView.findViewById(R.id.tv_pay);
        orderTypeLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
        hotelname = (TextView) itemView.findViewById(R.id.tv_content1);
        content2 = (TextView) itemView.findViewById(R.id.tv_content2);
        orderAmount = (TextView) itemView.findViewById(R.id.tv_order_amount);
        title = (TextView) itemView.findViewById(R.id.tv_title);
        status = (TextView) itemView.findViewById(R.id.tv_status);
        delete = (TextView) itemView.findViewById(R.id.tv_delete);
        again = (TextView) itemView.findViewById(R.id.tv_again);
        comment = (TextView) itemView.findViewById(R.id.tv_comment);
        share = (TextView) itemView.findViewById(R.id.tv_share);
        tv_exite_money = (TextView) itemView.findViewById(R.id.tv_exite_money);
        cancle = (TextView) itemView.findViewById(R.id.tv_cancle);
        detailCount = (TextView) itemView.findViewById(R.id.tv_detailcount);
        addressTv = (TextView) itemView.findViewById(R.id.hotel_address);
        llOne = (LinearLayout) itemView.findViewById(R.id.llOne);
        llTwo = (LinearLayout) itemView.findViewById(R.id.llTwo);
    }
}

