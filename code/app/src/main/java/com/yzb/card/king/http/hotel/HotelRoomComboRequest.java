package com.yzb.card.king.http.hotel;

import com.yzb.card.king.bean.hotel.HotelRoomParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：酒店房间套餐
 * 作  者：Li Yubing
 * 日  期：2017/8/5
 * 描  述：
 */
public class HotelRoomComboRequest extends BaseRequest {

    private String serverName = CardConstant.CARD_APP_HOTELROOMPOLICYS;

    Map<String, Object> param = new HashMap<String, Object>();

    public HotelRoomComboRequest(HotelRoomParam hotelRoomParam)
    {
        // 公共参数
        param.put("roomsId", hotelRoomParam.getRoomsId());

        param.put("arrDate", hotelRoomParam.getArrDate());

        if( hotelRoomParam.directStatus!=-1){
            param.put("directStatus", hotelRoomParam.directStatus);
        }

        if(hotelRoomParam.paymentType!=-1){
            param.put("paymentType", hotelRoomParam.paymentType);
        }
        if(hotelRoomParam.mealType!=-1){

            param.put("mealType", hotelRoomParam.mealType);
        }

        if(hotelRoomParam.roomsType!=-1){
            param.put("roomsType", hotelRoomParam.roomsType);
        }


    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}

