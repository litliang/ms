package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.ui.base.BaseRecyAdapter;
import com.yzb.card.king.util.CommonUtil;

import java.util.List;

/**
 * 功能：交通工具
 *
 * @author:gengqiyun
 * @date: 2016/11/19
 */
public class TrafficAdapter extends BaseRecyAdapter<TravelLineBean.Traffic>
{
    public TrafficAdapter(Context context)
    {
        super(context);
    }

    public TrafficAdapter(List<TravelLineBean.Traffic> trafficBeans, Context context)
    {
        super(trafficBeans, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.item_travel_detail_traffic, parent, false);
        TrafficViewHolder trafficViewHolder = new TrafficViewHolder(itemView);
        return trafficViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        TrafficViewHolder viewHolder = (TrafficViewHolder) holder;
        final TravelLineBean.Traffic trafficBean = mList.get(position);
        viewHolder.tvTrafficType.setText(trafficBean.getTrafficName());
        viewHolder.tvTrafficTime.setText(CommonUtil.formatMmToHh(trafficBean.getTrafficLength()));

        viewHolder.veticalDivider.setVisibility(position == mList.size() - 1 ? View.GONE : View.VISIBLE);
    }

    class TrafficViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvTrafficType;
        public TextView tvTrafficTime;
        public View veticalDivider;

        public TrafficViewHolder(View itemView)
        {
            super(itemView);
            tvTrafficType = (TextView) itemView.findViewById(R.id.tvTrafficType);
            tvTrafficTime = (TextView) itemView.findViewById(R.id.tvTrafficTime);
            veticalDivider = itemView.findViewById(R.id.veticalDivider);
        }
    }

}
