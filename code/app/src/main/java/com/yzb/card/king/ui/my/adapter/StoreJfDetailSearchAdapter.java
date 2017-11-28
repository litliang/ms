package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.my.bean.StoreJfBean;

import java.util.List;

/**
 * 类 名： 商店积分查询
 * 作 者： gaolei
 * 日 期：2017年01月03日
 * 描 述：商店积分查询
 */

public class StoreJfDetailSearchAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private List<StoreJfBean> list;
    private RecyclerView recyclerView;

    public StoreJfDetailSearchAdapter(Context context, List<StoreJfBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return R.layout.load_more_layout;
        } else {
            return R.layout.item_store_jf_search;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        if (viewType == R.layout.load_more_layout) {
            return new StoreJfDetailSearchAdapter.LoadMoreVH(view);
        } else {
            view.setOnClickListener(this);
            return new StoreJfDetailSearchAdapter.MyViewHolder(view);
        }
    }

    //底部加载更多item的viewholder
    static class LoadMoreVH extends RecyclerView.ViewHolder {

        public LoadMoreVH(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof StoreJfDetailSearchAdapter.LoadMoreVH) {
                listener.OnLoad();
            } else {
                ((MyViewHolder)holder).name.setText(list.get(position).getShopName() +"");
                ((MyViewHolder)holder).number.setText(list.get(position).getShopPoints() +"");
            }
    }

    public void addData(List<StoreJfBean> data) {
        list.addAll(data);
        notifyDataSetChanged();//添加数据后通知 adpter 更新
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (recyclerView != null && listener != null) {
            int position = recyclerView.getChildAdapterPosition(v);
            listener.OnMyItemClick(recyclerView, v, position, list);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, number;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.store_detail_search_name);
            number = (TextView) itemView.findViewById(R.id.store_detail_search_number);
        }
    }

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

    public interface OnMyItemClickListener {
        //view 被点击的view
        void OnMyItemClick(RecyclerView parent, View view, int position, List<StoreJfBean> data);
        void OnLoad();
    }

}
