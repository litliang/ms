package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.ui.app.model.ValidPhoneModel;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;

import java.util.Map;

/**
 * 功能：验证手机号
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class ValidPhonePresenter implements  BaseMultiLoadListener
{
    private ValidPhoneModel model;
    private BaseViewLayerInterface view;

    public ValidPhonePresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new ValidPhoneModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(data,1);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,1);
    }
}
