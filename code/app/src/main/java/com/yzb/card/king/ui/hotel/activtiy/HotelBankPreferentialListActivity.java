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
import com.yzb.card.king.bean.BankActivityInfoBean;
import com.yzb.card.king.bean.MonthBean;
import com.yzb.card.king.bean.hotel.BankActivityParam;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.appwidget.DefineTopView;
import com.yzb.card.king.ui.appwidget.popup.ActivityTimeLimitPopup;
import com.yzb.card.king.ui.appwidget.popup.BankSelectPopup;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.appwidget.popup.ChannelTypePopup;
import com.yzb.card.king.ui.appwidget.popup.PreferentialTypePopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.hotel.adapter.ProductBankPrivilegeAdapter;
import com.yzb.card.king.ui.hotel.adapter.ProductCardLifeStagesAdapter;
import com.yzb.card.king.ui.hotel.persenter.HotelBankActivityPersenter;
import com.yzb.card.king.ui.hotel.view.BankActivityView;
import com.yzb.card.king.ui.other.activity.CivilInternationCityActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;

/**
 * 银行优惠
 * Created by 玉兵 on 2017/7/17.
 */
@ContentView(R.layout.activity_hotel_preferential_list)
public class HotelBankPreferentialListActivity extends BaseActivity  implements View.OnClickListener,BankActivityView {

    @ViewInject(R.id.llTopFuction)
    private LinearLayout llTopFuction;

    @ViewInject(R.id.llTop)
    private LinearLayout llTop;

    @ViewInject(R.id.recycler_view)
    private RefreshRecyclerView mRecyclerView;

    @ViewInject(R.id.tvCityName)
    private TextView tvCityName;

    private int page = 0;

    private ProductBankPrivilegeAdapter mAdapter;

    private Handler mHandler;

    private List<DefineTopView> defineTopViewList;

    private ChannelTypePopup channelTypePopup;

    private int selectedIndex = 0;

    String temp = "酒店";

    private BankSelectPopup bankSelectPopup;

    private int selectedBankIndex = 0;

    private PreferentialTypePopup preferentialTypePopup;

    private int selectedPreferentialTypeIndex = -1;

    private ActivityTimeLimitPopup activityTimeLimitPopup;

    private int selectedActivityTimeLimitIndex = 0;

    private HotelBankActivityPersenter hotelBankAcitivityPresenter;

    private int effMonth = 0;

    private int actType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        hotelBankAcitivityPresenter = new HotelBankActivityPersenter(this);
        initTitleView();
        setTitleNmae(R.string.tab_bank_preferential);
        initTopFuction();
        initContent();
    }
    private void initTitleView()
    {

        findViewById(R.id.llSelectedAddress).setOnClickListener(this);

        tvCityName.setText(selectedCity.getCityName());
    }


    private void initTopFuction()
    {

        defineTopViewList = new ArrayList<DefineTopView>();

        String[] nameArray = this.getResources().getStringArray(R.array.bank_preferential_name_array);

        for (int i = 0; i < nameArray.length; i++) {

            DefineTopView defindTabView = new DefineTopView(this, onClickAction);

            defindTabView.setTabName(nameArray[i]);

            defindTabView.addTabToLL(llTopFuction, i);

            defineTopViewList.add(defindTabView);
        }

    }


    private DefineTopView.OnClickAction onClickAction = new DefineTopView.OnClickAction() {
        @Override
        public void onTabClickItem(int index, TextView textView, boolean selectedStatus)
        {

            if (index == 1) {

                if (channelTypePopup == null) {

                    channelTypePopup = new ChannelTypePopup(HotelBankPreferentialListActivity.this, -1);

                    channelTypePopup.setSelectIndex(selectedIndex);

                    channelTypePopup.setCallBack(callBack);
                }

                channelTypePopup.showPP(llTop);
            }else  if(index == 0){//银行


                if(bankSelectPopup == null){

                    bankSelectPopup = new BankSelectPopup(HotelBankPreferentialListActivity.this,-1,false);

                    bankSelectPopup.setSelectIndex(selectedBankIndex);

                    bankSelectPopup.setCallBack(callBankBack);
                }

                bankSelectPopup.showPP(llTop);


            } else if(index ==2){//优惠类型

                if(preferentialTypePopup == null){

                    preferentialTypePopup = new PreferentialTypePopup(HotelBankPreferentialListActivity.this,-1);

                    preferentialTypePopup.setSelectIndex(selectedPreferentialTypeIndex);

                    preferentialTypePopup.setCallBack(callPreferentialTypeCallBack);
                }

                preferentialTypePopup.showPP(llTop);

            }else if(index ==3){//活动期限

                if (activityTimeLimitPopup == null) {

                    activityTimeLimitPopup = new ActivityTimeLimitPopup(HotelBankPreferentialListActivity.this, -1);

                   activityTimeLimitPopup.setSelectIndex(selectedActivityTimeLimitIndex);

                    activityTimeLimitPopup.setCallBack(callActivityTimeLimitCallBack);
                }

                activityTimeLimitPopup.showPP(llTop);

            }
        }
    };

    private ActivityTimeLimitPopup.BottomDataListCallBack callActivityTimeLimitCallBack = new ActivityTimeLimitPopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(MonthBean selectedBean, int selectIndex)
        {
            DefineTopView defineTopView = defineTopViewList.get(3);

            if("不限".equals(selectedBean)){
                defineTopView.setTabName("活动期限");
            }else{
                defineTopView.setTabName(selectedBean.getMonthStr());
            }

            effMonth = selectedBean.getMonthNumber();

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };

    private  PreferentialTypePopup.BottomDataListCallBack callPreferentialTypeCallBack = new PreferentialTypePopup.BottomDataListCallBack(){
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {
            DefineTopView defineTopView = defineTopViewList.get(2);

            if( name ==null){

                defineTopView.setTabCheckStatus(false);

                return;

            }

            defineTopView.setTabName(name);

            actType = nameValue;

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };


    private  BankSelectPopup.BottomDataListCallBack callBankBack = new BankSelectPopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {
            DefineTopView defineTopView = defineTopViewList.get(0);

            if(name == null){

                defineTopView.setTabCheckStatus(false);

                return;
            }

            defineTopView.setTabName(name);

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };

    private ChannelTypePopup.BottomDataListCallBack callBack = new ChannelTypePopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            DefineTopView defineTopView = defineTopViewList.get(1);

            if(name == null){
                return;
            }

            defineTopView.setTabName(name);


            temp = name;

            if (selectIndex == 0) {//酒店


            } else if (selectIndex == 1) {//机票


            } else if (selectIndex == 2) {//旅游

            }
            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };

    private void initContent()
    {
        mHandler = new Handler();
        mAdapter = new ProductBankPrivilegeAdapter(this);
//        //添加Header
//        final TextView textView = new TextView(this);
//        textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(this, 48)));
//        textView.setTextSize(16);
//        textView.setGravity(Gravity.CENTER);
//        textView.setText("重庆邮电大学");
//        mAdapter.setHeader(textView);
//        //添加footer
//        final TextView footer = new TextView(this);
//        footer.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(this, 48)));
//        footer.setTextSize(16);
//        footer.setGravity(Gravity.CENTER);
//        footer.setText("-- Footer --");
//        mAdapter.setFooter(footer);

        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
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

    }


    public void getData(final boolean isRefresh)
    {
        if (isRefresh) {
            page = 0;

        } else {
            page = mAdapter.getItemCount();

        }

        hotelBankAcitivityPresenter.sendBankPreActivityRequest();
    }


    @Override
    public void onClick(View v)
    {

        switch (v.getId()){

            case R.id.llSelectedAddress:

                Intent intentCity = new Intent(this, CivilInternationCityActivity.class);

                startActivityForResult(intentCity,1000);

                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1000){

            if(resultCode == 5001){//选择城市

                reSetCityInfo();

                tvCityName.setText(cityName);

                mRecyclerView.showSwipeRefresh();

                getData(true);

            }

        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        List<BankActivityInfoBean> list = JSONArray.parseArray(o+"",BankActivityInfoBean.class);

        if (page == 0) {

            mAdapter.clear();

            mAdapter.addAll(list);

            mRecyclerView.dismissSwipeRefresh();

            mRecyclerView.getRecyclerView().scrollToPosition(0);

        } else {
            mAdapter.addAll(list);


        }

        int size = list.size();

        if ( size< AppConstant.MAX_PAGE_NUM) {

            mRecyclerView.showNoMore();
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if(page == 0){

            mRecyclerView.dismissSwipeRefresh();

            //错误信息
            if (HttpConstant.chechNoInfo(o + "")) {
                mAdapter.clear();
            }
        }
    }

    @Override
    public BankActivityParam getQueryParam()
    {
        BankActivityParam param = new BankActivityParam();

        param.setCityId(Integer.parseInt(cityId));

        param.setIndustryId(GlobalVariable.industryId);

        param.setEffMonth(effMonth);

        param.setActType(actType);

        param.setPageStart(page);

        return param;
    }
}
