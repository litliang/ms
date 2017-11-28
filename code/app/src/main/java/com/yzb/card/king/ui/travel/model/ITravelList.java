package com.yzb.card.king.ui.travel.model;

import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/11/28
 * 描  述：
 */
public interface ITravelList {

    /**
     * 全部
     */
    public static final String  ALL_BANKSTATUS_CODE = "0";
    /**
     * 我的银行
     */
    public static final String  MY_BANKSTATUS_CODE = "1";
    /**
     * 更多银行
     */
    public static final  String MORE_BANKSTATUS_CODE = "2";

    /**
     * 发送旅游基本信息请求识别符
     */
    public static final int  QUERY_TRAVEL_BASEINFO_URL = 1;

    /**
     * 发送查询旅游产品列表请求识别符
     */
    public static final int QUERY_TRAVEL_PRODUCT_URL = 2;

    /**
     * 发送旅游基本信息请求
     * @param type 1旅游类型、2旅游供应商、3当地特色
     * @param searchName  搜索内容
     * @param depCityId  四级城市id
     * @param arrType  1景点、4城市
     * @param arrDetailId 类型对应id（景点ID、城市id）
     * @param startDate  yyyy-MM-dd
     * @param endDate  yyyy-MM-dd
     */
    public void sendQueryTravelBaseInfoRequest(String type,String searchName,String depCityId,String arrType,String arrDetailId,String startDate,String endDate);

    /**
     * 发送查询旅游产品列表请求
     * @param map
     */
    public void sendQueryTravelProductRequest(Map<String, Object> map);

   public void  setDataCallBack(DataCallBack dataCallBack);
}
