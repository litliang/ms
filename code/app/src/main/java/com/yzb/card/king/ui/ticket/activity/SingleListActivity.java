package com.yzb.card.king.ui.ticket.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.ActivityMessageEvent;
import com.yzb.card.king.bean.ticket.FilterData;
import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.bean.ticket.FlightManager;
import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.sys.AppFactory;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.TicketFilterView;
import com.yzb.card.king.ui.appwidget.popup.CalendarPop;
import com.yzb.card.king.ui.credit.activity.CreditOnlineCardActivity;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.manage.FilterManager;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.activity.AddCardAllActivity;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.ui.other.bean.City;
import com.yzb.card.king.ui.ticket.presenter.SingleListPresenter;
import com.yzb.card.king.ui.ticket.view.SingleListView;
import com.yzb.card.king.ui.transport.adapter.SingleListAdapter;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机票-->单程；
 *
 * @author yinshuguang;
 *         修改：gengqiyun 2016.8.5; 修改功能：打通前后台；
 */
public class SingleListActivity extends BaseTicketActivity implements SingleListView {
    private static final String STATE_ALL = "0";
    private static final String STATE_MY_BANK = "1";
    private static final String STATE_ALL_BANK = "2";

    public static final int RQ_TO_AGENT = 1;
    private View currentView;
    private TextView startDate;
    private TextView tvCenterPrice;
    private TextView tvPrePrice;
    private TextView tvNextPrice;
    private SingleListPresenter presenter;
    private SingleListAdapter adapter;
    private FlightManager flightManager;
    private Flight currentFlight;
    private View tvPreDay;

    private boolean bankSelected;//银行优惠选择状态,true表示选中
    private View llPreLayout;
    private View llNextLayout;
    private TextView tvBankMsg;
    private TextView tvMsg;
    private View llNoData;
    private View llInfo;
    private RelativeLayout rlBankMore;
    private CalendarPop calendarPop;

    private TextView tvBindCard;
    private TextView tvApplyCard;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        colorStatusResId = R.color.color_436a8e;
        super.onCreate(savedInstanceState);
        if (presenter == null)
            presenter = new SingleListPresenter(this);


        GlobalApp.getInstance().setPublicActivity(this);
        recvIntentData();
        initView();
        initLisener();
        initData();
        EventBus.getDefault().register(this);
    }

    private void initLisener()
    {
        rlBankMore.setOnClickListener(new MoreBankClickListener());

        if (calendarPop == null) {
            calendarPop = new CalendarPop();
            Map<String, String> param = new HashMap<>();
            param.put("depCityId", currentFlight.getStartCity().getCityId());
            param.put("arrCityId", currentFlight.getEndCity().getCityId());
            param.put("basecabincode", currentFlight.getShippingSpace().getQabinCode());
            calendarPop.setTicketParams(param);
            calendarPop.setListener(new OnItemClickListener<CalendarDay>() {
                @Override
                public void onItemClick(CalendarDay data)
                {
                    pageStart = 0;
                    getDateFromMana(data.getDay());
                    calendarPop.dismiss();
                }
            });
        }

        tvBindCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (UserManager.getInstance().isLogin()) {
                    //startActivity(new Intent(SingleListActivity.this, AddNoPayCardActivity.class));

                    Intent intent = new Intent(SingleListActivity.this, AddCardAllActivity.class);
                    intent.putExtra(AddCardAllActivity.BUSINESS_ADD_CARD,AddCardAllActivity.PART_BUSINESS_VALUE);
                    startActivityForResult(intent,1000);

                } else {
                    startActivity(new Intent(SingleListActivity.this, LoginActivity.class));
                }
            }
        });

        tvApplyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(SingleListActivity.this, CreditOnlineCardActivity.class));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void mainEventThread(ActivityMessageEvent event)
    {
        if ("singleListActivity".equals(event.getActivityName())) {//

            int pagetypet = AppFactory.channelIdToFragmentIndex(event.getChildTypeBean().id);

            if (pagetypet != -1) {

                finish();

            } else {
                ToastUtil.i(SingleListActivity.this, "敬请期待");
            }
        }
    }

    /**
     * 接口句柄
     */
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            int what = msg.what;

            if (what == 0) {

                ChildTypeBean selectedBeanT = (ChildTypeBean) msg.obj;

                //检查是否开启代理商页面通知关闭代理商页面
                if ("ticketAgentActivity".equals(GlobalApp.activityStr)) {
                    ActivityMessageEvent activityMessageEvent = new ActivityMessageEvent();
                    activityMessageEvent.setActivityName(GlobalApp.activityStr);
                    activityMessageEvent.setChildTypeBean(selectedBeanT);
                    EventBus.getDefault().post(activityMessageEvent);
                }

                ActivityMessageEvent activityMessageEvent = new ActivityMessageEvent();
                activityMessageEvent.setActivityName("singleListActivity");
                activityMessageEvent.setChildTypeBean(selectedBeanT);
                EventBus.getDefault().post(activityMessageEvent);
            }
        }
    };

    /**
     * 接收Intent数据；
     */
    private void recvIntentData()
    {
        flightManager = (FlightManager) getIntent().getSerializableExtra("flightManager");
        if (flightManager != null) {
            currentFlight = flightManager.getCurrentFlight();
            FilterManager.getInstance().setCurrentFlight(currentFlight);
        }
    }

    @Override
    protected boolean isInternational()
    {
        String startType = currentFlight.getStartCity().getType();
        String endType = currentFlight.getEndCity().getType();
        if (startType != null) {
            return !startType.equals(endType);
        }
        return false;
    }

    @Override
    protected void refresh()
    {
        initData();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        getCityFrommMana();
    }

    private void getDateFromMana(Date date)
    {
        if (null != date) {
            if (flightManager.getCurrentLine() == TYPE_ROUND && flightManager.getTickets().size() == 1) {//如果用户修改返程时间则进行判断
                Date startDate = getDepDate(flightManager.getTickets().get(0));
                Date dataCopy = new Date(startDate.getTime());
                dataCopy.setHours(0);
                dataCopy.setMinutes(0);
                dataCopy.setSeconds(0);
                if (date.before(dataCopy)) {
                    String startStr = DateUtil.date2String(startDate, DateUtil.DATE_HOUR_MINIT);
                    UiUtils.shortToast("时间应在 " + startStr + " 之后");
                    return;
                } else if (date.getTime() == dataCopy.getTime()) {
                    return;
                }
            }
            date.setHours(0);
            date.setMinutes(0);
            currentFlight.setStartDate(date);
            initData();
        }
    }

    private void getCityFrommMana()
    {
        if (!StringUtils.isEmpty(CitySelectManager.getInstance().getPlaceId())) {
            String cityName = CitySelectManager.getInstance().getPlaceName();
            City startCity = currentFlight.getStartCity();
            City endCity = currentFlight.getEndCity();
            if (startCity.getCityName().equals(cityName) ||
                    startCity.getCityName().equals(cityName.substring(0, cityName.length() - 1)))
                return;

            ((TextView) currentView).setText(cityName);

            if (currentView.getId() == R.id.tv_title_1) {
                startCity.setCityName(cityName);
                startCity.setCityId(CitySelectManager.getInstance().getPlaceId());
            }

            if (currentView.getId() == R.id.tv_title_2) {
                endCity.setCityName(cityName);
                endCity.setCityId(CitySelectManager.getInstance().getPlaceId());
            }

            initData();
            CitySelectManager.getInstance().clearData();
        }
    }

    private void initView()
    {
        super.initView(this);
        View v = View.inflate(this, R.layout.second_title_single, null);
        setSecondTitle(v);
        //设置当前句柄控制
        setUiHandler(myHandler);

        int icon = R.mipmap.jipiao_icon_toaddr;

        String startCityName = currentFlight.getStartCity().getCityName();
        String endCityName = currentFlight.getEndCity().getCityName();
        if (flightManager.getCurrentLine() == TYPE_ROUND) {
            icon = R.mipmap.jipiao_arrow_wang_fan;
            if (flightManager.getFlights().size() == 1) {
                startCityName = currentFlight.getStartCity().getCityName();
                endCityName = currentFlight.getEndCity().getCityName();
            }
        }
        setTitle(startCityName, icon, endCityName);
        adapter = new SingleListAdapter(this, flightManager.getBabyNum(), flightManager.getChildrenNum());

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(adapterClickCallBack);

        startDate = (TextView) this.findViewById(R.id.tv_start_date);

//        startWeek = (TextView) this.findViewById(R.id.tv_start_week);
//        startWeek.setText(DateUtil.getDateExplain(currentFlight.getStartDate()));

        tvCenterPrice = (TextView) this.findViewById(R.id.tv_center_price);
        tvPrePrice = (TextView) this.findViewById(R.id.tv_pre_price);
        tvNextPrice = (TextView) this.findViewById(R.id.tv_next_price);
        tvPreDay = v.findViewById(R.id.tvPreDay);

        llPreLayout = v.findViewById(R.id.pre_layout);
        llNextLayout = v.findViewById(R.id.next_layout);

//        tvMoreBank = (TextView) findViewById(R.id.tvMoreBank);
//        tvMoreBank.setOnClickListener(new MoreBankClickListener());

        rlBankMore = (RelativeLayout) findViewById(R.id.rlBankMore);
        llInfo = findViewById(R.id.llInfo);
        llNoData = findViewById(R.id.llNoData);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        tvBankMsg = (TextView) findViewById(R.id.tvBankMsg);

        tvBindCard = (TextView) findViewById(R.id.tvBindCard);
        tvApplyCard = (TextView) findViewById(R.id.tvApplyCard);

    }

    private class MoreBankClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            TicketFilterView.filterData.setBankDisCode("2");
        }
    }

    private SingleListAdapter.ICallBack adapterClickCallBack = new SingleListAdapter.ICallBack() {
        @Override
        public void callBack(int position)
        {
            PlaneTicket map = adapter.getDataList().get(position);
            Flight flight = flightManager.getCurrentFlight();
            map.setStartCity(flight.getStartCity().getCityName());
            map.setEndCity(flight.getEndCity().getCityName());

            flightManager.setTicket(map);

            if (flightManager.getCurrentLine() == TYPE_ROUND && flightManager.getTickets().size() == 0) {
                //往返，返程起飞时间大于往程落地时间+3h
                Date depDate = getDepDate(map);
                List<Flight> flights = flightManager.getFlights();
                Flight backFlight = flights.get(1);
                if (backFlight.getStartDate().before(depDate)) {
                    flights.get(1).setStartDate(depDate);
                }

            }
            Intent intent = new Intent(SingleListActivity.this, TicketAgentActivity.class);
            intent.putExtra("flightManager", flightManager);
            startActivityForResult(intent, RQ_TO_AGENT);
        }
    };

    /**
     * 去时机票的落地时间 +3h
     *
     * @param planeTicket
     * @return Date
     */
    private Date getDepDate(PlaneTicket planeTicket)
    {
        String arrDayTime = planeTicket.getArrDay() + " " + planeTicket.getArrTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.string2Date(arrDayTime, DateUtil.DATE_HOUR_MINIT));
        calendar.add(Calendar.HOUR_OF_DAY, 3);
        return calendar.getTime();
    }

    private FilterData.OnDataChangeListener listener = new FilterData.OnDataChangeListener() {
        @Override
        public void onDataChanged()
        {
            pageStart = 0;
            loadListData();
        }

        @Override
        public void onBankChanged(boolean selected)
        {
            pageStart = 0;
            bankSelected = selected;
            loadListData();
        }
    };

    private void initData()
    {
        //往返情况下不能换城市
        if (flightManager.getCurrentLine() == TYPE_ROUND) {
            setChangePlaceEnable(false);
        } else {
            setChangePlaceEnable(true);
        }
        if ("0".equals(TicketFilterView.filterData.getDisBankCode())) {
            bankSelected = false;
        } else {
            bankSelected = true;
        }

        initSecondView();
        addTicketMsg();
        loadHeaderPrice();
        loadListData();
        TicketFilterView.filterData.setListener(listener);
    }

    private void initSecondView()
    {

        Date startDay = currentFlight.getStartDate();
        setCalendarDate(startDay);
        Date toDay = new Date();
        String startStr = DateUtil.date2String(startDay, DateUtil.DATE_FORMAT_DATE);
        String todayStr = DateUtil.date2String(toDay, DateUtil.DATE_FORMAT_DATE);
        if (todayStr.equals(startStr)) {
            setPreLayoutEnable(false);
        } else {
            setPreLayoutEnable(true);
        }
    }

    private void setCalendarDate(Date date)
    {
        startDate.setText(DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE_DAY2)
                + "/" + DateUtil.getDateExplain(date));
    }

    private void setPreLayoutEnable(boolean enable)
    {
        tvPreDay.setEnabled(enable);
        tvPrePrice.setEnabled(enable);
        llPreLayout.setEnabled(enable);
    }

    private void addTicketMsg()
    {
        int size = flightManager.getTickets().size();
        clearMsg();
        for (int i = 0; i < size; i++) {
            String dotText = "" + (i + 1);
            if (flightManager.getCurrentLine() == TYPE_ROUND) {
                dotText = "去";
            }
            PlaneTicket map = flightManager.getTickets().get(i);
            addMsg(this, map, dotText);
        }
    }

    private void loadListData()
    {

        String singleline_filter_copany = SharePrefUtil.getValueFromSp(getBaseContext(),"singleline"+"-filter-company","1");
        commonparam.put("operaCode", singleline_filter_copany);
        commonparam.put("depCityId", currentFlight.getStartCity().getCityId());
        commonparam.put("timeSeres", DateUtil.date2String(currentFlight.getStartDate(), DateUtil.DATE_HOUR_MINIT));
        commonparam.put("arrCityId", currentFlight.getEndCity().getCityId());
        commonparam.put("baseCabinCode", currentFlight.getShippingSpace().getQabinCode());
        commonparam.put("page", pageSize * pageStart);
        commonparam.put("pageSize", pageSize);
        commonparam.put("time", TicketFilterView.filterData.getTime().getCode());
        commonparam.put("price", TicketFilterView.filterData.getPrice().getCode());

        commonparam.put("screen", TicketFilterView.filterData.getFilterList());
        commonparam.put("bankStruts", TicketFilterView.filterData.getDisBankCode());

        this.serviceName = getServiceName();
        refreshData();
    }

    private String getServiceName()
    {
        return UserManager.getInstance().isLogin() ? CardConstant.card_app_queryonwayticket
                : CardConstant.card_app_airfarequerylistnotlogin;
    }

    private void loadHeaderPrice()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("depCityId", currentFlight.getStartCity().getCityId());
        map.put("depDay", DateUtil.date2String(currentFlight.getStartDate(), DateUtil.DATE_FORMAT_DATE));
        map.put("arrCityId", currentFlight.getEndCity().getCityId());
        map.put("basecabincode", currentFlight.getShippingSpace().getQabinCode());
        presenter.loadHeaderPrice(map);
    }

    /**
     * 数据加载结果回调方法；
     *
     * @param data
     */
    @Override
    protected void onSucess(boolean event_tag, String data)
    {
        if(pageStart==-1){
            ToastUtil.i(this,"已经是最后一页了");
            return;
        }
        LogUtil.e("event_tag="+event_tag+"  pageStart="+pageStart);

        List<PlaneTicket> dataList = JSON.parseArray(data, PlaneTicket.class);
        setTvMoreBankVisibility();
        if (event_tag) {
            adapter.clear();
            if (dataList.size() <= 0) {

                setEmptyDataMsg();
            } else {
                llNoData.setVisibility(View.GONE);
                rlBankMore.setVisibility(View.GONE);
                setMsg("");
            }
        }

        adapter.appendData(dataList);
        recyclerView.notifyData();
        if (dataList.size() < pageSize) {

            presenter.saveList(adapter.getDataList());

        } else {

        }
        if(adapter.getDataList().size() % pageSize>0){

            pageStart = -1;
        }else {
            if (dataList.size() == 0) {
                pageStart = -1;
            } else {
                pageStart++;

            }
        }

    }

    private void setTvMoreBankVisibility()
    {
        String code = TicketFilterView.filterData.getDisBankCode();
        if ("1".equals(code)) {
            rlBankMore.setVisibility(View.VISIBLE);
        } else {
            rlBankMore.setVisibility(View.GONE);
        }
    }

    private void setEmptyDataMsg()
    {
        String msg = "";
        String bankMsg = "";
        if (!STATE_ALL.equals(getDisBankCode())) {//银行优惠状态
            if (UserManager.getInstance().isLogin()) {

                if (STATE_MY_BANK.equals(getDisBankCode())) {
                    llNoData.setVisibility(View.VISIBLE);
                    rlBankMore.setVisibility(View.VISIBLE);
                    int cardNum = UserManager.getInstance().getUserBean().getCreditCardNum();
                    if (cardNum > 0) {
                        bankMsg = UiUtils.getString(R.string.ticket_no_bank_dis);
                    } else {
                        bankMsg = UiUtils.getString(R.string.ticket_no_bank_card);
                    }
                } else {
                    rlBankMore.setVisibility(View.GONE);
                    bankMsg = UiUtils.getString(R.string.app_no_data);
                }
            } else {
                llNoData.setVisibility(View.GONE);
                msg = UiUtils.getString(R.string.ticket_login);
            }
        } else {//非银行优惠
            llNoData.setVisibility(View.GONE);
            msg = UiUtils.getString(R.string.app_no_data);
        }
        setMsg(msg);
        setBankMsg(bankMsg);
    }

    private void setBankMsg(String msg)
    {
        tvBankMsg.setText(msg);
    }

    protected void setMsg(String msg)
    {
        setNormalMsg(tvMsg, msg);
    }

    private void setNormalMsg(TextView tvMsg, String msg)
    {
        if (tvMsg != null) {
            if (TextUtils.isEmpty(msg)) {
                tvMsg.setVisibility(View.GONE);
            } else {
                tvMsg.setText(msg);
                tvMsg.setVisibility(View.VISIBLE);
            }
        }
    }

    private String getDisBankCode()
    {
        return TicketFilterView.filterData.getDisBankCode();
    }

    /**
     * 获取广告信息
     *
     * @return
     */
    private PlaneTicket getAd()
    {
        return null;
    }

    @Override
    protected void clearData()
    {
        adapter.clear();
        recyclerView.notifyData();
    }

    /**
     * 获取城市
     *
     * @param v
     */
    public void getPlace(View v)
    {
        currentView = v;
        startActivity(new Intent(this, SelectPlaceActivity.class));
    }

    public void getDate(View v)
    {
        currentView = v;

        calendarPop.setPositionDate(currentFlight.getStartDate());
        calendarPop.showAtLocation(v, Gravity.TOP, 0, 0);
    }

    public void refresh(View v)
    {
        if (v.getId() == R.id.pre_layout) {
            Date preDate = (DateUtil.addDay(currentFlight.getStartDate(), -1));
            if (preDate.getTime() <= getToday()) {
                return;
            }
            currentFlight.setStartDate(preDate);
        }

        if (v.getId() == R.id.next_layout) {
            currentFlight.setStartDate(DateUtil.addDay(currentFlight.getStartDate(), 1));
        }
        startDate.setText(DateUtil.date2String(currentFlight.getStartDate(), DateUtil.DATE_FORMAT_DATE_DAY2));
        pageStart = 0;
        initData();
    }

    private long getToday()
    {
        Date date = new Date();
        String dateStr = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE);
        Date today = DateUtil.string2Date(dateStr, DateUtil.DATE_FORMAT_DATE);
        return DateUtil.addDay(today, -1).getTime();
    }

    @Override
    public void initHeaderPrice(String prePrice, String todayPrice, String tomorrowPrice)
    {
        tvCenterPrice.setText(todayPrice);
        tvPrePrice.setText(prePrice);
        tvNextPrice.setText(tomorrowPrice);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        TicketFilterView.filterData.reset();
        //取消注册事件
        EventBus.getDefault().unregister(this);


        GlobalApp.getInstance().setPublicActivity(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== 1000 && resultCode == Activity.RESULT_OK){
            pageStart = 0;
            refreshData();
        }
    }
}
