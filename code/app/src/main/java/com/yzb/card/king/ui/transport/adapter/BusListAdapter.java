package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.transport.bean.BusTicket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinsg on 2016/5/27.
 */
public class BusListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<BusTicket> dataList = null;
    private LayoutInflater inflater = null;
    private IAdapterClickCallBack callBack;

    public BusListAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    public List<BusTicket> getDataList()
    {
        return dataList;
    }

    public void appendData(List<BusTicket> dataList)
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
        View itemView = inflater.inflate(R.layout.transport_bus_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            BusTicket busTicket = dataList.get(position);

            viewHolder.tvDepartTime.setText(busTicket.departTime);
            viewHolder.tvStartPlace.setText(busTicket.departStationName);
            viewHolder.tvEndPlace.setText(busTicket.reachStationName);
            viewHolder.tvPrice.setText(busTicket.price);

            String seatNumText;
            String seatNum = busTicket.seatNum;
            if (TextUtils.isEmpty(seatNum) || (TextUtils.isDigitsOnly(seatNum) && Integer.parseInt(seatNum) <= 0))
            {
                seatNumText = "无票";
            } else
            {
                seatNumText = seatNum + "张";
            }
            viewHolder.tvSeatNum.setText(seatNumText);
            viewHolder.tvDuration.setText("约" + busTicket.duration + "小时");

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

    public void setOnItemClickListener(IAdapterClickCallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface IAdapterClickCallBack
    {
        void callBack(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public View root;
        public TextView tvStartPlace;
        public TextView tvDepartTime;
        public TextView tvEndPlace;
        public TextView tvPrice;
        public TextView tvSeatNum;
        public TextView tvDuration;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvDepartTime = (TextView) itemView.findViewById(R.id.tv_depart_time);
            tvStartPlace = (TextView) itemView.findViewById(R.id.tv_start_place);
            tvEndPlace = (TextView) itemView.findViewById(R.id.tv_end_place);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvSeatNum = (TextView) itemView.findViewById(R.id.tv_seat_num);
            tvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            root = itemView;
        }
    }
}
