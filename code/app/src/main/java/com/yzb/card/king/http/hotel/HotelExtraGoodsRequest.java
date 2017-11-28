package com.yzb.card.king.http.hotel;

import com.yzb.card.king.bean.hotel.HotelRoomParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：产品信息和套餐请求类
 * 作  者：Li Yubing
 * 日  期：2017/8/5
 * 描  述：2餐厅、3酒吧、5SPA、6大堂吧、9健身房、10游泳池
 */
public class HotelExtraGoodsRequest extends BaseRequest {

    private String serverName = CardConstant.CARD_APP_QUERYHOTELOTHERGOODS;

    Map<String, Object> param = new HashMap<String, Object>();

    public HotelExtraGoodsRequest(HotelRoomParam hotelRoomParam)
    {
        serverName = CardConstant.CARD_APP_QUERYHOTELOTHERGOODS;
        // 公共参数
        param.put("hotelId", hotelRoomParam.getHotelId());
        param.put("arrDate", hotelRoomParam.getArrDate());
        param.put("goodsType", hotelRoomParam.getGoodsType());


    }

    public HotelExtraGoodsRequest(HotelRoomParam hotelRoomParam,int flag)
    {
        serverName = CardConstant.CARD_APP_QUERYHOTELOTHERGOODSPOLICYS;
        // 公共参数
        param.put("goodsId", hotelRoomParam.getGoodsId());
        param.put("arrDate", hotelRoomParam.getArrDate());
        param.put("goodsType", hotelRoomParam.getGoodsType());


    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}

