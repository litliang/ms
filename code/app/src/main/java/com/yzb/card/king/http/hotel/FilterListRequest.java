package com.yzb.card.king.http.hotel;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 过滤项列表,酒店关键字搜索
 * Created by 玉兵 on 2017/7/28.
 */

public class FilterListRequest extends BaseRequest {

    public String serverName= CardConstant.HOTEL_FILTER_LIST;

    Map<String,Object> params=new HashMap<>();

    /**
     *
     * @param industryId
     * @param industryFun  行业id  酒店（1酒店列表；2礼品套餐；）；机票（1机票列表）
     * @param funType  功能类别 行业功能  1搜索；2筛选；3位置区域；4品牌；
     * @param addrId   位置id 如上海id（4级），暂无5级
     */
    public FilterListRequest(String industryId,String industryFun,String funType,String addrId){
        serverName= CardConstant.HOTEL_FILTER_LIST;
        params.put("industryId", industryId);
        params.put("industryFun",industryFun);
        params.put("funType", funType);
        params.put("addrId", addrId);
    }

    /**
     * 酒店关键字搜索请求构造器
     * @param cityName 城市名称
     * @param keyWord  关键字
     */
    public FilterListRequest(String cityName,String keyWord){
        serverName= CardConstant.HOTEL_KEYWORD_SEARCH;
        params.put("cityName", cityName);
        params.put("keyword",keyWord);
    }

    /**
     * 酒店礼品卡套餐关键字搜索请求构造器
     * @param addrId
     * @param giftsType 7 卡权益；8限时抢购
     * @param industryId  行业id
     * @param searchName
     * @param pageStart
     */
    public FilterListRequest(String addrId,int giftsType,int industryId,String searchName,int pageStart){
        serverName= CardConstant.HOTEL_SEARCHGIFTSSTORE;
        params.put("addrId", addrId);
        params.put("giftsType",giftsType);

        params.put("industryId", industryId);
        params.put("searchName",searchName);

        params.put("pageStart", pageStart);

        params.put("pageSize", AppConstant.MAX_PAGE_NUM);
    }



    @Override
    public void sendRequest(HttpCallBackData callBack) {
        sendPostRequest(setParams(AppConstant.sessionId,serverName,AppConstant.UUID,params),callBack);
    }
}
