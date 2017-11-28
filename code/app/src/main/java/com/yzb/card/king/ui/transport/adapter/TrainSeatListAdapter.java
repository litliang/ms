package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;

import java.util.List;
import java.util.Map;

/**
 * Created by yinsg on 2016/5/27.
 */
public class TrainSeatListAdapter extends BaseAdapter
{
    private LayoutInflater inflater;
    private Context context;
    private List<Map> list;
    private View.OnClickListener advanceOnClickListener = null;

    public TrainSeatListAdapter(Context context, List<Map> list, View.OnClickListener advanceOnClickListener)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.advanceOnClickListener = advanceOnClickListener;
    }

    @Override
    public int getCount()
    {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.train_seat_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(new ViewHolder(convertView));
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Map<String, Object> map = list.get(position);
        holder.tvSeatTypeName.setText(String.valueOf(map.get("seatName")));

        holder.agentLayout.removeAllViews();

        String supplierList = String.valueOf(map.get("supplierList"));
        if (!TextUtils.isEmpty(supplierList))
        {
            List<Map> agentList = JSON.parseArray(supplierList, Map.class);

            LinearLayout agentItem;
            TextView tvAgentName;
            TextView tvExplain;
            TextView tvPrice;
            TextView tvAllowance;
            TextView tvAdvance;
            for (final Map agentMap : agentList)
            {
                agentItem = (LinearLayout) inflater.inflate(R.layout.train_seat_agent_item, null);

                tvAgentName = (TextView) agentItem.findViewById(R.id.tvAgentName);
                tvExplain = (TextView) agentItem.findViewById(R.id.tvExplain);
                tvPrice = (TextView) agentItem.findViewById(R.id.tvPrice);
                tvAllowance = (TextView) agentItem.findViewById(R.id.tvAllowance);
                tvAdvance = (TextView) agentItem.findViewById(R.id.tvAdvance);

                tvAgentName.setText(String.valueOf(agentMap.get("supplierName")));
                if (String.valueOf(agentMap.get("supplierName")).equals(context.getResources().getString(R.string.train_cardking)))
                    tvExplain.setText(context.getResources().getString(R.string.train_agent_explain));
                else
                    tvExplain.setText(String.valueOf(agentMap.get("allowance")));
                tvPrice.setText(String.valueOf(agentMap.get("price")));
                tvAllowance.setText(String.valueOf(agentMap.get("allowance")) + context.getResources().getString(R.string.train_zhang));

                tvAdvance.setTag(R.id.train_seat_price, String.valueOf(agentMap.get("price")));
                tvAdvance.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        v.setTag(agentMap);
                        advanceOnClickListener.onClick(v);
                    }
                });

                holder.agentLayout.addView(agentItem);
            }
            holder.agentLayout.setTag(0);
        }
        return convertView;
    }

    class ViewHolder
    {
        public TextView tvSeatTypeName;
        public ImageView imgItem;
        public LinearLayout agentLayout;

        public ViewHolder(View parent)
        {
            tvSeatTypeName = (TextView) parent.findViewById(R.id.tvSeatTypeName);
            imgItem = (ImageView) parent.findViewById(R.id.imgItem);
            agentLayout = (LinearLayout) parent.findViewById(R.id.agentLayout);
        }
    }
}
