package com.yzb.card.king.ui.ticket.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.FlightDynamicsBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.ticket.model.IPlaneDtDetail;
import com.yzb.card.king.util.ToastUtil;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/11
 * 描  述：
 */
public class PlaneDtDetailDaoImpl implements IPlaneDtDetail {
    private DataCallBack dataCallBack;

    public PlaneDtDetailDaoImpl(DataCallBack dataCallBack)
    {
        this.dataCallBack = dataCallBack;
    }

    @Override
    public void conCern(Map<String, Object> map)
    {
        new SimpleRequest(CardConstant.card_app_followModify, map).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                dataCallBack.requestSuccess(o, IPlaneDtDetail.CONCERN_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map)
                {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(),
                            onSuccessData.get(HttpConstant.SERVER_ERROR));
                }
                dataCallBack.requestFailedDataCall(null, IPlaneDtDetail.CONCERN_CODE);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    @Override
    public void getDateInfo(Map<String, Object> map)
    {
        new SimpleRequest(CardConstant.card_app_plane_followQuery, map).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                FlightDynamicsBean result = JSON.parseObject(String.valueOf(o),FlightDynamicsBean.class);
                dataCallBack.requestSuccess(result, IPlaneDtDetail.SELECT_INFO);

            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o, IPlaneDtDetail.SELECT_INFO);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }
}
