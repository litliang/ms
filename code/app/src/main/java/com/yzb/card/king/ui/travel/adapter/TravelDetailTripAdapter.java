package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseRecyAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/14
 * 描  述：
 */
public class TravelDetailTripAdapter extends BaseRecyAdapter<Map>
{
    public TravelDetailTripAdapter(Context context, List<Map> list)
    {
        super(list, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(inflater.inflate(R.layout.travel_trip_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof ViewHolder)
        {
            ViewHolder viewHolder = (ViewHolder) holder;
            Map map = mList.get(position);

            viewHolder.txDay.setText("第" + map.get("scheduleDay") + "天");
            viewHolder.itemLin.setVisibility(position == mList.size() - 1 ? View.GONE : View.VISIBLE);

            if (position == 0)
            {
                viewHolder.itemTop.setBackgroundResource(R.color.discount_29292b);
            }
            viewHolder.txContent.setText(map.get("scheduleName") + "");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @ViewInject(R.id.item_view_trip)
        View itemLin;
        @ViewInject(R.id.item_top)
        View itemTop;
        @ViewInject(R.id.date_info)
        TextView txDay;
        @ViewInject(R.id.content_trip)
        TextView txContent;

        public ViewHolder(View itemView)
        {
            super(itemView);
            x.view().inject(this, itemView);
        }
    }
}
