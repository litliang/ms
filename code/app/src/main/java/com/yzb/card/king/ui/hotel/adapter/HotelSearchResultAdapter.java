package com.yzb.card.king.ui.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.SearchReusultBean;
import com.yzb.card.king.bean.SubItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/21
 * 描  述：
 */
public class HotelSearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  Activity activity;

    private  List<SearchReusultBean> searchReusultBeanList;

    private  SelectedItemObject  selectedItemObject;

    public  HotelSearchResultAdapter(Activity activity){

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

        if(holder instanceof  CurrentViewHolder){

            CurrentViewHolder viewHolder = (CurrentViewHolder) holder;

            viewHolder.initData(position);

        }
    }

    @Override
    public int getItemCount()
    {
        return searchReusultBeanList.size();
    }



    public void addNewListData(List<SearchReusultBean> resultList) {


        this.searchReusultBeanList.clear();

        this.searchReusultBeanList.addAll(resultList);

        notifyDataSetChanged();

    }

    public void clearData(){

        this.searchReusultBeanList.clear();

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

        SearchReusultBean bean;

        public void initData(int position)
        {

             bean = searchReusultBeanList.get(position);

            tvItemName.setText(Html.fromHtml(bean.getSearchName()));
        }
    }

   public   interface SelectedItemObject{

        void selectedCallDataBack( SearchReusultBean bean);
    }
}
