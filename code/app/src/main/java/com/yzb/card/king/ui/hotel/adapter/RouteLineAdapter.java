/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.yzb.card.king.ui.hotel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.yzb.card.king.R;
import com.yzb.card.king.util.Utils;

import java.util.List;

/**
 * 路线规划；
 *
 * @author gengqiyun
 * @date 2016.5.26
 */
public class RouteLineAdapter extends BaseAdapter
{

    private List<? extends RouteLine> routeLines;
    private LayoutInflater layoutInflater;
    private Type mtype;

    public RouteLineAdapter(Context context, List<? extends RouteLine> routeLines, Type type)
    {
        this.routeLines = routeLines;
        layoutInflater = LayoutInflater.from(context);
        mtype = type;
    }

    @Override
    public int getCount()
    {
        return routeLines == null ? 0 : routeLines.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        NodeViewHolder holder;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.activity_transit_item, null);
            holder = new NodeViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (NodeViewHolder) convertView.getTag();
        }
        switch (mtype)
        {
            case TRANSIT_ROUTE:
                holder.name.setText("路线" + (position + 1));
                int time = routeLines.get(position).getDuration();
                if (time / 3600 == 0)
                {
                    holder.lightNum.setText("大约需要：" + time / 60 + "分钟");
                } else
                {
                    holder.lightNum.setText("大约需要：" + time / 3600 + "小时" + (time % 3600) / 60 + "分钟");
                }
                holder.dis.setText("距离大约是：" + Utils.subZeroAndDot(routeLines.get(position).getDistance()/1000.0 + "") + "km");
                break;

            case DRIVING_ROUTE:
                DrivingRouteLine drivingRouteLine = (DrivingRouteLine) routeLines.get(position);
                holder.name.setText("线路" + (position + 1));
                holder.lightNum.setText("红绿灯数：" + drivingRouteLine.getLightNum());
                holder.dis.setText("拥堵距离为：" + drivingRouteLine.getCongestionDistance() + "米");
                break;
            default:
        }
        return convertView;
    }

    public class NodeViewHolder
    {
        public final TextView name;
        public final TextView lightNum;
        public final TextView dis;
        public final View root;

        public NodeViewHolder(View root)
        {
            name = (TextView) root.findViewById(R.id.transitName);
            lightNum = (TextView) root.findViewById(R.id.lightNum);
            dis = (TextView) root.findViewById(R.id.dis);
            this.root = root;
        }
    }

    public enum Type
    {
        TRANSIT_ROUTE, // 公交
        DRIVING_ROUTE // 驾车
    }
}
