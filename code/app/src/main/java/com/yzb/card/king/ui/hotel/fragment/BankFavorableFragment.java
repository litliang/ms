package com.yzb.card.king.ui.hotel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.BankActivityInfoBean;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.ui.appwidget.appdialog.HotelProductRoomInfoFragmentDialog;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.holder.BankLifeStageHolder;
import com.yzb.card.king.ui.hotel.persenter.HotelBankActivityPersenter;
import com.yzb.card.king.ui.ticket.fragment.TicketDetailFragmentDialog;
import com.yzb.card.king.util.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.lemon.view.adapter.MultiTypeAdapter;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/9
 * 描  述：银行优惠
 */
public class BankFavorableFragment extends Fragment implements View.OnClickListener,BaseViewLayerInterface {


    private HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall;

    private TicketDetailFragmentDialog.TicketDialogInterface ticketDetailDataCall;

    private RecyclerView wvLvData;

    private MultiTypeAdapter mAdapter;

    private HotelBankActivityPersenter hotelBankActivityPersenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        hotelBankActivityPersenter = new HotelBankActivityPersenter(this);

        View view = inflater.inflate(R.layout.view_bank_favorable, null);

        initView(view);

        return view;
    }

    private void initView(View view)
    {

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        tvTitle.setText("银行优惠");

        view.findViewById(R.id.llTemp).setOnClickListener(this);

        wvLvData = (RecyclerView) view.findViewById(R.id.wvLvData);

        mAdapter = new MultiTypeAdapter(getContext());

        wvLvData.setLayoutManager(new LinearLayoutManager(getContext()));
        wvLvData.setAdapter(mAdapter);

        wvLvData.post(new Runnable() {
            @Override
            public void run()
            {
                /**
                 *
                 */

                    initData();


            }
        });
    }


    private void initData()
    {

        Bundle bundle =   getArguments();

        if(bundle.containsKey("goldTicketParam")){

            GoldTicketParam goldTicketParam = (GoldTicketParam) bundle.getSerializable("goldTicketParam");

            int goodsType = bundle.getInt("goodsType");

            ProgressDialogUtil.getInstance().showProgressDialogMsg("正在请求数据……", getContext(), false);

            hotelBankActivityPersenter.sendBankPreActivityDetailRequest(goldTicketParam,goodsType);


        }else  if(bundle.containsKey("localData")) {

            ProgressDialogUtil.getInstance().closeProgressDialog();

            List<BankActivityInfoBean> bankList = new ArrayList<BankActivityInfoBean>();

            BankActivityInfoBean oneBean = new BankActivityInfoBean();

            oneBean.setBankId(57);

            oneBean.setEndDate("2018-01-04");

            oneBean.setActId("1");

            oneBean.setBankName("浦发银行");

            oneBean.setBankLogo("20170222150339169817021678");

            oneBean.setActName("满减1");

            oneBean.setStartDate("2017-08-17");

            bankList.add(oneBean);

            BankActivityInfoBean twoBean = new BankActivityInfoBean();

            twoBean.setBankId(46);

            twoBean.setEndDate("2018-01-04");

            twoBean.setActId("2");

            twoBean.setBankName("民生银行");

            twoBean.setBankLogo("20170222150250236017021675");

            twoBean.setActName("满减2");

            twoBean.setStartDate("2017-08-17");

            bankList.add(twoBean);


            BankActivityInfoBean threeBean = new BankActivityInfoBean();
            threeBean.setBankId(48);

            threeBean.setEndDate("2018-01-04");

            threeBean.setActId("3");

            threeBean.setBankName("光大银行");

            threeBean.setBankLogo("20170222150121211517021670");

            threeBean.setActName("满减3");

            threeBean.setStartDate("2017-08-17");

            bankList.add(threeBean);

            mAdapter.clear();

            mAdapter.addAll(BankLifeStageHolder.class, bankList);

        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.llTemp:

                if (dataCall != null) {
                    dataCall.backAction();
                }

                if(ticketDetailDataCall != null){

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

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        List<BankActivityInfoBean> bankList = JSONArray.parseArray(o+"",BankActivityInfoBean.class);

        mAdapter.clear();

        mAdapter.addAll(BankLifeStageHolder.class, bankList);
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }

    public void setTicketDetailDataCall(TicketDetailFragmentDialog.TicketDialogInterface ticketDetailDataCall)
    {
        this.ticketDetailDataCall = ticketDetailDataCall;
    }
}
