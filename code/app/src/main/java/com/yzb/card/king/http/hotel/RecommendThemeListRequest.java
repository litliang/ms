package com.yzb.card.king.http.hotel;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

import static com.yzb.card.king.sys.GlobalVariable.industryId;

/**
 * 类  名：酒店推荐主题
 * 作  者：Li Yubing
 * 日  期：2016/7/14
 * 描  述：
 */
public class RecommendThemeListRequest extends BaseRequest {

	Map<String, Object> param = new HashMap<String, Object>();


	/**
	 * 接口名
	 */
	private String serverName = CardConstant.RECOMMEND_THEME_LIST_URL;

	/**
	 * @param cityId
	 * @param pageStart
	 */
	public RecommendThemeListRequest( String pageStart) {
		param.put("pageStart", pageStart);
		param.put("industryId", "8");
		param.put("pageSize", AppConstant.MAX_PAGE_NUM + "");
	}


	@Override
	public void sendRequest(HttpCallBackData callBack) {
		//发送post请求
		sendPostRequest(setParams(AppConstant
				.sessionId, serverName, AppConstant.UUID, param), callBack);
	}
}
