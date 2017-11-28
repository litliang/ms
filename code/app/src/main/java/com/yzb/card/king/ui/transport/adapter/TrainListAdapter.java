package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dev on 2016/5/6.
 * 火车票适配；
 */
public class TrainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<Map> dataList = null;
    private LayoutInflater inflater = null;
    private ICallBack callBack;

    public TrainListAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    public List<Map> getDataList()
    {
        return dataList;
    }

    public void appendData(List<Map> dataList)
    {
        if (dataList == null)
        {
            return;
        }
        if (this.dataList == null)
        {
            this.dataList = new ArrayList<>();
        }
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clear()
    {
        if (this.dataList != null)
        {
            this.dataList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.train_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            Map<String, Object> map = dataList.get(position);

            viewHolder.trainNumber.setText(map.get("trainNumber").toString());
            viewHolder.startTime.setText(map.get("startTime").toString().substring(11, 16));
            viewHolder.endTime.setText(map.get("endTime").toString().substring(11, 16));

            viewHolder.timeLength.setText(map.get("timeLength").toString());
            viewHolder.startTrainName.setText(map.get("startTrainName").toString());
            viewHolder.endTrainName.setText(map.get("endTrainName").toString());

            viewHolder.price.setText(map.get("price").toString());
            viewHolder.seatType.setText(map.get("seatType").toString());
            if (map.get("allowance").toString().equals("0"))
            {
                viewHolder.allowance.setText("无票");
            } else
            {
                viewHolder.allowance.setText(map.get("allowance").toString() + "张");
            }

            viewHolder.root.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (callBack != null)
                    {
                        callBack.callBack(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return dataList == null ? 0 : dataList.size();
    }

    public void setOnItemClickListener(ICallBack callBack)
    {
        this.callBack = callBack;
    }


    public interface ICallBack
    {
        void callBack(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView startTime;
        public TextView endTime;
        public TextView timeLength;
        public TextView startTrainName;
        public TextView endTrainName;
        public TextView price;
        public TextView seatType;
        public TextView allowance;
        public TextView trainNumber;
        public View root;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            trainNumber = (TextView) itemView.findViewById(R.id.tv_train_number);
            startTime = (TextView) itemView.findViewById(R.id.tv_start_time);
            endTime = (TextView) itemView.findViewById(R.id.tv_end_time);

            timeLength = (TextView) itemView.findViewById(R.id.tv_timelength);
            startTrainName = (TextView) itemView.findViewById(R.id.tv_start_trainname);
            endTrainName = (TextView) itemView.findViewById(R.id.tv_end_trainname);

            price = (TextView) itemView.findViewById(R.id.tv_price);
            seatType = (TextView) itemView.findViewById(R.id.tv_seattype);
            allowance = (TextView) itemView.findViewById(R.id.tv_allowance);
            root = itemView;
        }
    }
}
