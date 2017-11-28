package com.yzb.card.king.ui.app.presenter;

import android.app.Activity;

import com.yzb.card.king.ui.app.interfaces.PayMethodsListener;
import com.yzb.card.king.ui.app.model.PayMethodsModel;
import com.yzb.card.king.ui.app.view.PayMethodsView;
import com.yzb.card.king.bean.common.PayMethod;

import java.util.List;
import java.util.Map;

/**
 * 功能：支付方式
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class PayMethodsPresenter implements PayMethodsListener
{
    private PayMethodsModel model;
    private PayMethodsView view;

    public PayMethodsPresenter(Activity context, PayMethodsView view)
    {
        this.view = view;
        model = new PayMethodsModel(context, this);
    }

    public void loadData(Map<String, String> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(String tag, List<PayMethod> data)
    {
        view.onGetPayMethodListSucess(tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetPayMethodListFail(msg);
    }
}
