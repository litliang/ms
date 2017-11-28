package com.yzb.card.king.http.travel;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：旅游特卖惠
 * 作  者：Li Yubing
 * 日  期：2016/9/1
 * 描  述：
 */
public class TravelSaleListRequest extends BaseRequest {

	private String serverName = CardConstant.TRAVEL_SALE_TITLE;

	Map<String, Object> params = new HashMap<>();

	public TravelSaleListRequest() {
//        params.put("cityId", "1116");
		params.put("industryId", "3");
		params.put("pageStart", 0);
		params.put("pageSize", AppConstant.MAX_PAGE_NUM);
	}

	@Override
	public void sendRequest(HttpCallBackData callBack) {
		sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, params), callBack);
	}
}
