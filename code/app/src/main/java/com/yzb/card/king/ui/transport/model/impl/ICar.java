package com.yzb.card.king.ui.transport.model.impl;

import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类名： ICar
 * 作者： Lei Chao.
 * 日期： 2016-09-07
 * 描述：
 */
public interface ICar
{

    /**
     * 自驾租车
     */
    int TYPE_SELF = 1;

    /**
     * 日包租车
     */
    int TYPE_DAILY = 2;

    void sendSelfDriveRequest(Map<String, Object> params, String serviceName);

    void sendDailyRentRequest(Map<String, Object> params, String serviceName);

    void setOnDataLoadFinish(DataCallBack onDataLoadFinish);
}