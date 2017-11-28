package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.CommonUtil;

/**
 * 功能：旅游行程安排；
 *
 * @author:gengqiyun
 * @date: 2016/11/19
 */
public class RouteScheduleAdapter extends BaseListAdapter<TravelLineBean.Schedule>
{
    public RouteScheduleAdapter(Context context)
    {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_listview_route_schedue, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TravelLineBean.Schedule routeScheBean = getItem(position);

        viewHolder.tvDay.setText("第" + CommonUtil.numberToStr(routeScheBean.getScheduleDay()) + "天");
        viewHolder.tvTravelSummay.setText(routeScheBean.getScheduleName());

        TravelDayTimeAdapter dayTimeAdapter = new TravelDayTimeAdapter(mContext);
        dayTimeAdapter.clearAll();
        dayTimeAdapter.appendALL(routeScheBean.getPlanList());
        viewHolder.dayTimeListView.setAdapter(dayTimeAdapter);

        return viewHolder.root;
    }

    public class ViewHolder
    {
        public final TextView tvDay;
        public final TextView tvTravelSummay;
        public final WholeListView dayTimeListView;
        public final View root;

        public ViewHolder(View root)
        {
            tvDay = (TextView) root.findViewById(R.id.tvDay);
            tvTravelSummay = (TextView) root.findViewById(R.id.tvTravelSummay);
            dayTimeListView = (WholeListView) root.findViewById(R.id.dayTimeListView);
            this.root = root;
        }
    }
}
