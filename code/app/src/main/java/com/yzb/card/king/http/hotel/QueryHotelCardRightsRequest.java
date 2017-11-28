package com.yzb.card.king.http.hotel;

import com.yzb.card.king.bean.hotel.HotelRoomParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：查询酒店卡权益列表
 * 作  者：Li Yubing
 * 日  期：2017/8/8
 * 描  述：
 */
public class QueryHotelCardRightsRequest extends BaseRequest {

    private String serverName = CardConstant.CARD_APP_QUERYHOTELCARDRIGHTS;

    Map<String, Object> param = new HashMap<String, Object>();

    public QueryHotelCardRightsRequest(long hotelId, String arrDate)
    {
        // 公共参数
        param.put("hotelId", hotelId);
        param.put("arrDate",arrDate);

    }

    public void setFlashSaleServerName(){

        serverName = CardConstant.CARD_QUERYHOTELFLASHSALE;
    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}


