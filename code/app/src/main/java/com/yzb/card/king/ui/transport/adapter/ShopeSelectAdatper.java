package com.yzb.card.king.ui.transport.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.discount.bean.BusShopBean;

import java.util.List;

/**
 * 类名： ShopeSelectAdatper
 * 作者： Lei Chao.
 * 日期： 2016-09-07
 * 描述： 附近门店适配器
 */
public class ShopeSelectAdatper extends RecyclerView.Adapter
{

    private List<BusShopBean> busShopList;

    private Activity mContext;

    private LayoutInflater inflater;

    private String str = "";

    public ShopeSelectAdatper(List<BusShopBean> busShopLists, Activity context)
    {
        this.busShopList = busShopLists;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        str = mContext.getString(R.string.tv_selfdrive_shop_desc);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.view_adapter_shopselectfragment, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        final MyViewHolder viewHold = (MyViewHolder) holder;

        BusShopBean busShopBean = this.busShopList.get(position);

        viewHold.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mOnItemClickListener != null)
                {
                    mOnItemClickListener.onItemClick((Object) busShopList.get(viewHold.getLayoutPosition()));
                }
            }
        });

        viewHold.tvRentCar.setText(busShopBean.storeName);

        viewHold.tvShopName.setText(busShopBean.shopName);

        float a = Float.parseFloat(busShopBean.vote);
        float b = a / 2;
        viewHold.rbEstimate.setStarMarkAndSore(b);

        viewHold.tvEstimate.setText(a + mContext.getString(R.string.tv_selfdrive_score));

        viewHold.tvRise.setText(mContext.getString(R.string.tv_selfdrive_money_unit) + busShopBean.price);

        String tvDesc = str.replace("##", busShopBean.distance);

        tvDesc = tvDesc.replace("@@@", busShopBean.carType);

        viewHold.tvDesc.setText(tvDesc);

        viewHold.tvaVerageDaily.setText(mContext.getString(R.string.tv_selfdrive_average_daily) + busShopBean.rentalCount);
    }

    @Override
    public int getItemCount()
    {
        return this.busShopList.size() == 0 ? 0 : this.busShopList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvRentCar;

        TextView tvShopName;

        StarBar rbEstimate;

        TextView tvEstimate;

        TextView tvDesc;

        TextView tvRise;

        TextView tvaVerageDaily;

        public MyViewHolder(View convertView)
        {
            super(convertView);
            rbEstimate = (StarBar) convertView.findViewById(R.id.rbEstimate);

            tvRentCar = (TextView) convertView.findViewById(R.id.tvRentCar);

            tvShopName = (TextView) convertView.findViewById(R.id.tvShopName);

            tvEstimate = (TextView) convertView.findViewById(R.id.tvEstimate);

            tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);

            tvRise = (TextView) convertView.findViewById(R.id.tvRise);

            tvaVerageDaily = (TextView) convertView.findViewById(R.id.tvaVerageDaily);
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(Object o);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }

}