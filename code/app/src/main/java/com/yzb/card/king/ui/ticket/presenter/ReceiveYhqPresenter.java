package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.ReceiveYhqModel;
import com.yzb.card.king.ui.ticket.view.ReceiveYhqView;

import java.util.Map;

/**
 * 功能：领取优惠券；
 *
 * @author:gengqiyun
 * @date: 2016/10/13
 */
public class ReceiveYhqPresenter implements BaseMultiLoadListener
{
    private ReceiveYhqModel model;
    private ReceiveYhqView view;

    public ReceiveYhqPresenter(ReceiveYhqView view)
    {
        this.view = view;
        model = new ReceiveYhqModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onReceiveYhqSucess(String.valueOf(data));
    }

    @Override
    public void onListenError(String msg)
    {
        view.onReceiveYhqFail(msg);
    }
}
