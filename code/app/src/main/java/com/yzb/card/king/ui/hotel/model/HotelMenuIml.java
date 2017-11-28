package com.yzb.card.king.ui.hotel.model;

import com.yzb.card.king.bean.hotel.HotelRoomParam;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.hotel.HotelRoomRequest;
import com.yzb.card.king.http.hotel.QueryHotelGoodsMenuRequest;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/16
 * 描  述：
 */
public class HotelMenuIml {

    private DataCallBack dataCallBack;


    public HotelMenuIml(DataCallBack dataCallBack){

        this.dataCallBack = dataCallBack;

    }

    /**
     * 酒店商品菜单事件
     * @param goodsType
     * @param goodsId
     * @param roomProductCode
     */
    public void hotelGoodsMenuAction(String  goodsType,String goodsId, final int roomProductCode)
    {

        QueryHotelGoodsMenuRequest request = new QueryHotelGoodsMenuRequest(goodsType,goodsId);

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
     * 酒店商品套餐菜单事件
     * @param goodsType
     * @param policysId
     * @param roomProductCode
     */
    public void hotelGoodsPolicyMenuAction(String  goodsType,String policysId, final int roomProductCode)
    {

        QueryHotelGoodsMenuRequest request = new QueryHotelGoodsMenuRequest(goodsType,policysId,false);

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
