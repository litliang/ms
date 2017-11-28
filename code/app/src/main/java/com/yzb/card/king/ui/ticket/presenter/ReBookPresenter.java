package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.bean.ticket.OrderIdBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.ReBookModel;
import com.yzb.card.king.ui.ticket.view.ReBookView;

import java.util.Map;

/**
 * 功能：改签确认；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class ReBookPresenter implements BaseMultiLoadListener
{
    private ReBookModel model;
    private ReBookView view;

    public ReBookPresenter(ReBookView view)
    {
        this.view = view;
        model = new ReBookModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onReBookSucess((OrderIdBean) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onReBookFail(msg);
    }
}
