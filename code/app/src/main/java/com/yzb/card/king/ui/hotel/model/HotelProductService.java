package com.yzb.card.king.ui.hotel.model;

import com.yzb.card.king.bean.hotel.HotelRoomParam;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.hotel.HotelExtraGoodsRequest;
import com.yzb.card.king.http.hotel.HotelRoomComboRequest;
import com.yzb.card.king.http.hotel.HotelRoomRequest;
import com.yzb.card.king.http.hotel.QueryHotelCardRightsRequest;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/4
 * 描  述：
 */
public class HotelProductService {

    private DataCallBack dataCallBack;


    public HotelProductService(DataCallBack dataCallBack){

        this.dataCallBack = dataCallBack;

    }

    /**
     * 酒店房间信息请求事件
     * @param param
     * @param roomProductCode
     */
    public void hotelRoomRequestAction(HotelRoomParam param, final int roomProductCode)
    {

        HotelRoomRequest request = new HotelRoomRequest(param);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                dataCallBack.requestSuccess(o,roomProductCode);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,roomProductCode);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });

    }

    /**
     * 房间套餐请求事件
     * @param param
     * @param roomProductCode
     */
    public void roomComboRequestAction(HotelRoomParam param, final int roomProductCode)
    {

        HotelRoomComboRequest request = new HotelRoomComboRequest(param);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                dataCallBack.requestSuccess(o,roomProductCode);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,roomProductCode);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });

    }

    /**
     * 酒店其它商品事件
     * @param param
     * @param roomProductCode
     */
    public void hotelOtherGoodsRequestAction(HotelRoomParam param, final int roomProductCode)
    {

        HotelExtraGoodsRequest request = new HotelExtraGoodsRequest(param);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                dataCallBack.requestSuccess(o,roomProductCode);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,roomProductCode);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });

    }

    /**
     * 酒店其它商品事件
     * @param param
     * @param roomProductCode
     */
    public void hotelOtherGoodsComboRequestAction(HotelRoomParam param, final int roomProductCode)
    {

        HotelExtraGoodsRequest request = new HotelExtraGoodsRequest(param,1);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                dataCallBack.requestSuccess(o,roomProductCode);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,roomProductCode);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });

    }

    /**
     * 查询酒店卡权益列表
     * @param hotelId
     * @param arrDate
     * @param roomProductCode
     */
    public void queryHotelCardRightsAction(long hotelId, String arrDate, final int roomProductCode)
    {

        QueryHotelCardRightsRequest request = new QueryHotelCardRightsRequest(hotelId,arrDate);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                dataCallBack.requestSuccess(o,roomProductCode);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,roomProductCode);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });

    }
    /**
     * 查询酒店限时抢购列表
     * @param hotelId
     * @param arrDate
     * @param roomProductCode
     */
    public void queryHotelFlashSaleAction(long hotelId, String arrDate, final int roomProductCode)
    {

        QueryHotelCardRightsRequest request = new QueryHotelCardRightsRequest(hotelId,arrDate);

        request.setFlashSaleServerName();

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                dataCallBack.requestSuccess(o,roomProductCode);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,roomProductCode);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });

    }
}
