package com.yzb.card.king.ui.integral.model.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.integral.ExchangeIntegralBean;
import com.yzb.card.king.bean.integral.ExchangeIntegralListBean;
import com.yzb.card.king.bean.integral.ExchangeMileageBean;
import com.yzb.card.king.bean.integral.IntegralExchangeBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.integral.IntegralPoinExchangeRequest;
import com.yzb.card.king.http.ticket.GetDefaultAddressRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.integral.model.IIntegralExchange;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.wallet.openInterface.PayPointHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：积分兑换实现类
 * 作  者：Li Yubing
 * 日  期：2016/9/13
 * 描  述：
 */
public class IntegralExchangeImpl implements IIntegralExchange{


    private DataCallBack dataCallBack;

    public IntegralExchangeImpl( DataCallBack dataCallBack)
    {

        this.dataCallBack = dataCallBack;
    }

    @Override
    public void sendAirlineCompanyListRequest(String countryId)
    {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("countryId",countryId);

        SimpleRequest  request = new SimpleRequest();

        request.setServerName(CardConstant.INTEGRAL_AIRLINECOMPANYLIST);

        request.setParam(param);

        request.sendRequest(new HttpCallBackData(){
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                String data = o+"";

                ExchangeMileageBean  userBean = JSON.parseObject(data, ExchangeMileageBean.class);

                dataCallBack.requestSuccess(userBean,0);
            }

            @Override
            public void onFailed(Object o)
            {

                dataCallBack.requestFailedDataCall(o,0);

            }

            @Override
            public void onCancelled(Object o)
            {

                dataCallBack.requestFailedDataCall(o,0);
            }

            @Override
            public void onFinished()
            {

            }
        });

    }

    @Override
    public void pointExchangePlanRequest(int type, String airlineId)
    {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("type",type);

        if(type == 1){
            if(!TextUtils.isEmpty(airlineId)){

                param.put("airlineId",airlineId);
            }
        }

        SimpleRequest  request = new SimpleRequest();

        request.setServerName(CardConstant.INTEGRAL_POINTEXCHANGEPLAN);

        request.setParam(param);

        request.sendRequest(new HttpCallBackData(){
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                String data = o+"";

                List<ExchangeIntegralListBean> list = JSON.parseArray(data, ExchangeIntegralListBean.class);

                dataCallBack.requestSuccess(list,IIntegralExchange.POINTEXCHANGEPLAN_CODE);
            }

            @Override
            public void onFailed(Object o)
            {

                dataCallBack.requestFailedDataCall(o,IIntegralExchange.POINTEXCHANGEPLAN_CODE);

            }

            @Override
            public void onCancelled(Object o)
            {

                dataCallBack.requestFailedDataCall(o,IIntegralExchange.POINTEXCHANGEPLAN_CODE);
            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    @Override
    public void sendFrequentPassengerPlanRequest(String airlineId)
    {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("airlineId",airlineId);

        SimpleRequest  request = new SimpleRequest();

        request.setServerName(CardConstant.INTEGRAL_FREQUENTPASSENGERPLAN);

        request.setParam(param);

        request.sendRequest(new HttpCallBackData(){
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                String data = o+"";

                List<ExchangeIntegralListBean> userBean = JSON.parseArray(data, ExchangeIntegralListBean.class);

                dataCallBack.requestSuccess(userBean,IIntegralExchange.FREQUENTPASSENGERPLANREQUEST_CODE);
            }

            @Override
            public void onFailed(Object o)
            {

                dataCallBack.requestFailedDataCall(o,IIntegralExchange.FREQUENTPASSENGERPLANREQUEST_CODE);

            }

            @Override
            public void onCancelled(Object o)
            {

                dataCallBack.requestFailedDataCall(o,IIntegralExchange.FREQUENTPASSENGERPLANREQUEST_CODE);
            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    @Override
    public void sendIntegralPointExchangeCashRequest(int type, List<ExchangeIntegralBean> adapterDataList, String planId)
    {
        new IntegralPoinExchangeRequest(type,adapterDataList,planId).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                String data = o+"";

                IntegralExchangeBean sucess = JSON.parseObject(data, IntegralExchangeBean.class);

                dataCallBack.requestSuccess(sucess,IIntegralExchange.INTEGRALPOINTEXCHANGECASH_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,IIntegralExchange.INTEGRALPOINTEXCHANGECASH_CODE);
            }

            @Override
            public void onCancelled(Object o)
            {
                dataCallBack.requestFailedDataCall(o,IIntegralExchange.INTEGRALPOINTEXCHANGECASH_CODE);
            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    @Override
    public void sendPayPointHandle(Map<String, String> params , Activity activity)
    {


        PayPointHandle payHandle = new PayPointHandle(activity);
        payHandle.pay(params);
        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String s) {

            }

            @Override
            public void setSuccess(Map<String, String> map, String s) {

                dataCallBack.requestSuccess(s,IIntegralExchange.PAYPOINT_CODE);
            }

            @Override
            public void setError(String s, String s1) {

                dataCallBack.requestFailedDataCall(s+"&&"+s1,IIntegralExchange.PAYPOINT_CODE);
            }
        });
    }

    @Override
    public void getDefaultAddress()
    {
        new GetDefaultAddressRequest().sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                List<GoodsAddressBean> list = JSON.parseArray(String.valueOf(o), GoodsAddressBean.class);

                dataCallBack.requestSuccess(list, IIntegralExchange.DEFAULTADDRESS_CODE);
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
                dataCallBack.requestFailedDataCall(null, IIntegralExchange.DEFAULTADDRESS_CODE);
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
    public void sendIntegralPointExchangeYoukaRequest(int type, List<ExchangeIntegralBean> adapterDataList, String planId, String status, String addressId, String postPage, String number)
    {

        new IntegralPoinExchangeRequest( type, adapterDataList,  planId, status,  addressId,  postPage,  number).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                String data = o+"";

                IntegralExchangeBean sucess = JSON.parseObject(data, IntegralExchangeBean.class);

                dataCallBack.requestSuccess(sucess,IIntegralExchange.INTEGRALPOINTEXCHANGECASH_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,IIntegralExchange.INTEGRALPOINTEXCHANGECASH_CODE);
            }

            @Override
            public void onCancelled(Object o)
            {
                dataCallBack.requestFailedDataCall(o,IIntegralExchange.INTEGRALPOINTEXCHANGECASH_CODE);
            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    @Override
    public void sendIntegralPointExchangePhoneRequest(int type, List<ExchangeIntegralBean> adapterDataList, String planId, String moblie)
    {

        new IntegralPoinExchangeRequest(  type,  adapterDataList,  planId,  moblie).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                String data = o+"";

                IntegralExchangeBean sucess = JSON.parseObject(data, IntegralExchangeBean.class);

                dataCallBack.requestSuccess(sucess,IIntegralExchange.INTEGRALPOINTEXCHANGECASH_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,IIntegralExchange.INTEGRALPOINTEXCHANGECASH_CODE);
            }

            @Override
            public void onCancelled(Object o)
            {
                dataCallBack.requestFailedDataCall(o,IIntegralExchange.INTEGRALPOINTEXCHANGECASH_CODE);
            }

            @Override
            public void onFinished()
            {

            }
        });
    }
}
