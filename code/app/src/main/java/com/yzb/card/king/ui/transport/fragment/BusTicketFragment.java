package com.yzb.card.king.ui.transport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.transport.activity.BusListActivity;
import com.yzb.card.king.ui.other.activity.CalendarActivity;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.StringUtils;

import java.util.Date;

/**
 * Created by ysg on 2016/5/25.
 * first modify  by gengqiyun on 2016/9/7.
 */
public class BusTicketFragment extends BaseFragment implements View.OnClickListener
{

    public static final int REQ_START_PLACE = 1;
    public static final int REQ_END_PLACE = 2;
    public static final int REQ_START_DATE = 3;

    private String startCityName;
    private String startCityId = "1";
    private String endCityName;
    private String endCityId = "1";
    private Date startDate = null;
    private int currentReq;

    private TextView tvStarCity;
    private TextView tvEndCity;
    private TextView tvStartWeek;
    private TextView tvStartDate;

    public static BusTicketFragment newInstance()
    {
        Bundle args = new Bundle();
        BusTicketFragment fragment = new BusTicketFragment();
        fragment.setArguments(args);
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
            tvStartDate.setText(DateUtil.getDate(startDate, 0));
            tvStartWeek.setText(DateUtil.getDateExplain(startDate));
            cm.clearData();
        }
        CitySelectManager cs = CitySelectManager.getInstance();
        if (!StringUtils.isEmpty(cs.getPlaceId()))
        {
            if (currentReq == REQ_START_PLACE)
            {
                startCityName = cs.getPlaceName();
                startCityId = cs.getPlaceId();
                tvStarCity.setText(startCityName);
            }

            if (currentReq == REQ_END_PLACE)
            {
                endCityName = cs.getPlaceName();
                endCityId = cs.getPlaceId();
                tvEndCity.setText(endCityName);
            }
            cs.clearData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_transport_bus, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData()
    {
        startCityName = tvStarCity.getText().toString();
        endCityName = tvEndCity.getText().toString();
        startDate = DateUtil.addDay(new Date(), 1);
        tvStartDate.setText(DateUtil.getDate(startDate, 0));
    }

    private void initView(View view)
    {
        view.findViewById(R.id.ll_end_city).setOnClickListener(this);
        view.findViewById(R.id.ll_start_city).setOnClickListener(this);
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
            case R.id.ll_start_city: //起始城市
                currentReq = REQ_START_PLACE;
                getPlace();
                break;
            case R.id.ll_end_city: //终点城市
                currentReq = REQ_END_PLACE;
                getPlace();
                break;
            case R.id.ll_start_date: //出发时间
                currentReq = REQ_START_DATE;
                getDate();
                break;
            case R.id.btn_search: //搜索
                startSearch();
                break;
        }
    }

    /**
     * 搜索
     */
    private void startSearch()
    {
        Intent intent = new Intent(getActivity(), BusListActivity.class);
        intent.putExtra("departCityId", startCityId);
        intent.putExtra("reachCityId", endCityId);
        intent.putExtra("departCityName", startCityName);
        intent.putExtra("reachCityName", endCityName);
        intent.putExtra("departDate", startDate);
        startActivity(intent);
    }

    /**
     * 选择时间
     */
    private void getDate()
    {
        Intent intent = new Intent(getContext(), CalendarActivity.class);
        intent.putExtra(CalendarActivity.TYPE, CalendarActivity.TYPE_NO_PRICE);
        startActivity(intent);
    }

    /**
     * 选择城市
     */
    private void getPlace()
    {
        Intent intent = new Intent(getContext(), SelectPlaceActivity.class);
        startActivity(intent);
    }

}
