package com.yzb.card.king.http.hotel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/8
 * 描  述：
 */
public class QueryGiftsListRequest extends BaseRequest
{

    private String serverName = CardConstant.CARD_APP_QUERYRIGHTSLIST;

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * 构造器
     * @param hotelParam
     */
    public QueryGiftsListRequest(HotelProductListParam hotelParam)
    {
        // 公共参数
        param.put("addrId", hotelParam.getAddrId());

        param.put("industryId", hotelParam.getIndustryId());

        if(hotelParam.getStoreName() != null){
            param.put("storeName", hotelParam.getStoreName());
        }

        if( hotelParam.getEffMonth()!= null){
            param.put("effMonth", hotelParam.getEffMonth());
        }

        if(hotelParam.getStoreUseType() != null){
            param.put("storeUseType", hotelParam.getStoreUseType());
        }

        param.put("bgnPrice", hotelParam.getBgnPrice());

        param.put("endPrice", hotelParam.getEndPrice());

        param.put("sort", hotelParam.getSort());

        param.put("pageStart", hotelParam.getPageStart());

        param.put("pageSize", hotelParam.getPageSize());

        if(hotelParam.getFilterList() != null && hotelParam.getFilterList() .size()>0){

            param.put("filterList", JSON.toJSONString(hotelParam.getFilterList()));
        }

        if(hotelParam.getActType() > 0){
            param.put("actType", hotelParam.getActType());
        }

    }

    /**
     * 设置限时抢购
     */
    public void setQueryFlashsaleListServer(){

        serverName = CardConstant.CARD_APP_QUERYFLASHSALELIST;
    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}
