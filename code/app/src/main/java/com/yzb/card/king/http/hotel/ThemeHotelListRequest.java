package com.yzb.card.king.http.hotel;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：主题酒店列表
 * 作  者：Li Yubing
 * 日  期：2016/7/14
 * 描  述：
 */
public class ThemeHotelListRequest extends BaseRequest {


    /**
     * 接口名
     */
    private String serverName = CardConstant.THEME_HOTEL_LIST_URL;


    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * @param themeId
     * @param pageStart
     */
    public ThemeHotelListRequest(String themeId, String pageStart)
    {

        param.put("themeId", themeId);

        param.put("pageStart", pageStart);

        param.put("pageSize", AppConstant.MAX_PAGE_NUM + "");

    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        //发送post请求
        sendPostRequest(setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}
