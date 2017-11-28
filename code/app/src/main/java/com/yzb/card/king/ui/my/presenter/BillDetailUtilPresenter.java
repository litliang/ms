package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.my.model.BillDetailUtilDao;
import com.yzb.card.king.ui.my.model.BillDetailUtilImpl;

import java.util.Map;

/**
 * 类 名： 收支明细统一格式
 * 作 者： gaolei
 * 日 期：2017年01月05日
 * 描 述： presenter
 */
public class BillDetailUtilPresenter implements DataCallBack {
    BillDetailUtilDao dao;
    BaseViewLayerInterface view;

    public BillDetailUtilPresenter(BaseViewLayerInterface view) {
        this.view = view;
        dao = new BillDetailUtilImpl(this);
    }

    public void getBillUtilData(Map<String, Object> map, String serviceName, int type){

        dao.setBillDetailUtil(map, serviceName, type);
    }

    @Override
    public void requestSuccess(Object o, int type) {
        if (type == 1) {
            view.callSuccessViewLogic(o,type);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {
        if (type == 1) {
            view.callFailedViewLogic(o,type);
        }
    }
}
