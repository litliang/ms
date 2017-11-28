package com.yzb.card.king.ui.hotel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.giftcard.ApplySoreModelBean;
import com.yzb.card.king.bean.giftcard.GoodsApplyStoreBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：使用门店城市适配器
 * 作  者：Li Yubing
 * 日  期：2017/9/9
 * 描  述：
 */
public class ApplyShopCityItemAdapter extends RecyclerView.Adapter{

    private List<GoodsApplyStoreBean> listBean = new ArrayList<GoodsApplyStoreBean>();


    public  ApplyShopCityItemAdapter(){

    }

    public void addNewList(List<GoodsApplyStoreBean> listBean){
        this.listBean.clear();

        this.listBean.addAll(listBean);

        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_apply_shop, parent, false);

        return new MyApplyShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if( holder instanceof  MyApplyShopViewHolder){

            MyApplyShopViewHolder myApplyShopViewHolder = (MyApplyShopViewHolder) holder;

            GoodsApplyStoreBean tempBean = listBean.get(position);

            myApplyShopViewHolder.tvStoreName.setText(tempBean.getStoreName());

            myApplyShopViewHolder.tvTel.setText(tempBean.getStoreTel());

            myApplyShopViewHolder.tvStoreAddress.setText("地址："+tempBean.getStoreAddr());


        }


    }

    @Override
    public int getItemCount()
    {
        return listBean.size();
    }

    public static class MyApplyShopViewHolder extends RecyclerView.ViewHolder{

        private TextView tvStoreName;

        private  TextView tvTel;

        private  TextView tvStoreAddress;

        public MyApplyShopViewHolder(View itemView)
        {
            super(itemView);

            tvStoreName = (TextView) itemView.findViewById(R.id.tvStoreName);

            tvTel = (TextView) itemView.findViewById(R.id.tvTel);

            tvStoreAddress = (TextView) itemView.findViewById(R.id.tvStoreAddress);
        }
    }
}
