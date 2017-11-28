package com.yzb.card.king.ui.gift.presenter;

import com.yzb.card.king.ui.gift.bean.FavorPayee;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.gift.modle.FavorPayeeModel;
import com.yzb.card.king.ui.bonus.view.FavorPayeeView;

import java.util.List;
import java.util.Map;

/**
 * 功能：礼品卡常用收款人
 *
 * @author:gengqiyun
 * @date: 2017/2/17
 */
public class FavorPayeePresenter implements BaseMultiLoadListener
{
    private FavorPayeeModel model;
    private FavorPayeeView view;

    public FavorPayeePresenter(FavorPayeeView view)
    {
        this.view = view;
        model = new FavorPayeeModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetFavorPayeeSuc(event_tag, (List<FavorPayee>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetFavorPayeeFail(msg);
    }
}
