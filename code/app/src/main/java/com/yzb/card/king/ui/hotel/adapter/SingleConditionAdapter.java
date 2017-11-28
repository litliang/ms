package com.yzb.card.king.ui.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.ui.appwidget.popup.adapter.SubItemadapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/9
 * 描  述：
 */
public class SingleConditionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> brandBeanList = new ArrayList<>();


    private Activity activity;


    private int currentIndex = 0;

    public int getCurrentIndex()
    {
        return currentIndex;
    }

    public SingleConditionAdapter(Activity activity)
    {
        this.activity = activity;

    }

    public SingleConditionAdapter(Activity activity, List<String> brandBeanList)
    {
        this.activity = activity;

        this.brandBeanList = brandBeanList;
    }

    public void addNewList(List<String> brandBeanList){

        currentIndex = 0;

        this.brandBeanList.clear();

        this.brandBeanList.addAll(brandBeanList);

        notifyDataSetChanged();
    }

    public void addNewList(List<String> brandBeanList,int index){

        currentIndex = index;

        this.brandBeanList.clear();

        this.brandBeanList.addAll(brandBeanList);

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(activity).inflate(R.layout.view_activity_month_item, null);


        CurrentViewHolder viewHolder = new CurrentViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if (holder instanceof CurrentViewHolder) {

            CurrentViewHolder holderTemp = (CurrentViewHolder) holder;

            holderTemp.initData(position);

        }

    }

    @Override
    public int getItemCount()
    {
        return brandBeanList.size();
    }

    class CurrentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTabName;

        private View itemView;

        public CurrentViewHolder(View itemView)
        {
            super(itemView);

            this.itemView = itemView;

            tvTabName = (TextView) itemView.findViewById(R.id.tvTabName);

        }

        public void initData(int position)
        {
            String bean = brandBeanList.get(position);

            tvTabName.setText(bean);

            if(currentIndex == position){
                tvTabName.setEnabled(true);
            }else {
                tvTabName.setEnabled(false);
            }

            itemView.setTag(position);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    currentIndex = (int) v.getTag();

                    notifyDataSetChanged();

                    if(itemDataCallBack != null){

                        itemDataCallBack.onSelectorItem(currentIndex);
                    }

                }
            });
        }
    }

    private ItemDataCallBack itemDataCallBack;

    public void setItemDataCallBack(ItemDataCallBack itemDataCallBack)
    {

        this.itemDataCallBack = itemDataCallBack;
    }


    public interface ItemDataCallBack {

        public void onSelectorItem(int subItemBean);

    }
}

