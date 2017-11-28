package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.ui.app.model.ResetLoginPwdModel;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：重置登录密码
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public class ResetLoginPwdPresenter implements  BaseMultiLoadListener
{
    private ResetLoginPwdModel model;
    private AppBaseView view;

    public ResetLoginPwdPresenter(AppBaseView view)
    {
        this.view = view;
        model = new ResetLoginPwdModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
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
