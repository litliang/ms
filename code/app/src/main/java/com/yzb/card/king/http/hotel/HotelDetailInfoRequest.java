package com.yzb.card.king.http.hotel;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：酒店详情页
 * 作  者：Li JianQiang
 * 日  期：2016/8/22
 * 描  述：酒店详情请求、酒店特殊服务请求
 */
public class HotelDetailInfoRequest extends BaseRequest {

    private String serverName= CardConstant.CARD_APP_HOTELINFODETAILS;

    Map<String,Object> params=new HashMap<>();

    public HotelDetailInfoRequest(String hotelId,String arrDate){
        params.put("hotelId", hotelId);
        params.put("arrDate", arrDate);
    }

    public HotelDetailInfoRequest(String hotelId){
        serverName= CardConstant.CARD_APP_SELECTHOTELSERVICEINFO;
        params.put("hotelId", hotelId);
    }
    @Override
    public void sendRequest(HttpCallBackData callBack) {
        sendPostRequest(setParams(AppConstant
                .sessionId,serverName, AppConstant.UUID,params),callBack);
    }
}
