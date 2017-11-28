package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.FilterCollection;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.bean.travel.TravelDepartureTimeFestivalBean;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.activity.CalendarActivity;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.travel.activity.TravelLineListActivity;
import com.yzb.card.king.ui.travel.bean.TravelScreenBean;
import com.yzb.card.king.ui.travel.holder.TravelListHolder;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sushuiku on 2016/11/11.
 * 旅游出发时间选择
 */

public class TravelDepartrueTimePop extends PopupWindow {

    private LinearLayout startTimeLayout, lastTimeLayout, goBackLayout;
    private RelativeLayout resetLayout, completeLayout;
    //private String dateTime;
    private TextView startTimeTV, lastTimeTV, startTimeTextView, testTv;
    private boolean isStartTime = true;
    //    private GridView gridView;
    private ListView listView;
    private List<FilterCollection> orgList = new ArrayList<>();
    private List<FilterCollection> listData;
    private List<TravelDepartureTimeFestivalBean> festivalList = new ArrayList<>();
    private List<FilterType> typeList = new ArrayList<>();
    private Date elaryDate, lateDate;
    private Date nowDate = new Date();
    private View rootView;
    private Activity activity;
    private FilterCollection travelFilterCollection;
    private AbsBaseListAdapter<FilterCollection> listAdapter;
    private TravelScreenBean travelScreenBean;
    private String star = "";
    private String end = "";

    private int choseHot = 1;

    private int choseWeight = 1;

    public TravelDepartrueTimePop(Activity activity, TravelScreenBean travelScreenBean)
    {
        this.travelScreenBean = travelScreenBean;
        this.activity = activity;
        initView();
        init();
        setListAdapter();
        loadListData(listData);
    }

    protected void loadListData(List<FilterCollection> listData)
    {
        SimpleRequest simpleRequest = new SimpleRequest(CardConstant.TRAVELQUERYFESTIVAL2);
//        SimpleRequest simpleRequest = new SimpleRequest("ScreenQuery");
//        simpleRequest.setParam(getParam());
        simpleRequest.sendRequest(new FilterCallBack(listData));
    }

    private void setListAdapter()
    {
        listData = new ArrayList<>();
        listAdapter = new ListAdapter(listData);
        listView.setAdapter(listAdapter);
    }


    protected Map<String, Object> getParam()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("industryId", 8);
        return param;
    }

    private String startStr = null;
    private String endStr = null;
    /**
     * 设置查询的最早和最晚日期
     *
     * @param startStr 2017-04-07\
     * @param endStr   2017-04-07\
     */
    public void setQueryDateStartEnd(String startStr, String endStr)
    {

        this.startStr = startStr;

        this.endStr = endStr;

        dateHandler.sendEmptyMessageDelayed(0,500);
    }


    private Handler dateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            if (!TextUtils.isEmpty(startStr)) {

                startTimeTV.setText(startStr);

                elaryDate = new Date(Utils.toTimestamp(startStr,7));
            }else{

                startTimeTV.setText("");

                elaryDate = null;
            }

            if (!TextUtils.isEmpty(endStr)) {
                lastTimeTV.setText(endStr);

                lateDate = new Date(Utils.toTimestamp(endStr,7));
            }else{

                lastTimeTV.setText("");
                lateDate = null;
            }


        }
    };


class FilterCallBack extends HttpCallBackImpl {
    private List<FilterCollection> listData;

    public FilterCallBack(List<FilterCollection> listData)
    {
        super();
        this.listData = listData;
    }

    @Override
    public void onSuccess(Object o)
    {
        festivalList = JSONArray.parseArray(String.valueOf(o), TravelDepartureTimeFestivalBean.class);
        for (int i = 0; i < festivalList.size(); i++) {
            FilterType filterType = new FilterType();
            filterType.setName(festivalList.get(i).getFestivalName());
//                filterType.setName("春节");
            filterType.setStarDate(festivalList.get(i).getStartDate());
            filterType.setEndDate(festivalList.get(i).getEndDate());
            filterType.setChildType(i + 1 + "");
            filterType.setType("1");
//                LogUtil.i("festivalList==2 "+ festivalList.get(i).getFestivalName());
            typeList.add(filterType);
        }

        travelFilterCollection = new FilterCollection("1", "热门节日", typeList, false);

        orgList.add(travelFilterCollection);
        initList(orgList);
    }

    private void initList(List<FilterCollection> list)
    {
        listData.clear();
        if (list != null && list.size() > 0) {
            listData.addAll(list);
        }
        addFirstItem();
        timeReset();

    }

    @Override
    public void onFailed(Object o)
    {
        initList(null);
    }
}


    protected void addFirstItem()
    {
        for (int i = 0; i < listData.size(); i++) {

            FilterType filterType = new FilterType();
            filterType.setName("不限");
            filterType.setStarDate("");
            filterType.setEndDate("");
            listData.get(i).getChildList().add(0, filterType);
        }
    }

class ListAdapter extends AbsBaseListAdapter<FilterCollection> {

    public ListAdapter(List<FilterCollection> list)
    {
        super(list);
    }

    @Override
    public int getViewTypeCount()
    {
        return super.getViewTypeCount() + 2;
    }

    @Override
    public int getItemViewType(int position)
    {
        FilterCollection item = getList().get(position);
        if ("评分".equals(item.getName())) {
            return 1;
        } else if ("早餐/房型".equals(item.getName())) {
            return 2;
        } else if ("public".equals(item.getType())) {
            return 3;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return super.getView(position, convertView, parent);
    }

    @Override
    protected Holder getHolder(int position)
    {
        TravelListHolder listHolder = new TravelListHolder(R.layout.travel_filter_pop_list_item);
        listHolder.setMaxLine(getMaxLine());

        if (1 == getItemViewType(position)) {
            listHolder.setGridParam(4, UiUtils.dp2px(15));
        } else if (2 == getItemViewType(position)) {
            listHolder.setGridParam(4, UiUtils.dp2px(15));
        } else if (3 == getItemViewType(position)) {
            listHolder.hideTextView();
        } else {

        }
        return listHolder;
    }
}

    /**
     * 每种类型显示最多行数
     *
     * @return
     */
    protected int getMaxLine()
    {
        return 2;
    }

    public void initView()
    {
        rootView = UiUtils.inflate(R.layout.activity_travel_departruetime);
        startTimeTextView = (TextView) rootView.findViewById(R.id.starttime_tv);
        completeLayout = (RelativeLayout) rootView.findViewById(R.id.complete_layout);
        resetLayout = (RelativeLayout) rootView.findViewById(R.id.reset_layout);
        startTimeLayout = (LinearLayout) rootView.findViewById(R.id.starttime_layout);
        lastTimeLayout = (LinearLayout) rootView.findViewById(R.id.lasttime_layout);
        startTimeTV = (TextView) rootView.findViewById(R.id.elarytime);
        lastTimeTV = (TextView) rootView.findViewById(R.id.lasttime);
        goBackLayout = (LinearLayout) rootView.findViewById(R.id.panel_back);
        listView = (ListView) rootView.findViewById(R.id.gridview);

        ClickListener listener = new ClickListener();
        completeLayout.setOnClickListener(listener);
        resetLayout.setOnClickListener(listener);
        startTimeLayout.setOnClickListener(listener);
        lastTimeLayout.setOnClickListener(listener);
        goBackLayout.setOnClickListener(listener);
    }

    //
    public void onStart()
    {
        if (null != CalendarManager.getInstance().getDate()) {
            choseWeight = 2;

            getDate(CalendarManager.getInstance().getDate());

            CalendarManager.getInstance().clearData();
        }
    }


    private long tempStartDataSys = 0L;

    private long tempEndDatSys = 0L;

    private void getDate(Date data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATE);

        if (isStartTime) {

            long startDateLong = data.getTime();
            if (startDateLong <= tempEndDatSys || tempEndDatSys == 0) {
                startTimeTV.setText(sdf.format(data));
                elaryDate = data;
                tempStartDataSys = startDateLong;
            } else {

                startTimeTV.setText("");
                elaryDate = null;
                tempStartDataSys = 0L;
                ToastUtil.i(GlobalApp.getInstance(), "最晚出发时间不能比最早出发时间早");
            }

        } else {
            long endDateLong = data.getTime();
            if (endDateLong >= tempStartDataSys) {
                lastTimeTV.setText(sdf.format(data));
                lateDate = data;
                tempEndDatSys = endDateLong;
            } else {

                lateDate = null;
                lastTimeTV.setText("");
                tempEndDatSys = 0L;
                ToastUtil.i(GlobalApp.getInstance(), "最晚出发时间不能比最早出发时间早");
            }


        }
    }

class ClickListener implements View.OnClickListener {
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.starttime_layout: //选择最早出发时间
                startCalendarActivity();
                isStartTime = true;
                break;
            case R.id.lasttime_layout: // 选择最晚出发时间
                startCalendarActivity();
                isStartTime = false;
                break;
            case R.id.reset_layout: // 重置
                timeReset();
                break;
            case R.id.complete_layout: //完成

                timeCompele();
                break;
            case R.id.panel_back:
                dismiss();
                break;


        }

    }
}

    // 时间选择完成
    private void timeCompele()
    {

        if (elaryDate != null && lateDate != null) {
            if (DateUtil.naturalDaysBetween(elaryDate, lateDate) < 0) {
                ToastUtil.i(activity, "出发最晚日期不能小于最早日期");
                return;
            } else if (DateUtil.naturalDaysBetween(elaryDate, lateDate) > 31) {
                ToastUtil.i(activity, "最早出发时间和最晚出发时间时间差为1个月内");
                return;
            }
            // 最早出发日期有，无最晚日期
        } else if (elaryDate != null && lateDate == null) {
            if (DateUtil.naturalDaysBetween(nowDate, elaryDate) < 0) {
                elaryDate = nowDate;
            }

            lateDate = DateUtil.addDay(elaryDate, 7);
            // 最早出发时间无，有最晚出发时间
        } else if (elaryDate == null && lateDate != null) {
            if (DateUtil.naturalDaysBetween(nowDate, lateDate) < 0) {
                ToastUtil.i(activity, "出发最晚日期不能晚于今日");
                return;
            } else {
                elaryDate = DateUtil.addDay(lateDate, -7);
                if (DateUtil.naturalDaysBetween(nowDate, elaryDate) < 0) {
                    elaryDate = nowDate;
                }

            }
        }
        if (choseWeight == 1) {
            star = "";
            end = "";
        } else {
            star = DateUtil.date2String(elaryDate, DateUtil.DATE_FORMAT_DATE);
            end = DateUtil.date2String(lateDate, DateUtil.DATE_FORMAT_DATE);
        }

         /*
           对最早和最晚出发日期特殊字段处理
         */
        if (elaryDate!=null) {

            travelScreenBean.setStartStr(startTimeTV.getText().toString());
        }else{

            travelScreenBean.setStartStr(null);
        }

        if (lateDate!=null) {
            travelScreenBean.setEndStr(lastTimeTV.getText().toString());
        }else{
            travelScreenBean.setEndStr(null);
        }


        confirm();

    }

    private void confirm()
    {
        List<FilterType> list = new ArrayList<>();
        String starDate = "";
        String endDate = "";
        for (int i = 0; i < orgList.size(); i++) {
            FilterCollection collection = orgList.get(i);
            for (int j = 0; j < collection.getChildList().size(); j++) {
                FilterType type = collection.getChildList().get(j);
                if (type.isSelected() && !"不限".equals(type.getName())) {
                    list.add(type);
                    starDate += type.getStarDate() + ",";
                    endDate += type.getEndDate() + ",";
                    choseHot = 2;
                } else if (type.isSelected() && "不限".equals(type.getName())) {
                    choseHot = 1;
                }
            }
        }
        if (!star.equals("")) {
            if (starDate.equals("")) {
                travelScreenBean.setElaryDate(star);
            } else {
                travelScreenBean.setElaryDate(star + "," + starDate.substring(0, starDate.lastIndexOf(",")));
            }
            choseWeight = 2;
        } else {
            if (starDate.equals("")) {
                travelScreenBean.setElaryDate("");
                choseWeight = 1;
            } else {
                travelScreenBean.setElaryDate(starDate.substring(0, starDate.lastIndexOf(",")));
            }

        }
        if (!end.equals("")) {
            if (endDate.equals("")) {
                travelScreenBean.setLateDate(end);
            } else {

                travelScreenBean.setLateDate(end + "," + endDate.substring(0, endDate.lastIndexOf(",")));
            }
            choseWeight = 2;
        } else {
            if (endDate.equals("")) {
                choseWeight = 1;
                travelScreenBean.setLateDate("");

            } else {
                travelScreenBean.setLateDate(endDate.substring(0, endDate.lastIndexOf(",")));
            }
        }
        travelScreenBean.setFilterTypes(list);

        if (choseWeight == 1 && choseHot == 1) {
            travelScreenBean.setSelect(false);
        } else if (choseHot == 2 || choseWeight == 2) {
            travelScreenBean.setSelect(true);
        }

        setOnClick.getDataInfo(travelScreenBean);
        TravelLineListActivity.setTravelScreenBean(travelScreenBean);
        travelScreenBean = new TravelScreenBean();

//        finish();
        dismiss();
    }

    // 重置时间选择
    public void timeReset()
    {
        listAdapter.notifyDataSetChanged();
        startTimeTV.setText("");
        lastTimeTV.setText("");
        choseWeight = 1;
        choseHot = 1;
        elaryDate = null;
        lateDate = null;
//        travelScreenBean.setLateDate("");
//        travelScreenBean.setElaryDate("");
        resetList();

    }

    private void resetList()
    {
        for (int i = 0; i < orgList.size(); i++) {
            List<FilterType> list = orgList.get(i).getChildList();
            for (int j = 0; j < list.size(); j++) {
                FilterType type = list.get(j);
                if (j == 0) {
                    type.setSelected(true);
                } else {
                    type.setSelected(false);
                }
            }
        }
    }

    public void startCalendarActivity()
    {
        CalendarManager.getInstance().setDateLoadFlag(false);
        Intent it = new Intent(activity, CalendarActivity.class);
        it.putExtra(CalendarActivity.TYPE, CalendarActivity.TYPE_NO_PRICE);
        activity.startActivity(it);
    }

    private void init()
    {
        this.setContentView(rootView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setTouchable(true);
        setOutsideTouchable(true);
    }

public interface ConfigInfo {
    void getDataInfo(TravelScreenBean screenBean);

}

    public ConfigInfo setOnClick;

    public void setOnInfoClick(ConfigInfo setOnClick)
    {
        this.setOnClick = setOnClick;
    }

}
