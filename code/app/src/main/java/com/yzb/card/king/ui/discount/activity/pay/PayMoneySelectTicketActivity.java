package com.yzb.card.king.ui.discount.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.BaseCouponBean;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.adapter.UserPaySelectTicketAdapter;
import com.yzb.card.king.ui.hotel.persenter.GetCouponPersenter;
import com.yzb.card.king.util.ProgressDialogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 类  名：特惠付款 --- 选择优惠券\代金券
 * 作  者：Li Yubing
 * 日  期：2017/8/23
 * 描  述：
 */
@ContentView(R.layout.activity_app_select_ticket_payment)
public class PayMoneySelectTicketActivity  extends BaseActivity implements BaseViewLayerInterface{

    @ViewInject(R.id.wvLvData)
    private RecyclerView wvLvData;

    @ViewInject(R.id.tvFuctionGet)
    private ImageView tvFuctionGet;

    private UserPaySelectTicketAdapter mAdapter;

    private   long selectActId = -1;

    private   int issuePlatform;

    private double orderMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        initData();

    }

    private void initData()
    {
        if(getIntent().hasExtra("selectActId")){

            selectActId = getIntent().getLongExtra("selectActId",-1);


            orderMoney = getIntent().getDoubleExtra("orderMoney",0);

            if(selectActId ==-1){

                tvFuctionGet.setVisibility(View.VISIBLE);
            }else {

                tvFuctionGet.setVisibility(View.INVISIBLE);
            }

        }else {

            tvFuctionGet.setVisibility(View.VISIBLE);
        }

        mAdapter.setActionId(selectActId);

        mAdapter.setOrderMoney(orderMoney);
    }

    private void initView()
    {

       if(getIntent().hasExtra("titleName")){

           setTitleNmae(getIntent().getStringExtra("titleName"));

            issuePlatform = getIntent().getIntExtra("issuePlatform",1);

       }else {

           setTitleNmae("选择券");

       }

        mAdapter = new UserPaySelectTicketAdapter(this);

        mAdapter.setHandler(dataHandler);

        wvLvData.setLayoutManager(new LinearLayoutManager(this));

        wvLvData.setAdapter(mAdapter);

        wvLvData.post(new Runnable() {
            @Override
            public void run()
            {
                /**
                 * 请求该酒店的房间信息
                 */
                initRequest();

            }
        });

        findViewById(R.id.llOne).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {

                if(issuePlatform ==1){
                    setResult(7778);
                }else if(  issuePlatform==2){
                    setResult(7779);
                }


                finish();
            }
        });
    }

    public void initRequest(){

        ProgressDialogUtil.getInstance().showProgressDialogMsg("正在请求数据……", this, false);

        GoldTicketParam goldTicketParam = (GoldTicketParam) getIntent().getSerializableExtra("goldTicketParam");

        GetCouponPersenter persenter = new GetCouponPersenter(this);

        persenter.sendCanUseCouponListAction(issuePlatform,goldTicketParam.getIndustryId(),goldTicketParam.getShopId(),goldTicketParam.getStoreId(),goldTicketParam.getGoodsId());

    }


    private Handler dataHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            BaseCouponBean    baseCouponBean = (BaseCouponBean) msg.obj;

            Intent couponBean = new Intent();

            couponBean.putExtra("baseCouponBean",baseCouponBean);

            if(issuePlatform ==1){

                setResult(7778,couponBean);

            }else if(  issuePlatform==2){

                setResult(7779,couponBean);
            }

            finish();

        }
    };

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

        ProgressDialogUtil.getInstance().closeProgressDialog();

        if(GetCouponPersenter.CANUSECOUPONLIST_CODE == type){

            List<BaseCouponBean> list  = JSON.parseArray(o+"",BaseCouponBean.class);

            mAdapter.clear();

            mAdapter.addAll(list);

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }
}
