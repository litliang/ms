package com.yzb.card.king.ui.my.model;

import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类 名： 消息
 * 作 者： gaolei
 * 日 期：2017年01月12日
 * 描 述：Impl
 */

public class MessageImpl implements MessageDao{

    private DataCallBack callBack;

    public MessageImpl(DataCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void setMessageData(Map<String, Object> map, String serviceName, int type) {
        CommonServerRequest request = new CommonServerRequest();
        request.sendReuqest(map,serviceName,type);
        request.setOnDataLoadFinish(callBack);
    }
}
