package com.yzb.card.king.ui.transport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.transport.activity.ShipLineActivity;
import com.yzb.card.king.ui.transport.activity.ShipListActivity;
import com.yzb.card.king.ui.other.activity.CalendarActivity;
import com.yzb.card.king.ui.transport.bean.ShipRoute;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.util.DateUtil;

import java.util.Date;

/**
 * Created by dev on 2016/5/25.
 * first modify  by gengqiyun on 2016/9/6. MVP优化；
 */
public class ShipTicketFragment extends BaseFragment implements View.OnClickListener
{
    private String startCityName;
    private String endCityName;
    private String startCityId;
    private String endCityId;
    private Date startDate;
    private TextView tvStarCity;
    private TextView tvEndCity;
    private TextView tvStartWeek;
    private TextView tvStartDate;

    public static ShipTicketFragment newInstance()
    {
        ShipTicketFragment fragment = new ShipTicketFragment();
        return fragment;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        CalendarManager cm = CalendarManager.getInstance();
        if (null != cm.getDate())
        {
            startDate = cm.getDate();
            setDate();
            cm.clearData();
        }
        ShipLineActivity shipLineActivity = ShipLineActivity.getInstance();
        ShipRoute.Route route = shipLineActivity.selectedRoute;
        if (null != route)
        {
            startCityId = route.startCityId;
            startCityName = route.startCityName;
            endCityId = route.endCityId;
            endCityName = route.endCityName;
            setCities();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_transport_ship, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData()
    {
        startCityName = "北京";
        endCityName = "上海";
        startCityId = "1";
        endCityId = "2";
        startDate = new Date();
        setCities();
        setDate();
    }

    private void initView(View view)
    {
        view.findViewById(R.id.ll_route).setOnClickListener(this);
        view.findViewById(R.id.ll_start_date).setOnClickListener(this);

        tvStarCity = (TextView) view.findViewById(R.id.tv_start_city);
        tvEndCity = (TextView) view.findViewById(R.id.tv_end_city);
        tvStartDate = (TextView) view.findViewById(R.id.tv_start_date);
        tvStartWeek = (TextView) view.findViewById(R.id.tv_start_week);

        view.findViewById(R.id.btn_search).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_route: //航线
                getRoute();
                break;
            case R.id.ll_start_date: //出发时间
                getDate();
                break;
            case R.id.btn_search: //搜索
                startSearch();
                break;
        }
    }

    /**
     * 选择航线
     */
    private void getRoute()
    {
        Intent intent = new Intent(getActivity(), ShipLineActivity.class);
        startActivity(intent);
    }

    /**
     * 搜索
     */
    private void startSearch()
    {
        Intent intent = new Intent(getContext(), ShipListActivity.class);
        intent.putExtra("startCityId", startCityId);
        intent.putExtra("endCityId", endCityId);
        intent.putExtra("startCityName", startCityName);
        intent.putExtra("endCityName", endCityName);
        intent.putExtra("startDate", startDate);
        startActivity(intent);

    }

    /**
     * 选择时间
     */
    private void getDate()
    {
        Intent intent = new Intent(getContext(), CalendarActivity.class);
        intent.putExtra("type", CalendarActivity.TYPE_NO_PRICE);
        startActivity(intent);
    }

    private void setCities()
    {
        tvStarCity.setText(startCityName);
        tvEndCity.setText(endCityName);
    }

    private void setDate()
    {
        tvStartDate.setText(DateUtil.getDate(startDate, 0));
        tvStartWeek.setText(DateUtil.getDateExplain(startDate));
    }
}
