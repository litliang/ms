package com.yzb.card.king.ui.ticket.presenter;


import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.ActivityDeductionModel;
import com.yzb.card.king.ui.ticket.view.ActivityDeductionView;

import java.util.Map;

/**
 * 功能：付款成功后，更新订单状态
 *
 * @author:gengqiyun
 * @date: 2016/10/15
 */
public class ActivityDeductionPresenter implements  BaseMultiLoadListener
{
    private ActivityDeductionModel model;
    private ActivityDeductionView view;

    public ActivityDeductionPresenter(ActivityDeductionView view)
    {
        this.view = view;
        model = new ActivityDeductionModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onUpdateOrderStatusSucess(String.valueOf(data));
    }

    @Override
    public void onListenError(String msg)
    {
        view.onUpdateOrderStatusFail(msg);
    }
}
