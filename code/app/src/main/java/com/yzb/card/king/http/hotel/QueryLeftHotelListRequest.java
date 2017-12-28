package com.yzb.card.king.http.hotel;

import android.text.TextUtils;

import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询今日甩房列表
 * Created by 玉兵 on 2017/7/31.
 */

public class QueryLeftHotelListRequest extends BaseRequest
{

    private String serverName = CardConstant.card_app_QueryLeftHotelList;

    Map<String, Object> param = new HashMap<String, Object>();

    public QueryLeftHotelListRequest(HotelProductListParam hotelParam)
    {
        // 公共参数
        param.put("addrId", hotelParam.getAddrId());

        if(!TextUtils.isEmpty( hotelParam.getRoomsTypes())){

            param.put("roomsTypes", hotelParam.getRoomsTypes());
        }

        param.put("bgnPrice", hotelParam.getBgnPrice());

        param.put("endPrice", hotelParam.getEndPrice());

        if(!TextUtils.isEmpty( hotelParam.getLevels())){

            param.put("levels", hotelParam.getLevels());

        }else {
            param.put("levels",0);
        }

        if(!TextUtils.isEmpty( hotelParam.getBrandTypes())){

            param.put("brandTypes", hotelParam.getBrandTypes());

        }else {

            param.put("brandTypes", 0);
        }

        param.put("sort", hotelParam.getSort());

        param.put("pageStart", hotelParam.getPageStart());

        param.put("pageSize", hotelParam.getPageSize());

    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}