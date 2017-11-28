package com.yzb.card.king.ui.ticket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.popup.CalendarPop;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.other.activity.CalendarActivity;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.ui.ticket.activity.PlaneDtDetailActivity;
import com.yzb.card.king.ui.ticket.activity.PlaneSeachActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.RegexUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/28
 * 描  述：
 */
public class PlaneNumFragment extends BaseFragment implements View.OnClickListener {

    private View view;

    private LinearLayout llDate;
    private TextView tvDate;
    private TextView tvWeek;
    private Button searchPlane;
    private EditText hbh_plane;
    private String dateTime;


    private   Date date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_plane_num, container, false);
        init(view);
        return view;
    }

    private void init(View view)
    {
        llDate = (LinearLayout) view.findViewById(R.id.ll_plane_date);
        llDate.setOnClickListener(this);
        tvDate = (TextView) view.findViewById(R.id.date_plane);
        tvDate.setText(DateUtil.getDate(null, 0));
        tvWeek = (TextView) view.findViewById(R.id.date_week);
        hbh_plane = (EditText) view.findViewById(R.id.hbh_plane);
        searchPlane = (Button) view.findViewById(R.id.searchPlane);
        searchPlane.setOnClickListener(this);
        date = new Date();
        dateTime = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE);

        if (calendarPop == null) {

            calendarPop = new CalendarPop();

            calendarPop.setListener(new OnItemClickListener<CalendarDay>() {
                @Override
                public void onItemClick(CalendarDay data)
                {
                     date = data.getDay();
                    SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
                    tvDate.setText(sdf.format(date));
                    tvWeek.setText(DateUtil.getDateExplain(date));
                    dateTime = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE);
                    calendarPop.dismiss();
                }
            });
        }
    }
    private CalendarPop calendarPop;

    public void getDate(Date date)
    {
        calendarPop.setPositionDate(date);
        calendarPop.showAtLocation(searchPlane, Gravity.TOP, 0, 0);
    }



    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.searchPlane:
                if ("".equals(hbh_plane.getText().toString()) || hbh_plane.getText().toString() == null)
                {
                    toastCustom(getString(R.string.plane_please_hbh));
                    return;
                }
                boolean isHbh = RegexUtil.validFlightNumber(hbh_plane.getText().toString());//判断输入的是不是正确的航班号
                if (!isHbh)
                {
                    toastCustom(getString(R.string.ticket_sr_succ));
                    return;
                }
                goDetail();  //搜索详情
                break;
            case R.id.ll_plane_date: //选择日期

                getDate(date);

                break;
        }
    }

    private void goDetail()
    {
        Intent intent = new Intent(getActivity(), PlaneDtDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hbInfo", hbh_plane.getText().toString());
        bundle.putString("hbTime", dateTime);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
