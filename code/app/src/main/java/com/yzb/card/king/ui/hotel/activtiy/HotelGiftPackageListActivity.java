package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.SearchGiftResultBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.GiftComboBean;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.hotel.RoomInfoBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.HttpUtil;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.appwidget.DefineTopView;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.appwidget.popup.ChannelTypePopup;
import com.yzb.card.king.ui.appwidget.popup.GiftComboPopup;
import com.yzb.card.king.ui.appwidget.popup.HotelBrandPopup;
import com.yzb.card.king.ui.appwidget.popup.HotelFileFlashSalePopup;
import com.yzb.card.king.ui.appwidget.popup.HotelFiltratePopup;
import com.yzb.card.king.ui.appwidget.popup.HotelSortTypePopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.holder.HotelCardEquityHolder;
import com.yzb.card.king.ui.hotel.holder.HotelFlashSaleHolder;
import com.yzb.card.king.ui.hotel.persenter.HotelProductListPresenter;
import com.yzb.card.king.ui.other.activity.CivilInternationCityActivity;
import com.yzb.card.king.util.LogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;
import cn.lemon.view.adapter.MultiTypeAdapter;

/**
 * 礼品卡套餐
 * Created by 玉兵 on 2017/7/17.
 */
@ContentView(R.layout.activity_hotel_gift_p)
public class HotelGiftPackageListActivity extends BaseActivity implements BaseViewLayerInterface, View.OnClickListener {

    @ViewInject(R.id.llTopFuction)
    private LinearLayout llTopFuction;

    @ViewInject(R.id.llTop)
    private LinearLayout llTop;

    @ViewInject(R.id.recycler_view)
    private RefreshRecyclerView mRecyclerView;

    @ViewInject(R.id.llSearch)
    private EditText llSearch;

    @ViewInject(R.id.tvCityName)
    private TextView tvCityName;

    private int page = 1;

    private MultiTypeAdapter mAdapter;

    private Handler mHandler;

    private List<DefineTopView> defineTopViewList;

    private GiftComboPopup giftComboPopup;

    private int selectedIndex = 0;

    private ChannelTypePopup channelTypePopup;

    private int selectedTypeIndex = 0;

    private HotelSortTypePopup hotelSortTypePopup;

    private int selectedGiftComboIndex = 0;

    private HotelBrandPopup hotelBrandPopup;

    private HotelFiltratePopup hotelFiltratePopup;

    private HotelFileFlashSalePopup hotelFileFlashSalePopup;

    private HotelProductListPresenter presenterl;

    private boolean oneTopClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getIntent().hasExtra("oneTopClick")){

            oneTopClick = getIntent().getBooleanExtra("oneTopClick",true);

        }

        HotelProductListParam productListParam = HotelLogicManager.getInstance().getFlashSaleHotelParam();

        HotelProductListParam productListParamCard = HotelLogicManager.getInstance().getCardRightHotelParam();

        productListParam.setIndustryId(Integer.parseInt(AppConstant.hotel_id));

        productListParamCard.setIndustryId(Integer.parseInt(AppConstant.hotel_id));

        industryId = Integer.parseInt(AppConstant.hotel_id);

        presenterl = new HotelProductListPresenter(this);

        initTitleView();

        initTopFuction();

        initContent();
    }

    private void initTitleView()
    {
        findViewById(R.id.llSelectedAddress).setOnClickListener(this);

        tvCityName.setText(selectedCity.getCityName());

        llSearch.setFocusable(false);

        llSearch.setHint("输入门店名称");

        llSearch.setOnClickListener(this);
    }

    private void initTopFuction()
    {

        defineTopViewList = new ArrayList<DefineTopView>();

        String[] nameArray = this.getResources().getStringArray(R.array.gife_pp_name_array);

        for (int i = 0; i < nameArray.length; i++) {

            DefineTopView defindTabView = new DefineTopView(this, onClickAction);

            if(i == 0 && !oneTopClick){//设置选中

                defindTabView.setTabCheckStatus(true);

            }

            defindTabView.setTabName(nameArray[i]);

            defindTabView.addTabToLL(llTopFuction, i);

            defineTopViewList.add(defindTabView);
        }

    }

    private DefineTopView.OnClickAction onClickAction = new DefineTopView.OnClickAction() {
        @Override
        public void onTabClickItem(int index, TextView textView, boolean selectedStatus)
        {

            if (index == 0 && oneTopClick) {//礼品套餐

                if (giftComboPopup == null) {

                    giftComboPopup = new GiftComboPopup(HotelGiftPackageListActivity.this, -1);

                    String[] nameArray = getResources().getStringArray(R.array.gift_combo_name_array);

                    giftComboPopup.setLogicData(nameArray, null);

                    giftComboPopup.setSelectIndex(selectedIndex);

                    giftComboPopup.setCallBack(callBack);
                }

                giftComboPopup.showPP(llTop);

            } else if (index == 1) {//频道类型

                if (channelTypePopup == null) {

                    channelTypePopup = new ChannelTypePopup(HotelGiftPackageListActivity.this, -1);

                    channelTypePopup.setSelectIndex(selectedTypeIndex);

                    channelTypePopup.setCallBack(callBackChannelTypePopup);
                }

                channelTypePopup.showPP(llTop);

            } else if (index == 4) {

                if (hotelSortTypePopup == null) {

                    hotelSortTypePopup = new HotelSortTypePopup(HotelGiftPackageListActivity.this, -1);

                    hotelSortTypePopup.setSelectIndex(selectedGiftComboIndex);

                    String[] nameArray = getResources().getStringArray(R.array.gift_combo_sort_name_array);

                    int[] valueArray = getResources().getIntArray(R.array.gift_combo_sort_value_array);

                    hotelSortTypePopup.setLogicData(nameArray, valueArray);

                    hotelSortTypePopup.setCallBack(callBackGiftCombo);
                }

                hotelSortTypePopup.showPP(llTop);


            } else if (index == 2) {//品牌

                if (hotelBrandPopup == null) {

                    hotelBrandPopup = new HotelBrandPopup(HotelGiftPackageListActivity.this, 313, "2");

                    hotelBrandPopup.setCallBack(entryCallBack);
                }
                hotelBrandPopup.showPP(llTop);
            } else if (index == 3) {//筛选

                if (productType) {

                    if (hotelFileFlashSalePopup == null) {

                        hotelFileFlashSalePopup = new HotelFileFlashSalePopup(HotelGiftPackageListActivity.this, -1);

                        hotelFileFlashSalePopup.setCallBack(flashSaleCallBack);
                    }

                    hotelFileFlashSalePopup.showPP(llTop);
                } else {

                    if (hotelFiltratePopup == null) {

                        hotelFiltratePopup = new HotelFiltratePopup(HotelGiftPackageListActivity.this, -1);

                        hotelFiltratePopup.setCallBack(hotelFiltrateCallBack);
                    }
                    hotelFiltratePopup.showPP(llTop);
                }


            }

        }
    };

    private HotelBrandPopup.ViewDataCallBack entryCallBack = new HotelBrandPopup.ViewDataCallBack() {
        @Override
        public void onConfirm(List<SubItemBean> selectedBean)
        {
            DefineTopView defineTopView = defineTopViewList.get(2);

            int size = selectedBean.size();
            if (size == 0) {
                defineTopView.setTabCheckStatus(false);
            } else {

                defineTopView.setTabCheckStatus(true);
            }
            HotelProductListParam productListParam;

            if (productType) {

                productListParam = HotelLogicManager.getInstance().getFlashSaleHotelParam();
            } else {

                productListParam = HotelLogicManager.getInstance().getCardRightHotelParam();
            }
            if (size > 0) {
                productListParam.setFilterList(selectedBean);
            } else {
                productListParam.setFilterList(null);
            }

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };

    private HotelFileFlashSalePopup.ViewDataCallBack flashSaleCallBack = new HotelFileFlashSalePopup.ViewDataCallBack() {
        @Override
        public void onConfirm(List<SubItemBean> selectedBean, int minValue, int maxValue)
        {
            DefineTopView defineTopView = defineTopViewList.get(3);

            int size1 = selectedBean.size();

            if (size1 == 0 && minValue == 100 && maxValue == 1001) {

                defineTopView.setTabCheckStatus(false);

            } else {
                defineTopView.setTabCheckStatus(true);
            }

            HotelProductListParam productListParam = HotelLogicManager.getInstance().getFlashSaleHotelParam();

            productListParam.setStoreUseType(null);

            productListParam.setEffMonth(null);

            if (size1 > 0) {

                for (SubItemBean temp : selectedBean) {

                    int localData = temp.getLocalDataCode();

                    if (localData == 3) {//有效期

                        productListParam.setEffMonth(temp.getFilterId());

                    } else if (localData == 4) {//适用门店

                        productListParam.setStoreUseType(temp.getFilterId());

                    }

                }
            }

            if (maxValue == 1001) {

                productListParam.setBgnPrice(minValue + "");

                productListParam.setEndPrice(Integer.MAX_VALUE + "");

            } else {

                productListParam.setBgnPrice(minValue + "");

                productListParam.setEndPrice(maxValue + "");

            }

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };


    private HotelFiltratePopup.ViewDataCallBack hotelFiltrateCallBack = new HotelFiltratePopup.ViewDataCallBack() {
        @Override
        public void onConfirm(List<SubItemBean> selectedBean, int minValue, int maxValue)
        {

            DefineTopView defineTopView = defineTopViewList.get(3);

            int size1 = selectedBean.size();

            if (size1 == 0 && minValue == 100 && maxValue == 1001) {

                defineTopView.setTabCheckStatus(false);

            } else {
                defineTopView.setTabCheckStatus(true);
            }

            HotelProductListParam productListParam = HotelLogicManager.getInstance().getCardRightHotelParam();

            productListParam.setStoreUseType(null);
            productListParam.setEffMonth(null);
            if (size1 > 0) {

                for (SubItemBean temp : selectedBean) {

                    int localData = temp.getLocalDataCode();

                    if (localData == 3) {//有效期

                        productListParam.setEffMonth(temp.getFilterId());

                    } else if (localData == 4) {//适用门店

                        productListParam.setStoreUseType(temp.getFilterId());

                    }

                }
            }

            if (maxValue == 1001) {

                productListParam.setBgnPrice(minValue + "");

                productListParam.setEndPrice(Integer.MAX_VALUE + "");

            } else {

                productListParam.setBgnPrice(minValue + "");

                productListParam.setEndPrice(maxValue + "");

            }

            mRecyclerView.showSwipeRefresh();
            getData(true);
        }
    };


    private boolean productType = true;//限时抢购

    private int giftsType = 8;

    private GiftComboPopup.BottomDataListCallBack callBack = new GiftComboPopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            DefineTopView defineTopView = defineTopViewList.get(0);

            if (name == null) {

                defineTopView.setTabCheckStatus(false);
                return;
            }


            defineTopView.setTabName(name);

            if (selectIndex == 0) {//限时抢购

                productType = true;

                giftsType = 8;

            } else if (selectIndex == 1) {//卡权益

                productType = false;

                giftsType = 7;
            }


            HotelProductListParam productListParam;

            if (productType) {

                productListParam = HotelLogicManager.getInstance().getFlashSaleHotelParam();

            } else {

                productListParam = HotelLogicManager.getInstance().getCardRightHotelParam();
            }

            boolean isShow = false;

            if (productListParam.getEffMonth() != null || productListParam.getStoreUseType() != null || !String.valueOf(Integer.MAX_VALUE).equals(productListParam.getEndPrice()) || !"100".equals(productListParam.getBgnPrice())) {

                isShow = true;
            }

            DefineTopView defineTopViewThree = defineTopViewList.get(3);

            if (isShow) {

                defineTopViewThree.setTabCheckStatus(true);

            } else {
                defineTopViewThree.setTabCheckStatus(false);
            }


            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };

    private int  industryId ;

    private ChannelTypePopup.BottomDataListCallBack callBackChannelTypePopup = new ChannelTypePopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            DefineTopView defineTopView = defineTopViewList.get(1);

            if (name == null) {

                defineTopView.setTabCheckStatus(false);

                return;
            }

            defineTopView.setTabName(name);

            HotelProductListParam productListParam = null;

            if (productType) {

                productListParam = HotelLogicManager.getInstance().getFlashSaleHotelParam();
            } else {

                productListParam = HotelLogicManager.getInstance().getCardRightHotelParam();
            }

            productListParam.setIndustryId(nameValue);

            industryId = nameValue;

            mRecyclerView.showSwipeRefresh();


            getData(true);
        }
    };

    private HotelSortTypePopup.BottomDataListCallBack callBackGiftCombo = new HotelSortTypePopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            DefineTopView defineTopView = defineTopViewList.get(4);

            if (name == null) {

                defineTopView.setTabCheckStatus(false);

                return;
            }

            defineTopView.setTabName(name);

            HotelProductListParam productListParam;

            if (productType) {

                productListParam = HotelLogicManager.getInstance().getFlashSaleHotelParam();
            } else {

                productListParam = HotelLogicManager.getInstance().getCardRightHotelParam();
            }

            productListParam.setSort(nameValue);

            mRecyclerView.showSwipeRefresh();


            getData(true);
        }
    };


    private void initContent()
    {
        mHandler = new Handler();
        mAdapter = new MultiTypeAdapter(this);

        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction()
            {
                getData(true);
            }
        });

        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction()
            {
                getData(false);
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run()
            {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });

        tvCityName.setText(cityName);

    }


    public void getData(final boolean isRefresh)
    {

        if (isRefresh) {//刷新

            page = 0;

        } else {

            page = mAdapter.getItemCount();
        }

        HotelProductListParam productListParam = null;

        if (productType) {

            productListParam = HotelLogicManager.getInstance().getFlashSaleHotelParam();
        } else {

            productListParam = HotelLogicManager.getInstance().getCardRightHotelParam();
        }

        productListParam.setAddrId(Integer.parseInt(cityId));

        productListParam.setPageStart(page);

        if (productType) {
            presenterl.sendFlashSaleListRequest(productListParam);
        } else {
            presenterl.sendQueryGiftListRequest(productListParam);
        }

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        HotelLogicManager.getInstance().clearGiftCombonData();
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        List<GiftComboBean> list = JSONArray.parseArray(o + "", GiftComboBean.class);
        if (page == 0) {
            mAdapter.clear();
            mRecyclerView.dismissSwipeRefresh();
            if (productType) {
                mAdapter.addAll(HotelFlashSaleHolder.class, list);
            } else {
                mAdapter.addAll(HotelCardEquityHolder.class, list);
            }
            mRecyclerView.getRecyclerView().scrollToPosition(0);

        } else {

            if (productType) {
                mAdapter.addAll(HotelFlashSaleHolder.class, list);
            } else {
                mAdapter.addAll(HotelCardEquityHolder.class, list);
            }

        }

        int size = list.size();
        if (size < AppConstant.MAX_PAGE_NUM) {

            mRecyclerView.showNoMore();
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if (page == 0) {
            mRecyclerView.dismissSwipeRefresh();

            if (HttpConstant.chechNoInfo(o + "")) {
                mAdapter.clear();
            }
        }
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId()) {

            case R.id.llSelectedAddress:

                Intent intentCity = new Intent(HotelGiftPackageListActivity.this, CivilInternationCityActivity.class);

                startActivityForResult(intentCity, 1000);

                break;

            case R.id.llSearch:
                Intent intentSearch = new Intent(HotelGiftPackageListActivity.this, HotelGiftSearchActivity.class);

                intentSearch.putExtra("giftsType",giftsType);

                intentSearch.putExtra("industryId",industryId);

                startActivityForResult(intentSearch, 1000);
                break;

            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {

            if (resultCode == 5001) {//选择城市

                reSetCityInfo();

                tvCityName.setText(cityName);

                mRecyclerView.showSwipeRefresh();

                getData(true);

            }else if(resultCode == 1041){

                String  searchGiftResultBean =  data.getStringExtra("storeName");

                HotelProductListParam productListParam;

                if (productType) {

                    productListParam = HotelLogicManager.getInstance().getFlashSaleHotelParam();

                } else {

                    productListParam = HotelLogicManager.getInstance().getCardRightHotelParam();
                }

                productListParam.setStoreName(searchGiftResultBean);

                mRecyclerView.showSwipeRefresh();

                getData(true);
            }
        }
    }
}
