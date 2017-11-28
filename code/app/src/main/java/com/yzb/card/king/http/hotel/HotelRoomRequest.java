package com.yzb.card.king.http.hotel;

import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.hotel.HotelRoomParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：酒店房间请求
 * 作  者：Li Yubing
 * 日  期：2017/8/4
 * 描  述：
 */
public class HotelRoomRequest extends BaseRequest {

    private String serverName =CardConstant.CARD_APP_HOTELROOMLIST;

    Map<String, Object> param = new HashMap<String, Object>();

    public HotelRoomRequest(HotelRoomParam hotelRoomParam)
    {
        // 公共参数
        param.put("hotelId", hotelRoomParam.getHotelId());
        param.put("arrDate", hotelRoomParam.getArrDate());
        param.put("depDate", hotelRoomParam.getDepDate());
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
