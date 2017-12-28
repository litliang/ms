package com.yzb.card.king.ui.hotel.activtiy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.SearchReusultBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.hotel.HotelLevelBean;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.hotel.HotelThemeBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.AppFactory;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.appwidget.DefindTabView;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.appwidget.headfootrecyclerview.RecyclerViewUtils;
import com.yzb.card.king.ui.appwidget.popup.AppCalendarPopup;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.appwidget.popup.ChannelPopWindow;
import com.yzb.card.king.ui.appwidget.popup.ChannelTypePopup;
import com.yzb.card.king.ui.appwidget.popup.HotelStarPricePopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.adapter.HotelTodayRecommendedAdapter;
import com.yzb.card.king.ui.hotel.model.IhotelHome;
import com.yzb.card.king.ui.hotel.persenter.HotelHomePersenter;
import com.yzb.card.king.ui.hotel.view.HotelHomeView;
import com.yzb.card.king.ui.other.activity.CivilInternationCityActivity;
import com.yzb.card.king.ui.ticket.activity.AirTicketHomeActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类  名：酒店首页
 * 作  者：Li Yubing
 * 日  期：2017/7/10
 * 描  述：
 */
@ContentView(R.layout.activity_hotel_home)
public class HotelHomeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, HotelHomeView {

    @ViewInject(R.id.swipeRefresh)
    private SwipeRefreshLayout swipeRefresh;

    @ViewInject(R.id.lvHomeHotelpage)
    private HeadFootRecyclerView lvHomeHotelpage;

    //底部控件
    @ViewInject(R.id.llBottomTab)
    private LinearLayout llBottomTab;

    private HotelTodayRecommendedAdapter adapter;

    private HotelStarPricePopup hotelStarPricePopup;

    @ViewInject(R.id.rlHotelHomeTitle)
    private RelativeLayout rlHotelHomeTitle;

    private AppCalendarPopup appCalendarPopup;

    //头部
    private TextView tvDuration, tvStartTime, tvEndTime, tvStartWeek, tvEndWeek, tv_destination, tvLevel, tv_key;

    private ImageView ivClearStarPrice;

    private ImageView ivClear;

    // 主页酒店观察者
    private HotelHomePersenter persenter;

    private AddCount addCount;

    private boolean specialFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        colorStatusResId = android.R.color.transparent;

        defaultFlag = true;
        super.onCreate(savedInstanceState);

        GlobalVariable.industryId = Integer.parseInt(AppConstant.hotel_id);

        persenter = new HotelHomePersenter(this);

        initView();

        initData();
    }

    private void initData() {
        //自动刷新
        selfRefresh();
        addCount = new AddCount();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.boardcast");
        registerReceiver(addCount, filter);
    }

    /**
     * 自动刷新
     */
    private void selfRefresh() {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });
        this.onRefresh();
    }

    private void initView() {

        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);

        swipeRefresh.setOnRefreshListener(this);

        lvHomeHotelpage.setIsEnale(true);

        lvHomeHotelpage.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HotelTodayRecommendedAdapter(this);

        lvHomeHotelpage.setAdapter(adapter);

        View headerView = LayoutInflater.from(this).inflate(R.layout.hotel_home_list_header_new, null);

        findViewFromHeader(headerView);

        initHeadData();

        RecyclerViewUtils.setHeaderView(lvHomeHotelpage, headerView);

        lvHomeHotelpage.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener() {

                persenter.sendHotelThemeRequest();
            }
        });

        lvHomeHotelpage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                boolean flag = !recyclerView.canScrollVertically(-1);

                if(flag){

                    rlHotelHomeTitle.setVisibility(View.VISIBLE);

                }else {

                    rlHotelHomeTitle.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });

        adapter.setOnItemClickListener(new HotelTodayRecommendedAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int index) {

                if (adapter.getItem(index) != null) {
                    HotelThemeBean bean = (HotelThemeBean) adapter.getItem(index);
                    Intent it = new Intent(HotelHomeActivity.this, HotelRecommedeyHotelActivity.class);
                    it.putExtra("data", bean);
                    startActivity(it);
                }
            }
        });

        initBottom();

    }

    private void initHeadData() {

        Date startDate = new Date();

        Date endDate = DateUtil.addDay(startDate, 1);

        setHeadData(startDate, endDate);

    }

    private List<DefindTabView> defindTabViewList = new ArrayList<DefindTabView>();

    //初始化底部
    private void initBottom() {

        for (int i = 0; i < 4; i++) {

            DefindTabView defindTabView = new DefindTabView(this, tabOnClick);

            int[] textColor = new int[]{R.color.text_color_14, R.color.text_color_14};

            defindTabView.setTextColor(textColor);

            if (i == 0) {//

                int[] drawable0 = new int[]{R.mipmap.icon_tab_today_left_room, R.mipmap.icon_tab_today_left_room};
                defindTabView.setViewData(R.string.tab_today_left_room, drawable0);

            } else if (i == 1) {
                int[] drawable1 = new int[]{R.mipmap.icon_tab_gift_package, R.mipmap.icon_tab_gift_package};
                defindTabView.setViewData(R.string.tab_gift_package, drawable1);

            } else if (i == 2) {//出发时间
                int[] drawable2 = new int[]{R.mipmap.icon_tab_life_stage, R.mipmap.icon_tab_life_stage};
                defindTabView.setViewData(R.string.tab_life_stage, drawable2);
            } else if (i == 3) {//排序
                int[] drawable3 = new int[]{R.mipmap.icon_tab_bank_preferential, R.mipmap.icon_tab_bank_preferential};
                defindTabView.setViewData(R.string.tab_bank_preferential, drawable3);
            }
            defindTabView.addTabToLL(llBottomTab, i);

            defindTabViewList.add(defindTabView);
        }
    }

    /**
     * 底部tab监听事件
     */
    private DefindTabView.OnClickAction tabOnClick = new DefindTabView.OnClickAction() {
        @Override
        public void onTabClickItem(int index, TextView textView, boolean selectedStatus) {

            if (index == 0) {//今日甩房
                readyGo(HotelHomeActivity.this, HotelTodayLeftRoomListActivity.class);
            } else if (index == 1) {//礼品套餐
                readyGo(HotelHomeActivity.this, HotelGiftPackageListActivity.class);
            } else if (index == 2) {//生活分期
                readyGo(HotelHomeActivity.this, HotelLifeStageListActivity.class);
            } else if (index == 3) {//银行优惠
                readyGo(HotelHomeActivity.this, HotelBankPreferentialListActivity.class);
            }

        }
    };

    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                initShow1ItemView();

            }
        }, 200);

        startIndex = 0;
        // 发送推荐主题请求
        persenter.sendHotelThemeRequest();

    }

    //初始广告信息
    private void initShow1ItemView() {

        slideShowView1.setParam(AppConstant.HOTEL_HOMEPAGER, cityId + "", GlobalVariable.industryId + "");

        swipeRefresh.setRefreshing(false);

        slideShowView1.setOnDataLoadFinishListener(new SlideShow1ItemView.OnDataLoadFinishListener() {
            @Override
            public void onDataLoadFinish() {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    /**
     * init header中的view；
     */
    private SlideShow1ItemView slideShowView1;

    private Button btn_search;

    private LinearLayout tjHotelList;

    /**
     * 初始化headerview视图
     */
    private void findViewFromHeader(View headerView) {

        slideShowView1 = (SlideShow1ItemView) headerView.findViewById(R.id.slideShowView1);

        btn_search = (Button) headerView.findViewById(R.id.btn_search);

        btn_search.setOnClickListener(this);

        headerView.findViewById(R.id.llFourStartUp).setOnClickListener(this);

        headerView.findViewById(R.id.ll_level_price).setOnClickListener(this);

        headerView.findViewById(R.id.llStardEndDate).setOnClickListener(this);

        headerView.findViewById(R.id.llBankFastSale).setOnClickListener(this);

        tvDuration = (TextView) headerView.findViewById(R.id.tv_duration);

        tvStartTime = (TextView) headerView.findViewById(R.id.tv_startTime);

        tvStartWeek = (TextView) headerView.findViewById(R.id.tv_start_week);

        tvEndTime = (TextView) headerView.findViewById(R.id.tv_endTime);

        tvEndWeek = (TextView) headerView.findViewById(R.id.tv_end_week);

        headerView.findViewById(R.id.ll_location).setOnClickListener(this);

        headerView.findViewById(R.id.llMyPostion).setOnClickListener(this);
        tv_destination = (TextView) headerView.findViewById(R.id.tv_destination);

        tvLevel = (TextView) headerView.findViewById(R.id.tv_level);

        ivClearStarPrice = (ImageView) headerView.findViewById(R.id.ivClearStarPrice);

        ivClearStarPrice.setOnClickListener(this);

        tv_key = (TextView) headerView.findViewById(R.id.tv_key);

        tv_key.setOnClickListener(this);

        TextPaint tp = tv_key.getPaint();

        tp.setFakeBoldText(false);

        ivClear = (ImageView) headerView.findViewById(R.id.ivClear);

        ivClear.setVisibility(View.INVISIBLE);

        ivClear.setOnClickListener(this);

        tjHotelList = (LinearLayout) headerView.findViewById(R.id.tjHotelList);

        LinearLayout ll_destination = (LinearLayout) headerView.findViewById(R.id.ll_destination);

        ll_destination.setOnClickListener(this);

    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llFourStartUp:

                HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();
                //设置查询四星级以上的酒店
                StringBuffer startSb = new StringBuffer();

                startSb.append(5).append(",").append(6);

                productListParam.setLevels(startSb.toString());

            case R.id.btn_search:

                readyGo(HotelHomeActivity.this, HotelProductListActivity.class);

                break;
            case R.id.llBankFastSale://银行限时抢购活动
                //设置查询银行限时抢购参数
                HotelLogicManager.getInstance().getFlashSaleHotelParam().setActType(2);

                Intent giftPackageIntent = new Intent(HotelHomeActivity.this, HotelGiftPackageListActivity.class);

                startActivity(giftPackageIntent);

                break;
            case R.id.ll_level_price://价格星级选择器

                if (hotelStarPricePopup == null) {

                    hotelStarPricePopup = new HotelStarPricePopup(HotelHomeActivity.this, BaseFullPP.ViewPostion.VIEW_BOTTOM);

                    hotelStarPricePopup.setViewDataCallBack(ppViewDataCallBack);

                }else {

                    hotelStarPricePopup.showSelectedData();
                }

                hotelStarPricePopup.showFull(getWindow().getDecorView());

                break;
            case R.id.llStardEndDate://设置入店或者离店日期

                if (appCalendarPopup == null) {

                    appCalendarPopup = new AppCalendarPopup(HotelHomeActivity.this, -1, BaseFullPP.ViewPostion.VIEW_BOTTOM);

                    appCalendarPopup.setType(AppCalendarPopup.CalendarType.DOUBLE);

                    appCalendarPopup.setCalendarCallBack(calendarDataCallBack);

                }

                View rootView = getWindow().getDecorView();

                appCalendarPopup.showBottomByViewPP(rootView);

                break;
            case R.id.llMyPostion:
            case R.id.ll_location://我的位置

                currentLocation();

                break;
            case R.id.ll_destination://选择城市

                Intent intentCity = new Intent(HotelHomeActivity.this, CivilInternationCityActivity.class);

                startActivityForResult(intentCity, 1000);

                break;
            case R.id.ivClearStarPrice://重置價格星級

                if (hotelStarPricePopup != null) {

                    hotelStarPricePopup.reSetPpData();
                }

                tvLevel.setText("");

                ivClearStarPrice.setVisibility(View.VISIBLE);

                break;
            case R.id.tv_key://关键字查询

                Intent intent = new Intent(HotelHomeActivity.this, HotelSearchActivity.class);

                if (subItemBean != null) {
                    intent.putExtra("transmitData", subItemBean);
                }
                startActivityForResult(intent, 1000);

                break;
            case R.id.ivClear://删除地名

                ivClear.setVisibility(View.INVISIBLE);

                tv_key.setText("");

                TextPaint tp = tv_key.getPaint();

                tp.setFakeBoldText(false);

                subItemBean = null;

                HotelLogicManager.getInstance().getHotelProductListParam().setHotelKeyWordList(null);

                HotelProductListParam productListParamClare = HotelLogicManager.getInstance().getHotelProductListParam();

                productListParamClare.setSearchAddrLat(0);

                productListParamClare.setSearchAddrLng(0);
                break;

            default:
                break;

        }
    }

    /**
     * 价格星级回调数据
     */
    private HotelStarPricePopup.ViewDataListCallBack ppViewDataCallBack = new HotelStarPricePopup.ViewDataListCallBack() {
        @Override
        public void onConfirm(List<HotelLevelBean> selectedList, int minValue, int maxValue) {
            //设置价格 或星级
            HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

            StringBuffer sb = new StringBuffer();

            int size = selectedList.size();

            if (size > 0) {

                StringBuffer sbLevels = new StringBuffer();

                StringBuffer sbBrandTypes = new StringBuffer();

                for (int i = 0; i < size; i++) {

                    HotelLevelBean bean = selectedList.get(i);

                    sb.append(bean.getStartName());

                    if (size - 1 == i) {

                    } else {
                        sb.append(",");
                    }

                    int index = bean.getLpIndex();

                    if (index == 1) {//经济型

                        sbBrandTypes.append(bean.getStartValue() + ",");

                        if (size == 1) {

                            sbLevels.append("0");
                        }

                    } else if (index == 2) {//二星级

                        sbLevels.append(bean.getStartValue() + ",");

                        if (size == 1) {

                            sbBrandTypes.append("0");
                        }


                    } else if (index == 6) {//五星级以上

                        sbLevels.append(bean.getStartValue() + ",");

                        if (size == 1) {

                            sbBrandTypes.append("0");
                        }


                    } else {

                        sbLevels.append(bean.getStartValue() + ",");

                        sbBrandTypes.append(bean.getStartValue() + ",");
                    }

                }
                sb.append(",¥");

                productListParam.setLevels(sbLevels.toString());

                productListParam.setBrandTypes(sbBrandTypes.toString());
            } else {

                sb.append("不限,¥");

                productListParam.setLevels(0 + "");

                productListParam.setBrandTypes(0 + "");
            }

            if (maxValue == 1001) {

                sb.append(minValue + "-1000+");

                productListParam.setBgnPrice(minValue + "");

                productListParam.setEndPrice(Integer.MAX_VALUE + "");

            } else {

                productListParam.setBgnPrice(minValue + "");

                productListParam.setEndPrice(maxValue + "");

                if (minValue == maxValue) {
                    sb.append(minValue);
                } else {
                    sb.append(minValue + "-" + maxValue);
                }

            }

            if ("不限,¥0-1000+".equals(sb.toString())) {

                tvLevel.setText("");

                ivClearStarPrice.setVisibility(View.INVISIBLE);

            } else {
                if (ivClearStarPrice.getVisibility() == View.INVISIBLE) {

                    ivClearStarPrice.setVisibility(View.VISIBLE);
                }
                tvLevel.setText(sb.toString());

            }

        }
    };
    private ChannelTypePopup channelTypePopup;

    private int selectedIndex = 0;

    private ChannelPopWindow channelPopWindow;
    //app频道选择方法
    public void onMenuAction(View v) {

//        if (channelTypePopup == null) {
//
//            channelTypePopup = new ChannelTypePopup(this, -1, BaseFullPP.ViewPostion.VIEW_BOTTOM);
//
//            channelTypePopup.setSelectIndex(selectedIndex);
//
//            channelTypePopup.setCallBack(dataCallBackChannelType);
//
//        }
//
//        View rootView = getWindow().getDecorView();
//
//        channelTypePopup.showBottomByViewPP(rootView);

        if(channelPopWindow == null){

            channelPopWindow = new ChannelPopWindow(channelChangeHandler, this);

        }

        channelPopWindow.showAsDropDown(rlHotelHomeTitle, channelPopWindow.getWidth(), 0);
    }
    private Handler channelChangeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            int what = msg.what;

            if(what == 0){//

                ChildTypeBean  bean = (ChildTypeBean) msg.obj;

                int  pagetypet = AppFactory.channelIdToFragmentIndex(bean.id);

                if(pagetypet != -1){

                    GlobalVariable.industryId = Integer.parseInt(bean.id);

                    if(pagetypet == 2){//酒店



                    }
                    else if(pagetypet == 1){//机票

                        startActivity(new Intent(HotelHomeActivity.this, AirTicketHomeActivity.class));

                        finish();
                    }
                    else{//旅游
                        String typeId = AppConstant.travel_id;

                        int index = AppFactory.channelIdToFragmentIndex(typeId);

                        GlobalVariable.industryId = Integer.parseInt(typeId);

                        Intent intent = new Intent();

                        intent.setClass(HotelHomeActivity.this, ChannelMainActivity.class);

                        intent.putExtra("pagetype", index);

                        intent.putExtra("data", bean);

                        startActivity(intent);

                        finish();
                    }

                }else{
                    ToastUtil.i(HotelHomeActivity.this,"敬请期待");
                }
            }
        }
    };
//    /**
//     * app频道选择回调数据
//     */
//    private ChannelTypePopup.BottomDataListCallBack dataCallBackChannelType = new ChannelTypePopup.BottomDataListCallBack() {
//        @Override
//        public void onClickItemDataBack(String name, int nameValue, int selectIndex) {
//            if (selectIndex == 1) {//机票
//
//                readyGo(HotelHomeActivity.this, AirTicketHomeActivity.class);
//
//                finish();
//
//            } else if (selectIndex == 2) {//旅游
//
//                String childTypeJson = SharePrefUtil.getValueFromSp(HotelHomeActivity.this,
//                        AppConstant.sp_childtypelist_home, "[]");
//
//                List<ChildTypeBean> childTypeBeans = JSON.parseArray(childTypeJson, ChildTypeBean.class);
//
//                ChildTypeBean selectedBean = null;
//
//                String typeId = AppConstant.travel_id;
//
//                for (ChildTypeBean bean : childTypeBeans) {
//
//                    if (bean.id.equals(typeId)) {
//
//                        selectedBean = bean;
//
//                        break;
//                    }
//
//                }
//
//                if (selectedBean != null) {
//
//                    int index = AppFactory.channelIdToFragmentIndex(typeId);
//
//                    GlobalVariable.industryId = Integer.parseInt(typeId);
//
//                    Intent intent = new Intent();
//
//                    intent.setClass(HotelHomeActivity.this, ChannelMainActivity.class);
//
//                    intent.putExtra("pagetype", index);
//
//                    intent.putExtra("data", selectedBean);
//
//                    startActivity(intent);
//
//                    finish();
//                }
//            }
//        }
//    };
    /**
     * 日历数据回调
     */
    private AppCalendarPopup.DataCalendarCallBack calendarDataCallBack = new AppCalendarPopup.DataCalendarCallBack() {
        @Override
        public void onSelectorStartEndDate(Date startDate, Date endDate) {
            setHeadData(startDate, endDate);
        }
    };

    /**
     * 设置酒店入住离店日期
     *
     * @param startDate
     * @param endDate
     */
    private void setHeadData(Date startDate, Date endDate) {

        tvDuration.setText("共" + DateUtil.naturalDaysBetween(startDate, endDate) + getString(R.string.hotel_toast_night));

        tvStartTime.setText(DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_MONTH_DAY));

        tvEndTime.setText(DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_MONTH_DAY));

        tvStartWeek.setText(DateUtil.getDateExplain(startDate));

        tvEndWeek.setText(DateUtil.getDateExplain(endDate));
        //设置日期
        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        productListParam.setSearchAddrLat(positionLatitude);

        productListParam.setSearchAddrLng(positionLongitude);

        productListParam.setSearchAddrType(0);

        productListParam.setArrDate(DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE));

        productListParam.setDepDate(DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_DATE));

        productListParam.setAddrName(cityName);

        tv_destination.setText(cityName);
    }

    /**
     * 获取当前位置信息
     */
    private void currentLocation() {

        showNoCancelPDialog(R.string.is_located);
        //重新开启定位功能
        GlobalApp.getInstance().setOnCityChangeListeners(onCityChangeListener);

        GlobalApp.getInstance().toReposition();

    }

    //定位数据回调
    private GlobalApp.OnCityChangeListener onCityChangeListener = new GlobalApp.OnCityChangeListener() {
        @Override
        public void onCityChange(Location city) {

            GlobalApp.getInstance().removeListener(onCityChangeListener);

            reSetUseAddress(city);

            closePDialog();


        }
    };

    private void reSetUseAddress(Location city){

        tv_destination.setText(city.addressInfoStr());

        GlobalApp.getInstance().setMCity(city);

        cityId = city.getCityId() + "";

        initShow1ItemView();

        //重新设置经纬度信息和类型
        updateUseCurrentPositionInfor();

        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        productListParam.setAddrName(city.cityName);

        productListParam.setSearchAddrLat(positionLatitude);

        productListParam.setSearchAddrLng(positionLongitude);

        productListParam.setSearchAddrType(1);//0市中心；1我的位置；

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        String cityNameStr = tv_destination.getText().toString();

        reSetCityInfo();

        if (cityName != null && !cityName.equals(cityNameStr)&&specialFlag) {

            tv_destination.setText(cityName);

            initShow1ItemView();

            specialFlag = true;
        }
    }

    private SubItemBean subItemBean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {

            if (resultCode == 1040) {//选择搜索页面的查询项

                if (data != null) {

                    subItemBean = (SubItemBean) data.getSerializableExtra("selectoSubItemBean");

                    if (subItemBean != null) {

                        tv_key.setText(Html.fromHtml(subItemBean.getFilterName()));

                        TextPaint tp = tv_key.getPaint();

                        tp.setFakeBoldText(true);

                        ivClear.setVisibility(View.VISIBLE);

                        List<SubItemBean> hotelKeyWordList = HotelLogicManager.getInstance().getHotelProductListParam().getHotelKeyWordList();

                        if (hotelKeyWordList == null) {

                            hotelKeyWordList = new ArrayList<SubItemBean>();
                        } else {
                            hotelKeyWordList.clear();
                        }

                        hotelKeyWordList.add(subItemBean);

                        HotelLogicManager.getInstance().getHotelProductListParam().setHotelKeyWordList(hotelKeyWordList);
                    } else {

                        HotelLogicManager.getInstance().getHotelProductListParam().setHotelKeyWordList(null);
                    }
                } else {

                    HotelLogicManager.getInstance().getHotelProductListParam().setHotelKeyWordList(null);
                }

            } else if (resultCode == 1041) {//搜索关键字

                if (data != null) {

                    SearchReusultBean searchReusultBean = (SearchReusultBean) data.getSerializableExtra("selectoSubItemBean");

                    if (searchReusultBean != null) {

                        subItemBean = new SubItemBean();

                        subItemBean.setFilterName(searchReusultBean.getSearchName());

                        subItemBean.setChildTypeCode(searchReusultBean.getTypeCode());

                        subItemBean.setFilterId(searchReusultBean.getSearchId());

                        tv_key.setText(Html.fromHtml(searchReusultBean.getSearchName()));

                        TextPaint tp = tv_key.getPaint();

                        tp.setFakeBoldText(true);

                        ivClear.setVisibility(View.VISIBLE);

                        List<SearchReusultBean> searchReusultBeanList = new ArrayList<>();

                        searchReusultBeanList.add(searchReusultBean);

                        HotelLogicManager.getInstance().getHotelProductListParam().setSearchList(searchReusultBeanList);

                    } else {

                        HotelLogicManager.getInstance().getHotelProductListParam().setSearchList(null);
                    }

                } else {

                    HotelLogicManager.getInstance().getHotelProductListParam().setSearchList(null);
                }

            } else if (resultCode == 5001) {//选择城市

                reSetCityInfo();

                HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                productListParam.setSearchAddrLat(positionLatitude);

                productListParam.setSearchAddrLng(positionLongitude);

                productListParam.setSearchAddrType(0);

                productListParam.setAddrName(cityName);

                tv_destination.setText(cityName);

                initShow1ItemView();

            }else if(resultCode == 5002){//城市搜索页面--当前城市

                specialFlag = false;

                Location   city =   GlobalApp.getPositionedCity();

                reSetUseAddress(city);

            }
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type) {
        swipeRefresh.setRefreshing(false);

        if (type == IhotelHome.HOTELTHEME_CODE) {
            //  RecyclerViewStateUtils.setFooterViewState(listview, LoadingFooter.State.Normal);
            //服务器返回9993
            if (o == null) {

                if (startIndex == 0) {

                    adapter.clearData();

                    if (tjHotelList.getVisibility() == View.VISIBLE) {

                        tjHotelList.setVisibility(View.GONE);
                    }
                } else {

                    // ifLoadMore = false;
                    lvHomeHotelpage.calByNewNum(0);
                }
            } else {

                if (o instanceof List) {

                    List<HotelThemeBean> list = (List<HotelThemeBean>) o;

                    int size = list.size();

                    if (size > 0) {

                        if (startIndex == 0) {

                            htbList.clear();

                            adapter.addNewList(list);

                            htbList.addAll(list);

                        } else {

                            adapter.addMoreList(list);

                            htbList.addAll(list);

                            lvHomeHotelpage.calByNewNum(size);

                        }

                        if (size == AppConstant.MAX_PAGE_NUM) {

                            startIndex = startIndex + AppConstant.MAX_PAGE_NUM;
                        }

                        if (tjHotelList.getVisibility() == View.GONE) {

                            tjHotelList.setVisibility(View.VISIBLE);

                        }
                    }
                }
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        swipeRefresh.setRefreshing(false);

        if (type == IhotelHome.HOTELTHEME_CODE) {

            tjHotelList.setVisibility(View.GONE);

            adapter.clearData();

            if (startIndex > 0) {

                startIndex = startIndex - AppConstant.MAX_PAGE_NUM;
            }
        }
    }

    private int startIndex = 0;

    @Override
    public String[] gatherHotelThemeRequestParam() {

        String a = startIndex + "";

        return new String[]{a};
    }

    @Override
    public void callBackData(Object o) {

    }


    private List<HotelThemeBean> htbList = new ArrayList<>();

    /**
     * 注册广播
     */
    private class AddCount extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.boardcast")) {
                for (int i = 0; i < htbList.size(); i++) {
                    if (intent.getExtras().getLong("themId") == htbList.get(i).getThemeId()) {
                        adapter.setCount(i);
                    }
                }

            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(addCount);

        HotelLogicManager.getInstance().clearData();
    }
}
