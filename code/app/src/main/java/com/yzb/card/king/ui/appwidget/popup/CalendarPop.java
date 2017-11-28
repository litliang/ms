package com.yzb.card.king.ui.appwidget.popup;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.WalletHomeInfo;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.ui.other.bean.CalendarMonth;
import com.yzb.card.king.ui.other.holder.TitleGridHolder;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/8 15:05
 */
public class CalendarPop extends PopupWindow
{
    private View mRoot;
    private ListView listView;
    private AbsBaseListAdapter adapter;
    private List<CalendarMonth> dataList = new ArrayList<>();
    private int monthCount = 13;//显示的月数
    private List<Map<Integer, String>> holidayList = new ArrayList<>();
    private List<Map<Integer, String>> workList = new ArrayList<>();
    private List<Map<Integer, Integer>> priceList = new ArrayList<>();
    private LinearLayout ll_holiday;
    private Map<String, String> ticketParams;
    private Map<String, String> travelParams;
    private List<View> tabs = new ArrayList<>();
    private Date positionDate;
    private String positionText;
    private OnItemClickListener<CalendarDay> listener;
    private View headerLeft;
    private ImageView headerLeftImage;
    private TextView headerTitle;
    private Date oneYearLater;
    private Date startDate = new Date();
    private CalendarDay positionDay;

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public int getMonthCount()
    {
        return monthCount;
    }

    public void setMonthCount(int monthCount)
    {
        this.monthCount = monthCount;
    }

    public void setListener(OnItemClickListener<CalendarDay> listener)
    {
        this.listener = listener;
    }

    public Date getPositionDate()
    {
        return positionDate;
    }

    public void setPositionDate(Date positionDate)
    {
        this.positionDate = positionDate;
        setPositionDay();
    }

    public String getPositionText()
    {
        return positionText;
    }

    public void setPositionText(String positionText)
    {
        this.positionText = positionText;
    }

    public void setTicketParams(Map<String, String> ticketParams)
    {
        this.ticketParams = ticketParams;
        getPriceList();
    }

    public void setTravelParams(Map<String, String> travelParams)
    {
        this.travelParams = travelParams;
        getTravelPriceList();
    }

    public CalendarPop()
    {
        initView();
        initListener();
        initData();
        init();
    }

    private void init()
    {
        this.setContentView(mRoot);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        setBackgroundDrawable(dw);
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.pop_calendar);
        headerLeft = mRoot.findViewById(R.id.headerLeft);
        headerLeftImage = (ImageView) mRoot.findViewById(R.id.headerLeftImage);
        headerTitle = (TextView) mRoot.findViewById(R.id.headerTitle);
        listView = (ListView) mRoot.findViewById(R.id.listView);
        ll_holiday = (LinearLayout) mRoot.findViewById(R.id.ll_holiday);
    }

    private void initListener()
    {
        listView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            private int currentState = SCROLL_STATE_IDLE;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {
                if (scrollState == SCROLL_STATE_FLING && currentState != SCROLL_STATE_FLING)
                {
                    refreshTabs(-1);
                }
                currentState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {

            }
        });
    }

    private void initData()
    {
        setHeader();
        setAdapter();
        getHolidayList();
    }

    private void setHeader()
    {
        headerLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        headerLeftImage.setImageResource(R.mipmap.icon_back_white);
        headerLeftImage.setVisibility(View.VISIBLE);
        headerTitle.setText("日历");
    }

    private void initDataList()
    {
        //2017.2.8
        dataList.clear();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getStartDate());
        calendar.add(Calendar.YEAR, 1);
        oneYearLater = calendar.getTime();
        calendar.setTime(startDate);
        for (int i = 0; i < monthCount; i++)
        {
            CalendarMonth month = new CalendarMonth();
            month.setTitle(DateUtil.date2String(calendar.getTime(), DateUtil.DATE_FORMAT_DATE_MONTH));
            Map<Integer, String> holidayMap = null;
            Map<Integer, String> workMap = null;
            Map<Integer, Integer> priceMap = null;
            if (i < holidayList.size())
            {
                holidayMap = holidayList.get(i);
            }
            if (i < workList.size())
            {
                workMap = workList.get(i);
            }
            if (i < priceList.size())
            {
                priceMap = priceList.get(i);
            }
            month.setDayList(getDayList(calendar, holidayMap, workMap, priceMap));
            dataList.add(month);
        }

        adapter.notifyDataSetChanged();
    }

    @NonNull
    private Date getStartDate()
    {
        return startDate;
    }

    private void setPositionDay()
    {
        if (positionDay != null) positionDay.setSelected(false);
        positionDay = findDay(positionDate);
        if (positionDay != null)
        {
            positionDay.setSelected(true);
            positionDay.setBottomText(positionText);
        }
    }

    private List<CalendarDay> getDayList(Calendar calendar, Map<Integer, String> holidayMap,
                                         Map<Integer, String> workMap, Map<Integer, Integer> priceMap)
    {
        List<CalendarDay> list = new ArrayList<>();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int start = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int length = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String firstHolidayStr = null;
        for (int i = 0; i < 42; i++)
        {
            if (i >= start && i < length + start)
            {
                CalendarDay day = new CalendarDay(calendar.getTime());
                day.setInOneYear(isInOneYear(day.getDay()));

                int dayIndex = calendar.get(Calendar.DAY_OF_MONTH);
                if (holidayMap != null)
                {
                    String holiday = holidayMap.get(dayIndex);
                    if (!TextUtils.isEmpty(holiday))
                    {
                        day.setRightText("休");
                        if (!holiday.equals(firstHolidayStr))
                        {
                            day.setHoliday(holidayMap.get(dayIndex));
                        }
                        firstHolidayStr = holiday;
                    }
                }
                if (workMap != null)
                {
                    if (workMap.containsKey(dayIndex))
                    {
                        day.setRightText("班");
                    }
                }
                if (priceMap != null)
                {
                    if (priceMap.containsKey(dayIndex))
                    {
                        day.setBottomText(UiUtils.getString(R.string.train_rmb) + priceMap.get(dayIndex));
                    }
                }
                list.add(day);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            } else
            {
                list.add(null);
            }
        }
        return list;
    }

    private CalendarDay findDay(Date date)
    {
        if (date == null) return null;
        CalendarMonth calendarMonth = findMonth(date);
        if (calendarMonth != null)
        {
            List<CalendarDay> calendarDays = calendarMonth.getDayList();
            for (int i = 0; i < calendarDays.size(); i++)
            {
                if (calendarDays.get(i) != null &&
                        DateUtil.isTheSameDay(date, calendarDays.get(i).getDay()))
                {
                    return calendarDays.get(i);
                }
            }
        }
        return null;
    }

    private CalendarMonth findMonth(Date date)
    {
        String title = DateUtil.date2String(date, "yyyy年MM月");
        if (dataList.size() <= 0) return null;
        for (int i = 0; i < monthCount; i++)
        {
            if (title.equals(dataList.get(i).getTitle()))
            {
                return dataList.get(i);
            }
        }
        return null;
    }

    private boolean isInOneYear(Date date)
    {
        long time = date.getTime();

        return !(time < startDate.getTime() || time > oneYearLater.getTime());
    }

    private void setAdapter()
    {
        adapter = new AbsBaseListAdapter<CalendarMonth>(dataList)
        {
            @Override
            protected Holder getHolder(int position)
            {
                TitleGridHolder holder = new TitleGridHolder();
                holder.setListener(listener);
                return holder;
            }
        };
        listView.setAdapter(adapter);
    }

    private void getHolidayList()
    {

        SimpleRequest< Map<String, JSONArray>> request = new SimpleRequest< Map<String, JSONArray>>("QueryFestival")
        {
            @Override
            protected  Map<String, JSONArray> parseData(String data)
            {
                return JSON.parseObject(data, Map.class);
            }
        };
        final Map<String, Object> params = new HashMap<>();

        request.setParam(params);
        request.sendRequestNew(new BaseCallBack<Map<String, JSONArray>>()
        {
            @Override
            public void onSuccess(Map<String, JSONArray> festWork)
            {
                String festivalStr = festWork.get("festivalList").toJSONString();
                String workStr = festWork.get("workList").toJSONString();
                setHoliday(festivalStr);
                setWork(workStr);
                initDataList();
            }

            @Override
            public void onFail(Error error)
            {
                holidayList.clear();
                workList.clear();
                initDataList();
            }
        });

    }

    private void setWork(String workStr)
    {
        if (!TextUtils.isEmpty(workStr))
        {
            List<Map> list = JSON.parseArray(workStr, Map.class);
            transMap(list, workList);
        } else
        {
            workList.clear();
        }
    }

    private void setHoliday(String festivalStr)
    {
        if (!TextUtils.isEmpty(festivalStr))
        {
            List<Map> list = JSON.parseArray(festivalStr, Map.class);
            transMap(list, holidayList);
            initHolidayView();
            initClick();
        } else
        {
            holidayList.clear();
        }
    }
/*
    private void loadPriceList()
    {
        if (travelParams != null)
        {
            getTravelPriceList();
        } else if (ticketParams != null)
        {
            getPriceList();
        } else
        {
            initDataList();
        }
    }*/

    private void transMap(List<Map> orgList, List<Map<Integer, String>> aimList)
    {
        aimList.clear();
        if (null != orgList && !orgList.isEmpty())
        {
            for (int i = 0; i < orgList.size(); i++)
            {
                Map<Integer, String> map = new HashMap<>();
                for (Object key : orgList.get(i).keySet())
                {
                    map.put(Integer.parseInt(key.toString()), String.valueOf(orgList.get(i).get(key)));
                }
                aimList.add(map);
            }
        }
    }

    private void initClick()
    {
        for (int i = 0; i < tabs.size(); i++)
        {
            final int index = i;
            tabs.get(i).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position = (int) v.getTag();
                    refreshTabs(index);
                    listView.setSelection(position);
                }
            });
        }
    }

    private void initHolidayView()
    {
        tabs.clear();
        for (int i = 0; i < holidayList.size(); i++)
        {
            Map<Integer, String> map = holidayList.get(i);
            String temp = "";
            for (Integer key : map.keySet())
            {
                String text = map.get(key);
                if (!temp.equals(text))
                {
                    temp = text;
                    View view = UiUtils.inflate(R.layout.calendar_holiday_item);
                    TextView textView = (TextView) view.findViewById(R.id.tv_holiday);
                    view.setTag(i);
                    textView.setText(text);
                    ll_holiday.addView(view);
                    tabs.add(view);
                }
            }
        }
        refreshTabs(0);
    }

    /**
     * 获取旅游价格列表；
     *
     * @author gengqiyun
     * @date 2016.8.12
     */
    private void getTravelPriceList()
    {

        SimpleRequest<List<Map>> request = new SimpleRequest<List<Map>>(CardConstant.card_app_querytravellineamount)
        {
            @Override
            protected List<Map> parseData(String data)
            {
                return JSON.parseArray(data, Map.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.putAll(travelParams);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<List<Map>>()
        {
            @Override
            public void onSuccess(List<Map> list)
            {

                if (null == list || list.isEmpty())
                {
                    return;
                }
                priceList.clear();
                for (int i = 0; i < list.size(); i++)
                {
                    Map<Integer, Integer> map = new HashMap<>();
                    for (Object key : list.get(i).keySet())
                    {
                        map.put(Integer.parseInt(key.toString()), floatStr2int(list.get(i).get(key)));
                    }
                    priceList.add(map);
                }
                initDataList();
            }

            @Override
            public void onFail(Error error)
            {

            }
        });

//        new CommentTask(CardConstant.card_app_querytravellineamount)
//        {
//            @Override
//            protected void parseJson(String data)
//            {
//                List<Map> list = JSON.parseArray(data, Map.class);
//                if (null == list || list.isEmpty())
//                {
//                    return;
//                }
//                priceList.clear();
//                for (int i = 0; i < list.size(); i++)
//                {
//                    Map<Integer, Integer> map = new HashMap<>();
//                    for (Object key : list.get(i).keySet())
//                    {
//                        map.put(Integer.parseInt(key.toString()), floatStr2int(list.get(i).get(key)));
//                    }
//                    priceList.add(map);
//                }
//                initDataList();
//            }
//
//            @Override
//            protected void setParam(Map<String, String> param)
//            {
//                param.putAll(travelParams);
//            }
//        }.execute();
    }

    private void refreshTabs(int position)
    {
        int count = ll_holiday.getChildCount();
        for (int i = 0; i < count; i++)
        {
            LinearLayout ll = (LinearLayout) ll_holiday.getChildAt(i);
            ll.getChildAt(0).setEnabled(i == position);
        }
    }

    private void refreshTabs(View v)
    {
        int count = ll_holiday.getChildCount();
        for (int i = 0; i < count; i++)
        {
            LinearLayout ll = (LinearLayout) ll_holiday.getChildAt(i);
            ll.getChildAt(0).setEnabled(ll == v);
        }
    }

    public void back(View v)
    {
        dismiss();
    }

    public void getPriceList()
    {
        SimpleRequest< List<Map>> request = new SimpleRequest< List<Map>>("MonthlyFareInfor") {
            @Override
            protected  List<Map> parseData(String data)
            {
                return JSON.parseArray(data, Map.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.putAll(ticketParams);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack< List<Map>>() {
            @Override
            public void onSuccess( List<Map> list)
            {


                if (null == list || list.isEmpty())
                    return;

                priceList.clear();
                for (int i = 0; i < list.size(); i++)
                {
                    Map<Integer, Integer> map = new HashMap<>();
                    for (Object key : list.get(i).keySet())
                    {
                        map.put(Integer.parseInt(key.toString()), floatStr2int(String.valueOf(list.get(i).get(key))));
                    }
                    priceList.add(map);
                }
                initDataList();
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });

    }

    private int floatStr2int(Object object)
    {
        int value = 0;
        if (object != null)
        {
            String str = String.valueOf(object);
            if (!TextUtils.isEmpty(str) && !"null".equals(str))
            {
                value = (int) Float.parseFloat(str);
            }
        }
        return value;
    }

}
