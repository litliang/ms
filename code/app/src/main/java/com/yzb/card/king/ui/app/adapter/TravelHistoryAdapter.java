package com.yzb.card.king.ui.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.HistoryRecordView;
import com.yzb.card.king.ui.base.BaseRecyAdapter;

/**
 * 功能：旅游目的地搜索历史记录；
 *
 * @author:gengqiyun
 * @date: 2016/11/25
 */
public class TravelHistoryAdapter extends BaseRecyAdapter<String>
{
    private HistoryRecordView.HistoryItemListener listener;

    public TravelHistoryAdapter(Context context)
    {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View rootView = inflater.inflate(R.layout.item_travel_recyle_history, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            final String keyword = mList.get(position);
            viewHolder.tv_title.setText(TextUtils.isEmpty(keyword) ? "" : keyword);

            viewHolder.root.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        listener.callBack(keyword);
                    }
                }
            });
        }
    }

    public void setItemListener(HistoryRecordView.HistoryItemListener listener)
    {
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tv_title;
        public View root;

        public MyViewHolder(View root)
        {
            super(root);
            this.root = root;
            tv_title = (TextView) root.findViewById(R.id.tv_title);
        }
    }
}
