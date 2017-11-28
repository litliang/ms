package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.ui.app.model.VerifyCodeValidityModel;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;

import java.util.Map;

/**
 * 功能：验证验证码是否正确
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public class VerifyCodeValidityPresenter implements  BaseMultiLoadListener
{
    private VerifyCodeValidityModel model;
    private BaseViewLayerInterface view;

    public VerifyCodeValidityPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new VerifyCodeValidityModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(data,2);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,2);
    }
}
