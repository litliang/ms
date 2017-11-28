package com.yzb.card.king.ui.transport.model;

import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类名： IShopeSelect
 * 作者： Lei Chao.
 * 日期： 2016-09-07
 * 描述：
 */
public interface IShopeSelect
{

    /**
     * 附近门店请求
     * @param params
     * @param serviceName
     */
    void sendShopeSelectRequest(Map<String,Object> params,String serviceName);

    void setOnDataLoadFinish(DataCallBack onDataLoadFinish);

}
