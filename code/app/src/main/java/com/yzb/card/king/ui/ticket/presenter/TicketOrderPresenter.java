package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.ticket.model.IPlaneTicketOrderSingle;
import com.yzb.card.king.ui.ticket.model.impl.PlaneTicketOrderSingleDaoImpl;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/5
 * 描  述：
 */
public class TicketOrderPresenter implements DataCallBack {

    private IPlaneTicketOrderSingle iPlaneTicketOrderSingle;

    private BaseViewLayerInterface planeTicketOrderSingleView;

    public TicketOrderPresenter(BaseViewLayerInterface planeTicketOrderSingleView)
    {
        this.planeTicketOrderSingleView = planeTicketOrderSingleView;
        this.iPlaneTicketOrderSingle = new PlaneTicketOrderSingleDaoImpl(this);
    }

    /**
     * 保险列表
     * @param params
     */
    public void getBxList(Map<String, Object> params)
    {
        iPlaneTicketOrderSingle.getBxList(params);
    }

    /**
     * 退改签规则
     */
    public void getRoleTicket()
    {
        iPlaneTicketOrderSingle.getRoleTicket();
    }

    /**
     * 默认收货地址
     */
    public void getDefaultAddress()
    {
        iPlaneTicketOrderSingle.getDefaultAddress();
    }

    /**
     * 默认发票抬头
     */
    public void getDefaultDebit()
    {
        iPlaneTicketOrderSingle.getDefaultDebit();
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == IPlaneTicketOrderSingle.BXLIST_CODE)
        {
            planeTicketOrderSingleView.callSuccessViewLogic(o, IPlaneTicketOrderSingle.BXLIST_CODE);
        } else if (type == IPlaneTicketOrderSingle.ROLETICKET_CODE)
        {
            planeTicketOrderSingleView.callSuccessViewLogic(o, IPlaneTicketOrderSingle.ROLETICKET_CODE);
        } else if (type == IPlaneTicketOrderSingle.DEFAULTADDRESS_CODE)
        {
            planeTicketOrderSingleView.callSuccessViewLogic(o, IPlaneTicketOrderSingle.DEFAULTADDRESS_CODE);
        } else if (type == IPlaneTicketOrderSingle.DEFAULTDEBIT_CODE)
        {
            planeTicketOrderSingleView.callSuccessViewLogic(o, IPlaneTicketOrderSingle.DEFAULTDEBIT_CODE);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (type == IPlaneTicketOrderSingle.BXLIST_CODE)
        {
            planeTicketOrderSingleView.callFailedViewLogic(o, IPlaneTicketOrderSingle.BXLIST_CODE);
        } else if (type == IPlaneTicketOrderSingle.ROLETICKET_CODE)
        {
            planeTicketOrderSingleView.callFailedViewLogic(o, IPlaneTicketOrderSingle.ROLETICKET_CODE);
        } else if (type == IPlaneTicketOrderSingle.DEFAULTADDRESS_CODE)
        {
            planeTicketOrderSingleView.callFailedViewLogic(o, IPlaneTicketOrderSingle.DEFAULTADDRESS_CODE);

        } else if (type == IPlaneTicketOrderSingle.DEFAULTDEBIT_CODE)
        {
            planeTicketOrderSingleView.callFailedViewLogic(o, IPlaneTicketOrderSingle.DEFAULTDEBIT_CODE);
        }
    }
}
