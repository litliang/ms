package com.yzb.card.king.ui.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.HistoryRecordView;
import com.yzb.card.king.ui.base.BaseRecyAdapter;
import com.yzb.card.king.util.CommonUtil;

import java.util.List;

/**
 * 功能：搜索历史记录；
 *
 * @author:gengqiyun
 * @date: 2016/10/27
 */
public class HistoryRecordAdapter extends BaseRecyAdapter<String>
{
    private int itemWith = 0;
    private HistoryRecordView.HistoryItemListener listener;

    public HistoryRecordAdapter(Context context)
    {
        super(context);
        itemWith = CommonUtil.getScreenWidth(context) / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        TextView rootView = (TextView) inflater.inflate(R.layout.item_recyle_history, null);
        rootView.setWidth(itemWith);
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

            viewHolder.tv_title.setOnClickListener(new View.OnClickListener()
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

        public MyViewHolder(TextView itemView)
        {
            super(itemView);
            tv_title = itemView;
        }
    }
}
