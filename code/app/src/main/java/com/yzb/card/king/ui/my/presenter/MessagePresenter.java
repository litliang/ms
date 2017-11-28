package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.my.model.MessageDao;
import com.yzb.card.king.ui.my.model.MessageImpl;

import java.util.Map;

/**
 * 类 名： 消息
 * 作 者： gaolei
 * 日 期：2017年01月16日
 * 描 述：Presenter
 */
public class MessagePresenter implements DataCallBack {
    private MessageDao dao;
    private BaseViewLayerInterface view;

    public MessagePresenter(BaseViewLayerInterface view) {
        this.view = view;
        dao = new MessageImpl(this);
    }

    public void getMessageData(Map<String, Object> map, String serviceName, int type) {
        dao.setMessageData(map, serviceName, type);
    }

    @Override
    public void requestSuccess(Object o, int type) {
            view.callSuccessViewLogic(o, type);
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {
            view.callFailedViewLogic(o, type);

    }
}
