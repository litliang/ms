package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.ui.app.model.GetBankBinModel;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：获得银行bin码
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public class GetBankBinPresenter implements BaseMultiLoadListener
{
    private GetBankBinModel model;
    private AppBaseView view;

    public GetBankBinPresenter(AppBaseView view)
    {
        this.view = view;
        model = new GetBankBinModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onViewCallBackSucess(data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onViewCallBackFail(msg);
    }
}
