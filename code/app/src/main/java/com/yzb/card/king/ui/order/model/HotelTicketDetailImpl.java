package com.yzb.card.king.ui.order.model;

import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.util.LogUtil;

import java.util.Map;

/**
 * Created by sushuiku on 2016/11/5.
 */

public class HotelTicketDetailImpl implements HotelTicketDetailDAO {

    private DataCallBack dataCallBack;

    private int type = 0;

    public void setType(int type)
    {
        this.type = type;
    }

    public HotelTicketDetailImpl(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    @Override
    public void sendRequst(String serviceName,Map<String, Object> paramMap) {
        new SimpleRequest(serviceName, paramMap).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {
//                Map map = JSON.parseObject(String.valueOf(o),Map.class);
//                LogUtil.i("object==1"+map);
                dataCallBack.requestSuccess(o, type);
            }

            @Override
            public void onFailed(Object o) {
                LogUtil.i("object==2  "+o);

                dataCallBack.requestFailedDataCall(o,type);
            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
