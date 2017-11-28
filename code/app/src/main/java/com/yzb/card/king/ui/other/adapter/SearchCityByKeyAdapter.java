package com.yzb.card.king.ui.other.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/18
 * 描  述：
 */
public class SearchCityByKeyAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;

    private List<NationalCountryBean> searchTotalSearchList;

    private  CityDataCallBack cityDataCallBack;

    public  SearchCityByKeyAdapter(Activity activity,List<NationalCountryBean> searchTotalSearchList){

        this.activity = activity;

        this.searchTotalSearchList = searchTotalSearchList;

    }

    public void addNewList(List<NationalCountryBean> searchTotalSearchList){

        this.searchTotalSearchList.clear();

        this.searchTotalSearchList.addAll(searchTotalSearchList);

        notifyDataSetChanged();

    }

    public void setCityDataCallBack(CityDataCallBack cityDataCallBack)
    {
        this.cityDataCallBack = cityDataCallBack;
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
        return searchTotalSearchList.size();
    }

    class CurrentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItemName;

        private LinearLayout llCity;

        private View itemView;

        public CurrentViewHolder(View itemView)
        {
            super(itemView);

            this.itemView = itemView;

            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);

            llCity = (LinearLayout) itemView.findViewById(R.id.llCity);

            llCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(cityDataCallBack != null){

                        NationalCountryBean  bean = (NationalCountryBean) v.getTag();

                        cityDataCallBack.selectedCityItem(bean);
                    }
                }
            });

        }

        public void initData(int position)
        {
            NationalCountryBean  bean = searchTotalSearchList.get(position);

           tvItemName.setText(bean.getCityName());

            llCity.setTag(bean);
        }
    }

   public interface  CityDataCallBack{

        void selectedCityItem( NationalCountryBean  bean);

    }
}
