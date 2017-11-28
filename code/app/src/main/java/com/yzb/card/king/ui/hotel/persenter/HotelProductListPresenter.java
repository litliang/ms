package com.yzb.card.king.ui.hotel.persenter;

import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.model.HotelProductListImpl;

/**
 * 酒店产品观察者
 * Created by 玉兵 on 2017/7/31.
 */

public class HotelProductListPresenter implements DataCallBack {

    public static final int CARDRIGHTSLIST_CODE = 7100;

    public static final int FLASHSALELIST_CODE = 7101;

    public static final int HOTELPRODUCTLIST_CODE = 7102;


    private BaseViewLayerInterface baseViewLayerInterface;

    private HotelProductListImpl impl;

    public  HotelProductListPresenter(BaseViewLayerInterface baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;

        impl = new HotelProductListImpl(this);

    }

    /**
     * 发送酒店产品地图信息请求
     * @param hotelProductListParam
     */
    public void sendHotelMapProductRequest(HotelProductListParam hotelProductListParam){

        impl.sendHotelProductMapAction(hotelProductListParam,-4);
    }

    /**
     * 发送酒店产品列表请求
     * @param hotelProductListParam
     */
    public void sendHotelProductListRequest(HotelProductListParam hotelProductListParam){

        impl.sendHotelProductListRequest(hotelProductListParam,HOTELPRODUCTLIST_CODE);

    }

    /**
     * 发送今日甩房请求
     * @param hotelProductListParam
     */
    public void sendTodayLeftRoomListRequest(HotelProductListParam hotelProductListParam){

        impl.sendLeftHotelRoomRequest(hotelProductListParam,-2);

    }

    /**
     * 发送限时抢购请求
     * @param hotelProductListParam
     */
    public void sendFlashSaleListRequest(HotelProductListParam hotelProductListParam){

        impl.queryFlashSaleAction(hotelProductListParam,FLASHSALELIST_CODE);
    }

    /**
     * 发送卡权益请求
     * @param hotelProductListParam
     */
    public void sendQueryGiftListRequest(HotelProductListParam hotelProductListParam){

        impl.queryGiftListAction(hotelProductListParam,CARDRIGHTSLIST_CODE);
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
