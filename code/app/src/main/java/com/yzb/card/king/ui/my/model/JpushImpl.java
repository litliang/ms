package com.yzb.card.king.ui.my.model;

import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类 名： Jpush
 * 作 者： gaolei
 * 日 期：2017年01月17日
 * 描 述： Impl
 */

public class JpushImpl implements JpushDao{

    private DataCallBack callBack;

    public JpushImpl(DataCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void setJpushData(Map<String, Object> map, String serviceName, int type) {
        CommonServerRequest request = new CommonServerRequest();
        request.sendReuqest(map,serviceName,type);
        request.setOnDataLoadFinish(callBack);
    }
}
