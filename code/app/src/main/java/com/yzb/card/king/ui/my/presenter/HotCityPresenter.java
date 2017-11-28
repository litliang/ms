package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.model.HotCityModel;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/23
 * 描  述：
 */
public class HotCityPresenter implements DataCallBack{

    private BaseViewLayerInterface baseViewLayerInterface;

    private HotCityModel hotCityModel;


    public  HotCityPresenter(BaseViewLayerInterface baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;

        hotCityModel = new HotCityModel(this);

    }

    /**
     * 发送热门城市请求
     * @param regionId
     * @param industryId
     * @param type
     */
    public void sendHotCityRequest(String regionId,String industryId,String type){

        hotCityModel.queryHotelCityAction(regionId,industryId,type);
    }

    public void sendCityStatisticsRequest(String addrId,String industryId){

        hotCityModel.statisticsCityInfoAction(addrId,industryId);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {

        baseViewLayerInterface.callSuccessViewLogic(o,type);
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {

        baseViewLayerInterface.callFailedViewLogic(o,type);

    }
}
