package com.yzb.card.king.ui.ticket.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.bean.ticket.FlightManager;
import com.yzb.card.king.bean.ticket.ShippingSpace;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.picks.SelectNumberPick;
import com.yzb.card.king.ui.appwidget.popup.AirLineCompanyPP;
import com.yzb.card.king.ui.appwidget.popup.CalendarPop;
import com.yzb.card.king.ui.appwidget.popup.PlaneCwPopup;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.discount.fragment.BaseFragment;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.ui.other.bean.City;
import com.yzb.card.king.ui.ticket.activity.BaseTicketActivity;
import com.yzb.card.king.ui.ticket.activity.SingleListActivity;
import com.yzb.card.king.ui.ticket.model.ITicketHome;
import com.yzb.card.king.ui.ticket.presenter.TickeHomePresenter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页机票碎片
 * Created by dev on 2016/5/17.
 */
public class TicketHomeFragment extends BaseFragment implements View.OnClickListener
        , BaseViewLayerInterface {

    private LinearLayout findLow;

    private LinearLayout order, zj;

    private List<ShippingSpace> shippingSpaces = new ArrayList<>();

    private TextView adult, chilDren, baBy;

    public final int SINGLE_LINE = BaseTicketActivity.TYPE_SINGLE;

    public final int ROUND_LINE = BaseTicketActivity.TYPE_ROUND;

    public final int MULTI_LINE = BaseTicketActivity.TYPE_MULTI;

    private int currentLine = SINGLE_LINE;

    private FrameLayout fl_ticket_home;

    private LinearLayout ll_mul_ticket_home = null;

    private LinearLayout ll_tab;

    private TextView yexzPlan;

    private SelectNumberPick selectNumberPick;

    private ShippingSpace cangwei;

    private PlaneCwPopup popup;

    private PlaneCwPopup.OnItemClickListener onItemClickListener;

    private LinearLayout peopleChose, hbdt;

    private TextView tv_cangwei;

    private TickeHomePresenter presenter;

    private FlightManager flightManager = new FlightManager();

    private LinearLayout singleLine;

    private ITicketFragment currentFragment;

    private RoundLineFragment rf;

    private SingleLineFragment sf;

    private MultiLineFragment f;

    private CalendarPop calendarPop;

    private TextView tvAirLine;

    private AirLineCompanyPP invoiceContentPp = null;

    private  int selectedCInvoicIndex = 0;

    public static TicketHomeFragment newInstance() {
        TicketHomeFragment fragment = new TicketHomeFragment();

        return fragment;
    }

    public TextView getCangweiTv(){

        return  tv_cangwei;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ticket_home, container, false);

        init(view);

        getData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (CalendarManager.getInstance().isDateLoadFlag()) {
            Date date = CalendarManager.getInstance().getDate();
            if (date != null) {
                currentFragment.setDate(date);
                CalendarManager.getInstance().clearData();
            }
        }

        if (CitySelectManager.getInstance().isLoadPlaceFlag()) {
            City city = (City) CitySelectManager.getInstance().getPlace();
            if (city != null) {
                currentFragment.setPlace(city);
                CitySelectManager.getInstance().clearData();
            }
        }

    }

    /**
     * 初始化视图信息
     *
     * @param view
     */
    private void init(View view) {
        if (view == null) return;
        // activity.et_search.setHint("输入想去旅游的地方");
        presenter = new TickeHomePresenter(this);
        getCW();//获取舱位信息

        findViewFromHeader(view);

        peopleChose = (LinearLayout) view.findViewById(R.id.people_chose);
        peopleChose.setOnClickListener(this);

        zj = (LinearLayout) view.findViewById(R.id.zj);
        zj.setOnClickListener(this);

        adult = (TextView) view.findViewById(R.id.cr_chose);
        chilDren = (TextView) view.findViewById(R.id.et_chose);
        baBy = (TextView) view.findViewById(R.id.ye_chose);

        hbdt = (LinearLayout) view.findViewById(R.id.hbdt);
        hbdt.setOnClickListener(this);

        yexzPlan = (TextView) view.findViewById(R.id.yexz_plan);
        yexzPlan.setOnClickListener(this);

        view.findViewById(R.id.llAirLine).setOnClickListener(this);
        tvAirLine = (TextView) view.findViewById(R.id.tvAirLine);

        onItemClickListener = new PlaneCwPopup.OnItemClickListener() {
            @Override
            public void onItemClick(ShippingSpace shippingSpace) {
                cangwei = shippingSpace;
                currentFragment.setShippSpace(shippingSpace);
                tv_cangwei.setText(shippingSpace.getQabinName());
            }
        };
    }

    /**
     * 获取初始数据
     */
    private void getData() {
        if (calendarPop == null) {
            calendarPop = new CalendarPop();
            calendarPop.setListener(new OnItemClickListener<CalendarDay>() {
                @Override
                public void onItemClick(CalendarDay data) {
                    currentFragment.setDate(data.getDay());
                    calendarPop.dismiss();
                }
            });
        }

        if (popup == null) {
            popup = new PlaneCwPopup(TicketHomeFragment.this, onItemClickListener, shippingSpaces);
        }
        selectTabFragment(0, singleLine);

    }

    /**
     * 初始化headerview里面的控件
     */
    private void findViewFromHeader(View view) {
        findLow = (LinearLayout) view.findViewById(R.id.find_low);

        findLow.setOnClickListener(this);

        order = (LinearLayout) view.findViewById(R.id.order);

        order.setOnClickListener(this);

        fl_ticket_home = (FrameLayout) view.findViewById(R.id.fl_ticket_home);

        ll_tab = (LinearLayout) view.findViewById(R.id.ll_tab);

        ll_mul_ticket_home = (LinearLayout) view.findViewById(R.id.ll_mul_ticket_home);

        singleLine = (LinearLayout) view.findViewById(R.id.singleLine);

        singleLine.setOnClickListener(this);

        LinearLayout roundLine = (LinearLayout) view.findViewById(R.id.roundLine);

        roundLine.setOnClickListener(this);

        LinearLayout multiLine = (LinearLayout) view.findViewById(R.id.multiLine);

        multiLine.setOnClickListener(this);

        tv_cangwei = (TextView) view.findViewById(R.id.tv_cangwei);

        tv_cangwei.setOnClickListener(this);

        view.findViewById(R.id.searchTicket).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order://企业商旅
//                //检测
//                if (!UserManager.getInstance().isLogin()) {
//                    new GoLoginDailog(getContext()).show();
//                    return;
//                }
//                Intent intent1 = new Intent(this.getActivity(), OrderManageActivity.class);
//                intent1.putExtra("orderType", OrderBean.ORDER_TYPE_AIRCRAFT);
//                startActivity(intent1);
                ToastUtil.i(getContext(), "敬请期待");
                break;
            case R.id.find_low: // 多地比价

                // startActivity(new Intent(TicketHomeFragment.this.getActivity(), CheckInActivity.class));
                ToastUtil.i(getContext(), "敬请期待");
                break;
            case R.id.singleLine://单程
                selectTabFragment(0, v);
                break;
            case R.id.roundLine: //往返
                selectTabFragment(1, v);
                break;
            case R.id.multiLine: //多程
                selectTabFragment(2, v);
                break;
            case R.id.tv_cangwei: //舱位选择
                popup.openPop(v);
                break;
            case R.id.searchTicket:
                exeSearchTicket();
                break;
            case R.id.people_chose:   //任务选择器
                //   showPeopleSelectDialog();
                showPeopleSelectDialog();
                break;
            case R.id.hbdt:  //低价机票
//                Intent iPlane = new Intent(TicketHomeFragment.this.getActivity(), PlaneSeachActivity.class);
//                startActivity(iPlane);
                ToastUtil.i(getContext(), "敬请期待");
                break;
            case R.id.zj:   //机+酒
//                setFlightManager();
//                Intent intent = new Intent();
//                intent.putExtra("flightManager", flightManager);
//                intent.setClass(this.getActivity(), LowPriceActivity.class);
//                startActivity(intent);

                ToastUtil.i(getContext(), "敬请期待");
                break;
            case R.id.yexz_plan:   //儿童应该预定须知
//                Bundle bundle = new Bundle();
//                bundle.putString("category", AppConstant.h5_category_plane_adlut_che_baby);
//                bundle.putString("titleName", getString(R.string.ticket_erye_ydxz));
//                Intent i = new Intent(TicketHomeFragment.this.getActivity(), WebViewClientActivity.class);
//                i.putExtra(AppConstants.INTENT_BUNDLE, bundle);
//                startActivity(i);
                showPeopleSelectDialog();
                break;
            case R.id.llAirLine://航空公司

                if (invoiceContentPp == null) {

                    String[] nameArray = getResources().getStringArray(R.array.ticket_air_line_company_name_array);

                    invoiceContentPp = new AirLineCompanyPP(getActivity(), -1);

                    invoiceContentPp.setLogicData(nameArray, null);

                    invoiceContentPp.setSelectIndex(selectedCInvoicIndex);

                    invoiceContentPp.setCallBack(invoiceCCallBack);
                }

                invoiceContentPp.showPP(getActivity().getWindow().getDecorView());

                break;
        }
    }

    private AirLineCompanyPP.BottomDataListCallBack invoiceCCallBack = new AirLineCompanyPP.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex) {

            tvAirLine.setText(name);

            String currline = "";
            switch(currentLine ){
                case MULTI_LINE:
                    currline = "multiline";
                    break;
                case SINGLE_LINE:
                    currline = "singleline";
                    break;
                case ROUND_LINE:
                    currline = "roundline";
                    break;
            }
            SharePrefUtil.saveToSp(getContext(),currline+"-filter-company","");

        }
    };

    /**
     * 获得多程默认舱位信息
     *
     * @return
     */
    public ShippingSpace getCangwei() {

        return cangwei;
    }

    /**
     * 获取机票的舱位信息
     */
    private void getCW() {
        Map<String, Object> param = new HashMap<>();
        param.put("type", AppConstant.TICKET_CANGWEI);
        presenter.getCangWei(param);
    }

    /**
     * 成人儿童婴儿选择dialog；
     */
    private void showPeopleSelectDialog() {
        int crAdult = Integer.parseInt(adult.getText().toString());
        int etChilDren = Integer.parseInt(chilDren.getText().toString());
        int yebaBy = Integer.parseInt(baBy.getText().toString());

        if (selectNumberPick == null) {

            selectNumberPick = new SelectNumberPick(getContext());
            selectNumberPick.setCancelable(true);

            selectNumberPick.setOnDataCallBackList(new SelectNumberPick.SelectedData() {
                @Override
                public void getSelectedData(List<Integer> data) {
                    adult.setText((data.get(0)) + "");
                    chilDren.setText(data.get(1) + "");
                    baBy.setText(data.get(2) + "");
                    flightManager.setAdultNum((data.get(0)));
                    flightManager.setChildrenNum(data.get(1));
                    flightManager.setBabyNum(data.get(2));
                }
            });
        }

        selectNumberPick.setCurrentIndex(crAdult, etChilDren, yebaBy);

        selectNumberPick.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (sf != null) {
            //记录单程航班信息
            List<Flight> flight = sf.getFlights();

            if (flight != null) {
                String singleFlight = JSON.toJSONString(flight);

                SharePrefUtil.saveToSp(getContext(), SharePrefUtil.TICKET_SINGLE_FLIGHT_INFO, singleFlight);

            }

            transaction.remove(sf);
        }
        if (rf != null) {

            //记录单程航班信息
            List<Flight> flight = rf.getFlights();

            if (flight != null) {

                String singleFlight = JSON.toJSONString(flight);

                SharePrefUtil.saveToSp(getContext(), SharePrefUtil.TICKET_ROUND_LINE_INFO, singleFlight);

            }
            transaction.remove(rf);
        }
        if (f != null) {

            //记录单程航班信息
            List<Flight> flight = f.getFlights();

            if (flight != null) {

                String singleFlight = JSON.toJSONString(flight);

                SharePrefUtil.saveToSp(getContext(), SharePrefUtil.TICKET_MULTI_LINE_INFO, singleFlight);

            }
            transaction.remove(f);
        }
    }

    /**
     * fragment的切换
     *
     * @param index
     * @param view
     */
    private void selectTabFragment(int index, View view) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();


        hideFragments(transaction);

        switch (index) {
            case 0: //单程
                currentLine = SINGLE_LINE;

                fl_ticket_home.setVisibility(View.VISIBLE);

                ll_mul_ticket_home.setVisibility(View.GONE);

                if (sf == null) {
                    sf = new SingleLineFragment();
                    transaction.add(R.id.fl_ticket_home, sf);

                } else {
                    transaction.show(sf);
                }

                currentFragment = sf;

                refresh(view);

                 int vZero =  tv_cangwei.getVisibility();

                 if(vZero == View.INVISIBLE){

                     tv_cangwei.setVisibility(View.VISIBLE);
                 }

                break;
            case 1:  //往返
                currentLine = ROUND_LINE;

                fl_ticket_home.setVisibility(View.VISIBLE);

                ll_mul_ticket_home.setVisibility(View.GONE);

                if (rf == null) {

                    rf = new RoundLineFragment();

                    transaction.add(R.id.fl_ticket_home, rf);

                } else {
                    transaction.show(rf);
                }

                currentFragment = rf;

                int vOne =  tv_cangwei.getVisibility();

                if(vOne == View.INVISIBLE){

                    tv_cangwei.setVisibility(View.VISIBLE);
                }
                refresh(view);

                break;
            case 2: //多程
                currentLine = MULTI_LINE;

                fl_ticket_home.setVisibility(View.GONE);

                ll_mul_ticket_home.setVisibility(View.VISIBLE);

                if (f == null) {

                    f = new MultiLineFragment();

                    transaction.add(R.id.fl_mul_ticket_home, f);

                    f.setListData(shippingSpaces);

                } else {

                    transaction.show(f);

                }

                currentFragment = f;
                int vTwo =  tv_cangwei.getVisibility();

                if(vTwo == View.VISIBLE){

                    tv_cangwei.setVisibility(View.INVISIBLE);
                }
                refresh(view);

                break;
        }
        // transaction.commitAllowingStateLoss();

        transaction.commit();
    }

    /**
     * 隐藏所有的fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (sf != null) {
            transaction.hide(sf);
        }
        if (rf != null) {
            transaction.hide(rf);
        }
        if (f != null) {
            transaction.hide(f);
        }
    }

    private void refresh(View v) {
        int count = ll_tab.getChildCount();
        for (int i = 0; i < count; i++) {
            View vTemp = ll_tab.getChildAt(i);
            if (vTemp instanceof LinearLayout) {
                LinearLayout child = (LinearLayout) vTemp;
                child.getChildAt(0).setEnabled(child == v);
                if (child == v) {

                    child.getChildAt(1).setBackgroundColor(Color.parseColor("#436a8e"));
                } else {
                    child.getChildAt(1).setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }

        }
    }

    /**
     * 搜索；
     */
    public void exeSearchTicket() {
        setFlightManager();

        //多程航班检查是否有国际航班
        if (currentLine == MULTI_LINE) {
            if (!hasNationalFlight()) {
                ToastUtil.i(getContext(), R.string.toast_at_less_one_city);
                return;
            }
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), SingleListActivity.class);
        intent.putExtra("flightManager", flightManager);
        startActivity(intent);
    }

    private boolean hasNationalFlight() {
        boolean flag = false;
        List<Flight> list = flightManager.getFlights();
        for (Flight f : list) {
            City endCity = f.getEndCity();
            City startCity = f.getStartCity();
            //是否有国际城市
            if (endCity != null && startCity != null) {
                if ("2".equals(endCity.getType()) || "2".equals(startCity.getType())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 设置传参数据
     */
    private void setFlightManager() {
        String cr = adult.getText().toString();

        String et = chilDren.getText().toString();

        String ye = baBy.getText().toString();

        flightManager.setAdultNum(Integer.parseInt(cr));

        flightManager.setChildrenNum(Integer.parseInt(et));

        flightManager.setBabyNum(Integer.parseInt(ye));

        flightManager.setCurrentLine(currentLine);

        flightManager.clearTicket();

        flightManager.setFlights(currentFragment.getFlights());
    }


    /**
     * 获取城市
     */
    public void getPlace() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), SelectPlaceActivity.class);
        startActivity(intent);
    }

    public void getDate(Date date, String text) {
        calendarPop.setPositionDate(date);
        calendarPop.showAtLocation(fl_ticket_home, Gravity.TOP, 0, 0);
    }


    @Override
    public void callSuccessViewLogic(Object o, int type) {
        if (type == ITicketHome.CANGWEI_TYPE) {
            if (o instanceof List) {
                shippingSpaces.clear();
                List<ShippingSpace> list = (List<ShippingSpace>) o;
                shippingSpaces.addAll(list);
                cangwei = shippingSpaces.get(0);
                currentFragment.setShippSpace(cangwei);
                tv_cangwei.setText(cangwei.getQabinName());
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        if (type == ITicketHome.CANGWEI_TYPE) {
            if (o != null && o instanceof Map) {

                Map<String, String> onSuccessData = (Map<String, String>) o;

                ToastUtil.i(GlobalApp.getInstance().getContext(),
                        onSuccessData.get(HttpConstant.SERVER_ERROR));
            }
        }
    }
}
