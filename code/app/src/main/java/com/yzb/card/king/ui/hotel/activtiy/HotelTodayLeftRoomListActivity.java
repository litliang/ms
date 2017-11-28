package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.BedTypeBean;
import com.yzb.card.king.bean.hotel.HotelLevelBean;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.hotel.HotelProductObjectBean;
import com.yzb.card.king.bean.hotel.RoomInfoBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.DefineTopView;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.appwidget.popup.BedTypeSelectedPopup;
import com.yzb.card.king.ui.appwidget.popup.HotelSortTypePopup;
import com.yzb.card.king.ui.appwidget.popup.HotelStarPricePopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.adapter.HotelTodayLeftRoomItemAdapter;
import com.yzb.card.king.ui.hotel.persenter.HotelProductListPresenter;
import com.yzb.card.king.ui.other.activity.CivilInternationCityActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;

/**
 * 今日甩房
 * Created by 玉兵 on 2017/7/17.
 */
@ContentView(R.layout.activity_hotel_preferential_list)
public class HotelTodayLeftRoomListActivity extends BaseActivity implements BaseViewLayerInterface {

    @ViewInject(R.id.llTopFuction)
    private LinearLayout llTopFuction;

    @ViewInject(R.id.llTop)
    private LinearLayout llTop;

    @ViewInject(R.id.recycler_view)
    private RefreshRecyclerView mRecyclerView;

    @ViewInject(R.id.tvCityName)
    private TextView tvCityName;

    @ViewInject(R.id.llSelectedAddress)
    private LinearLayout llSelectedAddress;

    private int page = 1;

    private HotelTodayLeftRoomItemAdapter mAdapter;

    private HotelSortTypePopup todayLeftSortPopup;

    private int selectedIndex = -1;

    private BedTypeSelectedPopup bedTypeSelectedPopup;

    private List<DefineTopView> defineTopViewList;

    private HotelStarPricePopup hotelStarPricePopup;

    private HotelProductListPresenter productListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productListPresenter = new HotelProductListPresenter(this);

        setTitleNmae(R.string.tab_today_left_room);

        initTopFuction();

        initContent();
    }

    private void initTopFuction() {

        defineTopViewList = new ArrayList<DefineTopView>();

        String[] nameArray = this.getResources().getStringArray(R.array.today_left_room_name_array);

        for (int i = 0; i < nameArray.length; i++) {

            DefineTopView defindTabView = new DefineTopView(this, onClickAction);

            defindTabView.setTabName(nameArray[i]);

            defindTabView.addTabToLL(llTopFuction, i);

            defineTopViewList.add(defindTabView);
        }

        tvCityName.setText(cityName);

        llSelectedAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCity = new Intent(HotelTodayLeftRoomListActivity.this, CivilInternationCityActivity.class);

                startActivityForResult(intentCity, 1000);
            }
        });

    }

    private void initContent() {
        mAdapter = new HotelTodayLeftRoomItemAdapter(this);

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

        HotelProductListParam productListParam = HotelLogicManager.getInstance().getTodayLeftRoomHotelParam();

        productListParam.setPageStart(page);

        productListParam.setAddrId(Long.parseLong(cityId));


        productListPresenter.sendTodayLeftRoomListRequest(productListParam);
    }


    private DefineTopView.OnClickAction onClickAction = new DefineTopView.OnClickAction() {
        @Override
        public void onTabClickItem(int index, TextView textView, boolean selectedStatus) {

            if (index == 2) {//排序

                if (todayLeftSortPopup == null) {

                    todayLeftSortPopup = new HotelSortTypePopup(HotelTodayLeftRoomListActivity.this, -1);

                    todayLeftSortPopup.setSelectIndex(selectedIndex);

                    String[] nameArray = getResources().getStringArray(R.array.today_leftroom_sort_name_array);

                    int[] nameValue = getResources().getIntArray(R.array.today_leftroom_sort_value_array);

                    todayLeftSortPopup.setLogicData(nameArray, nameValue);

                    todayLeftSortPopup.setCallBack(callBack);
                }

                todayLeftSortPopup.showPP(llTop);


            } else if (index == 1) {//床型

                if (bedTypeSelectedPopup == null) {

                    bedTypeSelectedPopup = new BedTypeSelectedPopup(HotelTodayLeftRoomListActivity.this, -1);

                    bedTypeSelectedPopup.setCallBack(callBackBedType);

                }

                bedTypeSelectedPopup.showPP(llTop);

            } else if (index == 0) {//星级价格

                if (hotelStarPricePopup == null) {

                    hotelStarPricePopup = new HotelStarPricePopup(HotelTodayLeftRoomListActivity.this, -1, BaseFullPP.ViewPostion.VIEW_TOP);

                    hotelStarPricePopup.setViewDataCallBack(ppViewHotelDataCallBack);

                }

                hotelStarPricePopup.showBottomByViewPP(llTop);

            }


        }
    };

    private HotelStarPricePopup.ViewDataListCallBack ppViewHotelDataCallBack = new HotelStarPricePopup.ViewDataListCallBack() {
        @Override
        public void onConfirm(List<HotelLevelBean> selectedList, int minValue, int maxValue) {

            DefineTopView defineTopView = defineTopViewList.get(0);

            int size1 = selectedList.size();

            if (size1 == 0 && minValue == 0 && maxValue == 1001) {

                defineTopView.setTabCheckStatus(false);

            } else {

                defineTopView.setTabCheckStatus(true);
            }

            HotelProductListParam productListParam = HotelLogicManager.getInstance().getTodayLeftRoomHotelParam();

            int size = selectedList.size();

            if (size > 0) {

                StringBuffer sbLevels = new StringBuffer();

                StringBuffer sbBrandTypes = new StringBuffer();

                for (int i = 0; i < size; i++) {

                    HotelLevelBean bean = selectedList.get(i);

                    int index = bean.getLpIndex();

                    if (index == 1) {//经济型

                        sbBrandTypes.append(bean.getStartValue() + ",");
                        if(size == 1){

                            sbLevels.append("0");
                        }

                    } else if (index == 2) {//二星级

                        sbLevels.append(bean.getStartValue() + ",");

                        if(size == 1){

                            sbBrandTypes.append("0");
                        }

                    } else if (index == 6) {//五星级以上

                        sbLevels.append(bean.getStartValue() + ",");

                        if(size == 1){

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

                productListParam.setLevels(0 + "");

                productListParam.setBrandTypes(0 + "");
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


    private HotelSortTypePopup.BottomDataListCallBack callBack = new HotelSortTypePopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex) {

            DefineTopView defineTopView = defineTopViewList.get(2);

            if (name == null) {

                defineTopView.setTabCheckStatus(false);

                return;
            }

            defineTopView.setTabName(name);

            HotelProductListParam productListParam = HotelLogicManager.getInstance().getTodayLeftRoomHotelParam();

            productListParam.setSort(nameValue);

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };

    private BedTypeSelectedPopup.DataListCallBack callBackBedType = new BedTypeSelectedPopup.DataListCallBack() {
        @Override
        public void onConfirm(List<BedTypeBean> selectoredListData) {

            HotelProductListParam productListParam = HotelLogicManager.getInstance().getTodayLeftRoomHotelParam();

            DefineTopView defineTopView = defineTopViewList.get(1);

            int size1 = selectoredListData.size();

            if (size1 == 0) {

                defineTopView.setTabCheckStatus(false);

                productListParam.setRoomsTypes(null);

            } else {

                defineTopView.setTabCheckStatus(true);

                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < size1; i++) {

                    BedTypeBean bedTypeBean = selectoredListData.get(i);

                    sb.append(bedTypeBean.getBedTypeValue());

                    if (i == size1 - 1) {

                    } else {

                        sb.append(",");
                    }

                }

                productListParam.setRoomsTypes(sb.toString());


            }

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };


    @Override
    public void callSuccessViewLogic(Object o, int type) {

        List<RoomInfoBean> list = JSONArray.parseArray(o + "", RoomInfoBean.class);

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
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        if (page == 0) {
            mRecyclerView.dismissSwipeRefresh();

            //错误信息
            if (HttpConstant.chechNoInfo(o + "")) {
                mAdapter.clear();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {

            if (resultCode == 5001) {//选择城市

                reSetCityInfo();
                tvCityName.setText(cityName);
                mRecyclerView.showSwipeRefresh();
                getData(true);

            }

        }
    }
}
