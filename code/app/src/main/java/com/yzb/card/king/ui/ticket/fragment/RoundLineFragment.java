package com.yzb.card.king.ui.ticket.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.bean.ticket.ShippingSpace;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.other.bean.City;
import com.yzb.card.king.ui.ticket.activity.AirTicketHomeActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 往返程
 * Created by dev on 2016/5/5.
 */
public class RoundLineFragment extends BaseFragment implements ITicketFragment {

    private TicketHomeFragment fragment = null;

    private TextView tv_end_week;
    private TextView tv_end_date;
    private TextView tv_start_date;
    private TextView tv_start_week;
    private LinearLayout ll_end_date;
    private LinearLayout ll_start_date;
    private ImageView trunsImage;
    private TextView tv_end_city;
    private TextView tv_start_city;
    private List<Flight> flights = new ArrayList<>();
    private int position;
    private boolean start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ticket_round_line, null, false);

        init(view);

        return view;
    }

    private void init(View view) {

        initView(view);

        initData();

        initListener();

        setDefault(flights);
    }

    private void initView(View view) {

        tv_start_city = (TextView) view.findViewById(R.id.tv_start_city);

        tv_end_city = (TextView) view.findViewById(R.id.tv_end_city);

        trunsImage = (ImageView) view.findViewById(R.id.trunsImage);

        ll_start_date = (LinearLayout) view.findViewById(R.id.ll_start_date);

        ll_end_date = (LinearLayout) view.findViewById(R.id.ll_end_date);

        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);

        tv_start_week = (TextView) view.findViewById(R.id.tv_start_week);

        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date);

        tv_end_week = (TextView) view.findViewById(R.id.tv_end_week);
    }

    private void initData() {

        AirTicketHomeActivity airTicketHomeActivity = (AirTicketHomeActivity) getActivity();

        fragment = airTicketHomeActivity.getTicketHomeFragment();

        //检查是否有历史数据
        String singFile = SharePrefUtil.getValueFromSp(getContext(), SharePrefUtil.TICKET_ROUND_LINE_INFO, null);

        if(singFile != null){

            if(flights!=null){

                flights.clear();
            }

            flights = JSONArray.parseArray(singFile,Flight.class);

        }else {

            Flight goFlight = new Flight(false);

            goFlight.setShippingSpace(fragment.getCangwei());

            initFlights(goFlight);
        }


    }

    private void initFlights(Flight goFlight) {

        Flight backFlight = new Flight(false);

        backFlight.setFEndCity(null);

        backFlight.setFStartCity(goFlight.getEndCity());

        backFlight.setFEndCity(goFlight.getStartCity());

        backFlight.setStartDate(DateUtil.addDay(goFlight.getStartDate(), 3));

        backFlight.setShippingSpace(goFlight.getShippingSpace());

        flights.clear();

        flights.add(goFlight);

        flights.add(backFlight);
    }

    private void initListener() {

        tv_start_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 0;
                start = true;
                fragment.getPlace();
            }
        });

        tv_end_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 0;
                start = false;
                fragment.getPlace();
            }
        });

        trunsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < flights.size(); i++) {
                    changeCity(flights.get(i));
                }
                setDefault(flights);
            }
        });

        ll_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 0;
                start = true;
                fragment.getDate(flights.get(0).getStartDate(), "去程");
            }
        });

        ll_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 1;
                start = false;
                fragment.getDate(flights.get(1).getStartDate(), "返程");
            }
        });
    }

    private void setDefault(List<Flight> flights) {

        Flight goFlight = flights.get(0);

        Flight backFlight = flights.get(1);

        tv_start_city.setText(goFlight.getStartCity().getCityName());

        tv_end_city.setText(goFlight.getEndCity().getCityName());

        tv_start_date.setText(DateUtil.date2String(goFlight.getStartDate(), DateUtil.DATE_FORMAT_MONTH_DAY));

        tv_start_week.setText(DateUtil.getDateExplain(goFlight.getStartDate()));

        tv_end_date.setText(DateUtil.date2String(backFlight.getStartDate(), DateUtil.DATE_FORMAT_MONTH_DAY));

        tv_end_week.setText(DateUtil.getDateExplain(backFlight.getStartDate()));
    }

    @Override
    public void setPlace(City city) {
        boolean hasSameCity;

        if (start) {
            flights.get(0).setFStartCity(city);
            hasSameCity = flights.get(1).setFEndCity(city);
        } else {
            flights.get(0).setFEndCity(city);
            hasSameCity = flights.get(1).setFStartCity(city);
        }
        if (hasSameCity) {
            UiUtils.shortToast("出发地与目的地不能相同");

        } else {
            setDefault(flights);

        }
    }

    @Override
    public void setDate(Date date) {
        Flight flight = flights.get(position);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        flight.setStartDate(date);
        checkDate(flights.get(0).getStartDate(),flights.get(1).getStartDate());

        setDefault(flights);
    }

    private void checkDate(Date before, Date after) {
        if (before != null && after != null) {
            int duration = DateUtil.naturalDaysBetween(before, after);
            if (duration < 0) {
                Calendar calendar = Calendar.getInstance();
                if (position == 0) {
                    calendar.setTime(before);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    flights.get(0).setStartDate(before);
                    flights.get(1).setStartDate(calendar.getTime());
                } else {

                    if (DateUtil.naturalDaysBetween(new Date(), after) > 0) {
                        calendar.setTime(after);
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        flights.get(0).setStartDate(calendar.getTime());
                        flights.get(1).setStartDate(after);
                    } else {
                        flights.get(0).setStartDate(before);
                        flights.get(1).setStartDate(before);
                    }
                }
            } else {
                flights.get(0).setStartDate(before);
                flights.get(1).setStartDate(after);
            }

        }
    }

    @Override
    public void setShippSpace(ShippingSpace shippSpace) {
        for (int i = 0; i < flights.size(); i++) {
            flights.get(i).setShippingSpace(shippSpace);
        }
    }

    @Override
    public List<Flight> getFlights() {
        return flights;
    }

    private void changeCity(Flight flight) {

        City endCity = flight.getEndCity();

        City startCity = flight.getStartCity();

        flight.setFEndCity(null);

        flight.setFStartCity(null);

        flight.setFEndCity(startCity);

        flight.setFStartCity(endCity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LogUtil.e("---------roundLineFragment---onDestroy----");
    }
}
