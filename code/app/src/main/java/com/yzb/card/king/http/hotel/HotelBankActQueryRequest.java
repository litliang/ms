package com.yzb.card.king.http.hotel;

import android.text.TextUtils;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/28
 * 描  述：
 */
public class HotelBankActQueryRequest extends BaseRequest {


    private String serverName = CardConstant.QUERY_BANK_ACTIVITY_LIST;

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     *   查询银行优惠的活动请求构造器
     * @param cityId
     * @param bankIds
     * @param industryId
     * @param actType 优惠类型(0全部；1满减；2折扣；3随机立减；)
     * @param effMonth  活动期限月份(0不限；)
     * @param pageStart
     */
    public HotelBankActQueryRequest(int cityId,String bankIds,int industryId,int actType,int effMonth,int pageStart )
    {
        serverName = CardConstant.QUERY_BANK_ACTIVITY_LIST;
        // 公共参数
        param.put("cityId", cityId);
        param.put("bankIds", bankIds);
        param.put("industryId", industryId);
        param.put("actType", actType);
        param.put("effMonth", effMonth);
        param.put("pageStart", pageStart);
        param.put("pageSize", AppConstant.MAX_PAGE_NUM);
    }


    /**
     *    查询银行分期活动请求的构造器
     * @param cityId
     * @param bankIds
     * @param industryId
     * @param stage 分期数(直接传3、6、12等数字)
     * @param pageStart
     */
    public HotelBankActQueryRequest(int cityId,String bankIds,int industryId,int stage,int pageStart )
    {
        serverName = CardConstant.QUERY_BANK_STAGE_LIST;
        // 公共参数
        param.put("cityId", cityId);
        param.put("industryId", industryId);
        param.put("pageStart", pageStart);
        param.put("pageSize", AppConstant.MAX_PAGE_NUM);
        if(!TextUtils.isEmpty(bankIds)){
            param.put("bankIds", bankIds);
        }

        if(stage != -1){
            param.put("stage", stage);
        }

    }

    /**
     *  查询产品可支持的银行优惠详情请求的构造器
     * @param industryId
     * @param shopId
     * @param storeId
     * @param goodsType
     * @param goodsId
     * @param startDate yyyy-MM-dd
     */
    public HotelBankActQueryRequest(int industryId,long shopId,long storeId,int goodsType,long goodsId,String startDate )
    {
        serverName = CardConstant.QUERY_GOODS_BANK_ACTIVITY_HTTP;
        // 公共参数
        param.put("industryId", industryId);
        param.put("shopId", shopId);
        param.put("storeId", storeId);
        param.put("goodsType", goodsType);
        param.put("goodsId", goodsId);
        param.put("startDate", startDate);
    }


    /**
     *  查询产品可支持的银行分期明细详情请求的构造器
     * @param industryId
     * @param shopId
     * @param storeId
     * @param goodsType
     * @param goodsId
     * @param goodsAmount
     */
    public HotelBankActQueryRequest(int industryId,long shopId,long storeId,int goodsType,long goodsId,double goodsAmount )
    {
        serverName = CardConstant.QUERY_BANKSTAGEINFO_HTTP;
        // 公共参数
        param.put("industryId", industryId);
        param.put("shopId", shopId);
        param.put("storeId", storeId);
        param.put("goodsType", goodsType);
        param.put("goodsId", goodsId);
        param.put("goodsAmount", goodsAmount);
    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}
