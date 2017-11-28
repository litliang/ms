package com.yzb.card.king.ui.bonus.presenter;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.bonus.model.BonusOrderCreateModel;

import java.util.Map;

/**
 * 功能：创建红包订单
 *
 * @author:gengqiyun
 * @date: 2016/12/30
 */
public class BonusOrderCreatePresenter implements BaseMultiLoadListener
{
    private BonusOrderCreateModel model;

    private BaseViewLayerInterface view;

    public BonusOrderCreatePresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new BonusOrderCreateModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
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
