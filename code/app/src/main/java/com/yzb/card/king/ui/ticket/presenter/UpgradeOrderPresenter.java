package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.bean.ticket.UpgradeOrderBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.UpgradeOrderModel;
import com.yzb.card.king.ui.ticket.view.UpgradeOrderView;

import java.util.Map;

/**
 * 功能：查询可改签的订单详细；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class UpgradeOrderPresenter implements BaseMultiLoadListener
{
    private UpgradeOrderModel model;
    private UpgradeOrderView view;

    public UpgradeOrderPresenter(UpgradeOrderView view)
    {
        this.view = view;
        model = new UpgradeOrderModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetUpgradeOrderSucess((UpgradeOrderBean) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetUpgradeOrderFail(msg);
    }
}
