package com.yzb.card.king.ui.travel.model;

import android.text.TextUtils;

import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.http.DataCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：旅游请求实现类
 * 作  者：Li Yubing
 * 日  期：2016/11/28
 * 描  述：
 */
public class TravelListImpl implements ITravelList {

    private DataCallBack dataCallBack;

    @Override
    public void sendQueryTravelBaseInfoRequest(String type, String searchName, String depCityId, String arrType, String arrDetailId, String startDate, String endDate)
    {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("type", type);

        if (!TextUtils.isEmpty(searchName)) {

            param.put("searchName", searchName);
        }
        if (!TextUtils.isEmpty(depCityId)) {
            param.put("depCityId", depCityId);
        }
        if (!TextUtils.isEmpty(arrType)) {

            param.put("arrType", arrType);
        }
        if (!TextUtils.isEmpty(arrDetailId)) {
            param.put("arrDetailId", arrDetailId);
        }
        if (!TextUtils.isEmpty(startDate)) {
            param.put("startTravelDate", startDate);
        }
        if (!TextUtils.isEmpty(endDate)) {
            param.put("endTravelDate", endDate);
        }
        CommonServerRequest request = new CommonServerRequest();//

        request.setOnDataLoadFinish(dataCallBack);

        request.sendReuqest(param, CardConstant.QUERY_TRAVEL_BASEINFO_URL, ITravelList.QUERY_TRAVEL_BASEINFO_URL);
    }

    @Override
    public void sendQueryTravelProductRequest(Map<String, Object> map)
    {

        CommonServerRequest request = new CommonServerRequest();//

        request.setOnDataLoadFinish(dataCallBack);

        request.sendReuqest(map, CardConstant.QUERY_TRAVEL_PRODUCT_URL, ITravelList.QUERY_TRAVEL_PRODUCT_URL);

    }

    public void setDataCallBack(DataCallBack dataCallBack)
    {
        this.dataCallBack = dataCallBack;
    }
}
