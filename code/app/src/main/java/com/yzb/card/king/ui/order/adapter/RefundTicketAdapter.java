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
import com.yzb.card.king.ui.base.BaseRecyAdapter;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类名： RefundApplicationAdapter
 * 作者： Lei Chao.
 * 日期： 2016-10-15
 * 描述：退票详情adapter；
 * <p/>
 * 第一次修改：gengqiyun  2016.12.2
 */
public class RefundTicketAdapter extends BaseRecyAdapter<TicketOrderDetBean.OrderInfoBean>
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

    public RefundTicketAdapter(List<TicketOrderDetBean.OrderInfoBean> orderInfos, Context context)
    {
        super(orderInfos, context);
    }

    @Override
    public void appendALL(List<TicketOrderDetBean.OrderInfoBean> orders)
    {
        super.appendALL(orders);
        //往返；判断组合套餐情况；
        if (AppConstant.TYPE_ROUND.equals(flightType) && orders != null && orders.size() == 2)
        {
            String firstOrderId = orders.get(0).getOrderId();
            String secondOrderId = orders.get(1).getOrderId();
            if (!TextUtils.isEmpty(firstOrderId) && firstOrderId.equals(secondOrderId))
            {
                isCombineProducts = true;
                notifyDataSetChanged();
                LogUtil.i("订单号相同,是组合套餐 firstOrderId=" + firstOrderId);
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.item_refund_ticket, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder viewHolder = (MyViewHolder) holder;
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
            String newDate = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE_DAY2);
            viewHolder.time.setText(newDate);

            viewHolder.line.setText(item.getStartCityName() + "—" + item.getEndCityName());

            //item是否选中；
            viewHolder.isSelected.setSelected(item.isSelect);
            viewHolder.guestListView.setVisibility(item.isSelect ? View.VISIBLE : View.GONE);

            viewHolder.hangban.setText(item.getToolNumber());
            viewHolder.cangwei.setText(item.getProduct());
            viewHolder.yongshi.setText(item.getFlyingTime());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    item.setIsSelect(!item.isSelect());
                    notifyDataSetChanged();
                }
            });

            GuestAdapter adapter = new GuestAdapter(context, item.getTicketsList());
            viewHolder.guestListView.setAdapter(adapter);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvIndex;
        TextView time;
        TextView line;
        TextView hangban;
        TextView cangwei;
        TextView yongshi;
        ImageView isSelected;
        WholeListView guestListView;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvIndex = (TextView) itemView.findViewById(R.id.tv_index);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            line = (TextView) itemView.findViewById(R.id.line);
            hangban = (TextView) itemView.findViewById(R.id.hangban);
            cangwei = (TextView) itemView.findViewById(R.id.cangwei);
            yongshi = (TextView) itemView.findViewById(R.id.yongshi);
            isSelected = (ImageView) itemView.findViewById(R.id.is_selected);
            guestListView = (WholeListView) itemView.findViewById(R.id.guestListView);
        }
    }

    /**
     * 是否有可退票的乘客；
     *
     * @return true: 还有没退票的；
     */
    public boolean hasAvailablePassengers()
    {
        boolean flag = false;
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
                        //找到还未退票的；
                        if (!"2".equals(ticketsEmp.get(j).getSturt()))
                        {
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 获取选中的未退票的乘机人；
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
                        if (ticketsEmp.get(j).isSelect() && !"2".equals(ticketsEmp.get(j).getSturt()))
                        {
                            tickets.add(ticketsEmp.get(j));
                        }
                    }
                }
            }
        }
        return tickets;
    }

    /**
     * 乘客列表；
     */
    public class GuestAdapter extends BaseListAdapter<TicketOrderDetBean.TicketsListBean>
    {

        public GuestAdapter(Context context, List<TicketOrderDetBean.TicketsListBean> list)
        {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = inflater.inflate(R.layout.item_refund_guest, parent, false);
            }
            TextView tvGuestName = (TextView) convertView.findViewById(R.id.tvGuestName);
            ImageView ivSelect = (ImageView) convertView.findViewById(R.id.ivSelect);
            final TicketOrderDetBean.TicketsListBean itemBean = getItem(position);
            tvGuestName.setText(itemBean.getGuestName());

            ivSelect.setSelected(itemBean.isSelect || "2".equals(itemBean.getSturt()));

            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //已退票不选中，置灰；（0：正常 1： 改升   2：退票   3：改签   ，4购票成功，5购票失败）
                    if ("2".equals(itemBean.getSturt()))
                    {
                        toastCustom(R.string.ticket_has_refund);
                    } else
                    {
                        changeSelection(itemBean);
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
     * @param itemBean
     */
    private void changeSelection(TicketOrderDetBean.TicketsListBean itemBean)
    {
        if (!isCombineProducts)
        {
            itemBean.setIsSelect(!itemBean.isSelect());
        } else
        {
            //组合套餐时，往返的2个航线中相同的人也要同时选中；
            TicketOrderDetBean.OrderInfoBean orderInfoBean;
            String guestIdCard = itemBean.getGuestIDCard();
            for (int i = 0; i < mList.size(); i++)
            {
                orderInfoBean = mList.get(i);
                List<TicketOrderDetBean.TicketsListBean> tickets = orderInfoBean.getTicketsList();
                if (tickets != null)
                {
                    for (int j = 0; j < tickets.size(); j++)
                    {
                        //找到相同的；
                        if (!TextUtils.isEmpty(guestIdCard) &&
                                guestIdCard.equals(tickets.get(j).getGuestIDCard()))
                        {
                            tickets.get(j).setIsSelect(!tickets.get(j).isSelect());
                        }
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

}