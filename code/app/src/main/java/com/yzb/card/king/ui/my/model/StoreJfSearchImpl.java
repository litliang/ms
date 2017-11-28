package com.yzb.card.king.ui.my.model;

import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类 名： 红包领取记录实现类
 * 作 者： gaolei
 * 日 期：2016年12月22日
 * 描 述：Model实现类
 */

public class StoreJfSearchImpl implements StoreJfSearchDao{

    private DataCallBack callBack;

    public StoreJfSearchImpl(DataCallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public void setBillDetail(Map<String, Object> map, String serviceName, int type) {
        CommonServerRequest request = new CommonServerRequest();
        request.sendReuqest(map,serviceName,type);
        request.setOnDataLoadFinish(callBack);
    }
}
