package com.yzb.card.king.ui.travel.presenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.travel.model.ITravelSale;
import com.yzb.card.king.ui.travel.model.impl.TravelSaleDaoImpl;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/22
 * 描  述：
 */
public class TravelSalePresenter implements DataCallBack {

    private BaseViewLayerInterface view;

    private ITravelSale dao;

    public TravelSalePresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        this.dao = new TravelSaleDaoImpl(this);

    }

    /**
     * 获取特卖会信息列表
     *
     * @param map
     * @param serverName
     */
    public void getSaleList(Map<String, Object> map, String serverName,int code)
    {
        dao.getTravelSaleInfo(map, serverName,code);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == ITravelSale.SALE_INFO)
        {
            view.callSuccessViewLogic(o, ITravelSale.SALE_INFO);
        } else if (type == ITravelSale.SALE_INFO_MORE)
        {
            view.callSuccessViewLogic(o, ITravelSale.SALE_INFO_MORE);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (type == ITravelSale.SALE_INFO)
        {
            view.callFailedViewLogic(o, ITravelSale.SALE_INFO);

        } else if (type == ITravelSale.SALE_INFO_MORE)
        {
            view.callFailedViewLogic(o, ITravelSale.SALE_INFO_MORE);
        }
    }
}
