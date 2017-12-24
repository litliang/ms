package com.yzb.card.king.ui.ticket.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.Date;
import java.util.List;

/**
 * 单程碎片
 * Created by dev on 2016/5/5.
 */
public class SingleLineFragment extends BaseFragment implements ITicketFragment {

    private TicketHomeFragment fragment = null;

    private TextView tv_start_date;

    private TextView tv_start_city;

    private TextView tv_end_city;

    private ImageView trunsImage;

    private LinearLayout ll_start_date;

    private TextView tv_start_week;

    private Flight flight;

    private boolean startCity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View  rootView = inflater.inflate(R.layout.ticket_single_line, null, false);

        init(rootView);// 控件初始化

        return rootView;
    }

    private void init(View view) {

        AirTicketHomeActivity airTicketHomeActivity = (AirTicketHomeActivity) getActivity();

        fragment = airTicketHomeActivity.getTicketHomeFragment();

        initView(view);

        initData();

        setListener();

        setDefault(flight);
    }

    private void initData() {

        //检查是否有历史数据
      String singFile = SharePrefUtil.getValueFromSp(getContext(), SharePrefUtil.TICKET_SINGLE_FLIGHT_INFO, null);

      if(singFile != null){

          List<Flight> flightSingleList = JSONArray.parseArray(singFile,Flight.class);

           flight =  flightSingleList.get(0);

           if(fragment.getCangweiTv()!=null&&flight.getShippingSpace() != null&& !TextUtils.isEmpty(flight.getShippingSpace().getQabinName())){

               fragment.getCangweiTv().setText(flight.getShippingSpace().getQabinName());

           }

      }else {

          flight = new Flight(false);

          if (fragment != null) {

              flight.setShippingSpace(fragment.getCangwei());
          }
      }

    }

    private void initView(View view) {

        tv_start_city = (TextView) view.findViewById(R.id.tv_start_city);

        tv_end_city = (TextView) view.findViewById(R.id.tv_end_city);

        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);

        ll_start_date = (LinearLayout) view.findViewById(R.id.ll_start_date);

        tv_start_week = (TextView) view.findViewById(R.id.tv_start_week);

        trunsImage = (ImageView) view.findViewById(R.id.trunsImage);
    }

    private void setListener() {

        tv_start_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCity = true;

                fragment.getPlace();
            }
        });

        tv_end_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCity = false;

                fragment.getPlace();
            }
        });

        trunsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                City end = flight.getEndCity();

                City start = flight.getStartCity();

                flight.setFEndCity(null);

                flight.setFStartCity(end);

                flight.setFEndCity(start);

                setDefault(flight);
            }
        });

        ll_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.getDate(flight.getStartDate(), "去程");
            }
        });
    }

    private void setDefault(Flight flight) {

        if (flight == null) {

            return;

        }

        tv_start_city.setText(flight.getStartCity().getCityName());

        tv_end_city.setText(flight.getEndCity().getCityName());

        tv_start_date.setText(DateUtil.date2String(flight.getStartDate(), DateUtil.DATE_FORMAT_MONTH_DAY));

        tv_start_week.setText(DateUtil.getDateExplain(flight.getStartDate()));

//        //记录单程航班信息
//        if (flight != null) {
//            String singleFlight = JSON.toJSONString(flight);
//
//            SharePrefUtil.saveToSp(getContext(), SharePrefUtil.TICKET_SINGLE_FLIGHT_INFO, singleFlight);
//            LogUtil.e("ticket", "------save----" + singleFlight);
//
//
//        }

    }

    @Override
    public void setPlace(City city) {

        boolean hasSameCity;

        if (startCity) {

            hasSameCity = flight.setFStartCity(city);

        } else {

            hasSameCity = flight.setFEndCity(city);

        }

        if (hasSameCity) {

            UiUtils.shortToast("出发地与目的地不能相同");

        } else {

            setDefault(flight);
        }
    }

    public void setDate(Date date) {
        flight.setStartDate(date);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        setDefault(flight);
    }

    @Override
    public void setShippSpace(ShippingSpace shippSpace) {
        flight.setShippingSpace(shippSpace);
    }

    @Override
    public List<Flight> getFlights() {

        List<Flight> list = new ArrayList<>();

        list.add(flight);

        return list;
    }

}
