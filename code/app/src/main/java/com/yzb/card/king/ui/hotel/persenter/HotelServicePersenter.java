package com.yzb.card.king.ui.hotel.persenter;

import com.yzb.card.king.bean.hotel.HotelRoomParam;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.model.HotelProductService;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/4
 * 描  述：
 */
public class HotelServicePersenter implements DataCallBack {

    private BaseViewLayerInterface baseViewLayerInterface;

    private HotelProductService hotelProductService;

    public static final  int  HOTEL_ROOM_PRODUCT_CODE = 8001;

    public static final  int  HOTEL_ROOM_COMBO_CODE = 8002;

    public static final  int  HOTEL_OTHER_GOODS_CODE = 8003;

    public static final  int  HOTEL_OTHER_COMBO_CODE = 8004;

    public static final  int  HOTEL_CARD_RIGHTS_CODE = 8005;

    public static final  int  HOTEL_FLASH_SALE_CODE = 8006;

    public  HotelServicePersenter( BaseViewLayerInterface baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;


        this.hotelProductService = new HotelProductService(this);


    }

    /**
     * 酒店房间产品
     * @param param
     */
    public void sendRoomProductRequest(HotelRoomParam param){

        hotelProductService.hotelRoomRequestAction(param,HOTEL_ROOM_PRODUCT_CODE);

    }

    /**
     * 房间套餐
     * @param param
     */
    public void sendRoomComboRequest(HotelRoomParam param){

        hotelProductService.roomComboRequestAction(param,HOTEL_ROOM_COMBO_CODE);
    }
    /**
     * 酒店其它产品请求
     * @param param
     */
    public void sendHotelExtraProductRequest(HotelRoomParam param){

        hotelProductService.hotelOtherGoodsRequestAction(param,HOTEL_OTHER_GOODS_CODE);
    }

    /**
     * 酒店其它产品套餐请求
     * @param param
     */
    public void sendHotelExtraProductComeboRequest(HotelRoomParam param){

        hotelProductService.hotelOtherGoodsComboRequestAction(param,HOTEL_OTHER_COMBO_CODE);
    }

    /**
     * 查询酒店卡权益列表
     * @param hotelId
     * @param arrDate
     */
    public  void sendQueryHotelCardRights(long hotelId, String arrDate){

        hotelProductService.queryHotelCardRightsAction(hotelId,arrDate,HOTEL_CARD_RIGHTS_CODE);
    }

    /**
     * 查询酒店卡权益列表
     * @param hotelId
     * @param arrDate
     */
    public  void queryHotelFlashSaleAction(long hotelId, String arrDate){

        hotelProductService.queryHotelFlashSaleAction(hotelId,arrDate,HOTEL_FLASH_SALE_CODE);
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
