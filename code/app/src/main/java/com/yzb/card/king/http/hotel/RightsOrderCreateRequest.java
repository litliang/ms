package com.yzb.card.king.http.hotel;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.hotel.HotelOrderParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/11
 * 描  述：
 */
public class RightsOrderCreateRequest extends BaseRequest {

    private String serverName = CardConstant.RIGHTSORDERCREATE_URL;

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * @param hotelOrderParam
     */
    public RightsOrderCreateRequest(HotelOrderParam hotelOrderParam)
    {
        // 公共参数
        param.put("shopId", hotelOrderParam.getShopId());
        param.put("hotelId", hotelOrderParam.getHotelId());
        param.put("actId", hotelOrderParam.getActId());

        param.put("goodsQuantity", hotelOrderParam.getGoodsQuantity());

        param.put("effDate", hotelOrderParam.getEffDate());

        param.put("receiveType", hotelOrderParam.getReceiveType());

        param.put("orderAmount", hotelOrderParam.getOrderAmount());

        param.put("useName", hotelOrderParam.getUseName());

        param.put("orderRemark", hotelOrderParam.getOrderRemark());

        param.put("contactsInfo", JSON.toJSONString(hotelOrderParam.getContactsInfo()));

        if (hotelOrderParam.getDeliveryAddress() != null) {
            param.put("deliveryAddress", JSON.toJSONString(hotelOrderParam.getDeliveryAddress()));
        }

        if (hotelOrderParam.getAddList() != null) {
            param.put("addList", JSON.toJSONString(hotelOrderParam.getAddList()));
        }

        if (hotelOrderParam.getInvoiceInfo() != null) {
            param.put("invoiceInfo", JSON.toJSONString(hotelOrderParam.getInvoiceInfo()));
        }

    }

    /**
     * 限时抢购构造器
     *
     * @param hotelOrderParam
     * @param flag
     */
    public RightsOrderCreateRequest(HotelOrderParam hotelOrderParam, boolean flag)
    {
        serverName = CardConstant.FLASHSALEORDERCREATE_URL;
        // 公共参数
        param.put("actType", hotelOrderParam.getActType());
        param.put("actId", hotelOrderParam.getActId());
        param.put("shopId", hotelOrderParam.getShopId());

        param.put("storeId", hotelOrderParam.getShopId());

        param.put("bankId", hotelOrderParam.getBankId());

        param.put("goodsQuantity", hotelOrderParam.getGoodsQuantity());

        param.put("useType", hotelOrderParam.getReceiveType());

        param.put("useName", hotelOrderParam.getUseName());

        param.put("orderRemark", hotelOrderParam.getOrderRemark());

        param.put("orderAmount", hotelOrderParam.getOrderAmount());

        param.put("contactsInfo", JSON.toJSONString(hotelOrderParam.getContactsInfo()));

        if (hotelOrderParam.getAddList() != null) {
            param.put("addList", JSON.toJSONString(hotelOrderParam.getAddList()));
        }

        if (hotelOrderParam.getInvoiceInfo() != null) {
            param.put("invoiceInfo", JSON.toJSONString(hotelOrderParam.getInvoiceInfo()));
        }

    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {

        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}
