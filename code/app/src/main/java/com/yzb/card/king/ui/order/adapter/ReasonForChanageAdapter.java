package com.yzb.card.king.ui.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.ReasonForChangeBean;
import com.yzb.card.king.ui.base.BaseRecyAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 类名： ReasonForChanageAdapter
 * 作者： Lei Chao.
 * 日期： 2016-10-12
 * 描述： 退签原因适配器
 */
public class ReasonForChanageAdapter extends BaseRecyAdapter<ReasonForChangeBean>
{

    public ReasonForChanageAdapter(List<ReasonForChangeBean> dataList, Context context)
    {
        super(dataList, context);
    }

    /**
     * 获取选中的item；
     *
     * @return
     */
    public ReasonForChangeBean getSelectItem()
    {
        for (int i = 0; i < mList.size(); i++)
        {
            if (mList.get(i).isSelect())
            {
                return mList.get(i);
            }
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.item_refund_reason, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        MyViewHolder vh = (MyViewHolder) holder;
        final ReasonForChangeBean data = mList.get(position);
        vh.tv.setText(data.getContent());

        vh.iv.setSelected(data.isSelect());

        vh.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!data.isSelect())
                {
                    selectItem(data);
                    notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 单选item；
     *
     * @param data
     */
    private void selectItem(ReasonForChangeBean data)
    {
        if (data != null)
        {
            for (int i = 0; i < mList.size(); i++)
            {
                mList.get(i).setIsSelect(mList.get(i).getId() == data.getId());
            }
            notifyDataSetChanged();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        @ViewInject(R.id.tv)
        public TextView tv;
        @ViewInject(R.id.iv)
        public ImageView iv;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            x.view().inject(this, itemView);
        }
    }
}