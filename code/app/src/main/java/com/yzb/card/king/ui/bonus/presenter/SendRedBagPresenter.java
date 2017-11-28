package com.yzb.card.king.ui.bonus.presenter;

import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.bonus.model.ReceiveRedBagDao;
import com.yzb.card.king.ui.bonus.model.ReceiveRedBagImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 类 名： Presenter
 * 作 者： gaolei
 * 日 期：2017年01月04日
 * 描 述：发红包记录的Presenter
 */
public class SendRedBagPresenter implements DataCallBack {

    private ReceiveRedBagDao dao;
    private BaseViewLayerInterface view;

    public SendRedBagPresenter(BaseViewLayerInterface view) {
        this.view = view;
        dao = new ReceiveRedBagImpl(this);
    }

    public void getSendRedBagData( int pageStart, int type){

        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", pageStart);
        param.put("pageSize", AppConstant.MAX_PAGE_NUM);

        dao.setReceiveRedBag(param, CardConstant.QueryBonusOrder,type);
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
