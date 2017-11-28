package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.DiscountIntegralBean;
import com.yzb.card.king.ui.ticket.model.impl.BankIntegralModel;
import com.yzb.card.king.ui.ticket.view.BankIntegralView;

import java.util.List;
import java.util.Map;

/**
 * 功能：机票红包列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/13
 */
public class BankIntegralPresenter implements  BaseMultiLoadListener
{
    private BankIntegralModel model;
    private BankIntegralView view;

    public BankIntegralPresenter(BankIntegralView view)
    {
        this.view = view;
        model = new BankIntegralModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetBankIntegralSucess(data != null ? (List<DiscountIntegralBean>) data : null);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetBankIntegralFail(msg);
    }
}
