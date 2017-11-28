package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.ticket.activity.BaseTicketActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 2016/3/24.
 * 航班路线详情；
 */
public class PlaneHbPopAdapter extends BaseListAdapter<PlaneTicket>
{
    private int routeType; //行程类型 多程，往返；

    public PlaneHbPopAdapter(Context context, List<PlaneTicket> data)
    {
        super(context, data);
    }

    /**
     * 设置行程类型；
     */
    public void setRouteType(int routeType)
    {
        this.routeType = routeType;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.popwindow_content_plane_hb_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PlaneTicket ticket = mList.get(position);

        if (BaseTicketActivity.TYPE_ROUND == routeType)//往返；
        {
            viewHolder.tv_order.setText(position == 0 ? "去" : "返");
            viewHolder.tv_order.setBackgroundResource(0);
        } else if (BaseTicketActivity.TYPE_MULTI == routeType)//多程；
        {
            viewHolder.tv_order.setText((position + 1) + "");
            viewHolder.tv_order.setBackgroundResource(R.drawable.icon_round_number_blue);
        }
        //日期；
        String start = ticket.getTimeSeres();
        viewHolder.flightDate.setText(start.substring(start.indexOf("-") + 1) + " 周" + DateUtil.getWeek(start));

        //出发地和目的地；
        viewHolder.tv_start_end_city.setText(ticket.getStartCity() + " - " + ticket.getEndCity());
        //总时长；
        viewHolder.totalTimeLength.setText(ticket.getFlyIngTime());

        List<PlaneTicket> tickets = ticket.getFlightList();
        //直飞
        if (tickets != null && tickets.size() <= 1)
        {
            initNoneStopView(viewHolder, ticket);
        } else
        {//中转
            initStopView(viewHolder, ticket);
        }
        return viewHolder.rootView;
    }

    /**
     * 非直飞初始化；
     *
     * @param viewHolder
     * @param ticket
     */
    private void initStopView(ViewHolder viewHolder, PlaneTicket ticket)
    {
        LogUtil.i("initStopView  ticket=" + JSON.toJSONString(ticket));

        // 中转列表；
        List<PlaneTicket> flightList = ticket.getFlightList();
        if (flightList == null || flightList.size() < 2)
        {
            viewHolder.rootView.setVisibility(View.GONE);
            return;
        }
        viewHolder.rootView.setVisibility(View.VISIBLE);
        viewHolder.firstLayout.setVisibility(View.VISIBLE);
        viewHolder.secondLayout.setVisibility(View.VISIBLE);

        initStopFirstView(viewHolder, flightList.get(0));
        setStopTransit(viewHolder, ticket);
        initStopLastView(viewHolder, flightList.get(flightList.size() - 1));
    }

    /**
     * 初始化非直飞结束布局；
     *
     * @param viewHolder
     * @param ticket
     */
    private void initStopLastView(ViewHolder viewHolder, PlaneTicket ticket)
    {
        LogUtil.i("initStopLastView  ticket=" + JSON.toJSONString(ticket));

        //开始结束时间；
        viewHolder.startTime2.setText(ticket.getDepTime());
        viewHolder.endTime2.setText(ticket.getArrTime());
        //开始地点: 机场+航站楼；
        viewHolder.startAirportName2.setText(ticket.getDepAirPort() + ticket.getDepartureTerminal());
        //结束地点: 机场+航站楼；
        viewHolder.endAirportName2.setText(ticket.getArrAirPort() + ticket.getArrartureTerminal());

        //飞行耗时；
        viewHolder.timeLength2.setText(ticket.getFlyIngTime());

        x.image().bind(viewHolder.hbImage2, ServiceDispatcher.getImageUrl(ticket.getShopLogo()));
        //航空公司名称
        viewHolder.flightName2.setText(ticket.getStoreName());
        //飞机机型
        viewHolder.tv_flightType2.setText(ticket.getAcft());
        //航班编号名；
        viewHolder.tv_flightNumber2.setText(ticket.getFlightNumber());
    }

    /**
     * 初始化非直飞开始布局；
     *
     * @param viewHolder
     * @param ticket
     */
    private void initStopFirstView(ViewHolder viewHolder, PlaneTicket ticket)
    {
        LogUtil.i("initStopFirstView  ticket=" + JSON.toJSONString(ticket));

        viewHolder.endTime.setTextSize(14);
        viewHolder.endAirportName.setTextSize(12);
        viewHolder.line.setBackgroundResource(R.mipmap.line_02);
        viewHolder.timeLength.setVisibility(View.VISIBLE);

        //开始结束时间；
        viewHolder.startTime.setText(ticket.getDepTime());
        viewHolder.endTime.setText(ticket.getArrTime());
        //开始地点: 机场+航站楼；
        viewHolder.startAirportName.setText(ticket.getDepAirPort() + ticket.getDepartureTerminal());
        //结束地点: 机场+航站楼；
        viewHolder.endAirportName.setText(ticket.getArrAirPort() + ticket.getArrartureTerminal());

        //飞行耗时；
        viewHolder.timeLength.setText(ticket.getFlyIngTime());

        x.image().bind(viewHolder.hbImage, ServiceDispatcher.getImageUrl(ticket.getShopLogo()));
        //航空公司名称
        viewHolder.flightName.setText(ticket.getStoreName());
        //飞机机型
        viewHolder.tv_flightType.setText(ticket.getAcft());
        //航班编号名；
        viewHolder.tv_flightNumber.setText(ticket.getFlightNumber());
    }

    /**
     * 设置经停或中转；
     *
     * @param viewHolder
     * @param map
     */
    private void setStopTransit(ViewHolder viewHolder, PlaneTicket map)
    {
        String stop = "";
        String transit = "";
        String transTime = "";//中转时长；
        if (map != null && map.getFlightList() != null && map.getFlightList().size() <= 1)
        {//直飞
            boolean stopIsEmpty = TextUtils.isEmpty(map.getStopCityContext());
            if (!stopIsEmpty)
            {
                stop = map.getStopCityContext();
            }
            transTime = "";
        } else
        {//中转
            List<String> stopCity = getStopCity(map);
            List<String> transitCity = map.getTransitCities();
            if (stopCity.size() == 0)
            {  //没有经停，只有中转
                LogUtil.i("flag=1");

                if (transitCity.size() > 1)
                {
                    transit = transitCity.size() + "中转";
                } else if (transitCity.size() == 1)
                {
                    transit = transitCity.get(0);
                    transTime = map.getTransLen();
                }
            } else
            {//有经停有中转
                LogUtil.i("flag=1");
                stop = stopCity.size() + "经停";
                transit = transitCity.size() + "中转";
                transTime = "";
            }
        }
        setTransitStopText(viewHolder, stop, transit, transTime);
    }


    /**
     * 设置中转，经停城市；
     *
     * @param viewHolder
     * @param stop
     * @param transit    //中转城市；
     * @param transTime  中转时长；
     */
    private void setTransitStopText(ViewHolder viewHolder, String stop, String transit, String transTime)
    {
        LogUtil.i("stop=" + stop + ",transit=" + transit + ",transTime=" + transTime);

        boolean stopIsEmpty = TextUtils.isEmpty(stop);
        boolean transitIsEmpty = TextUtils.isEmpty(transit);
        if (stopIsEmpty && transitIsEmpty)
        {
            viewHolder.rl_zhuan.setVisibility(View.GONE);
        } else
        {
            viewHolder.rl_zhuan.setVisibility(View.VISIBLE);
            if (stopIsEmpty)
            {
                viewHolder.tv_zhuan.setText("转");
                viewHolder.zhuanCity.setText(transit);
                viewHolder.zhuanTimeLength.setVisibility(View.VISIBLE);
                viewHolder.zhuanTimeLength.setText(transTime);
            } else if (transitIsEmpty)
            {
                viewHolder.tv_zhuan.setText("停");
                viewHolder.zhuanCity.setText(stop);
                viewHolder.zhuanTimeLength.setVisibility(View.VISIBLE);
                viewHolder.zhuanTimeLength.setText(transTime);
                viewHolder.zhuanTimeLength.setVisibility(View.GONE);
            } else
            {
                viewHolder.tv_zhuan.setText("转");
                viewHolder.zhuanCity.setText("多个中转");
                viewHolder.zhuanTimeLength.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 直飞初始化；
     *
     * @param viewHolder
     * @param ticket
     */
    private void initNoneStopView(ViewHolder viewHolder, PlaneTicket ticket)
    {
        LogUtil.i("initNoneStopView  ticket=" + JSON.toJSONString(ticket));

        viewHolder.rootView.setVisibility(View.VISIBLE);
        viewHolder.firstLayout.setVisibility(View.VISIBLE);
        viewHolder.timeLength.setVisibility(View.GONE);
        viewHolder.secondLayout.setVisibility(View.GONE);
        viewHolder.endTime.setTextSize(18);
        viewHolder.endAirportName.setTextSize(16);
        viewHolder.line.setBackgroundResource(R.mipmap.line_01);

        //开始结束时间；
        viewHolder.startTime.setText(ticket.getDepTime());
        viewHolder.endTime.setText(ticket.getArrTime());
        //开始地点: 机场+航站楼；
        viewHolder.startAirportName.setText(ticket.getDepAirPort() + ticket.getDepartureTerminal());
        //结束地点: 机场+航站楼；
        viewHolder.endAirportName.setText(ticket.getArrAirPort() + ticket.getArrartureTerminal());

        x.image().bind(viewHolder.hbImage, ServiceDispatcher.getImageUrl(ticket.getShopLogo()));
        //航空公司名称
        viewHolder.flightName.setText(ticket.getStoreName());
        //飞机机型
        viewHolder.tv_flightType.setText(ticket.getAcft());
        //航班编号名；
        viewHolder.tv_flightNumber.setText(ticket.getFlightNumber());
    }

    /**
     * 中转城市；
     *
     * @param map
     * @return
     */
    private List<String> getStopCity(PlaneTicket map)
    {
        List<String> stopCities = new ArrayList<>();
        if (map != null && map.getFlightList() != null)
        {
            for (int i = 0; i < map.getFlightList().size(); i++)
            {
                if (!TextUtils.isEmpty(getStopCityContext(map, i)))
                {
                    stopCities.add(getStopCityContext(map, i));
                }
            }
        }
        return stopCities;
    }

    private String getStopCityContext(PlaneTicket map, int i)
    {
        return map.getFlightList().get(i).getStopCityContext();
    }

    static class ViewHolder
    {
        View rootView;
        View secondLayout;
        View firstLayout;
        View rl_zhuan;
        TextView tv_order;
        TextView flightDate;
        TextView tv_zhuan;
        TextView tv_start_end_city;
        TextView totalTimeLength; //总时长；
        TextView startTime;
        TextView startAirportName;
        TextView timeLength;
        TextView endTime;
        TextView endAirportName;

        ImageView hbImage;//航空公司logo；
        TextView flightName;  //航空公司名称；
        TextView tv_flightNumber; //航班编号
        TextView tv_flightType; //飞机机型；

        ImageView line;

        TextView zhuanCity;
        TextView zhuanTimeLength; //中转机场逗留时间；

        ImageView line2;
        TextView startTime2;
        TextView startAirportName2;
        TextView timeLength2;
        TextView endTime2;
        TextView endAirportName2;
        ImageView hbImage2;//中转航空公司logo；
        TextView flightName2; //中转航班名；
        TextView tv_flightNumber2; //中转航班号
        TextView tv_flightType2; //中转航班机型；

        public ViewHolder(View convertView)
        {
            rootView = convertView;
            secondLayout = convertView.findViewById(R.id.secondLayout);
            firstLayout = convertView.findViewById(R.id.firstLayout);
            rl_zhuan = convertView.findViewById(R.id.rl_zhuan);

            tv_order = (TextView) convertView.findViewById(R.id.tv_order);
            flightDate = (TextView) convertView.findViewById(R.id.flightDate);
            tv_start_end_city = (TextView) convertView.findViewById(R.id.tv_start_end_city);
            totalTimeLength = (TextView) convertView.findViewById(R.id.totalTimeLength);
            startTime = (TextView) convertView.findViewById(R.id.startTime);
            startAirportName = (TextView) convertView.findViewById(R.id.startAirportName);
            timeLength = (TextView) convertView.findViewById(R.id.timeLength);
            endTime = (TextView) convertView.findViewById(R.id.endTime);
            endAirportName = (TextView) convertView.findViewById(R.id.endAirportName);
            hbImage = (ImageView) convertView.findViewById(R.id.hbImage);
            flightName = (TextView) convertView.findViewById(R.id.flightName);
            tv_flightNumber = (TextView) convertView.findViewById(R.id.tv_flightNumber);
            tv_flightType = (TextView) convertView.findViewById(R.id.tv_flightType);

            line = (ImageView) convertView.findViewById(R.id.line);

            zhuanCity = (TextView) convertView.findViewById(R.id.zhuanCity);
            zhuanTimeLength = (TextView) convertView.findViewById(R.id.zhuanTimeLength);

            tv_zhuan = (TextView) convertView.findViewById(R.id.tv_zhuan);
            line2 = (ImageView) convertView.findViewById(R.id.line2);
            startTime2 = (TextView) convertView.findViewById(R.id.startTime2);
            startAirportName2 = (TextView) convertView.findViewById(R.id.startAirportName2);

            hbImage2 = (ImageView) convertView.findViewById(R.id.hbImage2);
            timeLength2 = (TextView) convertView.findViewById(R.id.timeLength2);
            endTime2 = (TextView) convertView.findViewById(R.id.endTime2);
            endAirportName2 = (TextView) convertView.findViewById(R.id.endAirportName2);
            flightName2 = (TextView) convertView.findViewById(R.id.flightName2);
            tv_flightNumber2 = (TextView) convertView.findViewById(R.id.tv_flightNumber2);
            tv_flightType2 = (TextView) convertView.findViewById(R.id.tv_flightType2);
        }
    }
}
