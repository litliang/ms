package com.yzb.card.king.ui.bonus.presenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.bonus.model.RedBagRecOrSendDao;
import com.yzb.card.king.ui.bonus.model.RedBagRecOrSendImpl;

import java.util.Map;

/**
 * 类 名： Presenter
 * 作 者： gaolei
 * 日 期：2016年12月20日
 * 描 述：红包领取记录的Presenter
 */
public class RedBagRecOrSendPresenter implements DataCallBack {

    private RedBagRecOrSendDao dao;
    private BaseViewLayerInterface view;

    public RedBagRecOrSendPresenter(BaseViewLayerInterface view) {
        this.view = view;
        dao = new RedBagRecOrSendImpl(this);
    }

    public void getRedBagDetailTotalData(Map<String, Object> map, String serviceName, int type) {
        dao.setBillDetail(map, serviceName, type);
    }


    @Override
    public void requestSuccess(Object o, int type) {
        if (type == 1) {
            view.callSuccessViewLogic(o, type);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {
        if (type == 1) {
            view.callFailedViewLogic(o, type);
        }
    }
}
