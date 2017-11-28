package com.yzb.card.king.ui.ticket.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.ShippingSpace;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.ticket.model.ITicketHome;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/13
 * 描  述：
 */
public class TicketHomeDaoImpl implements ITicketHome {

    private DataCallBack dataCallBack;

    public TicketHomeDaoImpl(DataCallBack dataCallBack)
    {
        this.dataCallBack = dataCallBack;
    }


    @Override
    public void getCangWei(Map<String, Object> map)
    {
        new SimpleRequest(CardConstant.card_app_select_cangwei, map).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                List<ShippingSpace> shippingSpaces = JSON.parseArray(String.valueOf(o), ShippingSpace.class);
                dataCallBack.requestSuccess(shippingSpaces, ITicketHome.CANGWEI_TYPE);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(null, ITicketHome.CANGWEI_TYPE);
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
