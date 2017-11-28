package com.yzb.card.king.ui.integral.model;

import android.app.Activity;

import com.yzb.card.king.bean.integral.ExchangeIntegralBean;

import java.util.List;
import java.util.Map;

/**
 * 类  名：积分兑换接口
 * 作  者：Li Yubing
 * 日  期：2016/9/13
 * 描  述：
 */
public interface IIntegralExchange {

     String SPECIAL_CODE = "100";

    /**
     * 积分兑换计划请求识别符
     */
     int POINTEXCHANGEPLAN_CODE = 2;

    /**
     * 常旅客计划请求识别符
     */
     int FREQUENTPASSENGERPLANREQUEST_CODE = 3;


    /**
     * 发送积分兑换请求识别符
     */
     int INTEGRALPOINTEXCHANGECASH_CODE = 4;

    /**
     * 发送支付平台积分请求识别符
     */
     int PAYPOINT_CODE = 5;

    /**
     * 得到默认地址请求识别符
     */
     int DEFAULTADDRESS_CODE = 6;
    /**
     * 发送航空公司列表请求
     * @param countryId 国家id
     */
    public void sendAirlineCompanyListRequest(String countryId);

    /**
     * 发送获取积分交换计划请求
     *
     * 1、兑里程
     * 2、兑油卡（中石化）
     * 3、兑油卡（中石油）
     * 4、兑话费（中国移动）
     * 5、兑话费（中国联通）
     * 6、兑话费（中国电信）
     * 7、兑流量（中国移动）
     * 8、兑流量（中国联通）
     * 9、兑流量（中国电信）
     * 10、兑现金
     * @param type
     * @param airlineId
     *
     * 备注：type=1，airlineId必须有值
     */
   public void pointExchangePlanRequest(int  type,String airlineId);

    /**
     * 发送常旅客计划列表请求
     * @param airlineId
     */
    public void sendFrequentPassengerPlanRequest(String airlineId);

    /**
     * 发送积分兑换现金请求
     * @param type
     * @param adapterDataList
     * @param planId
     */
    public void sendIntegralPointExchangeCashRequest(int type, List<ExchangeIntegralBean> adapterDataList, String planId);

    /**
     * 发送积分兑换油卡请求
     * @param type
     * @param adapterDataList
     * @param planId
     * @param status
     * @param addressId
     * @param postPage
     * @param number
     */
    public void sendIntegralPointExchangeYoukaRequest(int type, List<ExchangeIntegralBean> adapterDataList, String planId, String status,String addressId,String postPage,String number);

    /**
     * 发送兑换流量和话费请求
     * @param adapterDataList
     * @param planId
     * @param moblie
     */
    public void sendIntegralPointExchangePhoneRequest(int type, List<ExchangeIntegralBean> adapterDataList, String planId, String moblie);

    /**
     *  发送支付平台积分请求
     * @param params
     */
    public void sendPayPointHandle(Map<String, String> params, Activity activity);

    /**
     * 得到默认地址
     */
    public void getDefaultAddress();

}
