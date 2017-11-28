package com.yzb.card.king.http.integral;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.integral.ExchangeIntegralBean;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：积分兑换请求类
 * 作  者：Li Yubing
 * 日  期：2016/9/14
 * 描  述：
 */
public class IntegralPoinExchangeRequest extends BaseRequest{

    /**
     * 接口名
     */
    private String serverName = CardConstant.INTEGRAL_POINTEXCHANGE;

    /**
     * 请求参数
     */
    Map<String, Object> param = new HashMap<String, Object>();


    /**
     * 兑换流量和话费构造器
     * @param type
     * @param adapterDataList
     * @param planId
     */
    public IntegralPoinExchangeRequest(int type, List<ExchangeIntegralBean> adapterDataList, String planId, String moblie) {

        List<ExchangeIntegralBean> totalList = new ArrayList<ExchangeIntegralBean>();

        for (ExchangeIntegralBean b : adapterDataList) {

            ExchangeIntegralBean a = new ExchangeIntegralBean();

            a.setPlanId(planId);

            a.setPoint(b.getPoint());

            a.setRuleId(b.getRuleId());
            a.setMoblie(moblie);

            a.setFlow(b.getExchangeResult());

            a.setTelFare(b.getExchangeResult());

            totalList.add(a);

        }

        param.put("type", type);

        param.put("exchangeList", JSON.toJSONString(totalList));

    }


    /**
     *  兑换现金构造器
     * @param type
     * @param adapterDataList
     * @param planId
     */
    public IntegralPoinExchangeRequest(int type, List<ExchangeIntegralBean> adapterDataList, String planId)
    {
        List<ExchangeIntegralBean> totalList = new ArrayList<ExchangeIntegralBean>();

        for (ExchangeIntegralBean b : adapterDataList) {

            ExchangeIntegralBean a = new ExchangeIntegralBean();

            a.setPlanId(planId);

            a.setPPFId(planId);

            a.setPoint(b.getPoint());

            a.setCash(b.getExchangeResult());

            a.setRuleId(b.getRuleId());

            a.setMileage(b.getExchangeResult());

            totalList.add(a);

        }

        param.put("type", type);

        param.put("exchangeList", JSON.toJSONString(totalList));
    }


    /**
     * 兑换油卡
     * @param type
     * @param adapterDataList
     * @param planId
     * @param status
     * @param addressId
     * @param postPage
     * @param number
     */
    public IntegralPoinExchangeRequest( int type, List<ExchangeIntegralBean> adapterDataList, String planId, String status, String addressId, String postPage, String number) {

        List<ExchangeIntegralBean> totalList = new ArrayList<ExchangeIntegralBean>();

        for (ExchangeIntegralBean b : adapterDataList) {

            ExchangeIntegralBean a = new ExchangeIntegralBean();

            a.setPlanId(planId);

            a.setPoint(b.getPoint());

            a.setRuleId(b.getRuleId());

            a.setOil(b.getExchangeResult());

            a.setStatus(status);

            a.setAddressId(addressId);

            a.setPostage(postPage);

            a.setNumber(number);


            totalList.add(a);

        }
        param.put("type", type);

        param.put("exchangeList", JSON.toJSONString(totalList));

    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        //发送post请求
        sendPostRequest( setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID,param),callBack);
    }
}
