package com.yzb.card.king.ui.other.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.adapter.CalendarListAdapter;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.WalletHomeInfo;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 2016.8.12 修改priceList; 价格类型int-->float； gengqiyun
 */
public class CalendarActivity extends BaseActivity
{
    /**
     * 日历无价格
     */
    public static final int TYPE_NO_PRICE = 1;
    /**
     * 日历有价格
     */
    public static final int TYPE_HAS_PRICE = 2;
    /**
     * 日历价格标记
     */
    public static final String TYPE = "type";

    private ListView lv_calendar;
    private LinearLayout ll_holiday;
    private List<Map<Integer, String>> holidayList = new ArrayList<>();
    private List<Map<Integer, String>> workList = new ArrayList<>();
    private List<Map<Integer, Integer>> priceList = new ArrayList<>();
    private String startCityId, endCityId;
    private int type;
    private Date travelDate; // 接收的开始日期；
    public static final String SOURCE = "sourceActivity";
    public static String sourceActivityValue; //来源页面；
    public static final String SOURCE_TRAVELDETAIL = "TRAVEL_DETIAL";//旅游详情；
    private String productId; // 旅游产品的id；
    private String lineId; // 线路的id；
    private String basecabincode;
    private CalendarListAdapter adapter;
    private List<View> tabs = new ArrayList<>();
    private Date positionDate;
    private String positionText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initView();
        initListener();
        recvIntentData();
        getHolidayList();
    }

    private void recvIntentData()
    {
        Intent intent = getIntent();
        startCityId = intent.getStringExtra("startCityId");
        endCityId = intent.getStringExtra("endCityId");
        basecabincode = intent.getStringExtra("basecabincode");
        positionDate = (Date) intent.getSerializableExtra("date");
        positionText = intent.getStringExtra("text");
        type = intent.getIntExtra(TYPE, TYPE_NO_PRICE);
        sourceActivityValue = intent.getStringExtra(SOURCE);
        productId = intent.getStringExtra("productId");
        lineId = intent.getStringExtra("lineId");

        Serializable serializable = intent.getSerializableExtra("travelDate");
        if (serializable instanceof Date)
        {
            travelDate = (Date) serializable;
        }
        setAdapter();
    }

    private void initView()
    {
        lv_calendar = (ListView) findViewById(R.id.lv_calendar);
        ll_holiday = (LinearLayout) findViewById(R.id.ll_holiday);
    }

    private void initListener()
    {
        lv_calendar.setOnScrollListener(new AbsListView.OnScrollListener()
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

    private void getHolidayList()
    {

        SimpleRequest< Map<String, JSONArray>> request = new SimpleRequest< Map<String, JSONArray>>("QueryFestival") {
            @Override
            protected  Map<String, JSONArray> parseData(String data)
            {
                return JSON.parseObject(data, Map.class);
            }
        };

        Map<String, Object> param = new HashMap<>();

        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<Map<String, JSONArray>>() {
            @Override
            public void onSuccess(Map<String, JSONArray> festWork)
            {
                String festivalStr = festWork.get("festivalList").toJSONString();
                String workStr = festWork.get("workList").toJSONString();
                setHoliday(festivalStr);
                setWork(workStr);
                loadPriceList();
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
                holidayList.clear();
                workList.clear();
                loadPriceList();
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

    private void loadPriceList()
    {
        if (type == TYPE_HAS_PRICE)
        {
            //来源于旅游；
            if (SOURCE_TRAVELDETAIL.equals(sourceActivityValue))
            {
                getTravelPriceList();
            } else
            {
                getPriceList();
            }
        } else
        {
            adapter.notifyDataSetChanged();
        }
    }

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
                    lv_calendar.setSelection(position);
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
                    View view = View.inflate(CalendarActivity.this, R.layout.calendar_holiday_item, null);
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
        param.put("productId", productId);
        param.put("lineId", lineId);
        param.put("depDate", travelDate == null ? "" : DateUtil.date2String(travelDate, DateUtil.DATE_FORMAT_DATE));
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
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {

            }
        });

//
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
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            protected void setParam(Map<String, String> param)
//            {
//                param.put("productId", productId);
//                param.put("lineId", lineId);
//                param.put("depDate", travelDate == null ? "" : DateUtil.date2String(travelDate, DateUtil.DATE_FORMAT_DATE));
//                LogUtil.i("旅游价格列表入参==" + JSON.toJSONString(param));
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
        CalendarManager.getInstance().clearData();
        finish();
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
        param.put("depCityId", startCityId);
        param.put("arrCityId", endCityId);
        param.put("basecabincode", basecabincode);
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
                adapter.notifyDataSetChanged();
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

    public void setAdapter()
    {
        adapter = new CalendarListAdapter(CalendarActivity.this, holidayList, priceList,workList);
        adapter.setCurDate(travelDate);
        adapter.setPositionDate(positionDate);
        adapter.setPositionText(positionText);
        adapter.setOnDateSelectedListener(new CalendarListAdapter.onDateSelectedListener()
        {
            @Override
            public void onDateSelected(Date date, float price)
            {
                CalendarManager.getInstance().setDate(date);
                CalendarManager.getInstance().setPrice(price);
                Intent intent = new Intent();
                intent.putExtra("date", date);
                intent.putExtra("price", price);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        lv_calendar.setAdapter(adapter);
    }

    @Override
    public void onBackPressed()
    {
        CalendarManager.getInstance().clearData();
        super.onBackPressed();
    }

    /**
     * 是否应用状态栏一体化；
     *
     * @return
     */
    protected boolean isApplyStatusBarTranslucency()
    {
        return true;
    }

}
