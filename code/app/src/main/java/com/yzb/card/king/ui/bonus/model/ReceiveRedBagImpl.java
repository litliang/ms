package com.yzb.card.king.ui.bonus.model;


import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类 名： 收到红包记录Model实现类
 * 作 者： gaolei
 * 日 期：2016年12月20日
 * 描 述：Model实现类
 */
public class ReceiveRedBagImpl implements ReceiveRedBagDao {

    private DataCallBack callback;

    public ReceiveRedBagImpl(DataCallBack callback) {
        this.callback = callback;
    }

    @Override
    public void setReceiveRedBag(Map<String, Object> map, String service, int type) {
        CommonServerRequest csr = new CommonServerRequest();
        csr.sendReuqest(map, service, type);
        csr.setOnDataLoadFinish(callback);
    }
}
