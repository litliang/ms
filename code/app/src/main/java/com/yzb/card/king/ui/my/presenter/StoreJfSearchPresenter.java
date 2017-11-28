package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.my.model.StoreJfSearchDao;
import com.yzb.card.king.ui.my.model.StoreJfSearchImpl;

import java.util.Map;

/**
 * 类 名： Presenter
 * 作 者： gaolei
 * 日 期：2017年01月04日
 * 描 述：商家积分查询Presenter
 */
public class StoreJfSearchPresenter implements DataCallBack {

    private StoreJfSearchDao dao;

    private BaseViewLayerInterface view;

    public StoreJfSearchPresenter(BaseViewLayerInterface view) {
        this.view = view;
        dao = new StoreJfSearchImpl(this);
    }

    public void getStoreJfData(Map<String, Object> map, String serviceName, int type){

        dao.setBillDetail(map, serviceName, type);
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
