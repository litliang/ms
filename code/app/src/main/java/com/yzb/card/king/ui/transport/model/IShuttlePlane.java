package com.yzb.card.king.ui.transport.model;

import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类名： IShuttlePlane
 * 作者： Lei Chao.
 * 日期： 2016-09-05
 * 描述： 接送机 和 接送火车的 功能接口
 */
public interface IShuttlePlane
{

    /**
     * 查询车型
     *
     * @param params      请求参数
     * @param serviceName 接口名称
     */
    void sendCardTypeRequest(Map<String, Object> params, String serviceName);

    void setOnDataLoadFinish(DataCallBack onDataLoadFinish);

}
