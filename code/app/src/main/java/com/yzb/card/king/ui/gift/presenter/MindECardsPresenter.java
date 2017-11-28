package com.yzb.card.king.ui.gift.presenter;

import com.yzb.card.king.ui.gift.bean.ECardBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.gift.modle.MindECardsModel;
import com.yzb.card.king.ui.gift.view.MindECardsView;

import java.util.List;
import java.util.Map;

/**
 * 功能：心意e卡列表；
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public class MindECardsPresenter implements BaseMultiLoadListener
{
    private MindECardsModel model;
    private MindECardsView view;

    public MindECardsPresenter(MindECardsView view)
    {
        this.view = view;
        model = new MindECardsModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetMindECardsSuc(event_tag, (List<ECardBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetMindECardsFail(msg);
    }
}
