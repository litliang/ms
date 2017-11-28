package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FlightDetailBean;
import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.ticket.activity.BaseTicketActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DateUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：机票--航班详情顶部view；
 *
 * @author:gengqiyun
 * @date: 2016/10/10
 */
public class FlightDetailTitleView extends LinearLayout
{
    private TextView date,end_date;
    private TextView startTime;
    private TextView endTime;
    private TextView startAirport;
    private TextView endAirport;
    private TextView duration;
    private TextView tv_company_name;
    private TextView planeType;
    //    private TextView tvAction;
    private TextView tvCity;
    private View panelTransStop;
    private TextView tv_num;
    private ImageView iv_company_logo;
    private TextView tv_share_flight; // 实际承运”或“共享”。
    private ImageView iv_carriage; //承运方图片；
    private TextView tv_carriage_num; //承运方编号；
    private View panel_carriage;
    private int tripType; //行程类型；
    private int index;
    private  TextView tvNumber;

    public FlightDetailTitleView(Context context)
    {
        this(context, null);
    }

    public FlightDetailTitleView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        setOrientation(VERTICAL);
        View v = View.inflate(getContext(), R.layout.ticket_agent_second_title, this);

        date = (TextView) v.findViewById(R.id.tv_date);//
        end_date = (TextView) v.findViewById(R.id.end_date);
        startTime = (TextView) v.findViewById(R.id.tv_startTime);
        endTime = (TextView) v.findViewById(R.id.tv_endTime);
        startAirport = (TextView) v.findViewById(R.id.tv_startAirport);
        endAirport = (TextView) v.findViewById(R.id.tv_endAirport);
        duration = (TextView) v.findViewById(R.id.tv_duration);
        tv_company_name = (TextView) v.findViewById(R.id.tv_company_name);
        iv_company_logo = (ImageView) v.findViewById(R.id.iv_company_logo);
        planeType = (TextView) v.findViewById(R.id.tv_planeType);
//        tvAction = (TextView) v.findViewById(R.id.tvAction);
        tvCity = (TextView) v.findViewById(R.id.tvCity);
        panelTransStop = v.findViewById(R.id.panelTransStop);
        tv_num = (TextView) v.findViewById(R.id.tv_num);

        panel_carriage = v.findViewById(R.id.panel_carriage);
        iv_carriage = (ImageView) v.findViewById(R.id.iv_carriage);
        tv_carriage_num = (TextView) v.findViewById(R.id.tv_carriage_num);
        tv_share_flight = (TextView) v.findViewById(R.id.tv_share_flight);
        tvNumber = (TextView) v.findViewById(R.id.tvNumber);
    }

    /**
     * 设置当前选择的航班的序列号；
     * 往返：size=1时：去；size=2：返；
     * 多程时 size=1，2，3，。。。；
     *
     * @param size
     */
    public void setIndex(int size)
    {
        this.index = size;
    }

    /**
     * 设置行程类型
     *
     * @param tripType
     */
    public void setTripType(int tripType)
    {
        this.tripType = tripType;
    }

    /**
     * 注入数据；
     *
     * @param flightDetailBean
     */
    public void setData(FlightDetailBean flightDetailBean)
    {
        if (flightDetailBean != null)
        {
            String numText = ""; //序列号；
            int bgResId = 0; // 序列号背景资源；
            //往返；
            if (tripType == BaseTicketActivity.TYPE_ROUND)
            {
                numText = index == 1 ? "去" : "返";
            } else if (tripType == BaseTicketActivity.TYPE_MULTI) //多程；
            {
                bgResId = R.drawable.icon_round_number_blue;
                numText = index + "";
            } else //单程；
            {
                tv_num.setVisibility(View.GONE);
            }
            tv_num.setBackgroundResource(bgResId);
            tv_num.setText(numText);

            //出发日；yyyy-MM-dd
            String start = flightDetailBean.getTimeSeres();
            date.setText(start.substring(start.indexOf("-") + 1) + " 周" + DateUtil.getWeek(start));//end_date
            end_date.setText(start.substring(start.indexOf("-") + 1) + " 周" + DateUtil.getWeek(start));
            duration.setText(flightDetailBean.getFlyingTime());
            //起飞时间和结束时间；
            startTime.setText(flightDetailBean.getDepTime());
            endTime.setText(flightDetailBean.getArrTime());

            startAirport.setText(flightDetailBean.getDepAirPort() + flightDetailBean.getDepartureTerminal());
            endAirport.setText(flightDetailBean.getArrAirPort() + flightDetailBean.getArrartureTerminal());

            //航空公司logo；
            CommonUtil.downloadImageForView(ServiceDispatcher.getImageUrl(flightDetailBean.getShopLogo()), iv_company_logo, 19);

            tv_company_name.setText(flightDetailBean.getStoreName());

            //飞机机型
            planeType.setText(flightDetailBean.getAcft());

            tvNumber.setText(flightDetailBean.getFlightNumber());

            setStopTransit(flightDetailBean);
            //当乘客乘坐的航班是共享航班时，此处显示乘客实际乘坐的航班，否则隐藏；
            //是否是共享航班；
            setSharePlane(flightDetailBean);
        }
    }


    /**
     * 设置共享航班显示；
     *
     * @param flightDetailBean
     */
    private void setSharePlane(FlightDetailBean flightDetailBean)
    {
        if (flightDetailBean != null && flightDetailBean.getFlightList() != null)
        {
            List<PlaneTicket> sharePlaneTickets = getShareFlights(flightDetailBean.getFlightList());
            if (sharePlaneTickets == null || sharePlaneTickets.size() == 0)
            {
                panel_carriage.setVisibility(GONE);
            } else
            {
                panel_carriage.setVisibility(VISIBLE);
                String shareFlightNumber;
                if (sharePlaneTickets.size() == 1)
                {
                    iv_carriage.setVisibility(VISIBLE);
                    PlaneTicket planeTicket = sharePlaneTickets.get(0);
                    shareFlightNumber = planeTicket.getIsFlightNumber();

                    CommonUtil.downloadImageForView(ServiceDispatcher.getImageUrl(planeTicket.getSharedFlightLogo()), iv_carriage, 12);
                } else
                {
                    iv_carriage.setVisibility(GONE);
                    shareFlightNumber = "多共享航班";
                }
                tv_carriage_num.setText(shareFlightNumber); //共享航班号；
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
     * @param map
     */
    private void setStopTransit(FlightDetailBean map)
    {
        String stop = "";
        String transit = "";
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
        setTransitStopText(stop, transit);
    }

    /**
     * 设置中转，经停城市；
     *
     * @param stop
     * @param transit
     */
    private void setTransitStopText(String stop, String transit)
    {
        boolean stopIsEmpty = TextUtils.isEmpty(stop);
        boolean transitIsEmpty = TextUtils.isEmpty(transit);
        if (stopIsEmpty && transitIsEmpty)
        {
            tvCity.setVisibility(View.GONE);
        } else
        {
            tvCity.setVisibility(View.VISIBLE);
            if (stopIsEmpty)
            {
                tvCity.setText("中转\u3000" + transit);
            } else if (transitIsEmpty)
            {
                tvCity.setText(stop);
            } else
            {
                tvCity.setText(transit + "\u3000" + stop);
            }
        }
    }

    private List<String> getStopCity(FlightDetailBean map)
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

    private String getStopCityContext(FlightDetailBean map, int i)
    {
        return map.getFlightList().get(i).getStopCityContext();
    }
}
