package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.DateBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.TestActivity;
import com.yzb.card.king.ui.base.BaseRecyAdapter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import java.util.List;

/**
 * 功能：旅游详情 日期列表；
 *
 * @author:gengqiyun
 * @date: 2016/11/17
 */
public class TravelDateAdapter extends BaseRecyAdapter<DateBean>
{
    private RecyclerView attachRecyView;
    private int selectItem = -1;

    public TravelDateAdapter(Context context)
    {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.item_travel_date, parent, false);
        DateViewHolder lineViewHolder = new DateViewHolder(itemView);
        return lineViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        DateViewHolder viewHolder = (DateViewHolder) holder;
        DateBean dateBean = mList.get(position);

        viewHolder.panelBg.setSelected(dateBean.isselected());
        viewHolder.tvDateState.setSelected(dateBean.isselected());
        viewHolder.tvPrice.setSelected(dateBean.isselected());
        viewHolder.tvTicketNum.setSelected(dateBean.isselected());

        //日期和周次（节日）；拼接；
        viewHolder.tvDateState.setText(dateBean.getMMddFormatDepDate() + "  " + dateBean.getDepDateDesc());
        viewHolder.tvPrice.setText("¥" + Utils.subZeroAndDot(dateBean.getPrice() + ""));

        int ticketNum = dateBean.getInventoryQuantity();
        int bgColor = ticketNum < 10 && ticketNum > 0 ? Color.WHITE : Color.TRANSPARENT;

        viewHolder.tvTicketNum.setBackgroundDrawable(new ColorDrawable(bgColor));
        viewHolder.tvTicketNum.setText(ticketNum < 10 && ticketNum > 0 ? "剩余" + ticketNum : "    ");

        //无票；
        if (ticketNum == 0)
        {
            viewHolder.tvDateState.setText(dateBean.getMMddFormatDepDate() + " 售完");
        }

        viewHolder.tvPrice.setVisibility(ticketNum == 0 ? View.GONE : View.VISIBLE);
        viewHolder.ivSellOut.setVisibility(ticketNum == 0 ? View.VISIBLE : View.GONE);

        holder.itemView.setClickable(ticketNum > 0 ? true : false);

        if (ticketNum > 0)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    selectItem(position);
                    attachRecyView.smoothScrollToPosition(position);
                }
            });
        }
    }


    /**
     * 选中item；
     *
     * @param position
     */
    private void selectItem(int position)
    {
        this.selectItem = position;
        DateBean dateBean = mList.get(position);

        for (int i = 0; i < mList.size(); i++)
        {
            if (mList.get(i).isselected())
            {
                mList.get(i).setIsselected(false);
                notifyItemChanged(i);
                break;
            }
        }
        dateBean.setIsselected(true);
        notifyItemChanged(position);
    }

    public DateBean getSelectItem()
    {
        if (mList != null)
        {
            for (int i = 0; i < mList.size(); i++)
            {
                if (mList.get(i).isselected())
                {
                    return mList.get(i);
                }
            }
        }
        return null;
    }

    public void setAttachRecyView(RecyclerView attachRecyView)
    {
        this.attachRecyView = attachRecyView;
    }

    /**
     * 初始化时选中某项；
     */
    public void selectItem()
    {
        LogUtil.i("选中的item=" + selectItem);
        if (mList.size() > 0)
        {
            int postion = selectItem < 0 ? 0 : selectItem;
            selectItem(postion);
        }
    }

    class DateViewHolder extends RecyclerView.ViewHolder
    {
        public View panelBg;
        public View panelTop;
        public View ivSellOut;
        public TextView tvDateState;
        public TextView tvPrice;
        public TextView tvTicketNum;

        public DateViewHolder(View itemView)
        {
            super(itemView);
            ivSellOut = itemView.findViewById(R.id.ivSellOut);
            panelTop = itemView.findViewById(R.id.panelTop);
            panelBg = itemView.findViewById(R.id.panelBg);
            tvDateState = (TextView) itemView.findViewById(R.id.tvDateState);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvTicketNum = (TextView) itemView.findViewById(R.id.tvTicketNum);
        }
    }

}
