package com.yzb.card.king.http.hotel;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.hotel.HotelOrderParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：酒店订单请求类
 * 作  者：Li Yubing
 * 日  期：2017/8/14
 * 描  述：
 */
public class HotelOrderCreateRequest extends BaseRequest {

    private String serverName = CardConstant.hotel_hotelordercreate;

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     *
     * @param hotelOrderParam
     */
    public HotelOrderCreateRequest(HotelOrderParam hotelOrderParam)
    {
        // 公共参数
        param.put("shopId",hotelOrderParam.getShopId());
        param.put("hotelId", hotelOrderParam.getHotelId());
        param.put("roomsId", hotelOrderParam.getRoomsId());
        param.put("policysId",hotelOrderParam.getPolicysId());

        param.put("paymentType",hotelOrderParam.getPaymentType());
        param.put("goodsQuantity", hotelOrderParam.getGoodsQuantity());

        param.put("arrDate", hotelOrderParam.getArrDate());
        param.put("depDate", hotelOrderParam.getDepDate());

        if(!TextUtils.isEmpty(hotelOrderParam.getRetentionTime())){
            param.put("retentionTime", hotelOrderParam.getRetentionTime());
        }
       if(!TextUtils.isEmpty(hotelOrderParam.getGuaranteeTime())){
           param.put("guaranteeTime", hotelOrderParam.getGuaranteeTime());
       }

        param.put("orderAmount", hotelOrderParam.getOrderAmount());
        param.put("orderRemark",hotelOrderParam.getOrderRemark());
        String guestListTemp = JSON.toJSONString(hotelOrderParam.getGuestList());
        param.put("guestList",guestListTemp);
        param.put("contactsInfo",JSON.toJSONString(hotelOrderParam.getContactsInfo()));

        param.put("addList",JSON.toJSONString(hotelOrderParam.getAddList()));

        if(hotelOrderParam.getInvoiceInfo()!=null){
            param.put("invoiceInfo",JSON.toJSONString(hotelOrderParam.getInvoiceInfo()));
        }

    }

    /**
     *创建酒店其它产品请求类
     * @param hotelOrderParam
     */
    public HotelOrderCreateRequest(HotelOrderParam hotelOrderParam,int type)
    {

        serverName = CardConstant.hotel_HotelOrderCreateOtherGoods;
        // 公共参数
        param.put("shopId",hotelOrderParam.getShopId());
        param.put("hotelId", hotelOrderParam.getHotelId());
        param.put("goodsType", hotelOrderParam.getGoodsType());
        param.put("goodsId",hotelOrderParam.getGoodsId());

        param.put("policysId",hotelOrderParam.getPolicysId());
        param.put("policysName",hotelOrderParam.getPolicysName());

        param.put("orderAmount",hotelOrderParam.getOrderAmount());
        param.put("goodsQuantity", hotelOrderParam.getGoodsQuantity());

        param.put("orderRemark", hotelOrderParam.getOrderRemark());
        param.put("contactsInfo",JSON.toJSONString(hotelOrderParam.getContactsInfo()));

        param.put("addList",JSON.toJSONString(hotelOrderParam.getAddList()));

        if(hotelOrderParam.getInvoiceInfo()!=null){
            param.put("invoiceInfo",JSON.toJSONString(hotelOrderParam.getInvoiceInfo()));
        }
    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}

