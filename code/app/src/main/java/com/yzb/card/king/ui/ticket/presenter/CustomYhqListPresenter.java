package com.yzb.card.king.ui.ticket.presenter;


import com.yzb.card.king.bean.ticket.CustCouponBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.CustomYhqListModel;
import com.yzb.card.king.ui.ticket.view.CustomYhqListView;

import java.util.List;
import java.util.Map;

/**
 * 功能：客户优惠券列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/15
 */
public class CustomYhqListPresenter implements BaseMultiLoadListener
{
    private CustomYhqListModel model;
    private CustomYhqListView view;

    public CustomYhqListPresenter(CustomYhqListView view)
    {
        this.view = view;
        model = new CustomYhqListModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetCustomYhqListSucess(data != null ? (List<CustCouponBean>) data : null);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetCustomYhqListFail(msg);
    }
}
