package com.yzb.card.king.ui.ticket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.baidu.platform.comapi.map.C;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.bean.ticket.ShippingSpace;
import com.yzb.card.king.ui.appwidget.popup.PlaneCwPopup;
import com.yzb.card.king.ui.other.bean.City;
import com.yzb.card.king.ui.ticket.activity.AirTicketHomeActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.DensityUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 多程
 * Created by dev on 2016/5/5.
 */
public class MultiLineFragment extends Fragment implements ITicketFragment {

    private static final int MAX_LINE_NUM = 4;

    private LinearLayout ll_add_line, ll_lines;

    private TicketHomeFragment fragment = null;

    private PlaneCwPopup pop;

    private PlaneCwPopup.OnItemClickListener onItemClickListener;

    private List<ShippingSpace> list;

    private List<Flight> flights = new ArrayList<>();

    private int position;

    private boolean start;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ticket_multi_line, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        initView(view);
        initData();
        initListener();
    }

    private void initData() {
        AirTicketHomeActivity airTicketHomeActivity = (AirTicketHomeActivity) getActivity();

        fragment = airTicketHomeActivity.getTicketHomeFragment();
        initFlights();
        initFlightView();
    }

    private void initFlightView() {

        ll_lines.removeAllViews();

        for (int i = 0; i < flights.size(); i++) {
            addMutView(i);
        }
    }

    private void initFlights() {

        //检查是否有历史数据
        String singFile = SharePrefUtil.getValueFromSp(getContext(), SharePrefUtil.TICKET_MULTI_LINE_INFO, null);

        if( singFile != null){

            if(flights != null){

                flights.clear();
            }

            flights = JSONArray.parseArray(singFile,Flight.class);

        }else {

            Flight flight1 = new Flight(false);

            flight1.setShippingSpace(fragment.getCangwei());

            Flight flight2 = new Flight(false);

            flight2.setFEndCity(null);

            flight2.setFStartCity(flight1.getEndCity());

            flight2.setShippingSpace(fragment.getCangwei());

            flight2.setStartDate(DateUtil.addDay(flight1.getStartDate(), 3));

            flights.clear();

            flights.add(flight1);

            flights.add(flight2);
        }


    }

    private void initListener() {
        onItemClickListener = new PlaneCwPopup.OnItemClickListener() {
            @Override
            public void onItemClick(ShippingSpace shippingSpace) {
                flights.get(position).setShippingSpace(shippingSpace);
            }
        };

        ll_add_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = flights.size();
                if (size >= MAX_LINE_NUM) {
                    UiUtils.shortToast("最多只能添加" + MAX_LINE_NUM + "程！");
                } else {
                    Flight lastFlight = flights.get(size - 1);
                    City endCity = lastFlight.getEndCity();
                    if (endCity == null) {
                        UiUtils.shortToast(UiUtils.getString(R.string.ticket_ws_toast));
                    } else {
                        addFlight(lastFlight);
                    }
                }
            }
        });
    }

    private void addFlight(Flight lastFlight) {
        Flight flight = new Flight(false);
        flight.setFEndCity(null);
        flight.setFStartCity(lastFlight.getEndCity());
        flight.setStartDate(DateUtil.addDay(lastFlight.getStartDate(), 3));
        flight.setShippingSpace(fragment.getCangwei());
        flights.add(flight);
        initFlightView();
    }

    private void initView(View view) {
        ll_add_line = (LinearLayout) view.findViewById(R.id.ll_add_line);
        ll_lines = (LinearLayout) view.findViewById(R.id.ll_lines);
    }

    private void addMutView(int number) {
        final int index = number;
        View item = UiUtils.inflate(R.layout.ticket_multi_item);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 0, CommonUtil.dip2px(getActivity(), 10));
        item.setLayoutParams(lp);

        ImageView tvDelete = (ImageView) item.findViewById(R.id.tv_delete);
        LinearLayout ll_start_date = (LinearLayout) item.findViewById(R.id.ll_start_date);
        TextView tv_start_date = (TextView) item.findViewById(R.id.tv_start_date);
        TextView tv_cangwei = (TextView) item.findViewById(R.id.tv_cangwei);
        TextView tv_start_city = (TextView) item.findViewById(R.id.tv_start_city);
        TextView tv_start_week = (TextView) item.findViewById(R.id.tv_start_week);
        TextView tv_end_city = (TextView) item.findViewById(R.id.tv_end_city);
        ImageView trunsImage = (ImageView) item.findViewById(R.id.trunsImage);
        TextView tv = (TextView) item.findViewById(R.id.tv_ticket_number);

        if (index == 2 || index == 3) {
            tvDelete.setTag(index);
            tvDelete.setVisibility(View.VISIBLE);
        }

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                ll_lines.removeViewAt(position);
                flights.remove(position);
                initFlightView();
            }
        });

        ll_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = true;
                position = index;
                fragment.getDate(flights.get(position).getStartDate(), "弟" + (position + 1) + "程");
            }
        });

        tv_start_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = true;
                position = index;
                fragment.getPlace();
            }
        });

        tv_end_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = false;
                position = index;
                fragment.getPlace();
            }
        });

        //往返城市交换
        trunsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCity(flights.get(index));
                initFlightView();
            }
        });

        tv_cangwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = index;
                if (pop == null) {
                    pop = new PlaneCwPopup(MultiLineFragment.this, onItemClickListener, list);
                }
                pop.openPop(view);
            }
        });

        Flight flight = flights.get(number);
        tv_start_city.setText(flight.getStartCity() == null ? "" : flight.getStartCity().getCityName());
        City endCity = flight.getEndCity();
        if (endCity != null) {
            tv_end_city.setText(endCity.getCityName());
        }
        tv_start_date.setText(DateUtil.date2String(flight.getStartDate(), DateUtil.DATE_FORMAT_MONTH_DAY));
        tv_start_week.setText(DateUtil.getDateExplain(flight.getStartDate()));
        tv_cangwei.setText(flight.getShippingSpace().getQabinName());
        tv.setText((index + 1) + "");
        ll_lines.addView(item);
    }

    public void setListData(List<ShippingSpace> listData) {
        this.list = listData;
    }

    @Override
    public void setPlace(City city) {
        Flight flight = flights.get(position);
        boolean hasSameCity;
        if (start) {
            hasSameCity = flight.setFStartCity(city);
        } else {
            hasSameCity = flight.setFEndCity(city);
        }
        if (hasSameCity) {
            UiUtils.shortToast("出发地与目的地不能相同");

        } else {
            initFlightView();
        }
    }

    @Override
    public void setDate(Date date) {
        Flight flight = flights.get(position);
        flight.setStartDate(date);
        checkDate();
        initFlightView();
    }

    private void checkDate() {
        for (int i = position; i >= 0; i--) {
            if (position <= 0) break;
            if (i - 1 < 0) break;
            reduceDate(i - 1, i);
        }

        for (int i = position; i < flights.size(); i++) {
            if (position == flights.size() - 1) break;
            if (i + 1 > flights.size() - 1) break;
            addDate(i, i + 1);
        }


    }

    private void addDate(int beforeIndex, int afterIndex) {
        Date before = flights.get(beforeIndex).getStartDate();
        Date after = flights.get(afterIndex).getStartDate();
        if (before != null && after != null) {
            int duration = DateUtil.naturalDaysBetween(before, after);
            if (duration < 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(before);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                flights.get(afterIndex).setStartDate(calendar.getTime());
            }
        }
    }

    private void reduceDate(int beforeIndex, int afterIndex) {

        Date before = flights.get(beforeIndex).getStartDate();
        Date after = flights.get(afterIndex).getStartDate();
        if (before != null && after != null) {
            int duration = DateUtil.naturalDaysBetween(before, after);
            if (duration < 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(after);
                if (DateUtil.naturalDaysBetween(new Date(), after) > 0) {
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    flights.get(beforeIndex).setStartDate(calendar.getTime());
                } else {
                    flights.get(beforeIndex).setStartDate(new Date());
                    flights.get(afterIndex).setStartDate(new Date());
                }
            }
        }
    }


    @Override
    public void setShippSpace(ShippingSpace shippSpace) {
        for (int i = 0; i < flights.size(); i++) {
            flights.get(i).setShippingSpace(shippSpace);
        }
        initFlightView();
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

        LogUtil.e("---------multiLineFragment---onDestroy----");
    }
}
