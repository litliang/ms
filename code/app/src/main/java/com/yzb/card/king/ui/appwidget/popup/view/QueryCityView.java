package com.yzb.card.king.ui.appwidget.popup.view;

import com.yzb.card.king.ui.other.bean.IPlace;

import java.util.List;
import java.util.Map;

/**
 * 功能：城市查询；
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public interface QueryCityView
{
    void onGetCitysSucess(Object data);

    void onGetCitysFail(String failMsg);

    void onQueryFail();

    void onQuerySuccess(Map<String, List<IPlace>> places);
}
