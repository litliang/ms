package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.DateUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 2016/3/24.
 * 机票订单-->顶部单程信息；
 */
public class PlaneHbAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<PlaneTicket> data;
    private LayoutInflater layoutInflater = null;
    private Context context;

    public PlaneHbAdapter(Context context, List<PlaneTicket> data)
    {
        this.data = data;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(layoutInflater.inflate(R.layout.plane_hb_item_new, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {
            PlaneTicket ticket = data.get(position);
            //航线类型；提交订单时使用；
            ticket.setRouteType("1");
            MyViewHolder viewHolder = (MyViewHolder) holder;

            //出发日；yyyy-MM-dd
            String start = ticket.getTimeSeres();
            String end = ticket.getArrDay();
            viewHolder.flightDateStart.setText(start.substring(start.indexOf("-") + 1) + " 周" + DateUtil.getWeek(start));
            viewHolder.flightDateEnd.setText(start.substring(end.indexOf("-") + 1) + " 周" + DateUtil.getWeek(end));

            viewHolder.tvLenPlaneType.setText(ticket.getFlyIngTime() + "/" + ticket.getAcft());

            //起飞时间和结束时间；
            viewHolder.startTime.setText(ticket.getDepTime());
            viewHolder.endTime.setText(ticket.getArrTime());
            viewHolder.tv_startAirport.setText(ticket.getDepAirPort() + ticket.getDepartureTerminal());
            viewHolder.tv_endAirport.setText(ticket.getArrAirPort() + ticket.getArrartureTerminal());

            viewHolder.tv_company_name.setText(ticket.getStoreName());

            setStopTransit(viewHolder, ticket);

            //当乘客乘坐的航班是共享航班时，此处显示乘客实际乘坐的航班，否则隐藏；
            //是否是共享航班；
            setSharePlane(viewHolder, ticket);
        }
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
        TextView tvLenPlaneType;
        TextView flightDateStart;
        TextView flightDateEnd;
        TextView startTime;
        TextView tv_company_name;
        TextView endTime;
        TextView tv_startAirport;
        TextView tv_endAirport;
        TextView tvCity;
        TextView tvAction;
        View panelTransStop;

        View panel_carriage; //实际承运；
        ImageView iv_carriage;
        TextView tv_carriage_num;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            flightDateStart = (TextView) itemView.findViewById(R.id.flightDateStart);
            flightDateEnd = (TextView) itemView.findViewById(R.id.flightDateEnd);
            tvLenPlaneType = (TextView) itemView.findViewById(R.id.tvLenPlaneType);
            startTime = (TextView) itemView.findViewById(R.id.startTime);
            tv_company_name = (TextView) itemView.findViewById(R.id.tv_company_name);
            endTime = (TextView) itemView.findViewById(R.id.endTime);
            tv_startAirport = (TextView) itemView.findViewById(R.id.tv_startAirport);
            tv_endAirport = (TextView) itemView.findViewById(R.id.tv_endAirport);

            tvAction = (TextView) itemView.findViewById(R.id.tvAction);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            panelTransStop = itemView.findViewById(R.id.panelTransStop);

            panel_carriage = itemView.findViewById(R.id.panel_carriage);
            iv_carriage = (ImageView) itemView.findViewById(R.id.iv_carriage);
            tv_carriage_num = (TextView) itemView.findViewById(R.id.tv_carriage_num);
        }
    }

    /**
     * 设置共享航班显示；
     *
     * @param viewHolder
     * @param flightDetailBean
     */
    private void setSharePlane(MyViewHolder viewHolder, PlaneTicket flightDetailBean)
    {
        if (flightDetailBean != null && flightDetailBean.getFlightList() != null)
        {
            List<PlaneTicket> sharePlaneTickets = getShareFlights(flightDetailBean.getFlightList());
            if (sharePlaneTickets == null || sharePlaneTickets.size() == 0)
            {
                viewHolder.panel_carriage.setVisibility(View.GONE);
            } else
            {
                viewHolder.panel_carriage.setVisibility(View.VISIBLE);
                String shareFlightNumber;
                if (sharePlaneTickets.size() == 1)
                {
                    viewHolder.iv_carriage.setVisibility(View.VISIBLE);
                    PlaneTicket planeTicket = sharePlaneTickets.get(0);
                    shareFlightNumber = planeTicket.getIsFlightNumber();
                    x.image().bind(viewHolder.iv_carriage, ServiceDispatcher.getImageUrl(planeTicket.getSharedFlightLogo()));
                } else
                {
                    viewHolder.iv_carriage.setVisibility(View.GONE);
                    shareFlightNumber = "多共享航班";
                }
                viewHolder.tv_carriage_num.setText(shareFlightNumber); //共享航班号；
            }
        }
    }

    /**
     * 获取共享航班列表；
     *
     * @param flightList
     */
    private List<PlaneTicket> getShareFlights(List<PlaneTicket> flightList)
    {
        if (flightList != null)
        {
            List<PlaneTicket> planeTickets = new ArrayList<>();
            for (int i = 0; i < flightList.size(); i++)
            {
                if (!TextUtils.isEmpty(flightList.get(i).getIsFlightNumber()))
                {
                    planeTickets.add(flightList.get(i));
                }
            }
            return planeTickets;
        }
        return null;
    }

    /**
     * 设置经停或中转；
     *
     * @param viewHolder
     * @param map
     */
    private void setStopTransit(MyViewHolder viewHolder, PlaneTicket map)
    {
        String stop = "";
        String transit = "";
        if (map.getFlightList().size() <= 1)
        {//直飞

            boolean stopIsEmpty = TextUtils.isEmpty(map.getStopCityContext())|| "无经停".equals(map.getStopCityContext());
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
            viewHolder.panelTransStop.setVisibility(View.GONE);
        } else
        {
            viewHolder.panelTransStop.setVisibility(View.VISIBLE);
            if (stopIsEmpty)
            {
                viewHolder.tvAction.setText("中转");
                viewHolder.tvCity.setText(transit);
            } else if (transitIsEmpty)
            {
                viewHolder.tvAction.setText("经停");
                viewHolder.tvAction.setVisibility(View.GONE);
                viewHolder.tvCity.setText(stop);
            } else
            {
                viewHolder.tvAction.setText(transit);
                viewHolder.tvCity.setText(stop);
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
