package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.bean.ticket.TicketAmountBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.FlightAmountModel;
import com.yzb.card.king.ui.ticket.view.FlightAmountView;

import java.util.List;
import java.util.Map;

/**
 * 功能：机票详情查询；
 *
 * @author:gengqiyun
 * @date: 2016/11/29
 */
public class FlightAmountPresenter implements BaseMultiLoadListener
{
    private FlightAmountModel model;
    private FlightAmountView view;

    public FlightAmountPresenter(FlightAmountView view)
    {
        this.view = view;
        model = new FlightAmountModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetFlightAmountSucess(event_tag, (List<TicketAmountBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetFlightAmountFail(msg);
    }
}
