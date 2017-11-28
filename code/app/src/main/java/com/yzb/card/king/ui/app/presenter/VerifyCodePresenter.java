package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.ui.app.model.VerifyCodeModel;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;

import java.util.Map;

/**
 * 功能：获取验证码
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public class VerifyCodePresenter implements BaseMultiLoadListener
{
    private VerifyCodeModel model;
    private BaseViewLayerInterface view;

    public VerifyCodePresenter(BaseViewLayerInterface view)
    {
        this.view = view;

        model = new VerifyCodeModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(data,10);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,10);
    }
}
