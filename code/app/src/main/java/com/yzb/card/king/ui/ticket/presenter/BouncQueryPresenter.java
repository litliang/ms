package com.yzb.card.king.ui.ticket.presenter;


import com.yzb.card.king.bean.ticket.BouncQueryBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.BouncQueryModel;
import com.yzb.card.king.ui.ticket.view.BouncQueryView;

import java.util.Map;

/**
 * 功能：退票查询；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class BouncQueryPresenter implements BaseMultiLoadListener
{
    private BouncQueryModel model;
    private BouncQueryView view;

    public BouncQueryPresenter(BouncQueryView view)
    {
        this.view = view;
        model = new BouncQueryModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onBouncQuerySucess((BouncQueryBean) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onBouncQueryFail(msg);
    }
}
