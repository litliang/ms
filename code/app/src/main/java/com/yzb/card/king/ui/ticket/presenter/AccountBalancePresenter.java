package com.yzb.card.king.ui.ticket.presenter;

import android.app.Activity;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.AccountBalanceBean;
import com.yzb.card.king.ui.ticket.model.impl.AccountBalanceModel;
import com.yzb.card.king.ui.ticket.view.AccountBalanceView;

import java.util.Map;

/**
 * 功能：查询红包账号和礼品卡余额；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class AccountBalancePresenter implements  BaseMultiLoadListener
{
    private final Activity context;
    private AccountBalanceModel model;
    private AccountBalanceView view;

    public AccountBalancePresenter(Activity context, AccountBalanceView view)
    {
        this.view = view;
        this.context = context;
        model = new AccountBalanceModel(context, this);
    }

    public void loadData(Map<String, String> paramMap)
    {
        model.loadData(context, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetAccountBalanceSucess(data != null ? (AccountBalanceBean) data : null);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetAccountBalanceFail(msg);
    }
}
