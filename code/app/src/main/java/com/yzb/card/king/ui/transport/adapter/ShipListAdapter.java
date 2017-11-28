package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.transport.bean.ShipTicket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinsg on 2016/5/30.
 */
public class ShipListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<ShipTicket> dataList;
    private LayoutInflater inflater = null;
    private ICallBack callBack;

    public ShipListAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    public List<ShipTicket> getDataList()
    {
        return dataList;
    }

    public void appendData(List<ShipTicket> dataList)
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
        View itemView = inflater.inflate(R.layout.transport_ship_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            ShipTicket shipTicket = dataList.get(position);
            viewHolder.tvStartTime.setText(shipTicket.startTime);
            viewHolder.tvEndTime.setText(shipTicket.endTime);
            viewHolder.tvStartStation.setText(shipTicket.startShipName);
            viewHolder.tvEndStation.setText(shipTicket.endShipName);
            viewHolder.tvPrice.setText(shipTicket.price + "");
            viewHolder.tvSeatNum.setText(shipTicket.allowance + "张");
            viewHolder.tvShipName.setText(shipTicket.shipName);
            viewHolder.tvSeatType.setText(shipTicket.shipSeatType);

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
        public TextView tvStartTime;
        public TextView tvEndTime;
        public TextView tvStartStation;
        public TextView tvEndStation;
        public TextView tvSeatNum;
        public TextView tvShipName;
        public TextView tvPrice;
        public TextView tvSeatType;
        public View root;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvStartTime = (TextView) itemView.findViewById(R.id.tvStartTime);
            tvEndTime = (TextView) itemView.findViewById(R.id.tvEndTime);
            tvStartStation = (TextView) itemView.findViewById(R.id.tvStartStation);
            tvEndStation = (TextView) itemView.findViewById(R.id.tvEndStation);
            tvSeatNum = (TextView) itemView.findViewById(R.id.tvSeatNum);
            tvShipName = (TextView) itemView.findViewById(R.id.tvShipName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvSeatType = (TextView) itemView.findViewById(R.id.tvSeatType);
            root = itemView;
        }
    }
}
