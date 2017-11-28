package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;

import java.util.List;
import java.util.Map;

/**
 * Created by yinsg on 2016/5/27.
 */
public class TrainTimeTableListAdapter extends BaseAdapter {

    private Context context;
    private List<Map> list;

    public TrainTimeTableListAdapter(Context context, List<Map> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.train_time_list_item,null);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        Map<String, Object> map = list.get(position);

        int type = Integer.parseInt(String.valueOf(map.get("type")));

        if (0 == position) {
            holder.trainName.setText(String.valueOf(map.get("trainName")));
            holder.reachTime.setText("--");
            holder.startTime.setText(String.valueOf(map.get("startTime")));
            holder.dueTime.setText("--");

            holder.trainName.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));
            holder.reachTime.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));
            holder.startTime.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));
            holder.dueTime.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));

            if(0 == type) {
                holder.middleImg.setBackgroundResource(R.mipmap.icon_radio_default);
                holder.afterView.setBackgroundResource(R.color.train_c8c8c8);
            }

            if(1 == type) {
                holder.middleImg.setBackgroundResource(R.drawable.icon_round_red2);
                holder.afterView.setBackgroundResource(R.color.train_d84043);

                holder.trainName.setTextColor(context.getResources().getColor(R.color.train_d84043));
                holder.startTime.setTextColor(context.getResources().getColor(R.color.train_d84043));
            }

            holder.beforeView.setVisibility(View.INVISIBLE);
            holder.afterView.setVisibility(View.VISIBLE);
        } else if(position == list.size() - 1) {
            holder.trainName.setText(String.valueOf(map.get("trainName")));
            holder.reachTime.setText(String.valueOf(map.get("reachTime")));
            holder.startTime.setText("--");
            holder.dueTime.setText("--");

            holder.trainName.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));
            holder.reachTime.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));
            holder.startTime.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));
            holder.dueTime.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));

            if(0 == type) {
                holder.beforeView.setBackgroundResource(R.color.train_c8c8c8);
                holder.middleImg.setBackgroundResource(R.mipmap.icon_radio_default);
            }

            if(3 == type) {
                holder.beforeView.setBackgroundResource(R.color.train_e69c9e);
                holder.middleImg.setBackgroundResource(R.drawable.icon_round_red2);

                holder.trainName.setTextColor(context.getResources().getColor(R.color.train_d84043));
                holder.reachTime.setTextColor(context.getResources().getColor(R.color.train_d84043));
            }

            holder.beforeView.setVisibility(View.VISIBLE);
            holder.afterView.setVisibility(View.INVISIBLE);
        } else {
            holder.trainName.setText(String.valueOf(map.get("trainName")));
            holder.reachTime.setText(String.valueOf(map.get("reachTime")));
            holder.startTime.setText(String.valueOf(map.get("startTime")));
            holder.dueTime.setText(String.valueOf(map.get("dueTime")));

            holder.trainName.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));
            holder.reachTime.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));
            holder.startTime.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));
            holder.dueTime.setTextColor(context.getResources().getColor(R.color.train_3f3f3f));

            if(0 == type) {
                holder.beforeView.setBackgroundResource(R.color.train_c8c8c8);
                holder.middleImg.setBackgroundResource(R.mipmap.icon_radio_default);
                holder.afterView.setBackgroundResource(R.color.train_c8c8c8);
            }

            if(1 == type) {
                holder.beforeView.setBackgroundResource(R.color.train_c8c8c8);
                holder.middleImg.setBackgroundResource(R.drawable.icon_round_red2);
                holder.afterView.setBackgroundResource(R.color.train_e69c9e);
            }

            if(2 == type) {
                holder.beforeView.setBackgroundResource(R.color.train_e69c9e);
                holder.middleImg.setBackgroundResource(R.drawable.icon_round_red2);
                holder.afterView.setBackgroundResource(R.color.train_e69c9e);
            }

            if(3 == type) {
                holder.beforeView.setBackgroundResource(R.color.train_e69c9e);
                holder.middleImg.setBackgroundResource(R.drawable.icon_round_red2);
                holder.afterView.setBackgroundResource(R.color.train_c8c8c8);
            }

            holder.beforeView.setVisibility(View.VISIBLE);
            holder.afterView.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
    class ViewHolder{
        public View beforeView;
        public ImageView middleImg;
        public View afterView;

        public TextView trainName;
        public TextView reachTime;
        public TextView startTime;
        public TextView dueTime;

        public ViewHolder(View parent){
            beforeView = parent.findViewById(R.id.beforeView);
            middleImg = (ImageView) parent.findViewById(R.id.middleImg);
            afterView = parent.findViewById(R.id.afterView);

            trainName = (TextView) parent.findViewById(R.id.trainName);
            reachTime = (TextView) parent.findViewById(R.id.reachTime);
            startTime = (TextView) parent.findViewById(R.id.startTime);
            dueTime = (TextView) parent.findViewById(R.id.dueTime);
        }
    }
}
