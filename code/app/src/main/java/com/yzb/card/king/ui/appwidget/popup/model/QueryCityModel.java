package com.yzb.card.king.ui.appwidget.popup.model;

/**
 * 功能：城市查询；
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public interface QueryCityModel
{
    void getCities();

    void queryCity(String type,String key);

    void hotCityStatistics(int industryId,String cityId);
}
