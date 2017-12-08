package com.yzb.card.king.ui.hotel.persenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.hotel.FilterListRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.model.FilterListModel;

/**
 * Created by 玉兵 on 2017/7/28.
 *
 * 过滤项列表观察者,酒店搜索关键字
 */

public class FilterListPersenter implements DataCallBack {

    private BaseViewLayerInterface  baseViewLayerInterface;

    private FilterListModel filterListModel;

    public FilterListPersenter(BaseViewLayerInterface  baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;

        filterListModel =  new FilterListModel(this);

    }

    /**
     * 酒店搜索页面的默认选项
     */

    public void sendHotelSearchDefaultFilter(String addrId){

        filterListModel.sendFilterListRequest(AppConstant.hotel_id,"1","1",addrId);

    }

    /**
     * 酒店筛选项请求
     */
    public void sendHotelScreenFilterRequest(String industryFun,String addrId){
        filterListModel.sendFilterListRequest(AppConstant.hotel_id,industryFun,"2",addrId);
    }


    /**
     * 发送区域位置请求
     */
    public void sendDistrictPositionRequest(String addrId){

        filterListModel.sendFilterListRequest(AppConstant.hotel_id,"1","3",addrId);
    }

    /**
     * 酒店品牌
     * @param industryFun
     */
    public void sendHotelBrandRequest(String industryFun,String addrId){

        filterListModel.sendFilterListRequest(AppConstant.hotel_id,industryFun,"4",addrId);
    }

    /**
     * 列表附近筛选信息
     * @param addrId
     */
    public void sendCouponFilterData(String addrId)
    {
        filterListModel.sendFilterListRequest("1029","1","2",addrId);
    }

    /**
     * 发起酒店关键字搜索
     * @param cityName
     * @param keyWord
     */
    public  void sendHotelKeywordSearchAction(String cityName,String keyWord){

        filterListModel.sendHotelKeywordSearchRequest(cityName,keyWord);
    }

    @Override
    public void requestSuccess(Object o, int type) {

        baseViewLayerInterface.callSuccessViewLogic(o,type);
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {

        baseViewLayerInterface.callFailedViewLogic(o,type);
    }


}
