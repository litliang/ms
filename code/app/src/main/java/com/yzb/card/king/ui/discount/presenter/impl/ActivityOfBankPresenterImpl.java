package com.yzb.card.king.ui.discount.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.model.ActivityOfBankModel;
import com.yzb.card.king.ui.discount.model.impl.ActivityOfBankModelImpl;
import com.yzb.card.king.ui.discount.presenter.ActivityOfBankPresenter;

import java.util.Map;

/**
 * 功能：银行优惠活动
 *
 * @author:gengqiyun
 * @date: 2016/9/20
 */
public class ActivityOfBankPresenterImpl implements ActivityOfBankPresenter, BaseMultiLoadListener
{
    private ActivityOfBankModel model;
    private BaseViewLayerInterface view;

    public ActivityOfBankPresenterImpl(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new ActivityOfBankModelImpl(this);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        //view.onLoadActivityOfBankSucess(data);
        view.callSuccessViewLogic(data,1);
    }

    @Override
    public void onListenError(String msg)
    {
       // view.onLoadActivityOfBankFail(msg);
        view.callFailedViewLogic(msg,1);
    }
}
