package com.yzb.card.king.http.hotel;

import com.yzb.card.king.bean.hotel.HotelRoomParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：查询房间套餐价格列表
 * 作  者：Li Yubing
 * 日  期：2017/8/14
 * 描  述：
 */
public class HotelRoomsPolicysPriceRequest extends BaseRequest {

    private String serverName = CardConstant.HOTEL_QUERYROOMSPOLICYSPRICE;

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     *
     *查询房间套餐价格列表请求构造器
     * @param policysId  套餐id
     * @param arrDate  入住日期
     * @param depDate  离店日期
     * @param goodsQuantity 订购数量
     */
    public HotelRoomsPolicysPriceRequest(String policysId, String arrDate,String depDate,int goodsQuantity)
    {
        serverName = CardConstant.HOTEL_QUERYROOMSPOLICYSPRICE;
        // 公共参数
        param.put("policysId",policysId);
        param.put("arrDate", arrDate);
        param.put("depDate", depDate);
        param.put("goodsQuantity", goodsQuantity);
    }

    /**
     * 查询房间套餐剩余数量请求构造器
     * @param policysId
     * @param arrDate
     */
    public HotelRoomsPolicysPriceRequest(String policysId, String arrDate,int interfaceType )
    {
        if( interfaceType == 1){

            serverName = CardConstant.APP_SELECTROOMSPOLICYSQUANTITY;

        }else  if( interfaceType == 2){

            serverName = CardConstant.APP_QUERYROOMSRETENTIONTIME;
        }

        // 公共参数
        param.put("policysId",policysId);
        param.put("arrDate", arrDate);
    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams("", serverName, AppConstant.UUID, param), callBack);
    }
}
