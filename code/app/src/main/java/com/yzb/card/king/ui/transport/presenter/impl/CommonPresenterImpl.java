package com.yzb.card.king.ui.transport.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.transport.model.CommonModel;
import com.yzb.card.king.ui.transport.model.impl.CommonModelImpl;
import com.yzb.card.king.ui.transport.presenter.CommonPresenter;
import com.yzb.card.king.ui.transport.view.CommonView;

import java.util.Map;

/**
 * 功能：机票
 *
 * @author:gengqiyun
 * @date: 2016/9/6
 */
public class CommonPresenterImpl implements CommonPresenter, BaseMultiLoadListener
{
    private CommonModel model;
    private CommonView view;

    public CommonPresenterImpl(CommonView view)
    {
    this.view = view;
    model = new CommonModelImpl(this);
}

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onLoadSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onLoadFail(msg);
    }
}
