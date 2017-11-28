package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.CreateFlightOrderModel;
import com.yzb.card.king.ui.ticket.view.CreateFlightOrderView;

import java.util.List;
import java.util.Map;

/**
 * 功能：生成机票订单；
 *
 * @author:gengqiyun
 * @date: 2016/9/30
 */
public class TicketOrderCreatePresenter implements BaseMultiLoadListener
{
    private CreateFlightOrderModel model;
    private CreateFlightOrderView view;

    public TicketOrderCreatePresenter(CreateFlightOrderView view)
    {
        this.view = view;
        model = new CreateFlightOrderModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.commit(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onCreateOrderSucess(data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onCreateOrderFail(msg);
    }

    /**
     * 获取商家id 多个用英文逗号分割；商家id可重复；
     *
     * @return
     */
    public String getStoreIds()
    {
        StringBuilder sb = new StringBuilder("");

        List<PlaneTicket> hbListData = view.getHbListData();
        if (hbListData != null)
        {
            PlaneTicket planeTicket;
            for (int i = 0; i < hbListData.size(); i++)
            {
                planeTicket = hbListData.get(i);
                if (i == hbListData.size() - 1)
                {
                    sb.append(planeTicket.getAgentId());
                } else
                {
                    sb.append(planeTicket.getAgentId() + ",");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取商家名称列表；
     *
     * @return 多个用英文逗号分割；
     */
    public String getStoreNames()
    {
        StringBuilder sb = new StringBuilder("");

        List<PlaneTicket> hbListData = view.getHbListData();
        if (hbListData != null)
        {
            PlaneTicket planeTicket;
            for (int i = 0; i < hbListData.size(); i++)
            {
                planeTicket = hbListData.get(i);
                //添加不重复的storeName；
                if (!sb.toString().contains(planeTicket.getStoreName()))
                {
                    if (i != 0)
                    {
                        sb.append(",");
                    }
                    sb.append(planeTicket.getStoreName());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取商品id 多个用英文逗号分割；商品id可重复；
     *
     * @return
     */
    public String getGoodIds()
    {
        StringBuilder sb = new StringBuilder("");

        List<PlaneTicket> hbListData = view.getHbListData();
        if (hbListData != null)
        {
            PlaneTicket planeTicket;
            for (int i = 0; i < hbListData.size(); i++)
            {
                planeTicket = hbListData.get(i);
                if (i == hbListData.size() - 1)
                {
                    sb.append(planeTicket.getFlightId());
                } else
                {
                    sb.append(planeTicket.getFlightId() + ",");
                }
            }
        }
        return sb.toString();
    }
}
