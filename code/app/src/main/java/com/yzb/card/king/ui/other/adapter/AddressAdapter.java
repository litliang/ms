package com.yzb.card.king.ui.other.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.KeywordsLightTextView;

import java.util.List;
import java.util.Map;

/**
 * Created by dev on 2016/3/24.
 */
public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Map> data;
    private LayoutInflater layoutInflater = null;
    private String keywords = "";
    private LayoutInflater inflater;

    public void setSearchParam(String param) {
        this.keywords = param;
    }

    public AddressAdapter(List<Map> data, Context context) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.activity_address_search_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {
            Map<String, Object> map = data.get(position);
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.keywordsLightTextView.setSpecifiedTextsColor(String.valueOf(map.get("name")), keywords, Color.RED);
            myViewHolder.address.setText(String.valueOf(map.get("street")));
            if (onItemClickListener != null) {

                myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(myViewHolder.getLayoutPosition());
                    }
                });
            }


        }

    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        KeywordsLightTextView keywordsLightTextView;
        TextView address;

        public MyViewHolder(View itemView) {
            super(itemView);
            keywordsLightTextView = (KeywordsLightTextView) itemView.findViewById(R.id.keywords_txt);
            address = (TextView) itemView.findViewById(R.id.address);
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }
}
