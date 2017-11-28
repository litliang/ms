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
import com.yzb.card.king.bean.GiftComboBean;
import com.yzb.card.king.bean.common.PaymethodAndBankPreStageBean;
import com.yzb.card.king.bean.hotel.BankActivityParam;
import com.yzb.card.king.bean.hotel.HotelProductObjectBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.appwidget.DefineTopView;
import com.yzb.card.king.ui.appwidget.popup.BankSelectPopup;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.appwidget.popup.ChannelTypePopup;
import com.yzb.card.king.ui.appwidget.popup.GiftComboPopup;
import com.yzb.card.king.ui.appwidget.popup.LifeStagePopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.hotel.adapter.ProductCardLifeStagesAdapter;
import com.yzb.card.king.ui.hotel.adapter.ProductGiftComboAdatper;
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
 * 生活分期
 * Created by 玉兵 on 2017/7/17.
 */
@ContentView(R.layout.activity_hotel_preferential_list)
public class HotelLifeStageListActivity extends BaseActivity implements View.OnClickListener,BankActivityView {

    @ViewInject(R.id.llTopFuction)
    private LinearLayout llTopFuction;

    @ViewInject(R.id.llTop)
    private LinearLayout llTop;

    @ViewInject(R.id.recycler_view)
    private RefreshRecyclerView mRecyclerView;

    @ViewInject(R.id.tvCityName)
    private TextView tvCityName;

    private int page = 0;

    private ProductCardLifeStagesAdapter mAdapter;

    private Handler mHandler;

    private List<DefineTopView> defineTopViewList;

    private ChannelTypePopup channelTypePopup;

    private int selectedIndex = 0;

    String temp = "酒店";

    private BankSelectPopup bankSelectPopup;

    private int selectedBankIndex = 0;

    private LifeStagePopup lifeStagePopup;

    private int selectedLifeStageIndex = -1;

    private HotelBankActivityPersenter hotelBankAcitivityPresenter;

    private String currentBankId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hotelBankAcitivityPresenter = new HotelBankActivityPersenter(this);

        initTitleView();
        setTitleNmae(R.string.tab_life_stage);
        initTopFuction();

        initContent();
    }

    private void initTitleView()
    {

        findViewById(R.id.llSelectedAddress).setOnClickListener(this);

        tvCityName.setText(selectedCity.getCityName());
    }

    private void initTopFuction() {

        defineTopViewList = new ArrayList<DefineTopView>();

        String[] nameArray = this.getResources().getStringArray(R.array.life_stage_name_array);

        for (int i = 0; i < nameArray.length; i++) {

            DefineTopView defindTabView = new DefineTopView(this,onClickAction);

            defindTabView.setTabName(nameArray[i]);

            defindTabView.addTabToLL(llTopFuction, i);

            defineTopViewList.add(defindTabView);

        }

    }


    private DefineTopView.OnClickAction onClickAction = new DefineTopView.OnClickAction() {
        @Override
        public void onTabClickItem(int index, TextView textView, boolean selectedStatus) {

            if(index == 1){//行业

                if(channelTypePopup == null){

                    channelTypePopup = new ChannelTypePopup(HotelLifeStageListActivity.this,-1);

                    channelTypePopup.setSelectIndex(selectedIndex);

                    channelTypePopup.setCallBack(callBack);
                }

                channelTypePopup.showPP(llTop);
            }else  if(index == 0){//银行


                if(bankSelectPopup == null){

                    bankSelectPopup = new BankSelectPopup(HotelLifeStageListActivity.this,-1,false);

                    bankSelectPopup.setCallBack(callBankBack);

                    bankSelectPopup.setSelectIndex(selectedBankIndex);
                }

                bankSelectPopup.showPP(llTop);


            }else if(index == 2){//分期

                if(lifeStagePopup == null){

                    lifeStagePopup = new LifeStagePopup(HotelLifeStageListActivity.this,-1);

                    lifeStagePopup.setCallBack(callLifeStageBack);

                    lifeStagePopup.setSelectIndex(selectedLifeStageIndex);
                }

                lifeStagePopup.showPP(llTop);
            }

        }
    };

    private  BankSelectPopup.BottomDataListCallBack callBankBack = new BankSelectPopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {
            DefineTopView defineTopView = defineTopViewList.get(0);

            if( name == null){

                defineTopView.setTabCheckStatus(false);

                return;
            }

            defineTopView.setTabName(name);

            if(selectIndex == 0){//全部银行

                currentBankId = bankSelectPopup.getAllBankIds();

            }else if(selectIndex == 1){//我的银行

                currentBankId = bankSelectPopup.getMyBankIds();

            }else if(selectIndex > 1){//其它银行

                List<PaymethodAndBankPreStageBean> bankList =  bankSelectPopup.getTotalList();

                if(bankList != null){

                    PaymethodAndBankPreStageBean bean = bankList.get(selectIndex-2);

                    currentBankId = bean.getBankId()+"";

                }

            }

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };


    private ChannelTypePopup.BottomDataListCallBack callBack = new ChannelTypePopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            DefineTopView defineTopView = defineTopViewList.get(1);

            if(name==null){
                return;
            }

            defineTopView.setTabName(name);

            temp = name;

            if (selectIndex == 0) {//酒店


            } else if (selectIndex == 1) {//机票


            }else if (selectIndex == 3){//旅游

            }

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };

    private int stage = - 1;

    private LifeStagePopup.BottomDataListCallBack callLifeStageBack = new LifeStagePopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            DefineTopView defineTopView = defineTopViewList.get(2);

            if( name == null && nameValue == -1){//清理
                defineTopView.setTabName("分期数");
                selectedLifeStageIndex = -1;

                defineTopView.setTabCheckStatus(false);

                stage = -1;
            }else{
                defineTopView.setTabName(name);
                defineTopView.setTabCheckStatus(true);

                stage = nameValue;

            }

            mRecyclerView.showSwipeRefresh();

            getData(true);
        }
    };

    private void initContent()
    {
        mHandler = new Handler();
        mAdapter = new ProductCardLifeStagesAdapter(this);
        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
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

        if (isRefresh) {
            page = 0;

        } else {
            page = mAdapter.getItemCount();

        }

        hotelBankAcitivityPresenter.sendBankLifeStageActivityRequest();

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

            if(resultCode == CivilInternationCityActivity.CIVIINTERNATION_RESULTCODE){//选择城市

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
        }
    }

    @Override
    public BankActivityParam getQueryParam()
    {
        BankActivityParam param = new BankActivityParam();

        param.setCityId(Integer.parseInt(cityId));

        param.setIndustryId(  GlobalVariable.industryId);

        param.setBankIds(currentBankId);

        param.setStage(stage);

        param.setPageStart(page);

        return param;
    }
}