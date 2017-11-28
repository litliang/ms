package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.ticket.model.IPlaneDtDetail;
import com.yzb.card.king.ui.ticket.model.impl.PlaneDtDetailDaoImpl;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/11
 * 描  述：
 */
public class PlaneDtDetailPresenter implements DataCallBack {

    private BaseViewLayerInterface view;

    private IPlaneDtDetail iPlaneDtDetail;

    public PlaneDtDetailPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        this.iPlaneDtDetail = new PlaneDtDetailDaoImpl(this);
    }




    public void getInfo(Map<String, Object> map)
    {
        iPlaneDtDetail.getDateInfo(map);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == IPlaneDtDetail.CONCERN_CODE)
        {
            view.callSuccessViewLogic(o, IPlaneDtDetail.CONCERN_CODE);
        } else if (type == IPlaneDtDetail.SELECT_INFO)
        {
            view.callSuccessViewLogic(o, IPlaneDtDetail.SELECT_INFO);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (type == IPlaneDtDetail.CONCERN_CODE)
        {
            view.callFailedViewLogic(o, IPlaneDtDetail.CONCERN_CODE);
        } else if (type == IPlaneDtDetail.SELECT_INFO)
        {
            view.callFailedViewLogic(o, IPlaneDtDetail.SELECT_INFO);
        }
    }
}
