package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.ticket.model.IPlaneMyConcern;
import com.yzb.card.king.ui.ticket.model.impl.PlaneMyConcernDaoImpl;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/28
 * 描  述：
 */
public class PlaneMyConcernPresenter implements DataCallBack {

    private BaseViewLayerInterface view;

    private IPlaneMyConcern planeMyConcern;

    public PlaneMyConcernPresenter(BaseViewLayerInterface view){
        this.view=view;
        this.planeMyConcern=new PlaneMyConcernDaoImpl(this);
    }


    public void getList(Map<String,Object> map){
        planeMyConcern.getList(map);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        if(type == IPlaneMyConcern.QUERT_CODE){
            view.callSuccessViewLogic(o,IPlaneMyConcern.QUERT_CODE);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if(type == IPlaneMyConcern.QUERT_CODE){
            view.callFailedViewLogic(o,IPlaneMyConcern.QUERT_CODE);
        }
    }
}
