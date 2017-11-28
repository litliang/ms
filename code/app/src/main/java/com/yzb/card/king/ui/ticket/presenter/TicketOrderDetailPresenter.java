package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.TicketOrderDetailModel;
import com.yzb.card.king.ui.ticket.view.TicketOrderDetailView;

import java.util.Map;

/**
 * 功能：退票订单详情；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class TicketOrderDetailPresenter implements BaseMultiLoadListener
{
    private TicketOrderDetailModel model;
    private TicketOrderDetailView view;

    public TicketOrderDetailPresenter(TicketOrderDetailView view)
    {
        this.view = view;
        model = new TicketOrderDetailModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetOrderDetailSucess((TicketOrderDetBean) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetOrderDetailFail(msg);
    }
}
