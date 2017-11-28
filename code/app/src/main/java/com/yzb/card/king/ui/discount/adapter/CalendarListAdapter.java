package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yzb.card.king.ui.appwidget.CalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dev on 2016/5/11.
 */
public class CalendarListAdapter extends BaseAdapter
{
    private final Date today;
    private Date positionDate;
    private String positionText;
    private Context context;
    private Calendar calendar;
    private List<Map<Integer, String>> holidayList;
    private List<Map<Integer, Integer>> priceList;
    private List<Map<Integer, String>> workList;
    private Date recvDate;  // 当前的日期；
    private Date[] dates = new Date[13];

    public CalendarListAdapter(Context context, List<Map<Integer, String>> holidayList,
                               List<Map<Integer, Integer>> priceList, List<Map<Integer, String>> workList)
    {
        this.holidayList = holidayList;
        this.priceList = priceList;
        this.context = context;
        this.workList = workList;
        calendar = Calendar.getInstance();
        today = new Date();
        calendar.setTime(today);
        for (int i = 0; i < 13; i++)
        {
            dates[i] = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
        }
    }


    public Date getPositionDate()
    {
        return positionDate;
    }

    public void setPositionDate(Date positionDate)
    {
        this.positionDate = positionDate;
    }

    public String getPositionText()
    {
        return positionText == null ? "" : positionText;
    }

    public void setPositionText(String positionText)
    {
        this.positionText = positionText;
    }

    public void setCurDate(Date recvDate)
    {
        this.recvDate = recvDate;
    }

    @Override
    public int getCount()
    {
        return 13;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CalendarView calendarView = new CalendarView(context, dates[position]);
        calendarView.setPositionedDate(getPositionDate());
        calendarView.setPositionText(getPositionText());
        //2016.7.8  gengqiyun 添加；
        if (recvDate != null)
        {
            calendarView.setYellowDate(recvDate);
        }

        if (holidayList != null)
        {
            if (position < holidayList.size())
            {
                calendarView.setHolidayMap(holidayList.get(position));
            }
        }

        if (priceList != null)
        {
            if (position < priceList.size())
            {
                calendarView.setPriceMap(priceList.get(position));
            }
        }

        if (workList != null)
        {
            if (position < workList.size())
            {
                calendarView.setWorkMap(workList.get(position));
            }
        }
        calendarView.setOnItemClickListener(new CalendarView.OnItemClickListener()
        {
            @Override
            public void OnItemClick(Date date, float price)
            {
                listener.onDateSelected(date, price);
            }
        });
        return calendarView;
    }

    private onDateSelectedListener listener;

    public void setOnDateSelectedListener(onDateSelectedListener listener)
    {
        this.listener = listener;
    }

    public interface onDateSelectedListener
    {
        void onDateSelected(Date date, float price);
    }
}
