package com.yzb.card.king.ui.hotel.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.hotel.HotelDetailServiceBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.hotel.HotelCollectionRequest;
import com.yzb.card.king.http.hotel.HotelDetailInfoRequest;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.bean.hotel.Hotel;

/**
 * 类  名：酒店详情dao的实现层
 * 作  者：Li JianQiang
 * 日  期：2016/8/25
 * 描  述：
 */
public class HotelDeatilDaoImpl implements IHotelDetail {

    private DataCallBack dataCallBack;

    public HotelDeatilDaoImpl(DataCallBack dataCallBack)
    {
        this.dataCallBack = dataCallBack;
    }

    /**
     * 发送查询酒店服务器事件
     * @param hotelId
     */
    @Override
    public void sendSelectHotelServiceInfoRequestAction(String hotelId){

        new HotelDetailInfoRequest(hotelId).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {
            }

            @Override
            public void onSuccess(Object o)
            {
                HotelDetailServiceBean hd= JSON.parseObject(String.valueOf(o),HotelDetailServiceBean.class);

                dataCallBack.requestSuccess(hd,IHotelDetail.HOTEL_SERVER_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,IHotelDetail.HOTEL_SERVER_CODE);
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
     * 获取酒店详情信息
     *
     * @param hotelId
     */
    @Override
    public void getHotelDetail(String hotelId,String arrDate)
    {
        new HotelDetailInfoRequest(hotelId,arrDate).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {
            }

            @Override
            public void onSuccess(Object o)
            {
                Hotel hd= JSON.parseObject(String.valueOf(o),Hotel.class);
                dataCallBack.requestSuccess(hd,IHotelDetail.HOTEL_DETAIL);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,IHotelDetail.HOTEL_DETAIL);
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
     * 收藏操作
     *
     * @param id
     * @param status
     */
    @Override
    public void collectHotel(String id, String status, String type, String category)
    {
        new HotelCollectionRequest(id, status, type, category).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {
            }

            @Override
            public void onSuccess(Object o)
            {
                dataCallBack.requestSuccess(o,IHotelDetail.HOTEL_COLLECT);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,IHotelDetail.HOTEL_COLLECT);
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
