package com.yzb.card.king.ui.transport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.activity.CalendarActivity;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.transport.activity.TrainListActivity;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.util.DateUtil;

import java.util.Date;

/**
 * 陆上交通-->火车票
 * Created by yinsg on 2016/5/25.
 * first modify  by gengqiyun on 2016/9/6.
 */
public class TrainTicketFragment extends BaseFragment implements View.OnClickListener
{
    public static final int REQ_START_PLACE = 1; //出发地；
    public static final int REQ_END_PLACE = 2; //目的地；
    public static final String TYPE_HIGHT_RAIL = "2"; //高铁；
    public static final String TYPE_ALL = "1"; //全部；

    private TextView tvStarCity, tvEndCity, tvStartWeek, tvStartDate;
    private String startCityName = "北京";
    private String startCityId = "1";
    private String endCityName = "上海";
    private String endCityId = "2";
    private Date startDate = new Date();
    private String trainType = TYPE_ALL; //2:高铁; 1:全部;
    private int currentReq = REQ_START_PLACE;

    private SlideButton mySlideButton;

    @Override
    public void onStart()
    {
        super.onStart();
        CalendarManager manager = CalendarManager.getInstance();
        if (null != manager.getDate())
        {
            startDate = manager.getDate();
            tvStartDate.setText(DateUtil.getDate(startDate, 0));
            tvStartWeek.setText(DateUtil.getDateExplain(startDate));
            manager.clearData();
        }

        CitySelectManager cityManager = CitySelectManager.getInstance();
        if (!TextUtils.isEmpty(cityManager.getPlaceId()))
        {
            //出发地；
            if (currentReq == REQ_START_PLACE)
            {
                if (endCityId.equals(cityManager.getPlaceId()))
                {
                    toastCustom(getString(R.string.transport_st_end_place_same));
                    return;
                }
                startCityName = cityManager.getPlaceName();
                startCityId = cityManager.getPlaceId();
                tvStarCity.setText(startCityName);
            }

            //目的地；
            if (currentReq == REQ_END_PLACE)
            {
                if (startCityId.equals(cityManager.getPlaceId()))
                {
                    toastCustom(getString(R.string.transport_st_end_place_same));
                    return;
                }
                endCityName = cityManager.getPlaceName();
                endCityId = cityManager.getPlaceId();
                tvEndCity.setText(endCityName);
            }
            cityManager.clearData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_transport_train, container, false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        view.findViewById(R.id.ll_end_city).setOnClickListener(this);
        view.findViewById(R.id.ll_start_city).setOnClickListener(this);
        view.findViewById(R.id.ll_start_date).setOnClickListener(this);

        tvStarCity = (TextView) view.findViewById(R.id.tv_start_city);
        tvEndCity = (TextView) view.findViewById(R.id.tv_end_city);
        tvStartDate = (TextView) view.findViewById(R.id.tv_start_date);
        tvStartDate.setText(DateUtil.getDate(new Date(), 0));
        tvStartWeek = (TextView) view.findViewById(R.id.tv_start_week);
        tvStartWeek.setText(DateUtil.getDateExplain(new Date()));

        view.findViewById(R.id.btn_search).setOnClickListener(this);

        mySlideButton = (SlideButton) view.findViewById(R.id.my_slide_button);
        mySlideButton.setOnToggleStateChangeListener(highRailChangeListener);
    }

    /**
     * 高铁筛选条件切换；
     */
    private SlideButton.OnToggleStateChangeListener highRailChangeListener = new SlideButton.OnToggleStateChangeListener()
    {
        @Override
        public void onToggleStateChange(SlideButton.ToggleState state)
        {
            if (state == SlideButton.ToggleState.open)
            {
                trainType = TYPE_HIGHT_RAIL;//高铁
            } else
            {
                trainType = TYPE_ALL;//全部
            }
        }
    };

    public static TrainTicketFragment newInstance()
    {
        return new TrainTicketFragment();
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.ll_start_city: //起始城市
                currentReq = REQ_START_PLACE;
                intent.putExtra(SelectPlaceActivity.CITY_ID, GlobalApp.getSelectedCity().cityId);
                intent.putExtra(SelectPlaceActivity.SIGN, AppConstant.FILE_TRAIN_NAME);//标记
                intent.setClass(getContext(), SelectPlaceActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_end_city: //终点城市
                currentReq = REQ_END_PLACE;
                intent.putExtra(SelectPlaceActivity.CITY_ID, GlobalApp.getSelectedCity().cityId);
                intent.putExtra(SelectPlaceActivity.SIGN, AppConstant.FILE_TRAIN_NAME);//标记
                intent.setClass(getContext(), SelectPlaceActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_start_date: //出发时间
                intent.setClass(getContext(), CalendarActivity.class);
                intent.putExtra(CalendarActivity.TYPE, CalendarActivity.TYPE_NO_PRICE);
                startActivity(intent);
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
        if (!valid())
        {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("startCityName", startCityName);
        bundle.putString("startCityId", startCityId);
        bundle.putString("endCityName", endCityName);
        bundle.putString("endCityId", endCityId);
        bundle.putString("startDate", DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE));
        bundle.putString("trainType", trainType);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(getActivity(), TrainListActivity.class);
        startActivity(intent);
    }

    //验证
    private boolean valid()
    {
        if (!TextUtils.isEmpty(startCityName) && startCityName.equals(endCityName))
        {
            toastCustom(getString(R.string.transport_st_end_place_same));
            return false;
        }
        return true;
    }

}


