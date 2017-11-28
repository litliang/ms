package com.yzb.card.king.ui.order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.RescheCondition;
import com.yzb.card.king.ui.base.BaseListAdapter;

/**
 * 作者： gengqiyun；
 * 日期： 2016-12-3
 * 描述：改签详情  更改条件；
 */
public class ReschedueConditionAdapter extends BaseListAdapter<RescheCondition>
{
    public ReschedueConditionAdapter(Context context)
    {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_reschedu_condition, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final RescheCondition condition = getItem(position);

//        viewHolder.ivClock.setImageResource(true ? R.mipmap.time_all_blue : R.mipmap.icon_all_time);
        viewHolder.ivClock.setImageResource(R.mipmap.icon_all_time);

        viewHolder.tvTime.setText(condition.getTimeIntro());
        viewHolder.tvPrice.setText("¥" + condition.getPrice() + "/人");

        viewHolder.root.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                condition.setIsSelect(!condition.isSelect());
                notifyDataSetChanged();
            }
        });
        return viewHolder.root;
    }

    public class ViewHolder
    {
        public final ImageView ivClock;
        public final TextView tvTime;
        public final TextView tvPrice;
        public final View root;

        public ViewHolder(View root)
        {
            ivClock = (ImageView) root.findViewById(R.id.ivClock);
            tvTime = (TextView) root.findViewById(R.id.tvTime);
            tvPrice = (TextView) root.findViewById(R.id.tvPrice);
            this.root = root;
        }
    }
}