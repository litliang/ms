package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.GetPostFeeModel;
import com.yzb.card.king.ui.ticket.view.GetPostFeeView;

import java.util.Map;

/**
 * 功能：邮费列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class GetPostFeePresenter implements BaseMultiLoadListener
{
    private GetPostFeeModel model;
    private GetPostFeeView view;

    public GetPostFeePresenter(GetPostFeeView view)
    {
        this.view = view;
        model = new GetPostFeeModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetPostFeeSucess(data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetPostFeeFail(msg);
    }
}
