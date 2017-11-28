package com.yzb.card.king.ui.appwidget.popup.presenter.impl;

import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.appwidget.popup.ChannelLoadListener;
import com.yzb.card.king.ui.appwidget.popup.model.ChannelModel;
import com.yzb.card.king.ui.appwidget.popup.model.impl.ChannelModelImpl;
import com.yzb.card.king.ui.appwidget.popup.presenter.ChannelPresenter;
import com.yzb.card.king.ui.appwidget.popup.view.ChannelView;

import java.util.List;
import java.util.Map;

/**
 * 功能：频道；
 *
 * @author:gengqiyun
 * @date: 2016/9/6
 */
public class ChannelPresenterImpl implements ChannelPresenter, ChannelLoadListener
{
    private ChannelModel model;
    private ChannelView view;

    public ChannelPresenterImpl(ChannelView view)
    {
        this.view = view;
        model = new ChannelModelImpl(this);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        model.getChannels(paramMap);
    }

    @Override
    public void onListenSuccess(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList)
    {
        view.onGetChannelSucess(displayChannelList,hideChannelList);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetChannelFail(msg);
    }

}
