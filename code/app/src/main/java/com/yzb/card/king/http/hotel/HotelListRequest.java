package com.yzb.card.king.http.hotel;

import com.yzb.card.king.bean.hotel.HotelParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.util.DateUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：酒店列表请求
 * 作  者：Li JianQiang
 * 日  期：2016/8/8
 * 描  述：
 */
public class HotelListRequest extends BaseRequest
{

    private String serverName = CardConstant.card_app_hotel_list;

    Map<String, Object> param = new HashMap<String, Object>();

    public HotelListRequest(HotelParam hotelParam)
    {
        // 公共参数
        param.put("cityId", hotelParam.getCityId());
        param.put("source", hotelParam.getSource());
        param.put("pageStart", hotelParam.getPageStart());
        param.put("pageSize", AppConstant.MAX_PAGE_NUM);
        param.put("levels", hotelParam.getLevels());
        param.put("arrDate", DateUtil.date2String(hotelParam.getArrDate(),DateUtil.DATE_FORMAT_DATE));
        param.put("depDate", DateUtil.date2String(hotelParam.getDepDate(),DateUtil.DATE_FORMAT_DATE));
        param.put("bgnPrice", hotelParam.getBgnPrice());
        param.put("endPrice", hotelParam.getEndPrice());
        param.put("lng", hotelParam.getLng()); // 经度；
        param.put("lat", hotelParam.getLat());
        param.put("distance", hotelParam.getDistance()); //距离
        param.put("lng", hotelParam.getLng()); // 经度；
        param.put("lat", hotelParam.getLat());
        param.put("childIndustryIds", hotelParam.getChildIndustryIds());
        param.put("sort", hotelParam.getSort()); //排序
        param.put("minVote", hotelParam.getMinVote());
        param.put("breakfasts", hotelParam.getBreakfasts());
        param.put("bedTypes", hotelParam.getBedTypes());
        param.put("searchList", hotelParam.getSearchList());
        param.put("positionList", hotelParam.getPositionList());
        param.put("bankStruts",hotelParam.getBankStruts());
        param.put("keyword",hotelParam.getKeyword());
        param.put("cancelStatus",hotelParam.getCancelStatus());
    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}
