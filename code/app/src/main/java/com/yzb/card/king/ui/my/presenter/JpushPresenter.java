package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.my.model.JpushDao;
import com.yzb.card.king.ui.my.model.JpushImpl;
import com.yzb.card.king.ui.my.view.JpushView;

import java.util.Map;

/**
 * 类 名： Jpush
 * 作 者： gaolei
 * 日 期：2017年01月17日
 * 描 述： Presenter
 */

public class JpushPresenter implements DataCallBack {
    private JpushDao dao;
    private JpushView view;

    public JpushPresenter(JpushView view) {
        this.view = view;
        dao = new JpushImpl(this);
    }

    public void getJpushData(Map<String, Object> map, String serviceName, int type) {
        dao.setJpushData(map, serviceName, type);
    }

    @Override
    public void requestSuccess(Object o, int type) {
        if (type == 1) {
            view.onLoadJpushSuccess(o, type);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {
        if (type == 1) {
            view.onLoadJpushFail(o, type);
        }
    }
}
