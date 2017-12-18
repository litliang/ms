package com.yzb.card.king.ui.hotel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.BaseCouponBean;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.appwidget.appdialog.HotelProductRoomInfoFragmentDialog;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.hotel.adapter.AppCouponItemAdapter;
import com.yzb.card.king.ui.hotel.holder.GoldTicketHolder;
import com.yzb.card.king.ui.hotel.persenter.GetCouponPersenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.activity.CouponMoreShopsActivity;
import com.yzb.card.king.ui.my.pop.BuySucesWithOkDialog;
import com.yzb.card.king.ui.ticket.fragment.TicketDetailFragmentDialog;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.wallet.logic.pay.PayRequestLogic;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.PayMethodListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lemon.view.adapter.MultiTypeAdapter;

/**
 * 类  名：优惠券
 * 作  者：Li Yubing
 * 日  期：2017/8/8
 * 描  述：
 */
public class GetCouponFragment extends Fragment implements View.OnClickListener,BaseViewLayerInterface {

    private  HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall;

    TicketDetailFragmentDialog.TicketDialogInterface ticketDetailDataCall;

    private RecyclerView wvLvData;

    private AppCouponItemAdapter mAdapter;

    private GetCouponPersenter persenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        persenter = new GetCouponPersenter(this);


        View view = inflater.inflate(R.layout.view_get_coupon_center,null);

        initView(view);

        return view;
    }

    private void initView(View view)
    {

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        tvTitle.setText("领取优惠券");

        view.findViewById(R.id.llTemp).setOnClickListener(this);

        wvLvData = (RecyclerView) view.findViewById(R.id.wvLvData);

        mAdapter = new AppCouponItemAdapter(getContext());

        mAdapter.setHandler(dataHandler);

        wvLvData.setLayoutManager(new LinearLayoutManager(getContext()));

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
    }

    public void initRequest(){

        Bundle bundle = getArguments();

        if(bundle.containsKey("localData")){


        }else {

            ProgressDialogUtil.getInstance().showProgressDialogMsg("正在请求数据……", getContext(), false);

            int issuePlatform =  bundle.getInt("issuePlatform");

            int industryId =  bundle.getInt("industryId");

            long shopId =  bundle.getLong("shopId");

            long storeId =  bundle.getLong("storeId");

            long goodsId =  bundle.getLong("goodsId");

            persenter.sendGetCouponListRequest(issuePlatform,industryId,shopId,storeId,goodsId);
        }



    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.llTemp:

                if(dataCall != null){
                    dataCall.backAction();
                }

                if( ticketDetailDataCall != null){

                    ticketDetailDataCall.backAction(2);
                }

                break;
            default:
                break;

        }
    }

    public void setDataCall(HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall)
    {
        this.dataCall = dataCall;
    }

    private  BaseCouponBean  baseCouponBean;

    private Handler dataHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            ProgressDialogUtil.getInstance().showProgressDialogMsg("正在请求数据……", getContext(), false);

              baseCouponBean = (BaseCouponBean) msg.obj;


             long actId = baseCouponBean.getActId();

             persenter.sendReceiveCouponRequest(actId);

        }
    };


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if(GetCouponPersenter.GETCOUPONLIST_CODE == type){
            List<BaseCouponBean> list = JSON.parseArray(o+"",BaseCouponBean.class);

            mAdapter.clear();

            mAdapter.addAll( list);

        }else if(GetCouponPersenter.RECEIVECOUPON_CODE == type){

            ToastUtil.i(getContext(),"领取成功");

            if(baseCouponBean != null){

                baseCouponBean.setRecieveStatus("1");

                mAdapter.notifyDataSetChanged();
            }

        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if(GetCouponPersenter.RECEIVECOUPON_CODE == type){

            ToastUtil.i(getContext(),"领取失败");


        }
    }

    public void setTicketDetailDataCall(TicketDetailFragmentDialog.TicketDialogInterface ticketDetailDataCall)
    {
        this.ticketDetailDataCall = ticketDetailDataCall;
    }


}
