package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.henry.calendarview.DatePickerController;
import com.henry.calendarview.DayPickerView;
import com.henry.calendarview.SimpleMonthAdapter;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.MonthBean;
import com.yzb.card.king.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/20
 * 描  述：可设置入店日期和离店日期
 */
public class AppCalendarPopup implements View.OnClickListener {


     public enum CalendarType {

        DOUBLE, SINGLE
    }

    private TextView tvTitleName;

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private DayPickerView dayPickerView;

    private DataCalendarCallBack calendarCallBack;

    private int selectorIndex = 0;

    private Date startDateTemp;

    private CalendarType type = CalendarType.DOUBLE;

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     * @param postion
     */
    public AppCalendarPopup(Activity activity, int defineHeight, BaseFullPP.ViewPostion postion)
    {

        this.activity = activity;

        Date startDate = new Date();
        Date endDate = DateUtil.addDay(startDate, 1);

        initView(defineHeight, startDate, endDate, postion);

    }

    public AppCalendarPopup(Activity activity, int defineHeight, BaseFullPP.ViewPostion postion,boolean f)
    {

        this.activity = activity;

        Date startDate = new Date();

        initView(defineHeight, startDate, startDate, postion);

    }


    public void setType(CalendarType type)
    {
        this.type = type;
    }

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     * @param postion
     */
    public AppCalendarPopup(Activity activity, int defineHeight, Date startData, Date endDate, BaseFullPP.ViewPostion postion)
    {

        this.activity = activity;

        initView(defineHeight, startData, endDate, postion);

    }

    private void initView(int defineHeight, Date startDate, Date endDate, BaseFullPP.ViewPostion postion)
    {

        baseBottomFullPP = new BaseFullPP(activity, postion);

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_hotel_calendar, null);

        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defineHeight);

            view.setLayoutParams(lp);
        }

        baseBottomFullPP.addChildView(view);

        view.findViewById(R.id.tvCancel).setOnClickListener(this);

        tvTitleName = (TextView) view.findViewById(R.id.tvTitleName);

        dayPickerView = (DayPickerView) view.findViewById(R.id.dpv_calendar);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        DayPickerView.DataModel dataModel = new DayPickerView.DataModel();
        dataModel.yearStart = year;
        dataModel.monthStart = month;
        dataModel.monthCount = 12;
        dataModel.defTag = " ";//
        dataModel.leastDaysNum = 2;
        dataModel.mostDaysNum = 180;

        SimpleMonthAdapter.CalendarDay startDay = new SimpleMonthAdapter.CalendarDay(startDate.getTime());
        SimpleMonthAdapter.CalendarDay endDay = new SimpleMonthAdapter.CalendarDay(endDate.getTime());
        SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays = new SimpleMonthAdapter.SelectedDays<>(startDay, endDay);
        dataModel.selectedDays = selectedDays;

        dayPickerView.setParameter(dataModel, new DatePickerController() {
            @Override
            public void onDayOfMonthSelected(SimpleMonthAdapter.CalendarDay calendarDay)
            {

                if (calendarCallBack != null) {

                    if (type == CalendarType.DOUBLE) {

                        if (selectorIndex == 0) {

                            startDateTemp = calendarDay.getDate();

                            selectorIndex = 1;

                        } else if (selectorIndex == 1) {

                            Date endDate = calendarDay.getDate();

                            if (endDate.getTime() > startDateTemp.getTime()) {
                                selectorIndex = 0;

                                calendarCallBack.onSelectorStartEndDate(startDateTemp, endDate);

                                baseBottomFullPP.dismiss();
                            }
                        }
                    }else if (type == CalendarType.SINGLE) {

                        startDateTemp = calendarDay.getDate();

                        calendarCallBack.onSelectorStartEndDate(startDateTemp, startDateTemp);

                        baseBottomFullPP.dismiss();

                    }
                }
            }

            @Override
            public void onDateRangeSelected(List<SimpleMonthAdapter.CalendarDay> selectedDays)
            {
                // Toast.makeText(activity, "onDateRangeSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void alertSelectedFail(FailEven even)
            {
                // Toast.makeText(activity, "alertSelectedFail", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setTitleName(String titleName){

        tvTitleName.setText(titleName);
    }

    public void showBottomByViewPP(View rootView)
    {

        baseBottomFullPP.show(rootView);

    }

    public void setCalendarCallBack(DataCalendarCallBack calendarCallBack)
    {

        this.calendarCallBack = calendarCallBack;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvCancel:

                baseBottomFullPP.dismiss();
                break;

            default:
                break;
        }
    }

    public interface DataCalendarCallBack {
        /**
         * 选择日历的开始和结束日期
         *
         * @param startDate
         * @param endDate
         */
        void onSelectorStartEndDate(Date startDate, Date endDate);
    }
}
