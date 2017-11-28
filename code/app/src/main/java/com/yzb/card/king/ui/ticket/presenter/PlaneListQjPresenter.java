package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.ticket.model.IPlaneListQj;
import com.yzb.card.king.ui.ticket.model.impl.PlaneListQjDaoImpl;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/14
 * 描  述：
 */
public class PlaneListQjPresenter implements DataCallBack {
    private BaseViewLayerInterface view;

    private IPlaneListQj planeListQj;


    public PlaneListQjPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        this.planeListQj = new PlaneListQjDaoImpl(this);
    }

    /**
     * 根据起降地查询列表信息
     *
     * @param map
     */
    public void getListInfo(Map<String, Object> map)
    {
        planeListQj.getListInfo(map);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == IPlaneListQj.GET_LIST_INFO)
        {
            view.callSuccessViewLogic(o, IPlaneListQj.GET_LIST_INFO);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (type == IPlaneListQj.GET_LIST_INFO)
        {
            view.callFailedViewLogic(o, IPlaneListQj.GET_LIST_INFO);
        }
    }
}
