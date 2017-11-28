package com.yzb.card.king.ui.integral.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.TicketOrderBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.OrderUtils;
import com.yzb.card.king.util.Utils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类名： PlainOrderAdapter
 * 作者： Lei Chao.
 * 日期： 2016-10-13
 * 描述：
 */
public class PlainOrderAdapter extends RecyclerView.Adapter
{
    private TicketOrderBean ticketOrderBean;
    private Context mContext;
    private LayoutInflater mInflater;
    private View.OnClickListener itemListener;
    private int routeType; //航班类型；1：单程；2：往返；3：多程；
    private int size;

    public void setItemListener(View.OnClickListener itemListener)
    {
        this.itemListener = itemListener;
    }

    public PlainOrderAdapter(TicketOrderBean ticketOrderBean, Context context)
    {
        this.ticketOrderBean = ticketOrderBean;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 获取航线类型；
     *
     * @return 1:单程；2：往返；3：多程；
     */
    public int getRouteType()
    {
        return routeType;
    }

    /**
     * 获取订单总金额；
     */
    public String getOrderAmount()
    {
        return ticketOrderBean == null ? "0" : Utils.subZeroAndDot(ticketOrderBean.orderAmount);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(mInflater.inflate(R.layout.plain_ticket_order, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder vh = (MyViewHolder) holder;
            vh.startCity.setText(ticketOrderBean.startCityName[position]);
            vh.endCity.setText(ticketOrderBean.endCityName[position]);

            String startDates = ticketOrderBean.timeSereses[position];
            Date startDatesEmp = DateUtil.string2Date(startDates, DateUtil.DATE_FORMAT_DATE);
            vh.time.setText(DateUtil.date2String(startDatesEmp, DateUtil.DATE_FORMAT_DATE));

            //格式：hh:mm:ss
            String startTime = ticketOrderBean.startTime[position];
            Date startDate = DateUtil.string2Date(startTime, DateUtil.DATE_FORMAT_HHMMSS);
            vh.startTime.setText(DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_HHMM));

            String endTime = ticketOrderBean.endTime[position];
            Date endDate = DateUtil.string2Date(endTime, DateUtil.DATE_FORMAT_HHMMSS);
            vh.endTime.setText(DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_HHMM));

            if (this.ticketOrderBean.carrierLogos.length > 0)
            {
                String str = ticketOrderBean.carrierLogos[position];
                if (!TextUtils.isEmpty(str))
                {
                    x.image().bind(vh.air_logo2, ServiceDispatcher.getImageUrl(str));
                }
            }

            if (this.ticketOrderBean.carrierNames.length > 0)
            {
                vh.airType.setText(this.ticketOrderBean.carrierNames[position]);
            }

            if (this.ticketOrderBean.product.length > 0)
            {
                vh.cangwei.setText(this.ticketOrderBean.product[position]);
            }

            //行程类型；
            switch (routeType)
            {
                case 1://单程
                    vh.number.setVisibility(View.GONE);
                    //订单显示飞机图片
                    vh.iv_logo.setVisibility(View.GONE);
                    vh.iv_logo.setImageResource(R.mipmap.icon_order_plane);
                    vh.dashLine.setVisibility(View.GONE);
                    break;
                case 2: //往返；
                    vh.number.setVisibility(View.VISIBLE);
                    vh.iv_logo.setVisibility(View.GONE);
                    vh.number.setText("1".equals(ticketOrderBean.routeType[position]) ? "去" : "返");
                    vh.dashLine.setVisibility(View.VISIBLE);
                    break;
                case 3: //多程；
                    vh.number.setVisibility(View.VISIBLE);
                    vh.iv_logo.setVisibility(View.GONE);
                    vh.number.setText((position + 1) + "");
                    vh.dashLine.setVisibility(View.VISIBLE);
                    break;
            }

            if(routeType != 1){
                //最后一条线不显示；
                vh.dashLine.setVisibility(position == size - 1 ? View.INVISIBLE : View.VISIBLE);
            }


            vh.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (itemListener != null)
                    {
                        itemListener.onClick(null);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        int flightCount = 0; //航班的数量；
        for (int i = 0; i < ticketOrderBean.flightnumbers.length; i++)
        {
            if (!TextUtils.isEmpty(ticketOrderBean.flightnumbers[i]))
            {
                flightCount++;
            }
        }
        this.size = flightCount;
        initRouteType();


        //多程中如果orderNo数组都相等，视为单程对待；
        if (routeType == 3)
        {
            String[] array = ticketOrderBean.orderNo;

            //找出不为空的item;
            List<String> newList = new ArrayList<>();
            for (int i = 0; i < array.length; i++)
            {
                if (!TextUtils.isEmpty(array[i]))
                {
                    newList.add(array[i]);
                }
            }

            //比较每个item是否都相同；
            if (newList.size() > 1)
            {
                boolean notEqual = false;
                String firstItem = newList.get(0);
                for (int i = 1; i < newList.size(); i++)
                {
                    if (!firstItem.equals(newList.get(i)))
                    {
                        notEqual = true;
                        break;
                    }
                }
                //都相同；
                if (!notEqual)
                {
                    routeType = 1;
                    size = 1;
                }
            }
        }
        return size;
    }

    /**
     * 获取航线类型；
     *
     * @return
     */
    public void initRouteType()
    {
        if (ticketOrderBean != null)
        {
            String[] route = ticketOrderBean.routeType;
            // 判断是否是往返；
            for (int i = 0; i < route.length; i++)
            {
                //航线类型（1：去，2：返 ）
                if ("2".equals(route[i]))
                {
                    routeType = 2;
                    return;
                }
            }
            // 判断单程，多程
            int flightLen = 0;
            for (int i = 0; i < route.length; i++)
            {
                if (!TextUtils.isEmpty(route[i]))
                {
                    flightLen++;
                }
            }
            //长度是1为单程；
            if (flightLen == 1)
            {
                routeType = 1;
            } else
            {
                routeType = 3;
            }
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView startCity;
        TextView endCity;
        TextView startTime;
        TextView endTime;
        TextView time;
        TextView airType;
        TextView cangwei;
        ImageView air_logo2;
        ImageView iv_logo;
        TextView number;
        View dashLine; //虚线

        public MyViewHolder(View itemView)
        {
            super(itemView);
            air_logo2 = (ImageView) itemView.findViewById(R.id.air_logo2);
            iv_logo = (ImageView) itemView.findViewById(R.id.iv_logo);
            startCity = (TextView) itemView.findViewById(R.id.start_city);
            endCity = (TextView) itemView.findViewById(R.id.end_city);
            dashLine = itemView.findViewById(R.id.dashLine);
            startTime = (TextView) itemView.findViewById(R.id.tv_start_time);
            endTime = (TextView) itemView.findViewById(R.id.tv_end_time);
            time = (TextView) itemView.findViewById(R.id.time);
            airType = (TextView) itemView.findViewById(R.id.air_type);
            cangwei = (TextView) itemView.findViewById(R.id.cangwei);
            number = (TextView) itemView.findViewById(R.id.number);
        }
    }

}