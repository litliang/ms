package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.my.model.BillDetailDao;
import com.yzb.card.king.ui.my.model.BillDetailDaoImpl;

import java.util.Map;

/**
 * 类 名： Presenter
 * 作 者： gaolei
 * 日 期：2016年12月20日
 * 描 述：个人账单的Presenter
 */

public class BillDetailPresenter implements DataCallBack {
    private BaseViewLayerInterface view;
    private BillDetailDao dao;

    public BillDetailPresenter(BaseViewLayerInterface view) {
        this.view = view;
        dao = new BillDetailDaoImpl(this);
    }

    public void getBillDetailTotalData(Map<String, Object> map, String serviceName, int type){
        dao.setBillDetail(map,serviceName,type);
    }

    @Override
    public void requestSuccess(Object o, int type) {
            view.callSuccessViewLogic(o,type);
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {
            view.callFailedViewLogic(o,type);
    }
}
