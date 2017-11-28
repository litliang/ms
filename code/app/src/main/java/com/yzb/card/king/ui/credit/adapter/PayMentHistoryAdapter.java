package com.yzb.card.king.ui.credit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/12/1
 * 描  述：
 */
public class PayMentHistoryAdapter extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Map> list;

    public PayMentHistoryAdapter(Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(inflater.inflate(R.layout.payment_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof ViewHolder)
        {
            ViewHolder vh = (ViewHolder) holder;
            Map map = list.get(position);
            LogUtil.i("mapinfo "+ JSON.toJSONString(map));
            Set<String> key = map.keySet();
            for (String names : key)
            {
                vh.txTime.setText(names);
                List<Map> child = (List<Map>) map.get(names);
                LogUtil.i("keyvalue " + names);
                PaymentHistoryItemAdapter adapter = new PaymentHistoryItemAdapter(context,child);
                vh.listView.setAdapter(adapter);
            }



//

        }
    }

    public void setList(List<Map> list)
    {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setOldList(List<Map> list)
    {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txTime;
        ListView listView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            txTime = (TextView) itemView.findViewById(R.id.time_info);
            listView = (ListView) itemView.findViewById(R.id.listInfo);
        }
    }
}
