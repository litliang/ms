package com.yzb.card.king.http.hotel;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/22
 * 描  述：
 */
public class HotelCollectionRequest extends BaseRequest {

    public String serverName= CardConstant.card_app_collections;

    Map<String,Object> params=new HashMap<>();

    public HotelCollectionRequest(String storeId,String status,String type,String category){
        params.put("category", category);
        params.put("type",type);
        params.put("detailsId", storeId);
        params.put("status", status);
    }
    @Override
    public void sendRequest(HttpCallBackData callBack) {
        sendPostRequest(setParams(AppConstant.sessionId,serverName,AppConstant.UUID,params),callBack);
    }
}
