package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.ui.app.model.SettingUpdateModel;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;

import java.util.Map;

/**
 * 功能：发票抬头更新（删除或修改）
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class SettingUpdatePresenter implements  BaseMultiLoadListener
{
    private SettingUpdateModel model;
    private BaseViewLayerInterface view;

    public SettingUpdatePresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new SettingUpdateModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(data,1);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,1);
    }
}
