package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.ui.app.model.H5Model;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：H5；
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class H5Presenter implements  BaseMultiLoadListener
{
    private H5Model model;
    private AppBaseView view;

    public H5Presenter(AppBaseView view)
    {
        this.view = view;
        model = new H5Model(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.getH5(paramMap);
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
