package com.yzb.card.king.ui.integral.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;

/**
 * 类  名：机票ViewHolder视图
 * 作  者：Li Yubing
 * 日  期：2017/2/18
 * 描  述：
 */
public class TypePlanVH extends RecyclerView.ViewHolder
{
    WholeRecyclerView wholeRecyclerView;

    TextView tvPay; //支付；

    TextView tvDelete; //退票；

    TextView tvGaiQian;//改签；
    TextView tvShanchu;//删除；
    TextView tvCancel;//取消；
    TextView tvOrderAmount;//订单金额；
    TextView tvStatus;//订单状态；

    LinearLayout llParent;

    public TypePlanVH(View itemView)
    {
        super(itemView);

        wholeRecyclerView = (WholeRecyclerView) itemView.findViewById(R.id.plain_recycler);

        tvOrderAmount = (TextView) itemView.findViewById(R.id.tvOrderAmount);
        tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        tvPay = (TextView) itemView.findViewById(R.id.tv_pay);

        tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
        tvShanchu = (TextView) itemView.findViewById(R.id.tv_shanchu);
        tvCancel = (TextView) itemView.findViewById(R.id.tv_cancel);

        tvGaiQian = (TextView) itemView.findViewById(R.id.tv_gaiqian);

        llParent = (LinearLayout) itemView.findViewById(R.id.llParent);
    }
}
