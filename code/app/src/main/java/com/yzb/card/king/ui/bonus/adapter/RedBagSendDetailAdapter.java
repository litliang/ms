package com.yzb.card.king.ui.bonus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;

import java.util.List;

/**
 * 类 名： 红包明细详情的Adapter
 * 作 者： gaolei
 * 日 期：2017年01月04日
 * 描 述: 红包明细详情的Adapter
 */

public class RedBagSendDetailAdapter extends RecyclerView.Adapter<RedBagSendDetailAdapter.RedBagSendViewHolder> implements View.OnClickListener {

    private Context context;
    private List<String> list;

    public RedBagSendDetailAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RedBagSendDetailAdapter.RedBagSendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_redbag_detail, parent, false);
        RedBagSendViewHolder holder = new RedBagSendViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RedBagSendViewHolder holder, int position) {
        //String tradeTime = list.get(position).getReceiveTime() +"";
        //String[] split = tradeTime.split(" ");

        if (position % 2 == 0) {
            holder.item_zuijia_img.setVisibility(View.VISIBLE);
            holder.item_zuijia.setVisibility(View.VISIBLE);
        } else {
            holder.item_zuijia_img.setVisibility(View.INVISIBLE);
            holder.item_zuijia.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    private OnMyItemClickListener listener;

    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (recyclerView != null && listener != null) {
            //取得位置
            int position = recyclerView.getChildAdapterPosition(v);
            listener.OnMyItemClick(recyclerView, v, position, list);
        }
    }

    public interface OnMyItemClickListener {
        //view 被点击的view
        void OnMyItemClick(RecyclerView parent, View view, int position, List<String> data);
    }


    public static class RedBagSendViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_icon,item_zuijia_img;
        private TextView item_time, item_money,item_zuijia ,item_date ,item_name;

        public RedBagSendViewHolder(View itemView) {
            super(itemView);
            item_icon = (ImageView) itemView.findViewById(R.id.item_icon);
            item_date = (TextView) itemView.findViewById(R.id.item_date);
            item_time = (TextView) itemView.findViewById(R.id.item_time);
            item_money = (TextView) itemView.findViewById(R.id.item_money);
            item_zuijia = (TextView) itemView.findViewById(R.id.item_zuijia);
            item_zuijia_img = (ImageView) itemView.findViewById(R.id.item_zuijia_img);
            item_name = (TextView) itemView.findViewById(R.id.item_name);

        }
    }

}
