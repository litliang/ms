package com.yzb.card.king.ui.appwidget.popup.presenter;


import java.util.Map;

/**
 * 功能：城市查询；
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public interface QueryCitysPresenter
{
    /**
     * 加载数据；
     */
    void loadData(Map<String, Object> paramMap);

    void queryCity(String currentRegion, String key);

    void sumHotCity(int industryId,String cityId);
}
