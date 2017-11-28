package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.ui.app.model.UpdateDefaultModel;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：更新默认发票抬头
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class UpdateDefaultPresenter implements  BaseMultiLoadListener
{
    private UpdateDefaultModel model;
    private AppBaseView view;

    public UpdateDefaultPresenter(AppBaseView view)
    {
        this.view = view;
        model = new UpdateDefaultModel(this);
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
