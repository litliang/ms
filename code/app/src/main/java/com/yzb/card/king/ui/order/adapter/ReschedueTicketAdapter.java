package com.yzb.card.king.ui.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类名： RefundApplicationAdapter
 * 作者： Lei Chao.
 * 日期： 2016-10-15
 * 描述：改签详情adapter；
 * <p/>
 * 第一次修改：gengqiyun  2016.12.2
 */
public class ReschedueTicketAdapter extends BaseListAdapter<TicketOrderDetBean.OrderInfoBean>
{
    private String flightType; //（单程：OW；往返：RT；多段：MT）
    private boolean isCombineProducts = false;//是否是组合套餐；组合套餐时，往返票捆绑关系；要退全退；

    public boolean isCombineProducts()
    {
        return isCombineProducts;
    }

    public void setFlightType(String flightType)
    {
        this.flightType = flightType;
    }

    public ReschedueTicketAdapter(Context context)
    {
        super(context);
    }

    @Override
    public void appendALL(List<TicketOrderDetBean.OrderInfoBean> orders)
    {
        super.appendALL(orders);
        //往返；判断组合套餐情况；
        if (AppConstant.TYPE_ROUND.equals(flightType) && orders != null && orders.size() == 2)
        {
            String firstOrderNo = orders.get(0).getOrderNo();
            String secondOrderNo = orders.get(1).getOrderNo();
            if (!TextUtils.isEmpty(firstOrderNo) && firstOrderNo.equals(secondOrderNo))
            {
                isCombineProducts = true;
                LogUtil.i("航空订单号相同,是组合套餐 firstOrderNo=" + firstOrderNo);
            }
        }
    }

    /**
     * 获取选中的机票；
     *
     * @return
     */
    public List<TicketOrderDetBean.OrderInfoBean> getSelectTicket()
    {
        if (mList != null)
        {
            List<TicketOrderDetBean.OrderInfoBean> orderInfos = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++)
            {
                if (mList.get(i).isSelect())
                {
                    orderInfos.add(mList.get(i));
                }
            }
            return orderInfos;
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        MyViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_modify_order_ticket, parent, false);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        viewHolder.tvIndex.setVisibility(AppConstant.TYPE_SINGLE.equals(flightType) ? View.GONE : View.VISIBLE);

        if (AppConstant.TYPE_ROUND.equals(flightType)) //往返；
        {
            viewHolder.tvIndex.setText(position == 0 ? "去" : "返");
            viewHolder.tvIndex.setBackgroundResource(0);
        } else if (AppConstant.TYPE_MULT.equals(flightType)) //多程；
        {
            viewHolder.tvIndex.setText((position + 1) + "");
            viewHolder.tvIndex.setBackgroundResource(R.drawable.icon_round_number_blue);
        }

        final TicketOrderDetBean.OrderInfoBean item = mList.get(position);

        //2016-12-17 06: 55: 00
        String startTime = item.getStartTime();
        Date date = DateUtil.string2Date(startTime, DateUtil.DATE_FORMAT_DATE_TIME);
        String newDate = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE_TIME3);

        viewHolder.startLine.setText(item.getStartCityName());
        viewHolder.endLine.setText(item.getEndCityName());

        //item是否选中；
        viewHolder.isSelected.setSelected(item.isSelect);
        viewHolder.panelBottom.setVisibility(item.isSelect ? View.VISIBLE : View.GONE);

        viewHolder.tvFlightIntro.setText(newDate + "起飞\n" + item.getAirwaysName() + item.getToolNumber() + item.getProduct());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                item.setIsSelect(!item.isSelect());
                notifyDataSetChanged();
            }
        });

        GuestAdapter adapter = new GuestAdapter(mContext, item);
        viewHolder.guestListView.setAdapter(adapter);
        return convertView;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvIndex;
        TextView startLine;
        TextView endLine;
        TextView tvFlightIntro;
        ImageView isSelected;
        View panelBottom;
        WholeListView guestListView;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            panelBottom = itemView.findViewById(R.id.panelBottom);
            tvIndex = (TextView) itemView.findViewById(R.id.tv_index);
            startLine = (TextView) itemView.findViewById(R.id.startLline);
            endLine = (TextView) itemView.findViewById(R.id.endLine);
            tvFlightIntro = (TextView) itemView.findViewById(R.id.tvFlightIntro);
            isSelected = (ImageView) itemView.findViewById(R.id.is_selected);
            guestListView = (WholeListView) itemView.findViewById(R.id.guestListView);
        }
    }


    /**
     * 乘客列表；
     */
    public class GuestAdapter extends BaseListAdapter<TicketOrderDetBean.TicketsListBean>
    {
        private TicketOrderDetBean.OrderInfoBean list;

        public GuestAdapter(Context context, TicketOrderDetBean.OrderInfoBean list)
        {
            super(context, list.getTicketsList());
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.item_reschedue_ticket, parent, false);
            }
            TextView tvGuestName = (TextView) convertView.findViewById(R.id.tvGuestName);
            ImageView ivSelect = (ImageView) convertView.findViewById(R.id.ivSelect);
            final TicketOrderDetBean.TicketsListBean itemBean = getItem(position);
            tvGuestName.setText(itemBean.getGuestName());

            ivSelect.setSelected(itemBean.isSelect || "3".equals(itemBean.getSturt()));

            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //已改签不选中，置灰；（0：正常 1： 改升   2：退票   3：改签   ，4购票成功，5购票失败）
                    if ("3".equals(itemBean.getSturt()))
                    {
                        toastCustom(R.string.ticket_has_upsign);
                    } else
                    {
                        changeSelection(list, itemBean);
                        notifyDataSetChanged();
                    }
                }
            });
            return convertView;
        }
    }

    /**
     * 切换选中状态；
     * 组合套餐；组合套餐时，往返票捆绑关系；要退全退；
     *
     * @param list
     * @param itemBean
     */
    private void changeSelection(TicketOrderDetBean.OrderInfoBean list, TicketOrderDetBean.TicketsListBean itemBean)
    {
        itemBean.setIsSelect(!itemBean.isSelect());

        if (list.getTicketsList() != null)
        {
            List<TicketOrderDetBean.TicketsListBean> ticks = list.getTicketsList();
            boolean hasSelectItem = false;
            for (int i = 0; i < ticks.size(); i++)
            {
                if (ticks.get(i).isSelect())
                {
                    hasSelectItem = true;
                    break;
                }
            }
            list.setIsSelect(hasSelectItem);
        }
        notifyDataSetChanged();
    }


    /**
     * 获取选中的乘机人；
     *
     * @return
     */
    public List<TicketOrderDetBean.TicketsListBean> getSelectPassengers()
    {
        List<TicketOrderDetBean.TicketsListBean> tickets = new ArrayList<>();
        if (mList != null)
        {
            TicketOrderDetBean.OrderInfoBean orderInfoBean;
            for (int i = 0; i < mList.size(); i++)
            {
                orderInfoBean = mList.get(i);
                List<TicketOrderDetBean.TicketsListBean> ticketsEmp = orderInfoBean.getTicketsList();
                if (ticketsEmp != null)
                {
                    for (int j = 0; j < ticketsEmp.size(); j++)
                    {
                        //找到相同的；
                        if (ticketsEmp.get(j).isSelect())
                        {
                            tickets.add(ticketsEmp.get(j));
                        }
                    }
                }
            }
        }
        return tickets;
    }

}