package com.yzb.card.king.http.hotel;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.hotel.HotelParam;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 酒店产品列表请求类
 * <p>
 * 酒店列表、酒店地图
 */
public class HotelProductListRequest extends BaseRequest {

    private String serverName = CardConstant.card_app_hotel_list;

    Map<String, Object> param = new HashMap<String, Object>();

    public HotelProductListRequest() {

    }

    public HotelProductListRequest(HotelProductListParam hotelParam) {
        // 公共参数
       // param.put("addrId", hotelParam.getAddrId());
        param.put("addrName", hotelParam.getAddrName());
        param.put("searchAddrType", hotelParam.getSearchAddrType());
        param.put("searchAddrLng", hotelParam.getSearchAddrLng());
        param.put("searchAddrLat", hotelParam.getSearchAddrLat());
        param.put("bgnPrice", hotelParam.getBgnPrice());
        param.put("endPrice", hotelParam.getEndPrice());
        param.put("levels", hotelParam.getLevels());
        param.put("brandTypes", hotelParam.getBrandTypes());
        param.put("arrDate", hotelParam.getArrDate());
        param.put("depDate", hotelParam.getDepDate());
        param.put("sort", hotelParam.getSort());
        param.put("pageStart", hotelParam.getPageStart());
        param.put("pageSize", hotelParam.getPageSize());

        if(!TextUtils.isEmpty(hotelParam.getCashCouponId())) {

            param.put("cashCouponId", hotelParam.getCashCouponId());
        }

        if(!TextUtils.isEmpty( hotelParam.getMinVote())){
            param.put("minVote", hotelParam.getMinVote());
        }


        if (!TextUtils.isEmpty(hotelParam.getBankIds())) {
            param.put("bankIds", hotelParam.getBankIds());
        }

        if(!TextUtils.isEmpty( hotelParam.getDisTypes())){
            param.put("disTypes", hotelParam.getDisTypes());
        }

        if (!TextUtils.isEmpty(hotelParam.getStageBankIds())) {
            param.put("stageBankIds", hotelParam.getStageBankIds());
        }

        List<SubItemBean> filterList = hotelParam.getFilterList();
        if ( filterList!= null && filterList.size() > 0) {

            LogUtil.e("size --end---="+filterList.size() );
            param.put("filterList", JSON.toJSONString(filterList));
        }

        if (hotelParam.getSearchList() != null && hotelParam.getSearchList().size() > 0) {
            param.put("searchList", JSON.toJSONString(hotelParam.getSearchList()));
        }


    }

    /**
     * 设置酒店地图请求接口
     */
    public void setHotelMapProductInterfaceData(HotelProductListParam hotelParam) {
        serverName = CardConstant.hotel_queryhotelmaplist;
        param.put("addrLevel", hotelParam.getAddrLevel());
        param.put("addrId", hotelParam.getAddrId());
        param.put("searchAddrType", hotelParam.getSearchAddrType());
        param.put("searchAddrLng", hotelParam.getSearchAddrLng());
        param.put("searchAddrLat", hotelParam.getSearchAddrLat());
        param.put("bgnPrice", hotelParam.getBgnPrice());
        param.put("endPrice", hotelParam.getEndPrice());
        param.put("levels", hotelParam.getLevels());
        param.put("brandTypes", hotelParam.getBrandTypes());
        param.put("arrDate", hotelParam.getArrDate());
        param.put("depDate", hotelParam.getDepDate());
        param.put("sort", 3);//地图模式下，默认距离优先
        param.put("pageStart", hotelParam.getPageStart());
        param.put("pageSize", hotelParam.getPageSize());

        if(!TextUtils.isEmpty(hotelParam.getCashCouponId())) {

            param.put("cashCouponId", hotelParam.getCashCouponId());
        }

        if(!TextUtils.isEmpty( hotelParam.getMinVote())){
            param.put("minVote", hotelParam.getMinVote());
        }

        if (!TextUtils.isEmpty(hotelParam.getBankIds())) {
            param.put("bankIds", hotelParam.getBankIds());
        }

        if(!TextUtils.isEmpty( hotelParam.getDisTypes())) {
            param.put("disTypes", hotelParam.getDisTypes());
        }

        if (!TextUtils.isEmpty(hotelParam.getStageBankIds())) {
            param.put("stageBankIds", hotelParam.getStageBankIds());
        }
        if (hotelParam.getFilterList() != null && hotelParam.getFilterList().size() > 0) {
            param.put("filterList", JSON.toJSONString(hotelParam.getFilterList()));

        }
        if (hotelParam.getSearchList() != null && hotelParam.getSearchList().size() > 0) {
            param.put("searchList", JSON.toJSONString(hotelParam.getSearchList()));

        }

    }

    @Override
    public void sendRequest(HttpCallBackData callBack) {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}

