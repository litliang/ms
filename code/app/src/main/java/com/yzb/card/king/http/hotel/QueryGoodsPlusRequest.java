package com.yzb.card.king.http.hotel;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：查询超值加购列表
 * 作  者：Li Yubing
 * 日  期：2017/8/18
 * 描  述：
 */
public class QueryGoodsPlusRequest extends BaseRequest{


    private String serverName = CardConstant.APP_QUERYGOODSPLUS;

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     *
     * @param industryId
     */
    public QueryGoodsPlusRequest(String industryId)
    {
        // 公共参数
        param.put("industryId", industryId);
    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}
