package com.yzb.card.king.ui.app.presenter;

import android.app.Activity;

import com.yzb.card.king.ui.app.model.UpdatePayOrderModel;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：更新支付顺序
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class UpdatePayOrderPresenter implements  BaseMultiLoadListener
{
    private UpdatePayOrderModel model;
    private AppBaseView view;

    public UpdatePayOrderPresenter(Activity context, AppBaseView view)
    {
        this.view = view;
        model = new UpdatePayOrderModel(context, this);
    }

    public void loadData(Map<String, String> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onViewCallBackSucess(data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onViewCallBackFail(msg);
    }
}
