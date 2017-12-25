package com.yzb.card.king.ui.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 玉兵 on 2017/12/23.
 */

public class HotelGiftSearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;

    private List<String> searchReusultBeanList;

    private SelectedItemObject selectedItemObject;

    public  HotelGiftSearchResultAdapter(Activity activity){

        this.activity = activity;

        searchReusultBeanList = new ArrayList<>();

    }

    public void setSelectedItemObject(SelectedItemObject selectedItemObject) {
        this.selectedItemObject = selectedItemObject;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view  = LayoutInflater.from(activity).inflate(R.layout.view_item_hotel_search_result,null);

        CurrentViewHolder viewHolder = new CurrentViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof CurrentViewHolder){

            CurrentViewHolder viewHolder = (CurrentViewHolder) holder;

            viewHolder.initData(position);

        }
    }

    @Override
    public int getItemCount()
    {
        return searchReusultBeanList.size();
    }


    public void addNewListData(List<String> resultList) {

        this.searchReusultBeanList.clear();

        this.searchReusultBeanList.addAll(resultList);

        notifyDataSetChanged();

    }

    public void clearData(){

        this.searchReusultBeanList.clear();

        notifyDataSetChanged();
    }

    public void addMoreList(List<String> list) {

        this.searchReusultBeanList.addAll(list);

        notifyDataSetChanged();
    }

    class CurrentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItemName;

        private View itemView;

        public CurrentViewHolder(View itemView)
        {
            super(itemView);

            this.itemView = itemView;

            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(selectedItemObject != null){

                        selectedItemObject.selectedCallDataBack(bean);
                    }

                }
            });

        }

        String bean;
        public void initData(int position)
        {

            bean = searchReusultBeanList.get(position);

            tvItemName.setText(Html.fromHtml(bean));
        }
    }

    public   interface SelectedItemObject{

        void selectedCallDataBack( String bean);
    }
}
