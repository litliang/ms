package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.ui.ticket.model.ISingleList;
import com.yzb.card.king.ui.ticket.model.impl.SingleListImpl;
import com.yzb.card.king.ui.ticket.view.SingleListView;

import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/5 18:17
 */
public class SingleListPresenter
{
    private SingleListView view;
    private ISingleList model;

    public SingleListPresenter(SingleListView singleListView)
    {
        this.view = singleListView;
        this.model = new SingleListImpl(new PriceLoaderCallBack());
    }

    public void loadHeaderPrice(Map<String, Object> param)
    {
        model.loadHeaderPrice(param);
    }

    public void saveList(List<PlaneTicket> dataList)
    {
        model.saveList(dataList);
    }

    private class PriceLoaderCallBack implements RequestCallback
    {

        @Override
        public void onSuccess(String prePrice, String todayPrice, String nextPrice)
        {
            view.initHeaderPrice(prePrice,todayPrice,nextPrice);
        }

        @Override
        public void onFail()
        {

        }
    }

    public interface RequestCallback
    {
        void onSuccess(String prePrice, String todayPrice, String nextPrice);

        void onFail();
    }
}
