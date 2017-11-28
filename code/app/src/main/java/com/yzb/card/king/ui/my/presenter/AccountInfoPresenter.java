package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.model.AccountInfoModel;

import java.util.Map;

/**
 * 功能：查询账户信息；
 *
 * @author:gengqiyun
 * @date: 2016/12/28
 */
public class AccountInfoPresenter implements BaseMultiLoadListener
{
    private AccountInfoModel model;
    private BaseViewLayerInterface view;

    public AccountInfoPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new AccountInfoModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic( data,1);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,1);
    }
}
