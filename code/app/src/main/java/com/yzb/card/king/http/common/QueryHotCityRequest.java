package com.yzb.card.king.http.common;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：查询热门城市请求类
 * 作  者：Li Yubing
 * 日  期：2017/8/23
 * 描  述：查询热门城市、统计热门城市
 */
public class QueryHotCityRequest  extends BaseRequest {

    private String serverName= CardConstant.QUERY_HOT_CITY;

    Map<String,Object> params=new HashMap<>();

    public QueryHotCityRequest(String regionId,String industryId,String type)
    {
        serverName= CardConstant.QUERY_HOT_CITY;
        params.put("regionId", regionId);
        params.put("industryId", industryId);
        params.put("type", type);

    }

    public QueryHotCityRequest(String addrId,String industryId)
    {
        serverName= CardConstant.HOTADDRSTATISTICS;
        params.put("addrId", addrId);
        params.put("industryId", industryId);

    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId,serverName,AppConstant.UUID,params),callBack);
    }
}
