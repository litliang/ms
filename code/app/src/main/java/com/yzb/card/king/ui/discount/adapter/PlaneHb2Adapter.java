package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.bean.ticket.FlightDetailBean;
import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.ui.ticket.activity.BaseTicketActivity;
import com.yzb.card.king.ui.ticket.activity.RefundTicketRuleActivity;
import com.yzb.card.king.ui.ticket.activity.TicketOrderActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 2016/3/24.
 * 往返和多程列表；
 */
public class PlaneHb2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<PlaneTicket> data;
    private LayoutInflater inflater;
    private int type = 0; //单程或多程的类型；

    private Context context;
    public PlaneHb2Adapter(Context context, List<PlaneTicket> data, int type)
    {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.plane_hb2_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {
            PlaneTicket ticket = data.get(position);
            MyViewHolder viewHolder = (MyViewHolder) holder;

            if (BaseTicketActivity.TYPE_ROUND == type)//往返；
            {
                viewHolder.bh.setText(position == 0 ? "去" : "返");
                ticket.setRouteType(position == 0 ? "1" : "2");

                viewHolder.bh.setBackgroundResource(0);
            } else if (BaseTicketActivity.TYPE_MULTI == type)//多程；
            {
                viewHolder.bh.setText((position + 1) + "");
                ticket.setRouteType("1");

                viewHolder.bh.setBackgroundResource(R.drawable.icon_round_number_blue);
            }

            //日期；yyyy-MM-dd
            String start = ticket.getTimeSeres();
            viewHolder.flightDate.setText(start.substring(start.indexOf("-") + 1) + " 周" + DateUtil.getWeek(start));

            viewHolder.startCity.setText(ticket.getStartCity());
            viewHolder.endCity.setText(ticket.getEndCity());
            setStopTransit(viewHolder, ticket);
            //航班号；
            viewHolder.tv_flightNumber.setText(ticket.getFlightNumber());
            //飞行时长；
            viewHolder.timeLength.setText(ticket.getFlyIngTime());

            FavInfoBean pachageBean=  ticket.getDisMap();
//            //折扣信息折扣状态, 1:有折扣；0：无折扣；
//            String discountStatus = pachageBean.getDiscountStatus();
//            viewHolder.tv_discount.setText("1".equals(discountStatus) ?
//                    pachageBean.getDiscountName() + " 退改签规则" : " 退改签规则");
            viewHolder.tv_discount.setText("退改签规则");

            viewHolder.ll_discount.setTag(ticket.getTicketPriceId());
            viewHolder.ll_discount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String priceId = (String) v.getTag();

                    Intent intent = new Intent(context, RefundTicketRuleActivity.class);
                    intent.putExtra("priceId", priceId);
                    context.startActivity(intent);
                }
            });

            viewHolder.llRount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  //  planHbHandler.sendEmptyMessage(R.id.llRount);
                }
            });
        }
    }

    private Handler planHbHandler ;

    public void setPlanHbHandler(Handler planHbHandler)
    {
        this.planHbHandler = planHbHandler;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getItemCount()
    {
        return data == null ? 0 : data.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView bh;
        TextView flightDate;
        private TextView startCity;
        private TextView endCity;
        TextView tv_flightNumber;
        TextView timeLength;
        View center_line;
        TextView transfer_type; //中转或经停；
        TextView transfer_city;//中转或经停城市；

        private LinearLayout ll_discount,llRount;
        TextView tv_discount;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            bh = (TextView) itemView.findViewById(R.id.bh);
            flightDate = (TextView) itemView.findViewById(R.id.flightDate);
            tv_flightNumber = (TextView) itemView.findViewById(R.id.tv_flightNumber);
            center_line = itemView.findViewById(R.id.center_line);
            timeLength = (TextView) itemView.findViewById(R.id.timeLength);
            startCity = (TextView) itemView.findViewById(R.id.start_city);
            endCity = (TextView) itemView.findViewById(R.id.end_city);
            transfer_type = (TextView) itemView.findViewById(R.id.transfer_type);
            transfer_city = (TextView) itemView.findViewById(R.id.transfer_city);
            tv_discount = (TextView) itemView.findViewById(R.id.tv_discount);
            ll_discount = (LinearLayout) itemView.findViewById(R.id.ll_discount);

            llRount = (LinearLayout) itemView.findViewById(R.id.llRount);
        }
    }

    /**
     * 设置经停或中转；
     *
     * @param viewHolder
     * @param map
     */
    private void setStopTransit(MyViewHolder viewHolder, PlaneTicket map)
    {
        String stop = ""; //经停；
        String transit = ""; //中转；
        if (map.getFlightList().size() <= 1)
        {//直飞

            boolean stopIsEmpty = TextUtils.isEmpty(map.getStopCityContext()) || "无经停".equals(map.getStopCityContext());
            if (!stopIsEmpty)
            {
                stop = map.getStopCityContext();
            }
        } else
        {//中转
            List<String> stopCity = getStopCity(map);
            List<String> transitCity = map.getTransitCities();
            if (stopCity.size() == 0)
            {//么有经停，只有中转
                if (transitCity.size() > 1)
                {
                    transit = transitCity.size() + "中转";
                } else if (transitCity.size() == 1)
                {
                    transit = transitCity.get(0);
                }
            } else
            {//有经停有中转
                stop = stopCity.size() + "经停";
                transit = transitCity.size() + "中转";
            }
        }
        setTransitStopText(viewHolder, stop, transit);
    }

    /**
     * 设置中转，经停城市；
     *
     * @param viewHolder
     * @param stop
     * @param transit
     */
    private void setTransitStopText(MyViewHolder viewHolder, String stop, String transit)
    {
        boolean stopIsEmpty = TextUtils.isEmpty(stop);
        boolean transitIsEmpty = TextUtils.isEmpty(transit);
        if (stopIsEmpty && transitIsEmpty)
        {
            viewHolder.transfer_type.setVisibility(View.GONE);
            viewHolder.transfer_city.setVisibility(View.GONE);
        } else
        {
            viewHolder.transfer_type.setVisibility(View.VISIBLE);
            viewHolder.transfer_city.setVisibility(View.VISIBLE);
            if (stopIsEmpty)
            {
                viewHolder.transfer_type.setText("中转");
                viewHolder.transfer_city.setText(transit);
            } else if (transitIsEmpty)
            {
                viewHolder.transfer_type.setText("经停");
                viewHolder.transfer_city.setText(stop);
            } else //有经停有中转
            {
                viewHolder.transfer_type.setText(transit + stop);
                viewHolder.transfer_city.setText("");
            }
        }
    }

    private List<String> getStopCity(PlaneTicket map)
    {
        List<String> stopCities = new ArrayList<>();
        for (int i = 0; i < map.getFlightList().size(); i++)
        {
            if (!TextUtils.isEmpty(getStopCityContext(map, i)))
            {
                stopCities.add(getStopCityContext(map, i));
            }
        }
        return stopCities;
    }

    private String getStopCityContext(PlaneTicket map, int i)
    {
        return map.getFlightList().get(i).getStopCityContext();
    }
}
