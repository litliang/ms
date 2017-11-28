package com.yzb.card.king.ui.ticket.model;

import com.yzb.card.king.ui.credit.model.IBaseOnLoadFinish;

import java.util.Map;

/**
 * 类名： IFindLow
 * 作者： Lei Chao.
 * 日期： 2016-10-11
 * 描述： 發現低價
 */

public interface IFindLow extends IBaseOnLoadFinish {
    
    int SING_TYPE = 1;

    int ROUND_TYPE = 2;

    /**
     * 查詢最低價格
     *
     * @param params
     * @param serviceName
     */
    void sendFindLowRequest(Map<String, Object> params, String serviceName);


    void sendRoundLowRequest(Map<String, Object> params, String serviceName);

}
