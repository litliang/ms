package com.yzb.card.king.ui.luxury.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.util.ToastUtil;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/20
 * 描  述：
 */
public class MsDetailDaoImpl implements IMsDetail {

    private DataCallBack dataCallBack;

    public MsDetailDaoImpl(DataCallBack dataCallBack){
        this.dataCallBack=dataCallBack;
    }

    @Override
    public void getData(Map<String, Object> param)
    {
        SimpleRequest simpleRequest=new SimpleRequest(CardConstant.card_app_storeinfo,param);
        simpleRequest.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                StoreBean storeBean = JSON.parseObject(String.valueOf(o), StoreBean.class);
                dataCallBack.requestSuccess(storeBean,IMsDetail.GETDATA_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map) {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                    if(dataCallBack != null) {
                        dataCallBack.requestFailedDataCall(null, IMsDetail.GETDATA_CODE);
                    }
                }
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
    public void collect(Map<String, Object> param)
    {
        SimpleRequest simpleRequest=new SimpleRequest(CardConstant.card_app_collections,param);
        simpleRequest.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                dataCallBack.requestSuccess(o,IMsDetail.COLLECT_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map) {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                    if(dataCallBack != null) {
                        dataCallBack.requestFailedDataCall(null, IMsDetail.COLLECT_CODE);
                    }
                }
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
    public void isCollect(Map<String, Object> param)
    {
        SimpleRequest simpleRequest=new SimpleRequest(CardConstant.card_app_collections,param);
        simpleRequest.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                Map map=JSONObject.parseObject(String.valueOf(o),Map.class);
                dataCallBack.requestSuccess(map,IMsDetail.ISCOLLECT_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map) {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                    if(dataCallBack != null) {
                        dataCallBack.requestFailedDataCall(null, IMsDetail.ISCOLLECT_CODE);
                    }
                }
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
