package com.yzb.card.king.ui.ticket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.ui.appwidget.popup.CalendarPop;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.other.activity.CalendarActivity;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.ui.ticket.activity.PlaneListQJActivity;
import com.yzb.card.king.ui.ticket.activity.PlaneSeachActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/28
 * 描  述：
 */
public class PlaneStarEndFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView tvStraCity;
    private TextView tvEndCity;
    private Button btnSearch;
    private TextView dateTime, dateWeek, hbhStart, hbhEnd;
    private String[] startDates = new String[3];
    private LinearLayout llPlaneDate, llStart, llEnd;
    private TicketHomeFragment fragment = null;
    private String starTime = "";
    private String endTime = "";
    private Flight flight;

    private String starCity;
    private String endCity;

    private int CITY_CODE = 0;
    /**
     * 特殊标签
     */
    public static boolean flagFlag = true;
    private Date date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_starend_city, container, false);
        init(view);
        return view;
    }

    /**
     * 初始化
     *
     * @param view
     */
    private void init(View view)
    {
        fragment = (TicketHomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag("" + 3);
        flight = new Flight(false);
        tvStraCity = (TextView) view.findViewById(R.id.hbh_start);
        tvStraCity.setText(flight.getStartCity().getCityName());
        tvEndCity = (TextView) view.findViewById(R.id.hbh_end);
        tvEndCity.setText(flight.getEndCity().getCityName());
        btnSearch = (Button) view.findViewById(R.id.searchPlane);
        btnSearch.setOnClickListener(this);
        dateTime = (TextView) view.findViewById(R.id.date_plane);
        dateTime.setText(DateUtil.getDate(null, 0));
        llPlaneDate = (LinearLayout) view.findViewById(R.id.ll_plane_date);
        llPlaneDate.setOnClickListener(this);

        llStart = (LinearLayout) view.findViewById(R.id.ll_start);
        llStart.setOnClickListener(this);
        llEnd = (LinearLayout) view.findViewById(R.id.ll_end);
        llEnd.setOnClickListener(this);

        dateWeek = (TextView) view.findViewById(R.id.date_week);
         date = new Date();
        String dateStr = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE);
        startDates[0] = dateStr;
        if (calendarPop == null) {

            calendarPop = new CalendarPop();

            calendarPop.setListener(new OnItemClickListener<CalendarDay>() {
                @Override
                public void onItemClick(CalendarDay data)
                {
                    date = data.getDay();

                    SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
                    starTime = sdf.format(date);
                    endTime = DateUtil.getDateExplain(date);

                    dateTime.setText(starTime);
                    dateWeek.setText(endTime);
                    startDates[0] = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE);

                    calendarPop.dismiss();
                }
            });
        }
    }

    private CalendarPop calendarPop;

    public void getDate(Date date)
    {
        calendarPop.setPositionDate(date);
        calendarPop.showAtLocation(btnSearch, Gravity.TOP, 0, 0);
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.searchPlane:  //搜索
                goListInfo();
                break;
            case R.id.ll_plane_date:    //选择日期

                getDate(date);
                break;
            case R.id.ll_start:
                CITY_CODE = 1;
                CitySelectManager.getInstance().setLoadPlaceFlag(false);
                startActivity(new Intent(getActivity(), SelectPlaceActivity.class));
                break;
            case R.id.ll_end:
                CITY_CODE = 2;
                CitySelectManager.getInstance().setLoadPlaceFlag(false);
                startActivity(new Intent(getActivity(), SelectPlaceActivity.class));
                break;
        }
    }


    @Override
    public void onStart()
    {
        super.onStart();


        if (CitySelectManager.getInstance().getPlace() != null && PlaneSeachActivity.currentIndex == 1)
        {

            if (CITY_CODE == 1)
            {
                tvStraCity.setText(CitySelectManager.getInstance().getPlaceName());
            } else if (CITY_CODE == 2)
            {
                tvEndCity.setText(CitySelectManager.getInstance().getPlaceName());
            }


            CitySelectManager.getInstance().clearData();
        }
    }


    private void goListInfo()
    {
        starCity = tvStraCity.getText().toString();
        endCity = tvEndCity.getText().toString();
        Intent intent = new Intent(getActivity(), PlaneListQJActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("starCity", starCity);
        bundle.putString("endCity", endCity);
        bundle.putStringArray("dateTime", startDates);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
