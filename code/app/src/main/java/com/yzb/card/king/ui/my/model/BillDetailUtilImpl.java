package com.yzb.card.king.ui.my.model;


import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类 名： 收支明细统一格式
 * 作 者： gaolei
 * 日 期：2017年01月05日
 * 描 述： 实现类
 */

public class BillDetailUtilImpl implements BillDetailUtilDao{
    private DataCallBack callBack;

    public BillDetailUtilImpl(DataCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void setBillDetailUtil(Map<String, Object> map, String serviceName, int type) {
        CommonServerRequest request = new CommonServerRequest();
        request.sendReuqest(map,serviceName,type);
        request.setOnDataLoadFinish(callBack);
    }
}
