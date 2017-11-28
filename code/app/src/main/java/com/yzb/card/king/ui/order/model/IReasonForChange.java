package com.yzb.card.king.ui.order.model;

import java.util.Map;

/**
 * 类名： IReasonForChange
 * 作者： Lei Chao.
 * 日期： 2016-10-12
 * 描述： 退改签原因
 */

public interface IReasonForChange {

    /**
     * 退改签原因
     *
     * @param params
     * @param serviceName
     */
    void sendReasonForChangeRequest(Map<String, Object> params, String serviceName);

}
