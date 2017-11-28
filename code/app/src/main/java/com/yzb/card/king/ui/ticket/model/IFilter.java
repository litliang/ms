package com.yzb.card.king.ui.ticket.model;

import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类名： IFilter
 * 作者： Lei Chao.
 * 日期： 2016-10-11
 * 描述： 获取筛选数据
 */
public interface IFilter
{

    /**
     * 获取筛选数据
     *
     * @param params
     * @param serviceName
     */
    void sendFilterDataRequest(Map<String, Object> params, String serviceName);

    void setOnDataLoadFinish(DataCallBack onDataLoadFinish);

}