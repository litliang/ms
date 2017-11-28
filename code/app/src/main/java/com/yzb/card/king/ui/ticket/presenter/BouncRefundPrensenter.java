package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.BouncRefundModel;
import com.yzb.card.king.ui.ticket.view.BouncRefundView;

import java.util.Map;

/**
 * 功能：确认退票；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class BouncRefundPrensenter implements BaseMultiLoadListener
{
    private BouncRefundModel model;
    private BouncRefundView view;

    public BouncRefundPrensenter(BouncRefundView view)
    {
        this.view = view;
        model = new BouncRefundModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onBouncRefundSucess();
    }

    @Override
    public void onListenError(String msg)
    {
        view.onBouncRefundFail(msg);
    }
}
