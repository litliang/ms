package com.yzb.card.king.ui.appwidget.popup.presenter.impl;

import com.yzb.card.king.ui.appwidget.popup.ChannelLoadListener;
import com.yzb.card.king.ui.appwidget.popup.model.ChannelUpdateModel;
import com.yzb.card.king.ui.appwidget.popup.model.impl.ChannelUpdateModelImpl;
import com.yzb.card.king.ui.appwidget.popup.presenter.ChannelUpdatePresenter;
import com.yzb.card.king.ui.appwidget.popup.view.ChannelUpdateView;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;

import java.util.List;
import java.util.Map;

/**
 * 功能：频道更新；
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class ChannelUpdatePresenterImpl implements ChannelUpdatePresenter, ChannelLoadListener
{
    private ChannelUpdateModel model;
    private ChannelUpdateView view;

    public ChannelUpdatePresenterImpl(ChannelUpdateView view)
    {
        this.view = view;
        model = new ChannelUpdateModelImpl(this);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        model.updateChannels(paramMap);
    }

    @Override
    public void onListenSuccess(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList)
    {
        view.onChannelUpdateSucess();
    }

    @Override
    public void onListenError(String msg)
    {
        view.onChannelUpdateFail();
    }

}
