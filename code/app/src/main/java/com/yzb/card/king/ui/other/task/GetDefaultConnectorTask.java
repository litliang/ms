package com.yzb.card.king.ui.other.task;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/20 16:05
 * 描述：
 */
public  class GetDefaultConnectorTask  extends BaseRequest implements HttpCallBackData {

    /**
     * 接口名
     */
    private String serverName = CardConstant.DISCOUNT_CONTACTSLIST;

    Map<String, Object> param = new HashMap<String, Object>();

    private ConnectorDataCall dataCall;

    public GetDefaultConnectorTask(ConnectorDataCall dataCall) {

        this.dataCall = dataCall;

        param.put("type", "2");
    }



    @Override
    public void onStart()
    {

    }

    @Override
    public void onSuccess(Object o)
    {
        String data = o+"";
        List<Connector> list = JSON.parseArray(data, Connector.class);
        if (list == null) {
            return;
        }
        for (Connector connector : list) {
            if (connector.isDefault) {
                dataCall.setConnector(connector);
                break;
            }
        }
    }

    @Override
    public void onFailed(Object o)
    {

        if( o != null){

            ToastUtil.i(GlobalApp.getInstance().getContext(),o+"");
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

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID, param), this);
    }

    public interface  ConnectorDataCall{

        void setConnector(Connector connector);
    }



}
