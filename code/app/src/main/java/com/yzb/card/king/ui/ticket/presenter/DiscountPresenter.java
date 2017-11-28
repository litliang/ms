package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.ui.app.interfaces.DiscountListener;
import com.yzb.card.king.ui.ticket.model.impl.DiscountModel;
import com.yzb.card.king.ui.ticket.view.DiscountView;

import java.util.Map;

/**
 * 功能：机票优惠券列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/12
 */
public class DiscountPresenter implements DiscountListener
{
    private DiscountModel model;
    private DiscountView view;

    public DiscountPresenter(DiscountView view)
    {
        this.view = view;
        model = new DiscountModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.commit(paramMap);
    }

    @Override
    public void onListenSuccess(String req_flag, Object data)
    {
        if (data != null)
        {
            view.onGetDiscountSucess(req_flag, data);
        }
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetDiscountFail(msg);
    }
}
