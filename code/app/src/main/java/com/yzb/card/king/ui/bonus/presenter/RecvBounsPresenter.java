package com.yzb.card.king.ui.bonus.presenter;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.bonus.model.RecvBounsModel;
import com.yzb.card.king.ui.bonus.view.RecvBounsView;

import java.util.Map;

/**
 * 功能：领取红包；
 *
 * @author:gengqiyun
 * @date: 2016/12/30
 */
public class RecvBounsPresenter implements BaseMultiLoadListener
{
    private RecvBounsModel model;
    private RecvBounsView view;

    public RecvBounsPresenter(RecvBounsView view)
    {
        this.view = view;
        model = new RecvBounsModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onRecvBounsSuc(String.valueOf(data));
    }

    @Override
    public void onListenError(String msg)
    {
        view.onRecvBounsFail(msg);
    }
}
