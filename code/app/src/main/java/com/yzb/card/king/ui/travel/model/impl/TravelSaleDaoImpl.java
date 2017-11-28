package com.yzb.card.king.ui.travel.model.impl;

import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.travel.model.ITravelSale;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/22
 * 描  述：
 */
public class TravelSaleDaoImpl implements ITravelSale {
    private DataCallBack dataCallBack;

    public TravelSaleDaoImpl(DataCallBack dataCallBack)
    {
        this.dataCallBack = dataCallBack;
    }

    /**
     * 获取特卖会信息
     *
     * @param map
     * @param service
     */
    @Override
    public void getTravelSaleInfo(Map<String, Object> map, String service,int code)
    {
        CommonServerRequest csr = new CommonServerRequest();
        csr.sendReuqest(map, service, code);
        csr.setOnDataLoadFinish(dataCallBack);
    }

}
