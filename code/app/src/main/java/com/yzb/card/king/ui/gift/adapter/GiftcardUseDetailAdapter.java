package com.yzb.card.king.ui.gift.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.gift.bean.GiftCardRecOrSendBean;

import java.util.List;

/**
 * 类 名： 礼品卡领取明细适配
 * 作 者： gaolei
 * 日 期：2016年12月27日
 * 描 述：礼品卡领取明细
 */

public class GiftcardUseDetailAdapter extends RecyclerView.Adapter<GiftcardUseDetailAdapter.MyViewHolder> {

    private Context context;
    private List<GiftCardRecOrSendBean> list;

    public GiftcardUseDetailAdapter(Context context, List<GiftCardRecOrSendBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public GiftcardUseDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giftcard_use_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GiftcardUseDetailAdapter.MyViewHolder holder, int position) {

        //list.get(position).getReceiveTime();
        if (TextUtils.isEmpty(list.get(position).getReceiveTime())) {
            holder.lqStatus.setText("未领取");

            holder.lqStatus.setTextColor(context.getResources().getColor(R.color.hotel_orange_fe7f07));
        } else {
            holder.lqStatus.setText("已领取");
        }
        holder.stCard.setText(list.get(position).getCardName() + "");
        holder.money.setText("¥ " + list.get(position).getAmount() + "");
        holder.issuePerson.setText(list.get(position).getReceivePerson() + "");
        holder.cardNumber.setText(list.get(position).getRecordNo() + "");



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView stCard;
        TextView issuePerson;
        TextView lqStatus;
        TextView cardNumber;
        TextView money;

        public MyViewHolder(View view) {
            super(view);
            stCard = (TextView) view.findViewById(R.id.stCard);
            issuePerson = (TextView) view.findViewById(R.id.issuePerson);
            lqStatus = (TextView) view.findViewById(R.id.lqStatus);
            cardNumber = (TextView) view.findViewById(R.id.cardNumber);
            money = (TextView) view.findViewById(R.id.money);
        }
    }
}
