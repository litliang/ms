package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.ticket.model.ITicketHome;
import com.yzb.card.king.ui.ticket.model.impl.TicketHomeDaoImpl;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/13
 * 描  述：
 */
public class TickeHomePresenter implements DataCallBack {
    private BaseViewLayerInterface view;

    private ITicketHome ticketHome;

    public TickeHomePresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        this.ticketHome = new TicketHomeDaoImpl(this);
    }

    /**
     * 获取舱位信息
     *
     * @param map
     */
    public void getCangWei(Map<String, Object> map)
    {
        ticketHome.getCangWei(map);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == ITicketHome.CANGWEI_TYPE)
        {
            view.callSuccessViewLogic(o, ITicketHome.CANGWEI_TYPE);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (type == ITicketHome.CANGWEI_TYPE)
        {
            view.callFailedViewLogic(o, ITicketHome.CANGWEI_TYPE);
        }
    }
}
