package com.yzb.card.king.ui.ticket.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.common.PayOrderBean;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.ticket.ModifyOrderManager;
import com.yzb.card.king.bean.ticket.OrderIdBean;
import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.bean.ticket.UpgradeOrderBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.discount.activity.pay.AppPreferentialPaymentActivity;
import com.yzb.card.king.ui.discount.activity.pay.DiscountPayActivity;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.activity.CalendarActivity;
import com.yzb.card.king.ui.ticket.presenter.ReBookPresenter;
import com.yzb.card.king.ui.ticket.presenter.UpgradeOrderPresenter;
import com.yzb.card.king.ui.ticket.view.ReBookView;
import com.yzb.card.king.ui.ticket.view.UpgradeOrderView;
import com.yzb.card.king.ui.transport.adapter.RescheduleListAdapter;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 机票-->改签列表；
 *
 * @author gengqiyun;
 * @date 2016.12.4
 */
public class RescheduleListActivity extends BaseTicketActivity implements UpgradeOrderView, ReBookView, View.OnClickListener {
    private TextView startDate;
    private TextView startWeek;
    private RescheduleListAdapter adapter;

    private UpgradeOrderPresenter upgradeOrderPresenter; //改签列表；
    private ReBookPresenter reBookPresenter; //改签确认；
    private ModifyOrderManager modifyOrderManager;
    private String departDate; //出发日期；

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        upgradeOrderPresenter = new UpgradeOrderPresenter(this);

        reBookPresenter = new ReBookPresenter(this);
        recvIntentData();
        initView();
        getData();
    }

    /**
     * 接口句柄
     */
    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg)
        {
            finish();
            return false;
        }
    });

    /**
     * 接收Intent数据；
     */
    private void recvIntentData()
    {
        // 订单管理器；
        Serializable modifyOrderManagerObj = getIntent().getSerializableExtra("modifyOrderManager");
        if (modifyOrderManagerObj != null) {
            modifyOrderManager = (ModifyOrderManager) modifyOrderManagerObj;
            if (modifyOrderManager.getCurrentOrderInfo() != null) {
                departDate = DateUtil.string2SpecString(modifyOrderManager.getCurrentOrderInfo().getStartTime(),
                        DateUtil.DATE_FORMAT_DATE);
            }
        }
    }

    @Override
    public void onRefresh()
    {
        getData();
    }

    /**
     * 获取数据；
     */
    public void getData()
    {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                adapter.clearAll();
                swipeRefreshLayout.setRefreshing(true);
                Map<String, Object> args = new HashMap<>();
                args.put("flightList", getFlightList());
                upgradeOrderPresenter.loadData(true, args);
            }
        }, 100);
    }

    /**
     * 获取要当前改签的航班；
     */
    private ArrayList<Map<String, Object>> getFlightList()
    {
        ArrayList<Map<String, Object>> mList = new ArrayList<>();
        TicketOrderDetBean.OrderInfoBean oib = modifyOrderManager.getCurrentOrderInfo();
        if (oib != null) {
            Map<String, Object> args = new HashMap<>();
            args.put("orderNo", oib.getOrderNo()); //航空订单号
            args.put("departDate", departDate); //改签起飞时间（格式：yyyyMMdd） 用户可选择，此处默认和原定起飞时间 相同；
            args.put("oriDepartDate", DateUtil.string2SpecString(oib.getStartTime(),
                    DateUtil.DATE_FORMAT_DATE)); //原定起飞时间  （格式：yyyyMMdd）
            /**
             *非组合套餐，设置为OW，否则正常处理；（依赖接口设计）
             */
            args.put("tripType", modifyOrderManager.isCombineProducts() ? modifyOrderManager.getFlightType() :
                    AppConstant.TYPE_SINGLE); //航班类型（航程类型OW,RT,MT）
            args.put("userList", getUserList(oib.getTicketsList())); //选择改签人员
            args.put("flightNumber", oib.getToolNumber()); //航班号；
            mList.add(args);
        }
        return mList;
    }

    /**
     * 获取证件信息
     *
     * @param ticketsList
     */
    private List<Map<String, String>> getUserList(List<TicketOrderDetBean.TicketsListBean> ticketsList)
    {
        List<Map<String, String>> args = new ArrayList<>();
        if (ticketsList != null) {
            for (int i = 0; i < ticketsList.size(); i++) {
                //添加选中的；
                if (ticketsList.get(i).isSelect()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("paxIdType", ticketsList.get(i).getIdType());
                    map.put("paxIdNo", ticketsList.get(i).getGuestIDCard());
                    args.add(map);
                }
            }
        }
        return args;
    }

    @Override
    protected void refresh()
    {
        getData();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        getDateFromMana();
    }

    private void getDateFromMana()
    {
        if (null != CalendarManager.getInstance().getDate()) {
            Date date = CalendarManager.getInstance().getDate();
            startDate.setText(DateUtil.date2String(date, getResources().getString(R.string.ticket_monDay)));
            startWeek.setText(DateUtil.getDateExplain(CalendarManager.getInstance().getDate()));

            //更新出发日期；
            departDate = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE3);

            getData();
            CalendarManager.getInstance().clearData();
        }
    }

    private void initView()
    {
        super.initView(this);
        rl_title_right.setVisibility(View.GONE);

        View v = View.inflate(this, R.layout.second_title_reschedule, null);
        setSecondTitle(v);
        //设置当前句柄控制
        setUiHandler(myHandler);

        findViewById(R.id.panelDate).setOnClickListener(this);

        int icon = R.mipmap.jipiao_icon_toaddr;

        adapter = new RescheduleListAdapter(this);
        //禁止加载更多；
        recyclerView.setLoadMoreEnable(false);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(adapterClickCallBack);

        startDate = (TextView) findViewById(R.id.tv_start_date);
        startWeek = (TextView) findViewById(R.id.tv_start_week);

        TicketOrderDetBean.OrderInfoBean orderInfoBean = modifyOrderManager.getCurrentOrderInfo();
        if (orderInfoBean != null && orderInfoBean.getFlightList() != null && orderInfoBean.getFlightList().size() > 0) {
            TicketOrderDetBean.MyFlight myFlight = orderInfoBean.getFlightList().get(0);
            String startCityName = myFlight.getDepAirPort();
            String endCityName = myFlight.getArrAirPort();
            setTitle(startCityName, icon, endCityName);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(myFlight.getTimeSeres());
            startDate.setText(DateUtil.date2String(calendar.getTime(), DateUtil.DATE_FORMAT_DATE_DAY2));

            startWeek.setText(DateUtil.getDateExplain(calendar.getTime()));
        }
        setBottomVisibility(View.GONE);
        // 显示原票部分；
        showOrignalTicket();
    }

    @Override
    public void onGetUpgradeOrderSucess(UpgradeOrderBean upgradeOrderBean)
    {
        swipeRefreshLayout.setRefreshing(false);
        try {
            if (upgradeOrderBean != null) {
                //全局处理；此处要克隆一个；
                UpgradeOrderBean uob = upgradeOrderBean.clone();
                //添加查询结果；
                modifyOrderManager.addResult(uob.getAvResultList() == null || uob.getAvResultList().size() == 0 ?
                        null : uob.getAvResultList().get(0));
//                modifyOrderManager.addResult(uob.getAvResultList());

                // 要改签的航班列表；
                List<UpgradeOrderBean.AvResult> results = upgradeOrderBean.getAvResultList();
                if (results != null && results.size() > 0) {
                    //此处显示第一个需要改签的查询出的可供用户选择的航班列表；
                    List<UpgradeOrderBean.FlightBean> flightBeans = groupData(results.get(0));
                    if (flightBeans.size() <= 0) {
                        setEmptyDataMsg();
                    } else {
                        setMsg("");
                    }
                    adapter.appendALL(flightBeans);
                    recyclerView.notifyData();
                }
            } else {
                setEmptyDataMsg();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(this, CalendarActivity.class);
        // intent.putExtra("type", CalendarActivity.TYPE_HAS_PRICE);
        startActivity(intent);
    }

    /**
     * 显示原票部分；
     */
    private void showOrignalTicket()
    {
        //已经预定的；
        int size = modifyOrderManager.getFlightBeans().size();
        clearMsg();

        String dotText;
        TicketOrderDetBean.OrderInfoBean orderInfoBean = modifyOrderManager.getCurrentOrderInfo();
        // 航班类型；
//        if (AppConstant.TYPE_ROUND.equals(modifyOrderManager.getCurrentLine()))
//        {
//            //航线类型（1：去，2：返 ）
//            dotText = "1".equals(orderInfoBean.getRouteType()) ? "去" : "返";
//        } else if (AppConstant.TYPE_MULT.equals(modifyOrderManager.getCurrentLine()))
//        {
        dotText = "" + (size + 1);
//        } else
//        {
//            dotText = "";
//        }
        addMsg(this, orderInfoBean, dotText);
    }

    /**
     * 根据仓等组合需要展示的航班列表数据；
     */
    private List<UpgradeOrderBean.FlightBean> groupData(UpgradeOrderBean.AvResult avResult)
    {
        //处理过的航班列表；
        List<UpgradeOrderBean.FlightBean> handleFlights = new ArrayList<>();

        if (avResult != null) {
            //航线列表
            List<UpgradeOrderBean.TripFlight> tripFlightList = avResult.getTripFlightList();
            if (tripFlightList != null) {
                //航线列表；
                for (int j = 0; j < tripFlightList.size(); j++) {
                    //航班列表；
                    List<UpgradeOrderBean.FlightBean> flightList = tripFlightList.get(j).getFlightList();
                    if (flightList != null) {
                        for (int k = 0; k < flightList.size(); k++) {
                            //单个航班（可能是中转）；
                            UpgradeOrderBean.FlightBean flightBean = flightList.get(k);
                            List<UpgradeOrderBean.CabinBean> cabinList = flightList.get(k).getCabinList();
                            //仓等列表；
                            if (cabinList != null) {
                                try {
                                    UpgradeOrderBean.CabinBean cabin;
                                    UpgradeOrderBean.FlightBean flightBeanEmp; //克隆出的对象；
                                    for (int l = 0; l < cabinList.size(); l++) {
                                        flightBeanEmp = flightBean.clone();
                                        cabin = cabinList.get(l);
                                        flightBeanEmp.setCabinCode(cabin.getCabinCode());
                                        flightBeanEmp.setPrice(cabin.getPrice());
                                        flightBeanEmp.setChdPrice(cabin.getChdPrice());
                                        flightBeanEmp.setInfPrice(cabin.getInfPrice());
                                        flightBeanEmp.setFueltax(cabin.getFueltax());
                                        flightBeanEmp.setFueltaxChd(cabin.getFueltaxChd());
                                        flightBeanEmp.setFueltaxBad(cabin.getFueltaxBad());
                                        handleFlights.add(flightBeanEmp);
                                    }
                                } catch (CloneNotSupportedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return handleFlights;
    }

    @Override
    public void onGetUpgradeOrderFail(String failMsg)
    {
        swipeRefreshLayout.setRefreshing(false);
        toastCustom(failMsg);
    }

    private RescheduleListAdapter.ICallBack adapterClickCallBack = new RescheduleListAdapter.ICallBack() {
        @Override
        public void callBack(int position)
        {
            UpgradeOrderBean.FlightBean flightBean = adapter.getData().get(position);
            modifyOrderManager.addFlightBean(flightBean);

            //还有未改签的航班；包括中转  ?????????????????
            //中转航班数量
            LogUtil.i("modifyOrderManager.getOrinalOrders()=" + modifyOrderManager.getOrinalOrders().size());
            LogUtil.i("modifyOrderManager.getFlightBeans()==" + modifyOrderManager.getFlightBeans().size());

            if (modifyOrderManager.getOrinalOrders().size() > modifyOrderManager.getFlightBeans().size()) {
                Intent intent = new Intent(RescheduleListActivity.this, RescheduleListActivity.class);
                intent.putExtra("modifyOrderManager", modifyOrderManager);
                intent.putExtra("flightList", getFlightList());
                startActivity(intent);
            } else {
                //执行确认改签；
                Set<UpgradeOrderBean.FlightBean> flightBeans = modifyOrderManager.getFlightBeans();
                List<UpgradeOrderBean.FlightBean> flightBeanList = new ArrayList<>(flightBeans);
                exeReBook(flightBeanList);
            }
        }
    };

    /**
     * 开始确认改签；
     *
     * @param flightBeans
     */
    public void exeReBook(List<UpgradeOrderBean.FlightBean> flightBeans)
    {
        showPDialog(R.string.loading);
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("avResultList", getResultList(flightBeans));
        reBookPresenter.loadData(true, argMap);
    }

    /**
     * 获取确认改签的入参；
     *
     * @param flightBeans 选择的要改签的航班列表；
     * @return
     */
    private List<UpgradeOrderBean.AvResult> getResultList(List<UpgradeOrderBean.FlightBean> flightBeans)
    {
        if (modifyOrderManager != null) {
            List<UpgradeOrderBean.AvResult> avResults = new ArrayList<>(modifyOrderManager.getResults());

            for (int i = 0; i < avResults.size(); i++) {
                List<UpgradeOrderBean.OrignalFlight> orignalFlights = avResults.get(i).getChangeAirOriDestList();
                if (orignalFlights != null) {
                    for (int j = 0; j < orignalFlights.size(); j++) {
                        List<UpgradeOrderBean.FlightBean> temp = new ArrayList<>();
                        temp.add(flightBeans.get(i));
                        orignalFlights.get(j).setChangeFlightCabinDtoList(temp);
                    }
                }
            }
            return avResults;
        }
        return null;
    }

    @Override
    public void onReBookSucess(final OrderIdBean orderIdBean)
    {
        closePDialog();
        if (orderIdBean != null && orderIdBean.getOrderIdList() != null) {
            float totalAmount = 0;
            final ArrayList<OrderIdBean.OrderIds> orderIdBeans = new ArrayList<>();
            //平台订单号列表；
            List<OrderIdBean.OrderIds> orderIdses = orderIdBean.getOrderIdList();
            for (int i = 0; i < orderIdses.size(); i++) {
                //需要付款；
                if (orderIdses.get(i).isNeedToPay()) {
                    orderIdBeans.add(orderIdses.get(i));
                    totalAmount += orderIdses.get(i).getTotalPrice();
                }
            }


            //需要支付手续费；
            if (totalAmount > 0 && modifyOrderManager != null) {

                final float finalTotalAmount = totalAmount;

                new AlertDialog.Builder(this).
                        setTitle(R.string.dialog_title).
                        setMessage("提示").
                        setMessage("改签手续费:" + Utils.subZeroAndDot(totalAmount + "") + "元").
                        setCancelable(false).
                        setNegativeButton(R.string.cancel, null).
                        setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                OrderBean orderBean = modifyOrderManager.getOrderBean();

//                                Intent intent = new Intent(RescheduleListActivity.this, DiscountPayActivity.class);
//                                HashMap<String, String> map = new HashMap();
//                                map.put(DiscountPayActivity.STORE_NAME, orderBean.getCarrierNames());//商家名称；会有多个；
//                                map.put(DiscountPayActivity.STORE_IDS, orderBean.getShopIds());//商家id(多个使用英文逗号分割)
//
//                                map.put(DiscountPayActivity.ORDER_ID, orderIdBean.getOrderIds());//订单平台总订单id；
//                                map.put(DiscountPayActivity.ORDER_NO, orderIdBean.getOrderNo());//订单平台总订单号；
//
//                                map.put(DiscountPayActivity.GOODS_CODES, AppConstant.discount_code_ticket);
//                                map.put(DiscountPayActivity.NOTIFY_URL, orderBean.getNotifyUrl());
//
//                                map.put(DiscountPayActivity.ORDER_TIME, DateUtil.formatOrderTime(orderIdBean.getOrderTime()));
//
//                                map.put(DiscountPayActivity.INDUSTRY_ID, AppConstant.ticket_id);//行业id；
//                                map.put(DiscountPayActivity.GOODS_IDS, orderBean.getGoodIds());//商品id(多个使用英文逗号分割)
//                                map.put(DiscountPayActivity.ORDER_AMOUNT, finalTotalAmount + "");//订单金额；
//                                map.put(DiscountPayActivity.IS_INPUT, AppConstant.ACCOUNT_NO_INPUT);//0：不需要手输入；1：需要；
//                                intent.putExtra(DiscountPayActivity.ARG_MAP, map);
//                                intent.putExtra("orderIdBeans", orderIdBeans);
//                                startActivityForResult(intent, DiscountPayActivity.REQ_TICKET_MODIFY);

                                UserManager.getInstance().setOrderIdBeans(orderIdBeans);

                                //新的特惠付款
                                //查询用户的代金券信息
                                //发放平台
                                int issuePlatform = 0;
                                //行业id
                                int industryId = Integer.parseInt(AppConstant.ticket_id);

                                GoldTicketParam goldTicketParam = new GoldTicketParam();

                                goldTicketParam.setIssuePlatform(issuePlatform);

                                goldTicketParam.setIndustryId(industryId);


                                if (!TextUtils.isEmpty(orderBean.getShopIds())) {

                                    int index = orderBean.getShopIds().indexOf(",");

                                    if (index == -1) {

                                        long a = Long.parseLong(orderBean.getShopIds());

                                        goldTicketParam.setShopId(a);
                                    } else {

                                        String[] strArray = orderBean.getShopIds().split(",");

                                        long a = Long.parseLong(strArray[0]);

                                        goldTicketParam.setShopId(a);
                                    }

                                }
//                goldTicketParam.setStoreId(roomPolicy.getHotelId());
//
                                if (!TextUtils.isEmpty(orderBean.getGoodIds())) {

                                    int index = orderBean.getGoodIds().indexOf(",");

                                    if (index == -1) {

                                        long a = Long.parseLong(orderBean.getGoodIds());

                                        goldTicketParam.setGoodsId(a);
                                    } else {

                                        String[] strArray = orderBean.getGoodIds().split(",");

                                        long a = Long.parseLong(strArray[0]);

                                        goldTicketParam.setGoodsId(a);
                                    }

                                }

                                PayOrderBean bean = new PayOrderBean();

                                bean.setOrderAmount(finalTotalAmount);

                                bean.setNotifyUrl(orderBean.getNotifyUrl());

                                bean.setOrderId(Long.parseLong(orderIdBean.getOrderIds()));

                                bean.setOrderNo(orderIdBean.getOrderNo());

                                bean.setOrderTime(orderIdBean.getOrderTime());

                                Intent intent = new Intent(RescheduleListActivity.this, AppPreferentialPaymentActivity.class);

                                intent.putExtra("orderData", bean);

                                intent.putExtra("goldTicketParam", goldTicketParam);

                                intent.putExtra("goodName", orderBean.getCarrierNames());

                                startActivityForResult(intent, DiscountPayActivity.REQ_TICKET_MODIFY);


                            }
                        }).show();
            } else {
                new AlertDialog.Builder(this).
                        setTitle(R.string.dialog_title).
                        setMessage("提示").
                        setMessage("改签成功").
                        setCancelable(false).
                        setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                ChildTypeBean bean = new ChildTypeBean();
                                bean.typeName = "机票";
                                bean.id = "6";
                                bean.parentId = "1003";
                                bean.status = "1";
                                Intent intent = new Intent(RescheduleListActivity.this, ChannelMainActivity.class);
                                intent.putExtra("pagetype", 3); //机票首页；
                                intent.putExtra("data", bean); //机票首页；
                                startActivity(intent);

                                finish();
                            }
                        }).show();
            }
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode) {
            case DiscountPayActivity.REQ_TICKET_MODIFY:
//                modifyOrderManager.removeLastFlight();
                break;
        }
    }

    @Override
    public void onReBookFail(String failMsg)
    {
//        modifyOrderManager.removeLastFlight();
        toastCustom(failMsg);
        closePDialog();
    }

    private String setEmptyDataMsg()
    {
        String msg;
        if (isLogin()) {
            int cardNum = getUserBean().getCreditCardNum();
            if (cardNum > 0) {
                msg = UiUtils.getString(R.string.ticket_no_bank_dis);
            } else {
                msg = UiUtils.getString(R.string.ticket_no_bank_card);
            }
        } else {
            msg = UiUtils.getString(R.string.ticket_login);
        }
        setMsg(msg);
        return msg;
    }

    @Override
    protected void clearData()
    {
        adapter.clearAll();
        recyclerView.notifyData();
    }


}
