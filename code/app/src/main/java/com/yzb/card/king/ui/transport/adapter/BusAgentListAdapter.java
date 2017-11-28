package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.transport.bean.BusAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinsg on 2016/5/27.
 * 汽车票代理商
 */
public class BusAgentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<BusAgent> dataList;
    private LayoutInflater inflater = null;
    public int priceType;
    private IAdapterClickCallBack callBack;

    public BusAgentListAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    public List<BusAgent> getDataList()
    {
        return dataList;
    }

    public void appendData(List<BusAgent> dataList)
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
        View itemView = inflater.inflate(R.layout.ticket_agent_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            BusAgent map = dataList.get(position);
            float price = map.price;
            viewHolder.price.setText("¥" + price);
            viewHolder.shopName.setText(map.shopName);
            viewHolder.advance.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (callBack != null)
                        callBack.callBack(position);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return dataList == null ? 0 : dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView shopName;
        private final TextView price;
        private final TextView tvDetail;
        private final TextView advance;
        private final TextView priceType;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            shopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            advance = (TextView) itemView.findViewById(R.id.tv_advance);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
            tvDetail.setVisibility(View.GONE);
            priceType = (TextView) itemView.findViewById(R.id.tv_priceType);
            priceType.setVisibility(View.GONE);
        }
    }

    public void setOnItemClickListener(IAdapterClickCallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface IAdapterClickCallBack
    {
        void callBack(int position);
    }
}
