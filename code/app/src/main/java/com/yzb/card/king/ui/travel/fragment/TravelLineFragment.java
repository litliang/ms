package com.yzb.card.king.ui.travel.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelCategorySBean;
import com.yzb.card.king.bean.travel.TravelLineProductBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.manage.TravelDataManager;
import com.yzb.card.king.ui.travel.adapter.TravelLineListAdapter;
import com.yzb.card.king.ui.travel.model.ITravelList;
import com.yzb.card.king.ui.travel.presenter.TravelListPresenter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 旅游线路碎片
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_travle_line)
public class TravelLineFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseViewLayerInterface {

    private SwipeRefreshLayout swipeRefresh;

    private HeadFootRecyclerView listview;

    private TravelCategorySBean bean;

    private TravelLineListAdapter adapter;

    private int pageStart = 0;

    private TravelListPresenter presenter;

    /**
     * 控制fragment刷新标记
     */
    private boolean ifRefresh = false;

    /**
     * 控制fragment无卡标记
     */
    private boolean ifUserNoCard = false;

    /**
     * 特殊标记符号
     */
    private boolean  specialFlag = false;

    public boolean isIfUserNoCard()
    {
        return ifUserNoCard;
    }

    public void setIfUserNoCard(boolean ifUserNoCard)
    {
        this.ifUserNoCard = ifUserNoCard;
    }

    public boolean isIfRefresh()
    {
        return ifRefresh;
    }

    public void setIfRefresh(boolean ifRefresh)
    {
        specialFlag = true;
        this.ifRefresh = ifRefresh;
    }

    public static TravelLineFragment getInstance(TravelCategorySBean bean)
    {

        TravelLineFragment sf = new TravelLineFragment();

        sf.bean = bean;

        return sf;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(v, savedInstanceState);

        presenter = new TravelListPresenter(this);
        //
        swipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh);

        listview = (HeadFootRecyclerView) v.findViewById(R.id.listview);

        SwipeRefreshSettings.setAttrbutes(getContext(), swipeRefresh);

        swipeRefresh.setOnRefreshListener(this);

        listview.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TravelLineListAdapter(this);

        adapter.setDataHandler(dataHandler);

        listview.setAdapter(adapter);

        listview.setIsEnale(true);
        listview.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener()
            {

                sendRequest();
            }
        });
        restartRequest();
    }

    private void restartRequest(){
        //自动刷新
        swipeRefresh.post(new Runnable() {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);

            }
        });

        this.onRefresh();
    }

    /**
     *
     */
    public void reStartSendHandler( ){

            restartRequest();
    }

    private Handler dataHandler = new Handler(){

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            int what = msg.what;

            if(what == 0){//由adapter内操作“更多银行”，发起新请求
                //查看更多银行
                TravelDataManager.getInstance().setBankStatus(ITravelList.MORE_BANKSTATUS_CODE);
                restartRequest();

            }else if(what == 1){//加载无卡信息

                adapter.addNoCardDataView(getString(R.string.travel_your_bank_no_pre));

                swipeRefresh.setRefreshing(false);

            }

        }
    };

    @Override
    public void onRefresh()
    {
        if(!ifUserNoCard) {

            pageStart = 0;
            sendRequest();

        }else{

            dataHandler.sendEmptyMessageDelayed(1,2*1000);
        }
    }

    /**
     * 发送请求
     * 以及特殊业务处理
     */
    public void sendRequest()
    {
        //设置请求参数
        TravelDataManager.getInstance().setRequestParameter();

        TravelDataManager.TravleListRequestBean requestBean = TravelDataManager.getInstance().getRequestBean();

        requestBean.setProductType(bean.getObjType() + "");

        String bankStatus = TravelDataManager.getInstance().getBankStatus();


        if(bankStatus.equals(ITravelList.MY_BANKSTATUS_CODE)){//获取我的银行卡的所有银行信息

            pageStart = 0;

            requestBean.setPageStart(pageStart);

            requestBean.setPageSize(Integer.MAX_VALUE);

            specialFlag = false;

        }else{
            requestBean.setPageStart(pageStart);

            requestBean.setPageSize(AppConstant.MAX_PAGE_NUM);

            /**
             * 特殊参数说明：当前fragment,加载完“我的”银行和“更多”银行，则其它fragment从“我的”银行重新加载
             */
            if(specialFlag ){
                /**
                 * 其它尚无加载“我的”旅游
                 */
                if(ITravelList.MY_BANKSTATUS_CODE.equals(bankStatus)){

                    pageStart = 0;

                    requestBean.setBankStatus(ITravelList.MY_BANKSTATUS_CODE);

                    requestBean.setPageStart(pageStart);

                    requestBean.setPageSize(Integer.MAX_VALUE);
                }else {

                    specialFlag = false;
                }
            }

        }

        presenter.getTavelLineProductByClass(requestBean);
    }



    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == ITravelList.QUERY_TRAVEL_PRODUCT_URL) {

            if (o == null) {//无信息处理

                if (pageStart == 0) {//刷新

                    String bankStatusStr = TravelDataManager.getInstance().getBankStatus();

                    if(bankStatusStr.equals(ITravelList.MY_BANKSTATUS_CODE)){//我的银行

                        specialFlag = false;

                        adapter.addNoCardDataView(getString(R.string.travel_your_bank_no_pre));

                    }else if(bankStatusStr.equals(ITravelList.MORE_BANKSTATUS_CODE)){//更多银行


                    }else if(bankStatusStr.equals(ITravelList.ALL_BANKSTATUS_CODE)){//全部

                        adapter.addNoDataPrompt(getString(R.string.travel_no_travel_info));
                    }

                    swipeRefresh.setRefreshing(false);

                } else {

                    listview.calByNewNum(0);
                }


            } else {

                List<TravelLineProductBean> list = JSONArray.parseArray(o + "", TravelLineProductBean.class);

                int size = list.size();

                if (size == AppConstant.MAX_PAGE_NUM) {

                    pageStart = adapter.getItemCount();
                }

                if (pageStart == 0) {//刷新

                    String bankStatusStr = TravelDataManager.getInstance().getBankStatus();

                    if(bankStatusStr.equals(ITravelList.MY_BANKSTATUS_CODE)){//我的银行

                        specialFlag = false;
                        adapter.addMoreBankView(list);

                    }else if(bankStatusStr.equals(ITravelList.MORE_BANKSTATUS_CODE)){//更多银行

                        adapter.addMoreBankRefresh(list);

                    }else if(bankStatusStr.equals(ITravelList.ALL_BANKSTATUS_CODE)){//全部

                        adapter.addRefresh(list);
                    }

                    swipeRefresh.setRefreshing(false);

                    if (size < AppConstant.MAX_PAGE_NUM) {

                        listview.calByNewNum(size);
                    }

                } else {//加载更多

                    String bankStatusStr = TravelDataManager.getInstance().getBankStatus();

                    if(bankStatusStr.equals(ITravelList.MORE_BANKSTATUS_CODE)){//更多银行

                        adapter.addMoreBankMoreInfo(list);

                    }else if(bankStatusStr.equals(ITravelList.ALL_BANKSTATUS_CODE)){//全部

                        adapter.addLoadMoreData(list);
                    }

                    if (size < AppConstant.MAX_PAGE_NUM) {

                        listview.calByNewNum(size);

                    } else {

                        listview.setLoadMoreEnable(true);
                    }

                }

            }
        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if(o != null){
            ToastUtil.i(getContext(),""+o);
        }

        if (pageStart == 0) {//刷新


            swipeRefresh.setRefreshing(false);

        } else {//加载更多

            listview.setLoadMoreEnable(true);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

//        LogUtil.e("ABCD","requestCode="+requestCode);
//
//        if(resultCode == Activity.RESULT_OK){
//
//            if(requestCode==1000){
//                reStartSendHandler();
//            }
//
//        }
    }
}