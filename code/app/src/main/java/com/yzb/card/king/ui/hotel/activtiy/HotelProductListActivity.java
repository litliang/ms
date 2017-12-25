package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.BankActivityInfoBean;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SearchReusultBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.common.PaymethodAndBankPreStageBean;
import com.yzb.card.king.bean.hotel.HotelLevelBean;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.hotel.HotelProductObjectBean;
import com.yzb.card.king.bean.hotel.PromotionTypeBean;
import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.DefindTabView;
import com.yzb.card.king.ui.appwidget.DefineTopView;
import com.yzb.card.king.ui.appwidget.popup.AppCalendarPopup;
import com.yzb.card.king.ui.appwidget.popup.BankSelectPopup;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.appwidget.popup.HotelBrandPopup;
import com.yzb.card.king.ui.appwidget.popup.HotelSortTypePopup;
import com.yzb.card.king.ui.appwidget.popup.HotelStarPricePopup;
import com.yzb.card.king.ui.appwidget.popup.ProductPromotionTypePopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.adapter.HotelProductItemAdapter;
import com.yzb.card.king.ui.hotel.persenter.GetCouponPersenter;
import com.yzb.card.king.ui.hotel.persenter.HotelBankActivityPersenter;
import com.yzb.card.king.ui.hotel.persenter.HotelProductListPresenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.activity.CivilInternationCityActivity;
import com.yzb.card.king.ui.other.fragment.HotelMapModelFragment;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DateUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;

/**
 * 酒店產品列表页
 * Created by 玉兵 on 2017/7/15.
 */
@ContentView(R.layout.activity_hotel_product_list)
public class HotelProductListActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    private boolean listModel = true;//默认列表模式，false地图模式

    @ViewInject(R.id.rlMap)
    private RelativeLayout rlMap;

    @ViewInject(R.id.llTopFuction)
    private LinearLayout llTopFuction;

    @ViewInject(R.id.llTop)
    private LinearLayout llTop;

    @ViewInject(R.id.recycler_view)
    private RefreshRecyclerView mRecyclerView;
    //底部控件
    @ViewInject(R.id.llBottomTab)
    private LinearLayout llBottomTab;

    @ViewInject(R.id.tvSearch)
    private TextView tvSearch;

    @ViewInject(R.id.tvCityName)
    private TextView tvCityName;

    @ViewInject(R.id.tvMapController)
    private TextView tvMapController;

    private List<DefineTopView> defineTopViewList;

    private int page = 1;

    private HotelProductItemAdapter mAdapter;

    private BankSelectPopup bankSelectPopup;

    private int selectedBankIndex = -1;

    private BankSelectPopup lifeStageSelectPopup;

    private int selectedLifeStageIndex = -1;

    private LinearLayout llSEdate;

    private AppCalendarPopup appCalendarPopup;

    private TextView tvStartDate, tvEndDate;

    private HotelStarPricePopup hotelStarPricePopup;

    private ProductPromotionTypePopup productPromotionTypePopup;

    private HotelBrandPopup hotelBrandPopup;

    private HotelSortTypePopup todayLeftSortPopup;

    private int selectedSortIndex = -1;

    private HotelProductListPresenter hotelProductListPresenter;

    //地图模式
    private HotelMapModelFragment mapFragment;

    private boolean bankFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        defaultFlag = true;
        hotelProductListPresenter = new HotelProductListPresenter(this);
        initTitleView();
        initTopFuction();
        initContent();
        initBottom();

    }

    private void initTitleView() {
        findViewById(R.id.llSelectedAddress).setOnClickListener(this);

        findViewById(R.id.headerLeftImage).setOnClickListener(this);

        if (selectedCity != null) {
            tvCityName.setText(selectedCity.getCityName());
        }

        tvMapController.setOnClickListener(this);
    }

    private void initContent() {
        mAdapter = new HotelProductItemAdapter(this);
        //检测是否有查询特定银行酒店信息
        if (getIntent().hasExtra("bankData") && getIntent().hasExtra("bankType")) {

            int bankType = getIntent().getIntExtra("bankType", 0);

            if (bankType != 0) {

                BankActivityInfoBean data = (BankActivityInfoBean) getIntent().getSerializableExtra("bankData");

                String proBank = "";

                if (bankType == HotelBankActivityPersenter.BANK_PRE_ACT_LIST_CODE) {//生活分期

                    proBank = data.getBankName() + "信用卡分期";

                } else if (bankType == HotelBankActivityPersenter.BANK_LIFE_STAGE_LIST_CODE) {

                    proBank = data.getBankName() + " " + data.getActName();
                }
                //添加Header
                final TextView textView = new TextView(this);

                textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(this, 25)));

                textView.setBackgroundColor(Color.parseColor("#f0f5f9"));

                textView.setTextColor(Color.parseColor("#44698f"));

                textView.setTextSize(12);

                textView.setGravity(Gravity.CENTER);

                textView.setText(proBank);

                mAdapter.setHeader(textView);

                //禁止银行优惠和生活分期选择
                bankFlag = false;

                DefineTopView oneDefineTopView = defineTopViewList.get(1);

                oneDefineTopView.setUiColor();

                DefineTopView twoDefineTopView = defineTopViewList.get(2);

                twoDefineTopView.setUiColor();
            }

        }

        //检测是否有代金券
        if (getIntent().hasExtra("CouponInfo")) {

            CouponInfoBean bean = (CouponInfoBean) getIntent().getSerializableExtra("CouponInfo");
            //添加Header
            final TextView textView = new TextView(this);

            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(this, 25)));

            textView.setBackgroundColor(Color.parseColor("#f0f5f9"));

            textView.setTextColor(Color.parseColor("#44698f"));

            textView.setTextSize(12);

            textView.setGravity(Gravity.CENTER);

            textView.setText(bean.getActName());

            mAdapter.setHeader(textView);
            //设置查询使用代金券
            HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

            productListParam.setCashCouponId(bean.getActId() + "");

        }

        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {

                getData(true);
            }
        });

        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {

                getData(false);

            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });

    }

    public void getData(final boolean isRefresh) {

        if (isRefresh) {//刷新

            page = 0;

        } else {

            page = mAdapter.getItemCount();
        }

        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        productListParam.setAddrId(Integer.parseInt(cityId));

        productListParam.setPageStart(page);

        if (listModel) {

            hotelProductListPresenter.sendHotelProductListRequest(productListParam);

        } else {

            if (mapFragment != null) {

                mapFragment.refreshData();

            }

        }

    }

    private void initTopFuction() {

        String[] nameArray = this.getResources().getStringArray(R.array.tab_hotel_type_name_array);

        defineTopViewList = new ArrayList<>();

        for (int i = 0; i < nameArray.length; i++) {

            DefineTopView defindTabView = new DefineTopView(this, onClickAction);

            defindTabView.setTabName(nameArray[i]);

            defindTabView.addTabToLL(llTopFuction, i);

            defineTopViewList.add(defindTabView);
        }

        llSEdate = (LinearLayout) findViewById(R.id.llSEdate);

        llSEdate.setOnClickListener(this);

        tvStartDate = (TextView) findViewById(R.id.tvStartDate);

        tvEndDate = (TextView) findViewById(R.id.tvEndDate);

        tvSearch.setOnClickListener(this);
        //设置日期
        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        Date startDate = DateUtil.string2Date(productListParam.getArrDate(), DateUtil.DATE_FORMAT_DATE);

        Date endDate = DateUtil.string2Date(productListParam.getDepDate(), DateUtil.DATE_FORMAT_DATE);

        tvStartDate.setText("住 " + DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE_DAY2));

        tvEndDate.setText("离 " + DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_DATE_DAY2));
    }


    private DefineTopView.OnClickAction onClickAction = new DefineTopView.OnClickAction() {
        @Override
        public void onTabClickItem(int index, TextView textView, boolean selectedStatus) {

            if (index == 1) {//银行优惠

                if (bankFlag) {

                    if (bankSelectPopup == null) {

                        bankSelectPopup = new BankSelectPopup(HotelProductListActivity.this, -1, true);

                        bankSelectPopup.setSelectIndex(selectedBankIndex);

                        bankSelectPopup.setCallBack(bankFreCallBankBack);
                    } else {

                        bankSelectPopup.showSelectedData();
                    }


                    bankSelectPopup.showPP(llTop);
                }

            } else if (index == 2) {//生活分期

                if (bankFlag) {

                    if (lifeStageSelectPopup == null) {

                        lifeStageSelectPopup = new BankSelectPopup(HotelProductListActivity.this, -1, true);

                        lifeStageSelectPopup.setSelectIndex(selectedLifeStageIndex);

                        lifeStageSelectPopup.setCallBack(lifeStageCallBack);

                    } else {

                        lifeStageSelectPopup.showSelectedData();
                    }

                    lifeStageSelectPopup.showPP(llTop);

                }

            } else if (index == 0) {//优惠促销

                if (productPromotionTypePopup == null) {

                    productPromotionTypePopup = new ProductPromotionTypePopup(HotelProductListActivity.this, -1);

                    productPromotionTypePopup.setCallBack(callBackPtp);
                } else {

                    productPromotionTypePopup.showSelectedData();
                }

                HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                Date startDate = DateUtil.string2Date(productListParam.getArrDate(), DateUtil.DATE_FORMAT_DATE);

                productPromotionTypePopup.setTypeDataByDate(startDate);

                productPromotionTypePopup.showPP(llTop);

            } else if (index == 3) {//酒店品牌

                if (hotelBrandPopup == null) {

                    hotelBrandPopup = new HotelBrandPopup(HotelProductListActivity.this, 313, "1");

                    hotelBrandPopup.setCallBack(entryCallBack);

                } else {

                    hotelBrandPopup.showSelectedData();
                }
                hotelBrandPopup.showPP(llTop);
            }
        }
    };
    /**
     * 银行优惠回调数据
     */
    private BankSelectPopup.BottomDataListCallBack bankFreCallBankBack = new BankSelectPopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex) {
            DefineTopView defineTopView = defineTopViewList.get(1);

            if (name == null && nameValue == -1) {

                defineTopView.setTabName("银行优惠");

                defineTopView.setTabCheckStatus(false);

            } else {

                defineTopView.setTabName(name);

                defineTopView.setTabCheckStatus(true);
            }
            HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

            if (selectIndex == 0) {//全部银行

                productListParam.setBankIds(bankSelectPopup.getAllBankIds());

            }
//            else if (selectIndex == 1) {//我的银行
//
//                if(UserManager.getInstance().isLogin()){
//
//                    productListParam.setBankIds(bankSelectPopup.getMyBankIds());
//                }else {
//
//                    List<PaymethodAndBankPreStageBean> bankList = bankSelectPopup.getTotalList();
//
//                    if (bankList != null) {
//
//                        PaymethodAndBankPreStageBean bean = bankList.get(selectIndex - 1);
//
//                        productListParam.setBankIds(bean.getBankId() + "");
//
//                    }
//                }
//
//            }
            else if (selectIndex >= 1) {//其它银行

                List<PaymethodAndBankPreStageBean> bankList = bankSelectPopup.getTotalList();

                if (bankList != null) {

                    int numnber = 1;

//                    if(UserManager.getInstance().isLogin()){
//
//                        numnber = 2;
//                    }

                    PaymethodAndBankPreStageBean bean = bankList.get(selectIndex - numnber);

                    productListParam.setBankIds(bean.getBankId() + "");

                }

            } else {

                productListParam.setBankIds(null);
            }

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };
    /**
     * 生活分期数据回调
     */
    private BankSelectPopup.BottomDataListCallBack lifeStageCallBack = new BankSelectPopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex) {
            DefineTopView defineTopView = defineTopViewList.get(2);

            if (name == null && nameValue == -1) {

                defineTopView.setTabName("生活分期");

                defineTopView.setTabCheckStatus(false);

            } else {

                defineTopView.setTabName(name);

                defineTopView.setTabCheckStatus(true);
            }

            HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

            if (selectIndex == 0) {//全部银行

                productListParam.setStageBankIds(lifeStageSelectPopup.getAllBankIds());

            }
//            else if (selectIndex == 1) {//我的银行
//
//                if(UserManager.getInstance().isLogin()){
//
//                    productListParam.setStageBankIds(lifeStageSelectPopup.getMyBankIds());
//                }else {
//
//                    List<PaymethodAndBankPreStageBean> bankList = lifeStageSelectPopup.getTotalList();
//
//                    if (bankList != null) {
//
//                        PaymethodAndBankPreStageBean bean = bankList.get(selectIndex - 1);
//
//                        productListParam.setStageBankIds(bean.getBankId() + "");
//
//                    }
//                }
//
//            }
            else if (selectIndex >= 1) {//其它银行

                List<PaymethodAndBankPreStageBean> bankList = lifeStageSelectPopup.getTotalList();

                if (bankList != null) {

                    int numnber = 1;

//                    if(UserManager.getInstance().isLogin()){
//
//                        numnber = 2;
//                    }

                    PaymethodAndBankPreStageBean bean = bankList.get(selectIndex - numnber);

                    productListParam.setStageBankIds(bean.getBankId() + "");

                }

            } else {

                productListParam.setStageBankIds(null);
            }


            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };

    /**
     * 优惠促销回调
     */
    private ProductPromotionTypePopup.DataListCallBack callBackPtp = new ProductPromotionTypePopup.DataListCallBack() {
        @Override
        public void onConfirm(List<PromotionTypeBean> selectoredListData) {
            DefineTopView defineTopView = defineTopViewList.get(0);
            int size = selectoredListData.size();

            HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();
            if (size > 0) {

                defineTopView.setTabCheckStatus(true);

                StringBuffer youhuiSb = new StringBuffer();

                for (int i = 0; i < size; i++) {

                    PromotionTypeBean bean = selectoredListData.get(i);

                    youhuiSb.append(bean.getTypeValue());

                    if (i == size - 1) {

                    } else {
                        youhuiSb.append(",");
                    }
                }

                productListParam.setDisTypes(youhuiSb.toString());

            } else {

                productListParam.setDisTypes(null);

                defineTopView.setTabCheckStatus(false);
            }
            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };
    /**
     * 酒店品牌数据回调
     */
    private HotelBrandPopup.ViewDataCallBack entryCallBack = new HotelBrandPopup.ViewDataCallBack() {
        @Override
        public void onConfirm(List<SubItemBean> selectedBean) {
            DefineTopView defineTopView = defineTopViewList.get(3);

            HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

            int size = selectedBean.size();


            if (size == 0) {

                defineTopView.setTabCheckStatus(false);

                productListParam.setHotelBrandList(null);

            } else {

                defineTopView.setTabCheckStatus(true);

                productListParam.setHotelBrandList(selectedBean);

            }

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };


    private List<DefindTabView> defindTabViewList = new ArrayList<DefindTabView>();

    //初始化底部
    private void initBottom() {

        for (int i = 0; i < 4; i++) {

            DefindTabView defindTabView = new DefindTabView(this, tabOnClick);

            defindTabView.setPpflag(true);

            int[] textColor = new int[]{R.color.text_color_14, R.color.color_980100};

            defindTabView.setTextColor(textColor);

            if (i == 0) {//

                int[] drawable0 = new int[]{R.mipmap.ionc_tab_screening_nochecke, R.mipmap.ionc_tab_screening_checked};
                defindTabView.setViewData(R.string.tab_screening, drawable0);

            } else if (i == 1) {
                int[] drawable1 = new int[]{R.mipmap.icon_tab_start_price_nocheck, R.mipmap.icon_tab_start_price_checked};

                defindTabView.setViewData(R.string.tab_price_start, drawable1);

            } else if (i == 2) {//出发时间
                int[] drawable2 = new int[]{R.mipmap.con_tab_location_nochecke, R.mipmap.icon_tab_location_checked};
                defindTabView.setViewData(R.string.tab_lcation_area, drawable2);
            } else if (i == 3) {//排序
                int[] drawable3 = new int[]{R.mipmap.con_tab_sort_nochecke, R.mipmap.con_tab_sort_checked};
                defindTabView.setViewData(R.string.tab_sorting, drawable3);
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

            if (index == 0) {//筛选

                readyGoForResult(HotelProductListActivity.this, HotelScreenActivity.class, 1000);

            } else if (index == 1) {//星级价格

                if (hotelStarPricePopup == null) {

                    hotelStarPricePopup = new HotelStarPricePopup(HotelProductListActivity.this, -1, BaseFullPP.ViewPostion.VIEW_BOTTOM);

                    hotelStarPricePopup.setViewDataCallBack(ppViewDataCallBack);

                } else {

                    hotelStarPricePopup.showSelectedData();
                }

                hotelStarPricePopup.showPP(llBottomTab);


            } else if (index == 2) {//位置区域

                readyGoForResult(HotelProductListActivity.this, HotelDistrictActivity.class, 1000);

            } else if (index == 3) {//排序

                if (todayLeftSortPopup == null) {

                    todayLeftSortPopup = new HotelSortTypePopup(HotelProductListActivity.this, -1, BaseFullPP.ViewPostion.VIEW_BOTTOM);

                    todayLeftSortPopup.setSelectIndex(selectedSortIndex);

                    String[] nameArray = getResources().getStringArray(R.array.hotel_sort_name_array);

                    int[] valueArray = getResources().getIntArray(R.array.hotel_sort_value_array);

                    todayLeftSortPopup.setLogicData(nameArray, valueArray);

                    todayLeftSortPopup.setCallBack(callBackSort);
                }

                todayLeftSortPopup.showTopPP(llBottomTab);

            }


        }
    };

    private HotelSortTypePopup.BottomDataListCallBack callBackSort = new HotelSortTypePopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex) {
            //设置排序方式   0智能排序；1低价优先；2高价优先；3距离优先；

            if (nameValue != -1) {

                DefindTabView oneDefindTabView = defindTabViewList.get(3);

                oneDefindTabView.setSelectedTabStatus(true);

                HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                productListParam.setSort(nameValue);

                mRecyclerView.showSwipeRefresh();

                getData(true);
            }

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.headerLeftImage:

                finish();

                break;

            case R.id.llSEdate:

                if (appCalendarPopup == null) {

                    //设置日期
                    HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                    Date startDate = DateUtil.string2Date(productListParam.getArrDate(), DateUtil.DATE_FORMAT_DATE);

                    Date endDate = DateUtil.string2Date(productListParam.getDepDate(), DateUtil.DATE_FORMAT_DATE);

                    appCalendarPopup = new AppCalendarPopup(HotelProductListActivity.this, -1, startDate, endDate, BaseFullPP.ViewPostion.VIEW_BOTTOM);

                    appCalendarPopup.setCalendarCallBack(calendarDataCallBack);

                }

                View rootView = getWindow().getDecorView();

                appCalendarPopup.showBottomByViewPP(rootView);

                break;
            case R.id.tvSearch:

                Intent intent = new Intent(HotelProductListActivity.this, HotelSearchActivity.class);

                List<SubItemBean> hotelKeyWordList = HotelLogicManager.getInstance().getHotelProductListParam().getHotelKeyWordList();

                if (hotelKeyWordList != null && hotelKeyWordList.size() == 1) {
                    intent.putExtra("transmitData", hotelKeyWordList.get(0));
                }
                startActivityForResult(intent, 1000);

                break;

            case R.id.llSelectedAddress:

                Intent intentCity = new Intent(HotelProductListActivity.this, CivilInternationCityActivity.class);

                startActivityForResult(intentCity, 1000);

                break;

            case R.id.tvMapController://地图模式切换

                // 初始化地图；
                if (mapFragment == null) {

                    mapFragment = HotelMapModelFragment.newInstance();

                    FragmentManager manager = getSupportFragmentManager();

                    FragmentTransaction transaction = manager.beginTransaction();

                    transaction.replace(R.id.rlMap, mapFragment);

                    transaction.commit();
                }

                switchModel();

            default:
                break;

        }
    }


    private void switchModel() {
        if (listModel) {
            tvMapController.setText("列表");

            mRecyclerView.setVisibility(View.GONE);

            rlMap.setVisibility(View.VISIBLE);

            llBottomTab.getChildAt(3).setVisibility(View.GONE);

        } else {
            tvMapController.setText("地图");

            mRecyclerView.setVisibility(View.VISIBLE);

            rlMap.setVisibility(View.GONE);

            llBottomTab.getChildAt(3).setVisibility(View.VISIBLE);

        }
        listModel = !listModel;

    }

    private AppCalendarPopup.DataCalendarCallBack calendarDataCallBack = new AppCalendarPopup.DataCalendarCallBack() {
        @Override
        public void onSelectorStartEndDate(Date startDate, Date endDate) {

            tvStartDate.setText("住 " + DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE_DAY2));

            tvEndDate.setText("离 " + DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_DATE_DAY2));
            //设置日期
            HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

            productListParam.setArrDate(DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE));

            productListParam.setDepDate(DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_DATE));

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };

    private HotelStarPricePopup.ViewDataListCallBack ppViewDataCallBack = new HotelStarPricePopup.ViewDataListCallBack() {
        @Override
        public void onConfirm(List<HotelLevelBean> selectedList, int minValue, int maxValue) {
            //设置价格 或星级
            HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

            int size = selectedList.size();

            boolean oneFlag = false;

            boolean oneFlagTemp = false;

            if (size > 0) {

                oneFlag = true;

                StringBuffer sbLevels = new StringBuffer();

                StringBuffer sbBrandTypes = new StringBuffer();

                for (int i = 0; i < size; i++) {

                    HotelLevelBean bean = selectedList.get(i);

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

                productListParam.setLevels(sbLevels.toString());

                productListParam.setBrandTypes(sbBrandTypes.toString());

            } else {

                oneFlag = false;

                productListParam.setLevels(0 + "");

                productListParam.setBrandTypes(0 + "");
            }

            productListParam.setBgnPrice(minValue + "");

            if (maxValue == 1001) {

                productListParam.setEndPrice(Integer.MAX_VALUE + "");

            } else {

                productListParam.setEndPrice(maxValue + "");
            }

            if (maxValue == 1001 && minValue == 0) {

                oneFlagTemp = false;
            } else {

                oneFlagTemp = true;
            }

            DefindTabView oneDefindTabView = defindTabViewList.get(1);

            oneDefindTabView.setSelectedTabStatus(oneFlag || oneFlagTemp);

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {

            if (resultCode == 1001 && data != null) {//获取用户选择的筛选页的条件

                CatalogueTypeBean catalogueTypeBean = (CatalogueTypeBean) data.getSerializableExtra("transmitData");

                List<SubItemBean> selectedData = catalogueTypeBean.getChildList();

                int size = catalogueTypeBean.getChildList().size();

                DefindTabView oneDefindTabView = defindTabViewList.get(0);

                if (size > 0) {

                    oneDefindTabView.setSelectedTabStatus(true);

                    //设置评分 优惠促销 等筛选信息
                    HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                    List<SubItemBean> tempList = productListParam.getHotelBaseFilterList();
                    //清理下曾经已经选择的数据
                    tempList.clear();

                    StringBuffer youhuiSb = new StringBuffer();

                    boolean flag = false;

                    boolean hotelBaseFilterFlag = false;

                    for (SubItemBean bean : selectedData) {

                        int localDataCode = bean.getLocalDataCode();

                        if (localDataCode == 1) {//评分
                            //设置评分查询参数
                            productListParam.setMinVote(bean.getFilterId());
                            flag = true;

                        } else if (localDataCode == 2) {//优惠促销

                            youhuiSb.append(bean.getFilterId()).append(",");

                        } else {

                            hotelBaseFilterFlag = true;

                            tempList.add(bean);
                        }

                    }

                    if (!flag) {

                        productListParam.setMinVote(null);
                    }

                    if (!hotelBaseFilterFlag) {

                        productListParam.setHotelBaseFilterList(null);
                    }

                    String disTypesStr = youhuiSb.toString();

                    if (disTypesStr.length() > 0) {
                        //设置筛选查询参数
                        productListParam.setDisTypes(disTypesStr.substring(0, disTypesStr.length() - 1));
                    } else {

                        productListParam.setDisTypes(null);
                    }

                    mRecyclerView.showSwipeRefresh();

                    getData(true);

                } else {

                    oneDefindTabView.setSelectedTabStatus(false);

                    HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                    productListParam.setHotelBaseFilterList(null);

                    productListParam.setDisTypes(null);

                    productListParam.setMinVote(null);

                    mRecyclerView.showSwipeRefresh();

                    getData(true);
                }


            } else if (resultCode == 1003 && data != null) {//位置区域

                if (data.hasExtra("transmitData")) {

                    Object transmitData = data.getSerializableExtra("transmitData");

                    HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                    if (transmitData instanceof SubItemBean) {

                        SubItemBean bean = (SubItemBean) transmitData;

                        List<SubItemBean> list = productListParam.getHotelPositionList();

                        if (list == null) {

                            list = new ArrayList<SubItemBean>();
                        } else {
                            list.clear();
                        }

                        list.add(bean);

                        productListParam.setHotelPositionList(list);

                        DefindTabView oneDefindTabView = defindTabViewList.get(2);

                        int size = list.size();

                        if (size > 0) {

                            oneDefindTabView.setSelectedTabStatus(true);

                        } else {

                            oneDefindTabView.setSelectedTabStatus(false);
                        }


                    } else if (transmitData instanceof SubItemBean.ChildSubItemBean) {

                        SubItemBean.ChildSubItemBean childSubItemBean = (SubItemBean.ChildSubItemBean) transmitData;

                        SubItemBean bean = new SubItemBean();

                        bean.setFilterId(childSubItemBean.getFilterId());

                        bean.setFilterName(childSubItemBean.getFilterName());

                        bean.setChildTypeCode(childSubItemBean.getChildTypeCode());

                        bean.setFilterLat(childSubItemBean.getFilterLat());

                        bean.setFilterLng(childSubItemBean.getFilterLng());

                        List<SubItemBean> list = productListParam.getHotelPositionList();

                        if (list == null) {

                            list = new ArrayList<SubItemBean>();
                        } else {
                            list.clear();
                        }

                        list.add(bean);

                        productListParam.setHotelPositionList(list);

                        DefindTabView oneDefindTabView = defindTabViewList.get(2);

                        int size = list.size();

                        if (size > 0) {

                            oneDefindTabView.setSelectedTabStatus(true);

                        } else {

                            oneDefindTabView.setSelectedTabStatus(false);
                        }


                    }

                    mRecyclerView.showSwipeRefresh();

                    getData(true);
                }

            } else if (resultCode == 5001) {//选择城市

                reSetCityInfo();

                HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                productListParam.setSearchAddrLat(positionLatitude);

                productListParam.setSearchAddrLng(positionLongitude);

                productListParam.setSearchAddrType(0);

                productListParam.setAddrName(cityName);

                tvCityName.setText(cityName);

                mRecyclerView.showSwipeRefresh();

                getData(true);

            } else if (resultCode == 1040) {//选择搜索页面的查询项

                if (data != null) {

                    SubItemBean subItemBean = (SubItemBean) data.getSerializableExtra("selectoSubItemBean");

                    if (subItemBean != null) {

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
                getData(true);
            } else if (resultCode == 1041) {//搜索关键字

                if (data != null) {

                    SearchReusultBean searchReusultBean = (SearchReusultBean) data.getSerializableExtra("selectoSubItemBean");

                    if (searchReusultBean != null) {

                        List<SearchReusultBean> searchReusultBeanList = new ArrayList<>();

                        searchReusultBeanList.add(searchReusultBean);

                        HotelLogicManager.getInstance().getHotelProductListParam().setSearchList(searchReusultBeanList);

                    } else {

                        HotelLogicManager.getInstance().getHotelProductListParam().setSearchList(null);
                    }

                } else {

                    HotelLogicManager.getInstance().getHotelProductListParam().setSearchList(null);
                }

                getData(true);
            }

        }
    }


    @Override
    public void callSuccessViewLogic(Object o, int type) {

        if (type == HotelProductListPresenter.HOTELPRODUCTLIST_CODE) {

            List<HotelProductObjectBean> list = JSONArray.parseArray(o + "", HotelProductObjectBean.class);

            if (page == 0) {
                mAdapter.clear();
                mAdapter.addAll(list);
                mRecyclerView.dismissSwipeRefresh();
                mRecyclerView.getRecyclerView().scrollToPosition(0);

            } else {

                mAdapter.addAll(list);

            }
            int size = list.size();

            if (size < AppConstant.MAX_PAGE_NUM) {

                mRecyclerView.showNoMore();
            }
        } else if (type == GetCouponPersenter.QUERYCOOPERATIVEBANK_CODE) {


        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        if (type == HotelProductListPresenter.HOTELPRODUCTLIST_CODE) {

            if (page == 0) {
                mRecyclerView.dismissSwipeRefresh();

                //错误信息
                if (HttpConstant.chechNoInfo(o + "")) {
                    mAdapter.clear();
                }
            }


        } else if (type == GetCouponPersenter.QUERYCOOPERATIVEBANK_CODE) {


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理用户设置筛选数据
        HotelLogicManager.getInstance().clearUseHandlerData();
    }
}
