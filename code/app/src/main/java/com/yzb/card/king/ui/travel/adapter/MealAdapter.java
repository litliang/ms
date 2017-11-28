package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.ui.base.BaseRecyAdapter;

import java.util.List;

/**
 * 功能：交通工具
 *
 * @author:gengqiyun
 * @date: 2016/11/19
 */
public class MealAdapter extends BaseRecyAdapter<TravelLineBean.Meal>
{
    public MealAdapter(List<TravelLineBean.Meal> trafficBeans, Context context)
    {
        super(trafficBeans, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.item_travel_detail_meal, parent, false);
        TrafficViewHolder trafficViewHolder = new TrafficViewHolder(itemView);
        return trafficViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        TrafficViewHolder viewHolder = (TrafficViewHolder) holder;
        final TravelLineBean.Meal meal = mList.get(position);

        //用餐方式	1酒店内、2自理、3团餐
        String mode = meal.getMealsMode();
        String text = "";
        switch (mode)
        {
            case "1":
                text = "酒店内";
                break;
            case "2":
                text = "自理";
                break;
            case "3":
                text = "团餐";
                break;
        }
        viewHolder.tvTrafficType.setText(text);
        viewHolder.tvTrafficTime.setText(meal.getMealsQuantity() + "次");

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
