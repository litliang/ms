package com.yzb.card.king.ui.my.model;

import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类 名： 个人账单实现类
 * 作 者： gaolei
 * 日 期：2016年12月22日
 * 描 述：Model实现类
 */
public class BillDetailDaoImpl implements BillDetailDao{
    private DataCallBack callBack;

    public BillDetailDaoImpl(DataCallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public void setBillDetail(Map<String, Object> map, String serviceName, int type) {
        CommonServerRequest request = new CommonServerRequest();
        request.sendReuqest(map,serviceName,type);
        request.setOnDataLoadFinish(callBack);
    }
}
